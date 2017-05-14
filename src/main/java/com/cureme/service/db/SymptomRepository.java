package com.cureme.service.db;


import com.cureme.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, String> {

    Symptom findByUuid(String uuid);

    Symptom save(Symptom symptom);

    void delete(Symptom symptom);

    List<Symptom> findAllByOrderByNameAsc();

    List<Symptom> findByNameIgnoreCaseContainingOrderByNameAsc(String name);
}
