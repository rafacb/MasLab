import java.io.IOException;


public class ViewTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Robot robot = new Robot(args[0]);
		
		while (true){
			
			robot.image();
			Thread.sleep(1000);

			System.out.println("ball = "+robot.isBall);
			System.out.println("wall = "+robot.isWall);
			System.out.println("goal = "+robot.isGoal);
			
		}
	}

}
