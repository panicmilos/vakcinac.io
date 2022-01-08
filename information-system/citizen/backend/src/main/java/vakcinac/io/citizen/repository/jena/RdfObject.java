package vakcinac.io.citizen.repository.jena;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import vakcinac.io.citizen.utils.extractors.MetadataExtractor;

public class RdfObject {

	private byte[] rdf;
	
	public RdfObject(byte[] rdf) {
		this.rdf = rdf;
	}
	
	public static RdfObject from(String xml) {
		MetadataExtractor extractor = new MetadataExtractor();
		
		byte[] rdf = extractor.extract(xml);
		
		return new RdfObject(rdf);
	}
	
	public String toString(String rdfFormat) {
		Model model = ModelFactory.createDefaultModel();
		model.read(new ByteArrayInputStream(rdf), "");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, rdfFormat);

		return new String(out.toByteArray());
	}
}
