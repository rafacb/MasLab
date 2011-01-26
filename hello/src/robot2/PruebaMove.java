package robot2;

import java.io.IOException;

public class PruebaMove {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot r = new Robot("red");
		double[] speed = {1,1};
		while (true){
			r.move(speed);
			r.moveServo(Math.PI/4);
		}
	}

}
