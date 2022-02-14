package vakcinac.io.core.utils;

import java.time.LocalDate;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {
	public static boolean isValidDateOfBirth(XMLGregorianCalendar date) {
		if (date == null) {
			return false;
		}
		
		LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
		if (!localDate.isBefore(LocalDate.now())) {
			return false;
		}
		
		return true;
	}
}
