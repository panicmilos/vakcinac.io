package vakcinac.io.core.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	private final String role;
	
	public Authority() {
		this.role = "";
	}
	
	public Authority(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
