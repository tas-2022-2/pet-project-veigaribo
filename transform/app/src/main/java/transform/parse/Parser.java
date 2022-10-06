package transform.parse;

import java.util.List;

import transform.Person;

public interface Parser {
    public List<Person> parse(String input);
}
