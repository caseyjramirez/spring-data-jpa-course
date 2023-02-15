package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            int studentCount = 400;
            int page = 0;
            int itemCount = 20;

            generateRandomStudents(studentRepository, studentCount);
            Page<Student> students = sortStudentsByName(studentRepository, page, itemCount);



            students.forEach(System.out::println);
            System.out.println(students);

        };
    }


    private static Page<Student> sortStudentsByName(StudentRepository studentRepository, int page, int itemCount) {

        Sort sort = Sort.by("firstName").ascending()
                .and(Sort.by("lastName").ascending());

        PageRequest pageRequest = PageRequest.of(page, itemCount, sort);

        return studentRepository.findAll(pageRequest);
    }

    private void generateRandomStudents(StudentRepository studentRepository, int count) {
        Faker faker = new Faker();

        for(int i = 0; i < count; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int uniqueId = i + 1;

            Student newStudent = new Student(
                    firstName,
                    lastName,
                    String.format("%s.%s.%d@gmail.com", firstName, lastName, uniqueId),
                    faker.number().numberBetween(15, 35));
            studentRepository.save(newStudent);
        }
    }

}
