package transform.gen;

import org.junit.jupiter.api.Test;

import transform.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class JsonGeneratorTest {
    @Test
    void generateEmpty() {
        var sut = new JsonGenerator();
        var people = new ArrayList<Person>();
        var json = "[]";

        assertEquals(json, sut.generate(people));
    }

    @Test
    void generatePerson() {
        var sut = new JsonGenerator();
        var person = new Person("00000000100", "name 1", 18);
        var json = "{\"cpf\":\"00000000100\",\"name\":\"name 1\",\"age\":18}";

        assertEquals(json, sut.generate(person));
    }

    @Test
    void generate() {
        var sut = new JsonGenerator();

        var people = List.of(new Person("00000000100", "name 1", 18), new Person("00000000200", "name 2", 24),
                new Person("00000000300", "name 3", 32));

        var json = "[" + "{\"cpf\":\"00000000100\",\"name\":\"name 1\",\"age\":18},"
                + "{\"cpf\":\"00000000200\",\"name\":\"name 2\",\"age\":24},"
                + "{\"cpf\":\"00000000300\",\"name\":\"name 3\",\"age\":32}" + "]";

        assertEquals(json, sut.generate(people));
    }

    @Test
    void generateWithQuotes() {
        var sut = new JsonGenerator();

        var people = List.of(new Person("00000000100", "name \"1\"", 18));

        var json = "[" + "{\"cpf\":\"00000000100\",\"name\":\"name \\\"1\\\"\",\"age\":18}" + "]";

        assertEquals(json, sut.generate(people));
    }

    @Test
    void generateWithNewLine() {
        var sut = new JsonGenerator();

        var people = List.of(new Person("00000000100", "name\n1\n", 18));

        var json = "[" + "{\"cpf\":\"00000000100\",\"name\":\"name\\n1\\n\",\"age\":18}" + "]";

        assertEquals(json, sut.generate(people));
    }

    @Test
    void escapeNewLine() {
        var sut = new JsonGenerator();
        var str = "a b\nc d\n e f";

        assertEquals("a b\\nc d\\n e f", sut.escapeNewLine(str));
    }

    @Test
    void escapeQuotes() {
        var sut = new JsonGenerator();
        var str = "a b\"c d\" e f";

        assertEquals("a b\\\"c d\\\" e f", sut.escapeQuotes(str));
    }

    @Test
    void escape() {
        var sut = new JsonGenerator();
        var str = "a\nb\"c d\" e\nf";

        assertEquals("a\\nb\\\"c d\\\" e\\nf", sut.escape(str));
    }
}
