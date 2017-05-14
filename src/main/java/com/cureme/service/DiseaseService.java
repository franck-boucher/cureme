package com.cureme.service;

import com.cureme.model.Disease;
import com.cureme.model.Symptom;
import com.cureme.service.db.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DiseaseRepository diseaseRepository;

    public Disease getByUuid(String uuid) {
        return diseaseRepository.findByUuid(uuid);
    }

    public List<Disease> getAll() {
        return diseaseRepository.findAllByOrderByNameAsc();
    }

    public Disease create(Disease disease) {
        return diseaseRepository.save(disease);
    }

    public Disease update(Disease disease) {
        return diseaseRepository.save(disease);
    }

    public void delete(Disease disease) {
        diseaseRepository.delete(disease);
    }

    public void addSymptom(String diseaseUuid, Symptom symptom) {
        Disease disease = getByUuid(diseaseUuid);
        List<Symptom> listSymptom = disease.getSymptoms();
        listSymptom.add(symptom);
        disease.setSymptoms(listSymptom);
        update(disease);
    }

    public void removeSymptom(String diseaseUuid, String symptomUuid) {
        Disease disease = getByUuid(diseaseUuid);
        List<Symptom> listSymptom = disease.getSymptoms();
        disease.setSymptoms(listSymptom.stream()
                .filter(symptom -> !symptom.getUuid().equals(symptomUuid))
                .collect(Collectors.toList()));
        update(disease);
    }

    public List<Disease> search(String search) {
        return diseaseRepository.findByNameIgnoreCaseContainingOrderByNameAsc(search);
    }

    public List<Disease> searchFromSymptoms(List<Symptom> symptoms) {
        List<String> strSymptoms = symptoms.stream().map(symptom -> symptom.getName()).collect(Collectors.toList());
        //return diseaseRepository.findBySymptoms(symptoms);
        return getAll().stream()
                .filter(disease -> CollectionUtils.containsAny(disease.getSymptoms().stream()
                        .map(symptom -> symptom.getName()).collect(Collectors.toList()), strSymptoms))
                .collect(Collectors.toList());
    }

}
