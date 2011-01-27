package robot2;

import java.io.IOException;

public class CenterBall {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		
		Robot robot = new Robot("red");
		int width = robot.width/2;
		robot.image2();
		
		if (robot.isBall){
			robot.center(robot.ball_pos, width);			
		}
		
	}

}
