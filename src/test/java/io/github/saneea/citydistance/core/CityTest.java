package io.github.saneea.citydistance.core;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class CityTest {

	private CityFactory f = new CityFactory();

	@Test
	public void noConnection() {
		City a = f.getCity("A");
		City b = f.getCity("B");

		assertPaths("[]", a, b);
		assertPaths("[]", b, a);
	}

	@Test
	public void oneDirect() {
		City a = f.getCity("A");
		City b = f.getCity("B");

		a.connect(10, b);

		assertPaths("[[10 -> B]]", a, b);
		assertPaths("[[10 -> A]]", b, a);
	}

	@Test
	public void simpleJoin() {
		City a = f.getCity("A");
		City b = f.getCity("B");
		City c = f.getCity("C");

		a.connect(10, b);
		b.connect(2, c);

		assertPaths("[[10 -> B, 2 -> C]]", a, c);
		assertPaths("[[2 -> B, 10 -> A]]", c, a);
	}

	@Test
	public void diamond() {
		City a = f.getCity("A");
		City b1 = f.getCity("B1");
		City b2 = f.getCity("B2");
		City c = f.getCity("C");

		a.connect(1, b1);
		a.connect(2, b2);

		b1.connect(3, c);
		b2.connect(4, c);

		assertPaths("[[1 -> B1, 3 -> C], [2 -> B2, 4 -> C]]", a, c);
		assertPaths("[[3 -> B1, 1 -> A], [4 -> B2, 2 -> A]]", c, a);
	}

	@Test
	public void diamondWithDirect() {
		City a = f.getCity("A");
		City b1 = f.getCity("B1");
		City b2 = f.getCity("B2");
		City c = f.getCity("C");

		a.connect(1, b1);
		a.connect(2, b2);

		b1.connect(3, c);
		b2.connect(4, c);

		a.connect(5, c);

		assertPaths("[[1 -> B1, 3 -> C], [2 -> B2, 4 -> C], [5 -> C]]", a, c);
		assertPaths("[[3 -> B1, 1 -> A], [4 -> B2, 2 -> A], [5 -> A]]", c, a);
	}

	@Test
	public void diamondWithEdge() {
		City a = f.getCity("A");
		City b1 = f.getCity("B1");
		City b2 = f.getCity("B2");
		City c = f.getCity("C");

		a.connect(1, b1);
		a.connect(2, b2);

		b1.connect(3, c);
		b2.connect(4, c);

		b1.connect(5, b2);// edge

		assertPaths("[[1 -> B1, 3 -> C], [1 -> B1, 5 -> B2, 4 -> C], [2 -> B2, 4 -> C], [2 -> B2, 5 -> B1, 3 -> C]]", //
				a, c);
		assertPaths("[[3 -> B1, 1 -> A], [3 -> B1, 5 -> B2, 2 -> A], [4 -> B2, 2 -> A], [4 -> B2, 5 -> B1, 1 -> A]]", //
				c, a);
	}

	@Test
	public void diamondWithEdgeHeadTail() {
		// test cycles
		City h = f.getCity("H");
		City a = f.getCity("A");
		City b1 = f.getCity("B1");
		City b2 = f.getCity("B2");
		City c = f.getCity("C");
		City t = f.getCity("T");

		a.connect(1, b1);
		a.connect(2, b2);

		b1.connect(3, c);
		b2.connect(4, c);

		b1.connect(5, b2);// edge
		h.connect(6, a);// head
		c.connect(7, t);// tail

		// without head and tail
		assertPaths("[[1 -> B1, 3 -> C], [1 -> B1, 5 -> B2, 4 -> C], [2 -> B2, 4 -> C], [2 -> B2, 5 -> B1, 3 -> C]]", //
				a, c);
		assertPaths("[[3 -> B1, 1 -> A], [3 -> B1, 5 -> B2, 2 -> A], [4 -> B2, 2 -> A], [4 -> B2, 5 -> B1, 1 -> A]]", //
				c, a);

		// from head to tail
		assertPaths(
				"[[6 -> A, 1 -> B1, 3 -> C, 7 -> T], [6 -> A, 1 -> B1, 5 -> B2, 4 -> C, 7 -> T], [6 -> A, 2 -> B2, 4 -> C, 7 -> T], [6 -> A, 2 -> B2, 5 -> B1, 3 -> C, 7 -> T]]", //
				h, t);
		assertPaths(
				"[[7 -> C, 3 -> B1, 1 -> A, 6 -> H], [7 -> C, 3 -> B1, 5 -> B2, 2 -> A, 6 -> H], [7 -> C, 4 -> B2, 2 -> A, 6 -> H], [7 -> C, 4 -> B2, 5 -> B1, 1 -> A, 6 -> H]]", //
				t, h);
	}

	private static void assertPaths(String expected, City city1, City city2) {
		// it is very useful represent expected as string
		// it is not so fast, but it is enough for these tests
		List<City2CityPath> actual = city1.getAllPathsTo(city2);
		assertEquals(expected, actual.toString());
	}
}
