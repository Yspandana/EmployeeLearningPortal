package com.example.test.test.controller;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.test.test.model.Userdetails;
import com.example.test.test.service.UserdetailsService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeDetailsController
{
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserdetailsService userdetailsService;
    @GetMapping("/login")
    public String login() 
    {
    return "index.html";
    }
    @RequestMapping("employee/register")
    public String register(@ModelAttribute Userdetails u, RedirectAttributes redirectAttributes) 
    {
    System.out.println("User details = " + u);
    boolean isAlreadyRegistered = userdetailsService.isEmployeeAlreadyRegistered(u);
    System.out.println("isAlreadyRegistered = " + isAlreadyRegistered);
        if (!isAlreadyRegistered) {
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login now!");
        }
        else{
            redirectAttributes.addFlashAttribute("errorMessage", "User is already registered! Please login with existing account!");
        }
        return "redirect:/login";
    }
    @RequestMapping("/")
    public void forward(HttpServletResponse res) throws IOException 
    {
        res.sendRedirect(servletContext.getContextPath() + "/login");
    }
    @RequestMapping("/SignUp")
    public String signUp()
    {
        return "SignUp.html";
    }
    @RequestMapping("/profile")
    public String profile(HttpSession session, Model model) 
    {  Integer userId = (Integer) session.getAttribute("userID");
        System.out.println("profffffffffffffffffffffffffffile");
        Optional<Userdetails> user = userdetailsService.getUserdetails(userId);      
        model.addAttribute("user", user);
        return "profile.html";
    }
    
}
