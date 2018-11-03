import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2018-11-03.
 */
public class CollectorsToMapTest {
    
    @Test(expected = NullPointerException.class)
    public void null_valueMapper() {
        Stream.of(new Person("1")).collect(Collectors.toMap(Person::getName, null));
    }

    @Test(expected = NullPointerException.class)
    public void null_keyMapper() {
        Stream.of(new Person("1")).collect(Collectors.toMap(null, Person::getName));
    }

    @Test(expected = NullPointerException.class)
    public void valueMapper_returns_null() {
        Stream.of(new Person("1")).collect(Collectors.toMap(Person::getName, x -> null));
    }

    @Test
    public void keyMapper_returns_null() {
        Stream.of(new Person("1")).collect(Collectors.toMap(x -> null, Person::getName));
    }
}

class Person {
    String name;

    String getName() {
        return name;
    }

    public Person(String name) {
        this.name = name;
    }
}
