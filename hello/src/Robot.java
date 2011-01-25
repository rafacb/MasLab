import java.awt.image.BufferedImage;
import java.io.IOException;

import maslab.camera.Camera;
import maslab.telemetry.channel.ImageChannel;
import orc.IRRangeFinder;
import orc.Motor;
import orc.Orc;


public class Robot {

	//Variables to store motors.
	public static Motor[] motors = new Motor[3];
	
	//orc
	static Orc orco = Orc.makeOrc();
	
	//Analog input
	IRRangeFinder irRight = IRRangeFinder.makeGP2D12(orco, 7);
	IRRangeFinder irLeft = IRRangeFinder.makeGP2D12(orco, 0);
	
	
	//Motors
	static Motor motorR = new Motor(orco, 0, false);
	static Motor motorL = new Motor(orco, 1, true);
	static Motor drib = new Motor(orco, 2, true);
	
	//Camera
	public Camera cam;
	public int height;
	public int width;
	
	//Position of Ball and goal
	int[] ball_pos = new int[2];
	int[] goal_pos = new int[2];
	
	//Can you see something good?
	boolean isBall;
	boolean isGoal;
	
	
	/**
	 * Constructor of a robot.
	 * Initiates the motors and gives them speed.
	 * @throws IOException 
	 */
	public Robot() throws IOException{
		motors[0] = motorR;
		motors[1] = motorL;
		motors[2] = drib;
		cam = Camera.makeCamera();
	}
	
	/**
	 * Move with speed "speeds".
	 */
	public void move(double[] speeds){
		Motor.setMultiplePWM(motors, speeds);
	}
	
	/**
	 * Method to display sensor readings.
	 * @return list of doubles representing the reading from sensors.
	 * 
	 */
	public double[] input(){
		double[] irs = new double[2];
		irs[0] = irRight.getRange();
		irs[1] = irLeft.getRange();
		return irs;
	}
	
	public void image(){
		BufferedImage pic = cam.capture(true);
		
		// Create the ImageTutorial object, and all that that implies
		Image2 it = null; //WTF initialize to null to placate the compiler
		it = new Image2(pic);
		// Do the work
		//it.find_red_blob();
		it.find_objects("red");
		it.renderStatistics("red");
		height = it.height;
		width = it.width;
		
		
		
		//System.out.println("x = "+ it.pos[0] + " y = "+it.pos[1]);
		//Store position.
		ball_pos = it.pos;
		goal_pos[0] = it.x_goal;
		goal_pos[1] = it.y_goal;
		
		ImageChannel ic = new ImageChannel("Foto");
		ic.publish(it.im);
		
		isBall = it.isBall();
		isGoal = it.isGoal();
	}
	
	public void turn(boolean right){
		double[] speeds = new double[3];
		if (right){
			speeds[0] = 1;
			speeds[1] = -1;
			speeds[2] = 1;
		}else{
			speeds[0] = -1;
			speeds[1] = 1;
			speeds[2] = 1;
		}
		
		move(speeds);
	}
	
	public void randomWalk(){
		double[] speeds = new double[3];
		double[] irs = input();
		//System.out.println("irsD = "+irs[0]);
		//System.out.println("irsIzq = "+irs[1]);
		//Alante
		if ((irs[0] >= .3 && irs[1] >= .3) 
				|| (irs[0] == 0.0 && irs[1] == 0.0)
				|| (irs[0] == 0.0 && irs[1] > .3)
				|| (irs[0] > .3 && irs [1] == 0.0)){
			speeds[0] = 0.7;
			speeds[1] = 0.7;
			speeds[2] = 1;
			//System.out.println("Pa lante");
			
			move(speeds);
		}
		//Atras
		else if ((irs[0] < .3 && irs[1] < .3) 
				&& (irs[0] != 0) && (irs[1] != 0)){
			speeds[0] = -0.7;
			speeds[1] = -0.7;
			speeds[2] = 1;
			//System.out.println("Back down!");
			
			move(speeds);
		}
		//Derecha
		else if ((irs[1] < .3 && irs[1] != 0.0) 
				&& (irs[0] == 0 || irs[0] > .3)){
			speeds[0] = -0.7;
			speeds[1] = 0.7;
			speeds[2] = 1;
			//System.out.println("Pa la izq");
			
			move(speeds);
		}
		//Izq
		else if ((irs[0] < .3 && irs[0] != 0.0) 
				&& (irs[1] == 0 || irs[1] > .3)){
			speeds[0] = 0.7;
			speeds[1] = -0.7;
			speeds[2] = 1;
			//System.out.println("Pa la derecha");
			
			move(speeds);
		}
		/**if (irs[0] > .3 || irs[0] == 0.0){
			speeds[0] = 0.5;
			speeds[1] = 0.5;
			speeds[2] = 1;
			System.out.println("Pa lante");
			
			move(speeds);
		}else if (irs[0] <= .3){
			speeds[0] = 0.5;
			speeds[1] = -0.5;
			speeds[2] = 1;
			System.out.println("Pa la izq");
			
			move(speeds);
		}**/
	}

	
}
