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
}
