package vakcinac.io.civil.servant.factories;

import java.math.BigInteger;

import vakcinac.io.civil.servant.models.stanj.StanjeVakcina;

public class StanjeVakcineFactory {
	public static StanjeVakcina.StanjeVakcine create(String vaccineId, int available, int reserved) {
		StanjeVakcina.StanjeVakcine stanjeVakcine = new StanjeVakcina.StanjeVakcine();
		
		stanjeVakcine.setVakcina(vaccineId);
		stanjeVakcine.setDostupno(BigInteger.valueOf(available));
		stanjeVakcine.setRezervisano(BigInteger.valueOf(reserved));
		
		return stanjeVakcine;
	}
}
