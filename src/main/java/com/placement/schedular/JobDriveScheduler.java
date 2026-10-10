
package com.placement.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.placement.entity.JobDrive;
import com.placement.enums.JobDriveStatus;
import com.placement.repository.JobDriveRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobDriveScheduler {

    private final JobDriveRepository jobDriveRepository;

  
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void closeExpiredJobDrives() {

        LocalDateTime currentTime = LocalDateTime.now();

        List<JobDrive> expiredDrives =
                jobDriveRepository
                    .findByApplicationDeadlineBeforeAndStatus(
                            currentTime,
                            JobDriveStatus.OPEN
                    );

        for (JobDrive jobDrive : expiredDrives) {
            jobDrive.setStatus(JobDriveStatus.CLOSED);
        }

        jobDriveRepository.saveAll(expiredDrives);

        System.out.println(
                "Expired job drives closed: " + expiredDrives.size()
        );
    }
}

