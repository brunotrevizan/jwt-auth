package br.bookstore.security.dto;

import br.bookstore.dto.UserDTO;

public class LoginDTO {
    private String user;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public LoginDTO(UserDTO userDTO) {
        this.user = userDTO.getUsername();
        this.password = userDTO.getPassword();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}