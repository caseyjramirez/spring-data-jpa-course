package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository, BookRepository bookRepository) {
        return args -> {
            int studentCount = 100;
            int bookCount = 400;
            int page = 0;
            int itemCount = 20;
            Sort sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());


            generateNewStudents(studentIdCardRepository, studentCount);
            generateNewBooks(bookRepository, studentRepository, bookCount, studentCount);
            System.out.println("***************************************");

            Page<Student> studentPage = sortStudents(studentRepository, page, itemCount, sort);
            studentPage.forEach(student -> System.out.println(student.toString()));



        };
    }


    private static Page<Student> sortStudents(StudentRepository studentRepository, int page, int itemCount, Sort sort) {

        PageRequest pageRequest = PageRequest.of(page, itemCount, sort);

        return studentRepository.findAll(pageRequest);
    }

    private void generateNewStudents(StudentIdCardRepository studentIdCardRepository, int count) {
        Faker faker = new Faker();

        for(int i = 0; i < count; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int uniqueId = i + 1;
            String cardId = String.format("%1$" + 15 + "s", uniqueId).replace(' ', '0');

            Student student = new Student(
                    firstName,
                    lastName,
                    String.format("%s.%s.%d@gmail.com", firstName, lastName, uniqueId),
                    faker.number().numberBetween(15, 35)
            );

            StudentIdCard studentIdCard = new StudentIdCard(cardId, student);

            studentIdCardRepository.save(studentIdCard);
        }
    }

    private void generateNewBooks(BookRepository bookRepository, StudentRepository studentRepository, int bookCount, int studentCount) {
        Faker faker = new Faker();

        for (int i = 0; i < bookCount; i++) {

            long generatedLong = 1L + (long) (Math.random() * (Long.valueOf(studentCount) - 1L));
            Student student = studentRepository.findStudentById(generatedLong).get();

            Book book = new Book(
                    faker.book().title(),
                    Instant.now(),
                    student
            );

            bookRepository.save(book);






        }
    }


}
