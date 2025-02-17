package br.bookstore.service;

import br.bookstore.dto.StoreDTO;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Store;
import br.bookstore.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.exception.DataException;

@ApplicationScoped
public class StoreService {

    EntityManager entityManager;

    @Inject
    public StoreService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createStore(StoreDTO storeDTO) throws BookStoreException {
        try {
            validateNameAlreadyExists(storeDTO.getUserDTO().getName());
            validateUsernameAlreadyExists(storeDTO.getUserDTO().getUsername());
            Store store = new Store(storeDTO);
            User admin = new User(storeDTO.getUserDTO());
            admin.setStore(store);
            admin.setUserRole("Admin");
            entityManager.persist(store);
            entityManager.persist(admin);
            entityManager.flush();
        } catch (DataException e) {
            throw new BookStoreException("A imagem enviada em base64 é muito grande, tente com uma imagem menor.");
        }
    }

    private void validateNameAlreadyExists(String name) throws BookStoreException {
        try {
            User userByName = (User) entityManager.createNamedQuery(User.GET_USER_BY_NAME).setParameter("name", name).getSingleResult();
            if (userByName != null)
                throw new BookStoreException("Já existe um usuário cadastrado com esse nome: " + name);
        } catch (NoResultException e) {
            System.out.println("Nenhum usuário encontrado com esse nome: " + name);
        }
    }

    private void validateUsernameAlreadyExists(String username) throws BookStoreException {
        try {
            User userByUsername = (User) entityManager.createNamedQuery(User.GET_USER_BY_USERNAME).setParameter("username", username).getSingleResult();
            if (userByUsername != null)
                throw new BookStoreException("Já existe um usuário cadastrado com esse username: " + username);
        } catch (NoResultException e) {
            System.out.println("Nenhum usuário encontrado com esse username: " + username);
        }
    }

    @Transactional
    public void editStore(StoreDTO storeDTO) throws BookStoreException {
        Store store = entityManager.find(Store.class, storeDTO.getIdStore());
        if (store != null) {
            store.updateFields(storeDTO);
            entityManager.merge(store);
            entityManager.flush();
        } else {
            throw new BookStoreException("A loja não existe");
        }
    }

    public StoreDTO getStore(Long idStore) {
        Store store = entityManager.find(Store.class, idStore);
        return store != null ? new StoreDTO(store) : null;
    }
}
