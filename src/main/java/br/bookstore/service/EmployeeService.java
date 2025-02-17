package br.bookstore.service;

import br.bookstore.dto.EmployeeDTO;
import br.bookstore.dto.UserDTO;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Store;
import br.bookstore.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.exception.DataException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EmployeeService {

    EntityManager entityManager;

    @Inject
    public EmployeeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createEmployee(Long idStore, UserDTO employee) throws BookStoreException {
        try {
            validateNameAlreadyExists(employee.getName());
            validateUsernameAlreadyExists(employee.getUsername());
            User user = new User(employee);
            user.setStore(entityManager.find(Store.class, idStore));
            entityManager.persist(user);
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
    public void editEmployee(Long idUser, UserDTO employee) throws BookStoreException {
        User user = entityManager.find(User.class, idUser);
        if (user != null) {
            if (employee.getRole() == null) employee.setRole("Employee");
            user.updateFields(employee);
            entityManager.merge(user);
            entityManager.flush();
        } else {
            throw new BookStoreException("Usuário não existe.");
        }
    }

    @Transactional
    public void deleteEmployee(Long idUser) throws BookStoreException {
        User user = entityManager.find(User.class, idUser);
        if (user != null) {
            entityManager.remove(user);
            entityManager.flush();
        } else {
            throw new BookStoreException("Usuário não existe.");
        }
    }

    public List<EmployeeDTO> getEmployees(Long idStore) throws BookStoreException {
        if (storeExists(idStore)) {
            List<User> employees = entityManager.createNamedQuery(Store.GET_EMPLOYEES).setParameter("idStore", idStore).getResultList();
            return employees != null ? employees.stream().map(EmployeeDTO::new).toList() : new ArrayList<>();
        }
        throw new BookStoreException("Loja não existe.");
    }

    private boolean storeExists(Long idStore) {
        return entityManager.find(Store.class, idStore) != null;
    }
}
