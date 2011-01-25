package robot2;
import java.io.IOException;

import maslab.telemetry.channel.TextChannel;

public class MoveWithCam {
	// Variables to store speeds of the motors
	static double[] speeds = new double[2];
	static TextChannel canal = new TextChannel("The Mighty Patos!");

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Robot robot = new Robot();
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			
			robot.image();
			if (robot.isBall) {
				canal.publish("Veo Bola!!\n");
				int width = robot.width / 2;
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
				robot.moveTo(robot.ball_pos, true);
				robot.servo.setPosition(Math.PI/6);
				
				
			} else if (robot.hasBall){
				if (robot.isWall){
					canal.publish("Veo la pared!\n");
					robot.moveTo(robot.wall_pos, false);
				}
			}else {
				canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}
	}

}