package vakcinac.io.core.utils;

import java.time.LocalDate;

import javax.xml.datatype.XMLGregorianCalendar;

public class LocalDateUtils {

	public static LocalDate from(XMLGregorianCalendar xmlGregorianCalendar) {
		return LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(),
				xmlGregorianCalendar.getDay());
	}
	
	public static LocalDate max(LocalDate first, LocalDate second) {
		if (first.isAfter(second)) {
			return first;
		}
		
		return second;
	}
}
