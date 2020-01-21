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

		// it is satisfiable when "email is category A" and "email from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[1, 3])))
		assertArrayEquals(#[1, -2, 3, -4], solver.model)

		// it is not satisfiable when "email is category A" and "email is not from domain A"
		assertFalse(solver.isSatisfiable(new VecInt(#[1, -3])))

		// it is satisfiable when "email is category B" and "email from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[2, 3])))
		assertArrayEquals(#[-1, 2, 3, 4], solver.model)

		// it is satisfiable when "email is category B" and "email is not from domain A"
		assertTrue(solver.isSatisfiable(new VecInt(#[2, -3])))
		assertArrayEquals(#[-1, 2, -3, 4], solver.model)

		assertEquals("A", solver.eval("A"))
		assertEquals("B", solver.eval("B"))
		assertNull(solver.eval("C"))
		
		assertTrue(solver.isSatisfiable(new VecInt(#[1])))
		assertArrayEquals(#[1, -2, 3, -4], solver.model)

		assertTrue(solver.isSatisfiable(new VecInt(#[2])))
		assertArrayEquals(#[-1, 2, -3, 4], solver.model)
	}

	def String eval(ISolver solver, String domain) {
		for (category : categories.entrySet) {
			if (domains.containsKey(domain)) {
				val lit = domains.get(domain)
				if (solver.isSatisfiable(new VecInt(#[category.value, lit])) &&
					!solver.isSatisfiable(new VecInt(#[category.value, -lit]))) {
					return category.key
				}
			}
		}
	}

}
