package ferromera.mutantdetector.service.impl;

import ferromera.mutantdetector.controller.MutantDetectorController;
import ferromera.mutantdetector.dao.DnaDao;
import ferromera.mutantdetector.dto.DNAChainDTO;
import ferromera.mutantdetector.service.MutantDetectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MutantDetectorServiceImpl implements MutantDetectorService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MutantDetectorServiceImpl.class);
    
    
    private static final int MUTANT_AMOUNT = 2;
    
    private DnaDao dnaDao;
    
    public MutantDetectorServiceImpl(@Autowired DnaDao dnaDao) {
        this.dnaDao = dnaDao;
    }
    
    @Override
    public boolean detect(DNAChainDTO dnaDto) {
    
        String [] dna = dnaDto.getDna();
        LOGGER.info("Detecting mutant on dna: {}", Arrays.toString(dna) );
        boolean isMutant = isMutant(dna);
        LOGGER.info("Detection results {} on dna: {}",isMutant, Arrays.toString(dna) );
        dnaDao.saveDna(dna,isMutant);
        return isMutant;
    }
    
    public boolean isMutant(String[] dna) {
        if(dna.length < 4) //If its lesser than 4 is not consider a mutant
            return false;
        int fourMatches = 0;
        fourMatches+= countHorizontally(dna);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        fourMatches+= countVertically(dna,fourMatches);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        fourMatches+= countDiagonally(dna,fourMatches);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        fourMatches+= countDiagonallyReverse(dna,fourMatches);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        return false;
    }
    
    private int countHorizontally(String [] dna) {
        int fourMatches = 0;
        for(int i=0 ; i < dna.length ; i++){
            int sameCount=1;
            char previous = dna[i].charAt(0);
            for(int j=1; j < dna.length ; j++){
                char current = dna[i].charAt(j);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT)
                break;
        }
        return fourMatches;

    }
    
    private int countVertically(String [] dna,int matches) {
        int fourMatches = 0;
        for(int i=0 ; i < dna.length ; i++){
            int sameCount=1;
            char previous = dna[0].charAt(i);
            for(int j=1; j < dna.length ; j++){
                char current = dna[j].charAt(i);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT - matches)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT - matches)
                break;
        }
        return fourMatches;
        
    }
    
    private int countDiagonally(String[] dna, int matches) {
        int fourMatches = 0;
        for(int i=0 ; i < dna.length-3 ; i++){
            int sameCount=1;
            char previous = dna[0].charAt(i);
            for(int j=1 , k=i+1; j < dna.length && k < dna.length ; j++,k++){
                char current = dna[j].charAt(k);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT - matches)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT - matches)
                break;
        }
    
        if(fourMatches >= MUTANT_AMOUNT - matches)
            return fourMatches;
    
        for(int i=1 ; i < dna.length-3 ; i++){
            int sameCount=1;
            char previous = dna[i].charAt(0);
            for(int j=1 , k=i+1; j < dna.length && k < dna.length ; j++,k++){
                char current = dna[k].charAt(j);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT - matches)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT - matches)
                break;
        }
        return fourMatches;
    }
    
    
    private int countDiagonallyReverse(String[] dna, int matches) {
        int fourMatches = 0;
        // index i and k for columns , index j for rows
        for(int i=dna.length-1 ; i > 2 ; i--){
            int sameCount=1;
            char previous = dna[0].charAt(i);
            for(int j=1 , k=i-1; j < dna.length && k >= 0 ; j++,k--){
                char current = dna[j].charAt(k);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT - matches)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT - matches)
                break;
        }
        
        if(fourMatches >= MUTANT_AMOUNT - matches)
            return fourMatches;
    
        // index i and k for rows , index j for columns
        for(int i=1 ; i < dna.length-3 ; i++){
            int sameCount=1;
            char previous = dna[i].charAt(dna.length-1);
            for(int j=dna.length-2 , k=i+1; j >= 0 && k < dna.length ; j--,k++){
                char current = dna[k].charAt(j);
                if(current == previous) {
                    sameCount++;
                    if(sameCount==4){
                        sameCount=0;
                        fourMatches++;
                        if(fourMatches >= MUTANT_AMOUNT - matches)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches >= MUTANT_AMOUNT - matches)
                break;
        }
        return fourMatches;
    }
}
