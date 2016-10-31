package com.project.web.facades;

import com.project.web.entities.Contact;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class ContactRepository {

    // @Inject
    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public Contact findById(Long id) {
        return em.find(Contact.class, id);
    }

    public List<Contact> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);

        criteria.select(contact).orderBy(cb.desc(contact.get("id")));
        return em.createQuery(criteria).getResultList();
    }

    public Contact findByEmail(String email) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> criteria = cb.createQuery(Contact.class);
        Root<Contact> contact = criteria.from(Contact.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
        criteria.select(contact).where(cb.equal(contact.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
    }
}
