package com.example.test.test.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.test.test.model.Course;
import com.example.test.test.model.Userdetails;
import com.example.test.test.service.CourseService;
import com.example.test.test.service.EnrolledCourseService;
import com.example.test.test.service.UserdetailsService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController
 {  @Autowired
    private EnrolledCourseService enrolledCourseService;
    @Autowired
    private UserdetailsService userdetailsService;
    @Autowired
    private CourseService courseService;
    @PostMapping("/loginHandler/login")
    public String employeeLogin(  @ModelAttribute Userdetails userdetails, 
        RedirectAttributes redirectAttributes,  HttpSession session) 
    {
    System.out.println("Login attempt with details: " + userdetails);
    // Validate credentials using the service
    String validationMessage = userdetailsService.validateEmail(userdetails.getEmail(), userdetails.getPassword());
    if (validationMessage != null) {
        // If validation fails, redirect back to login with an error message
        redirectAttributes.addFlashAttribute("errorMessage", validationMessage);
        return "redirect:/login";
    } 
    else 
    {      
    Integer userId = userdetailsService.findUserIdByEmail(userdetails.getEmail());
    if (userId == null) 
    {
        redirectAttributes.addFlashAttribute("errorMessage", "User ID not found for the provided email.");        // If validation succeeds, set session attribute and redirect to home page
    }
    else
    {
        System.out.println(userId+" user id found login controller");
        session.setAttribute("userID", userId);
    } 
        session.setAttribute("isAuthenticated", true);
        session.setAttribute("userEmail", userdetails.getEmail()); // Store email in session
        System.out.println("Login succeeded for email: " + userdetails.getEmail() + ". Redirecting to home page.");
        return "redirect:/home";
    }
    }
    @GetMapping("/home")
    public String homePage(HttpSession session,Model model) {
    Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
    if (isAuthenticated == null || !isAuthenticated) 
    {
        // Redirect to login if user is not authenticated
        return "redirect:/login";
    }
    // Proceed to home page if authenticated
    else
    {    Integer userId = (Integer) session.getAttribute("userID");
        // Fetch all courses from the service and add to the model
         List<Course> courses = courseService.getAllCourses();
          // Fetch enrolled course details
          List<Map<String, Object>> enrolledCourses = enrolledCourseService.getEnrolledCourseDetails(userId);
          // Extract enrolled course IDs
          List<Integer> enrolledCourseIds = enrolledCourses.stream()
                                                           .map(course -> (Integer) course.get("courseId"))
                                                           .toList();
  
        model.addAttribute("enrolledCourseIds", enrolledCourseIds);
        model.addAttribute("courses", courses);
        return "home";
    }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the current session
        session.invalidate();
        // Redirect to the logout page
        return "redirect:/login";
    }

}

