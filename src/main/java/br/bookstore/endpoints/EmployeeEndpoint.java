package br.bookstore.endpoints;

import br.bookstore.dto.EmployeeDTO;
import br.bookstore.dto.UserDTO;
import br.bookstore.model.BookStoreException;
import br.bookstore.model.Message;
import br.bookstore.service.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/store/{idStore:[0-9]+}/employee")
@RolesAllowed({ "Employee", "Admin" })
public class EmployeeEndpoint {

	private EmployeeService employeeService;

	@Inject
	public EmployeeEndpoint(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

		@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createEmployee(@PathParam("idStore") Long idStore, UserDTO employee) {
		try {
			employee.setRole("Employee");
			employeeService.createEmployee(idStore, employee);
			return Response.ok(new Message("Funcionário criado com sucesso")).build();
		} catch (BookStoreException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}

	}

	@PUT
	@Path("{idUser:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(@PathParam("idUser") Long idUser, UserDTO employee) {
		try {
			employeeService.editEmployee(idUser, employee);
			return Response.ok(new Message("Funcionário editado com sucesso")).build();
		} catch (BookStoreException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("{idUser:[0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idUser") Long idUser) {
		try {
			employeeService.deleteEmployee(idUser);
			return Response.ok(new Message("Funcionário removido com sucesso")).build();
		} catch (BookStoreException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("idStore") Long idStore) {
		try {
			List<EmployeeDTO> employees = employeeService.getEmployees(idStore);
			return Response.ok(employees).build();
		} catch (BookStoreException e) {
			return Response.status(404).entity(e.getMessage()).build();
		}
	}
}