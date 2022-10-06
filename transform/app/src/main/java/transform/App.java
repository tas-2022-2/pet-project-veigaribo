package transform;

import java.io.IOException;

import transform.gen.Generator;
import transform.gen.HtmlGenerator;
import transform.gen.JsonGenerator;
import transform.gen.SqlGenerator;
import transform.gen.XmlGenerator;
import transform.io.TransformerIo;
import transform.parse.CsvParser;
import transform.parse.Parser;
import transform.transform.Transformer;
import transform.transform.ParserGeneratorTransformer;

public class App {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Output type argument not provided. Please specify one of json, xml, html or sql");
            return;
        }

        final var arg = args[0];
        final Generator gen;

        switch (arg) {
        case "json":
            gen = new JsonGenerator();
            break;
        case "xml":
            gen = new XmlGenerator();
            break;
        case "html":
            gen = new HtmlGenerator();
            break;
        case "sql":
            gen = new SqlGenerator();
            break;
        default:
            System.err.println(
                    "Invalid output type argument provided: " + arg + ". Please specify one of json, xml, html or sql");
            return;
        }

        final Parser parser = new CsvParser(true, ';', NewLineTypes.LF);
        final Transformer transformer = new ParserGeneratorTransformer(parser, gen);

        try (final var io = new TransformerIo(transformer, System.in, System.out)) {
            io.pipe();
        } catch (IOException error) {
            System.err.println("Xabum " + error);
        }
    }
}
