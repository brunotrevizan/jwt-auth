package br.bookstore.endpoints;

import br.bookstore.dto.BookDTO;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Message;
import br.bookstore.service.BookService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/store/{idStore:[0-9]+}/book")
@RolesAllowed({"Employee", "Admin"})
public class BookEndpoint {

    private BookService bookService;

    @Inject
    public BookEndpoint(BookService bookService) {
        this.bookService = bookService;
    }

    @POST
    @RolesAllowed({"Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(@PathParam("idStore") Long idStore, BookDTO bookDTO) {
        try {
            BookDTO bookDTOCadastrado = bookService.createBook(bookDTO, idStore);
            return Response.ok(bookDTOCadastrado).build();
        } catch (BookStoreException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @RolesAllowed({"Employee", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBook(@PathParam("idStore") Long idStore,
                               @DefaultValue("20") @QueryParam("limit") int limit,
                               @DefaultValue("0") @QueryParam("offset") int offset,
                               @QueryParam("author") String author,
                               @QueryParam("title") String title,
                               @QueryParam("year-start") int yearStart,
                               @QueryParam("year-finish") int yearFinish,
                               @QueryParam("rating") int rating,
                               @QueryParam("available") Boolean available) {
        List<BookDTO> bookDTO = bookService.searchBooks(limit, offset, author, title, yearStart, yearFinish, rating, available, idStore);
        return Response.ok(bookDTO).build();
    }

    @PUT
    @Path("{idBook:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Employee", "Admin"})
    public Response edit(@PathParam("idBook") Long idBook, BookDTO bookDTO) {
        try {
            bookDTO.setId(idBook);
            bookService.editBook(bookDTO);
            return Response.ok(new Message("Livro editado com sucesso")).build();
        } catch (BookStoreException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{idBook:[0-9]+}")
    @RolesAllowed({"Employee", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("idBook") Long idBook) {
        BookDTO bookDTO = bookService.getBook(idBook);
        return bookDTO != null ? Response.ok(bookDTO).build() : Response.status(404).build();
    }

    @DELETE
    @Path("{idBook:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Employee", "Admin"})
    public Response deleteBook(@PathParam("idBook") Long idBook) {
        try {
            bookService.deleteBook(idBook);
            return Response.ok(new Message("Livro removido com sucesso")).build();
        } catch (BookStoreException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

}