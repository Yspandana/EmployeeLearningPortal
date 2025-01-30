package com.example.test.test.controller;
import com.example.test.test.service.EnrolledCourseService;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EnrolledCourseController 
{
    @Autowired
    private EnrolledCourseService enrolledCourseService;
    //enroll a new course 
    @PostMapping("/enroll/{courseId}")
    public  ResponseEntity<Map<String, String>> enrollCourse(@PathVariable int courseId, @RequestParam int userId)
    {
    boolean status = enrolledCourseService.enrollCourse(courseId, userId);
    Map<String, String> response = new HashMap<>();
    response.put("status", status ? "success" : "failure");
    return ResponseEntity.ok(response);
    }
    //view enrolled courses for a user 
    @GetMapping("/enrolled-courses")
    public String getEnrolledCourses(HttpSession session , Model model) 
    {
        Integer userId = (Integer) session.getAttribute("userID");
        List<Map<String, Object>> enrolledCourses = enrolledCourseService.getEnrolledCourseDetails(userId);
         // Check if enrolledCourses is empty
        if (enrolledCourses.isEmpty()) {
        model.addAttribute("message", "You are not enrolled in any courses.");
        }
        model.addAttribute("enrolledCourses", enrolledCourses);
        return "enrolled-courses"; // Points to the Thymeleaf template
    }
   
  // Drop Course for a user
  @GetMapping("/drop-course")
  public ResponseEntity<Map<String, String>> dropCourse(@RequestParam("userId") int userId, @RequestParam("courseId") int courseId) {
      Map<String, String> response = new HashMap<>();
      try {
          // Log the action
          System.out.println("Attempting to drop course: " + courseId + " for user: " + userId);
          // Call service method to drop the course
          boolean isDropped = enrolledCourseService.dropCourse(userId, courseId);
          if (isDropped) {
              response.put("status", "success");
              response.put("message", "Course dropped successfully!");
              return ResponseEntity.ok(response);
          } else {
              response.put("status", "error");
              response.put("message", "Course not found for this user or already dropped.");
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
          }
      } catch (Exception e) {
          // Log the error and return error message
          e.printStackTrace();
          response.put("status", "error");
          response.put("message", "An error occurred while dropping the course.");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
  }
}
