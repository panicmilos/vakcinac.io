package vakcinac.io.core.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HttpUtils {
    public static HttpEntity<?> configureHeader(String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        return entity;
    }

    public static HttpEntity<?> configureHeaderWithBody(Object body, String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        return entity;
    }
}
