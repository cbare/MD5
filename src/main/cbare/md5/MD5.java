package cbare.md5;

/**
 * needs update & finalize
 * main method - digest file or stdin
 * implement java.crypto.Mac?
 * @author christopherbare
 *
 */
public class MD5 {

	private static int[] h0 = {0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476};


	private int f(int x, int y, int z) {
		return (x & y) | ((~x) & z);
	}

	private int g(int x, int y, int z) {
		return (x & z) | (y & (~z));
	}

	private int h(int x, int y, int z) {
		return (x ^ y ^ z);
	}

	private int i(int x, int y, int z) {
		return (y ^ (x | (~z)));
	}

	private int ff(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + f(b, c, d) + Bits.rev(x) + ac, s) + b;
	}

	private int gg(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + g(b, c, d) + Bits.rev(x) + ac, s) + b;
	}

	private int hh(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + h(b, c, d) + Bits.rev(x) + ac, s) + b;
	}

	private int ii(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + i(b, c, d) + Bits.rev(x) + ac, s) + b;
	}


	public void reset() {

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

	    // Round 1
	    a = ff(a, b, c, d, x[ 0],  7, 0xd76aa478); /* 1 */
	    d = ff(d, a, b, c, x[ 1], 12, 0xe8c7b756); /* 2 */
	    c = ff(c, d, a, b, x[ 2], 17, 0x242070db); /* 3 */
	    b = ff(b, c, d, a, x[ 3], 22, 0xc1bdceee); /* 4 */
	    a = ff(a, b, c, d, x[ 4],  7, 0xf57c0faf); /* 5 */
	    d = ff(d, a, b, c, x[ 5], 12, 0x4787c62a); /* 6 */
	    c = ff(c, d, a, b, x[ 6], 17, 0xa8304613); /* 7 */
	    b = ff(b, c, d, a, x[ 7], 22, 0xfd469501); /* 8 */
	    a = ff(a, b, c, d, x[ 8],  7, 0x698098d8); /* 9 */
	    d = ff(d, a, b, c, x[ 9], 12, 0x8b44f7af); /* 10 */
	    c = ff(c, d, a, b, x[10], 17, 0xffff5bb1); /* 11 */
	    b = ff(b, c, d, a, x[11], 22, 0x895cd7be); /* 12 */
	    a = ff(a, b, c, d, x[12],  7, 0x6b901122); /* 13 */
	    d = ff(d, a, b, c, x[13], 12, 0xfd987193); /* 14 */
	    c = ff(c, d, a, b, x[14], 17, 0xa679438e); /* 15 */
	    b = ff(b, c, d, a, x[15], 22, 0x49b40821); /* 16 */

	    // Round 2
	    a = gg(a, b, c, d, x[ 1],  5, 0xf61e2562); /* 17 */
	    d = gg(d, a, b, c, x[ 6],  9, 0xc040b340); /* 18 */
	    c = gg(c, d, a, b, x[11], 14, 0x265e5a51); /* 19 */
	    b = gg(b, c, d, a, x[ 0], 20, 0xe9b6c7aa); /* 20 */
	    a = gg(a, b, c, d, x[ 5],  5, 0xd62f105d); /* 21 */
	    d = gg(d, a, b, c, x[10],  9,  0x2441453); /* 22 */
	    c = gg(c, d, a, b, x[15], 14, 0xd8a1e681); /* 23 */
	    b = gg(b, c, d, a, x[ 4], 20, 0xe7d3fbc8); /* 24 */
	    a = gg(a, b, c, d, x[ 9],  5, 0x21e1cde6); /* 25 */
	    d = gg(d, a, b, c, x[14],  9, 0xc33707d6); /* 26 */
	    c = gg(c, d, a, b, x[ 3], 14, 0xf4d50d87); /* 27 */
	    b = gg(b, c, d, a, x[ 8], 20, 0x455a14ed); /* 28 */
	    a = gg(a, b, c, d, x[13],  5, 0xa9e3e905); /* 29 */
	    d = gg(d, a, b, c, x[ 2],  9, 0xfcefa3f8); /* 30 */
	    c = gg(c, d, a, b, x[ 7], 14, 0x676f02d9); /* 31 */
	    b = gg(b, c, d, a, x[12], 20, 0x8d2a4c8a); /* 32 */

	    // Round 3
	    a = hh(a, b, c, d, x[ 5],  4, 0xfffa3942); /* 33 */
	    d = hh(d, a, b, c, x[ 8], 11, 0x8771f681); /* 34 */
	    c = hh(c, d, a, b, x[11], 16, 0x6d9d6122); /* 35 */
	    b = hh(b, c, d, a, x[14], 23, 0xfde5380c); /* 36 */
	    a = hh(a, b, c, d, x[ 1],  4, 0xa4beea44); /* 37 */
	    d = hh(d, a, b, c, x[ 4], 11, 0x4bdecfa9); /* 38 */
	    c = hh(c, d, a, b, x[ 7], 16, 0xf6bb4b60); /* 39 */
	    b = hh(b, c, d, a, x[10], 23, 0xbebfbc70); /* 40 */
	    a = hh(a, b, c, d, x[13],  4, 0x289b7ec6); /* 41 */
	    d = hh(d, a, b, c, x[ 0], 11, 0xeaa127fa); /* 42 */
	    c = hh(c, d, a, b, x[ 3], 16, 0xd4ef3085); /* 43 */
	    b = hh(b, c, d, a, x[ 6], 23,  0x4881d05); /* 44 */
	    a = hh(a, b, c, d, x[ 9],  4, 0xd9d4d039); /* 45 */
	    d = hh(d, a, b, c, x[12], 11, 0xe6db99e5); /* 46 */
	    c = hh(c, d, a, b, x[15], 16, 0x1fa27cf8); /* 47 */
	    b = hh(b, c, d, a, x[ 2], 23, 0xc4ac5665); /* 48 */

	    // Round 4
	    a = ii(a, b, c, d, x[ 0],  6, 0xf4292244); /* 49 */
	    d = ii(d, a, b, c, x[ 7], 10, 0x432aff97); /* 50 */
	    c = ii(c, d, a, b, x[14], 15, 0xab9423a7); /* 51 */
	    b = ii(b, c, d, a, x[ 5], 21, 0xfc93a039); /* 52 */
	    a = ii(a, b, c, d, x[12],  6, 0x655b59c3); /* 53 */
	    d = ii(d, a, b, c, x[ 3], 10, 0x8f0ccc92); /* 54 */
	    c = ii(c, d, a, b, x[10], 15, 0xffeff47d); /* 55 */
	    b = ii(b, c, d, a, x[ 1], 21, 0x85845dd1); /* 56 */
	    a = ii(a, b, c, d, x[ 8],  6, 0x6fa87e4f); /* 57 */
	    d = ii(d, a, b, c, x[15], 10, 0xfe2ce6e0); /* 58 */
	    c = ii(c, d, a, b, x[ 6], 15, 0xa3014314); /* 59 */
	    b = ii(b, c, d, a, x[13], 21, 0x4e0811a1); /* 60 */
	    a = ii(a, b, c, d, x[ 4],  6, 0xf7537e82); /* 61 */
	    d = ii(d, a, b, c, x[11], 10, 0xbd3af235); /* 62 */
	    c = ii(c, d, a, b, x[ 2], 15, 0x2ad7d2bb); /* 63 */
	    b = ii(b, c, d, a, x[ 9], 21, 0xeb86d391); /* 64 */

		h[0] += a;
		h[1] += b;
		h[2] += c;
		h[3] += d;
	}

	public byte[] doFinal() {
		reset();
		return null;
	}

	public void update(byte[] input) {

	}

	public byte[] pad(byte[] data) {
		int len = data.length;
		int paddedLen = (len/64)*64  + 64;

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
		Bits.toBytesBigEndian(bits, paddedData, i);

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
        System.out.println("paddedData in int[] = " + Bits.toHexString(paddedData));
        System.out.println(paddedData.length);


        // set initial values
        int[] h = new int[h0.length];
        for (int i=0; i<h0.length; i++)
        		h[i] = h0[i];

		// hash each block
		// a block is 512 bits or 64 bytes or 16 32-bit ints.
		for (int i=0; i<len; i+=16) {
			hashBlock(h, paddedData, i);
		}

		// make a 16 byte array (128 bits)
		byte[] byteArray = new byte[16];
		for (int i=0; i<4; i++) {
			Bits.toBytesBigEndian(h[i], byteArray, i*4);
		}

		return byteArray;
	}


	public static void main(String[] args) throws Exception {
		MD5 md5 = new MD5();

        System.out.println("md5(\"\") = " + Bits.toHexString(md5.hash("".getBytes("US-ASCII"))));
        System.out.println("md5(\"a\") = " + Bits.toHexString(md5.hash("a".getBytes("US-ASCII"))));
        System.out.println("md5(\"abc\") = " + Bits.toHexString(md5.hash("abc".getBytes("US-ASCII"))));
        System.out.println("md5(\"abcdefghijklmnopqrstuvwxyz\") = " + Bits.toHexString(md5.hash("abcdefghijklmnopqrstuvwxyz".getBytes("US-ASCII"))));
        System.out.println("md5(\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\") = " + Bits.toHexString(md5.hash("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".getBytes("US-ASCII"))));
//        System.out.println(Bits.toHexString(md5.hash("The quick brown fox jumps over the lazy dog".getBytes("US-ASCII"))));
//        System.out.println(Bits.toHexString(md5.hash("The quick brown fox jumps over the lazy cog".getBytes("US-ASCII"))));
	}
}
