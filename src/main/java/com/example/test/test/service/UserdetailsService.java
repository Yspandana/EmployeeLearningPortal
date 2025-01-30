package com.example.test.test.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.test.test.model.Userdetails;
import com.example.test.test.repository.UserdetailsRepository;

@Service
public class UserdetailsService {
    @Autowired
    private UserdetailsRepository userDetailsRepository;

    // Check if the employee is already registered
    public boolean isEmployeeAlreadyRegistered(Userdetails employee) {
        if (userDetailsRepository.existsByEmail(employee.getEmail())) {
            System.out.println("Employee email already exists");
            return true;
        } else {
            saveEmployeeRegistration(employee);
            System.out.println("New user and proceed with registration");
            return false;
        }
    }

    // Find user ID by user_id
    public Integer findUserId(Integer userId) {
        return userDetailsRepository.findById(userId)
                .map(Userdetails::getId)
                .orElse(null);
    }

    // Find user ID by email
    public Integer findUserIdByEmail(String email) {
        Userdetails user = userDetailsRepository.findByEmail(email);
        return user != null ? user.getId() : null;
    }
   
    // Save employee registration
    public void saveEmployeeRegistration(Userdetails employee) {
        try {
            userDetailsRepository.save(employee);
            System.out.println("Employee saved successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to save employee: " + ex.getMessage());
        }
    }
     public Optional<Userdetails> getUserdetails(int userId)
     {
        Optional<Userdetails> user =  userDetailsRepository.findById(userId);
                return user;
     }
     // Validate email and password
    public String validateEmail(String email, String password) {
        System.out.println("Validating email: " + email);

        // Fetch user details by email
        Userdetails user = userDetailsRepository.findByEmail(email);

        if (user == null) {
            return "There is no account registered with this email, please sign up!";
        }

        // Check if the passwords match
        if (!validatePassword(password, user.getPassword())) {
            return "Invalid password, please check and retry!";
        }

        return null; // Null indicates successful validation
    }

    // Password validation logic
    private boolean validatePassword(String inputPassword, String storedPassword) {
        System.out.println("Input Password: " + inputPassword + ", Stored Password: " + storedPassword);
        return inputPassword != null && inputPassword.equals(storedPassword);
    }

}

