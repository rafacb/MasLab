import java.io.IOException;

public class MoveForward {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException{
		
		Robot robot = new Robot();
		
		while (true){
			
			robot.randomWalk();
			
		}
	}

}
