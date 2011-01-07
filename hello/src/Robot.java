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
		ImageChannel ic = new ImageChannel("Foto");
		ic.publish(pic);
		
		processImage(pic);
	}

	
	public void processImage(BufferedImage pic){
		int[][] rgb = new int[pic.getNumXTiles()][pic.getNumYTiles()];
		for (int x = pic.getMinTileX(); x < pic.getNumXTiles(); x++){
			for (int y = pic.getMinTileY(); y < pic.getNumYTiles(); y++){
				rgb[x][y] = pic.getRGB(x, y);
				int r = (rgb[x][y] & 0x00FF0000) >> 16;
				int g = (rgb[x][y] & 0x0000FF00) >> 8;
				int b = (rgb[x][y] & 0x000000FF);

			}
		}
	}
	
}
