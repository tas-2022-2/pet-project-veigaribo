package transform.gen;

import java.util.List;

import transform.Person;

public interface Generator {
    public String generate(List<Person> people);
}
