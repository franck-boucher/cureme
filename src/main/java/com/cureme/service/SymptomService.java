package com.cureme.service;

import com.cureme.model.Symptom;
import com.cureme.service.db.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SymptomRepository symptomRepository;

    public Symptom getByUuid(String uuid) {
        return symptomRepository.findByUuid(uuid);
    }

    public List<Symptom> getAll() {
        return symptomRepository.findAllByOrderByNameAsc();
    }

    public Symptom create(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public Symptom update(Symptom symptom) {
        return symptomRepository.save(symptom);
    }

    public void delete(Symptom symptom) {
        symptomRepository.delete(symptom);
    }

    public List<Symptom> search(String search) {
        return symptomRepository.findByNameIgnoreCaseContainingOrderByNameAsc(search);
    }
}
