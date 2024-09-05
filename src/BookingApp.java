public class BookingApp {
	public static void main(String[] args){
		String[][] namesAndEmails = {{"Keith","keithdog"}, {"Fred","keithdog"}, {"Boingi","BoingiBox"}, {"", ""}}; //Create an array of the names and emails of Assistants
		String[] roomCodes = {"Room 101", "The Mechadome", "Duel Arena", "Death Realm"}; //Create an array of room codes
		int[] roomCaps = {2, 1, 3, 0}; //Create an array of room capacities
		String startTimeString = "<28/02/2021 07:00>";
		String endTimeString = "<28/02/2021 10:00>";
		int duration = 60;
		BookingSystem bookingSystem = new BookingSystem(namesAndEmails, roomCodes, roomCaps, startTimeString, endTimeString, duration);
		bookingSystem.mainMenu(args);
	}
}