package org.tbk.vishy.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("{\n" +
                "  \"message\": \"Hello world\"\n" +
                "}");
    }

    @RequestMapping("/user")
    public ResponseEntity<Principal> user(Principal principal) {
        return ResponseEntity.ok(principal);
    }
}
