package com.example.test.test.repository;

import com.example.test.test.model.EnrolledCourse;
import com.example.test.test.model.Userdetails;
import jakarta.transaction.Transactional;
import com.example.test.test.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Integer> 
{
    List<EnrolledCourse> findByUser(Userdetails user);
    List<EnrolledCourse> findByCourse(Course course);
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +"FROM EnrolledCourse e WHERE e.user.id = :userId AND e.course.id = :courseId")
    boolean existsByUser_IdAndCourse_Id(int userId, int courseId);
    @Query("SELECT new map(e.user.id as userId, e.course.id as courseId, c.name as courseName, c.description as courseDescription, e.enrollmentDate as enrollmentDate, e.status as status) " +
    "FROM EnrolledCourse e " +
    "JOIN e.course c " +
    "JOIN e.user u " +
    "WHERE u.id = :userId")
    List<Map<String, Object>> findEnrolledCourseDetailsByUserId(int userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM EnrolledCourse ec WHERE ec.user.id = :userId AND ec.course.id = :courseId")
    void deleteByUser_IdAndCourse_Id(@Param("userId") int userId, @Param("courseId") int courseId);
}
