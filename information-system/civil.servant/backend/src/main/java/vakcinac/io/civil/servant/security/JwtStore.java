package vakcinac.io.civil.servant.security;

import org.springframework.stereotype.Component;

@Component
public class JwtStore {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
