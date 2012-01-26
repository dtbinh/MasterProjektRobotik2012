/// @file wallfollowing.cpp
/// @author Sebastian Rockel
///
/// @mainpage Robotic Project 2009
///
/// @par Copyright
/// Copyright (C) 2009 Sebastian Rockel.
/// This program can be distributed and modified under the condition mentioning
/// the @ref author.
///
/// @par Description
/// Wall following example for Pioneer 2DX robot.
/// This is part of the robotics students project at Uni Hamburg in 2009.
/// The task was to implement sensor fusion (besides laser and sonar also
/// omnidirectional camera) to find and track a (pink) ball or when idle do
/// exploring floor and rooms (via wall following) always avoiding collisions
/// with static or dynamic obstacles.
///
/// @par Sensor fusion
/// The program depends on sensor fusion of 16 sonar rangers and a 240 degree
/// urg laser ranger (as well as an omni directional camera when enabled, see
/// makefile).
///
/// @par Source code
/// @code git clone git://github.com/buzzer/Pioneer-Project-2009.git @endcode
///
/// (Scalable) Vector Graphic formatted figures :
/// @image html PioneerShape.svg "Overview about how laser and sonar sensors are fused"
/// @image html AngleDefinition.svg "Calculating turning angle via the atan function"
/// @image latex PioneerShape.pdf "Overview about how laser and sonar sensors are fused" width=\textwidth
/// @image latex AngleDefinition.pdf "Calculating turning angle via the atan function" width=0.8\textwidth
/// Same graphics in Portable Network Graphic format (html only):
/// @image html PioneerShape.png "Overview about how laser and sonar sensors are fused"
/// @image html AngleDefinition.png "Calculating turning angle via the atan function"
package usecase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import robot.Pioneer;

import device.DeviceNode;

public class Wallfollow {

	private static BufferedReader in = new BufferedReader(
          new InputStreamReader(System.in));

	@SuppressWarnings("deprecation")
	public static void main (String[] args) {
		try {
			DeviceNode roboClient = new DeviceNode("localhost", 6667);
			roboClient.runThreaded();
			
			Pioneer pion = new Pioneer(roboClient);
			pion.runThreaded();
			
			// Wait until enter is pressed
			in.readLine();
			
			pion.shutdown();
			roboClient.shutdown();
			
		} catch (Exception e) { e.printStackTrace(); }
	}
}
