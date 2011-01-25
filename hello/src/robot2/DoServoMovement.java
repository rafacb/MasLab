package robot2;

import java.io.IOException;

public class DoServoMovement {
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException{
			
			Robot robot = new Robot();
			
			while (true){
			
			robot.moveServo(false);
			}
			//long startTime = System.currentTimeMillis();
			//long maxDurationInMilliseconds =3*60*1000;
	
			
			//while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
				//
			
	}
	

}
