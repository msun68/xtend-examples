package sat4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
  private final List<Pair<Integer, String>> categories = Collections.<Pair<Integer, String>>unmodifiableList(CollectionLiterals.<Pair<Integer, String>>newArrayList(Pair.<Integer, String>of(Integer.valueOf(1), "A"), Pair.<Integer, String>of(Integer.valueOf(2), "B")));
  
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
   *   1 -> 3 = !1 | 3
   *   2 -> 4 = !2 | 4
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
      VecInt _vecInt_3 = new VecInt(new int[] { 1, (-3) });
      Assertions.assertFalse(solver.isSatisfiable(_vecInt_3));
      VecInt _vecInt_4 = new VecInt(new int[] { 2, 3 });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_4));
      VecInt _vecInt_5 = new VecInt(new int[] { 2, (-3) });
      Assertions.assertTrue(solver.isSatisfiable(_vecInt_5));
      Assertions.assertEquals("A", this.eval(solver, "A"));
      Assertions.assertEquals("B", this.eval(solver, "B"));
      Assertions.assertNull(this.eval(solver, "C"));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public String eval(final ISolver solver, final String emailFromDomain) {
    try {
      for (final Pair<Integer, String> category : this.categories) {
        {
          final Integer from = this.domains.get(emailFromDomain);
          if ((((from != null) && solver.isSatisfiable(new VecInt(new int[] { (category.getKey()).intValue(), (from).intValue() }))) && 
            (!solver.isSatisfiable(new VecInt(new int[] { (category.getKey()).intValue(), (-(from).intValue()) }))))) {
            return category.getValue();
          }
        }
      }
      return null;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
