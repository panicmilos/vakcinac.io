package vakcinac.io.core.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "count-response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountResponse {
	
	@XmlValue
	private int value;
	
	public CountResponse() {
	}
	
	public CountResponse(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
