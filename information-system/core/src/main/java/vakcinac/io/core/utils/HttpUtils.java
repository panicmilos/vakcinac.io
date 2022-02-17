package vakcinac.io.core.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpUtils {
    public static HttpEntity<?> configureHeader(String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        headers.setContentType(MediaType.APPLICATION_XML);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return entity;
    }

    public static HttpEntity<?> configureHeaderWithBody(Object body, String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        return entity;
    }
}
