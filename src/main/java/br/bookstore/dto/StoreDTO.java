package br.bookstore.dto;

import br.bookstore.model.Store;
import jakarta.json.bind.annotation.JsonbProperty;

public class StoreDTO {
    private Long idStore;
    private String name;
    private String slogan;
    private String banner;
    @JsonbProperty("admin")
    private UserDTO userDTO;

    public StoreDTO() {
    }

    public StoreDTO(Long idStore, String name, String slogan, String banner, UserDTO userDTO) {
        this.idStore = idStore;
        this.name = name;
        this.slogan = slogan;
        this.banner = banner;
        this.userDTO = userDTO;
    }

    public StoreDTO(Store store) {
        this.idStore = store.getIdStore();
        this.name = store.getName();
        this.slogan = store.getSlogan();
        this.banner = store.getBanner();
    }

    public Long getIdStore() {
        return idStore;
    }

    public void setIdStore(Long idStore) {
        this.idStore = idStore;
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
