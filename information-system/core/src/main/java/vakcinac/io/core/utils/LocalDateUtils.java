package vakcinac.io.core.utils;

import java.time.LocalDate;

import javax.xml.datatype.XMLGregorianCalendar;

public class LocalDateUtils {

	public static LocalDate from(XMLGregorianCalendar xmlGregorianCalendar) {
		return LocalDate.of(xmlGregorianCalendar.getYear(), xmlGregorianCalendar.getMonth(),
				xmlGregorianCalendar.getDay());
	}
}
