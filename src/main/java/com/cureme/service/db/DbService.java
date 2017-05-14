package com.cureme.service.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DiseaseRepository diseaseRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SymptomRepository symptomRepository;

    public void cleanDb() {
        diseaseRepository.deleteAll();
        symptomRepository.deleteAll();
    }
}
