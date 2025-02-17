package br.bookstore.model;

import br.bookstore.dto.UserDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "user_bookstore")
@NamedQuery(name = User.GET_USER_BY_CREDENTIALS,
        query = " SELECT u FROM User u " +
                " WHERE u.username = :username" +
                " AND u.password = :password")
@NamedQuery(name = User.GET_USER_BY_NAME,
        query = " SELECT u FROM User u " +
                " WHERE u.name = :name")
@NamedQuery(name = User.GET_USER_BY_USERNAME,
        query = " SELECT u FROM User u " +
                " WHERE u.username = :username")
public class User {

    public static final String GET_USER_BY_CREDENTIALS = "GET_USER_BY_CREDENTIALS";
    public static final String GET_USER_BY_NAME = "GET_USER_BY_NAME";
    public static final String GET_USER_BY_USERNAME = "GET_USER_BY_USERNAME";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true)
    private String name;
    //image base64
    @Column
    private String photo;
    @Column
    private String username;
    @Column
    private String password;

    @Column(name = "user_role")
    private String userRole;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    public User() {
    }

    public User(Long id, String name, String photo, String username, String password, String userRole) {
        this.idUser = id;
        this.name = name;
        this.photo = photo;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.photo = userDTO.getPhoto();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.userRole = userDTO.getRole();
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void updateFields(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.photo = userDTO.getPhoto();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.userRole = userDTO.getRole();
    }
}
