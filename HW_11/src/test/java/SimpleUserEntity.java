import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SimpleUserEntity {
    @Getter
    @Setter
    private long id;

    @Getter @Setter private String name;

    @Getter @Setter private int age;

    @Getter @Setter private String address;
}
