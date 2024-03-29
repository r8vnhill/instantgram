package instantgram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ignacio Slater Muñoz
 * @version 1.0rc1
 * @since 0.1
 */
public class IGUserTest {

  final private String
      CAROL_DANCE = "Carol Dance",
      JADEN_SMITH = "Jaden Smith",
      ELON_MUSK = "Elon Musk";

  private IGUser
      carolDance,
      jadenSmith,
      elonMusk;

  @BeforeEach
  public void setUp() {
    carolDance = new IGUser(CAROL_DANCE);
    jadenSmith = new IGUser(JADEN_SMITH);
    elonMusk = new IGUser(ELON_MUSK);
  }

  @Test
  public void testUserCreation() {
    assertEquals(CAROL_DANCE, carolDance.getName());
    assertEquals(JADEN_SMITH, jadenSmith.getName());
    assertEquals(ELON_MUSK, elonMusk.getName());

    assertTrue(elonMusk.getFollowers().isEmpty());
    assertTrue(jadenSmith.getFollowers().isEmpty());
    assertTrue(carolDance.getFollowers().isEmpty());

    assertTrue(elonMusk.getFeed().isEmpty());
    assertTrue(jadenSmith.getFeed().isEmpty());
    assertTrue(carolDance.getFeed().isEmpty());
  }

  @Test
  public void testFollow() {
    carolDance.follow(elonMusk);
    jadenSmith.follow(elonMusk);
    elonMusk.follow(jadenSmith);

    assertEquals(2, elonMusk.getFollowers().size());
    assertTrue(elonMusk.getFollowers().containsAll(List.of(carolDance, jadenSmith)));
    assertTrue(jadenSmith.getFollowers().contains(elonMusk));

    assertTrue(elonMusk.getFeed().contains(CAROL_DANCE + " is now following you!"),
        ELON_MUSK + " wasn't notified of his new follow: " + CAROL_DANCE);
    assertTrue(elonMusk.getFeed().contains(JADEN_SMITH + " is now following you!"),
        ELON_MUSK + " wasn't notified of his new follow: " + CAROL_DANCE);
    assertTrue(jadenSmith.getFeed().contains(ELON_MUSK + " is now following you!"),
        JADEN_SMITH + " wasn't notified of his new follow: " + ELON_MUSK);

    assertFalse(carolDance.getFeed().contains(ELON_MUSK + " is now following you!"),
        CAROL_DANCE + " thinks " + ELON_MUSK + " is following him");
  }

  @Test
  public void testPost() {
    final String
        elonMsg = "Technically, alcohol is a solution",
        jadenMsg = "How can mirrors be real if our eyes aren't real.";
    carolDance.follow(elonMusk);
    jadenSmith.follow(elonMusk);
    elonMusk.follow(jadenSmith);

    jadenSmith.post(jadenMsg);
    elonMusk.post(elonMsg);

    assertTrue(elonMusk.getFeed().contains(jadenMsg),
        ELON_MUSK + " wasn't notified of " + JADEN_SMITH + "'s new post");
    assertTrue(carolDance.getFeed().contains(elonMsg),
        CAROL_DANCE + " wasn't notified of " + ELON_MUSK + "'s new post");
    assertTrue(jadenSmith.getFeed().contains(elonMsg),
        JADEN_SMITH + " wasn't notified of " + ELON_MUSK + "'s new post");

    assertFalse(carolDance.getFeed().contains(jadenMsg),
        CAROL_DANCE + " was notified of " + JADEN_SMITH + "'s new post");
  }
}