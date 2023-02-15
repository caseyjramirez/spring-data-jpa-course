package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
//            System.out.println("********* ARGS *********" + args);

            Student casey = new Student("Casey", "Ramirez", "testing@email.com", 23);
            studentRepository.save(casey);

            List<Student> allStudents = studentRepository.findAll();
            System.out.println(allStudents);
        };
    }

}
