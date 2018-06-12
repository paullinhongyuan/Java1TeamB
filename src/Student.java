import java.util.*;

public class Student {
	private String studentFirstName, studentLastName, studentEmail;
	private List<Course> courseEnrolled = new ArrayList<Course>();
	private int studentID;
	static int studentIDCounter = 0;

	public Student(String firstName, String lastName, String email) {
		//studentId increments for every student
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
	public void registerCourse(Course newCourse) {
		//Will update to first check the Course max enrollment once the Course Class is complete
		this.courseEnrolled.add(newCourse);
	}
	
	//Could possibly return a boolean based on how this will be called.
	public void removeCourse(Course oldCourse) {
		if(this.courseEnrolled.contains(oldCourse)) {
			this.courseEnrolled.remove(oldCourse);
		}
	}
	
	//View courses Enrolled
	public void listEnrolledCourses() {
		for(Course myCourse: this.courseEnrolled) {
			System.out.println(myCourse);  //update to myCourse.name for example
		}
	}
}
