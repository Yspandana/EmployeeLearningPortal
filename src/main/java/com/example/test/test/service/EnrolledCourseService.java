package com.example.test.test.service;
import com.example.test.test.model.Course;
import com.example.test.test.model.EnrolledCourse;
import com.example.test.test.model.Userdetails;
import com.example.test.test.repository.CourseRepository;
import com.example.test.test.repository.EnrolledCourseRepository;
import com.example.test.test.repository.UserdetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EnrolledCourseService {
    @Autowired
    private EnrolledCourseRepository enrolledCourseRepository;
    @Autowired
    private UserdetailsRepository userDetailsRepository;
    @Autowired
    private CourseRepository courseRepository;
    
    public boolean enrollCourse(int courseId, int userId) {

        boolean enrollemntStatus = false;
        // Fetch user by userId
        Userdetails user = userDetailsRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    
        // Fetch course by courseId
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
    
        // Logging for debugging (use proper logger in production)
        System.out.println("User ID: " + userId + ", Course ID: " + courseId);
    
        // Check if the user is already enrolled in the course
    if (enrolledCourseRepository.existsByUser_IdAndCourse_Id(userId, courseId)) {
        throw new IllegalStateException("User already enrolled in this course");
    }

    if (course.getCurrentCapacity() >= course.getMaxCapacity()) {
        throw new IllegalStateException("Course is full");
    }

        // Increment capacity and save the enrollment
        course.setCurrentCapacity(course.getCurrentCapacity() + 1);
        courseRepository.save(course);
        // If not enrolled, create a new enrollment record
        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setCourse(course); // Set course reference
        enrolledCourse.setUser(user); // Set user reference
        enrolledCourse.setEnrollmentDate(LocalDate.now()); // Set today's date
        enrolledCourse.setStatus("Active"); // Set initial status
        // Save the enrollment record to the repository
        enrolledCourseRepository.save(enrolledCourse);
        enrollemntStatus = true;
        return enrollemntStatus;
    }    
    public List<Map<String, Object>> getEnrolledCourseDetails(int userId) {
        return enrolledCourseRepository.findEnrolledCourseDetailsByUserId(userId);
    }

  //drop course
  public boolean dropCourse(int userId, int courseId) {
    // Fetch course by courseId
    Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
    boolean enrolledCourse = enrolledCourseRepository.existsByUser_IdAndCourse_Id(userId, courseId);
    if (enrolledCourse ) {
        //decrement course_capacity
        course.setCurrentCapacity(course.getCurrentCapacity()-1);
        enrolledCourseRepository.deleteByUser_IdAndCourse_Id(userId, courseId);
        return true; // Successfully dropped the course
    } else {
        return false; // User was not enrolled in the course
    }
}

}