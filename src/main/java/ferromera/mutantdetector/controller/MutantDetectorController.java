package ferromera.mutantdetector.controller;

import java.util.Arrays;

import ferromera.mutantdetector.dto.InvalidDnaResponse;
import ferromera.mutantdetector.exception.InvalidDnaException;
import ferromera.mutantdetector.service.DNAValidator;
import ferromera.mutantdetector.service.MutantDetectorService;
import ferromera.mutantdetector.dto.DNAChainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectorController {

    private MutantDetectorService mutantDetectorService;
    private DNAValidator dnaValidator;


    public MutantDetectorController(
            @Autowired MutantDetectorService mutantDetectorService,
            @Autowired DNAValidator dnaValidator) {
        this.mutantDetectorService = mutantDetectorService;
        this.dnaValidator = dnaValidator;
    }

    @PostMapping("/mutant")
    public ResponseEntity greeting(@RequestBody DNAChainDTO dnaDto, @RequestParam boolean validateMatrixSize) {
        if(validateMatrixSize)
            dnaValidator.validate(dnaDto);

        return mutantDetectorService.detect(dnaDto) ?
             ResponseEntity.ok(ResponseEntity.EMPTY) :
             new ResponseEntity(HttpStatus.FORBIDDEN);
    }



}
