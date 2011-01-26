package robot2;

import java.io.IOException;

public class DoServoMovement {
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException, InterruptedException{
			
			Robot robot = new Robot();
			
			while (true){
			
				for (int i = 0; i < 5000; i++){
					robot.moveServo(Math.PI/6);
					System.out.println("Abajo!");
				}for (int i = 0; i < 5000; i++){
					robot.moveServo(Math.PI*.4);
					System.out.println("Arriba!");
				}for (int i = 0; i < 5000; i++){
					robot.moveServo(Math.PI/6);
					System.out.println("Abajo!");
				}for (int i = 0; i < 5000; i++){
					robot.moveServo(Math.PI*.4);
					System.out.println("Arriba!");
				}
			
			}
			
	}
	

}
