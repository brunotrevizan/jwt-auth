package br.bookstore.security.dto;

public class AuthenticatedUserDTO {

    private String token;
    private String refreshToken;
    private LoginUserDTO user;
    private LoginStoreDTO store;

    public AuthenticatedUserDTO(String token, String refreshToken, LoginUserDTO user, LoginStoreDTO store) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.store = store;
    }

    public AuthenticatedUserDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LoginUserDTO getUser() {
        return user;
    }

    public void setUser(LoginUserDTO user) {
        this.user = user;
    }

    public LoginStoreDTO getStore() {
        return store;
    }

    public void setStore(LoginStoreDTO store) {
        this.store = store;
    }
}
