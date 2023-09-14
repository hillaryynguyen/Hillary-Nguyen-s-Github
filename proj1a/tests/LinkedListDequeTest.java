
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.

    //tests for isEmpty and size
    @Test
    public void testISEmptyOnEmptyDeque() {
         LinkedListDeque<String> deque = new LinkedListDeque<>();
         assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void testIsEmptyOnNonEmptyDeque() {
         LinkedListDeque<Integer> deque = new LinkedListDeque<>();
         deque.addFirst(42);
         assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void testSizeZero() {
         LinkedListDeque<Character> deque = new LinkedListDeque<>();
         assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testSizeOne() {
         LinkedListDeque<Double> deque = new LinkedListDeque<>();
         deque.addLast(3.14);
         assertThat(deque.size()).isEqualTo(1);
    }

    @Test
    public void testSizeMultipleElements() {
         LinkedListDeque<Integer> deque = new LinkedListDeque<>();
         deque.addLast(10);
         deque.addLast(20);
         assertThat(deque.size()).isEqualTo(2);
         deque.removeFirst();
         assertThat(deque.size()).isEqualTo(1);
    }

    //tests for get
    @Test
    public void testGetValidIndex() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.get(0)).isEqualTo("A");
        assertThat(deque.get(1)).isEqualTo("B");
        assertThat(deque.get(2)).isEqualTo("C");
    }

    @Test
    public void testGetNegativeIndex() {
         LinkedListDeque<Integer> deque = new LinkedListDeque<>();
         deque.addLast(42);

         assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void testGetOutOfRangeIndex() {
         LinkedListDeque<Character> deque = new LinkedListDeque<>();
         deque.addLast('X');

         assertThat(deque.get(1)).isNull();
         assertThat(deque.get(28723)).isNull();
    }

    @Test
    public void testGetEmptyDeque() {
         LinkedListDeque<Double> deque = new LinkedListDeque<>();

         assertThat(deque.get(0)).isNull();
         assertThat(deque.get(1)).isNull();
    }

    //tests for getRecursive
    @Test
    public void testGetRecursiveValidIndex() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.get(0)).isEqualTo("A");
        assertThat(deque.get(1)).isEqualTo("B");
        assertThat(deque.get(2)).isEqualTo("C");
    }

    @Test
    public void testGetRecursiveNegativeIndex() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addLast(42);

        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void testGetRecursiveOutOfRangeIndex() {
        LinkedListDeque<Character> deque = new LinkedListDeque<>();
        deque.addLast('X');

        assertThat(deque.get(1)).isNull();
        assertThat(deque.get(28723)).isNull();
    }

    @Test
    public void testGetRecursiveEmptyDeque() {
        LinkedListDeque<Double> deque = new LinkedListDeque<>();

        assertThat(deque.get(0)).isNull();
        assertThat(deque.get(1)).isNull();
    }

    //tests for removeFirst and removeLast
    @Test
    public void testRemoveFirst() {
         LinkedListDeque<String> deque = new LinkedListDeque<>();
         deque.addLast("A");
         deque.addLast("B");
         deque.addLast("C");

         assertThat(deque.removeFirst()).isEqualTo("A");
         assertThat(deque.toList()).containsExactly("B", "C");
         assertThat(deque.size()).isEqualTo(2);

         assertThat(deque.removeFirst()).isEqualTo("B");
         assertThat(deque.toList()).containsExactly("C");
         assertThat(deque.size()).isEqualTo(1);

        assertThat(deque.removeFirst()).isEqualTo("C");
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);

        assertThat(deque.removeFirst()).isNull();
    }

    @Test
    public void testRemoveLast() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.removeFirst()).isEqualTo("C");
        assertThat(deque.toList()).containsExactly("A", "B");
        assertThat(deque.size()).isEqualTo(2);

        assertThat(deque.removeFirst()).isEqualTo("B");
        assertThat(deque.toList()).containsExactly("A");
        assertThat(deque.size()).isEqualTo(1);

        assertThat(deque.removeFirst()).isEqualTo("A");
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);

        assertThat(deque.removeFirst()).isNull();
    }

}
