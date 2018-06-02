package ferromera.mutantdetector.controller;


import ferromera.mutantdetector.dto.StatsDTO;
import ferromera.mutantdetector.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {


    private StatService statService;
    
    public StatsController(@Autowired StatService statService) {
        this.statService = statService;
    }
    
    @GetMapping(value = "/stats")
    public StatsDTO getStats(){
        return statService.getStats();
    }



}
