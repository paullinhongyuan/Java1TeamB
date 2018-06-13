import java.util.*;
import java.util.regex.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Registration {
	public static List<Course> availableCourses = new ArrayList<Course>();
	public static List<Student> studentList = new ArrayList<Student>();
	public static Student currentStudent = null;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Read course information for display from txt file
		File courseListFile = new File("Files/availableCourses.txt");
		try(Scanner scFile = new Scanner(courseListFile)) {

			while (scFile.hasNextLine()) {
				String line = scFile.nextLine();
			    String[] elements = line.split(";");
			    if(elements.length == 6) {
			    		availableCourses.add(new Course(elements[0].trim(), elements[1].trim(), Integer.parseInt(elements[2].trim()), Integer.parseInt(elements[3].trim()), elements[4].trim(), elements[5].trim()));
			    }
			    else {
			    		System.out.println("Invalid input in Course Source .txt");
			    }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		File studentListFile = new File("Files/studentList.txt");
		try(Scanner scFile = new Scanner(studentListFile)) {

			while (scFile.hasNextLine()) {
				String line = scFile.nextLine();
			    String[] elements = line.split(",");
			    if(elements.length == 3 && !elements[0].equals("  Enrolled Courses")) {
			    		Student newStudent = new Student(elements[0].trim(), elements[1].trim(), elements[2].trim());
			    		String enrolledLine = scFile.nextLine();
					String[] enrolled = enrolledLine.split("[:,]");
					for(String value : enrolled) {
						if(!value.equals("Enrolled Courses")) {
							newStudent.registerCourse(value);	
						}
					}
			    		studentList.add(newStudent);
			    }
			    else {
			    		System.out.println("Invalid input in Student Source .txt");
			    }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mainMenu();
		sc.close();
	}
	
	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
	}
	
	public static void logIn() {
		System.out.println("Please enter in your email address:");
		System.out.println();
		try {
			//TODO validate proper email address
			String email = sc.next();
			if(!validateEmail(email)) {
				//check if email is in list
				System.out.println("You must enter in a valid email address, eg.  hello@hotmail.com");
				logIn();
			}
			System.out.println("Please enter in your first name:");
			String firstName = sc.next();
			System.out.println("Please enter in your last name:");
			String lastName = sc.next();
			
			//check the student list for email and matching first/last name.
			boolean studentFound = false;
			loop: for(Student student : studentList) {
				if(student.getEmail().equals(email)) {
					studentFound = true;
					if(student.getFirstName().equalsIgnoreCase(firstName) && student.getLastName().equalsIgnoreCase(lastName)) {
							currentStudent = student;
							break loop;
					}
					else {
						System.out.println("You must enter the same email address, First name, and Last Name as when you first registered.");
						logIn();
					}
				}	
			}
			if(!studentFound) {
				//generate a new student
				currentStudent = new Student(firstName, lastName, email);
				studentList.add(currentStudent);
			}
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
	
	public static void updateFiles() {
		//Outputs the student list to text file
		File studentfile = new File("Files/studentList.txt");
		File coursefile = new File("Files/availableCourses.txt");
	    FileWriter writer = null;
	    try {
	        writer = new FileWriter(studentfile);
	        for(Student student : studentList) {
	        		writer.write(student.getFirstName() + ", " + student.getLastName() + ", " + student.getEmail() + "\n");
	        		writer.write("Enrolled Courses: ");
	        		String prefix = "";
	        		for(String courseId : student.getEnrolledList()) {
//	        			System.out.println(courseId);
	        			if(!courseId.equals("Enrolled Courses")) {
	        				if(!courseId.equals(",")) {
	        					writer.write(prefix);
	        					prefix = ", ";
	        					writer.write(courseId);
	        				}
	        			}
	        		}
	        		writer.write("\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    } finally {
	        if (writer != null) try { writer.close(); } catch (IOException ignore) {}
	    }
	    System.out.printf("File is located at %s%n", studentfile.getAbsolutePath());
	    
	    //Outputs the course listing with proper counts to text file
	    try {
	        writer = new FileWriter(coursefile);
	        for(Course course : availableCourses) {
	        		writer.write(course.getCourseID() + "; " + course.getCourseName() + "; " +  course.getClassSize() + "; " + course.getNumberEnrolled() + "; " + course.getCourseSummary() + "; " + course.getCourseDates() + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    } finally {
	        if (writer != null) try { writer.close(); } catch (IOException ignore) {}
	    }
	    System.out.printf("File is located at %s%n", coursefile.getAbsolutePath());
	}
	
	public static void listCourses() {
		if(currentStudent == null) {
			System.out.println("Calling Login");
			logIn();
		}
		
		System.out.println("Listed below are all the courses that you are enrolled in.");
		System.out.println("To unregister from a course, enter in the course number only.");
		System.out.println("To return to the main menu enter, M");
		//get the list of course id's the student is enrolled in then find and list the course details
		for(String courseId : currentStudent.getEnrolledList()) {
//			System.out.println(courseId.trim());
			Course course = findCourse(courseId.trim());
			System.out.println("Course Id: " + course.getCourseID() + ", Course Name: " + course.getCourseName() + ", Class Size: " + course.getClassSize() + ", Currently Enrolled: " + course.getNumberEnrolled() + "\n   Course Dates: " + course.getCourseDates());
		}
		try {
			String courseId = sc.next();
			if(courseId.equalsIgnoreCase("M")) {
				mainMenu();
			}
			else {
				Course course = findCourse(courseId);
				//if we are unable to find the course based on course id, re-enter course id.
				if(course == null) {
					System.out.println("You entered an invalid course number, please try again");
					mainMenu();
				}
				else {
					System.out.println("You are about to unregister from Course: " + course.getCourseName() + " Course ID:" + course.getCourseID());
					System.out.println("To proceed enter YES");
					String response = sc.next();
					if(response.equalsIgnoreCase("YES")) {
						if(course.removeStudent(currentStudent)) {
							currentStudent.removeCourse(course);
							System.out.println("You have unregistered from Course: "  + course.getCourseName());
							listCourses();
						}
					}
					else {
						listCourses();
					}
				}
			}
		}
		catch(Exception e) {
			//TODO update this message;
			System.out.println(e);
			mainMenu();
		}
	}
	
	public static void register() {
		//first check if we have a student object
		if(currentStudent == null) {
			System.out.println("Calling Login");
			logIn();
		}
		
		System.out.println("Listed below are all the courses that are available for registration this semister.");
		System.out.println("Please enter in the course number only to register for a course, eg. \"101\" for the \"101 - Java Basics\" course.");
		for(Course course : availableCourses) {
			//Only prints courses in which the number enrolled is less than the max course size.
			if(course.getNumberEnrolled() < course.getClassSize()) {
				System.out.println("Course Id: " + course.getCourseID() + ", Course Name: " + course.getCourseName() + "\n      Class Size: " + course.getClassSize() + ", Currently Enrolled: " + course.getNumberEnrolled() + "\n      Course Detail: " + course.getCourseSummary() + "\n      Course Dates: " + course.getCourseDates() + "\n");
			}
		}
		
		try {
			String courseId = sc.next();
			Course course = findCourse(courseId);
			//if we are unable to find the course based on course id, re-enter course id.
			if(course == null) {
				System.out.println("You entered an invalid course number, please try again");
				register();
			}
		//if we found the course, try to register
			else {
				if(course.enrollStudent(currentStudent)) {
					currentStudent.registerCourse(course.getCourseID());
					System.out.println("Congtratulations! " + currentStudent.getFirstName() + " " + currentStudent.getLastName() + ", you have successfully registered for: " + course.getCourseName());
					//update text files (write to output)
					updateFiles();
					mainMenu();
				}
				else {
					System.out.println("You are already registered for this course or the max number of students have already enrolled");
					mainMenu();
				}
			}
		}
		catch(Exception e) {
			//TODO update this message;
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
			System.out.println("Course Id: " + course.getCourseID() + ", Course Name: " + course.getCourseName() + "\n      Class Size: " + course.getClassSize() + ", Currently Enrolled: " + course.getNumberEnrolled() + "\n      Course Detail: " + course.getCourseSummary() + "\n      Course Dates: " + course.getCourseDates() + "\n");
		}
		System.out.println();
		System.out.println("To register for a course enter R.");
		System.out.println("To return to the main menu enter M.");
		System.out.println();
		try {
			String choice = sc.next();
			switch(choice.toUpperCase()) {
			case "R":
				register();
				break;
			case "M":
				mainMenu();
				break;
			default:
				System.out.println("You must enter either R, or M");
				courseListing();
			}
		}
		catch(Exception e) {
			System.out.print(e);
			System.out.println("You entered an invalid character, please enter either R, or M");
			courseListing();
		}
	}
	
	public static void mainMenu() {
		System.out.println("Welcome to the Student Registration Page!");
		System.out.println();
		System.out.println("You may chose one of three options, please enter in the number of your choice:");
		System.out.println("Enter 1 - View all available courses for registration.");
		System.out.println("Enter 2 - Register for a class (You must first Log In).");
		System.out.println("Enter 3 - View your courses & Unregister if necessary (You must first Log In).");
		System.out.println("Enter 4 - Exit out and end this program");
		
		try {
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				courseListing();
				break;
			case 2:
				register();
				break;
			case 3:
				listCourses();
				break;
			case 4:
				System.out.println("Goodbye!");
				break;
			default:
				System.out.println("You must enter either 1, 2, or 3");
				mainMenu();
			}
		}
		catch(InputMismatchException e) {
//			System.out.print(e);
			System.out.println("You entered an invalid character, please enter either 1, 2, or 3");
			mainMenu();
		}
	}

}
