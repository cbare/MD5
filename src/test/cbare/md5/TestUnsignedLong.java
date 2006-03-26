package cbare.md5;

import junit.framework.TestCase;

/**
 * can we use a long as if it were unsigned?
 * @author Chris
 */
public class TestUnsignedLong extends TestCase {

	public void test() {
		long x = 0x7444744474447444L;
		System.out.println(x);
		System.out.println(Bits.toHexString(Bits.toBytes(x)));
		x *= 2;
		System.out.println(x);
		System.out.println(Bits.toHexString(Bits.toBytes(x)));
		assertEquals("e888e888e888e888", Bits.toHexString(Bits.toBytes(x)));
	}
}
