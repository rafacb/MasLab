import java.awt.image.BufferedImage;
import java.io.IOException;

import maslab.camera.Camera;
import maslab.telemetry.channel.ImageChannel;
import orc.IRRangeFinder;
import orc.Motor;
import orc.Orc;


public class Robot {

	//Variables to store motors.
	public static Motor[] motors = new Motor[2];
	
	//orc
	static Orc orco = Orc.makeOrc();
	
	//Analog input
	IRRangeFinder irFront = IRRangeFinder.makeGP2D12(orco, 1);
	
	//Motors
	static Motor motorR = new Motor(orco, 0, false);
	static Motor motorL = new Motor(orco, 1, true);
	
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
		motors[0] = motorL;
		motors[1] = motorR;
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
	public double input(){
		return irFront.getRange();
	}
	
	public void image(){
		BufferedImage pic = cam.capture();
		
		// Create the ImageTutorial object, and all that that implies
		ImageTutorial it = null; //WTF initialize to null to placate the compiler
		it = new ImageTutorial(pic);
		// Do the work
		it.find_red_blob();
		it.renderStatistics();
		
		
		System.out.println("x = "+ it.pos[0] + " y = "+it.pos[1]);
		//Store position.
		pos = it.pos;
		
		ImageChannel ic = new ImageChannel("Foto");
		ic.publish(it.im);
	}
	
	public void turn(boolean right){
		double[] speeds = new double[2];
		if (right){
			speeds[0] = -.2;
			speeds[1] = .2;
		}else{
			speeds[0] = .2;
			speeds[1] = -.2;
		}
		
		move(speeds);
	}

	
}
