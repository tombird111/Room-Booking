import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class BookingSystem {
	ArrayList<BookableRoom> bookableRooms = new ArrayList<BookableRoom>();
	ArrayList<ShiftAssistant> assistantsOnShift = new ArrayList<ShiftAssistant>();
	ArrayList<Booking> bookings = new ArrayList<Booking>();
	University uniResources = new University();
	Scanner userInput = new Scanner(System.in);
	
	public BookingSystem(String[][] namesAndEmails, String[] roomCodes, int[] roomCaps, String defaultStartTime, String defaultEndTime, int duration){
		this.initialiseUniResources(namesAndEmails, roomCodes, roomCaps);
		if(getDateFromString(defaultStartTime).equals(getDateFromString(defaultEndTime))){ //Check the items are on the same day
			this.addToArrayLists(defaultStartTime, defaultEndTime, duration);
		} else {
			System.out.println("The start and end dates are not on the same day");
		}
	}
	
	public static void emptyConsole() {  //Empties the console
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {}
	}	  
	
	public void mainMenu(String[] args){ //Prints out the main menu and handles the choices
		boolean exitInput = false;
		boolean inMenu = false;
		while (exitInput == false && inMenu == false){
			emptyConsole();
			System.out.println("University of Knowledge - COVID test \n\n" + 
			"Manage Bookings \n\n" + 
			"Please, enter the number to select your option: \n\n" +  
			"To manage Bookable Rooms: \n" +
			"	1. List\n" +
			"	2. Add\n" +
			"	3. Remove\n" + 
			"To manage Assistants on Shift:\n" +
			"	4. List\n" +
			"	5. Add\n" +
			"	6. Remove\n" +
			"To manage Bookings:\n" +
			"	7. List\n" + 
			"	8. Add\n" +
			"	9. Remove\n" + 
			"	10. Conclude\n" + 
			"After selecting one the options above, you will be presented other screens.\n" + 
			"If you press 0, you will be able to return to this main menu.\n" + 
			"Press -1 (or ctrl+c) to quit this application.\n"
			);
			String currentInput = userInput.nextLine();
			switch (currentInput){
				case "1": //List rooms
					exitInput = listMenu("ROOMS",  inMenu);
					break;
				case "2": //Add rooms
					exitInput = addRoomMenu(inMenu);
					break;
				case "3": //Remove rooms
					exitInput = removeRoomMenu(inMenu);
					break;
				case "4": //List assistants
					exitInput = listMenu("ASSISTANTS", inMenu);
					break;
				case "5": //Add assistants
					exitInput = addAssistantMenu(inMenu);
					break;
				case "6": //Remove assistants
					exitInput = removeAssistantMenu(inMenu);
					break;
				case "7": //List bookings
					exitInput = listMenu("BOOKINGS", inMenu);
					break;
				case "8": //Add bookings
					exitInput = addBookingsMenu(inMenu);
					break;
				case "9": //Remove bookings
					exitInput = removeBookingsMenu(inMenu);
					break;
				case "10": //Conclude booking
					exitInput = concludeBookingsMenu(inMenu);
					break;
				case "-1": //Quit the app
					exitInput = true;
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
	}
	
	private boolean concludeBookingsMenu(boolean inMenu){
		inMenu = true;
		boolean exitInput = false;
		while(inMenu == true){
			System.out.println("University of Knowledge - COVID test \n\n");
			this.showBookings("SCHEDULED");
			System.out.println("Conclude booking\n\n" + 
				"Please, enter one of the following:\n\n"+
				"The sequential ID to select the booking to be removed from the listed bookings above\n"+
				"0. Back to main menu.\n"+
				"-1. Quit application.\n");
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					String bookingIDString = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(Character.isDigit(currentInput.charAt(i))){
							bookingIDString = bookingIDString + currentInput.charAt(i);
						}
					}
					if(bookingIDString != ""){
						bookings.get(Integer.parseInt(bookingIDString) - 11).completeBooking();
					} else {
						System.out.println("Invalid input, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean removeBookingsMenu(boolean inMenu){
		inMenu = true;
		boolean exitInput = false;
		while(inMenu == true){
			System.out.println("University of Knowledge - COVID test \n\n");
			this.showBookings("SCHEDULED");
			System.out.println("Conclude booking\n\n" +
				"Please, enter one of the following:\n\n"+
				"The sequential ID to select the booking to be removed from the listed bookings above\n"+
				"0. Back to main menu.\n"+
				"-1. Quit application.\n");
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					String bookingIDString = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(Character.isDigit(currentInput.charAt(i))){
							bookingIDString = bookingIDString + currentInput.charAt(i);
						}
					}
					if(bookingIDString != ""){
						removeBooking(Integer.parseInt(bookingIDString));
					} else {
						System.out.println("Invalid input, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean addBookingsMenu(boolean inMenu){
		emptyConsole();
		System.out.println("University of Knowledge - COVID test \n\n" +
		"Adding booking (appointment for a COVID test) to the system \n");
		this.showBookableRooms("");
		inMenu = true;
		boolean exitInput = false;
		System.out.println("Please, enter one of the following:\n\n"+
		"A time-slot and the start of a student email, separated by a white space.\n"+
		"0. Back to main menu.\n"+
		"-1. Quit application.\n");
		while(inMenu == true){
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					int spaceCount = 0;
					String timeSlotID = "";
					String email = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(currentInput.charAt(i) != ' '){
							switch(spaceCount){
								case 0:
									if(Character.isDigit(currentInput.charAt(i))){timeSlotID = timeSlotID + currentInput.charAt(i);}
									break;
								case 1:
									email = email + currentInput.charAt(i);
									break;
							}
						} else {
							spaceCount++;
						}
					}
					if(spaceCount == 1 && email != ""){
						createBooking(bookableRooms.get(Integer.parseInt(timeSlotID) - 11).getTimeSlot(), email);
					} else {
						System.out.println("Invalid entry, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean removeAssistantMenu(boolean inMenu){
		inMenu = true;
		boolean exitInput = false;
		while(inMenu == true){
			System.out.println("University of Knowledge - COVID test \n\n");
			showShiftAssistants("FREE");
			System.out.println("Please, enter one of the following:\n\n"+
				"The sequential ID to select the assistant on shift to be removed.\n"+
				"0. Back to main menu.\n"+
				"-1. Quit application.\n");
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					String assistantIDString = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(Character.isDigit(currentInput.charAt(i))){
							assistantIDString = assistantIDString + currentInput.charAt(i);
						}
					}
					if(assistantIDString != ""){
						removeAssistantOnShift(Integer.parseInt(assistantIDString));
					} else {
						System.out.println("Invalid input, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean addAssistantMenu(boolean inMenu){
		emptyConsole();
		System.out.println("University of Knowledge - COVID test \n\n" +
		"Adding assistant on shift \n");
		this.showAssistants();
		inMenu = true;
		boolean exitInput = false;
		System.out.println("Please, enter one of the following:\n\n"+
		"The sequential ID of an assistant, a date (dd/mm/yyyy) separated by a white space.\n"+
		"0. Back to main menu.\n"+
		"-1. Quit application.\n");
		while(inMenu == true){
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					int spaceCount = 0;
					String assistantIDString = "";
					String date = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(currentInput.charAt(i) != ' '){
							switch(spaceCount){
								case 0:
									if(Character.isDigit(currentInput.charAt(i))){assistantIDString = assistantIDString + currentInput.charAt(i);}
									break;
								case 1:
									date = date + currentInput.charAt(i);
									break;
							}
						} else {
							spaceCount++;
						}
					}
					if(spaceCount == 1 && date != "" && date.length() == 10){
						addAssistantOnShift(Integer.parseInt(assistantIDString), date);
					} else {
						System.out.println("Invalid entry, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean removeRoomMenu(boolean inMenu){
		inMenu = true;
		boolean exitInput = false;
		while(inMenu == true){
			System.out.println("University of Knowledge - COVID test \n\n");
			showBookableRooms("EMPTY");
			System.out.println("Please, enter one of the following:\n\n"+
				"The sequential ID to select the bookable room to be removed\n"+
				"0. Back to main menu.\n"+
				"-1. Quit application.\n");
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					String roomIDString = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(Character.isDigit(currentInput.charAt(i))){
							roomIDString = roomIDString + currentInput.charAt(i);
						}
					}
					if(roomIDString != ""){
						removeBookableRoom(Integer.parseInt(roomIDString));
					} else {
						System.out.println("Invalid input, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean addRoomMenu(boolean inMenu){
		emptyConsole();
		System.out.println("University of Knowledge - COVID test \n\n" +
		"Adding bookable room \n");
		this.showRooms();
		inMenu = true;
		boolean exitInput = false;
		System.out.println("Please, enter one of the following:\n\n"+
		"The sequential ID listed to a room, a date (dd/mm/yyyy), and a time (HH:MM), separated by a white space.\n"+
		"0. Back to main menu.\n"+
		"-1. Quit application.\n");
		while(inMenu == true){
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					int spaceCount = 0;
					String roomIDString = "";
					String date = "";
					String time = "";
					for(int i = 0; i < currentInput.length(); i++){
						if(currentInput.charAt(i) != ' '){
							switch(spaceCount){
								case 0:
									if(Character.isDigit(currentInput.charAt(i))){roomIDString = roomIDString + currentInput.charAt(i);}
									break;
								case 1:
									date = date + currentInput.charAt(i);
									break;
								case 2:
									time = time + currentInput.charAt(i);
									break;
							}
						} else {
							spaceCount++;
						}
					}
					if(spaceCount == 2 && time != "" && date.length() == 10 && time.length() == 5){
						addBookableRoom(Integer.parseInt(roomIDString), date, time);
					} else {
						System.out.println("Invalid entry, please try again");
					}
			}
		}
		return exitInput;
	}
	
	private boolean listMenu(String typeSelected, boolean inMenu){ //Open the menu to show a list of a given type
		emptyConsole();
		System.out.println("University of Knowledge - COVID test \n\n");
		switch(typeSelected){
			case "ROOMS":
				this.showBookableRooms("");
				break;
			case "ASSISTANTS":
				this.showShiftAssistants("");
				break;
			case "BOOKINGS":
				this.showBookings("");
				break;
		}
		inMenu = true;
		System.out.println("0. Back to main menu. \n -1. Quit application.\n\n");
		boolean exitInput = false;
		while(inMenu == true){
			String currentInput = userInput.nextLine();
			switch(currentInput){
				case "0":
					inMenu = false;
					emptyConsole();
					break;
				case "-1":
					inMenu = false;
					exitInput = true;
					break;
				default:
					System.out.println("Invalid input, please try again");
			}
		}
		return exitInput;
	}
	
	public void addAssistantOnShift(int assistantID, String date){
		String startTime = "07:00"; //Create a start time
		for(int i = 0; i < 3; i++){  //For 3 shifts
			String currentTime = getTimeFromMinutes(getMinutes(startTime) + (60 * i)); //Get the time for each time slot
			String timeSlot = ("<" + date + " " + currentTime + ">"); //Add the date and the time slot to make a timeSlot with the format <dd/mm/yyyy HH:MM>
			Assistant newAssistant = uniResources.getAssistants().get(assistantID - 11);
			ShiftAssistant assistantOnShift = new ShiftAssistant(newAssistant, timeSlot);
			assistantsOnShift.add(assistantOnShift);
			System.out.println("Assistant successfully added");
			System.out.println(assistantOnShift.getTranscript());
		}
	}
	
	public void addBookableRoom(int roomID, String date, String time){
		String timeSlot = ("<" + date + " " + time + ">");
		Room newRoom = uniResources.getRooms().get(roomID - 11);
		BookableRoom newBookableRoom = new BookableRoom(newRoom, timeSlot);
		bookableRooms.add(newBookableRoom);
		System.out.println("Bookable Room added successfully:\n");
		System.out.println(newBookableRoom.getTranscript());
	}
	
	public void removeAssistantOnShift(int id){
		int indexToGet = id - 11;
		for(int index = 0; index < assistantsOnShift.size(); index++){ //For every assistant on shift
			if(index == indexToGet){ //Check if the index is the same as the ID - 10
				if(assistantsOnShift.get(index).getStatus().equals("FREE")){ //If it is FREE
					System.out.println("Assistant removed successfully");
					System.out.println(assistantsOnShift.get(index).getTranscript());
					assistantsOnShift.remove(index); //Remove the assistant from the list
				} else {
					System.out.println("You cannot remove BUSY assistants on shift");
				}
			}
		}
	}
	
	public void removeBookableRoom(int id){
		int indexToGet = id - 11;
		for(int index = 0; index < bookableRooms.size(); index++){ //For every bookable room
			if(index == indexToGet){ //Check if the index is the same as the ID - 10
				if(bookableRooms.get(index).getStatus().equals("EMPTY")){ //If it is EMPTY
					System.out.println("Room removed successfully");
					System.out.println(bookableRooms.get(index).getTranscript());
					bookableRooms.remove(index); //Remove the bookable room from the list
				} else {
					System.out.println("You cannot remove non EMPTY rooms");
				}
			}
		}
	}
	
	//--- BOOKING METHODS ---
	public void removeBooking(int id){ //Removes a booking at a given ID
		int indexToGet = id - 11;
		for(int index = 0; index < bookings.size(); index++){ //For every booking
			if(index == indexToGet){ //Check if the index is the same as the ID - 10
				if(bookings.get(index).getStatus().equals("SCHEDULED")){ //If it is scheduled
					bookings.get(index).getAssistant().setFree(); //Free up the assistant
					bookings.get(index).getRoom().decreaseOccupancy(); //Decrease the occupancy of the room
					bookings.remove(index); //Remove the booking from the list
				}
			}
		}
	}
	
	public void createBooking(String timeSlot, String emailStarter){ //Creates a booking from a given timeSlot and emailStarter
		if(checkRoomAvailability(timeSlot) && checkAssistantAvailability(timeSlot)){ //Check that you can make a booking at this time
			int roomIndex = getSpareRoomIndex(timeSlot); //Get a usable room
			int assistantIndex = getSpareAssistantIndex(timeSlot); //Get a usable assistant
			String email = Assistant.createEmail(emailStarter); //Create an email from the emailStarter
			Booking newBooking = new Booking(timeSlot, bookableRooms.get(roomIndex), assistantsOnShift.get(assistantIndex), email);
			bookings.add(newBooking);
			System.out.println("Booking successfully created");
			System.out.println(newBooking.getTranscript());
			bookableRooms.get(roomIndex).increaseOccupancy(); //Increase the occupancy of the room
			assistantsOnShift.get(assistantIndex).setBusy(); //Set the assistantOnShift to busy
		}
	}
	
	public int getSpareAssistantIndex(String timeSlot){ //Returns the index of a free assistant for a given timeslot
		boolean assistantFound = false;
		int assistantIndex = 0;
		while(assistantFound == false && assistantIndex < assistantsOnShift.size()){ //Whilst you haven't found an assistant, and you haven't exceeded the length of the list
			if(assistantsOnShift.get(assistantIndex).getTimeSlot().equals(timeSlot) && (assistantsOnShift.get(assistantIndex).getStatus().equals("FREE"))){ //If you have found a room with the same timeslot
				assistantFound = true; //Note that you have found a room
			} else {
				assistantIndex++; //Iterate by 1 along the arraylist
			}
		}
		return assistantIndex;
	}
	
	public int getSpareRoomIndex(String timeSlot){ //Returns the index of an available room for a given timeslot
		boolean roomFound = false; //Note that you have not yet found an available room
		int roomIndex = 0;
		while(roomFound == false && roomIndex < bookableRooms.size()){ //Whilst you haven't found a room, and you haven't exceeded the length of the list
			if(bookableRooms.get(roomIndex).getTimeSlot().equals(timeSlot) && bookableRooms.get(roomIndex).getStatus().equals("FULL") != true){ //If you have found a room with the same timeslot
				roomFound = true; //Note that you have found a room
			} else {
				roomIndex++; //Iterate by 1 along the arraylist
			}
		}
		return roomIndex;
	}
	
	public boolean checkAssistantAvailability(String timeSlot){ //Checks if an assistant is available for a specific timeSlot
		boolean availableAssistant = false;
		for(ShiftAssistant assistantOnShift : assistantsOnShift){ //Check if there is an available assistant on shift for that time
			if(assistantOnShift.getTimeSlot().equals(timeSlot) && (assistantOnShift.getStatus().equals("FREE"))){
				availableAssistant = true;
			}
		}
		if(availableAssistant != true){System.out.println("There are no available assistants for that date and time");} //Print an error message if there is not
		return availableAssistant;
	}
	
	public boolean checkRoomAvailability(String timeSlot){ //Checks if a room is available for a specific timeSlot
		boolean availableRoom = false;
		for (int roomIndex = 0; roomIndex < bookableRooms.size(); roomIndex++){ //For every room
			if(bookableRooms.get(roomIndex).getTimeSlot().equals(timeSlot) & (bookableRooms.get(roomIndex).getStatus().equals("FULL") != true)){ //Check if there is an available room for that time
				availableRoom = true;
			}
		}
		if(availableRoom != true){System.out.println("There are no available rooms for that date and time");} //Print an error message if there is not
		return availableRoom;
	}
	
	//--- SHOWING METHODS ---
	public void showBookings(String statusToSearch){ //Show the transcripts of all Bookings
		int count = 11;
		switch (statusToSearch){
			case "":
				System.out.println("List of Bookings:");
				break;
			default:
				System.out.println("List of Bookings: " + statusToSearch);
				break;
		}
		if(statusToSearch != ""){ //If you are searching with a condition
			for(Booking booking : bookings){ 
				if(booking.getStatus().equals(statusToSearch)){
					System.out.println(count + ". " + booking.getTranscript());
				}
				count++;
			}
		} else {
			for(Booking booking : bookings){ 
				System.out.println(count + ". " + booking.getTranscript());
				count++;
			}
		}
	}
	
	public void showRooms(){ //Show the transcripts of all Rooms
		int count = 11;
		System.out.println("List of Rooms");
		for(Room room : uniResources.getRooms()){ 
			System.out.println(count + ". " + room.getTranscript());
			count++;
		}
	}
	
	public void showAssistants(){ //Show the transcripts of all Assistants
		int count = 11;
		System.out.println("List of Assistants");
		for(Assistant assistant : uniResources.getAssistants()){ 
			System.out.println(count + ". " + assistant.getTranscript());
			count++;
		}
	}
	
	public void showShiftAssistants(String statusToSearch){ //Show the transcripts of all ShiftAssistants
		int count = 11;
		switch (statusToSearch){
			case "":
				System.out.println("List of Assistants on Shift:");
				break;
			default:
				System.out.println("List of Assistants on Shift: " + statusToSearch);
				break;
		}
		if(statusToSearch != ""){ //If you are searching with a condition
			for(ShiftAssistant assistantOnShift : assistantsOnShift){ //For every assistant
				if(assistantOnShift.getStatus().equals(statusToSearch)){
					System.out.println(count + ". " + assistantOnShift.getTranscript()); //Print if its status matches the status to search
				}
				count++;
			}
		} else {
			for(ShiftAssistant assistantOnShift : assistantsOnShift){
				System.out.println(count + ". " + assistantOnShift.getTranscript());
				count++;
			}
		}
	}
	
	public void showBookableRooms(String statusToSearch){ //Show the transcripts of all bookableRooms
		int count = 11;
		switch (statusToSearch){
			case "":
				System.out.println("List of Bookable Rooms: ");
				break;
			default:
				System.out.println("List of Bookable Rooms: " + statusToSearch);
				break;
		}
		if(statusToSearch != ""){ //If you are searching with a condition
			for(BookableRoom room : bookableRooms){ 
				if(room.getStatus().equals(statusToSearch)){
					System.out.println(count + ". " + room.getTranscript());
				}
				count++;
			}
		} else {
			for(BookableRoom room : bookableRooms){ 
				System.out.println(count + ". " + room.getTranscript());
				count++;
			}
		}
	}
	
	//--- TIME AND SETUP FUNCTIONS ---
	public void addToArrayLists(String startTimeString, String endTimeString, int duration){
	//Add BookableRooms and ShiftAssistants to the list arrays based on start and end time strings, with a duration
		String startTime = getTimeFromString(startTimeString); //Get the times from ecah of the strings, as dates are unimportant for calculating time
		String endTime = getTimeFromString(endTimeString);
		int timeDifference = getTimeDifference(startTime, endTime); //Get the difference in time between the start and end times
		int numberOfSlots = timeDifference/duration; //Divide the time difference to get the number of potential slots
		String date = getDateFromString(startTimeString);  //Get the date from the timeString
		for(int i = 0; i < numberOfSlots; i++){  //For every possible slot
			String currentTime = getTimeFromMinutes(getMinutes(startTime) + (duration * i)); //Get the time for each time slot
			String timeSlot = ("<" + date + " " + currentTime + ">"); //Add the date and the time slot to make a timeSlot with the format <dd/mm/yyyy HH:MM>
			for (Assistant currentAssistant: uniResources.getAssistants()){ //Create an assistantOnShift with each assistant and time slot
				ShiftAssistant assistantOnShift = new ShiftAssistant(currentAssistant, timeSlot);
				assistantsOnShift.add(assistantOnShift);
			}
			for (Room currentRoom: uniResources.getRooms()){ //Create a bookableRoom to make with each room and time slot
				BookableRoom newBookableRoom = new BookableRoom(currentRoom, timeSlot);
				bookableRooms.add(newBookableRoom);
			}
		}
	}
	
	public static String getDateFromString(String dateTime){ //Get the date from a given stringin the format <dd/mm/yyyy HH:MM>
		String date = "";
		if(dateTime.length() == 18) { //Check the string has the same length of the format <dd/mm/yyyy HH:MM>
			for(int index = 1; index < 11; index++) { //For the characters between 1 and 11
				date = date + dateTime.charAt(index); //Add them to the date string
			}
		} else {
			System.out.println("The inputted time and date were not the correct length");
		}
		return date;
	}
	
	public static String getTimeFromMinutes(int minutes){ //Return a string in the format "HH:MM" from a number of minutes
		String timeString = "";
		int tenHours = minutes / (60*10); //Work out how many sets of 10 hours are in the minutes
		minutes -= (tenHours * 60 * 10); //Subtract the sets of 10 hours from the total
		int oneHours = minutes / 60; //Work out the remaining number of hours
		minutes -= (oneHours * 60); //Subtract the hours from the total
		int tenMinutes = minutes / 10; //Work out how many sets of 10 minutes are in the minutes
		minutes -= (tenMinutes * 10); //Subtract the sets of 10 minutes from the total
		int oneMinutes = minutes; //Get the remaining number of minutes
		timeString = timeString + tenHours + oneHours + ":" + tenMinutes + oneMinutes; //Concatenate the numbers to form a time
		return timeString;
	}
	
	public static int getTimeDifference(String startTime, String endTime){ //Return the difference in time in minutes between two times of format "HH:MM"
		int timeDifference = getMinutes(endTime) - getMinutes(startTime);
		if (timeDifference < 0) {
			System.out.println("The end time is before the start time");
			timeDifference = 0;
		}
		return timeDifference;
	}
	
	public static int getMinutes(String timeString){ //Return the number of minutes from a time of format "HH:MM"
		int totalMinutes = 0;
		if(timeString.length() == 5){ //Check the string is the correct length
			for(int index = 0; index < 5; index++){ //For every character in the string
				if(Character.isDigit(timeString.charAt(index))){ //Check it is a digit
					int multiplier = 1;
					switch (index) { //Decide how much the digit should be multiplied based on position within the string
						case 0:
							multiplier = 60*10; //The first number represents 10 hours, which are 60 minutes each
							break;
						case 1:
							multiplier = 60; //The second number represents 1 hour, which is 60 minutes
							break;
						case 3: //The third number represents 10 minutes
							multiplier = 10;
							break;
						case 4: //The fourth number is the remainder of minutes
							multiplier = 1;
							break;
					}
					totalMinutes += multiplier * Character.getNumericValue(timeString.charAt(index));
				}
			}
		} else {
			System.out.println("An inputted time was not the correct length");
		}
		return totalMinutes;
	}
	
	public static String getTimeFromString(String dateTime){ //Return the time from a string format  <dd/mm/yyyy HH:MM>
		String time = ""; //Create an empty time string
		if(dateTime.length() == 18) { //Check the string has the same length of the format <dd/mm/yyyy HH:MM>
			for(int index = 12; index < 17; index++) { //For the characters between 12 and 18
				time = time + dateTime.charAt(index); //Add them to the time string
			}
		} else {
			System.out.println("The inputted time and date were not the correct length");
		}
		return time;
	}
	
 	public void initialiseUniResources(String[][] namesAndEmails, String[] roomCodes, int[] roomCaps){
		uniResources.setAssistants(namesAndEmails); //Add the assistants to the university resources
		uniResources.setRooms(roomCodes, roomCaps); //Add the rooms to university resources
	}
}