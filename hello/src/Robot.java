import orc.IRRangeFinder;
import orc.Motor;
import orc.Orc;


public class Robot {

	//Variables to store motors.
	public static Motor[] motors = new Motor[2];
	
	//orc
	static Orc orco = Orc.makeOrc();
	
	//Analog input
	IRRangeFinder irFront = IRRangeFinder.makeGP2D12(orco, 1);
	
	//Motors
	static Motor motorR = new Motor(orco, 0, false);
	static Motor motorL = new Motor(orco, 1, true);
	
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
		Motor.setMultiplePWM(motors, speeds);
	}
	
	public double input(){
		return irFront.getRange();
	}
	
}
