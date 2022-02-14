package vakcinac.io.core.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HandleableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected final HttpStatus code;
	protected final Object payload;

	protected HandleableException(HttpStatus code) {
		this("", null, code);
	}
	
	protected HandleableException(String message, HttpStatus code) {
		this(message, null, code);
	}

	protected HandleableException(String message, Object payload, HttpStatus code) {
		super(message);
		this.payload = payload;
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public HttpStatus getCode() {
		return code;
	}

	public Object getPayload() {
		return payload;
	}
}
