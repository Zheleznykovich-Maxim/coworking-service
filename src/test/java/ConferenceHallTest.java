import org.example.coworking.model.ConferenceHall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceHallTest {

    private ConferenceHall conferenceHall;

    @BeforeEach
    public void setUp() {
        conferenceHall = new ConferenceHall(1, "Main Hall", true);
    }

    @Test
    public void testConstructorAndGetters() {
        assertThat(conferenceHall.getId()).isEqualTo(1);
        assertThat(conferenceHall.getName()).isEqualTo("Main Hall");
        assertThat(conferenceHall.isAvailable()).isTrue();
    }

    @Test
    public void testSetters() {
        conferenceHall.setId(2);
        conferenceHall.setName("Small Hall");
        conferenceHall.setAvailable(false);

        assertThat(conferenceHall.getId()).isEqualTo(2);
        assertThat(conferenceHall.getName()).isEqualTo("Small Hall");
        assertThat(conferenceHall.isAvailable()).isFalse();
    }

    @Test
    public void testToString() {
        String expectedString = "ConferenceHall(id=1, name=Main Hall, isAvailable=true)";
        assertThat(conferenceHall.toString()).isEqualTo(expectedString);
    }
}
