package br.bookstore.service;

import br.bookstore.model.User;
import br.bookstore.security.dto.AuthenticatedUserDTO;
import br.bookstore.security.dto.LoginDTO;
import br.bookstore.security.dto.LoginStoreDTO;
import br.bookstore.security.dto.LoginUserDTO;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;

@ApplicationScoped
public class AuthService {

    EntityManager entityManager;

    @Inject
    public AuthService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String generateToken(User user) {
        return Jwt.issuer("https://api-prova-flutter.com/issuer")
                .upn(user.getUsername())
                .expiresIn(Duration.ofDays(30))
                .groups(new HashSet<>(Collections.singletonList(user.getUserRole())))
                .sign();
    }

    public String generateRefreshToken(User user) {
        return Jwt.issuer("https://api-prova-flutter.com/issuer")
                .upn(user.getUsername())
                .claim("password", user.getPassword())
                .expiresIn(Duration.ofDays(360))
                .groups(new HashSet<>(Collections.singletonList(user.getUserRole())))
                .sign();
    }

    public AuthenticatedUserDTO authenticateUser(LoginDTO loginDTO) {
        User user = validateAndGetUser(loginDTO);
        if (user != null) {
            AuthenticatedUserDTO authenticatedUser = new AuthenticatedUserDTO(generateToken(user), generateRefreshToken(user));
            authenticatedUser.setUser(new LoginUserDTO(user));
            authenticatedUser.setStore(new LoginStoreDTO(user.getStore()));
            return authenticatedUser;
        }
        return null;
    }

    private User validateAndGetUser(LoginDTO loginDTO) {
        return (User) entityManager.createNamedQuery(User.GET_USER_BY_CREDENTIALS)
                .setParameter("username", loginDTO.getUser())
                .setParameter("password", loginDTO.getPassword())
                .getSingleResult();
    }

}
