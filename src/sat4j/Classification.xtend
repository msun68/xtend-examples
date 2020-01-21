package sat4j

import org.junit.jupiter.api.Test
import org.sat4j.core.VecInt
import org.sat4j.minisat.SolverFactory
import org.sat4j.specs.ISolver

import static org.junit.jupiter.api.Assertions.*

class Classification {

	val categories = #{"A" -> 1, "B" -> 2}
	val domains = #{"A" -> 3, "B" -> 4}

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
	def void example() {
		val solver = SolverFactory.newDefault

		solver.addClause(new VecInt(#[-1, 3]))
		solver.addClause(new VecInt(#[-2, 4]))

		// it should be satisfiable when "email is category A" and "email from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[1, 3])))
		assertArrayEquals(#[1, -2, 3, -4], solver.model)

		// it should not be satisfiable when "email is category A" and "email is not from domain A"
		assertFalse(solver.isSatisfiable(new VecInt(#[1, -3])))

		// it should be satisfiable when "email is category B" and "email from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[2, 3])))
		assertArrayEquals(#[-1, 2, 3, 4], solver.model)

		// it should be satisfiable when "email is category B" and "email is not from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[2, -3])))
		assertArrayEquals(#[-1, 2, -3, 4], solver.model)

		// when email is from domain A, it should be classified as category A
		assertEquals("A", solver.eval("A"))

		// When email is from domain B, it should be classified as category B
		assertEquals("B", solver.eval("B"))

		// When email is from domain C (undefined), it should return null (can not be classified)
		assertNull(solver.eval("C"))

		// it should return a solution for "email is category A"
		assertTrue(solver.isSatisfiable(new VecInt(#[1])))
		assertArrayEquals(#[1, -2, 3, -4], solver.model)

		// it should return a solution for "email is category B"
		assertTrue(solver.isSatisfiable(new VecInt(#[2])))
		assertArrayEquals(#[-1, 2, -3, 4], solver.model)
	}

	def String eval(ISolver solver, String domain) {
		if (domains.containsKey(domain)) {
			val d = domains.get(domain)
			for (category : categories.entrySet) {
				if (solver.isSatisfiable(new VecInt(#[category.value, d])) &&
					!solver.isSatisfiable(new VecInt(#[category.value, -d]))) {
					return category.key
				}
			}
		}
	}

}
