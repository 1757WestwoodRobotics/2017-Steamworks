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
 
## More Info

We are a veteran FRC team from Westwood High School in Westwood, Massachusetts.
Feel free to check us out on [Twitter](https://twitter.com/WWRobotics1757), [Snapchat](https://www.snapchat.com/add/wwrobotics), or at our [website](team1757.com)!

![Robot](https://pbs.twimg.com/media/C5Plwg-VUAAwj8x.jpg:large)
![Robot](https://pbs.twimg.com/media/C6uKH6jWoAE3xXp.jpg:large)
=======
Team 1757 - Wolverines' robot code for the 2017 <i>FIRST</i> STEAMWORKS game challenge. Main robot code is written in Java based off of WPILib's command-based control system. Onboard Arduino code is written in Arduino C.

The code is divided into several packages based off of the command-based structure. 
All required library blobs are included in the `lib/` directory.

Compiled javadocs are in the `doc/` directory. Open the `doc/index.html` file to view it.

## Robot Features
 - Mecanum drivetrain
 - Fuel collector
 - Fuel shooter
 - Gear scorer (active)
 - Rope climber
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
 - robot 
   - 'administrative' driver classes
 - subsystems
   - High-level representations of physical hardware systems
   - Interface between highest-level Commands and lowest-level hardware
 - commands
   - Represent in-game actions at the highest level of abstraction
 - utils
   - Any utility classes for PID control or improving hardware functionality

 For a more complete breakdown of the code structure, see [blog post](https://acabey.xyz/blog/posts/3-9-17/).

## Naming Conventions
 - `CONSTANT_EXAMPLE` (i.e. `DISTANCE_MAX_MM`)	: Final constants
 - `camelCase` (i.e. `defaultUnit`)				: Instance variables
 - `kInstanceName` (i.e. `kInches`)				: enum instances
 
## About Us
We are a veteran <i>FIRST</i> Robotics Competition team from Westwood High School in Westwood, Massachusetts.
Feel free to check us out on [Twitter](https://twitter.com/WWRobotics1757), [Snapchat](https://www.snapchat.com/add/wwrobotics), or at our [website](http://team1757.com)!

![Robot](https://pbs.twimg.com/media/C5Plwg-VUAAwj8x.jpg:large)
![Robot](https://pbs.twimg.com/media/C6uKH6jWoAE3xXp.jpg:large)
