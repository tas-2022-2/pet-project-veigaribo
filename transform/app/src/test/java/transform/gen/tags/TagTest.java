package transform.gen.tags;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static transform.testutil.Assertions.*;

import java.util.Map;

class TagTest {
    @Test
    void emptyTagNoAttrToString() {
        var sut = new Tag("tag");
        assertEquals("<tag/>", sut.toString());
    }

    @Test
    void emptyTagWithAttrToString() {
        var attrs = Map.of("abc", "def", "mno", "pqr");
        var sut = new Tag("tag", attrs);
        String[] options = { "<tag abc=\"def\" mno=\"pqr\"/>", "<tag mno=\"pqr\" abc=\"def\"/>" };

        assertEqualsAny(options, sut.toString());
    }

    @Test
    void nestedNoAttrTagNoAttrToString() {
        var child = new Tag("child");
        var sut = new Tag("tag", child);

        assertEquals("<tag><child/></tag>", sut.toString());
    }

    @Test
    void nestedWithAttrTagNoAttrToString() {
        var attrs = Map.of("abc", "def", "mno", "pqr");
        var child = new Tag("child", attrs);
        var sut = new Tag("tag", child);

        String[] options = { "<tag><child abc=\"def\" mno=\"pqr\"/></tag>",
                "<tag><child mno=\"pqr\" abc=\"def\"/></tag>" };

        assertEqualsAny(options, sut.toString());
    }

    @Test
    void nestedWithAttrTagWithAttrToString() {
        var attrs = Map.of("abc", "def", "mno", "pqr");
        var child = new Tag("child", attrs);
        var sut = new Tag("tag", attrs, child);

        String[] options = { "<tag abc=\"def\" mno=\"pqr\"><child abc=\"def\" mno=\"pqr\"/></tag>",
                "<tag abc=\"def\" mno=\"pqr\"><child mno=\"pqr\" abc=\"def\"/></tag>",
                "<tag mno=\"pqr\" abc=\"def\"><child abc=\"def\" mno=\"pqr\"/></tag>",
                "<tag mno=\"pqr\" abc=\"def\"><child mno=\"pqr\" abc=\"def\"/></tag>", };

        assertEqualsAny(options, sut.toString());
    }

    @Test
    void spacesInName() {
        var sut = new Tag("tag tag tag");

        assertEquals("<tagtagtag/>", sut.toString());
    }

    @Test
    void spacesInAttrKey() {
        var attrs = Map.of("a b c", "def");
        var sut = new Tag("tag", attrs);

        assertEquals("<tag abc=\"def\"/>", sut.toString());
    }

    @Test
    void quotesInAttrValue() {
        var attrs = Map.of("abc", "d \"e\" f");
        var sut = new Tag("tag", attrs);

        assertEquals("<tag abc=\"d &quot;e&quot; f\"/>", sut.toString());
    }

    @Test
    void appendChild() {
        var child = new Tag("child");
        var sut = new Tag("tag");

        sut.appendChild(child);
        assertEquals("<tag><child/></tag>", sut.toString());

        sut.appendChild(child);
        assertEquals("<tag><child/><child/></tag>", sut.toString());
    }

    @Test
    void stripSpaces() {
        var sut = new Tag("");
        var str = " a b  c   d    e  ";

        assertEquals("abcde", sut.stripSpaces(str));
    }

    @Test
    void escapeQuotes() {
        var sut = new Tag("");
        var str = "'\"a\" \"\"b\"'";

        assertEquals("'&quot;a&quot; &quot;&quot;b&quot;'", sut.escapeQuotes(str));
    }
}
