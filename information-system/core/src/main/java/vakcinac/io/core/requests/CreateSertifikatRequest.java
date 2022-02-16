package vakcinac.io.core.requests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import vakcinac.io.core.requests.helpers.SertifikatTest;

@XmlRootElement(name = "digitalni-sertifikat")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateSertifikatRequest {
	@XmlElement(name = "gradjanin-id")
    private String gradjaninId;
	
    private String sluzbenikId;
	
	@XmlElement(name = "vrsta-identifikacije")
	private Integer citizenIdentification;
	
	@XmlElement(name = "broj-testova")
	private BigInteger brojTestova;
	
	@XmlElementWrapper(name = "testovi")
	@XmlElement(name = "test")
	private List<SertifikatTest> testovi;

	public CreateSertifikatRequest() {
		testovi = new ArrayList<SertifikatTest>();
	}

	public String getGradjaninId() {
		return gradjaninId;
	}

	public void setGradjaninId(String gradjaninId) {
		this.gradjaninId = gradjaninId;
	}
	
	public String getSluzbenikId() {
		return sluzbenikId;
	}

	public void setSluzbenikId(String sluzbenikId) {
		this.sluzbenikId = sluzbenikId;
	}

	public Integer getCitizenIdentification() {
		return citizenIdentification;
	}

	public void setCitizenIdentification(Integer citizenIdentification) {
		this.citizenIdentification = citizenIdentification;
	}

	public BigInteger getBrojTestova() {
		return brojTestova;
	}

	public void setBrojTestova(BigInteger brojTestova) {
		this.brojTestova = brojTestova;
	}

	public List<SertifikatTest> getTestovi() {
		return testovi;
	}

	public void setTestovi(List<SertifikatTest> testovi) {
		this.testovi = testovi;
	}
}
