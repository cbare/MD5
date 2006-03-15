package cbare.md5;

import cbare.md5.MD5;
import junit.framework.TestCase;

public class TestMD5 extends TestCase {

	MD5 md5 = new MD5();

	public void testPad1() {
		byte[] padded = md5.pad(Bits.toBytes(0x0123456789abcdefL));
		System.out.println(Bits.toHexString(padded));
		assertEquals(64, padded.length);
		assertEquals(64, padded[63]);
		assertEquals(0, padded[62]);
		assertEquals((byte)0x80, padded[8]);
	}

	public void testPad2() {
		byte[] b = new byte[60];
		Bits.toBytes(0x0123456789abcdefL, b, 0);
		Bits.toBytes(0x0123456789abcdefL, b, 8);
		Bits.toBytes(0x0123456789abcdefL, b, 16);
		Bits.toBytes(0x0123456789abcdefL, b, 24);
		Bits.toBytes(0x0123456789abcdefL, b, 32);
		Bits.toBytes(0x0123456789abcdefL, b, 40);
		Bits.toBytes(0x0123456789abcdefL, b, 48);
		b[56] = 1;
		b[57] = 2;
		b[58] = 3;
		b[59] = 4;
		String output = Bits.toHexString(md5.pad(b));
		System.out.println(output);
		assertEquals(
			"[0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0102030480000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001e0]",
			output);
	}

	public void testPad3() {
		byte[] b = new byte[55];
		Bits.toBytes(0x0123456789abcdefL, b, 0);
		Bits.toBytes(0x0123456789abcdefL, b, 8);
		Bits.toBytes(0x0123456789abcdefL, b, 16);
		Bits.toBytes(0x0123456789abcdefL, b, 24);
		Bits.toBytes(0x0123456789abcdefL, b, 32);
		Bits.toBytes(0x0123456789abcdefL, b, 40);
		for (int i=48; i<b.length; i++) {
			b[i] = (byte)(i-47);
		}
		String output = Bits.toHexString(md5.pad(b));
		System.out.println(output);
		assertEquals(
			"[0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef0123456789abcdef010203040506078000000000000001b8]",
			output);
	}

}
