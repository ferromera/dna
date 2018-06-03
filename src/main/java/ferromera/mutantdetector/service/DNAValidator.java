package ferromera.mutantdetector.service;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.exception.InvalidDnaException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DNAValidator implements IDnaValidator{


    @Override
    public void validateSize(String[] dna){
        if(  Arrays.stream(dna).anyMatch(row -> row.length() != dna.length))
            throw new InvalidDnaException("dna must be a square matrix");

    }
    
    @Override
    public void validateBases(String[] dna) {
        String concat = "";
        for(String row: dna)
            concat+=row;
        
        String remainingBases =concat.replaceAll("A","")
                .replaceAll("C","")
                .replaceAll("G","")
                .replaceAll("T","");
        
        if(!remainingBases.isEmpty())
            throw new InvalidDnaException("dna must be only composed by A,C,T,G bases");
    }
}
