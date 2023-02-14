package com.example.demo;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Student")
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(name = "student_email_unique", columnNames = "email")
})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String lastName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name="age", nullable = false)
    private Integer age;
}
