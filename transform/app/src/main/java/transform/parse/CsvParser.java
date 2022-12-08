package transform.parse;

import java.util.ArrayList;
import java.util.List;

import transform.NewLineTypes;
import transform.Person;

public class CsvParser implements Parser {
    public final char sep;
    public final boolean headers;
    public final NewLineTypes newLineType;

    public CsvParser(boolean headers) {
        this(headers, ',', NewLineTypes.LF);
    }

    public CsvParser(boolean headers, char sep) {
        this(headers, sep, NewLineTypes.LF);
    }

    public CsvParser(boolean headers, NewLineTypes newLineType) {
        this(headers, ',', newLineType);
    }

    public CsvParser(boolean headers, char sep, NewLineTypes newLineType) {
        this.headers = headers;
        this.sep = sep;
        this.newLineType = newLineType;
    }

    public List<Person> parse(String input) {
        final var machine = new StateMachine(headers, sep, newLineType);
        final var chars = input.toCharArray();

        for (final var token : chars) {
            machine.consume(token);
        }

        machine.finish();
        return machine.getCurrentPeople();
    }

    // Here be dragons
    protected class StateMachine {
        private int lineIndex = 0;
        private boolean parsingNewline = false;
        private boolean parsingColumn = false;
        private byte columnIndex = 0;
        private boolean insideQuotes = false;
        private boolean lookingForQuoteEscape = false;
        private Person.Builder currentPerson = new Person.Builder();
        private StringBuilder currentColumn = new StringBuilder();
        private List<Person> currentPeople = new ArrayList<Person>();

        public final boolean headers;
        public final char sep;
        public final NewLineTypes newlineType;

        public StateMachine(boolean headers, char sep, NewLineTypes newlineType) {
            this.headers = headers;
            this.sep = sep;
            this.newlineType = newlineType;
        }

        public void consume(char token) {
            final boolean isNewline = this.isNewline(token);

            if (shouldSkip(token)) {
                return;
            }

            if (lineIndex == 0 && headers) {
                if (isNewline) {
                    ++lineIndex;
                    return;
                }

                return;
            }

            if (isNewline && (!insideQuotes || insideQuotes && lookingForQuoteEscape) && columnIndex >= 2) {
                setColumnToPerson();

                final var person = currentPerson.build();
                currentPeople.add(person);

                insideQuotes = false;
                lookingForQuoteEscape = false;
                ++lineIndex;
                columnIndex = 0;
                currentColumn.setLength(0);
                return;
            }

            if (token == sep && (!insideQuotes || insideQuotes && lookingForQuoteEscape)) {
                setColumnToPerson();

                insideQuotes = false;
                lookingForQuoteEscape = false;
                ++columnIndex;
                currentColumn.setLength(0);
                return;
            }

            if (!insideQuotes && token == '"' && currentColumn.length() == 0) {
                insideQuotes = true;
                return;
            }

            if (insideQuotes) {
                if (lookingForQuoteEscape) {
                    lookingForQuoteEscape = false;

                    if (token != '"') {
                        return;
                    }
                } else {
                    if (token == '"') {
                        lookingForQuoteEscape = true;
                        return;
                    }
                }
            }

            currentColumn.append(token);
        }

        public boolean isNewline(char token) {
            // Ifs for readability
            if (newlineType == NewLineTypes.LF && token == '\n') {
                return true;
            }

            if (newlineType == NewLineTypes.CR && token == '\r') {
                return true;
            }

            if (newlineType == NewLineTypes.CRLF && token == '\n' && parsingNewline) {
                parsingNewline = false;
                return true;
            }

            if (newlineType == NewLineTypes.CRLF && token == '\r') {
                parsingNewline = true;
            } else {
                parsingNewline = false;
            }

            return false;
        }

        // should be called after isNewline
        public boolean shouldSkip(char token) {
            return parsingNewline;
        }

        public void setColumnToPerson() {
            switch (columnIndex) {
            case 0:
                currentPerson.withCpf(currentColumn.toString());
                break;
            case 1:
                currentPerson.withName(currentColumn.toString());
                break;
            case 2:
                currentPerson.withAge(Integer.parseInt(currentColumn.toString()));
                break;
            }
        }

        public boolean isWaitingForInput() {
            return !parsingColumn;
        }

        public void finish() {
            if (columnIndex >= 2) {
                setColumnToPerson();

                final var person = currentPerson.build();
                currentPeople.add(person);

                // state left dirty
            }
        }

        public List<Person> getCurrentPeople() {
            return currentPeople;
        }
    }
}
