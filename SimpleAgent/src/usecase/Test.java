package usecase;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import robot.NavRobot;
//import data.Position;
import device.Device;
import device.DeviceNode;
import device.Position2d;
import device.external.IDevice;



public class Test {

	private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));
	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	public static void main(String[] args) throws IOException {
		NavRobot pion = null;
		DeviceNode roboClient = null, plannerClient = null;
		try {
			// Init the robot clients
//			plannerClient = new RobotClient("localhost", 6668);
			roboClient = new DeviceNode("localhost", 6667);
			// Start the robot clients
//			plannerClient.runThreaded();
			roboClient.runThreaded();
			((Position2d) roboClient.getDevice(new Device(IDevice.DEVICE_POSITION2D_CODE, null, -1, -1))).setSpeed(0);
			// Create a Device containing all the clients devices
//			Device gripperDevices = new Device( new Device[]{roboClient, plannerClient} );
			// Init the robot with the devices
//			pion = new GripperRobot(gripperDevices);
//			pion.setPosition(new Position(-3,-5,Math.toRadians(90)));
			//			for(int i=0; i<1; i++) {
			//				System.out.println(
			//						pion.getPosition().toString());
			//				Thread.sleep(1000);
			//			}
			// Start the robot
//			pion.runThreaded();
			
			in.readLine();
//			
//			pion.shutdown();
			roboClient.shutdown();
//			plannerClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
