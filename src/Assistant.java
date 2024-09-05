public class Assistant {
	private String name;
	private String email;
	
	public Assistant(String newName, String email){
		this.name = newName;
		this.email = email;
	}
	
	public String getName(){return this.name;}
	public String getEmail(){return this.email;}
	public static String createEmail(String emailStarter){return emailStarter + "@uok.ac.uk";}
	public String getTranscript(){return ("| " + name + " | " + email + " |");} //Returns a summary of information as a string
}