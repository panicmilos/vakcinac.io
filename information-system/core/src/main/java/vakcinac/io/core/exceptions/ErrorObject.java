package vakcinac.io.core.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;

@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorObject {
	
	@XmlElement
	private String message;
	
	@XmlElement
	private Object payload;
	
	@XmlElement
	private HttpStatus code;

	@XmlElement
	private ZonedDateTime timestamp;
	
	public ErrorObject() {}

	public ErrorObject(String message, Object payload, HttpStatus code) {
		this.message = message;
		this.payload = payload;
		this.code = code;
		this.timestamp = ZonedDateTime.now(ZoneId.of("Z"));
	}

	public ErrorObject(String message, HttpStatus code) {
		this(message, null, code);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
