package robot2;

import java.io.IOException;

public class TestVision {
	
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot robot = new Robot("red");
		
		while (true){
			robot.image2();
		}
		
	}

}
