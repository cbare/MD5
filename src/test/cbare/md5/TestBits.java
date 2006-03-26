package cbare.md5;

import junit.framework.TestCase;

/**
 * Test bit-twiddling operations.
 * @author Chris
 */
public class TestBits extends TestCase {

	public String rotate(String bits, int r) {
		r = r % bits.length();
		return bits.substring(r) + bits.substring(0, r);
	}

    public void testRotate() {
        int k = 0x98765432;
        System.out.println("k =  " + Integer.toBinaryString(k));

        for (int i=0; i<=32; i++) {
        	int r = Bits.leftRotate(k,i);
            System.out.println(String.format("%2d:  %32s", i, Integer.toBinaryString(r)));
            long expected = Long.parseLong(rotate("10011000011101100101010000110010", i) ,2);
            long actual = ((long)r & 0xFFFFFFFFL);
            assertEquals(expected, actual);
        }
    }

    public void testToBigEndianInt() {

    }
}
