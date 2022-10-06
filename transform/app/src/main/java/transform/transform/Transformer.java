package transform.transform;

import transform.gen.Generator;
import transform.parse.Parser;

public class Transformer {
    public final Parser parser;
    public final Generator generator;

    public Transformer(Parser parser, Generator generator) {
        this.parser = parser;
        this.generator = generator;
    }

    public final String transform(String input) {
        final var people = this.parser.parse(input);
        return this.generator.generate(people);
    }
}
