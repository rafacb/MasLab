package robot2;

import java.io.IOException;

public class GyroTest {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException, InterruptedException{
			
			Robot robot = new Robot("red");
			robot.gyro.calibrate(1);
			robot.gyro.reset();
			System.out.println(robot.gyro.getTheta());
			double gyro = 0;
			
			
			while (gyro < .368  && gyro > -.368){
				
				robot.move(new double[] {.2,.4});
				gyro = robot.gyro.getTheta();
				System.out.println("gyro = "+gyro);
				//robot.move(new double [] {1000,100});
			}System.out.println("No te muevas!");
			//robot.move(new double [] {1000,100});
			
	}

}
