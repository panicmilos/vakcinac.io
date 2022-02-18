package vakcinac.io.civil.servant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import vakcinac.io.core.mail.MailContent;
import vakcinac.io.core.mail.MailService;

@Service
public class MailingService extends MailService {

    @Value("${mailService.url}")
    private String mailServiceUrl;

    public void Send(MailContent mail) {
        super.SendMail(mailServiceUrl, mail);
    }

}
