package transform.gen;

import org.junit.jupiter.api.Test;

import transform.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class XmlGeneratorTest {
    @Test
    void generateEmpty() {
        var sut = new XmlGenerator();
        var people = new ArrayList<Person>();
        var xml = "<people/>";

        assertEquals(xml, sut.generate(people));
    }

    @Test
    void generate() {
        var sut = new XmlGenerator();

        var people = List.of(new Person("00000000100", "name 1", 18), new Person("00000000200", "name 2", 24),
                new Person("00000000300", "name 3", 32));

        var xml = "<people>" + "<person><cpf>00000000100</cpf><name>name 1</name><age>18</age></person>"
                + "<person><cpf>00000000200</cpf><name>name 2</name><age>24</age></person>"
                + "<person><cpf>00000000300</cpf><name>name 3</name><age>32</age></person>" + "</people>";

        assertEquals(xml, sut.generate(people));
    }
}
