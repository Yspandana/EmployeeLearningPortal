package com.example.test.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userdetails")
public class Userdetails {

public Userdetails(int id, String firstName, String lastName, String email, String password,
            String confirmPassword) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
public Userdetails() {
}
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String firstName ;
private String lastName ;
private String email; 
private String password;
private String confirmPassword;
@Override
public String toString() {
    return "Userdetails [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
            + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}
public String getFirstName() {
    return firstName;
}
public void setFirstName(String firstName) {
    this.firstName = firstName;
}
public String getLastName() {
    return lastName;
}
public void setLastName(String lastName) {
    this.lastName = lastName;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}
public String getConfirmPassword() {
    return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
}
public Userdetails orElseThrow(Object object) {
    throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
}


}
