package vakcinac.io.civil.servant.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import vakcinac.io.civil.servant.security.utils.JwtUtil;
import vakcinac.io.core.Roles;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.security.User;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.header.string}")
    public String headerString;

    @Value("${jwt.token.prefix}")
    public String tokenPrefix;
    
	@Value("${gradjanin.url}")
	private String gradjaninUrl;

    private final UserDetailsService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(@Qualifier("zaposleniService") UserDetailsService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader(headerString);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(tokenPrefix)) {
            jwt = authorizationHeader.replace(tokenPrefix, "");
            try {
                username = jwtUtil.extractUsernameFromToken(jwt);
            } catch (IllegalArgumentException | SignatureException | ExpiredJwtException | MalformedJwtException ignored) {
                System.out.println(ignored.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	String role = jwtUtil.extractRoleFromToken(jwt);
            UserDetails userDetails = null;
            try {
            	if (Roles.CITIZEN_ROLES.contains(role)) {
            		userDetails = userService.loadUserByUsername(username);
            	}
            	else if (Roles.CIVIL_ROLES.contains(role)) {
            		HttpHeaders headers = new HttpHeaders();
            		headers.set("Authorization", "Bearer " + jwt); 
            		HttpEntity<?> entity = new HttpEntity<>(headers);
	        	   
            		RestTemplate restTemplate = new RestTemplate();
            		String url = String.format("%s/gradjani/korisnicko-ime/%s", gradjaninUrl, username);
            		try {
                		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
                		userDetails = response.getBody();
            		} catch (Exception e) {
            			throw new BadLogicException(String.format("Ne postoji korisnik sa zadatim korisničkim imenom.", role));
            		}
	    	       
            	}
            	else {
            		throw new BadLogicException(String.format("Ne postoji uloga %s.", role));
            	}

                if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, userDetails))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            jwtUtil.getAuthenticationToken(jwt, userDetails);
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (UsernameNotFoundException ignored) {
                System.out.println(ignored.getMessage());
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
