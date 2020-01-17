package sorting

import java.util.Collections
import java.util.List
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class TopologicalSort {

	@Test def void test() {
		val tasks = #[
			"A" -> #["B"],
			"B" -> #["C"],
			"C" -> #[]
		]
		assertEquals(#["C", "B", "A"], sort(tasks, #[]).map[key])
	}

	def List<Pair<String, List<String>>> sort(List<Pair<String, List<String>>> tasks,
		List<Pair<String, List<String>>> sorted) {
		if (!tasks.empty) {
			val names = tasks.map[key]
			val canRun = tasks.filter [ task |
				Collections.disjoint(task.value, names)
			].toList
			if (canRun.empty) {
				throw new Exception
			}
			return sort(tasks - canRun, sorted + canRun)
		}
		sorted
	}

	def <T> List<T> operator_plus(List<T> a, List<T> b) {
		(a.iterator + b.iterator).toList
	}

	def <T> List<T> operator_minus(List<T> a, List<T> b) {
		a.filter[!b.contains(it)].toList
	}

}
