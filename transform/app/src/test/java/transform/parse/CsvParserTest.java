package transform.parse;

import org.junit.jupiter.api.Test;

import transform.NewLineTypes;
import transform.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class CsvParserTest {
    @Test
    void parseWithHeadersCommaLf() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLf2() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21\n" + "222.222.222.-22,betito,90\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLfNoTrailingNewline() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersTabLf() {
        var sut = new CsvParser(true, '\t', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "cpf\tnome\tidade\n333.444.333-33\tveiga' OR 1=1 --\t21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithoutHeadersCommaLf() {
        var sut = new CsvParser(false, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "333.444.333-33,veiga' OR 1=1 --,21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaCR() {
        var sut = new CsvParser(true, ',', NewLineTypes.CR);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "cpf,nome,idade\r333.444.333-33,veiga' OR 1=1 --,21\r";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaCR2() {
        var sut = new CsvParser(true, ',', NewLineTypes.CR);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\r333.444.333-33,veiga' OR 1=1 --,21\r" + "222.222.222.-22,betito,90\r";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaCrlf() {
        var sut = new CsvParser(true, ',', NewLineTypes.CRLF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21));

        var csv = "cpf,nome,idade\r\n333.444.333-33,veiga' OR 1=1 --,21\r\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaCrlf2() {
        var sut = new CsvParser(true, ',', NewLineTypes.CRLF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\r\n333.444.333-33,veiga' OR 1=1 --,21\r\n" + "222.222.222.-22,betito,90\r\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLfEmpty() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of();

        var csv = "cpf,nome,idade\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithoutHeadersCommaLfEmpty() {
        var sut = new CsvParser(false, ',', NewLineTypes.LF);

        var people = List.of();

        var csv = "\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithoutHeadersCommaLfEmptyNoTrailingNewline() {
        var sut = new CsvParser(false, ',', NewLineTypes.LF);

        var people = List.of();

        var csv = "";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLfNewlineInColumn() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga'\nOR\n1=1 --", 21));

        var csv = "cpf,nome,idade\n333.444.333-33,\"veiga'\nOR\n1=1 --\",21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaCrlfNewlineInColumn() {
        var sut = new CsvParser(true, ',', NewLineTypes.CRLF);

        var people = List.of(new Person("333.444.333-33", "veiga'\nOR\n1=1 --", 21));

        var csv = "cpf,nome,idade\r\n333.444.333-33,veiga'\nOR\n1=1 --,21\r\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLfQuoteInColumn() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' \"OR 1=1\" --", 21));

        var csv = "cpf,nome,idade\n333.444.333-33,\"veiga' \"\"OR 1=1\"\" --\",21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void parseWithHeadersCommaLfQuoteInColumnNotEnclosedInQuotes() {
        var sut = new CsvParser(true, ',', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' \"OR 1=1\" --", 21));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' \"OR 1=1\" --,21\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void igorSyntax() {
        var sut = new CsvParser(true, ';', NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        // trailing separator in each line
        var csv = "cpf;nome;idade;\n333.444.333-33;veiga' OR 1=1 --;21;\n" + "222.222.222.-22;betito;90;\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void lfByDefault() {
        var sut = new CsvParser(true, ',');

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21\n" + "222.222.222.-22,betito,90\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void commaByDefault() {
        var sut = new CsvParser(true, NewLineTypes.LF);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21\n" + "222.222.222.-22,betito,90\n";

        assertIterableEquals(people, sut.parse(csv));
    }

    @Test
    void commaLfByDefault() {
        var sut = new CsvParser(true);

        var people = List.of(new Person("333.444.333-33", "veiga' OR 1=1 --", 21),
                new Person("222.222.222.-22", "betito", 90));

        var csv = "cpf,nome,idade\n333.444.333-33,veiga' OR 1=1 --,21\n" + "222.222.222.-22,betito,90\n";

        assertIterableEquals(people, sut.parse(csv));
    }
}
