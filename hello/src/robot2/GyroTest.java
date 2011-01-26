package robot2;

import java.io.IOException;

public class GyroTest {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException, InterruptedException{
			
			Robot robot = new Robot("red");
			System.out.println(robot.gyro.getTheta());
			double gyro = 0;
			
			while (gyro < 180){
				
				robot.move(new double[] {-.2,.2});
				gyro = robot.gyro.getTheta();
				System.out.println("gyro = "+gyro);
			
				
			
			}
			
	}

}
