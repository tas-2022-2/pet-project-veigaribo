package transform.gen.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Tag implements TagOrContent {
    public final String name;
    public final Map<String, String> attributes;
    public final List<TagOrContent> children;

    public final Pattern spacePattern = Pattern.compile("\s");
    public final Pattern quotePattern = Pattern.compile("\"");

    public Tag(String name) {
        this(name, new HashMap<String, String>(), new ArrayList<TagOrContent>());
    }

    public Tag(String name, Map<String, String> attributes) {
        this(name, attributes, new ArrayList<TagOrContent>());
    }

    public Tag(String name, TagOrContent... children) {
        this(name, new HashMap<String, String>(), Arrays.asList(children));
    }

    public Tag(String name, List<TagOrContent> children) {
        this(name, new HashMap<String, String>(), children);
    }

    public Tag(String name, Map<String, String> attributes, TagOrContent... children) {
        this(name, attributes, Arrays.asList(children));
    }

    public Tag(String name, Map<String, String> attributes, List<TagOrContent> children) {
        this.name = name;
        this.attributes = attributes;
        this.children = children;
    }

    public void appendChild(TagOrContent child) {
        children.add(child);
    }

    @Override
    public String toString() {
        if (children.size() == 0) {
            return emptyTag(name, attributes);
        }

        final var builder = new StringBuilder();
        builder.append(openTag(name, attributes));

        for (final var child : children) {
            builder.append(child.toString());
        }

        builder.append(closeTag(name));
        return builder.toString();
    }

    private String formatAttribute(String key, String value) {
        final var builder = new StringBuilder();
        builder.append(stripSpaces(key));
        builder.append("=\"");
        builder.append(escapeQuotes(value));
        builder.append("\"");
        return builder.toString();
    }

    private String formatAttributes(Map<String, String> attributes) {
        if (attributes.size() == 0)
            return "";

        final var entries = attributes.entrySet().iterator();
        final var builder = new StringBuilder();

        final var head = entries.next();
        builder.append(formatAttribute(head.getKey(), head.getValue()));

        entries.forEachRemaining((attribute) -> {
            builder.append(" ");
            builder.append(formatAttribute(attribute.getKey(), attribute.getValue()));
        });

        return builder.toString();
    }

    private String emptyTag(String name, Map<String, String> attributes) {
        final var builder = new StringBuilder(name.length() + 2);
        builder.append("<");
        builder.append(stripSpaces(name));

        if (attributes.size() > 0) {
            builder.append(" ");
            builder.append(formatAttributes(attributes));
        }

        builder.append("/>");
        return builder.toString();
    }

    private String openTag(String name, Map<String, String> attributes) {
        final var builder = new StringBuilder(name.length() + 2);
        builder.append("<");
        builder.append(stripSpaces(name));

        if (attributes.size() > 0) {
            builder.append(" ");
            builder.append(formatAttributes(attributes));
        }

        builder.append(">");
        return builder.toString();
    }

    private String closeTag(String name) {
        final var builder = new StringBuilder(name.length() + 3);
        builder.append("</");
        builder.append(name);
        builder.append(">");

        return builder.toString();
    }

    public String stripSpaces(String x) {
        return spacePattern.matcher(x).replaceAll("");
    }

    public String escapeQuotes(String x) {
        return quotePattern.matcher(x).replaceAll("&quot;");
    }
}
