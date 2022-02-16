package vakcinac.io.core.utils;

import java.time.LocalDate;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {
	public static boolean isValidDateOfBirth(XMLGregorianCalendar date) {
		if (date == null) {
			return false;
		}
		
		LocalDate localDate = fromXMLToLocalDate(date);
		if (!localDate.isBefore(LocalDate.now())) {
			return false;
		}
		
		return true;
	}
	
	public static LocalDate fromXMLToLocalDate(XMLGregorianCalendar date) {
		return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
	}
}
