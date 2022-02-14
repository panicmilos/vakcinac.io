package vakcinac.io.core.factories;

import vakcinac.io.core.models.os.Tlink;

public class TlinkFactory {
	
	public static Tlink create(String rel, String href, String typeof) {
		Tlink link = new Tlink();
		
		link.setRel(rel);
		link.setHref(href);
		link.setTypeof(typeof);
		
		return link;
	}
}
