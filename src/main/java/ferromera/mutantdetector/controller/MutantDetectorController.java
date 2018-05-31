package ferromera.mutantdetector.controller;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.service.IDnaValidator;
import ferromera.mutantdetector.service.IMutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantDetectorController {

    private IMutantDetectorService mutantDetectorService;
    private IDnaValidator dnaValidator;


    public MutantDetectorController(
            @Autowired IMutantDetectorService mutantDetectorService,
            @Autowired IDnaValidator dnaValidator) {
        this.mutantDetectorService = mutantDetectorService;
        this.dnaValidator = dnaValidator;
    }

    @PostMapping("/mutant")
    public ResponseEntity greeting(@RequestBody DNAChainDTO dnaDto, @RequestParam boolean validateMatrixSize) {
        if(validateMatrixSize)
            dnaValidator.validate(dnaDto);

        return mutantDetectorService.detect(dnaDto) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }



}
