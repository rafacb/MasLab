package robot2;

import java.io.IOException;

public class GoToBall {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot robot = new Robot("red");
		
		while (true){
			robot.image2();
			System.out.println("sensor delantero = "+robot.input()[2]);
			System.out.println(robot.isBall);
			System.out.println("w = "+robot.width);
			System.out.println("position = "+robot.ball_pos[0]);
			
			if (robot.isBall) {
				robot.canal.publish("Veo Bola!!\n");
				//int width = robot.width / 2;
				// If the ball is seen in the right side of the picture, then
				// turn right
				// Do the opposite if it's seen on the left side.
				
				/**if (robot.ball_pos[0] > width + 100) {
					robot.move(new double[] {-.4,.4,1});
				} else if (robot.ball_pos[0] < width - 100) {
					robot.move(new double[] {.4,-.4,1});
				} 
				else {
					System.out.println("Cetered!!");
					// System.out.println("Pa lante como el elefante...");
					robot.move(new double[] {.7,.7,1});
				}**/
				robot.moveTo(robot.ball_pos);
				for (int i = 0; i < 5000; i++){
					robot.moveServo(-Math.PI/8);
					System.out.println("Abajo!");
				}for (int i = 0; i < 5000; i++){
					robot.moveServo(Math.PI*.75);
					System.out.println("Abajo!");
				}robot.moveServo(1000);
			}
		}
		
	}
}
