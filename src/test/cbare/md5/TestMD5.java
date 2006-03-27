package cbare.md5;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

/**
 * Test MD5
 * @author Chris
 */
public class TestMD5 extends TestCase {
	private MD5 md5 = new MD5();

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestMD5.class);
	}

	public TestMD5(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		md5.reset();
	}

	public void md5Test(String expectedHash, String testString) {
		try {
			byte[] hash = md5.doFinal(testString.getBytes("US-ASCII"));
			System.out.println("md5(\"" + testString + "\") = " + Bits.toHexString(hash));
			assertEquals(expectedHash, Bits.toHexString(hash));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			fail("no support for encoding: \"US-ASCII\"");
		}
	}

	public void testEmptyString() {
		md5Test("d41d8cd98f00b204e9800998ecf8427e", "");
	}

	public void testA() {
		md5Test("0cc175b9c0f1b6a831c399e269772661", "a");
	}

	public void testAbc() {
		md5Test("900150983cd24fb0d6963f7d28e17f72", "abc");
	}

	public void testMessageDigest() {
		md5Test("f96b697d7cb7938d525a2f31aaf161d0", "message digest");
	}

	public void testAlphabet() {
		md5Test("c3fcd3d76192e4007dfb496cca67e13b", "abcdefghijklmnopqrstuvwxyz");
	}

	public void testAlphanumeric() {
		md5Test("d174ab98d277d9f5a5611c2c9f419d9f", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
	}

	public void testDigits() {
		md5Test("57edf4a22be3c955ac49da2e2107b67a", "12345678901234567890123456789012345678901234567890123456789012345678901234567890");
	}

	public void testQuickBrownFox() {
		md5Test("9e107d9d372bb6826bd81d3542a419d6",
				"The quick brown fox jumps over the lazy dog");
	}

	public void testQuickBrownFox2() {
		md5Test("1055d3e698d289f2af8663725127bd4b",
				"The quick brown fox jumps over the lazy cog");
	}

	public void testMessageDigestTwoUpdates() throws Exception {
		md5.update("message ".getBytes("US-ASCII"));
		md5.update("digest".getBytes("US-ASCII"));
		byte[] hash = md5.doFinal();
		System.out.println("md5(\"message digest\") = " + Bits.toHexString(hash));
		assertEquals("f96b697d7cb7938d525a2f31aaf161d0", Bits.toHexString(hash));
	}

	public void testEvenBlockBoundary1() throws Exception {
		md5.update("abcdefghijklmnopqrstuvwxyzABCDEF".getBytes("US-ASCII"));
		byte[] hash = md5.doFinal();
		System.out.println("md5(\"abcdefghijklmnopqrstuvwxyzABCDEF\") = " + Bits.toHexString(hash));
		assertEquals("dd2596a4d6ab3b9da8e7b19d448cf35a", Bits.toHexString(hash));
	}

	public void testEvenBlockBoundary2() throws Exception {
		md5.update("abcdefghijklmnopqrstuvwxyzABCDEF12345678901234567890123456789012".getBytes("US-ASCII"));
		byte[] hash = md5.doFinal();
		System.out.println("md5(\"abcdefghijklmnopqrstuvwxyzABCDEF12345678901234567890123456789012\") = " + Bits.toHexString(hash));
		assertEquals("70538c9c758ec5b11e777a03be07efac", Bits.toHexString(hash));
	}

	public void testEvenBlockBoundary3() throws Exception {
		md5.update("abcdefghijklmnopqrstuvwxyzABCDEF".getBytes("US-ASCII"));
		md5.update("12345678901234567890123456789012".getBytes("US-ASCII"));
		byte[] hash = md5.doFinal();
		System.out.println("md5(\"abcdefghijklmnopqrstuvwxyzABCDEF12345678901234567890123456789012\") = " + Bits.toHexString(hash));
		assertEquals("70538c9c758ec5b11e777a03be07efac", Bits.toHexString(hash));
	}

	public void testMultipleUpdates() throws Exception {
		for (int i=0; i<8; i++) {
			md5.update("1234567890".getBytes("US-ASCII"));
		}
		byte[] hash = md5.doFinal();
		System.out.println("md5.update(\"1234567890\"x8) = " + Bits.toHexString(hash));
		assertEquals("57edf4a22be3c955ac49da2e2107b67a", Bits.toHexString(hash));
	}

	public void testUpdateOffset() throws Exception {
		md5.update("don't-hash-me:abcdefghijklmnopqrstuvwxyz:don't-hash-me".getBytes("US-ASCII"), 14, 26);
		byte[] hash = md5.doFinal();
		System.out.println("md5(\"abcdefghijklmnopqrstuvwxyz\") = " + Bits.toHexString(hash));
		assertEquals("c3fcd3d76192e4007dfb496cca67e13b", Bits.toHexString(hash));
	}

	public void testFile() throws Exception {
		md5.hashFile("classpath:test.txt");
		byte[] hash = md5.doFinal();
		System.out.println("md5( file ) = " + Bits.toHexString(hash));
		assertEquals("57edf4a22be3c955ac49da2e2107b67a", Bits.toHexString(hash));
	}
}
