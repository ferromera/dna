package ferromera.mutantdetector.service;

import ferromera.mutantdetector.dto.DNAChainDTO;
import org.springframework.stereotype.Service;

@Service
public class MutantDetectorService implements IMutantDetectorService {

    @Override
    public boolean detect(DNAChainDTO dnaDto) {
        String [] dna = dnaDto.getDna();
        int fourMatches = 0;
        fourMatches+= countHorizontally(dna);
        if(fourMatches > 1) return true;
//        fourMatches+= countVertically(dna);
//        if(fourMatches > 1) return true;
//        fourMatches+= countDiagonally(dna);
//        if(fourMatches > 1) return true;

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
                        if(fourMatches > 1)
                            break;
                    }
                }else
                    sameCount=1;
                previous=current;
            }
            if(fourMatches > 1)
                break;
        }

        return fourMatches;


    }
}
