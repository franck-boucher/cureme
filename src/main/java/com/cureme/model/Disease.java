package com.cureme.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Disease {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String uuid;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Symptom> symptoms;


    public Disease() {
    }

    public Disease(String name, String description) {
        this.name = name;
        this.description = description;
        this.setSymptoms(Collections.emptyList());
    }

    public Disease(String name, String description, List<Symptom> symptoms) {
        this.name = name;
        this.description = description;
        this.symptoms = symptoms;
    }


    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Symptom> getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
}
