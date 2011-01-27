package robot2;

import java.io.IOException;

public class RearSensor {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		Robot robot = new Robot("red");
		
		while (true){
			System.out.println(robot.input()[3]);
		}
	}

}
