package ferromera.mutantdetector.service.impl;


import ferromera.mutantdetector.dao.StatsDao;
import ferromera.mutantdetector.dao.impl.AsyncronicDnaDao;
import ferromera.mutantdetector.dto.StatsDTO;
import ferromera.mutantdetector.service.StatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
public class StatServiceImpl implements StatService {
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StatServiceImpl.class);
    
    private StatsDao statsDao;
    
    public StatServiceImpl(@Autowired StatsDao statsDao) {
        this.statsDao = statsDao;
    }
    
    public StatsDTO getStats(){
        LOGGER.info("Obtaining stats of mutants");
        Long mutants = statsDao.getMutantCount();
        Long notMutants = statsDao.getNotMutantCount();
        BigDecimal ratio;
        if((mutants + notMutants) != 0L)
            ratio = new BigDecimal(mutants).divide(new BigDecimal(notMutants+mutants),2,ROUND_HALF_UP);
        else
            ratio=BigDecimal.ZERO;
        
        StatsDTO response = new StatsDTO();
        response.setCountHuman(mutants+notMutants);
        response.setCountMutant(mutants);
        response.setRatio(ratio);
        LOGGER.info("Mutant stats: mutantCount={}, humanCount={}, ration={}",response.getCountMutant(),response.getCountHuman(),response.getRatio());
        return response;
    }
}
