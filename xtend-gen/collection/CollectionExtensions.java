package collection;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
public class CollectionExtensions {
  @Test
  public void map() {
    final List<String> list = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Hello", "World"));
    final Function1<String, String> _function = (String it) -> {
      return it.toUpperCase();
    };
    Assertions.assertEquals(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("HELLO", "WORLD")), ListExtensions.<String, String>map(list, _function));
    final Set<String> set = Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("Hello", "World"));
    final Function1<String, String> _function_1 = (String it) -> {
      return it.toUpperCase();
    };
    Assertions.assertEquals(Collections.<String>unmodifiableSet(CollectionLiterals.<String>newHashSet("HELLO", "WORLD")), IterableExtensions.<String>toSet(IterableExtensions.<String, String>map(set, _function_1)));
    final Iterator<String> iter = list.iterator();
    final Function1<String, String> _function_2 = (String it) -> {
      return it.toUpperCase();
    };
    Assertions.assertEquals(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("HELLO", "WORLD")), IteratorExtensions.<String>toList(IteratorExtensions.<String, String>map(iter, _function_2)));
  }
  
  @Test
  public void filter() {
    final IntegerRange list = new IntegerRange(1, 10);
    final Function1<Integer, Boolean> _function = (Integer n) -> {
      return Boolean.valueOf((((n).intValue() % 2) != 0));
    };
    Assertions.assertEquals(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(9))), IterableExtensions.<Integer>toList(IterableExtensions.<Integer>filter(list, _function)));
    final ListIterator<Integer> iter = list.iterator();
    final Function1<Integer, Boolean> _function_1 = (Integer n) -> {
      return Boolean.valueOf((((n).intValue() % 2) != 0));
    };
    Assertions.assertEquals(Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(9))), IteratorExtensions.<Integer>toList(IteratorExtensions.<Integer>filter(iter, _function_1)));
  }
  
  @Test
  public void reduce() {
    final IntegerRange list = new IntegerRange(1, 10);
    final Function2<Integer, Integer, Integer> _function = (Integer p1, Integer p2) -> {
      return Integer.valueOf(((p1).intValue() + (p2).intValue()));
    };
    Assertions.assertEquals(55, IterableExtensions.<Integer>reduce(list, _function));
    final ListIterator<Integer> iter = list.iterator();
    final Function2<Integer, Integer, Integer> _function_1 = (Integer p1, Integer p2) -> {
      return Integer.valueOf(((p1).intValue() + (p2).intValue()));
    };
    Assertions.assertEquals(55, IteratorExtensions.<Integer>reduce(iter, _function_1));
  }
}
