import ferromera.mutantdetector.controller.StatsController
import ferromera.mutantdetector.service.StatService
import spock.lang.Specification


class StatControllerSpec extends Specification{


    StatsController statsController = new StatsController()
    StatService statService = Mock(StatService)

    def setup(){
        statsController.statService = statService
    }

    def "when call getStats call the service"(){
        given:
        when: statsController.getStats()
        then: 1 * statService.getStats()
    }


}
