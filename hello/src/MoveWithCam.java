import java.io.IOException;

import maslab.telemetry.channel.TextChannel;

public class MoveWithCam {
	// Variables to store speeds of the motors
	static double[] speeds = new double[2];
	static TextChannel canal = new TextChannel("The Mighty Patos!");

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Robot robot = new Robot();
		int count = 0;
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			/**
			 * System.out.println(robot.input()); if (robot.input() > 1.8 ||
			 * robot.input() == 0){ speeds[0] = 0.4; speeds[1] = 0.4;
			 * robot.move(speeds); }else{ speeds[0] = 0.4; speeds[1] = -0.4;
			 * robot.move(speeds); }
			 **/
			
			// if (count%20 == 0){
			
			robot.image();
			if (robot.isBall) {
				canal.publish("Veo Bola!!\n");
				int width = robot.width / 2;
				// If the ball is seen in the right side of the picture, then
				// turn right
				// Do the opposite if it's seen on the left side.
				//System.out.println("pos = "+robot.ball_pos[0] + " w = "+(width-100)+" w = "+(width+100));
				
				if (robot.ball_pos[0] > width + 100) {
					//System.out.println("Veo bola a la derecha, mueve izq");
					//robot.turn(true);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {-.5,.5,1});
						Thread.sleep(80);
					//}
				} else if (robot.ball_pos[0] < width - 100) {
					//System.out.println("Veo bola a la izq, mueve derecha");
					//robot.turn(false);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {.5,-.5,1});
						Thread.sleep(80);
					//}
				} 
				else {
					System.out.println("Cetered!!");
					// System.out.println("Pa lante como el elefante...");
					robot.move(new double[] {.7,.7,1});
				}
				
				
				} else if (robot.isGoal){
				canal.publish("Veo Goal!!\n");
				for (int i = 0; i < 5; i++){
					robot.move(new double[] {.7,.7,-1});
				}for (int i = 0; i < 5; i++){
					robot.move(new double[] {-1,-1,-1});
				}for (int i = 0; i < 3; i++){
					robot.randomWalk();
				}
			}else {
				canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}
	}

}