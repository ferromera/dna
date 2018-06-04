import ferromera.mutantdetector.dao.StatsDao
import ferromera.mutantdetector.dto.StatsDTO
import ferromera.mutantdetector.service.StatService
import spock.lang.Specification





class StatServiceSpec  extends Specification {

    StatService statService = new StatService()
    StatsDao statsDao = Mock(StatsDao)


    def setup(){
        statService.statsDao = statsDao
        }

    def "when get stats call count mutant and count no mutant"(){
        given:

        when:
            statService.getStats()
        then:
            1 * statsDao.getMutantCount() >>2
            1 * statsDao.getNotMutantCount() >>3
    }


    def "when there is no human ratio is zero"(){
        given:

        when:
         StatsDTO stats= statService.getStats()
        then:
        1 * statsDao.getMutantCount() >>0
        1 * statsDao.getNotMutantCount() >>0
        stats.ratio == 0

    }
}
