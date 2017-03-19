package com.team1757.utils;

import edu.wpi.first.wpilibj.AnalogInput;


/**
 * Implementation of Maxbotix MB1013 Ultrasonic rangefinder
 * 
 * Provides voltage scaling to report range in mm, cm, or inches
 * 
 * See {@link http://www.maxbotix.com/ultrasonic-sensor-hrlv%E2%80%91maxsonar%E2%80%91ez-guide-158.htm} for details
 * 
 * @author ACabey
 */
public class MaxbotixUltrasonicAnalog extends AnalogInput {
	
	// TODO Verify true maximum and minumum accurate distance reported
    private final double DISTANCE_MIN_MM = 300.0;						// Advertised minumum distance (mm)
    private final double DISTANCE_MAX_MM = 5000.0;						// Advertised max distance is (mm)
    
//    private final double DISTANCE_MIN = DISTANCE_MIN_MM*MM_TO_IN;		// Minumum accurate distance (inches)
//    private final double DISTANCE_MAX = DISTANCE_MAX_MM*MM_TO_IN;		// Maximum accurate distance (inches)
    
    private final double VOLTAGE_IN = 5.0;								// Supplied voltage
    private final double VOLTAGE_SCALE = VOLTAGE_IN/1024.0;				// Volts per 5mm (scaling factor)
    
    
    // (Range x VoltsPer5mm)/5 = VoltageMeasured
    private final double VOLTAGE_MIN = DISTANCE_MIN_MM * VOLTAGE_SCALE / 5.0;
    private final double VOLTAGE_MAX = DISTANCE_MAX_MM * VOLTAGE_SCALE / 5.0;
    
	public MaxbotixUltrasonicAnalog(int channel) {
		super(channel);
	}
    
    /**
     * getRangeMM
     * 
     * [(Vcc/1024) = Vi]
     * Vcc = Supplied Voltage
     * Vi = Volts per 5 mm (Scaling) 
     * 
     * [5*(Vm/Vi) = Ri]
     * Vm = Measured Voltage
     * Vi = Volts per 5 mm (Scaling)
     * Ri = Range in mm
     * 
     * @return double measured range (mm) in range [TODO COME BACK TO MEEEE]
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    private double getRangeMM() {
    	double voltageMeasured = getVoltage();
    	if (voltageMeasured < VOLTAGE_MIN) {
    		return -1.0;
    	}
    	else if (voltageMeasured > VOLTAGE_MAX) {
    		return -2.0;
    	}
    	else {
    		return 5.0 * voltageMeasured/VOLTAGE_SCALE;
    	}
    }
	
    /**
     * getRangeInches
     * 
     * @return double measured range (unit)
     * @return -1.0 if the voltage is below the minimum
     * @return -2.0 if voltage is above the maximum
     */
    public double getRange(Unit unit) {
    	return unit.get(getRangeMM());
    }
}
