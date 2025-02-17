package br.bookstore.endpoints;

import br.bookstore.model.RefreshToken;
import br.bookstore.model.Token;
import br.bookstore.security.dto.AuthenticatedUserDTO;
import br.bookstore.security.dto.LoginDTO;
import br.bookstore.service.AuthService;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/v1/auth")
@RequestScoped
public class AuthenticationEndpoint {

	private AuthService authService;

	private JWTParser parser;

	@Inject
	public AuthenticationEndpoint(AuthService authService, JWTParser parser) {
		this.authService = authService;
		this.parser = parser;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(LoginDTO loginDTO) {
		try {
			AuthenticatedUserDTO authenticatedUser = authService.authenticateUser(loginDTO);
			return Response.ok(authenticatedUser).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Path("/validateToken")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateToken(RefreshToken refreshToken) {
		try {
			JsonWebToken jwt = parser.parse(refreshToken.refreshToken());
			LoginDTO loginDTO = new LoginDTO(jwt.getClaim("upn"), jwt.getClaim("password"));
			AuthenticatedUserDTO authenticatedUser = authService.authenticateUser(loginDTO);
			Token token = new Token(authenticatedUser.getToken(), authenticatedUser.getRefreshToken());
			return Response.ok(token).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}