import ferromera.mutantdetector.dto.DNAChainDTO
import ferromera.mutantdetector.exception.InvalidDnaException
import ferromera.mutantdetector.service.DNAValidator
import spock.lang.Specification

class ValidatorSpec extends Specification{


    DNAValidator validator = new DNAValidator();

    def "fails when not square"(){
        given: String[]  dna= [
                        "12",
                        "1"
                ]

        when: validator.validateSize(dna)
        then: thrown(InvalidDnaException)
    }

    def "succeeds when square"(){
        given: String[] dna= [
                        "125",
                        "142",
                        "142"
                ]
        when: validator.validateSize(dna)
        then: noExceptionThrown()
    }


}
