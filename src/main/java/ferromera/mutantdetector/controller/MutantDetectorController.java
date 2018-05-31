package ferromera.mutantdetector.controller;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.service.DNAValidator;
import ferromera.mutantdetector.service.MutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }



}
