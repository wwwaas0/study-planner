package com.example.studyPlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StudyPlannerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyPlannerProjectApplication.class, args);
    }

}
