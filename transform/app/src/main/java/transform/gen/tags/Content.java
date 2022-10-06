package transform.gen.tags;

import java.util.regex.Pattern;

public class Content implements TagOrContent {
    final String content;

    final Pattern ltPattern = Pattern.compile("<");
    final Pattern gtPattern = Pattern.compile(">");

    public Content(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return escapeTags(content);
    }

    public String escapeTags(String x) {
        final var noLt = ltPattern.matcher(x).replaceAll("&lt;");
        final var noLtGt = gtPattern.matcher(noLt).replaceAll("&gt;");
        return noLtGt;
    }
}
