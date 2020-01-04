package expression;

import com.google.common.base.Objects;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IntegerRange;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
public class BasicExpressions {
  @Test
  public void literals() {
    Assertions.assertEquals("Hello", "Hello");
    Assertions.assertEquals(42, ((20 + 20) + (1 * 2)));
    BigDecimal _plus = new BigDecimal("0.00").add(BigDecimal.valueOf(42L));
    Assertions.assertEquals(new BigDecimal("42.00"), _plus);
    Assertions.assertEquals(Boolean.valueOf(true), Boolean.valueOf((!false)));
    Assertions.assertEquals(this.getClass(), BasicExpressions.class);
  }
  
  @Test
  public void operators() {
    int i = 5;
    Assertions.assertEquals(6, (i + 1));
    Assertions.assertEquals(4, (i - 1));
    Assertions.assertEquals(1, (i / 5));
    Assertions.assertEquals(15, (i * 3));
    Assertions.assertEquals(0, (i % 5));
    int _i = i;
    int _multiply = i = (_i * 2);
    Assertions.assertEquals(10, _multiply);
    Assertions.assertFalse((i < 0));
    Assertions.assertEquals(5, (i >> 1));
    int j = 4;
    int _minusMinus = j--;
    Assertions.assertEquals(4, _minusMinus);
    Assertions.assertEquals(3, j);
    int _plusPlus = j++;
    Assertions.assertEquals(3, _plusPlus);
    Assertions.assertEquals(4, j);
  }
  
  @Test
  public void collections() {
    final List<String> list = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Hello", "World"));
    final Function1<String, String> _function = (String it) -> {
      return it.toUpperCase();
    };
    Assertions.assertEquals("HELLO", IterableExtensions.<String>head(ListExtensions.<String, String>map(list, _function)));
    final Set<Integer> set = Collections.<Integer>unmodifiableSet(CollectionLiterals.<Integer>newHashSet(Integer.valueOf(1), Integer.valueOf(3), Integer.valueOf(5)));
    final Function1<Integer, Boolean> _function_1 = (Integer it) -> {
      return Boolean.valueOf(((it).intValue() >= 3));
    };
    Assertions.assertEquals(2, IterableExtensions.size(IterableExtensions.<Integer>filter(set, _function_1)));
    Pair<String, Integer> _mappedTo = Pair.<String, Integer>of("one", Integer.valueOf(1));
    Pair<String, Integer> _mappedTo_1 = Pair.<String, Integer>of("two", Integer.valueOf(2));
    Pair<String, Integer> _mappedTo_2 = Pair.<String, Integer>of("three", Integer.valueOf(3));
    final Map<String, Integer> map = Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(_mappedTo, _mappedTo_1, _mappedTo_2));
    Assertions.assertEquals(2, map.get("two"));
    final ArrayList<String> mutableList = CollectionLiterals.<String>newArrayList();
    mutableList.add("Foo");
    final HashSet<String> mutableSet = CollectionLiterals.<String>newHashSet();
    mutableSet.add("Bar");
    final HashMap<String, String> mutableMap = CollectionLiterals.<String, String>newHashMap();
    mutableMap.put("Fizz", "Buzz");
  }
  
  @Test
  public void controlStructures() {
    int _length = "text".length();
    boolean _equals = (_length == 4);
    if (_equals) {
      int _xifexpression = (int) 0;
      String _lowerCase = "BAR".toLowerCase();
      boolean _notEquals = (!Objects.equal("foo", _lowerCase));
      if (_notEquals) {
        _xifexpression = 42;
      } else {
        _xifexpression = (-24);
      }
      Assertions.assertEquals(42, _xifexpression);
    } else {
      Assertions.<Object>fail("Never happens!");
    }
    final String t = "text";
    boolean _matched = false;
    int _length_1 = t.length();
    boolean _greaterThan = (_length_1 > 8);
    if (_greaterThan) {
      _matched=true;
      Assertions.<Object>fail("Never happens!");
    }
    if (!_matched) {
      if (Objects.equal(t, "text")) {
        _matched=true;
        Assertions.assertTrue(true);
      }
    }
    if (!_matched) {
      Assertions.<Object>fail("never happens!");
    }
    final Object someValue = "a string typed to Object";
    String _switchResult_1 = null;
    boolean _matched_1 = false;
    if (someValue instanceof Number) {
      _matched_1=true;
      _switchResult_1 = "number";
    }
    if (!_matched_1) {
      if (someValue instanceof String) {
        _matched_1=true;
        _switchResult_1 = "string";
      }
    }
    Assertions.assertEquals("string", _switchResult_1);
    final int num = 3;
    String _switchResult_2 = null;
    switch (num) {
      case 1:
      case 2:
      case 4:
        _switchResult_2 = "divisor of 4";
        break;
      default:
        _switchResult_2 = "not a divisor of 4";
        break;
    }
    Assertions.assertEquals("not a divisor of 4", _switchResult_2);
  }
  
  @Test
  public void loops() {
    int counter = 1;
    IntegerRange _upTo = new IntegerRange(1, 10);
    for (final Integer i : _upTo) {
      {
        Assertions.assertEquals(counter, i);
        counter = (counter + 1);
      }
    }
    for (int i_1 = 11; (i_1 > 0); i_1--) {
      {
        Assertions.assertEquals(counter, i_1);
        int _counter = counter;
        counter = (_counter - 1);
      }
    }
    final Iterator<Integer> iterator = Collections.<Integer>unmodifiableList(CollectionLiterals.<Integer>newArrayList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5))).iterator();
    counter = 1;
    while (iterator.hasNext()) {
      {
        final Integer i_1 = iterator.next();
        Assertions.assertEquals(counter, i_1);
        counter = (counter + 1);
      }
    }
  }
}
