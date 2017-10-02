package org.akceptor;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class MainTest {

	@Test
	@Parameters({ 
	      "AA\\, Aa\\, aa\\, bb\\, bb\\, cc, 3", "AA Aa aa. bb\\, bb\\, cc, 4"})
	public void testWordCount(String line) {
		assertEquals(3, Main.countWordsInSteram(Stream.of(line)).size());
	}

}
