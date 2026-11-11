import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }
    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> L = new ArrayDeque61B<>();

        L.addFirst("back"); // after this call we expect: ["back"]
        assertThat(L.toList()).containsExactly("back").inOrder();

        L.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(L.toList()).containsExactly("middle", "back").inOrder();

        L.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(L.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> L = new ArrayDeque61B<>();

        L.addLast("front"); // after this call we expect: ["front"]
        L.addLast("middle"); // after this call we expect: ["front", "middle"]
        L.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(L.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        L.addLast(0);   // [0]
        L.addLast(1);   // [0, 1]
        L.addFirst(-1); // [-1, 0, 1]
        L.addLast(2);   // [-1, 0, 1, 2]
        L.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(L.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void toListTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addLast(5);
        assertThat((L.toList()).isEmpty()).isEqualTo(false);
        L.removeLast();
        assertThat(L.toList().isEmpty()).isEqualTo(true);
    }

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        assertThat(L.isEmpty()).isEqualTo(true);
        L.addFirst(3);
        assertThat(L.isEmpty()).isEqualTo(false);
        L.removeFirst();
        assertThat(L.isEmpty()).isEqualTo(true);
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.addLast(2);
        assertThat(L.size()).isEqualTo(2);
        L.removeLast();
        assertThat(L.size()).isEqualTo(1);
        L.removeFirst();
        assertThat(L.size()).isEqualTo(0);
    }

    @Test
    public void getTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.addLast(2);
        L.addLast(0);
        assertThat(L.get(0)).isEqualTo(3);
        assertThat(L.get(1)).isEqualTo(2);
        assertThat(L.get(-1)).isEqualTo(null);
        assertThat(L.get(3)).isEqualTo(null);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.removeFirst();
        assertThat(L.isEmpty()).isEqualTo(true);
        L.addFirst(2);
        L.addFirst(3);
        L.addLast(0);
        L.removeFirst();
        assertThat(L.toList()).containsExactly(2, 0).inOrder();
        assertThat(L.isEmpty()).isEqualTo(false);
        assertThat(L.removeFirst()).isEqualTo(2);
        L.removeFirst();
        assertThat(L.isEmpty()).isEqualTo(true);
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.removeLast();
        assertThat(L.isEmpty()).isEqualTo(true);
        L.addLast(2);
        L.addFirst(3);
        L.addLast(0);
        L.removeLast();
        assertThat(L.toList()).containsExactly(3, 2).inOrder();
        assertThat(L.isEmpty()).isEqualTo(false);
        assertThat(L.removeLast()).isEqualTo(2);
        L.removeLast();
        assertThat(L.isEmpty()).isEqualTo(true);
    }

    @Test
    public void resizeTest() {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(3);
        L.addLast(4);
        L.addLast(2);
        L.addFirst(5);
        L.addFirst(9);
        L.addLast(1);
        L.addLast(7);
        L.addFirst(6);
        L.addFirst(8);
        assertThat(L.size()).isEqualTo(9);
        L.removeFirst();
        L.removeLast();
        L.removeFirst();
        L.removeFirst();
        L.removeLast();
        L.removeLast();
        L.removeFirst();
        assertThat(L.size()).isEqualTo(2);
        L.removeLast();
        assertThat(L.size()).isEqualTo(1);
    }

    @Test
    public void testToString() {
        ArrayDeque61B<Integer> L = new ArrayDeque61B<>();
        L.addLast(4);
        L.addLast(6);
        L.addLast(3);
        assertThat(L.toString()).isEqualTo("[4, 6, 3]");
        L.addFirst(0);
        assertThat(L.toString()).isEqualTo("[0, 4, 6, 3]");
        L.removeLast();
        assertThat(L.toString()).isEqualTo("[0, 4, 6]");
        L.removeFirst();
        assertThat(L.toString()).isEqualTo("[4, 6]");
    }

    @Test
    public void testEquals() {
        ArrayDeque61B<Integer> l1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> l2 = new ArrayDeque61B<>();
        l1.addFirst(5);
        l1.addLast(9);
        l2.addFirst(5);
        l2.addLast(9);
        assertThat(l1.equals(l2)).isEqualTo(true);
        l2.removeFirst();
        assertThat(l1.equals(l2)).isEqualTo(false);
    }

    @Test
    public void iteratorTest() {
        ArrayDeque61B<Integer> L = new ArrayDeque61B<>();
    }
}
