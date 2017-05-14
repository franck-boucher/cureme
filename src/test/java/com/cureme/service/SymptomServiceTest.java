package com.cureme.service;

import com.cureme.model.Symptom;
import com.cureme.service.db.DbService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SymptomServiceTest {

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DbService dbService;

    @Before
    public void setUp() throws Exception {
        dbService.cleanDb();
    }

    @Test
    public void createAndRetrieve(){

        Symptom symptom = new Symptom("nez qui coule");
        symptom = symptomService.create(symptom);

        Symptom retrievedSymptom = symptomService.getByUuid(symptom.getUuid());
        assertEquals("nez qui coule", retrievedSymptom.getName());
    }

    @Test
    public void updateAndRetrieve(){

        Symptom symptom = new Symptom("nez qui coule");
        symptom = symptomService.create(symptom);

        symptom.setName("mal à la tête");
        symptomService.update(symptom);

        Symptom retrievedSymptom = symptomService.getByUuid(symptom.getUuid());
        assertEquals("mal à la tête", retrievedSymptom.getName());
    }

    @Test
    public void searchDisease() {

        Symptom fievre = new Symptom("Fièvre");
        fievre = symptomService.create(fievre);

        Symptom toux = new Symptom("Toux ");
        toux = symptomService.create(toux);

        Symptom fatigue = new Symptom("Fatigue");
        fatigue = symptomService.create(fatigue);

        assertEquals(2, symptomService.search("t").size());

        assertEquals(1, symptomService.search("toux").size());

    }

    @Test
    public void symptomOrder() {

        Symptom fievre = new Symptom("Fièvre");
        fievre = symptomService.create(fievre);

        Symptom toux = new Symptom("Toux ");
        toux = symptomService.create(toux);

        Symptom fatigue = new Symptom("Fatigue");
        fatigue = symptomService.create(fatigue);

        assertEquals("Fatigue", symptomService.search("t").get(0).getName());

        assertEquals("Fatigue", symptomService.getAll().get(0).getName());

    }
}
