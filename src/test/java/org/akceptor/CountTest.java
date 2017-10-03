package org.akceptor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CountTest {

	private int size;
	private Stream<String> line;

	public CountTest(Stream<String> line, int size) {
		this.line = line;
		this.size = size;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { 
			{ Stream.of("AAA AaA Bb bB ccc c"), 4 }, 
			{ Stream.of("A, b, c, d,"), 4 },
			{ Stream.of("A, a, A. A,"), 1 }, 
			{ Stream.of("A , a     A  . A   ,"), 1 },
			{ Stream.of("A- a  A -a a, A-A   a-A"), 4 } };
		return Arrays.asList(data);
	}

	@Test
	public void testWordCount() {
		assertEquals(size, Main.countWordsInStream(line).size());
	}

}
