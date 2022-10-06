package transform.gen;

import java.util.List;
import java.util.regex.Pattern;

import transform.Person;

public class SqlGenerator implements Generator {
    public final Pattern quotePattern = Pattern.compile("'");
    public final Pattern simplifyCpfPattern = Pattern.compile("[^0-9]");

    @Override
    public String generate(List<Person> people) {
        final var builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS people (cpf CHAR(11), name VARCHAR(200), age INTEGER);\n\n");

        for (final var person : people) {
            builder.append(insert(person));
            builder.append("\n");
        }

        return builder.toString();
    }

    public String insert(Person person) {
        final var builder = new StringBuilder();
        builder.append("INSERT INTO people (cpf, name, age) VALUES ('");
        builder.append(simplifyCpf(person.cpf));
        builder.append("', '");
        builder.append(escape(person.name));
        builder.append("', ");
        builder.append(person.age);
        builder.append(");");

        return builder.toString();
    }

    public String escape(String string) {
        return quotePattern.matcher(string).replaceAll("''");
    }

    public String simplifyCpf(String cpf) {
        return simplifyCpfPattern.matcher(cpf).replaceAll("");
    }
}
