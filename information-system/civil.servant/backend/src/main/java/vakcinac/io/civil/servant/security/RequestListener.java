package vakcinac.io.civil.servant.security;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@WebListener
public class RequestListener extends RequestContextListener {
}
