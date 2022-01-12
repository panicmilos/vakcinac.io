package vakcinac.io.citizen.utils.adapters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String date) throws Exception {
    	if (date == null || date.isBlank()) {
    		return null;
    	}
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        return LocalDate.parse(date, formatter);
    }

    @Override
    public String marshal(LocalDate date) throws Exception {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        return date.format(formatter);
    }
}
