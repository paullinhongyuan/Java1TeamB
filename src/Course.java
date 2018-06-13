//import java.util.Comparator;
import java.util.*;

public class Course{
    private String courseID;
    private String courseName;
    private int classSize;
    private int numberEnrolled;
    private List<Student> studentList = new ArrayList<Student>();
    
    
    Course(String id, String name, int size){
        this.courseID = id;
        this.courseName = name;
        this.classSize  = size;
        this.numberEnrolled = 0;
    }
    
    public String getCourseName() {
        return this.courseName;
    }
    
    public String getCourseID() {
        return this.courseID;
    }
    
    public int getClassSize() {
        return this.classSize;
    }
    
    public int getNumberEnrolled() {
    		return this.numberEnrolled;
    }
    
    public boolean enrollStudent(Student newStudent) {
    		for(Student student : this.studentList) {
			if(student.getEmail().equals(newStudent.getEmail())){
				return false;
			}
		}
    		if(this.getNumberEnrolled() < this.getClassSize()) {
			++this.numberEnrolled;
			studentList.add(newStudent);
			return true;
    		}
    		return false;
    }
    
    public static Comparator<Course> courseNameComparator = new Comparator<Course>() {

	    	public int compare(Course c1, Course c2) {
	    	   String courseName1 = c1.getCourseName().toUpperCase();
	    	   String courseName2 = c2.getCourseName().toUpperCase();
	
	    	   //ascending order
	    	   return courseName1.compareTo(courseName2);
	        }
	 };
   
}