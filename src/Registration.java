import java.util.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Registration {
	public static List<Course> availableCourses = new ArrayList<Course>();
	public static List<Student> studentList = new ArrayList<Student>();
	public static Student currentStudent = null;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Read course information for display from txt file
		File courseListFile = new File("Files/availableCourses.txt");
		try {

			Scanner sc = new Scanner(courseListFile);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
			    String[] elements = line.split(", ");
			    if(elements.length == 3) {
			    		availableCourses.add(new Course(elements[0], elements[1], Integer.parseInt(elements[2])));
			    }
			    else {
			    		System.out.println("Invalid input in Course Source .txt");
			    }
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File studentListFile = new File("Files/studentList.txt");
		try {

			Scanner sc = new Scanner(studentListFile);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
			    String[] elements = line.split(", ");
			    if(elements.length == 3) {
			    		studentList.add(new Student(elements[0], elements[1], elements[2]));
			    }
			    else {
			    		System.out.println("Invalid input in Student Source .txt");
			    }
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		mainMenu();
	}
	
	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
	}
	
	public static void logIn() {
		System.out.println("Please enter in your email address:");
		System.out.println();
		try {
			Scanner sc = new Scanner(System.in);
			//TODO validate proper email address
			String email = sc.next();
			if(!validateEmail(email)) {
				//check if email is in list
				sc.close();
				System.out.println("You must enter in a valid email address, eg.  hello@hotmail.com");
				clearScreen();
				logIn();
			}
			
			System.out.println("Please enter in your first and last name seperated by a space:");
			System.out.println();
		
			sc.useDelimiter(" ");
			String firstName = sc.next();
			String lastName = sc.next();
			
			//check the student list for email and matching first/last name.
			loop: for(Student student : studentList) {
				if(student.getEmail().equals(email)) {
					if(student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)) {
							currentStudent = student;
							break loop;
					}
					else {
						System.out.println("You must enter the same email address, First name, and Last Name as when you first registered.");
						logIn();
					}
				}
				else {
					//generate a new student
					currentStudent = new Student(firstName, lastName, email);
					studentList.add(currentStudent);
				}
			}
			sc.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static Course findCourse(String courseId) {
		Course myCourse = null;
		loop: for(Course course : availableCourses) {
			if(course.getCourseID().equals(courseId)) {
				myCourse = course;
				break loop;
			}
		}
		return myCourse;
	}
	
	public static void register() {
		//first check if we have a student object
		if(currentStudent == null) {
			logIn();
		}
		
		System.out.println("Listed below are all the courses that are available for registration this semister.");
		System.out.println("Please enter in the course number only to register for a course, eg. \"101\" for the \"101 - Java Basics\" course.");
		for(Course course : availableCourses) {
			System.out.println(course.getCourseID() + ", " + course.getCourseName());
		}
		try {
			Scanner sc = new Scanner(System.in);
			String courseId = sc.next();
			Course course = findCourse(courseId);
			if(course == null) {
				System.out.println("You entered an invalid course number, please try again");
				register();
			}
			else {
				if(course.enrollStudent(currentStudent)) {
					currentStudent.registerCourse(course);
					System.out.println("You have successfully registered for: " + course.getCourseName());
					mainMenu();
				}
				else {
					System.out.println("You are already registered for this course or the max number of students have already enrolled");
					mainMenu();
				}
			}
			sc.close();
		}
		catch(Exception e) {
			System.out.println(e);
			register();
		}
		
	}
	
	public static void courseListing() {
		System.out.println("Listed below are all the courses that are available for registration this semister.");
		System.out.println();
		
		//Sorts the list of Courses by course name
		Collections.sort(availableCourses, Course.courseNameComparator);
		
		for(Course course : availableCourses) {
			System.out.println(course.getCourseID() + ", " + course.getCourseName());
		}
		System.out.println();
		System.out.println("To register for a course enter R.");
		System.out.println("To return to the main menu enter M.");
		System.out.println();
		Scanner sc = new Scanner(System.in);
		try {
			String choice = sc.next();
			switch(choice.toUpperCase()) {
			case "R":
				clearScreen();
				register();
				break;
			case "M":
				clearScreen();
				mainMenu();
				break;
			default:
				clearScreen();
				System.out.println("You must enter either R, or M");
				courseListing();
			}
		}
		catch(Exception e) {
			System.out.print(e);
			System.out.println("You entered an invalid character, please enter either R, or M");
			courseListing();
		}
		sc.close();
	}
	
	public static void mainMenu() {
		System.out.println("Welcome to the Student Registration Page!");
		System.out.println();
		System.out.println("You may chose one of three options, please enter in the number of your choice:");
		System.out.println("Enter 1 - to view all available courses for registration.");
		System.out.println("Enter 2 - to Register or Unregister for a class (You must first Log In).");
		System.out.println("Enter 3 - to View all of courses in which you are registerd (You must first Log In).");
		System.out.println("Enter 4 - to exit out and end this program");
		Scanner sc = new Scanner(System.in);
		try {
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				clearScreen();
				courseListing();
				break;
			case 2:
				clearScreen();
				register();
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				clearScreen();
				System.out.println("You must enter either 1, 2, or 3");
				mainMenu();
			}
		}
		catch(InputMismatchException e) {
//			System.out.print(e);
			System.out.println("You entered an invalid character, please enter either 1, 2, or 3");
			mainMenu();
		}
		sc.close();
	}
	
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  

}
