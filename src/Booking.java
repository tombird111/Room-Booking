public class Booking {
	private BookableRoom bookedRoom;
	private ShiftAssistant bookedAssistant;
	private String studentEmail;
	private String timeSlot;
	private String status;
	
	public Booking(String timeSlot, BookableRoom room, ShiftAssistant assistant, String studentEmail) {
		this.timeSlot = timeSlot;
		this.bookedRoom = room;
		this.bookedAssistant = assistant;
		this.studentEmail = studentEmail;
		this.status = "SCHEDULED";
	}
	
	
	public ShiftAssistant getAssistant(){return this.bookedAssistant;}
	public BookableRoom getRoom(){return this.bookedRoom;}
	public String getStatus(){return this.status;} //Returns the status
	public void completeBooking(){this.status = "COMPLETED";} //Change the state of a Booking to COMPLETED
	public String getTranscript(){ //Returns a summary of information as a string
		String assistantEmail = bookedAssistant.getAssistant().getEmail();
		String roomCode = bookedRoom.getRoom().getCode();
		return ("| " + timeSlot + " | " + status + " | " + assistantEmail + " | " + roomCode + " | " + studentEmail + " |");
	}
}