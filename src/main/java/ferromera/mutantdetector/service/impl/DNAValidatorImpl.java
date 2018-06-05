package ferromera.mutantdetector.service.impl;

import ferromera.mutantdetector.controller.MutantDetectorController;
import ferromera.mutantdetector.exception.InvalidDnaException;
import ferromera.mutantdetector.service.DnaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DNAValidatorImpl implements DnaValidator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DNAValidatorImpl.class);
    
    
    @Override
    public void validateSize(String[] dna){
        LOGGER.info("Validating dna size of dna: {}",Arrays.toString(dna) );
        if(  Arrays.stream(dna).anyMatch(row -> row.length() != dna.length))
            throw new InvalidDnaException("dna must be a square matrix");
        LOGGER.info("Size validation OK for dna: {}",Arrays.toString(dna) );
    }
    
    @Override
    public void validateBases(String[] dna) {
        LOGGER.info("Validating dna bases of dna: {}",Arrays.toString(dna) );
        String concat = "";
        for(String row: dna)
            concat+=row;
        
        String remainingBases =concat.replaceAll("A","")
                .replaceAll("C","")
                .replaceAll("G","")
                .replaceAll("T","");
        
        if(!remainingBases.isEmpty())
            throw new InvalidDnaException("dna must be only composed by A,C,T,G bases");
        LOGGER.info("Bases validation OK for dna: {}",Arrays.toString(dna) );
    }
}
