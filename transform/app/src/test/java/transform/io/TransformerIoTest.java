package transform.io;

import org.junit.jupiter.api.Test;

import transform.transform.Transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class TransformerIoTest {
    static class MockTransformer implements Transformer {
        public String toreturn = null;
        public List<String> calledWith = new ArrayList<>();

        public MockTransformer(String toreturn) {
            this.toreturn = toreturn;
        }

        @Override
        public String transform(String input) {
            calledWith.add(input);
            return toreturn;
        }
    }

    @Test
    void transform() throws IOException {
        final var input = "abc";
        final var output = "cba";
        final var transformer = new MockTransformer(output);
        final var inputStream = new IoAbstractTest.MockInputStream(null);
        final var outputStream = new IoAbstractTest.MockOutputStream();

        final var sut = new TransformerIo(transformer, inputStream, outputStream, StandardCharsets.UTF_8);
        final var result = sut.transform(input);

        assertEquals(output, result);
        assertEquals(input, transformer.calledWith.get(0));

        sut.close();
    }

    @Test
    void utf8ByDefault() throws IOException {
        final var transformer = new MockTransformer(null);
        final var inputStream = new IoAbstractTest.MockInputStream(null);
        final var outputStream = new IoAbstractTest.MockOutputStream();

        final var sut = new TransformerIo(transformer, inputStream, outputStream);

        assertEquals(StandardCharsets.UTF_8, sut.charset);

        sut.close();
    }
}
