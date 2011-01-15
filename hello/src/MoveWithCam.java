import java.io.IOException;

public class MoveWithCam {
	
	//Variables to store speeds of the motors
	static double[] speeds = new double[2];
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		Robot robot = new Robot();
		
		while (true){
			/**System.out.println(robot.input());
			if (robot.input() > 1.8 || robot.input() == 0){
				speeds[0] = 0.4;
				speeds[1] = 0.4;
				
				robot.move(speeds);
			}else{
				speeds[0] = 0.4;
				speeds[1] = -0.4;
				
				robot.move(speeds);
			}**/
			Thread.sleep(20);
			//if (count%20 == 0){
			if (robot.image()){
				//If the ball is seen in the right side of the picture, then turn right
				//Do the opposite if it's seen on the left side.
				if (robot.pos[0] < 150){
					System.out.println("Veo bola a la izq, mueve derecha");
					robot.turn(true);
				}else if (robot.pos[0] > 170){
					System.out.println("Veo bola a la der, mueve izq");
					robot.turn(false);
				}
			}else{
				robot.randomWalk();
			}
			
			
			
			
			
		}
	}

}
