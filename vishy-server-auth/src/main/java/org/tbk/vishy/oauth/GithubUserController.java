package org.tbk.vishy.oauth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

/**
 * Created by void on 18.10.15.
 */
@Controller
public class GithubUserController {

    @RequestMapping("/github/user")
    public ResponseEntity<Object> githubUser() {
        final Subject subject = SecurityUtils.getSubject();

        final Object principal = subject.getPrincipal();

        return ResponseEntity.ok(principal);
    }
}
