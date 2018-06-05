package ferromera.mutantdetector.dao;

import ferromera.mutantdetector.model.DnaMessage;

public interface DnaDao {
    
    
    void saveDna(String[] dna,boolean isMutant);
    
}
