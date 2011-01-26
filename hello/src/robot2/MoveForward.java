package robot2;
import java.io.IOException;

public class MoveForward {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static double[] irs;
	
	public static void main(String[] args) throws IOException{
		
		Robot robot = new Robot("green");
		
		long startTime = System.currentTimeMillis();
		long maxDurationInMilliseconds =3*60*1000;

		
		while (System.currentTimeMillis()<startTime+maxDurationInMilliseconds){
			//
			double[] irs = robot.input();
			System.out.println("irs 1 ="+irs[1]);
			System.out.println("irs0 ="+irs[0]);
			//robot.randomWalk();
			
			if (irs[0] < 0.5 && irs[0] != 0){
				System.out.println("Muy pegao a la derecha");
			}else if (irs[1] < 0.5 && irs[1] != 0){
				System.out.println("Izq...");
			}else if ((irs[0] > 0.5 && irs[1] > 0.5) || (irs[0] == 0 && irs[0] == 0)){
				System.out.println("Mete mano");
			}else{
				System.out.println("No se");
			}
			
		}
	}

}
