package vakcinac.io.civil.servant.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stock")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddVakcinaStockRequest {
	
    @XmlElement(name = "amount")
    private Integer amount;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
