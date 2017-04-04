package com.team1757.utils;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A PID source for a single vision target using the center of the target
 * 
 * @author Ryan Marten
 *
 */
public class VisionSourcePlayerStation implements PIDSource {
	
	

	private PIDSourceType pidSourceType = PIDSourceType.kDisplacement;
	private double offset;

	public VisionSourcePlayerStation() {
		this.offset = 0;
	}
	
	public VisionSourcePlayerStation(double offset){
		this.offset = offset;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return pidSourceType;
	}


	@Override
	public double pidGet() {
		//TODO: Write a method in Vision to get a center of a player station	
		return 0 + offset;
	}

}
