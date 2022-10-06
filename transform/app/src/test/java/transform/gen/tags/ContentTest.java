package transform.gen.tags;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContentTest {
    @Test
    void simple() {
        var sut = new Content("lorem ipsum dolor sit amet");
        assertEquals("lorem ipsum dolor sit amet", sut.toString());
    }

    @Test
    void withAngleBrackets() {
        var sut = new Content("lorem <ipsum dolor>> sit");
        assertEquals("lorem &lt;ipsum dolor&gt;&gt; sit", sut.toString());
    }

    @Test
    void escapeTags() {
        var sut = new Content("");
        var str = "[{<(ab<c>)>}]";

        assertEquals("[{&lt;(ab&lt;c&gt;)&gt;}]", sut.escapeTags(str));
    }
}
