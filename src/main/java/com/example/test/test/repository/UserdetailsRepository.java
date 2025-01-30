package com.example.test.test.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.test.test.model.Userdetails;


@Repository
public interface UserdetailsRepository extends JpaRepository<Userdetails,Integer> {
  // Custom method to find a user by email
  Userdetails findByEmail(String email);
  // Optional: Check if an email exists
  boolean existsByEmail(String email);
}
