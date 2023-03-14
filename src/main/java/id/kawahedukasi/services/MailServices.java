package id.kawahedukasi.services;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class MailServices {
    @Inject
    Mailer mailer;

    @Inject
    ExportServices exportServices;

    public void sendEmail(String email){
        mailer.send(
                Mail.withText(email,
                        "Test From CRUD API Quarkus",
                        "Hello, Ini adalah pesan Email Quarkus Pertama Item Service"));
    }
    public void sendExcelTOEmail(String email) throws IOException {
        mailer.send(
                Mail.withText(email,
                        "File Excel Item KawahEdukasi",
                        "Hello, Berikut File Excel Anda").addAttachment("list-item.xlxs", exportServices.excelItem().toByteArray()
                        , "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
