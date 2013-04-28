package ca.ubc.ctlt.ipeerb2.integration;

public class UserFixture {
	public final static String[][] USERS = new String[][] {
		// First Name, Last Name, Email, Username, Role, student number
		// those fixtures match the fixture in iPeer to test grade sync
		{"Instructor", "iPeer", "ipeertestinst@ubc.ca", "ipeertestinst", "Instructor", "00000000"},
		{"Ed", "Student", "redshirt0001@ubc.ca", "redshirt0001", "Student", "65498451"},
		{"Alex", "Student", "redshirt0002@ubc.ca", "redshirt0002", "Student", "65468188"},
		{"Matt", "Student", "redshirt0003@ubc.ca", "redshirt0003", "Student", "98985481"},
		{"Damien", "Student", "redshirt0009@ubc.ca", "redshirt0009", "Student", "84188465"},
		{"Jennifer", "Student", "redshirt0011@ubc.ca", "redshirt0011", "Student", "48877031"},
		{"Edna", "Student", "redshirt0013@ubc.ca", "redshirt0013", "Student", "37116036"},
		{"Jonathan", "Student", "redshirt0015@ubc.ca", "redshirt0015", "Student", "90938044"},
		{"Nicole", "Student", "redshirt0017@ubc.ca", "redshirt0017", "Student", "22784037"},
		{"Bill", "Student", "redshirt0022@ubc.ca", "redshirt0022", "Student", "19524032"},
		{"Michael", "Student", "redshirt0024@ubc.ca", "redshirt0024", "Student", "38058020"},
		{"Hui", "Student", "redshirt0027@ubc.ca", "redshirt0027", "Student", "10186039"},
		{"Bowinn", "Student", "redshirt0028@ubc.ca", "redshirt0028", "Student", "19803030"},
		{"Joe", "Student", "redshirt0039@ubc.ca", "redshirt0029", "Student", "51516498"},
	};
	
	public final static int NUM_STUDENTS = USERS.length - 1;
}
