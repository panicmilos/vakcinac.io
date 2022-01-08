package vakcinac.io.citizen.utils.transformers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import net.sf.saxon.TransformerFactoryImpl;
import vakcinac.io.citizen.Constants;
import vakcinac.io.citizen.utils.parsers.JaxBParser;
import vakcinac.io.citizen.utils.parsers.JaxBParserFactory;
import vakcinac.io.citizen.utils.registries.XslFoRegistry;

public class PDFTransformer {
	private FopFactory fopFactory;
	private TransformerFactory transformerFactory;
	private XslFoRegistry xslFoRegistry;
	
	public PDFTransformer() {		
		try {
			fopFactory = FopFactory.newInstance(new File(Constants.ROOT_RESOURCE + "/fop.xconf"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		transformerFactory = new TransformerFactoryImpl();
		xslFoRegistry = new XslFoRegistry();
	}
	
	public byte[] generate(Object obj) {
		try {
			String serializedObj = serializeSource(obj);
	
			StreamSource transformSource = new StreamSource(new File(findXslPathFor(obj)));
			Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
	
			FOUserAgent userAgent = fopFactory.newFOUserAgent();
			ByteArrayOutputStream pdfResult = new ByteArrayOutputStream();
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, pdfResult);
	
			StreamSource source = new StreamSource(new ByteArrayInputStream(serializedObj.getBytes()));
			Result res = new SAXResult(fop.getDefaultHandler());
			xslFoTransformer.transform(source, res);
	
			return pdfResult.toByteArray();
		}
		catch (TransformerException | FOPException  e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String serializeSource(Object obj) {
		JaxBParser parser = JaxBParserFactory.newInstanceFor(obj.getClass());
		
		return parser.marshall(obj);
	}
	
	private String findXslPathFor(Object obj) {
		Class<?> objClass = obj.getClass();
		
		return Constants.ROOT_RESOURCE + xslFoRegistry.getPathFor(objClass);
	}


}
