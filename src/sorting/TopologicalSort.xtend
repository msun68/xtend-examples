package sorting

import java.util.Collections
import java.util.List
import org.eclipse.xtend.lib.annotations.Data
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class TopologicalSort {

	@Test
	def void example1() {
		val tasks = #[
			"A" -> #["B"],
			"B" -> #["C"],
			"C" -> #[]
		]

		assertEquals(#["C", "B", "A"], tasks.sort)
	}

	@Test
	def void example2() {
		val tasks = #[new Task("A"), new Task("B"), new Task("C")]
		val dependencies = #[new Dependency("A", "B"), new Dependency("B", "C")]

		assertEquals(#["C", "B", "A"], tasks.join(dependencies).sort)
	}

	@Test
	def void testCircularDependency() {
		val tasks = #[
			"A" -> #["B"],
			"B" -> #["C"],
			"C" -> #["A"]
		]

		assertThrows(IllegalArgumentException, [tasks.sort])
	}

	def List<Pair<String, List<String>>> join(List<Task> tasks, List<Dependency> dependencies) {
		tasks.map[t|t.name -> dependencies.filter[d|d.task == t.name].map[dependency].toList]
	}

	def List<String> sort(List<Pair<String, List<String>>> tasks) {
		sort(tasks, #[])
	}

	def List<String> sort(List<Pair<String, List<String>>> list, List<String> sorted) {
		if (!list.empty) {
			val keys = list.map[key]
			val nodeps = list.filter[Collections.disjoint(value, keys)].toList
			if (nodeps.empty) {
				throw new IllegalArgumentException
			}
			return sort(list - nodeps, sorted + nodeps.map[key])
		}
		sorted
	}

	def <T> List<T> operator_plus(List<T> a, List<T> b) {
		(a.iterator + b.iterator).toList
	}

	def <T> List<T> operator_minus(List<T> a, List<T> b) {
		a.filter[!b.contains(it)].toList
	}

	@Data
	static class Task {
		String name
	}

	@Data
	static class Dependency {
		String task
		String dependency
	}

}
