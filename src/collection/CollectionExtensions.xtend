package collection

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.*

class CollectionExtensions {

	@Test
	def void map() {
		// ListExtensions
		val list = #['Hello', 'World']
		assertEquals(#['HELLO', 'WORLD'], list.map[toUpperCase])
		// IterableExtensions
		val set = #{'Hello', 'World'}
		assertEquals(#{'HELLO', 'WORLD'}, set.map[toUpperCase].toSet)
		// IteratorExtensions
		val iter = list.iterator
		assertEquals(#['HELLO', 'WORLD'], iter.map[toUpperCase].toList)
	}

	@Test
	def void filter() {
		// IterableExtensions
		val list = 1 .. 10
		assertEquals(#[1, 3, 5, 7, 9], list.filter[n|n % 2 != 0].toList)
		// IteratorExtensions
		val iter = list.iterator
		assertEquals(#[1, 3, 5, 7, 9], iter.filter[n|n % 2 != 0].toList)
	}

	@Test
	def void reduce() {
		// IterableExtensions
		val list = 1 .. 10
		assertEquals(55, list.reduce[p1, p2|p1 + p2])
		// IteratorExtensions
		val iter = list.iterator
		assertEquals(55, iter.reduce[p1, p2|p1 + p2])
	}

}
