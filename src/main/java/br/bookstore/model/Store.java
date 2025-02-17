package br.bookstore.model;

import br.bookstore.dto.StoreDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "store")
@NamedQuery(name = Store.GET_EMPLOYEES,
        query = " SELECT s.users FROM Store s " +
                " JOIN s.users u " +
                " WHERE s.idStore = :idStore AND u.userRole like 'Employee'")
public class Store {

    public static final String GET_EMPLOYEES = "GET_EMPLOYEES";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStore;

    @Column
    private String name;

    @Column
    private String slogan;

    //imagem base64
    @Column
    private String banner;

    @OneToMany(mappedBy = "store", orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<User> users;

    public Store() {
    }

    public Store(StoreDTO storeDTO) {
        this.name = storeDTO.getName();
        this.slogan = storeDTO.getSlogan();
        this.banner = storeDTO.getBanner();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setIdStore(Long id) {
        this.idStore = id;
    }

    public Long getIdStore() {
        return idStore;
    }

    public void updateFields(StoreDTO storeDTO) {
        this.name = storeDTO.getName();
        this.slogan = storeDTO.getSlogan();
        this.banner = storeDTO.getBanner();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}