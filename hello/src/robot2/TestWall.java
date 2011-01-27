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
				}for (int i=0; i<Integer.valueOf(args[0]);i++){
				    double speed = Double.valueOf(args[1]);
				    robot.move(new double[] {speed,-speed});
				}
				Thread.sleep(80);
				robot.move(new double[] {1000,0});
				while (robot.input()[3] > .11195 && robot.rearBump.getVoltage() > .2){
				    System.out.println(robot.input()[3]);
				    robot.move(new double[] {-.7, -.7});
				}System.out.println(robot.input()[3]);
				//Servo shit!
				for (int i = 0; i < 4000; i++){
					robot.moveServo(Math.PI*.75);
					
				}for (int i = 0; i < 1000; i++){
					robot.moveServo(Math.PI*.4);
				       
				}for (int i = 0; i < 1000; i++){
				    robot.moveServo(1000);
				}
			}
	    }
		
	}

}
