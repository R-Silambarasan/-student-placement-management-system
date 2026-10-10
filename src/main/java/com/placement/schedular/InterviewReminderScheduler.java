package com.placement.schedular;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.placement.entity.Interview;
import com.placement.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InterviewReminderScheduler {

    private final InterviewRepository interviewRepository;

    
    @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Kolkata")
    public void sendInterviewReminders() {

        LocalDate tomorrow =
                LocalDate.now(ZoneId.of("Asia/Kolkata")).plusDays(1);

        List<Interview> interviews =
                interviewRepository.findByInterviewDate(tomorrow);

        if (interviews.isEmpty()) {
            System.out.println("No interviews scheduled for tomorrow.");
            return;
        }

        for (Interview interview : interviews) {
            System.out.println("--------------------------------");
            System.out.println("INTERVIEW REMINDER");
            System.out.println("Interview ID: " + interview.getId());
            System.out.println("Round: " + interview.getRound());
            System.out.println("Interview Date: "
                    + interview.getInterviewDate());
            System.out.println("Interviewer: "
                    + interview.getInterviewer());
            System.out.println("Application ID: "
                    + interview.getApplication().getId());
            System.out.println("--------------------------------");
        }
    }
}