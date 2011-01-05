import orc.Motor;
import orc.Orc;

public class MoveForward {
	
	static Motor[] motors = new Motor[2];
	static double[] speeds = new double[2];
	
	public static void main(String[] args){
		Orc orco = Orc.makeOrc();
		
		Motor motorL = new Motor(orco, 0, true);
		Motor motorR = new Motor(orco, 1, false);
		motors[0] = motorL;
		motors[1] = motorR;
		speeds[0] = 0;
		speeds[1] = 0.5;
		
		while (true){
			Motor.setMultiplePWM(motors, speeds);
		}
	}

}
