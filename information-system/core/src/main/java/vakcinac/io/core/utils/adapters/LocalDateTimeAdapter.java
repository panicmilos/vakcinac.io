package vakcinac.io.core.utils.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    @Override
    public LocalDateTime unmarshal(String dateTime) throws Exception {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

        return LocalDateTime.parse(dateTime, formatter);
    }

    @Override
    public String marshal(LocalDateTime dateTime) throws Exception {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");

        return dateTime.format(formatter);
    }
}
