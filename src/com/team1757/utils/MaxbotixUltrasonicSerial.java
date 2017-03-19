package com.team1757.utils;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.StopBits;

public class MaxbotixUltrasonicSerial {

	private final double MM_TO_IN = 0.0394;
	private final double MM_TO_CM = 0.100;
	
	private SerialPort serial;

	public MaxbotixUltrasonicSerial() {

		serial = new SerialPort(9600, Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
		// http://www.maxbotix.com/documents/HRLV-MaxSonar-EZ_Datasheet.pdf
		// Serial data sent is 9600 baud, with 8 data bits, no parity, and one
		// stop bit
		serial.enableTermination((char) 0x13);
	}

	
	/**
     * getRangeMM
     * 
     * The output is an ASCII capital “R”, followed by four ASCII character
	 * digits representing the range in millimeters, followed by a carriage
	 * return (ASCII 13). The maximum distance reported is 5000. The serial
	 * output is the most accurate of the range outputs
     * 
     * @return double measured range (mm) in range [TODO COME BACK TO MEEEE]
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    public double getRangeMM() {
    	return Double.parseDouble(serial.readString().substring(1));
    }
	
    /**
     * getRangeInches
     * 
     * @return double measured range (inches) in range [3.0, 60.0]
     * @return -1.0 if the voltage is below the minimum
     */
    public double getRangeInches() {
    	return getRangeMM() * MM_TO_IN;
    }
    
    /**
     * getRangeCM
     * 
     * @return double measured range (inches) in range [7.62, 152.4]
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    public double getRangeCM() {
        return getRangeMM() * MM_TO_CM;
    }
}
