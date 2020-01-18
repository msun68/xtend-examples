package sorting;

import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

@SuppressWarnings("all")
public class TopologicalSort {
  @Data
  public static class Task {
    private final String name;
    
    public Task(final String name) {
      super();
      this.name = name;
    }
    
    @Override
    @Pure
    public int hashCode() {
      return 31 * 1 + ((this.name== null) ? 0 : this.name.hashCode());
    }
    
    @Override
    @Pure
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TopologicalSort.Task other = (TopologicalSort.Task) obj;
      if (this.name == null) {
        if (other.name != null)
          return false;
      } else if (!this.name.equals(other.name))
        return false;
      return true;
    }
    
    @Override
    @Pure
    public String toString() {
      ToStringBuilder b = new ToStringBuilder(this);
      b.add("name", this.name);
      return b.toString();
    }
    
    @Pure
    public String getName() {
      return this.name;
    }
  }
  
  @Data
  public static class Dependency {
    private final String task;
    
    private final String dependency;
    
    public Dependency(final String task, final String dependency) {
      super();
      this.task = task;
      this.dependency = dependency;
    }
    
    @Override
    @Pure
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.task== null) ? 0 : this.task.hashCode());
      return prime * result + ((this.dependency== null) ? 0 : this.dependency.hashCode());
    }
    
    @Override
    @Pure
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TopologicalSort.Dependency other = (TopologicalSort.Dependency) obj;
      if (this.task == null) {
        if (other.task != null)
          return false;
      } else if (!this.task.equals(other.task))
        return false;
      if (this.dependency == null) {
        if (other.dependency != null)
          return false;
      } else if (!this.dependency.equals(other.dependency))
        return false;
      return true;
    }
    
    @Override
    @Pure
    public String toString() {
      ToStringBuilder b = new ToStringBuilder(this);
      b.add("task", this.task);
      b.add("dependency", this.dependency);
      return b.toString();
    }
    
    @Pure
    public String getTask() {
      return this.task;
    }
    
    @Pure
    public String getDependency() {
      return this.dependency;
    }
  }
  
  @Test
  public void example1() {
    Pair<String, List<String>> _mappedTo = Pair.<String, List<String>>of("A", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("B")));
    Pair<String, List<String>> _mappedTo_1 = Pair.<String, List<String>>of("B", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C")));
    Pair<String, List<String>> _mappedTo_2 = Pair.<String, List<String>>of("C", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList()));
    final List<Pair<String, List<String>>> tasks = Collections.<Pair<String, List<String>>>unmodifiableList(CollectionLiterals.<Pair<String, List<String>>>newArrayList(_mappedTo, _mappedTo_1, _mappedTo_2));
    Assertions.assertEquals(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C", "B", "A")), this.sort(tasks));
  }
  
  @Test
  public void example2() {
    TopologicalSort.Task _task = new TopologicalSort.Task("A");
    TopologicalSort.Task _task_1 = new TopologicalSort.Task("B");
    TopologicalSort.Task _task_2 = new TopologicalSort.Task("C");
    final List<TopologicalSort.Task> tasks = Collections.<TopologicalSort.Task>unmodifiableList(CollectionLiterals.<TopologicalSort.Task>newArrayList(_task, _task_1, _task_2));
    TopologicalSort.Dependency _dependency = new TopologicalSort.Dependency("A", "B");
    TopologicalSort.Dependency _dependency_1 = new TopologicalSort.Dependency("B", "C");
    final List<TopologicalSort.Dependency> dependencies = Collections.<TopologicalSort.Dependency>unmodifiableList(CollectionLiterals.<TopologicalSort.Dependency>newArrayList(_dependency, _dependency_1));
    Assertions.assertEquals(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C", "B", "A")), this.sort(this.join(tasks, dependencies)));
  }
  
  @Test
  public void testCircularDependency() {
    Pair<String, List<String>> _mappedTo = Pair.<String, List<String>>of("A", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("B")));
    Pair<String, List<String>> _mappedTo_1 = Pair.<String, List<String>>of("B", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("C")));
    Pair<String, List<String>> _mappedTo_2 = Pair.<String, List<String>>of("C", Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("A")));
    final List<Pair<String, List<String>>> tasks = Collections.<Pair<String, List<String>>>unmodifiableList(CollectionLiterals.<Pair<String, List<String>>>newArrayList(_mappedTo, _mappedTo_1, _mappedTo_2));
    final Executable _function = () -> {
      this.sort(tasks);
    };
    Assertions.<IllegalArgumentException>assertThrows(IllegalArgumentException.class, _function);
  }
  
  public List<Pair<String, List<String>>> join(final List<TopologicalSort.Task> tasks, final List<TopologicalSort.Dependency> dependencies) {
    final Function1<TopologicalSort.Task, Pair<String, List<String>>> _function = (TopologicalSort.Task t) -> {
      final Function1<TopologicalSort.Dependency, Boolean> _function_1 = (TopologicalSort.Dependency d) -> {
        return Boolean.valueOf(Objects.equal(d.task, t.name));
      };
      final Function1<TopologicalSort.Dependency, String> _function_2 = (TopologicalSort.Dependency it) -> {
        return it.dependency;
      };
      List<String> _list = IterableExtensions.<String>toList(IterableExtensions.<TopologicalSort.Dependency, String>map(IterableExtensions.<TopologicalSort.Dependency>filter(dependencies, _function_1), _function_2));
      return Pair.<String, List<String>>of(t.name, _list);
    };
    return ListExtensions.<TopologicalSort.Task, Pair<String, List<String>>>map(tasks, _function);
  }
  
  public List<String> sort(final List<Pair<String, List<String>>> tasks) {
    return this.sort(tasks, Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList()));
  }
  
  public List<String> sort(final List<Pair<String, List<String>>> list, final List<String> sorted) {
    List<String> _xblockexpression = null;
    {
      boolean _isEmpty = list.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final Function1<Pair<String, List<String>>, String> _function = (Pair<String, List<String>> it) -> {
          return it.getKey();
        };
        final List<String> keys = ListExtensions.<Pair<String, List<String>>, String>map(list, _function);
        final Function1<Pair<String, List<String>>, Boolean> _function_1 = (Pair<String, List<String>> it) -> {
          return Boolean.valueOf(Collections.disjoint(it.getValue(), keys));
        };
        final List<Pair<String, List<String>>> nodeps = IterableExtensions.<Pair<String, List<String>>>toList(IterableExtensions.<Pair<String, List<String>>>filter(list, _function_1));
        boolean _isEmpty_1 = nodeps.isEmpty();
        if (_isEmpty_1) {
          throw new IllegalArgumentException();
        }
        List<Pair<String, List<String>>> _minus = this.<Pair<String, List<String>>>operator_minus(list, nodeps);
        final Function1<Pair<String, List<String>>, String> _function_2 = (Pair<String, List<String>> it) -> {
          return it.getKey();
        };
        List<String> _map = ListExtensions.<Pair<String, List<String>>, String>map(nodeps, _function_2);
        List<String> _plus = this.<String>operator_plus(sorted, _map);
        return this.sort(_minus, _plus);
      }
      _xblockexpression = sorted;
    }
    return _xblockexpression;
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
