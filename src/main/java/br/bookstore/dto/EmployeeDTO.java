package br.bookstore.dto;

import br.bookstore.model.User;

public class EmployeeDTO {

    private Long id;
    private String name;
    private String username;
    private String photo;

    public EmployeeDTO(Long id, String name, String username, String photo) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.photo = photo;
    }

    public EmployeeDTO(User user) {
        this.id = user.getIdUser();
        this.name = user.getName();
        this.username = user.getUsername();
        this.photo = user.getPhoto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}