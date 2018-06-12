public class Course {
    private String courseID;
    private String courseName;
    private int classSize;
    
    
    Course(String id, String name, int size){
        this.courseID = id;
        this.courseName = name;
        this.classSize  = size;
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
   
}
