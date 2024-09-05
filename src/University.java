import java.util.ArrayList;

public class University {
	ArrayList<Assistant> assistantArrayList = new ArrayList<Assistant>(); //Create a new list to store assistants
	ArrayList<Room> roomArrayList = new ArrayList<Room>(); //Create a new list to store rooms
	
	//--- Assistant methods ---
	public void inputAssistant(String inputName, String inputEmail) { //Used for when you want to input a new assistant 
		if(inputName != "" && inputName != null) { //Check if the name is non empty
			if(checkEmailPresence(inputEmail) == false) { //Check if the email could be used
				addNewAssistant(inputName, inputEmail); //Add the assistant using the inputs
			} else {
				System.out.println("That email is not valid or already in use"); 
			}
		} else {
			System.out.println("The name is not valid");
		}
	}
	
	public boolean checkEmailPresence(String emailToCheck) { //Checks whether an email would be present in the assistantArrayList
		if(emailToCheck != "" && emailToCheck != null) { //Check whether you could make a valid email
			String newEmail = Assistant.createEmail(emailToCheck); //Create a string for the email to be created
			for(int index = 0; index < assistantArrayList.size(); index++) { //For every assistant in the list
				if (newEmail.equals(assistantArrayList.get(index).getEmail())) { //Check if the email is already present
					return true; //Return true if the email cannot be used
				}
			}
		} else {
			System.out.println("The email is not in the correct format");
		}
		return false; //Return false if the email can be used
	}
	
	public void addNewAssistant(String newName, String newEmail) { //Add a new user based on two inputted strings
		Assistant newAssistant = new Assistant(newName, Assistant.createEmail(newEmail)); //Create a new assistant
		assistantArrayList.add(newAssistant); //Add it to the list
	}
	
	//--- Room methods ---
	public void inputRoom(String inputCode, int inputCapacity) { //Adds a room to the roomArrayList
		if(inputCapacity > 0){ //If the rooms capacity is greater than 0
			if(checkCode(inputCode) == false){ //If the code is not present in the list and valid
				Room newRoom = new Room(inputCode, inputCapacity); //Create a room with the inputs
				roomArrayList.add(newRoom); //Add the room
			} else {
				System.out.println("The inputted code is not valid or already in use");
			}
		} else {
			System.out.println("The inputted capacity is less than 0");
		}
	}
	
	public boolean checkCode(String codeToCheck) { //Checks if a code is valid, and if it is present in roomArrayList
		if(codeToCheck != "" && codeToCheck != null) {
			for(int index = 0; index < roomArrayList.size(); index++) {
				if(roomArrayList.get(index).getCode().equals(codeToCheck)) {
					return true;
				}
			}
		} else {
			System.out.println("The inputted code is invalid");
		}
		return false; //Return false if the code can be used
	}
	
	public ArrayList<Assistant> getAssistants(){return assistantArrayList;} //Returns the assistants arrayList
	public ArrayList<Room> getRooms(){return roomArrayList;} //Returns the rooms arrayList
	
	public void setAssistants(String[][] namesAndEmails){ //Sets the assistant list based on a 2d string array
		for(int index = 0; index < namesAndEmails.length; index++) { //For every name in the array
			this.inputAssistant(namesAndEmails[index][0],namesAndEmails[index][1]); //Add its name and respective email to the arrayList
		}
	}
	
	public void setRooms(String[] roomCodes, int[] roomCaps){ //Sets the room list based on a string array and an integer array
		if (roomCodes.length == roomCaps.length){
			for(int index = 0; index < roomCodes.length; index++) { //For every code in the array (This is only for the test)
				this.inputRoom(roomCodes[index], roomCaps[index]);
			}
		} else {
			System.out.println("The number of room codes and the number of room capacities is not equal, please check your inputs");
		}
	}
}