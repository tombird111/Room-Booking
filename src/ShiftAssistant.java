public class ShiftAssistant {
	private Assistant assistant;
	private String timeSlot;
	private String status;
	
	public ShiftAssistant(Assistant assistant, String timeSlot) {
		this.assistant = assistant;
		this.timeSlot = timeSlot;
		this.status = "FREE";
	}
	
	public void setBusy(){this.status = "BUSY";} //Change the state of a ShiftAssistant to BUSY
	public void setFree(){this.status = "FREE";} //Change the state of a ShiftAssistant to FREE
	public Assistant getAssistant(){return assistant;}
	public String getTimeSlot(){return timeSlot;}
	public String getStatus(){return status;} //Returns the status
	public String getTranscript(){return ("| " + timeSlot + " | " + status + " | " + assistant.getEmail() + " |");} //Returns a summary of information as a string
}