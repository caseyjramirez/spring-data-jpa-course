package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {

            Student casey = new Student("Casey", "Ramirez", "testing@email.com", 23);
            Student casey1 = new Student("Casey", "Xavier", "testing1@email.com", 23);
            Student casey2 = new Student("Casey", "Badger", "testing2@email.com", 23);
            Student casey3 = new Student("Casey", "Clairo", "testing3@email.com", 23);
            Student jonny = new Student("Jonny", "Randall", "jonny@email.com", 30);
            studentRepository.saveAll(List.of(casey, casey1, casey2, casey3, jonny));
            System.out.println("**************************");

            studentRepository
                    .findStudentByEmail("jonny@email.com")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> {System.out.println("NOT FOUND");
                    });

            studentRepository
                    .findStudentsByFirstName("Casey")
                    .ifPresentOrElse(
                            students -> {
                                students.forEach(System.out::println);
                            },
                            () -> {
                                System.out.println("Not Present");
                            }
                    );

            List<Student> query = studentRepository.findStudentsByAgeEqualsAndFirstNameEquals(30, "Jonny");
            List<Student> query1 = studentRepository.findStudentsByAgeEqualsAndFirstNameEquals(25, "Casey");
            System.out.println(query);
            System.out.println(query1);

            studentRepository.nativeQuery("Casey", 18)
                    .ifPresent(students -> students.forEach(System.out::println));

            System.out.println(studentRepository.deleteStudentByEmail("testing@email.com"));
        };
    }

}
