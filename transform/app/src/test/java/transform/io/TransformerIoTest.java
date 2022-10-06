package transform.io;

import org.junit.jupiter.api.Test;

import transform.transform.Transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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

    static class MockInputStream extends InputStream {
        public byte[] toreturn = null;
        public int i = 0;

        public MockInputStream(byte[] toreturn) {
            this.toreturn = toreturn;
        }

        @Override
        public int read() throws IOException {
            if (i >= toreturn.length) {
                return -1;
            }

            // get byte value as if it was unsigned
            int x = toreturn[i] & 0xFF;
            ++i;

            return x;
        }
    }

    static class MockOutputStream extends OutputStream {
        public List<Integer> calledWith = new ArrayList<>();

        @Override
        public void write(int b) throws IOException {
            calledWith.add(b);
        }
    }

    List<Integer> asListOfInt(byte[] bytes) {
        final var result = new ArrayList<Integer>(bytes.length);
        for (final var b : bytes) {
            result.add(Integer.valueOf(b));
        }
        return result;
    }

    @Test
    void transform() throws IOException {
        final var input = "abc";
        final var output = "cba";
        final var transformer = new MockTransformer(output);
        final var inputStream = new MockInputStream(null);
        final var outputStream = new MockOutputStream();

        final var sut = new TransformerIo(transformer, inputStream, outputStream);
        final var result = sut.transform(input);

        assertEquals(output, result);
        assertEquals(input, transformer.calledWith.get(0));

        sut.close();
    }

    @Test
    void pipeUtf8() throws IOException {
        final var input = "inpút";
        final var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        final var output = "túpni";
        final var outputInts = asListOfInt(output.getBytes(StandardCharsets.UTF_8));

        final var transformer = new MockTransformer(output);
        final var inputStream = new MockInputStream(inputBytes);
        final var outputStream = new MockOutputStream();

        final var sut = new TransformerIo(transformer, inputStream, outputStream, StandardCharsets.UTF_8);

        sut.pipe();

        assertEquals(input, transformer.calledWith.get(0));
        assertIterableEquals(outputInts, outputStream.calledWith);

        sut.close();
    }

    @Test
    void pipeUtf16() throws IOException {
        final var input = "inpút";
        final var inputBytes = input.getBytes(StandardCharsets.UTF_16);
        final var output = "túpni";
        final var outputInts = asListOfInt(output.getBytes(StandardCharsets.UTF_16));

        final var transformer = new MockTransformer(output);
        final var inputStream = new MockInputStream(inputBytes);
        final var outputStream = new MockOutputStream();

        final var sut = new TransformerIo(transformer, inputStream, outputStream, StandardCharsets.UTF_16);

        sut.pipe();

        assertEquals(input, transformer.calledWith.get(0));
        assertIterableEquals(outputInts, outputStream.calledWith);

        sut.close();
    }
}
