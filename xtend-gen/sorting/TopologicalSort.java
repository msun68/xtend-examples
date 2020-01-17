package sorting;

import com.google.common.collect.Iterators;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
public class TopologicalSort {
  @Test
  public void test() {
    Pair<String, List<String>> _mappedTo = Pair.<String, List<String>>of("A", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("B")));
    Pair<String, List<String>> _mappedTo_1 = Pair.<String, List<String>>of("B", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C")));
    Pair<String, List<String>> _mappedTo_2 = Pair.<String, List<String>>of("C", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList()));
    final List<Pair<String, List<String>>> tasks = Collections.<Pair<String, List<String>>>unmodifiableList(CollectionLiterals.<Pair<String, List<String>>>newArrayList(_mappedTo, _mappedTo_1, _mappedTo_2));
    final Function1<Pair<String, List<String>>, String> _function = (Pair<String, List<String>> it) -> {
      return it.getKey();
    };
    Assertions.assertEquals(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C", "B", "A")), ListExtensions.<Pair<String, List<String>>, String>map(this.sort(tasks, Collections.<Pair<String, List<String>>>unmodifiableList(CollectionLiterals.<Pair<String, List<String>>>newArrayList())), _function));
  }
  
  public List<Pair<String, List<String>>> sort(final List<Pair<String, List<String>>> tasks, final List<Pair<String, List<String>>> sorted) {
    try {
      List<Pair<String, List<String>>> _xblockexpression = null;
      {
        boolean _isEmpty = tasks.isEmpty();
        boolean _not = (!_isEmpty);
        if (_not) {
          final Function1<Pair<String, List<String>>, String> _function = (Pair<String, List<String>> it) -> {
            return it.getKey();
          };
          final List<String> names = ListExtensions.<Pair<String, List<String>>, String>map(tasks, _function);
          final Function1<Pair<String, List<String>>, Boolean> _function_1 = (Pair<String, List<String>> task) -> {
            return Boolean.valueOf(Collections.disjoint(task.getValue(), names));
          };
          final List<Pair<String, List<String>>> canRun = IterableExtensions.<Pair<String, List<String>>>toList(IterableExtensions.<Pair<String, List<String>>>filter(tasks, _function_1));
          boolean _isEmpty_1 = canRun.isEmpty();
          if (_isEmpty_1) {
            throw new Exception();
          }
          List<Pair<String, List<String>>> _minus = this.<Pair<String, List<String>>>operator_minus(tasks, canRun);
          List<Pair<String, List<String>>> _plus = this.<Pair<String, List<String>>>operator_plus(sorted, canRun);
          return this.sort(_minus, _plus);
        }
        _xblockexpression = sorted;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public <T extends Object> List<T> operator_plus(final List<T> a, final List<T> b) {
    Iterator<T> _iterator = a.iterator();
    Iterator<T> _iterator_1 = b.iterator();
    return IteratorExtensions.<T>toList(Iterators.<T>concat(_iterator, _iterator_1));
  }
  
  public <T extends Object> List<T> operator_minus(final List<T> a, final List<T> b) {
    final Function1<T, Boolean> _function = (T it) -> {
      boolean _contains = b.contains(it);
      return Boolean.valueOf((!_contains));
    };
    return IterableExtensions.<T>toList(IterableExtensions.<T>filter(a, _function));
  }
}
