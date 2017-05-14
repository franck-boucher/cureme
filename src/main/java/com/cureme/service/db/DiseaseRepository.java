package com.cureme.service.db;

import com.cureme.model.Disease;
import com.cureme.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, String> {

    Disease findByUuid(String uuid);

    Disease save(Disease disease);

    void delete(Disease disease);

    List<Disease> findAllByOrderByNameAsc();

    List<Disease> findByNameIgnoreCaseContainingOrderByNameAsc(String name);

    @Query("select d from Disease d where d.symptoms in ( select s from Symptom s where s.name in :symptomNames )")
    List<Disease> findBySymptomsName(@Param("symptomNames") List<String> symptomNames);

    List<Disease> findBySymptoms(List<Symptom> symptomNames);

}
