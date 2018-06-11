
public class Student {
	private String studentFirstName, studentLastName, studentEmail;
	private String[] courseEnrolled;
	private int studentID;
	static int studentIDCounter = 0;

	public Student(String firstName, String lastName, String email) {
		++studentIDCounter;
		this.studentID = studentIDCounter;
		this.studentFirstName = firstName;
		this.studentLastName = lastName;
		this.studentEmail = email;
	}
	
	
	//Getters and setters
	public String getFirstName() {
		return this.studentFirstName;
	}
	
	public String getLastName() {
		return this.studentLastName;
	}
	
	public String getEmail() {
		return this.studentEmail;
	}
	
	public int getStudentId() {
		return this.studentID;
	}
	
	public void setFirstName(String firstName) {
		this.studentFirstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.studentLastName = lastName;
	}
	//email cannot be changed once created/verified.
	
	//Add or remove courses
	public void addCourse() {
		
	}
	
	public void removeCourse() {
		
	}
}
