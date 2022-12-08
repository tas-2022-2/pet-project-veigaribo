package transform.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import transform.Person;
import transform.gen.tags.*;

// HTML is basically a fuzzier XML so I think it works out
public class HtmlGenerator extends XmlGenerator {
    public final Pattern lineBreakPattern = Pattern.compile("\\r\\n|\\n|\\r");

    protected TagOrContent asTags(List<Person> people) {
        final var cpfTh = new Tag("th", new Content("CPF"));
        final var nameTh = new Tag("th", new Content("Name"));
        final var ageTh = new Tag("th", new Content("Age"));

        final var headerTr = new Tag("tr", cpfTh, nameTh, ageTh);
        final var thead = new Tag("thead", headerTr);

        final var tbody = new Tag("tbody");

        for (final var person : people) {
            final var tr = asTr(person);
            tbody.appendChild(tr);
        }

        final var table = new Tag("table", thead, tbody);
        final var body = new Tag("body", table);

        final var title = new Tag("title", new Content("People"));
        final var head = new Tag("head", title);

        final var html = new Tag("html", head, body);
        return html;
    }

    protected TagOrContent asTr(Person person) {
        final var cpfTd = new Tag("td", lines(person.cpf));
        final var nameTd = new Tag("td", lines(person.name));
        final var ageTd = new Tag("td", new Content("" + person.age));

        return new Tag("tr", cpfTd, nameTd, ageTd);
    }

    protected List<TagOrContent> lines(String x) {
        final var list = new ArrayList<TagOrContent>();
        final var lines = lineBreakPattern.split(x, -1);
        final var newLine = new Tag("br");

        for (int i = 0; i < lines.length * 2 - 1; ++i) {
            if (i % 2 == 0) {
                list.add(new Content(lines[i >> 1]));
            } else {
                list.add(newLine);
            }
        }

        return list;
    }
}
