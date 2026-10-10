package com.placement.schedular;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.placement.entity.JobDrive;
import com.placement.enums.JobDriveStatus;
import com.placement.repository.JobDriveRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobDriveReminderScheduler {

    private final JobDriveRepository jobDriveRepository;

     @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Kolkata")
     public void sendUpcomingDriveReminders() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDate endDate = today.plusDays(3);

        List<JobDrive> upcomingDrives =
                jobDriveRepository.findByDriveDateBetweenAndStatus(
                        today,
                        endDate,
                        JobDriveStatus.OPEN
                );

        if (upcomingDrives.isEmpty()) {
            System.out.println("No upcoming placement drives.");
            return;
        }

        for (JobDrive drive : upcomingDrives) {
            System.out.println("--------------------------------");
            System.out.println("UPCOMING PLACEMENT DRIVE REMINDER");
            System.out.println("Company: " + drive.getCompany().getName());
            System.out.println("Job Title: " + drive.getJobTitle());
            System.out.println("Drive Date: " + drive.getDriveDate());
            System.out.println("Application Deadline: "
                    + drive.getApplicationDeadline());
            System.out.println("--------------------------------");
        }
    }
}