import org.example.coworking.model.Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkplaceTest {

    private Workplace workplace;

    @BeforeEach
    public void setUp() {
        workplace = new Workplace(1, "Desk A", true);
    }

    @Test
    public void testConstructorAndGetters() {
        assertThat(workplace.getId()).isEqualTo(1);
        assertThat(workplace.getName()).isEqualTo("Desk A");
        assertThat(workplace.isAvailable()).isTrue();
    }

    @Test
    public void testSetters() {
        workplace.setId(2);
        workplace.setName("Desk B");
        workplace.setAvailable(false);

        assertThat(workplace.getId()).isEqualTo(2);
        assertThat(workplace.getName()).isEqualTo("Desk B");
        assertThat(workplace.isAvailable()).isFalse();
    }

    @Test
    public void testToString() {
        String expectedString = "Workplace{id=1, name='Desk A', isAvailable=true}";
        assertThat(workplace.toString()).isEqualTo(expectedString);
    }
}
