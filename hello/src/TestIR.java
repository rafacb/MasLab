import java.io.IOException;


public class TestIR {

public static void main(String[] args) throws IOException{
		
		Robot robot = new Robot(args[0]);
		
		while (true){
			System.out.println(robot.input()[2]);
		}
		
	}
}
