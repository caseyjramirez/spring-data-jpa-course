package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enrollment {

    @EmbeddedId
    private EnrollementId id;

    @ManyToOne()
    @MapsId("studentId")
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "entrollment_student_id_fk")
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(
            name = "course_id",
            foreignKey = @ForeignKey(name = "entrollment_course_id_fk")
    )
    private Course course;


    @Column(
            nullable = false
//            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private Instant createdAt;



}
