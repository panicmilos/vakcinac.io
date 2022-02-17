package vakcinac.io.civil.servant.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import vakcinac.io.core.exceptions.AuthorizationException;
import vakcinac.io.core.exceptions.BadLogicException;
import vakcinac.io.core.exceptions.ForbiddenException;
import vakcinac.io.core.exceptions.MissingEntityException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode().series() == Series.CLIENT_ERROR 
				|| httpResponse.getStatusCode().series() == Series.SERVER_ERROR;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		System.out.println(response.getStatusText());
		
		switch(response.getStatusCode()) {
			case UNAUTHORIZED:
				throw new AuthorizationException();
			case BAD_REQUEST:
				throw new BadLogicException();
			case FORBIDDEN:
				throw new ForbiddenException();
			case NOT_FOUND:
				throw new MissingEntityException();
		default:
			throw new BadLogicException();
		}	
	}

}
