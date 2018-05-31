package ferromera.mutantdetector.service;

import ferromera.mutantdetector.dto.DNAChainDTO;

/**
 * Created by ferromera on 5/30/2018.
 */
public interface IDnaValidator {

    void validate(DNAChainDTO dnaDto);
}
