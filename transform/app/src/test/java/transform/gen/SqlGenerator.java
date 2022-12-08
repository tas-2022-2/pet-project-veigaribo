package transform.gen;

import org.junit.jupiter.api.Test;

import transform.Person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class SqlGeneratorTest {
    @Test
    void generateEmpty() {
        var sut = new SqlGenerator();
        var people = new ArrayList<Person>();
        var sql = "CREATE TABLE IF NOT EXISTS people (cpf CHAR(11), name VARCHAR(200), age INTEGER);\n\n";

        assertEquals(sql, sut.generate(people));
    }

    @Test
    void insert() {
        var sut = new SqlGenerator();
        var person = new Person("00000000100", "name 1", 18);
        var sql = "INSERT INTO people (cpf, name, age) VALUES ('00000000100', 'name 1', 18);";

        assertEquals(sql, sut.insert(person));
    }

    @Test
    void generate() {
        var sut = new SqlGenerator();

        var people = List.of(new Person("00000000100", "name 1", 18), new Person("00000000200", "name 2", 24),
                new Person("00000000300", "name 3", 32));

        var sql = "CREATE TABLE IF NOT EXISTS people (cpf CHAR(11), name VARCHAR(200), age INTEGER);\n\n"
                + "INSERT INTO people (cpf, name, age) VALUES ('00000000100', 'name 1', 18);\n"
                + "INSERT INTO people (cpf, name, age) VALUES ('00000000200', 'name 2', 24);\n"
                + "INSERT INTO people (cpf, name, age) VALUES ('00000000300', 'name 3', 32);\n";

        assertEquals(sql, sut.generate(people));
    }

    @Test
    void generateWithQuotes() {
        var sut = new SqlGenerator();

        var people = List.of(new Person("00000000100", "name '1", 18), new Person("00000000200", "' OR 1=1 --", 24));

        var sql = "CREATE TABLE IF NOT EXISTS people (cpf CHAR(11), name VARCHAR(200), age INTEGER);\n\n"
                + "INSERT INTO people (cpf, name, age) VALUES ('00000000100', 'name ''1', 18);\n"
                + "INSERT INTO people (cpf, name, age) VALUES ('00000000200', ''' OR 1=1 --', 24);\n";

        assertEquals(sql, sut.generate(people));
    }

    @Test
    void escape() {
        var sut = new SqlGenerator();
        var str = "a b' c '' d";

        assertEquals("a b'' c '''' d", sut.escape(str));
    }

    @Test
    void simplifyCpf() {
        var sut = new SqlGenerator();
        var str = "0.1.2-3 4 #5\n6";

        assertEquals("0123456", sut.simplifyCpf(str));
    }
}
