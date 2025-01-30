package com.example.test.test.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.test.test.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    @Query("SELECT c FROM Course c") 
    List<Course> findAll();
    @Query("SELECT c FROM Course c ORDER BY c.Id ASC")
    List<Course> findAllSorted();
    
}
