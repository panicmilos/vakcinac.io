package vakcinac.io.citizen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vakcinac.io.citizen.service.ExistSearchService;
import vakcinac.io.core.results.doc.QueryDocumentsResult;

@Controller
@CrossOrigin("*")
@RequestMapping("/documents/search")
public class SearchController {
	
	private ExistSearchService existSearchService;
	
	@Autowired
	public SearchController(ExistSearchService existSearchService) {
		this.existSearchService = existSearchService;
	}
	
	@PreAuthorize("hasAnyRole('Sluzbenik')")
	@GetMapping("")
	public ResponseEntity<QueryDocumentsResult> search(@RequestParam() String query) throws Exception {
		
		return ResponseEntity.ok(existSearchService.search(query));
	}

}
