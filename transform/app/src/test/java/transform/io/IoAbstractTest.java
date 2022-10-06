package transform.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IoAbstractTest {
    static class MockIo extends IoAbstract {
        public MockIo(InputStream in, OutputStream out) {
            super(in, out);
        }

        public MockIo(InputStream in, OutputStream out, Charset charset) {
            super(in, out, charset);
        }

        public List<String> calledWith = new ArrayList<>();

        @Override
        public String transform(String input) {
            calledWith.add(input);
            return input;
        }
    }

    public static class MockInputStream extends InputStream {
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

    public static class MockOutputStream extends OutputStream {
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
        final var output = input;
        final var inputStream = new MockInputStream(null);
        final var outputStream = new MockOutputStream();

        final var sut = new MockIo(inputStream, outputStream);
        final var result = sut.transform(input);

        assertEquals(output, result);
        sut.close();
    }

    @Test
    void pipeUtf8() throws IOException {
        final var input = "inpút";
        final var inputBytes = input.getBytes(StandardCharsets.UTF_8);
        final var output = input;
        final var outputInts = asListOfInt(output.getBytes(StandardCharsets.UTF_8));

        final var inputStream = new MockInputStream(inputBytes);
        final var outputStream = new MockOutputStream();

        final var sut = new MockIo(inputStream, outputStream, StandardCharsets.UTF_8);

        sut.pipe();

        assertEquals(input, sut.calledWith.get(0));
        assertIterableEquals(outputInts, outputStream.calledWith);

        sut.close();
    }

    @Test
    void pipeUtf16() throws IOException {
        final var input = "inpút";
        final var inputBytes = input.getBytes(StandardCharsets.UTF_16);
        final var output = input;
        final var outputInts = asListOfInt(output.getBytes(StandardCharsets.UTF_16));

        final var inputStream = new MockInputStream(inputBytes);
        final var outputStream = new MockOutputStream();

        final var sut = new MockIo(inputStream, outputStream, StandardCharsets.UTF_16);

        sut.pipe();

        assertEquals(input, sut.calledWith.get(0));
        assertIterableEquals(outputInts, outputStream.calledWith);

        sut.close();
    }

    @Test
    void utf8ByDefault() throws IOException {
        final var inputStream = new IoAbstractTest.MockInputStream(null);
        final var outputStream = new IoAbstractTest.MockOutputStream();

        final var sut = new MockIo(inputStream, outputStream);

        assertEquals(StandardCharsets.UTF_8, sut.charset);

        sut.close();
    }
}
