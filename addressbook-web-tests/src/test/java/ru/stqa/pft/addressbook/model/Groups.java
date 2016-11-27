package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gis on 27.11.2016.
 */
public class Groups extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    public Groups(Groups groups) {
        this.delegate = new HashSet<GroupData>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    public Groups(List<GroupData> list) {
        this.delegate = new HashSet<GroupData>(list);
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups without(GroupData group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }

    public Groups withModified(GroupData groupOld, GroupData groupNew) {
        Groups groups = new Groups(this);
        groups.remove(groupOld);
        groups.add(groupNew);
        return groups;
    }

    public GroupData getRandom() {
        List<GroupData> groups = new ArrayList<GroupData>(this.delegate);
        int index = (int)(Math.random() * groups.size());
        return groups.get(index);
    }
}
