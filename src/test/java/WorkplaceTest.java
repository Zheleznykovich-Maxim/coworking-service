import org.example.coworking.domain.model.Workplace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Workplace Class Tests")
public class WorkplaceTest {

    private Workplace workplace;

    @BeforeEach
    public void setUp() {
        workplace = new Workplace(1, "Desk A", true);
    }

    @Test
    @DisplayName("Test Constructor and Getters")
    public void testConstructorAndGetters() {
        assertThat(workplace.getId()).isEqualTo(1);
        assertThat(workplace.getName()).isEqualTo("Desk A");
        assertThat(workplace.isAvailable()).isTrue();
    }

    @Test
    @DisplayName("Test Setters")
    public void testSetters() {
        workplace.setId(2);
        workplace.setName("Desk B");
        workplace.setAvailable(false);

        assertThat(workplace.getId()).isEqualTo(2);
        assertThat(workplace.getName()).isEqualTo("Desk B");
        assertThat(workplace.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("Test toString() Method")
    public void testToString() {
        String expectedString = "Workplace(id=1, name=Desk A, isAvailable=true)";
        assertThat(workplace.toString()).isEqualTo(expectedString);
    }
}
