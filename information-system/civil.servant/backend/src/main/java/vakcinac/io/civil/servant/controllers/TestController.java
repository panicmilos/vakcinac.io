package vakcinac.io.civil.servant.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping(path="proxy-test")
    public ResponseEntity<String> testCoreString() {
        return ResponseEntity.ok("Benny the Butcher");
    }

}
