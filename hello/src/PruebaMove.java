import java.io.IOException;

public class PruebaMove {
	
	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot r = new Robot("red");
		double[] speed = {Double.valueOf(args[0]),Double.valueOf(args[1]), 0};
		for (int i = 0; i<1000; i++){
			r.move(speed);
		}
	}

}
