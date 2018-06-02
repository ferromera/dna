package ferromera.mutantdetector.service;


import ferromera.mutantdetector.dao.StatsDao;
import ferromera.mutantdetector.dto.StatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class StatService {
    
    private StatsDao statsDao;
    
    public StatService(@Autowired StatsDao statsDao) {
        this.statsDao = statsDao;
    }
    
    public StatsDTO getStats(){
        Long mutants = statsDao.getMutantCount();
        Long notMutants = statsDao.getNotMutantCount();
        BigDecimal ratio;
        if((mutants + notMutants) != 0L)
            ratio = new BigDecimal(mutants).divide(new BigDecimal(notMutants+mutants)).setScale(2,BigDecimal.ROUND_HALF_UP);
        else
            ratio=BigDecimal.ZERO;
        
        StatsDTO response = new StatsDTO();
        response.setCountHuman(mutants+notMutants);
        response.setCountMutant(mutants);
        response.setRatio(ratio);
        return response;
    }
}
