public class Room {
	private String code;
	private int capacity;
	
	public Room(String newCode, int newCap) {
		this.code = newCode;
		this.capacity = newCap;
	}
	
	public String getCode(){return this.code;}
	public int getCapacity(){return this.capacity;}
	public String getTranscript(){return ("| " + code + " | capacity: " + capacity + " |");} //Returns a summary of information as a string
}