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
	IRRangeFinder irRight = IRRangeFinder.makeGP2D12(orco, 0);
	IRRangeFinder irLeft = IRRangeFinder.makeGP2D12(orco, 1);
	
	
	//Motors
	static Motor motorR = new Motor(orco, 0, false);
	static Motor motorL = new Motor(orco, 1, true);
	static Motor drib = new Motor(orco, 2, true);
	
	//Camera
	public Camera cam;
	
	//Position of redBall
	int[] pos = new int[2];
	
	
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
		Image it = null; //WTF initialize to null to placate the compiler
		it = new Image(pic);
		// Do the work
		it.find_red_blob();
		it.renderStatistics();
		it.whereIsBall();
		
		
		System.out.println("x = "+ it.pos[0] + " y = "+it.pos[1]);
		//Store position.
		pos = it.pos;
		
		ImageChannel ic = new ImageChannel("Foto");
		ic.publish(it.im);
	}
	
	public void turn(boolean right){
		double[] speeds = new double[3];
		if (right){
			speeds[0] = -.2;
			speeds[1] = .2;
			speeds[2] = .8;
		}else{
			speeds[0] = .2;
			speeds[1] = -.2;
			speeds[2] = .8;
		}
		
		move(speeds);
	}
	
	public void randomWalk(){
		double[] speeds = new double[2];
		double[] irs = input();
		if ((irs[0] > 2 && irs[1] > 2) || (irs[0] == 0 && irs[0] == 0)){
			speeds[0] = 0.4;
			speeds[1] = 0.4;
			speeds[2] = .8;
			
			move(speeds);
		}else if (irs[0] < 2){
			speeds[0] = 0.4;
			speeds[1] = -0.4;
			speeds[2] = .8;
			
			move(speeds);
		}else if (irs[1] < 2){
			speeds[0] = -0.4;
			speeds[1] = 0.4;
			speeds[2] = .8;
			
			move(speeds);
		}else{
			speeds[0] = -0.3;
			speeds[1] = -0.4;
			speeds[2] = .8;
			
			move(speeds);
		}
	}

	
}