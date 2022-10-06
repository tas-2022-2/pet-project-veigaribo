package transform.io;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import transform.transform.Transformer;

// Template
public abstract class IoAbstract implements Closeable, Transformer {
    public final BufferedInputStream breader;
    public final OutputStream out;
    public final Charset charset;

    public IoAbstract(InputStream in, OutputStream out) {
        this(in, out, StandardCharsets.UTF_8);
    }

    public IoAbstract(InputStream in, OutputStream out, Charset charset) {
        this.breader = new BufferedInputStream(in);
        this.out = out;
        this.charset = charset;
    }

    @Override
    public abstract String transform(String input);

    public void pipe() throws IOException {
        final var bytes = breader.readAllBytes();
        final var input = new String(bytes, charset);

        final var output = transform(input);

        out.write(output.getBytes(charset));
    }

    @Override
    public void close() throws IOException {
        breader.close();
        out.close();
    }
}
