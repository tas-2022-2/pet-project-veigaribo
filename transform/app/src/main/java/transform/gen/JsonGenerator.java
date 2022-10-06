package transform.gen;

import java.util.List;
import java.util.regex.Pattern;

import transform.Person;

public class JsonGenerator implements Generator {
    public final Pattern lfPattern = Pattern.compile("\\n");
    public final Pattern crPattern = Pattern.compile("\\r");
    public final Pattern quotePattern = Pattern.compile("\"");

    @Override
    public String generate(List<Person> people) {
        final var peopleSize = people.size();

        if (peopleSize == 0)
            return "[]";

        final var builder = new StringBuilder();
        builder.append("[");

        builder.append(generate(people.get(0)));

        for (int i = 1; i < people.size(); ++i) {
            builder.append(",");
            builder.append(generate(people.get(i)));
        }

        builder.append("]");

        return builder.toString();
    }

    public String generate(Person person) {
        final var builder = new StringBuilder();
        builder.append("{\"cpf\":\"");
        builder.append(escape(person.cpf));
        builder.append("\",\"name\":\"");
        builder.append(escape(person.name));
        builder.append("\",\"age\":");
        builder.append(person.age);
        builder.append("}");

        return builder.toString();
    }

    public String escape(String x) {
        return escapeNewLine(escapeQuotes(x));
    }

    public String escapeNewLine(String x) {
        return crPattern.matcher(lfPattern.matcher(x).replaceAll("\\\\n")).replaceAll("\\\\r");
    }

    public String escapeQuotes(String x) {
        return quotePattern.matcher(x).replaceAll("\\\\\"");
    }
}
