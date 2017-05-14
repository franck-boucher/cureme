package com.cureme.service.init;

import com.cureme.model.Disease;
import com.cureme.model.Symptom;
import com.cureme.service.DiseaseService;
import com.cureme.service.SymptomService;
import com.cureme.service.db.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializerService.class);

    private final DiseaseService diseaseService;
    private final SymptomService symptomService;

    @Autowired
    public DataInitializerService(ApplicationArguments args, DbService dbService, DiseaseService diseaseService, SymptomService symptomService) {

        this.diseaseService = diseaseService;
        this.symptomService = symptomService;

        if (args.containsOption("reset-db")) {
            LOGGER.info("Empty DB or data reset asked");
            dbService.cleanDb();
            importData();
        }
    }

    private void importData() {

        // SYMPTOMS

        Symptom ecoulementNasal = new Symptom("Ecoulement nasal");
        ecoulementNasal = symptomService.create(ecoulementNasal);

        Symptom eternuement = new Symptom("Eternuement");
        eternuement = symptomService.create(eternuement);

        Symptom fievre = new Symptom("Fièvre");
        fievre = symptomService.create(fievre);

        Symptom toux = new Symptom("Toux ");
        toux = symptomService.create(toux);

        Symptom douleurGorge = new Symptom("Douleur à la gorge");
        douleurGorge = symptomService.create(douleurGorge);

        Symptom fatigue = new Symptom("Fatigue");
        fatigue = symptomService.create(fatigue);

        Symptom diarrhee = new Symptom("Diarrhée");
        diarrhee = symptomService.create(diarrhee);

        Symptom vomissement = new Symptom("Vomissement");
        vomissement = symptomService.create(vomissement);

        LOGGER.debug("Bootstrapped some diseases");


        // DISEASES

        Disease rhume = new Disease("Rhume", "Un rhume est une infection des voies aériennes supérieures par un virus",
                Arrays.asList(ecoulementNasal, eternuement, fievre, toux));
        rhume = diseaseService.create(rhume);

        Disease angine = new Disease("Angine", "L’angine est une infections aiguë de l'oropharynx, causée par des bactéries ou des virus",
                Arrays.asList(fievre, douleurGorge, toux));
        angine = diseaseService.create(angine);

        Disease gastro = new Disease("Gastro-entérite", "Une gastro-entérite est une infection inflammatoire du système digestif pouvant entraîner",
                Arrays.asList(fatigue, vomissement, diarrhee, fievre));
        gastro = diseaseService.create(gastro);

        LOGGER.debug("Bootstrapped some diseases");

    }
}
