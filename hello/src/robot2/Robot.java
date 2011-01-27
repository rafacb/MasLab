package robot2;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import maslab.camera.Camera;
import maslab.camera.ImageUtil;
import maslab.telemetry.channel.ImageChannel;
import maslab.telemetry.channel.TextChannel;
import orc.Gyro;
import orc.IRRangeFinder;
import orc.Motor;
import orc.Orc;
import orc.Servo;


public class Robot {

	//Variables to store motors.
	public static Motor[] motors = new Motor[2];
	
	//orc
	static Orc orco = Orc.makeOrc();
	
	//Analog input
	IRRangeFinder irRight = IRRangeFinder.makeGP2D12(orco, 0);
	IRRangeFinder irLeft = IRRangeFinder.makeGP2D12(orco, 7);
	IRRangeFinder irFront = IRRangeFinder.makeGP2D12(orco, 2);
	
	
	//Motors
	static Motor motorR = new Motor(orco, 2, false);
	static Motor motorL = new Motor(orco, 1, true);
	static Servo servo = Servo.makeMPIMX400(orco, 0);
	
	//Gyro
	Gyro gyro = new Gyro(orco, 2);
	
	//Camera
	public Camera cam;
	public int height;
	public int width;
	
	//Position of Ball and goal
	int[] ball_pos = new int[2];
	int[] goal_pos = new int[2];
	int[] wall_pos = new int[2];
	
	//Can you see something good?
	boolean isBall;
	boolean isGoal;
	boolean isWall;
	
	//Has balls?
	boolean hasBall = false;
	
	//Ball Color
	String color;
	
	//Channels
	public TextChannel canal = new TextChannel("The Mighty Patos!");
	
	/**
	 * Constructor of a robot.
	 * Initiates the motors and gives them speed.
	 * @throws IOException 
	 */
	public Robot(String color) throws IOException{
		motors[0] = motorR;
		motors[1] = motorL;
		cam = Camera.makeCamera();
		this.color = color;
	}
	
	/**
	 * Move with speed "speeds".
	 */
	public void move(double[] speeds){
		if (speeds[0] == 1000){
			motorL.idle();
			motorR.idle();
		}
		Motor.setMultiplePWM(motors, speeds);
	}
	
	public void moveServo(double angle){
		if (angle == 1000){
			servo.idle();
		}else{
			servo.setPosition(angle);
		}
	}
	
	/**
	 * Method to display sensor readings.
	 * @return list of doubles representing the reading from sensors.
	 * 
	 */
	public double[] input(){
		double[] irs = new double[3];
		irs[0] = irRight.getRange();
		irs[1] = irLeft.getRange();
		irs[2] = irFront.getRange();
		return irs;
	}
	
	public void image(){
		BufferedImage pic = cam.capture(true);
		
		// Create the ImageTutorial object, and all that that implies
		Image2 it = null; //WTF initialize to null to placate the compiler
		it = new Image2(pic);
		// Do the work
		//it.find_red_blob();
		it.find_objects(color);
		it.renderStatistics(color);
		height = it.height;
		width = it.width;
		
		
		
		//System.out.println("x = "+ it.pos[0] + " y = "+it.pos[1]);
		//Store position.
		ball_pos = it.pos;
		goal_pos[0] = it.x_goal;
		goal_pos[1] = it.y_goal;
		
		//if (it.isWall()){
			//wall_pos = it.pos;
		//}
		
		ImageChannel ic = new ImageChannel("Pato Cam");
		ic.publish(it.im);
		
		isBall = it.isBall();
		isGoal = it.isGoal();
		//isWall = it.isWall();
	}
	
	
	public void image2() throws InterruptedException{
		BufferedImage pic = cam.capture(true);
		
		pic = ImageUtil.scaleImage(pic, 160, 120);
		
		// Create the ImageTutorial object, and all that that implies
		Image2 it = new Image2(pic);
		// Do the work
		//it.find_red_blob();
		it.find_objects(color);
		it.renderStatistics(color);
		height = it.height;
		width = it.width;
		
		
		
		//System.out.println("x = "+ it.pos[0] + " y = "+it.pos[1]);
		//Store position.
		ball_pos = it.pos;
		goal_pos[0] = it.x_goal;
		goal_pos[1] = it.y_goal;
		
		//if (it.isWall()){
			//wall_pos = it.pos;
		//}
		
		ImageChannel ic = new ImageChannel("Pato Cam");
		//ic.publish(it.im);
		ic.publish(pic);
		Thread.sleep(120);
		//isBall = it.isBall();
		//isGoal = it.isGoal();
		//isWall = it.isWall();
	}
	
