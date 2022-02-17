package vakcinac.io.core.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "search-results")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResult {

    @XmlElement(name = "result")
    List<String> results;

    public SearchResult() {
        results = new ArrayList<>();
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
