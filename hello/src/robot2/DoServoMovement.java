package robot2;

import java.io.IOException;

public class DoServoMovement {
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException{
			
			Robot robot = new Robot();
			
			int i = 0;
			
			while (true){
			
				robot.servo.setPosition(Math.PI*.4);
				
				if (i%1500 == 0){
					System.out.println("NoW!");
					robot.servo.setPosition(Math.PI*.18);
				}i++;
			//robot.gyro.reset();
			
			//System.out.println(robot.gyro.getTheta());
			
			}
			//long startTime = System.currentTimeMillis();
			//long maxDurationInMilliseconds =3*60*1000;
	
			
			//while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
				//
			
	}
	

}
