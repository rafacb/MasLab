import java.io.IOException;

public class MoveWithCam {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static void main(String[] args) throws IOException{
		
		Robot robot = new Robot();
		
		int count = 0;
		
		while (true){
			/**System.out.println(robot.input());
			if (robot.input() > 1.8 || robot.input() == 0){
				speeds[0] = 0.4;
				speeds[1] = 0.4;
				
				robot.move(speeds);
			}else{
				speeds[0] = 0.4;
				speeds[1] = -0.4;
				
				robot.move(speeds);
			}**/
			if (count%20 == 0){
				robot.image();
			}
			count++;
			
		}
	}

}
