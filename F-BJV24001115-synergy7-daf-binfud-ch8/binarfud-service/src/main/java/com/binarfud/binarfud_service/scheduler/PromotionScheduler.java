package com.binarfud.binarfud_service.scheduler;

import com.binarfud.binarfud_service.fcm.dto.NotificationRequest;
import com.binarfud.binarfud_service.fcm.service.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class PromotionScheduler {
    @Autowired
    FCMService fcmService;

    @Scheduled(cron = "0 0 12 * * *")
    public void cronJob() throws ExecutionException, InterruptedException {
        NotificationRequest request = new NotificationRequest();
        request.setTitle("Promo Siang Binarfud");
        request.setBody("Silakan order pada Pukul 12.00 - 13.00 untuk mendapatakan diskon 20%");
        request.setToken("dDJfJShadd5NJOuHl_aAGX:APA91bH-A7GNACvHRZN_ADK61ilJXLOmDhSOTfWyWKevhGkRAKnipaLJkdiNjbF4n52wG3c8vJjeGDPtxFxD6isRWiEM70EF5aL7EBa0j6adhvCbN3aXQBqSMEyDWvMsnohrNjA7ghtE");
        System.out.println("Sending...");
        fcmService.sendMessageToToken(request);
    }
}
