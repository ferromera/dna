import ferromera.mutantdetector.controller.MutantDetectorController
import ferromera.mutantdetector.dto.DNAChainDTO
import ferromera.mutantdetector.service.DNAValidator
import ferromera.mutantdetector.service.MutantDetectorService
import spock.lang.Specification

class MutantDetectorControllerSpec extends Specification{

    MutantDetectorController controller
    MutantDetectorService service = Mock(MutantDetectorService)
    DNAValidator dnaValidator = Mock(DNAValidator)

    def setup(){
        controller= new MutantDetectorController(service,dnaValidator)

    }

    def "when call the controller without validations must call the service"(){
        given:
        controller.validateMatrixSize=false
        controller.validateBases = false
        DNAChainDTO dto = new DNAChainDTO()
        when:
        controller.detectMutant(dto)
        then:
        1 * service.detect(dto)
    }

    def "when validate size is set must call validateSize"(){
        given:
        controller.validateMatrixSize=true
        DNAChainDTO dto = new DNAChainDTO(dna: ["AA","CC"])
        when:
        controller.detectMutant(dto)
        then:
        1* dnaValidator.validateSize(dto.dna)
    }
    def "when validate bases is set must call validateSize"(){
        given:
        controller.validateBases=true
        DNAChainDTO dto = new DNAChainDTO(dna: ["AA","CC"])
        when:
        controller.detectMutant(dto)
        then:
        1* dnaValidator.validateBases(dto.dna)
    }

}
