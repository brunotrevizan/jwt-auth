package br.bookstore.security.dto;

import br.bookstore.model.User;

public class LoginUserDTO {

    private Long id;
    private String name;
    private String role;
    private String photo;

    public LoginUserDTO(Long id, String name, String role, String photo) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.photo = photo;
    }

    public LoginUserDTO(User user) {
        this.id = user.getIdUser();
        this.name = user.getName();
        this.role = user.getUserRole();
        this.photo = user.getPhoto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
