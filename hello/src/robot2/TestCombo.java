package robot2;

import java.io.IOException;

public class TestCombo {
	
	public static void grabBall(Robot robot) throws InterruptedException{
		boolean sawBall = false;
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

				if (robot.ball_pos[0] > width + 55) {
					robot.move(new double[] {.35,.4});
					Thread.sleep(80);
				} else if (robot.ball_pos[0] < width - 55) {
					robot.move(new double[] {.4,.35});
					Thread.sleep(80);
				}else {
					System.out.println("Cetered!!");
					// System.out.println("Pa lante como el elefante...");
					for (int i =0; i<1000; i++){
					    robot.move(new double[] {.8,.8});
					}
					Thread.sleep(80);
				}
				robot.image2();
				Thread.sleep(80);
			}
			if (sawBall && !robot.isBall){
				Thread.sleep(80);
				//After you can't see it, you have it so move the servo
				for (int i = 0; i < 1500; i++){
					robot.moveServo(-Math.PI);
					System.out.println("Abajo!");
				}for (int i = 0; i < 200; i++){
					robot.moveServo(Math.PI*.4);
					System.out.println("Normal!");
				}robot.moveServo(1000);	
				sawBall = false;
			}break;
		}
	}
	
	public static Object wall(Robot robot) throws InterruptedException{
		if (robot.isWall){
			while (robot.input()[2] > .2 && robot.input()[2] != 0.0){
				robot.move(new double[] {.5,.5});
			}for (int i=0; i<500;i++){
			    double speed = .65;
			    robot.move(new double[] {speed,-speed});
			}
			Thread.sleep(80);
			robot.move(new double[] {1000,0});
			while ((robot.input()[3] > .17 && robot.input()[3] != 0.0) && robot.rearBump.getVoltage() > .2){
			    System.out.println(robot.input()[3]);
			    robot.move(new double[] {-.5, -.5});
			}System.out.println(robot.input()[3]);
			//Servo shit!
			for (int i = 0; i < 3000; i++){
				robot.moveServo(Math.PI*.75);
				
			}for (int i = 0; i < 500; i++){
				robot.moveServo(Math.PI*.4);
			       
			}for (int i = 0; i < 500; i++){
			    robot.moveServo(1000);
			}
		}robot.image2();
		return null;
	}
	
	public static void hold(Robot robot) throws InterruptedException{
		while (true){
			System.out.println(robot.rearBump.getVoltage());
			if (robot.rearBump.getVoltage() > .2){
				Thread.sleep(100);
			}else{
				System.out.println("Start!!");
				break;
			}
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		Robot robot = new Robot("red");
		
		hold(robot);
		
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
				grabBall(robot);
				System.out.println("Tengo BOLAS!");
				robot.hasBall = true;
				
			} else if (robot.hasBall && robot.isWall){
				robot.canal.publish("Veo la pared!\n");
				wall(robot);
				robot.hasBall = false;
			}else {
				robot.canal.publish("No Veo Nada!! Random Walk!!\n");
				System.out.println("No veo!!");
				robot.randomWalk();
			}
		}
		robot.move(new double[] {1000,0});
		robot.moveServo(1000);
	}
}
