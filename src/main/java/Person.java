import lombok.Builder;
import lombok.Value;

/**
 * Created by mtumilowicz on 2018-11-04.
 */
@Value
@Builder
class Person {
    int id;
    String name;
}
