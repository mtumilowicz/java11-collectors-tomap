import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by mtumilowicz on 2018-11-03.
 */
public class CollectorsToMapTest {
    
    private final Person p1 = Person.builder()
            .id(1)
                .name("name1")
                .build();
    
    private final Person p1_1 = Person.builder()
            .id(1)
            .name("name1_1")
            .build();
    
    private final Person p2 = Person.builder()
            .id(2)
            .name("name2")
            .build();

    @Test(expected = NullPointerException.class)
    public void null_valueMapper() {
        Stream.of(p1).collect(Collectors.toMap(Person::getName, null));
    }

    @Test(expected = NullPointerException.class)
    public void null_keyMapper() {
        Stream.of(p1).collect(Collectors.toMap(null, Person::getName));
    }

    @Test(expected = NullPointerException.class)
    public void valueMapper_returns_null() {
        Stream.of(p1).collect(Collectors.toMap(Person::getName, x -> null));
    }

    @Test
    public void keyMapper_returns_null() {
        Stream.of(p1).collect(Collectors.toMap(x -> null, Person::getName));
    }

    @Test(expected = IllegalStateException.class)
    public void key_value__same_keys() {
        var persons = List.of(p1, p1_1);

        /*
        message:
            exception message: java.lang.IllegalStateException: Duplicate key 1 
            (attempted merging values Person(id=1, name=name1) and Person(id=1, name=name2))
        */
        persons.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
    }

    @Test
    public void key_value__different_keys() {
        var persons = List.of(p1, p2);

        Map<Integer, Person> personMap = persons.stream()
                .collect(Collectors.toMap(Person::getId, Function.identity()));

        assertThat(personMap.size(), is(2));
        assertThat(personMap.get(1), is(p1));
        assertThat(personMap.get(2), is(p2));
    }

    @Test
    public void key_value_mergeFunction_takeFirst__same_keys() {
        var persons = List.of(p1, p1_1);

        Map<Integer, Person> personMap = persons.stream()
                .collect(Collectors.toMap(Person::getId, Function.identity(), (x, y) -> x));

        assertThat(personMap.size(), is(1));
        assertThat(personMap.get(1), is(p1));
        assertThat(personMap.get(1), not(is(p1_1)));
    }

    @Test
    public void key_value_mergeFunction_takeLast__same_keys() {
        var persons = List.of(p1, p1_1);

        Map<Integer, Person> personMap = persons.stream()
                .collect(Collectors.toMap(Person::getId, Function.identity(), (x, y) -> y));

        assertThat(personMap.size(), is(1));
        assertThat(personMap.get(1), is(p1_1));
        assertThat(personMap.get(1), not(is(p1)));
    }

    @Test(expected = IllegalStateException.class)
    public void key_value_mergeFunction_exception__same_keys() {
        var persons = List.of(p1, p1_1);

        Map<Integer, Person> personMap = persons.stream()
                .collect(Collectors.toMap(Person::getId,
                        Function.identity(),
                        (x, y) -> {
                            throw new IllegalStateException();
                        }));
    }
    
    @Test
    public void key_value_mergeFunction_exception__treeSet__different_keys() {
        var persons = List.of(p1, p2);

        TreeMap<Integer, Person> personMap = persons.stream()
                .collect(Collectors.toMap(Person::getId,
                        Function.identity(),
                        (x, y) -> {
                            throw new IllegalStateException();
                        },
                        () -> new TreeMap<Integer, Person>(Comparator.comparingInt(Integer::intValue).reversed()))
                );

        assertThat(personMap.size(), is(2));
        assertThat(personMap.firstEntry().getValue(), is(p2));
        assertThat(personMap.lastEntry().getValue(), is(p1));
    }
}

