public class MoveForward {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static void main(String[] args){
		
		speeds[0] = 0.2;
		speeds[1] = 0.2;
		
		Robot robot = new Robot();
		
		while (true){
			robot.move(speeds);
		}
	}

}
