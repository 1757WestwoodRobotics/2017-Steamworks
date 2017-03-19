package com.team1757.utils;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

public class MaxbotixUltrasonicSerial {
	
	private SerialPort serial;
	
	// Bounds for noise filtering (mm)
	private final double BOUND_UPPER = 5000;
	private final double BOUND_LOWER = 30;

	public MaxbotixUltrasonicSerial() {
		serial = new SerialPort(9600, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
		// http://www.maxbotix.com/documents/HRLV-MaxSonar-EZ_Datasheet.pdf
		// Serial data sent is 9600 baud, with 8 data bits, no parity, and one
		// stop bit
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
     * @return double measured range (mm) in range [TODO COME BACK TO MEEEE]
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    private double getRangeMM() {
    	String response = serial.readString();
    	
    	if (response.isEmpty() || response == null) {
    		return -1.0;
    	}
    	else {
    		double parsed = Double.parseDouble(response.substring(1));
    		if (parsed >= BOUND_UPPER) {
    			return -2.0;
    		}
    		else if (parsed <= BOUND_LOWER) {
    			return -1.0;
    		}
    		else {
    			return parsed;
    		}
    	}
    }
    
	/**
     * getRange
     * 
     * @return double measured range (unit) in range [TODO COME BACK TO MEEEE]
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    public double getRange(Unit unit) {
    	return unit.get(getRangeMM());
    }
    
    public String getRangeDebug() {
    	String response = serial.readString();
    	
    	if (response.isEmpty() || response == null) {
    		return "";
    	}
    	else {
    		return response;
    	}
    }
}
