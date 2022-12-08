package transform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

class PersonTest {
    @Test
    void equalsId() {
        final var person1 = new Person("cpf", "name", 18);

        assertTrue(person1.equals(person1));
    }

    @Test
    void equalsTrue() {
        final var person1 = new Person("cpf", "name", 18);
        final var person2 = new Person("cpf", "name", 18);

        assertTrue(person1.equals(person2));
    }

    @Test
    void equalsDifferentCpf() {
        final var person1 = new Person("cpf", "name", 18);
        final var person2 = new Person("cpf2", "name", 18);

        assertFalse(person1.equals(person2));
    }

    @Test
    void equalsNullCpf() {
        final var person1 = new Person(null, "name", 18);
        final var person2 = new Person(null, "name", 18);

        assertTrue(person1.equals(person2));
    }

    @Test
    void equalsNullDifferentCpf() {
        final var person1 = new Person(null, "name", 18);
        final var person2 = new Person("cpf", "name", 18);

        assertFalse(person1.equals(person2));
    }

    @Test
    void equalsDifferentName() {
        final var person1 = new Person("cpf", "name", 18);
        final var person2 = new Person("cpf", "name2", 18);

        assertFalse(person1.equals(person2));
    }

    @Test
    void equalsNullName() {
        final var person1 = new Person("cpf", null, 18);
        final var person2 = new Person("cpf", null, 18);

        assertTrue(person1.equals(person2));
    }

    @Test
    void equalsNullDifferentName() {
        final var person1 = new Person("cpf", null, 18);
        final var person2 = new Person("cpf", "name", 18);

        assertFalse(person1.equals(person2));
    }

    @Test
    void equalsDifferentAge() {
        final var person1 = new Person("cpf", "name", 18);
        final var person2 = new Person("cpf", "name", 182);

        assertFalse(person1.equals(person2));
    }

    @Test
    void equalsNull() {
        final var person1 = new Person("cpf", "name", 18);

        assertFalse(person1.equals(null));
    }

    @Test
    void equalsDifferentClass() {
        final var person1 = new Person("cpf", "name", 18);

        assertFalse(person1.equals(Instant.now()));
    }

    @Test
    void ttoString() {
        final var person1 = new Person("cpf", "name", 18);

        assertEquals("name (cpf), 18 years old", person1.toString());
    }

    static class BuilderTest {
        @Test
        void withCpf() {
            final var sut = new Person.Builder().withCpf("1234");
            final var expected = new Person("1234", null, 0);

            assertEquals(expected, sut.build());
        }

        @Test
        void withName() {
            final var sut = new Person.Builder().withName("name");
            final var expected = new Person(null, "name", 0);

            assertEquals(expected, sut.build());
        }

        @Test
        void withAge() {
            final var sut = new Person.Builder().withAge(20);
            final var expected = new Person(null, null, 20);

            assertEquals(expected, sut.build());
        }

        @Test
        void complete() {
            final var sut = new Person.Builder().withCpf("1234").withName("name").withAge(20);
            final var expected = new Person("1234", "name", 20);

            assertEquals(expected, sut.build());
        }
    }
}
