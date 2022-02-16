package vakcinac.io.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Roles {
	public static Set<String> CITIZEN_ROLES = new HashSet<>(Arrays.asList("DomaciGradjanin", "StraniGradjanin"));
	
	public static Set<String> CIVIL_ROLES = new HashSet<>(Arrays.asList("Sluzbenik", "ZdravstveniRadnik"));
}
