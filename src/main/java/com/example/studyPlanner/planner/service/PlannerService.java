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

    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;

    @Transactional
    public String studyStart(Long plannerId, LocalDateTime startTime) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다."));
        planner.setStudyStart(startTime);
        String parsedLocalDateTimeNow = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        plannerRepository.save(planner);

        return parsedLocalDateTimeNow;
    }

    @Transactional
    public String studyStop(Long plannerId, LocalDateTime stopTime) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다."));
        LocalDateTime startTime = planner.getStudyStart();

        if (startTime != null) {
            Duration studyDuration = Duration.between(startTime, stopTime);
            int seconds = (int) studyDuration.getSeconds();
            int currentStudyTime = planner.getStudyTime();

            planner.stopStudy(planner, currentStudyTime + seconds);
        }

        plannerRepository.save(planner);
        int studyTime = planner.getStudyTime();

        return "공부 시간: " + studyTime;
    }

    public Planner getHome(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Optional<Planner> plannerOptional = plannerRepository.findByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);

        if (plannerOptional.isPresent()) {
            return plannerOptional.get();
        } else {
            User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(userId + "번 유저가 존재하지 않습니다."));

            Planner newPlanner = new Planner();
            newPlanner.setUser(user);

            // 플래너 저장 및 리턴
            return plannerRepository.save(newPlanner);
        }
    }


    public Planner getPlanner(Long plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new EntityNotFoundException(plannerId + "번 플래너가 존재하지 않습니다."));

        return planner;
    }

    public String formatStudyTime(int studyTimeInSeconds) {
        int hours = studyTimeInSeconds / SECONDS_PER_HOUR;
        int minutes = (studyTimeInSeconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
        int seconds = studyTimeInSeconds % SECONDS_PER_MINUTE;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
