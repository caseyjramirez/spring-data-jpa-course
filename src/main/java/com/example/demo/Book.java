package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "book"
)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name = "book_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String bookName;

    @Column(
            name = "created_at",
            nullable = false
    )
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "book_student_id_fk"
            )
    )
    private Student student;

    public Book(String bookName, Instant createdAt, Student student) {
        this.bookName = bookName;
        this.createdAt = createdAt;
        this.student = student;
    }

    public Book(String bookName, Instant createdAt) {
        this.bookName = bookName;
        this.createdAt = createdAt;
    }
}
