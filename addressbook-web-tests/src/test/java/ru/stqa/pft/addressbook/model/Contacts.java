package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gis on 27.11.2016.
 */
public class Contacts extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public Contacts(List<ContactData> list) {
        this.delegate = new HashSet<ContactData>(list);
    }

    public Contacts() {
        this.delegate = new HashSet<ContactData>();
    }

    public Contacts(Contacts contacts) {
        this.delegate = new HashSet<ContactData>(contacts);
    }

    public Contacts(Set<ContactData> contacts) {
        this.delegate = new HashSet<ContactData>(contacts);
    }

    @Override
    protected Set<ContactData> delegate() {
        return delegate;
    }

    public Contacts withAdded(ContactData contact) {
        Contacts contacts = new Contacts(this);
        contacts.add(contact);
        return contacts;
    }

    public Contacts without(ContactData contact) {
        Contacts contacts = new Contacts(this);
        contacts.remove(contact);
        return contacts;
    }

    public Contacts withModified(ContactData contactOld, ContactData contactNew) {
        Contacts contacts = new Contacts(this);
        contacts.remove(contactOld);
        contacts.add(contactNew);
        return contacts;
    }

    public ContactData getRandom() {
        List<ContactData> contacts = new ArrayList<ContactData>(this.delegate);
        int index = (int)(Math.random() * contacts.size());
        return contacts.get(index);
    }

    public ContactData getById(int id) {
        return this.delegate.stream().filter(c -> c.getId() == id).findFirst().get();
    }
}
