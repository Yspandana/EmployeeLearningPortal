package com.example.test.test.service;
import org.springframework.stereotype.Service;

import com.example.test.test.model.Course;
import com.example.test.test.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
        public List<Course> getAllCourses() 
        {
        List<Course> courses = courseRepository.findAllSorted(); 
          return courses; 
        }
        public Optional<Course> getCourseById(Integer id) {
            return courseRepository.findById(id);
        }

        public String validateCoursecapacity(int courseId)
        {
        //Max capacity check
        // Fetch course by courseId
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        if(course.getMaxCapacity()>course.getCurrentCapacity())
        {
            return "No seats avaiable for the course at the moment";
        }
    
        return null;
    }
}

