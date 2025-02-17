package br.bookstore.security.dto;

import br.bookstore.model.Store;

public class LoginStoreDTO {

    private Long id;
    private String name;
    private String slogan;
    private String banner;

    public LoginStoreDTO(Long id, String name, String slogan, String banner) {
        this.name = name;
        this.slogan = slogan;
        this.banner = banner;
    }

    public LoginStoreDTO(Store store) {
        this.id = store.getIdStore();
        this.name = store.getName();
        this.slogan = store.getSlogan();
        this.banner = store.getBanner();
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
}
