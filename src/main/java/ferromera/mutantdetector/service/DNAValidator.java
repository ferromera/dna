package ferromera.mutantdetector.service;

import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.exception.InvalidDnaException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DNAValidator implements IDnaValidator{


    @Override
    public void validate(DNAChainDTO dnaDto){
        if(  Arrays.stream(dnaDto.getDna()).anyMatch(row -> row.length() != dnaDto.getDna().length))
            throw new InvalidDnaException("dna must be a square matrix");

    }
}
