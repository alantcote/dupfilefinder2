/**
 * 
 */
package cotelab.dupfilefinder2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test case for {@link cotelab.dupfilefinder2.String2StringConverter}.
 */
public class String2StringConverterTest {
	public static final String TEST_STRING = "test string";

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.String2StringConverter#fromString(java.lang.String)}.
	 */
	@Test
	public void testFromStringString() {
		String2StringConverter fixture = new String2StringConverter();
		String result = fixture.fromString(TEST_STRING);

		assertEquals(TEST_STRING, result);
	}

	/**
	 * Test method for
	 * {@link cotelab.dupfilefinder2.String2StringConverter#toString(java.lang.String)}.
	 */
	@Test
	public void testToStringString() {
		String2StringConverter fixture = new String2StringConverter();
		String result = fixture.toString(TEST_STRING);

		assertEquals(TEST_STRING, result);
	}

}
