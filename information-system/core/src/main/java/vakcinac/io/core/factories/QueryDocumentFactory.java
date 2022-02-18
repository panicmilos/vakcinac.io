package vakcinac.io.core.factories;

import vakcinac.io.core.results.doc.QueryDocumentsResult.Document;

public class QueryDocumentFactory {
	public static Document create(Document other) {
		
		other.setType(findType(other.getUrl()));
		other.setId(findId(other.getUrl()));
		
		return other;
		
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
    	
    	if (link.contains("izvestaj")) {
    		return "Izvestaj";
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
		
		if (link.contains("izvestaj")) {
			return link.split("izvestaj")[1].substring(1);
		}
		
		return "";
	}
}
