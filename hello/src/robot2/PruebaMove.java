package robot2;

import java.io.IOException;

public class PruebaMove {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot r = new Robot("red");
		double[] speed = {.5,.5};
		for (int i = 0; i<1000; i++){
			r.move(speed);
		}
	}

}
