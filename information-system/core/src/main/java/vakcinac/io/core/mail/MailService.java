package vakcinac.io.core.mail;

import org.springframework.web.client.RestTemplate;

public class MailService {
    public void SendMail(String mailServiceUrl, MailContent mail) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(String.format("%s/", mailServiceUrl), mail, Object.class);
    }
}
