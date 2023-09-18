import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {

    // @Test
    // @DisplayName("ArrayDeque has no fields besides backing array and primitives")
    // void noNonTrivialFields() {
    //     List<Field> badFields = Reflection.getFields(ArrayDeque.class)
    //             .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
    //             .toList();

    //     assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    // }

    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(10);
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(20);
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(20);
    }

    @Test
    public void testAddFirstAndAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        deque.addLast(20);
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.get(0)).isEqualTo(10);
        assertThat(deque.get(1)).isEqualTo(20);
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        int removedItem = deque.removeFirst();
        assertThat(removedItem).isEqualTo(10);
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveFirstEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Integer removedItem = deque.removeFirst();
        assertThat(removedItem).isNull();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        int removedItem = deque.removeLast();
        assertThat(removedItem).isEqualTo(10);
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveLastEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Integer removedItem = deque.removeLast();
        assertThat(removedItem).isNull();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testRemoveLastLeavingEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        int removedItem = deque.removeLast();
        assertThat(removedItem).isEqualTo(10);
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void testToList() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        deque.addLast(20);
        List<Integer> list = deque.toList();
        assertThat(list).containsExactly(10, 20).inOrder();
    }

    @Test
    public void testGet() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);

        assertThat(deque.get(0)).isEqualTo(10);
        assertThat(deque.get(1)).isEqualTo(20);
        assertThat(deque.get(2)).isEqualTo(30);
    }

    @Test
    public void testGetEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertThat(deque.get(0)).isNull();
    }

    @Test
    public void testGetOutOfBounds() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(10);
        deque.addLast(20);

        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(2)).isNull();
    }

}
