import ferromera.mutantdetector.util.Gzip
import spock.lang.Specification

class GzipSpec extends Specification{

    def "compress a large repetitive string"(){
        given: def input = "ATATATTATTATTTATTAAAAAATTTTATTTATTTATAAATTTATTTATTTTTTTAAATTTATATTATTAATTTATATTTATTTTTTAAAA";
        when: byte[] output = Gzip.compress(input)
        then: output.length < input.length()
    }

    def "obtain de same string after compressing"(){
        given: def input="ATGCGGGCCTTAGCCCTAAGCACCA"
        when: byte[] output = Gzip.compress(input)
        def decompress = Gzip.decompress(output)
        then:
            new String(decompress).equals(input)

    }

    def "compress a medium string"(){
        given: def input = "ATCGCTAAAAAAAAAAAAAAAAATCGATGAAGCTAGCTAGCATGGCTAC";
        when: byte[] output = Gzip.compress(input)
        then: output.length < input.length()
    }
}
