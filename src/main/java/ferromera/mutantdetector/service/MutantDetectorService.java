package ferromera.mutantdetector.service;

import ferromera.mutantdetector.dto.DNAChainDTO;
import org.springframework.stereotype.Service;

@Service
public class MutantDetectorService implements IMutantDetectorService {
    
    private static final int MUTANT_AMOUNT = 2;
    
    @Override
    public boolean detect(DNAChainDTO dnaDto) {
        String [] dna = dnaDto.getDna();
        int fourMatches = 0;
        fourMatches+= countHorizontally(dna);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        fourMatches+= countVertically(dna,fourMatches);
        if(fourMatches >= MUTANT_AMOUNT) return true;
        fourMatches+= countDiagonally(dna,fourMatches);
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
                    if(sameCount == 0){
                        //already matched
                        previous=current;
                        continue;
                    }
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
                    if(sameCount == 0){
                        //already matched
                        previous=current;
                        continue;
                    }
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
                    if(sameCount == 0){
                        //already matched
                        previous=current;
                        continue;
                    }
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
                    if(sameCount == 0){
                        //already matched
                        previous=current;
                        continue;
                    }
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
