package com.project.web.controller;

import com.project.web.entities.Contact;
import com.project.web.facades.ContactRegistration;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class ContactController {

    private FacesContext facesContext = null;
    private ExternalContext externalContext = null;

    @Inject
    //@EJB
    private ContactRegistration contactRegistration;

    @Produces
    @Named
    private Contact newContact;

    @PostConstruct
    public void initNewMember() {
        facesContext = FacesContext.getCurrentInstance();
        externalContext = facesContext.getExternalContext();
        newContact = new Contact();
    }

    public void register() throws Exception {
        try {
            contactRegistration.register(newContact);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(m.getSummary(), m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(m.getSummary(), m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
