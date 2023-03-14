package id.kawahedukasi.services;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class ScheduleService {
    @Inject
    MailServices mailServices;

    Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Scheduled(every = "5s")
    public void generateItem(){
        logger.info("item_{}", LocalDateTime.now());
    }
    @Scheduled(cron = "0/1 0 0 ? 1/1 * *")
    public void scheduleSendListItemTOEmail() throws IOException {
        mailServices.sendExcelTOEmail("mqodzy@gmail.com");
        logger.info("EMAIL BERHASIL TERKIRIM");
    }
}
