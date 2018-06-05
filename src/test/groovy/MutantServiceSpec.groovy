import ferromera.mutantdetector.dao.DnaDao
import ferromera.mutantdetector.dto.DNAChainDTO
import ferromera.mutantdetector.service.impl.MutantDetectorServiceImpl
import spock.lang.Specification

class MutantServiceSpec extends Specification {

    MutantDetectorServiceImpl service = new MutantDetectorServiceImpl()
    DnaDao dnaDao = Mock(DnaDao)

    def setup(){
        service.dnaDao = dnaDao
    }

    def "fails when N < 4"() {
        given:
        String[] dna = [
                "AAA",
                "AAA",
                "AAA"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }

    def "fails when zero segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCGGCC",
                "AATTAA",
                "CCGGCC",
                "AATTAA",
                "CCGGCC"
        ]
        when:
            boolean result = service.isMutant(dna)
        then:
            result == false

    }
    def "fails when one hor segment of 7"() {
        given:
        String [] dna =  [
                "AAAAAAAT",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }



    def "fails when one hor segment"() {
        given:
        String [] dna =  [
                "AAAAAT",
                "CCGGCC",
                "AATTAA",
                "CCGGCC",
                "AATTAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }

    def "fails when one ver segment"() {
        given:
        String [] dna =  [
                "AATAAT",
                "CCGGCT",
                "AATTAT",
                "CCGGCT",
                "AATTAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }

    def "fails when one diagonal segment"() {
        given:
        String [] dna =  [
                "AATAAT",
                "CCGGCC",
                "ACTTAT",
                "CCCGCT",
                "AATCAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }

    def "fails when one reverse diagonal segment"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCGGCC",
                "AATTCA",
                "CCGCCC",
                "AACTAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == false

    }

    //SUCCESSFUL

    def "succeeds when one hor segment of 8"() {
        given:
        String [] dna =  [
                "AAAAAAAA",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG",
                "AATTAATT",
                "CCGGCCGG"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 2 hor segments"() {
        given:
        String [] dna =  [
                "AAAATT",
                "CCGGCC",
                "AATTAA",
                "CCGGCC",
                "AAAATT",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }
    def "succeeds when 2 vertical segments"() {
        given:
        String [] dna =  [
                "AAGTAC",
                "CCGGCC",
                "AAGTAC",
                "CCGGCC",
                "AATTAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 1 hor and 1 ver segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCCCGG",
                "AAGTAC",
                "CCGGCC",
                "AATTAC",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 2 diagonal segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCAGCC",
                "AATAAA",
                "CAGGAC",
                "AAATAA",
                "CCGACC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 1 hor and 1 diagonal segments"() {
        given:
        String [] dna =  [
                "ACTTAA",
                "CCCCGC",
                "AATCTA",
                "CTGGCC",
                "AAATAC",
                "CCGACC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 1 ver and 1 diagonal segments"() {
        given:
        String [] dna =  [
                "AATTAC",
                "CCGGCC",
                "AACAAC",
                "CTGCAC",
                "AAATCC",
                "CCGACC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 2 reverse diagonal segments"() {
        given:
        String [] dna =  [
                "AATTGA",
                "CCGGCC",
                "AAGTCA",
                "CGGCCC",
                "GACTAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 1 diagonal and 1 reverse diagonal segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCGGCC",
                "ACGTCA",
                "CGCCGC",
                "AACCAA",
                "CCGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }
    def "succeeds when 1 ver and 1 reverse diagonal segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCGGAC",
                "AGTAAA",
                "CGAGCC",
                "AGTTAA",
                "CGGGCC"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "succeeds when 1 hor, 1 ver and 1 reverse diagonal segments"() {
        given:
        String [] dna =  [
                "AATTAA",
                "CCGGCT",
                "AGTTTA",
                "CGGTCC",
                "AGTTAA",
                "CGTTTT"
        ]
        when:
        boolean result = service.isMutant(dna)
        then:
        result == true

    }

    def "when call to detect must return result of isMutant and save"(){
        given:
        String [] dna = ["AATT",
                         "CCGG",
                         "AATT",
                         "CCGG"]

        DNAChainDTO dto = new DNAChainDTO(dna:dna)
        when: boolean result = service.detect(dto)
        then:
            1*dnaDao.saveDna(_,_) >> { args ->
                assert args[1] == false
                String[] dnaArg =  args[0]
                assert dnaArg[0].equals(dna[0])
                assert dnaArg[1].equals(dna[1])
                assert dnaArg[2].equals(dna[2])
                assert dnaArg[3].equals(dna[3])
            }
    }
}