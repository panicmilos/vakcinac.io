package vakcinac.io.core.factories;

import vakcinac.io.core.models.os.Tmeta;

public class TmetaFactory {
	
	public static Tmeta create(String property, String datatype, String value) {
		Tmeta meta = new Tmeta();
		
		meta.setProperty(property);
		meta.setDatatype(datatype);
		meta.setValue(value);
		
		return meta;
	}

}
