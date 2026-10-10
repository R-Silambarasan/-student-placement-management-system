package com.placement.schedular;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.placement.entity.Offer;
import com.placement.enums.OfferStatus;
import com.placement.repository.OfferRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OfferExpiryScheduler {

    private final OfferRepository offerRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    @Transactional
    public void expireUnrepliedOffers() {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        List<Offer> expiredOffers =
                offerRepository.findByStatusAndResponseDeadlineBefore(
                        OfferStatus.RELEASED,
                        today
                );

        for (Offer offer : expiredOffers) {
            offer.setStatus(OfferStatus.EXPIRED);
        }

        offerRepository.saveAll(expiredOffers);

        System.out.println(
                "Unreplied offers expired: " + expiredOffers.size()
        );
    }
}