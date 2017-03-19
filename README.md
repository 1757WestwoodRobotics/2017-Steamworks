# 2017-Steamworks

Team 1757 - Wolverines' robot code for Steamworks. Main robot code is written in Java based off of WPILib's command-based control system. 
Onboard Arduino code is written in Arduino C.

The code is divided into several packages based off of the command-based structure. 
All required library blobs are included in the lib/ directory.

## Robot Features
 - Mecanum drivetrain
 - Fuel collector
 - Fuel shooter
 - Gear loader (active)
 - Rope lifter
 - Onboard arduino for LED control

 - Sensors
  - NavX-MXP (gyroscope, accelerometer)
  - Lifecam 3000 (vision source)
  - Maxbotix MB1013 (ultrasonic rangefinder)
 
 - Vision Processing
  - Sourced from 2x Lifecam 3000 cameras
  - Processed through GRIP running on DriverStation PC
  - Closed-loop translation and rotation control for perfect alignment with gear and boiler targets 

## Packages
 - Command-based
 - robot 
  - 'administrative' driver classes
 - subsystems
  - High-level representations of physical hardware systems
  - Interface between highest-level Commands and lowest-level hardware
 - Commands
  - Represent in-game actions at the highest level of abstraction
 - Utils
  - Any utility classes for PID control or improving hardware functionality

 For a more complete breakdown of the code structure, see [blog post](https://acabey.xyz/blog/posts/3-9-17/)

## Naming Conventions

 - CONSTANT_EXAMPLE (i.e. DISTANCE_MAX_MM)	: Final constants
 - camelCase (i.e. defaultUnit)				: Instance variables
 - kInstanceName (i.e. kInches)				: enum instances