	public void turn(boolean right){
		double[] speeds = new double[2];
		if (right){
			speeds[0] = 1;
			speeds[1] = -1;
		}else{
			speeds[0] = -1;
			speeds[1] = 1;
		}
		
		move(speeds);
	}
	
	public void randomWalk(){
		double[] speeds = new double[2];
		double[] irs = input();
		//System.out.println("irsD = "+irs[0]);
		//System.out.println("irsIzq = "+irs[1]);
		//Alante
		if (irs[2] < .3){
			canal.publish("Random-Muy Cerca de Frente");
			if (new Random().nextInt(1) == 1){
			    speeds[0] = 0.5;
			    speeds[1] = -0.45;
			    move(speeds);
			}else{
			    speeds[0] = -0.5;
			    speeds[1] = 0.45;
			    move(speeds);
			}
		}else if ((irs[0] >= .3 && irs[1] >= .3) 
				|| (irs[0] == 0.0 && irs[1] == 0.0)
				|| (irs[0] == 0.0 && irs[1] > .3)
				|| (irs[0] > .3 && irs [1] == 0.0)){
			canal.publish("Random-Alante!");
			speeds[0] = 0.5;
			speeds[1] = 0.45;
			//System.out.println("Pa lante");
			//servo.setPosition(Math.PI*.4);
			
			move(speeds);
		}
		//Atras
		else if ((irs[0] < .3 && irs[1] < .3) 
				&& (irs[0] != 0) && (irs[1] != 0)){
			canal.publish("Random-Para atras!");
			speeds[0] = -0.5;
			speeds[1] = -0.45;
			//System.out.println("Back down!");
			//servo.setPosition(Math.PI*.4);
			
			move(speeds);
		}
		//Derecha
		else if ((irs[1] < .3 && irs[1] != 0.0) 
				&& (irs[0] == 0 || irs[0] > .3)){
			canal.publish("Random-Izquierda!");
			speeds[0] = -0.5;
			speeds[1] = 0.45;
			//System.out.println("Pa la izq");
			//servo.setPosition(Math.PI*.4);
			
			move(speeds);
		}
		//Izq
		else if ((irs[0] < .3 && irs[0] != 0.0) 
				&& (irs[1] == 0 || irs[1] > .3)){
			canal.publish("Random-Derecha!");
			speeds[0] = 0.5;
			speeds[1] = -0.45;
			//System.out.println("Pa la derecha");
			//servo.setPosition(Math.PI*.4);
			
			move(speeds);
		}
	}
	
	public void moveTo(int[] pos, boolean ball) throws InterruptedException{
		double irs[] = input();
		if (pos[0] > width/2 + 100) {
			if (irs[0] < .3){
				move(new double[] {.5,.45});
			}else{
				move(new double[] {-.5,.45});
			}//servo.setPosition(Math.PI*.4);
		} else if (pos[0] < width/2 - 100) {
			if (irs[0] < .3){
				move(new double[] {.5,.45});
			}else{
				move(new double[] {.5,-.45});
			}//servo.setPosition(Math.PI*.4);
		} 
		else {
			System.out.println("Cetered!!");
			// System.out.println("Pa lante como el elefante...");
			while(isBall){
				move(new double[] {.5,.45});
				//servo.setPosition(Math.PI*.4);
				image2();
			}servo.setPosition(Math.PI/6);
		}
	}
}
