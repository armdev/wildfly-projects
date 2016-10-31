package com.project.web.data;

import com.project.web.entities.Contact;
import com.project.web.facades.ContactRepository;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class ContactListProducer {

    @Inject
    private ContactRepository contactRepository;

    private List<Contact> contacts;

    public ContactListProducer() {
        System.out.println("Called constructor ");
    }

    @PostConstruct
    public void retrieveAllMembersOrderedByName() {
        System.out.println("Called list ");
        contacts = contactRepository.findAll();
    }

    @Produces
    @Named
    public List<Contact> getContacts() {
        return contacts;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Contact contact) {
        retrieveAllMembersOrderedByName();
    }

}
