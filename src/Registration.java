import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Registration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Course> availableCourses = new ArrayList<Course>();
		
		System.out.println("Welcome to the Student Registration Page!");
		System.out.println();
		System.out.println("Listed below are all the courses that are available for registration this semister.");
		System.out.println();
		
		//Read course information for display from txt file
		File file = new File("Files/availableCourses.txt");
		try {

			Scanner sc = new Scanner(file);

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
		
		for(Course course : availableCourses) {
			System.out.println(course.getCourseID() + ", " + course.getCourseName());
		}
		System.out.println();
		System.out.println("Please enter in the course number only to register for a course, eg. \"101\" for the \"101 - Java Basics\" course.");
		
//		Scanner sc = new Scanner(System.in);
//		
//		String[] inputValues = sc.nextLine().split(",");
		//Test Input for valid number use switch case to search for proper course  
		//Sort Arraylist and use bubble sort to find right course.

	}
	
	
	public static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}  

}
