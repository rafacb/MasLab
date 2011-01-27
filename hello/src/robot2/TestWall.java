package robot2;

import java.io.IOException;

public class TestWall {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
	    Robot robot = new Robot("red");
	    while (true){
	    	robot.image2();
	    	Thread.sleep(100);
	    	System.out.println(robot.isWall);
	    	if (robot.isWall){
				while (robot.input()[2] > .2){
					robot.move(new double[] {.5,.5});
				}for (int i=0; i<1750;i++){
				    robot.move(new double[] {.5,-.5});
				}
				Thread.sleep(80);
				while (robot.rearBump.getVoltage() > .2){
					robot.move(new double[] {-.5, -.5});
				}
				//Servo shit!
				for (int i = 0; i < 4000; i++){
					robot.moveServo(Math.PI*.75);
					System.out.println("Mas Arriba!");
				}for (int i = 0; i < 1000; i++){
					robot.moveServo(Math.PI*.4);
					System.out.println("Arriba!");
				}for (int i = 0; i < 1000; i++){
					robot.moveServo(1000);
					System.out.println("Muere!!");
				}
			}
	    }
		
	}

}
