package transform.gen;

import java.util.List;

import transform.Person;
import transform.gen.tags.*;

public class XmlGenerator implements Generator {
    @Override
    public String generate(List<Person> people) {
        return asTags(people).toString();
    }

    protected TagOrContent asTags(List<Person> people) {
        final var root = new Tag("people");

        for (final var person : people) {
            final var cpf = new Tag("cpf", new Content(person.cpf));
            final var name = new Tag("name", new Content(person.name));
            final var age = new Tag("age", new Content("" + person.age));

            final var personTag = new Tag("person", cpf, name, age);

            root.appendChild(personTag);
        }

        return root;
    }
}
