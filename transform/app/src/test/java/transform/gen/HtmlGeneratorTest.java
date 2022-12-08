package transform.gen;

import org.junit.jupiter.api.Test;

import transform.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class HtmlGeneratorTest {
    @Test
    void generateEmpty() {
        var sut = new HtmlGenerator();
        var people = new ArrayList<Person>();
        var html = "<html>" + "<head><title>People</title></head>" + "<body><table>"
                + "<thead><tr><th>CPF</th><th>Name</th><th>Age</th></tr></thead>" + "<tbody/>" + "</table></body>"
                + "</html>";

        assertEquals(html, sut.generate(people));
    }

    @Test
    void generate() {
        var sut = new HtmlGenerator();

        var people = List.of(new Person("00000000100", "name 1", 18), new Person("00000000200", "name 2", 24),
                new Person("00000000300", "name 3", 32));

        var html = "<html>" + "<head><title>People</title></head>" + "<body><table>"
                + "<thead><tr><th>CPF</th><th>Name</th><th>Age</th></tr></thead>" + "<tbody>"
                + "<tr><td>00000000100</td><td>name 1</td><td>18</td></tr>"
                + "<tr><td>00000000200</td><td>name 2</td><td>24</td></tr>"
                + "<tr><td>00000000300</td><td>name 3</td><td>32</td></tr>" + "</tbody>" + "</table></body>"
                + "</html>";

        assertEquals(html, sut.generate(people));
    }

    @Test
    void generateWithLineBreaks() {
        var sut = new HtmlGenerator();

        var people = List.of(new Person("00000000100", "name\n1", 18), new Person("00000000200", "\nname 2\n", 24));

        var html = "<html>" + "<head><title>People</title></head>" + "<body><table>"
                + "<thead><tr><th>CPF</th><th>Name</th><th>Age</th></tr></thead>" + "<tbody>"
                + "<tr><td>00000000100</td><td>name<br/>1</td><td>18</td></tr>"
                + "<tr><td>00000000200</td><td><br/>name 2<br/></td><td>24</td></tr>" + "</tbody>" + "</table></body>"
                + "</html>";

        assertEquals(html, sut.generate(people));
    }
}
