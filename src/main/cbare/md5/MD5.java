package cbare.md5;

import java.io.UnsupportedEncodingException;


public class MD5 {

	private static int[] s = {
		 7,12,17,22, 7,12,17,22, 7,12,17,22, 7,12,17,22,
		 5, 9,14,20, 5, 9,14,20, 5, 9,14,20, 5, 9,14,20,
		 4,11,16,23, 4,11,16,23, 4,11,16,23, 4,11,16,23,
		 6,10,15,21, 6,10,15,21, 6,10,15,21, 6,10,15,21,
	};

	private static int[] h0 = {0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476};

	private static int[] ac = new int[64];
	static
	{
		for (int i=0; i<64; i++) {
			long l = (long)Math.floor(Math.abs(Math.sin(i + 1)) * 0x0100000000L);
			if (l>Integer.MAX_VALUE)
				l = (Integer.MAX_VALUE - l) ^ 0x7FFFFFFF;
			ac[i] = (int)l;
		}
	}

	private int f(int x, int y, int z) {
		return (x & y) | (~x & z);
	}
	private int g(int x, int y, int z) {
		return (x & z) | (y & ~z);
	}
	private int h(int x, int y, int z) {
		return (x ^ y ^ z);
	}
	private int i(int x, int y, int z) {
		return (y ^ (x | ~z));
	}

	/**
	 *
	 * @param h
	 * @param x
	 */
	public void hashBlock(int[] h, int[] x, int offset) {
		int a = h[0];
		int b = h[1];
		int c = h[2];
		int d = h[3];
		int temp;

		int i = 0;
		for (; i<16; i++) {
			temp = d;
			d = c;
			c = b;
			b = ((a + f(b,c,d) + ac[i] + x[offset + i]) << s[i]) + b;
			a = temp;
		}
		for (; i<32; i++) {
			temp = d;
			d = c;
			c = b;
			b = ((a + g(b,c,d) + ac[i] + x[offset + (5*i + 1) % 16]) << s[i]) + b;
			a = temp;
		}
		for (; i<48; i++) {
			temp = d;
			d = c;
			c = b;
			b = ((a + h(b,c,d) + ac[i] + x[offset + (3*i + 5) % 16]) << s[i]) + b;
			a = temp;
		}
		for (; i<64; i++) {
			temp = d;
			d = c;
			c = b;
			b = ((a + i(b,c,d) + ac[i] + x[offset + (7*i) % 16]) << s[i]) + b;
			a = temp;
		}

		h[0] += a;
		h[1] += b;
		h[2] += c;
		h[3] += d;
	}

	public byte[] pad(byte[] data) {
		int len = data.length;
		int paddedLen = (len/64)*64 + 64;

		if (paddedLen - len < 9)
			paddedLen += 64;

		byte[] paddedData = new byte[paddedLen];
		System.arraycopy(data, 0, paddedData, 0, len);
		paddedData[len] = (byte)0x80;

		int i = len+1;
		for (; i<paddedLen-8; i++) {
			paddedData[i] = 0;
		}

		long bits = len * 8;
		Bits.toBytes(bits, paddedData, i);

		return paddedData;
	}

	public byte[] hash(byte[] data) {
		byte[] paddedDataByteArray = pad(data);

		Bits.arrayToString(paddedDataByteArray);

		// convert byte array to int array
		int len = paddedDataByteArray.length / 4;
		int[] paddedData = new int[len];
		for (int i=0; i<len; i++) {
			paddedData[i] = Bits.toInt(paddedDataByteArray, i*4);
		}


		// hash each block
		int[] h = h0;
		// a block is 512 bits or 64 bytes or 16 32-bit ints.
		for (int i=0; i<len; i+=16) {
			hashBlock(h, paddedData, i);
		}

		// make a 16 byte array (128 bits)
		byte[] byteArray = new byte[16];
		for (int i=0; i<4; i++) {
			Bits.toBytes(h[i], byteArray, i*4);
		}

		return byteArray;
	}

	public static void main(String[] args) throws Exception {
		MD5 md5 = new MD5();
		System.out.println(Bits.toHexString(md5.hash("".getBytes("US-ASCII"))));
		System.out.println(Bits.toHexString(md5.hash("The quick brown fox jumps over the lazy dog".getBytes("US-ASCII"))));
	}
}
