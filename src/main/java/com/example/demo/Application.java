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
import java.util.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository, BookRepository bookRepository, CourseRepository courseRepository) {
        return args -> {
            Faker faker = new Faker();

            int studentCount = 100;
            int bookCount = 400;
            int courseCount = 20;
            int enrollmentCount = 350;
            int page = 0;
            int itemCount = 20;
            Sort sort = Sort.by("firstName").ascending().and(Sort.by("lastName").ascending());


            generateNewStudents(studentIdCardRepository, studentCount);
//            generateNewBooks(bookRepository, studentRepository, bookCount, studentCount);
            generateCourses(courseRepository, courseCount);
//            generateEnrollments(studentRepository, courseRepository, studentCount, courseCount, enrollmentCount);





            System.out.println("***************************************");
            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println(s);
                List<Book> books = s.getBooks();
                books.forEach(System.out::println);
            });


        };
    }


    private static Page<Student> sortStudents(StudentRepository studentRepository, int page, int itemCount, Sort sort) {

        PageRequest pageRequest = PageRequest.of(page, itemCount, sort);

        return studentRepository.findAll(pageRequest);
    }

    private void generateNewStudents(StudentIdCardRepository studentIdCardRepository, int count) {
        Faker faker = new Faker();
        Random random = new Random();

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

            for (int j = 0; j < random.nextInt(20); j++) {
                student.addBook(new Book(
                        faker.book().title(),
                        Instant.now()
                ));
            }

            student.addEnrollment(new Enrollment(
                    new EnrollementId(),
                    student,
                    new Course("Class", "Department"),
                    Instant.now()
            ));


            StudentIdCard studentIdCard = new StudentIdCard(cardId, student);

            studentIdCardRepository.save(studentIdCard);
        }
    }

    private Long getRandomId(int count) {
        return 1L + (long) (Math.random() * (Long.valueOf(count) - 1L));
    }

    private void generateNewBooks(BookRepository bookRepository, StudentRepository studentRepository, int bookCount, int studentCount) {
        Faker faker = new Faker();

        for (int i = 0; i < bookCount; i++) {

            long randomId = getRandomId(studentCount);
            Student student = studentRepository.findStudentById(randomId);

            Book book = new Book(
                    faker.book().title(),
                    Instant.now(),
                    student
            );

            bookRepository.save(book);
        }
    }

    private void generateCourses(CourseRepository courseRepository, int courseCount) {
        Faker faker = new Faker();
        Random rand = new Random();

        List<String> courses = Arrays.asList("Math", "English", "Science", "CTE");

        for (int i = 0; i < courseCount; i++) {
            courseRepository.save(new Course(
                    faker.book().title(),
                    courses.get(rand.nextInt(courses.size()))
            ));

        }
    }

    private void generateEnrollments(StudentRepository studentRepository, CourseRepository courseRepository, int studentCount, int courseCount, int enrollmentCount) {

        for (int i = 0; i < enrollmentCount; i++) {
            long studentId = getRandomId(studentCount);
            long courseId = getRandomId(courseCount);

            Student student = studentRepository.findStudentById(studentId);
            Course course = courseRepository.findCourseById(courseId).get();

            System.out.println(course);
            System.out.println(student);

        }


    }


}
