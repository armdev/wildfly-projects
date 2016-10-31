package com.project.web.rest;

import com.project.web.entities.Contact;
import com.project.web.facades.ContactRegistration;
import com.project.web.facades.ContactRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contacts")
@RequestScoped
public class ContactsRESTService {

    @Inject
    private ContactRepository repository;

    @Inject
    private ContactRegistration registration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> listAllContacts() {
        return repository.findAll();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Contact lookupContactById(@PathParam("id") Long id) {        
        Contact contact = repository.findById(id);
        if (contact == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return contact;
    }

    /**
     * Creates a new member from the values provided. Performs validation, and
     * will return a JAX-RS response with either 200 ok, or with a map of
     * fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(Contact contact) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateMember(contact);

            registration.register(contact);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private void validateMember(Contact contact) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
       // Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

//        if (!violations.isEmpty()) {
//            throw new ConstraintViolationException(new HashSet<>(violations));
//        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(contact.getEmail())) {
            throw new ValidationException("Unique Email Violation");
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        //log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

   
    public boolean emailAlreadyExists(String email) {
        Contact contact = null;
        try {
            contact = repository.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }
        return contact != null;
    }
}
