package com.example.demo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(
                name = "student_email_unique",
                columnNames = "email"
        )
})

@Getter
@Setter
//@ToString
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

    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true
    )
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.ALL, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if(!this.books.contains(book)) {
            this.books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if(this.books.contains(book)) {
            this.books.remove(book);
            book.setStudent(null);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public Student(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
