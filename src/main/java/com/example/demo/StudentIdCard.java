package com.example.demo;

import lombok.*;

import javax.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_card_number_unique",
                        columnNames = "card_number"
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_id_card_sequence",
            sequenceName = "student_id_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_id_card_sequence"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    @Column(name = "card_number", nullable = true, length = 15)
    private String cardNumber;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_card_student_id_fk"
            )
    )
    private Student student;

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }
}
