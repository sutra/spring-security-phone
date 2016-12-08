package org.oxerr.spring.security.phone.samples.helloworld.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RolesAllowed("ROLE_USER")
public class IndexController {

	@RequestMapping(method = GET, path = "/")
	public void getIndex(
		Principal principal,
		Authentication authentication,
		HttpServletResponse response
	) throws IOException {

		String username = ((User) authentication.getPrincipal()).getUsername();

		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setContentType("text/plain");
		response.getWriter().println("hello " + username);
		response.flushBuffer();
	}

}
