package ferromera.mutantdetector.controller;

import ferromera.mutantdetector.dao.StatsDao;
import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.model.Stat;
import ferromera.mutantdetector.service.IDnaValidator;
import ferromera.mutantdetector.service.IMutantDetectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MutantDetectorController {

    private IMutantDetectorService mutantDetectorService;
    private IDnaValidator dnaValidator;

    @Autowired
    private StatsDao statsDao;
    @Value("${validate.matrixsize:true}")
    private boolean validateMatrixSize;
    
    public MutantDetectorController(
            @Autowired IMutantDetectorService mutantDetectorService,
            @Autowired IDnaValidator dnaValidator) {
        this.mutantDetectorService = mutantDetectorService;
        this.dnaValidator = dnaValidator;
    }

    
    @PostMapping("/mutant")
    public ResponseEntity greeting(@RequestBody DNAChainDTO dnaDto) {
        if(validateMatrixSize)
            dnaValidator.validate(dnaDto);

        return mutantDetectorService.detect(dnaDto) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.FORBIDDEN);
    }



}
