package sat4j;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;

@SuppressWarnings("all")
public class Classification {
  private final Map<String, Integer> categories = Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(Pair.<String, Integer>of("A", Integer.valueOf(1)), Pair.<String, Integer>of("B", Integer.valueOf(2))));
  
  private final Map<String, Integer> domains = Collections.<String, Integer>unmodifiableMap(CollectionLiterals.<String, Integer>newHashMap(Pair.<String, Integer>of("A", Integer.valueOf(3)), Pair.<String, Integer>of("B", Integer.valueOf(4))));
  
  /**
   * Giving the email filtering criteria:
   * 
   * Email is from domain A, it should be classified as category A
   * Email is from domain B, it should be classified as category B
   * 
   * Literals:
   *   1: email is category A
   *   2: email is category B
   *   3: email is from domain A
   *   4: email is from domain B
   * 
   * Clauses:
   *   1 -> 3 = !1 | 3 = [-1, 3]
   *   2 -> 4 = !2 | 4 = [-2, 4]
   */
  @Test
  public void example() {
    try {
      final ISolver solver = SolverFactory.newDefault();
      VecInt _vecInt = new VecInt(new int[] { (-1), 3 });
      solver.addClause(_vecInt);
      VecInt _vecInt_1 = new VecInt(new int[] { (-2), 4 });
      solver.addClause(_vecInt_1);
      VecInt _vecInt_2 = new VecInt(new int[] { 1, 3 });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_2));
      Assertions.assertArrayEquals(new int[] { 1, (-2), 3, (-4) }, solver.model());
      VecInt _vecInt_3 = new VecInt(new int[] { 1, (-3) });
      Assertions.assertFalse(solver.isSatisfiable(_vecInt_3));
      VecInt _vecInt_4 = new VecInt(new int[] { 2, 3 });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_4));
      Assertions.assertArrayEquals(new int[] { (-1), 2, 3, 4 }, solver.model());
      VecInt _vecInt_5 = new VecInt(new int[] { 2, (-3) });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_5));
      Assertions.assertArrayEquals(new int[] { (-1), 2, (-3), 4 }, solver.model());
      Assertions.assertEquals("A", this.eval(solver, "A"));
      Assertions.assertEquals("B", this.eval(solver, "B"));
      Assertions.assertNull(this.eval(solver, "C"));
      VecInt _vecInt_6 = new VecInt(new int[] { 1 });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_6));
      Assertions.assertArrayEquals(new int[] { 1, (-2), 3, (-4) }, solver.model());
      VecInt _vecInt_7 = new VecInt(new int[] { 2 });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_7));
      Assertions.assertArrayEquals(new int[] { (-1), 2, (-3), 4 }, solver.model());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String eval(final ISolver solver, final String domain) {
    try {
      Object _xifexpression = null;
      boolean _containsKey = this.domains.containsKey(domain);
      if (_containsKey) {
        final Integer d = this.domains.get(domain);
        Set<Map.Entry<String, Integer>> _entrySet = this.categories.entrySet();
        for (final Map.Entry<String, Integer> category : _entrySet) {
          if ((solver.isSatisfiable(new VecInt(new int[] { (category.getValue()).intValue(), (d).intValue() })) && 
            (!solver.isSatisfiable(new VecInt(new int[] { (category.getValue()).intValue(), (-(d).intValue()) }))))) {
            return category.getKey();
          }
        }
      }
      return ((String)_xifexpression);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
