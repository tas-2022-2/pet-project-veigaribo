package transform.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import transform.transform.Transformer;

public class TransformerIo extends IoAbstract {
    public final Transformer transformer;

    public TransformerIo(Transformer transformer, InputStream in, OutputStream out) {
        this(transformer, in, out, StandardCharsets.UTF_8);
    }

    public TransformerIo(Transformer transformer, InputStream in, OutputStream out, Charset charset) {
        super(in, out, charset);
        this.transformer = transformer;
    }

    @Override
    public String transform(String input) {
        return transformer.transform(input);
    }
}
