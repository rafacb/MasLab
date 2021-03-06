package robot2;
import java.io.IOException;

public class MoveWithCam {
	// Variables to store speeds of the motors
	static double[] speeds = new double[2];
	

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Robot robot = new Robot("red");
		double[] start = robot.input();
		while (true){
			System.out.println("Start = "+start[0]);
			if (start[0] > .5 || start[0] == 0.0){
				start = robot.input();
				System.out.println("Start = "+start[0]);
				Thread.sleep(100);
			}else{
				System.out.println("Start = "+start[0]);
				System.out.println("here!!");
				break;
			}
		}
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			
			robot.image2();
			if (robot.isBall) {
				robot.canal.publish("Veo Bola!!\n");
				//int width = robot.width / 2;
				// If the ball is seen in the right side of the picture, then
				// turn right
				// Do the opposite if it's seen on the left side.
				
				/**if (robot.ball_pos[0] > width + 100) {
					robot.move(new double[] {-.4,.4});
				} else if (robot.ball_pos[0] < width - 100) {
					robot.move(new double[] {.4,-.4});
				} 
				else {
					System.out.println("Cetered!!");
					// System.out.println("Pa lante como el elefante...");
					robot.move(new double[] {.7,.7});
				}**/
				robot.moveTo(robot.ball_pos);
				robot.servo.setPosition(Math.PI/6);
				robot.servo.setPosition(Math.PI*.4);
				
				
			} else if (robot.hasBall){
				if (robot.isWall){
					robot.canal.publish("Veo la pared!\n");
					//robot.moveTo(robot.wall_pos, false);
				}
			}else {
				robot.canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}
		robot.motorL.idle();
		robot.motorR.idle();
	}

}