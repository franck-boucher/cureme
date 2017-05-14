package com.cureme.service;

import com.cureme.model.Disease;
import com.cureme.model.Symptom;
import com.cureme.service.db.DbService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiseaseServiceTest {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DbService dbService;

    @Before
    public void setUp() throws Exception {
        dbService.cleanDb();
    }

    @Test
    public void createAndRetrieve() {

        Disease disease = new Disease("rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease = diseaseService.create(disease);

        Disease retrievedDisease = diseaseService.getByUuid(disease.getUuid());
        assertEquals("rhume", retrievedDisease.getName());
    }

    @Test
    public void updateAndRetrieve() {

        Disease disease = new Disease("rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease = diseaseService.create(disease);

        disease.setName("fièvre");
        diseaseService.update(disease);

        Disease retrievedDisease = diseaseService.getByUuid(disease.getUuid());
        assertEquals("fièvre", retrievedDisease.getName());
    }

    @Test
    public void retrieveAll() {

        Disease disease1 = new Disease("Rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease1 = diseaseService.create(disease1);

        Disease disease2 = new Disease("Angine", "L’angine est une infections aiguë de l'oropharynx, causée par des bactéries ou des virus");
        disease2 = diseaseService.create(disease2);

        Disease disease3 = new Disease("Gastro-entérite", "Une gastro-entérite est une infection inflammatoire du système digestif pouvant entraîner");
        disease3 = diseaseService.create(disease3);

        assertEquals(3, diseaseService.getAll().size());
    }

    @Test
    public void addSymptoms() {

        Disease disease = new Disease("rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease = diseaseService.create(disease);

        Symptom symptom1 = new Symptom("nez qui coule");
        symptom1 = symptomService.create(symptom1);

        Symptom symptom2 = new Symptom("yeux qui pleurent");
        symptom2 = symptomService.create(symptom2);

        assertEquals(0, disease.getSymptoms().size());

        diseaseService.addSymptom(disease.getUuid(), symptom1);
        diseaseService.addSymptom(disease.getUuid(), symptom2);

        assertEquals(2, diseaseService.getByUuid(disease.getUuid()).getSymptoms().size());
    }

    @Test
    public void removeSymptoms() {

        Symptom symptom1 = new Symptom("nez qui coule");
        symptom1 = symptomService.create(symptom1);

        Symptom symptom2 = new Symptom("yeux qui pleurent");
        symptom2 = symptomService.create(symptom2);

        Disease disease = new Disease("rhume", "Un rhume est une infection des voies aériennes supérieures par un virus",
                Arrays.asList(symptom1, symptom2));
        disease = diseaseService.create(disease);

        assertEquals(2, disease.getSymptoms().size());

        diseaseService.removeSymptom(disease.getUuid(), symptom2.getUuid());

        assertEquals(1, diseaseService.getByUuid(disease.getUuid()).getSymptoms().size());
        assertEquals("nez qui coule", diseaseService.getByUuid(disease.getUuid()).getSymptoms().get(0).getName());
    }

    @Test
    public void searchDisease() {

        Disease disease1 = new Disease("Rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease1 = diseaseService.create(disease1);

        Disease disease2 = new Disease("Angine", "L’angine est une infections aiguë de l'oropharynx, causée par des bactéries ou des virus");
        disease2 = diseaseService.create(disease2);

        Disease disease3 = new Disease("Gastro-entérite", "Une gastro-entérite est une infection inflammatoire du système digestif pouvant entraîner");
        disease3 = diseaseService.create(disease3);

        assertEquals(2, diseaseService.search("a").size());

        assertEquals(1, diseaseService.search("gastro").size());

    }

    @Test
    public void diseaseOrder() {

        Disease disease1 = new Disease("Rhume", "Un rhume est une infection des voies aériennes supérieures par un virus");
        disease1 = diseaseService.create(disease1);

        Disease disease2 = new Disease("Angine", "L’angine est une infections aiguë de l'oropharynx, causée par des bactéries ou des virus");
        disease2 = diseaseService.create(disease2);

        Disease disease3 = new Disease("Gastro-entérite", "Une gastro-entérite est une infection inflammatoire du système digestif pouvant entraîner");
        disease3 = diseaseService.create(disease3);

        assertEquals("Angine", diseaseService.search("a").get(0).getName());

        assertEquals("Angine", diseaseService.getAll().get(0).getName());

    }

    @Test
    public void searchDiseaseFromSymptoms() {

        Symptom symptom1 = new Symptom("symptom1");
        symptom1 = symptomService.create(symptom1);

        Symptom symptom2 = new Symptom("symptom2");
        symptom2 = symptomService.create(symptom2);

        Disease disease1 = new Disease("disease1", "desc disease1",
                Arrays.asList(symptom1, symptom2));
        disease1 = diseaseService.create(disease1);

        Disease disease2 = new Disease("disease2", "desc disease2",
                Collections.singletonList(symptom2));
        disease2 = diseaseService.create(disease2);

        assertEquals(2, diseaseService.searchFromSymptoms(Arrays.asList(symptom1, symptom2)).size());

        assertEquals(1, diseaseService.searchFromSymptoms(Collections.singletonList(symptom1)).size());

    }
}
