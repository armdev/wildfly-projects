package com.project.web.facades;

import com.project.web.entities.Contact;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import javax.persistence.PersistenceContext;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class ContactRegistration {
    
    @PersistenceContext(unitName = "primary")
    private EntityManager em;
   // private Logger log;  

    @Inject
    private Event<Contact> memberEventSrc;

    public void register(Contact contact) throws Exception {
       System.out.println("Registering " + contact.getFirstName());
        em.persist(contact);
        memberEventSrc.fire(contact);
    }
}
