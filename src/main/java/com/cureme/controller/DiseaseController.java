package com.cureme.controller;

import com.cureme.model.Disease;
import com.cureme.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Disease> search(@RequestParam(value = "search", required = false, defaultValue = "") String search) {
        return diseaseService.search(search);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public Disease getByUuid(@PathVariable String uuid) {
        return diseaseService.getByUuid(uuid);
    }
}
