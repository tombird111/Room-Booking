public class BookableRoom {
	private Room room;
	private String timeSlot;
	private String status;
	private int occupancy;
	
	public BookableRoom(Room room, String timeSlot){
		this.room = room;
		this.timeSlot = timeSlot;
		this.status = "EMPTY";
		this.occupancy = 0; //When a bookable room is created, it has no one using it
	}
	
	public void increaseOccupancy(){ //Increase the occupancy by 1
		this.occupancy++;
		if(this.occupancy == this.room.getCapacity()){ //If the room is at capacity
			status = "FULL"; //Mark its status as full
		} else { //If not
			status = "AVAILABLE"; //Mark its status as available (cannot go to empty)
		}
	}
	
	public void decreaseOccupancy(){ //Decrease the occupancy by 1
		this.occupancy--;
		if(occupancy == 0){ //If the room is now empty
			status = "EMPTY"; //Mark its status as empty
		} else { //If not
			status = "AVAILABLE"; //Mark its status as available (cannot go to full)
		}
	}
	
	public String getTranscript(){return ("| " + timeSlot + " | " + status + " | " + room.getCode() + " |  occupancy: " + occupancy + " |");} //Returns a summary of information as a string
	public Room getRoom(){return room;} //Returns the related room
	public String getTimeSlot(){return timeSlot;} //Returns the timeSlot
	public String getStatus(){return status;} //Returns the status
}