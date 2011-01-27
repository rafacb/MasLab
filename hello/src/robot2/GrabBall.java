package robot2;

import java.io.IOException;

public class GrabBall {
	
		private static boolean sawBall = false;
		
		public static void main(String[] args) throws IOException,
		InterruptedException {
			Robot robot = new Robot("red");
			
			while (true){
				robot.image2();
				System.out.println("sensor delantero = "+robot.input()[2]);
				System.out.println(robot.isBall);
				System.out.println("w = "+robot.width);
				System.out.println("position = "+robot.ball_pos[0]);
				
				while (robot.isBall) {
					sawBall = true;
					robot.canal.publish("Veo Bola!!\n");
					int width = robot.width / 2;
					// If the ball is seen in the right side of the picture, then
					// turn right
					// Do the opposite if it's seen on the left side.

					if (robot.ball_pos[0] > width + 50) {
						robot.move(new double[] {-.3,.25});
						Thread.sleep(80);
					} else if (robot.ball_pos[0] < width - 50) {
						robot.move(new double[] {.3,-.25});
						Thread.sleep(80);
					} 
					else {
						System.out.println("Cetered!!");
						// System.out.println("Pa lante como el elefante...");
						robot.move(new double[] {.4,.35});
						Thread.sleep(80);
					}robot.image2();
				}
				if (sawBall){
					Thread.sleep(80);
					//After you can't see it, you have it so move the servo
					for (int i = 0; i < 5000; i++){
						robot.moveServo(-Math.PI/8);
						System.out.println("Abajo!");
					}for (int i = 0; i < 5000; i++){
						robot.moveServo(Math.PI*.75);
						System.out.println("Abajo!");
					}for (int i = 0; i < 5000; i++){
						robot.moveServo(Math.PI*.4);
						System.out.println("Arriba!");
					}robot.moveServo(1000);	
					sawBall = false;
				}
			}
		}


}
