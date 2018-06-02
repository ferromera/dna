import ferromera.mutantdetector.dto.DNAChainDTO
import ferromera.mutantdetector.exception.InvalidDnaException
import ferromera.mutantdetector.service.DNAValidator
import org.apache.tomcat.util.descriptor.tld.ValidatorXml
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
        when: validator.validate(chain)
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
        when: validator.validate(chain)
        then: noExceptionThrown()
    }


}
