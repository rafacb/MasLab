package robot2;

import java.io.IOException;

public class CenterBall {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
	    Robot robot = new Robot("red");
		while (true){
			int width = robot.width/2;
			robot.image2();
			
			if (robot.isBall){
				int[] pos = robot.ball_pos;
				if (pos[0] > width + 50) {
				    robot.canal.publish("bola a la der");
					robot.move(new double[] {-Double.valueOf(args[0]),Double.valueOf(args[1])});
					//robot.move(new double[] {1000,0});
					Thread.sleep(100);
				} else if (pos[0] < width - 50) {
				    robot.canal.publish("bola a la izq");
					robot.move(new double[] {Double.valueOf(args[0]),-Double.valueOf(args[1])});
					//robot.move(new double[] {1000,0});
					Thread.sleep(100);
				} 			
			}Thread.sleep(100);
		}
		
	}

}
