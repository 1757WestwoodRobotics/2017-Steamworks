package com.team1757.utils;

import java.util.concurrent.atomic.AtomicInteger;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

/**
 * Implementation of Maxbotix MB1013 Ultrasonic rangefinder for RS-232 serial (digital) input
 * 
 * Updates serial asynchronously every .5s
 * 
 * Provides voltage scaling to report range in mm, cm, or inches
 * 
 * See {@link http://www.maxbotix.com/documents/HRLV-MaxSonar-EZ_Datasheet.pdf } for details
 * 
 * @author ACabey
 */

public class MaxbotixUltrasonicSerial implements PIDSource {

	private final double DISTANCE_MIN_MM = 300.0; // Advertised minumum distance
													// is 300 (mm)
	private final double DISTANCE_MAX_MM = 3000.0; // Advertised max distance is
													// 5000 (mm)
	private final int RESPONSE_BYTES = 6;			// R header byte, 4 bytes payload, ASCII 13 termination

	private SerialPort serial;
	private Unit defaultUnit = Unit.kInches;
	
	private Thread serialReader;
	private AtomicInteger rangeOutput;
	
	private PIDSourceType pidSource = PIDSourceType.kDisplacement;
	

	public MaxbotixUltrasonicSerial() {
		this(Port.kOnboard);
	}
	
	public MaxbotixUltrasonicSerial(Port port) {
		
		rangeOutput = new AtomicInteger();
		
		try {
			serial = new SerialPort(9600, port, 8, Parity.kNone, StopBits.kOne);
		}
		catch(RuntimeException e) {
			if(e.getMessage().contains("Busy")) {
				System.out.println("Failed to initialize serial port: Serial port busy");
			}
			else {
				System.out.println("Failed to initialize serial port");
			}
		}
		// serial.setTimeout(2);
		serial.setReadBufferSize(RESPONSE_BYTES);
		
		serialReader = new Thread(this::readBytes);
		serialReader.start();
	}
	
	
	/**
	 * validateResponse
	 * 
	 * Checks if response from serial buffer matches format for MB1013 (e.g. R1234<13>)
	 * 
	 * @param Response from serial buffer
	 * @return Response is valid
	 */
	private boolean validateResponse(String response) {
		
		// Check malformed buffer
		if(response == null || response.length() < 4) {
			return false;
		}
		
		// Strip header and footer ('R', ASCII 13)
		response = response.substring(1, response.length() - 1);
		
		
		// Check payload is all digits
		for (char ordinal : response.toCharArray()) {
			if (!(Character.isDigit(ordinal))) return false;
		}
		
		return true;
	}
	
	
	/**
	 * parseResponse
	 * 
	 * Parses the response from serial buffer to an integer
	 * 
	 * Result is the measured range in MM
	 * 
	 * @param Response from serial buffer
	 * @return Parsed double representation of digits
	 */
	private int parseResponse(String response) throws NumberFormatException {
		
		// Strip header and footer ('R', ASCII 13)
		response = response.substring(1, response.length() - 1);
		
		try {
			int range = Integer.parseInt(response);
			return range;
		}
		catch(NumberFormatException e) {
			throw e;
		}
	}
	
	private void readBytes() {
		while(true) {

			String serialBuffer;
			
			serialBuffer = serial.readString(RESPONSE_BYTES);	
			
			if(serialReader.isInterrupted()) {
				return;
			}
			
			if(validateResponse(serialBuffer) == true) {
				int output = -1;
				try {
					 output = parseResponse(serialBuffer);
				}
				catch (NumberFormatException e) {
					System.out.println("Failed to parse integer from response");
				}
				
				rangeOutput.set(output);
			} 
			else {
				serial.reset();
			}

		}
	}
	
	/**
	 * getRangeMM
	 * 
	 * The output is an ASCII capital "R", followed by four ASCII character
	 * digits representing the range in millimeters, followed by a carriage
	 * return (ASCII 13). The maximum distance reported is 5000. The serial
	 * output is the most accurate of the range outputs
	 * 
	 * Serial data sent is 9600 baud, with 8 data bits, no parity, and one stop bit
	 * 
	 * @return double measured range (mm)
	 * @return -1.0 if there was an error reading from serial
	 */
	private double getRangeMM() {
		return (double) rangeOutput.get();
	}
	
    /**
     * getDefaultUnit
     * 
     * @return Current default unit
     */
    public Unit getDefaultUnit() {
    	return defaultUnit;
    }
    
    /**
     * setDefaultUnit
     * 
     * @param unit
     * 		Unit instance for default measurements
     */
    public void setDefaultUnit(Unit unit) {
    	defaultUnit = unit;
    }
    
    /**
     * getRange
     * 
     * @return double measured range (default unit)
     * @return -1.0 if there was an error reading from serials
     */
    public double getRange() {
    	double rangeMM = getRangeMM();
    	
    	// Return error values
    	if (rangeMM < 0.0) {
    		return rangeMM;
    	}
    	else {
    		return defaultUnit.get(getRangeMM());
    	}
    }
    
	/**
	 * getRange
	 * 
	 * @return double measured range (unit)
	 * @return -1.0 if there was an error reading from serial
	 */
	public double getRange(Unit unit) {
    	double rangeMM = getRangeMM();
    	
    	// Return error values
    	if (rangeMM < 0.0) {
    		return rangeMM;
    	}
    	else {
    		return unit.get(getRangeMM());
    	}
	}
	
	/**
	 * getRangeRaw
	 * 
	 * @return Raw string response from console terminated by carriage-return
	 */
	public String getRangeRaw() {
		String response = serial.readString();

		if (response.isEmpty() || response == null) {
			return "";
		} else {
			return response;
		}
	}
	
    /**
     * getMinRange
     * 
     * @param Desired unit of length
     * @return Minimum accurate distance in unit
     */
    public double getMinRange(Unit unit) {
    	return unit.get(DISTANCE_MIN_MM);
    }
    
    /**
     * getMinRange
     * 
     * @return Minimum accurate distance in default unit
     */
    public double getMinRange() {
    	return defaultUnit.get(DISTANCE_MIN_MM);
    }
    
    /**
     * getMaxRange
     * 
     * @param Desired unit of length
     * @return Maximum accurate distance in unit
     */
    public double getMaxRange(Unit unit) {
    	return unit.get(DISTANCE_MAX_MM);
    }
    
    /**
     * getMaxRange
     * 
     * @return Maximum accurate distance in default unit
     */
    public double getMaxRange() {
    	return defaultUnit.get(DISTANCE_MAX_MM);
    }
    
    @Override
    public double pidGet() {
    	return getRange(defaultUnit);
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}
}
