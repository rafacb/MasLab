import java.io.IOException;


public class PalanteYPatras {

	public static void main(String[] args) throws IOException,
	InterruptedException {
		Robot robot = new Robot(args[0]);
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			for (int i = 0; i < 3; i++){
				robot.move(new double[] {-1,-1,-1});
			}for (int i = 0; i < 3; i++){
				robot.move(new double[] {1,1,-1});
			}
		}
	}
	
	
}
