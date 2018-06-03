package ferromera.mutantdetector.controller;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.service.IDnaValidator;
import ferromera.mutantdetector.service.IMutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MutantDetectorController {

    private IMutantDetectorService mutantDetectorService;
    private IDnaValidator dnaValidator;
    
    @Value("${validate.matrixsize:true}")
    private boolean validateMatrixSize;
    @Value("${validate.bases:true}")
    private boolean validateBases;
    
    public MutantDetectorController(
            @Autowired IMutantDetectorService mutantDetectorService,
            @Autowired IDnaValidator dnaValidator) {
        this.mutantDetectorService = mutantDetectorService;
        this.dnaValidator = dnaValidator;
    }

    
    @PostMapping("/mutant")
    public ResponseEntity greeting(@RequestBody DNAChainDTO dnaDto) {
        if(validateMatrixSize)
            dnaValidator.validateSize(dnaDto.getDna());
        if(validateBases)
            dnaValidator.validateBases(dnaDto.getDna());

        return mutantDetectorService.detect(dnaDto) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }



}
