package robot2;

import java.io.IOException;

public class GyroTest {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException, InterruptedException{
			
			Robot robot = new Robot("red");
			
			while(true){
			
			while (robot.rearBump.getVoltage() > .1){
				Thread.sleep(100);
			}
			System.out.println("aquiii");
			
			/**
			while (gyro < .35  && gyro > -.35){
				
				robot.move(new double[] {.5,.3});
				gyro = robot.gyro.getTheta();
				System.out.println("gyro = "+gyro);
				//robot.move(new double [] {1000,100});
			}System.out.println("No te muevas!");
			//robot.move(new double [] {1000,100});**/
			
			for (int i=0; i<Integer.valueOf(args[2]);i++){
			    robot.move(new double[] {Double.valueOf(args[0]),Double.valueOf(args[1])});
			}
			}

			
	}

}
