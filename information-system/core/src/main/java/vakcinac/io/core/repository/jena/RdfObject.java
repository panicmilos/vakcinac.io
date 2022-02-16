package vakcinac.io.core.repository.jena;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RIOT;

import vakcinac.io.core.utils.extractors.MetadataExtractor;

public class RdfObject implements Closeable {

	private Model model;
	
	public RdfObject(Model model) {
		this.model = model;
	}
	
	public static RdfObject from(String xml) {
		MetadataExtractor extractor = new MetadataExtractor();
		
		byte[] rdf = extractor.extract(xml);
		
		Model model = ModelFactory.createDefaultModel();
		model.read(new ByteArrayInputStream(rdf), "");
				
		return new RdfObject(model);
	}
	
	public static RdfObject from(Model model) {				
		return new RdfObject(model);
	}
	
	public String toString(String rdfFormat) {
		RIOT.init();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, rdfFormat);
		
		System.out.println(new String(out.toByteArray()));
		
		return new String(out.toByteArray());
	}

	@Override
	public void close() throws IOException {
		model.close();
	}
}
