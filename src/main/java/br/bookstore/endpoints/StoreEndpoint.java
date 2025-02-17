package br.bookstore.endpoints;

import br.bookstore.dto.StoreDTO;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Message;
import br.bookstore.security.dto.AuthenticatedUserDTO;
import br.bookstore.security.dto.LoginDTO;
import br.bookstore.service.AuthService;
import br.bookstore.service.BookService;
import br.bookstore.service.StoreService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/store")
public class StoreEndpoint {

    private StoreService storeService;

    private BookService bookService;

    private AuthService authService;

    @Inject
    public StoreEndpoint(StoreService storeService, BookService bookService, AuthService authService) {
        this.storeService = storeService;
        this.bookService = bookService;
        this.authService = authService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(StoreDTO storeDTO) {
        try {
            storeService.createStore(storeDTO);
            AuthenticatedUserDTO authenticatedUser = authService.authenticateUser(new LoginDTO(storeDTO.getUserDTO()));
            return Response.ok(authenticatedUser).build();
        } catch (BookStoreException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("{idStore:[0-9]+}")
    @RolesAllowed({"Employee", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("idStore") Long idStore, StoreDTO storeDTO) {
        try {
            storeDTO.setIdStore(idStore);
            storeService.editStore(storeDTO);
            return Response.ok(new Message("Loja editada com sucesso")).build();
        } catch (BookStoreException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{idStore:[0-9]+}")
    @RolesAllowed({"Employee", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("idStore") Long idStore) {
        StoreDTO storeDTO = storeService.getStore(idStore);
        return storeDTO != null ? Response.ok(storeDTO).build() : Response.status(404).build();
    }

}