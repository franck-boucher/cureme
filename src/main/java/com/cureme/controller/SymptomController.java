package com.cureme.controller;

import com.cureme.model.Symptom;
import com.cureme.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/symptom")
public class SymptomController {

    @Autowired
    private SymptomService symptomService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Symptom> search(@RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return symptomService.search(search);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public Symptom getByUuid(@PathVariable String uuid) {
        return symptomService.getByUuid(uuid);
    }
}
