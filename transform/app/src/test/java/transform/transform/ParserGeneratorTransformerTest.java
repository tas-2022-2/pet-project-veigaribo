package transform.transform;

import org.junit.jupiter.api.Test;

import transform.Person;
import transform.gen.Generator;
import transform.parse.Parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ParserGeneratorTransformerTest {
    static class MockParser implements Parser {
        public List<Person> toreturn = null;
        public List<String> calledWith = new ArrayList<>();

        public MockParser(List<Person> toreturn) {
            this.toreturn = toreturn;
        }

        @Override
        public List<Person> parse(String input) {
            calledWith.add(input);
            return toreturn;
        }
    }

    static class MockGenerator implements Generator {
        public String toreturn = null;
        public List<List<Person>> calledWith = new ArrayList<>();

        public MockGenerator(String toreturn) {
            this.toreturn = toreturn;
        }

        @Override
        public String generate(List<Person> people) {
            calledWith.add(people);
            return toreturn;
        }
    }

    @Test
    void transform() {
        final var input = "a,b,c";
        final var people = List.of(new Person("a", "b", 12));
        final var output = "c,b,a";

        final var parser = new MockParser(people);
        final var generator = new MockGenerator(output);

        final var sut = new ParserGeneratorTransformer(parser, generator);
        final var result = sut.transform(input);

        assertEquals(output, result);
        assertEquals(input, parser.calledWith.get(0));
        assertIterableEquals(people, generator.calledWith.get(0));
    }
}
