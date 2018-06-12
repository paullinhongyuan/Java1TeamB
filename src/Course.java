public class Course {
    private String courseID;
    private String courseName;
    private int classSize;
    
    
    Course(String id, String name, int size){
        courseID = id;
        courseName = name;
        classSize  = size;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    
    public String getCourseID() {
        return courseID;
    }
    
    public int getClassSize() {
        return classSize;
    }
   
}
