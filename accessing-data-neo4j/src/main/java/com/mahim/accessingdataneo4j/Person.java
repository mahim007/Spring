package com.mahim.accessingdataneo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Relationship(type = "teammates", direction = Relationship.UNDIRECTED)
    public Set<Person> teamMates;

    public void worksWith(Person person) {
        if (teamMates == null) {
            teamMates = new HashSet<>();
        }

        teamMates.add(person);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamMates=" + teamMates +
                '}';
    }
}
