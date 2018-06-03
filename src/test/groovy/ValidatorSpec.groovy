import ferromera.mutantdetector.dto.DNAChainDTO
import ferromera.mutantdetector.exception.InvalidDnaException
import ferromera.mutantdetector.service.DNAValidator
import spock.lang.Specification

class ValidatorSpec extends Specification{


    DNAValidator validator = new DNAValidator();

    def "fails when not square"(){
        given: DNAChainDTO chain = new DNAChainDTO(
                dna: [
                        "12",
                        "1"
                ]
        )
        when: validator.validateSize(chain)
        then: thrown(InvalidDnaException)
    }

    def "succeeds when square"(){
        given: DNAChainDTO chain = new DNAChainDTO(
                dna: [
                        "125",
                        "142",
                        "142"
                ]
        )
        when: validator.validateSize(chain)
        then: noExceptionThrown()
    }


}
