import orc.Motor;
import orc.Orc;


public class Robot {

	//Variables to store motors.
	public static Motor[] motors;
	
	//orc
	static Orc orco = Orc.makeOrc();
	
	//Motors
	static Motor motorL = new Motor(orco, 0, false);
	static Motor motorR = new Motor(orco, 1, false);
	
	/**
	 * Constructor of a robot.
	 * Initiates the motors and gives them speed.
	 */
	public Robot(){
		motors[0] = motorL;
		motors[1] = motorR;
	}
	
	/**
	 * Move with speed "speeds".
	 */
	public void move(double[] speeds){
		while (true){
			Motor.setMultiplePWM(motors, speeds);
		}
	}
	
}
