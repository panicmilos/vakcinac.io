package vakcinac.io.core.factories;

import vakcinac.io.core.results.link.Links.Link;

public class LinkFactory {
	public static Link create(String url, String createdAt) {
		Link link = new Link();
		
		link.setUrl(url);
		link.setId(findId(url));
		link.setType(findType(url));
		link.setCreatedAt(createdAt);
		
		return link;
	}
	
	private static String findType(String link) {
    	if (link.contains("digitalni-sertifikat")) {
    		return "Digitalni Sertifikat";
    	}
    	
    	if (link.contains("izjava")) {
    		return "Izjava";
    	}
    	
    	if (link.contains("potvrda")) {
    		return "Potvrda";
    	}
    	
    	if (link.contains("saglasnost")) {
    		return "Saglasnost";
    	}
    	
    	if (link.contains("zahtev")) {
    		return "Zahtev";
    	}
    	
        return "";
	}
	
	private static String findId(String link) {
		if (link.contains("digitalni-sertifikat")) {
			return link.split("digitalni-sertifikat")[1].substring(1);
		}
		
		if (link.contains("izjava")) {
			return link.split("izjava")[1].substring(1);
		}
		
		if (link.contains("saglasnost")) {
			return link.split("saglasnost")[1].substring(1);
		}
		
		if (link.contains("zahtev")) {
			return link.split("zahtev")[1].substring(1);
		}
		
		if (link.contains("potvrda")) {
			return link.split("potvrda")[1].substring(1);
		}
		
		return "";
	}

}
