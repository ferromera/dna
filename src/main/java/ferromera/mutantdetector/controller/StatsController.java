package ferromera.mutantdetector.controller;


import ferromera.mutantdetector.dto.StatsDTO;
import ferromera.mutantdetector.service.StatService;
import ferromera.mutantdetector.service.impl.StatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {


    private StatServiceImpl statService;
    
    public StatsController(@Autowired StatServiceImpl statService) {
        this.statService = statService;
    }
    
    @GetMapping(value = "/stats")
    public StatsDTO getStats(){
        return statService.getStats();
    }



}
