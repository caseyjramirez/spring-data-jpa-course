package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
    @Query("SELECT s from Student s WHERE s.age = ?1 AND s.firstName = ?2")
    List<Student> findStudentsByAgeEqualsAndFirstNameEquals(Integer age, String fistName);
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1")
    Optional<List<Student>> findStudentsByFirstName(String firstName);

// SQL NATIVE QUERY
    @Query(value = "SELECT * FROM Student WHERE first_name = :firstName AND age >= :age", nativeQuery = true)
    Optional<List<Student>> nativeQuery(@Param("firstName") String firstName, @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("DELETE Student s WHERE s.email = :email")
    int deleteStudentByEmail(@Param("email") String email);
}
