import maslab.telemetry.channel.TextChannel;

public class HolaMundo {

	public static void main(String[] args){
		TextChannel canal = new TextChannel("Hola Mundo!");
		while (true){
			canal.publish("Hola Mundo!");
		}
	}
}
