package com.example.studyPlanner.planner.service;

import com.example.studyPlanner.planner.entity.Planner;
import com.example.studyPlanner.planner.repository.PlannerRepository;
import com.example.studyPlanner.user.entity.User;
import com.example.studyPlanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;

    @Transactional
    public String studyStart(Long plannerId, LocalDateTime startTime) {
        String parsedLocalDateTimeNow = "";
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        if (plannerOptional.isPresent()) {
            Planner planner = plannerOptional.get();
            planner.setStudyStart(startTime);
            parsedLocalDateTimeNow = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            plannerRepository.save(planner);
        } else {
            throw new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다.");
        }

        return parsedLocalDateTimeNow;
    }

    @Transactional
    public String studyStop(Long plannerId, LocalDateTime stopTime) {
        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);
        int studyTime = 0;

        if (plannerOptional.isPresent()) {
            Planner planner = plannerOptional.get();
            LocalDateTime startTime = planner.getStudyStart();

            if (startTime != null) {
                Duration studyDuration = Duration.between(startTime, stopTime);
                int seconds = (int) studyDuration.getSeconds();

                int currentStudyTime = planner.getStudyTime();
                planner.setStudyTime(currentStudyTime + seconds);
                planner.setStudyStart(null);
            }

            plannerRepository.save(planner);
            studyTime = planner.getStudyTime();
        } else {
            throw new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다.");
        }

        return "공부 시간: " + studyTime;
    }

    public Planner getHome(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Optional<Planner> plannerOptional = plannerRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);

        if (plannerOptional.isPresent()) {
            return plannerOptional.get();
        } else {
            // 새로운 플래너 생성
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Planner newPlanner = new Planner();
                newPlanner.setUser(user);

                // 플래너 저장 및 리턴
                return plannerRepository.save(newPlanner);
            } else {
                throw new EntityNotFoundException(userId + "번 유저가 존재하지 않습니다.");
            }


        }
    }

    public Planner getPlanner(Long plannerId) {

        Optional<Planner> plannerOptional = plannerRepository.findById(plannerId);

        if (plannerOptional.isPresent()) {
            return plannerOptional.get();
        } else {
            throw new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다.");
        }
    }

    public String formatStudyTime(int studyTimeInSeconds) {
        int hours = studyTimeInSeconds / 3600;
        int minutes = (studyTimeInSeconds % 3600) / 60;
        int seconds = studyTimeInSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
