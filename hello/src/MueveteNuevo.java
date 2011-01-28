import java.io.IOException;

import maslab.telemetry.channel.TextChannel;

public class MueveteNuevo {
	// Variables to store speeds of the motors
	static double[] speeds = new double[2];
	static TextChannel canal = new TextChannel("The Mighty Patos!");
	
	public static void hold(Robot robot) throws InterruptedException{
		while (true){
			System.out.println(robot.input()[1]);
			if (robot.input()[0] > .3 || robot.input()[0] == 0.0){
				Thread.sleep(100);
			}else{
				System.out.println("Start!!");
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Robot robot = new Robot("green");
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;
		int width = robot.width / 2;
		
		hold(robot);
		
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds-40000){
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
				
				// If the ball is seen in the right side of the picture, then
				// turn right
				// Do the opposite if it's seen on the left side.
				//System.out.println("pos = "+robot.ball_pos[0] + " w = "+(width-100)+" w = "+(width+100));
				
				if (robot.ball_pos[0] > width + 100) {
					//System.out.println("Veo bola a la derecha, mueve izq");
					//robot.turn(true);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {.5,.4,1});
						Thread.sleep(80);
					//}
				} else if (robot.ball_pos[0] < width - 100) {
					//System.out.println("Veo bola a la izq, mueve derecha");
					//robot.turn(false);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {.4,.5,1});
						Thread.sleep(80);
					//}
				} 
				else {
					System.out.println("Cetered!!");
					// System.out.println("Pa lante como el elefante...");
					robot.move(new double[] {.7,.7,1});
				}
				
				
				}/** else if (robot.isGoal){
				canal.publish("Veo Goal!!\n");
				while (robot.input()[0] > .5){
					System.out.println();
					robot.move(new double[] {.7,.7,-1});
				}for (int i = 0; i < 10; i++){
					robot.move(new double[] {-1,-1,-1});
				}for (int i = 0; i < 3; i++){
					robot.randomWalk();
				}
			}**/else {
				canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}while(System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			canal.publish("Veo Goal!!\n");
			robot.image();
			boolean sawGoal = false;
			while (robot.isGoal){
				sawGoal = true;
				if (robot.goal_pos[0] > width + 100) {
					//System.out.println("Veo bola a la derecha, mueve izq");
					//robot.turn(true);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {.4,.6,1});
						Thread.sleep(80);
					//}
				} else if (robot.goal_pos[0] < width - 100) {
					//System.out.println("Veo bola a la izq, mueve derecha");
					//robot.turn(false);
					//for (int i = 0; i < 10; i++){
						robot.move(new double[] {.6,.4,1});
						Thread.sleep(80);
					//}
				}else{
					while (robot.input()[2] > .5){
						System.out.println();
						robot.move(new double[] {.7,.7,-1});
					}break;
				}robot.image();
			}
			if (sawGoal){
				break;
			}else{
				canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}
	}

}