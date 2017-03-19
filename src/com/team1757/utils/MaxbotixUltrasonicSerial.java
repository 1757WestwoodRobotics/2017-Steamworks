package com.team1757.utils;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

/**
 * Implementation of Maxbotix MB1013 Ultrasonic rangefinder for RS-232 serial (digital) input
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

	private SerialPort serial;
	private Unit defaultUnit = Unit.kInches;
	private PIDSourceType pidSource = PIDSourceType.kDisplacement;
	

	public MaxbotixUltrasonicSerial() {
		serial = new SerialPort(9600, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
		serial.enableTermination((char) 13);
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
	 * @return -1.0 if the voltage is below the minimum
	 * @return -2.0 if voltage is above the maximum
	 */
	private double getRangeMM() {
		String response = serial.readString();

		if (response.isEmpty() || response == null) {
			return -1.0;
		} else {
			double parsed = Double.parseDouble(response.substring(1));
			if (parsed < DISTANCE_MIN_MM) {
				return -1.0;
			} else if (parsed > DISTANCE_MAX_MM) {
				return -2.0;
			} else {
				return parsed;
			}
		}
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
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
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
	 * @return -1.0 if the voltage is below the minimum
	 * @return -2.0 if voltage is above the maximum
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
