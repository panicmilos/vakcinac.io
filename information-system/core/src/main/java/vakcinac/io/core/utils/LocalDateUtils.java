package vakcinac.io.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.datatype.XMLGregorianCalendar;

public class LocalDateUtils {

	private static final DateTimeFormatter xmlDateStringFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static LocalDate from(XMLGregorianCalendar xmlGregorianCalendar) {
		return LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(),
				xmlGregorianCalendar.getDay());
	}

	public static String toXMLDateString(LocalDate date) {
		return xmlDateStringFormatter.format(date);
	}
}
