package vakcinac.io.citizen.utils.handlers;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

public class JaxBParsingHandler implements ValidationEventHandler {

	public boolean handleEvent(ValidationEvent event) {

		ValidationEventLocator validationEventLocator = event.getLocator();
		
		if (event.getSeverity() != ValidationEvent.WARNING) {
			System.out.format("ERROR: Line %d: Col %d: %s\n", validationEventLocator.getLineNumber(), validationEventLocator.getColumnNumber(), event.getMessage());

			return false;
		} else {
			System.out.format("WARNING: Line %d: Col %d: %s\\n", validationEventLocator.getLineNumber(), validationEventLocator.getColumnNumber(), event.getMessage());

			return true;
		}
	}

}
