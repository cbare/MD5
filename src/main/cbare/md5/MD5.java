package cbare.md5;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Implements the MD5 cryptographic hash function as described in RFC1321 by R. Rivest.
 * <p>
 * <b>Warning:</b> The MD5 algorithm is no longer considered secure. This implementation
 * was made just for laughs and has not been thoroughly tested. Don't rely on its security
 * or correctness.
 * <p>
 * TODO: zero out copies of input and intermediate data.
 *
 * @author christopherbare@cbare.org
 * @date 2006.03.26
 *
 */
public class MD5 {

	private final static int BLOCK_SIZE = 16;
	private final static int BLOCK_SIZE_BYTES = BLOCK_SIZE * 4;

	/**
	 * initial state values
	 */
	private final static int[] h0 = {0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476};

	/**
	 * the hash state value
	 */
	private int[] h = new int[4];

	/**
	 * store partial block left over at end of an update
	 */
	private byte[] leftover;
	private int leftoverLen;

	/**
	 * length of input hashed so far in bytes
	 */
	private long hashedLen;


	public MD5() {
		reset();
	}

	/**
	 * flip the endian-ness of the input block
	 * @param block an int array of BLOCK_SIZE elements
	 * @return an int array of BLOCK_SIZE elements
	 */
	private int[] decode(int[] block) {
		int[] x = new int[BLOCK_SIZE];
		for (int i=0; i<BLOCK_SIZE; i++) {
			x[i] = Bits.rev(block[i]);
		}
		return x;
	}

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
		return Bits.leftRotate(a + f(b, c, d) + x + ac, s) + b;
	}

	private int gg(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + g(b, c, d) + x + ac, s) + b;
	}

	private int hh(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + h(b, c, d) + x + ac, s) + b;
	}

	private int ii(int a, int b, int c, int d, int x, int s, int ac)
	{
		return Bits.leftRotate(a + i(b, c, d) + x + ac, s) + b;
	}


	/**
	 * transform a block according to the MD5 algorithm.
	 * @param block a 16 element array of integers (64 bytes)
	 */
	public void hashBlock(int[] block) {
		int a = h[0];
		int b = h[1];
		int c = h[2];
		int d = h[3];

		// flip the endian-ness of the input block
		int[] x = decode(block);

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

	    // zero out inputs
	    for (int i=0; i<x.length; i++) {
	    	x[i] = 0;
	    }

		h[0] += a;
		h[1] += b;
		h[2] += c;
		h[3] += d;
	}

//  I decided to inline this, 'cause that makes it easier to reuse the int array
//  over multiple iterations and because we need to keep the value of the
//  input index in the main loop in update().
//	public void hashBlock(byte[] bytes, int index, int[] block) {
//		// convert a block of bytes to a block of integers and hashes it
//		for (int k=0; k<BLOCK_SIZE; k++) {
//			block[k] = Bits.toInt(bytes, index);
//			index += 4;
//		}
//		hashBlock(block);
//	}


	/**
	 * reset state to initial value
	 */
	public void reset() {
        for (int i=0; i<h.length; i++) {
        	h[i] = h0[i];
        }
        leftover = null;
        leftoverLen = 0;
        hashedLen = 0;
	}

	/**
	 * Computes and returns the hash of the input in one step.
	 * @return the MD5 hash code of the input
	 */
	public byte[] doFinal(byte[] input) {
		update(input);
		return doFinal();
	}

	/**
	 * Returns the hash of input previously submitted to the update(...) method
	 * and resets the state of the MD5 class.
	 * @return the MD5 hash code of accumulated input
	 */
	public byte[] doFinal() {
		int[] block = new int[BLOCK_SIZE];

		hashedLen += leftoverLen;

		// we might have one or two more blocks to hash at this point.
		// If the leftover partial block is smaller than 56 bytes, we
		// can append the padding bits and the length to it. Otherwise,
		// the padding and length will extend into another block.

		// if partial block exists
		if (leftover != null) {

			// tag the end of input
			// leftoverLen should be < leftover.length
			leftover[leftoverLen++] = (byte)0x80;

			if (leftoverLen > BLOCK_SIZE_BYTES - 8) {
				for (;leftoverLen < BLOCK_SIZE_BYTES; leftoverLen++) {
					leftover[leftoverLen] = (byte)0x00;
				}

				// convert the block to integers
				int i = 0;
				for (int k=0; k<BLOCK_SIZE; k++) {
					block[k] = Bits.toInt(leftover, i);
					i += 4;
				}
				hashBlock(block);

				leftover = new byte[BLOCK_SIZE_BYTES];
				leftoverLen = 0;
			}
		}
		else {
			leftover = new byte[BLOCK_SIZE_BYTES];
			leftoverLen = 0;
			leftover[leftoverLen++] = (byte)0x80;
		}


		for (;leftoverLen < (BLOCK_SIZE_BYTES - 8); leftoverLen++) {
			leftover[leftoverLen] = (byte)0x00;
		}
		Bits.toBytesBigEndian(hashedLen * 8, leftover, leftoverLen);

		// convert the block to integers
		int i = 0;
		for (int k=0; k<BLOCK_SIZE; k++) {
			block[k] = Bits.toInt(leftover, i);
			i += 4;
		}
		//System.out.println(Bits.toHexString(block));
		hashBlock(block);

		// make a 16 byte array (128 bits)
		byte[] byteArray = new byte[16];
		for (int k=0; k<4; k++) {
			Bits.toBytesBigEndian(h[k], byteArray, k*4);
		}

		reset();

		return byteArray;
	}

	/**
	 * updates the state of the MD5 class by processing the given input.
	 */
	public void update(byte[] input) {
		update(input, 0, input.length);
	}

	/**
	 * updates the state of the MD5 class by processing the given input
	 * starting at <i>offset</i> and extending for <i>len</i> bytes.
	 */
	public void update(byte[] input, int offset, int len) {

		int[] block = new int[BLOCK_SIZE];
		int inputIndex = offset;

		// if there is a leftover partial block, start with that
		if (leftover != null) {

			// if we still can't fill a complete block
			if (len + leftoverLen < BLOCK_SIZE_BYTES)
			{
				// store the input and bail out
				System.arraycopy(input, offset, leftover, leftoverLen, len);
				leftoverLen += input.length;
				return;
			}

			// fill up the partial block
			inputIndex += leftover.length - leftoverLen;
			System.arraycopy(input, offset, leftover, leftoverLen, inputIndex);

			// convert the block to integers
			int i = 0;
			for (int k=0; k<BLOCK_SIZE; k++) {
				block[k] = Bits.toInt(leftover, i);
				i += 4;
			}
			hashBlock(block);
			hashedLen += BLOCK_SIZE_BYTES;
		}

		// if we have enough input for a block
		while ( (len+offset-inputIndex) >= (BLOCK_SIZE_BYTES) ) {
			// convert the block to integers
			for (int k=0; k<BLOCK_SIZE; k++) {
				block[k] = Bits.toInt(input, inputIndex);
				inputIndex += 4;
			}
			hashBlock(block);
			hashedLen += BLOCK_SIZE_BYTES;
		}

		// store leftover partial block
		leftoverLen = len+offset-inputIndex;
		if (leftoverLen > 0) {
			leftover = new byte[BLOCK_SIZE_BYTES];
			System.arraycopy(input, inputIndex, leftover, 0, leftoverLen);
		}
		else {
			leftover = null;
		}

	}

	/**
	 * updates the MD5 state by hashing the contents of the given file.
	 * @param filename a path that my start with the prefix "classpath:" to
	 * indicate a classpath relative path.
	 * @throws IOException
	 */
	public void hashFile(String filename) throws IOException {
		InputStream input = null;
		if (filename.startsWith("classpath:")) {
			filename = filename.substring(10);
			input = MD5.class.getResourceAsStream(filename);
		}
		else {
			input = new BufferedInputStream(new FileInputStream(filename));
		}
		hashInputStream(input);
	}

	/**
	 * updates the MD5 state by hashing data from an input stream.
	 */
	public void hashInputStream(InputStream in) throws IOException {
		byte[] bytes = new byte[BLOCK_SIZE];
		int len;
		while ((len = in.read(bytes)) > -1) {
			update(bytes, 0, len);
		}
	}


	public static void usage() {
		String ln = System.getProperty("line.separator");
		System.out.println(
			"=====" + ln +
			" MD5" + ln +
			"=====" + ln + ln +
			"> java cbare.md5.MD5 [filename] [filename2] ..." + ln + ln +
			"Implements the MD5 cryptographic hash function. If filename(s) are given," + ln +
			"the program computes the hash code of the given file(s). If no filename is" + ln +
			"given, the program reads input from the standard input stream. The program" + ln +
			"then prints the MD5 hash code to the standard output stream. ");
		System.out.println( ln +
			"**Warning: MD5 is no longer recommended and this implementation is not" + ln +
			"well tested, so don't rely on it for security.");
	}

	/**
	 * entry point for command line usage
	 */
	public static void main(String[] args) throws Exception {
		MD5 md5 = new MD5();

		if (args.length > 0 && ("?".equals(args[0]) || "-?".equals(args[0]) || "-h".equals(args[0]) || "--help".equals(args[0]))) {
			usage();
			return;
		}

		if (args.length > 0) {
			for (int i=0; i<args.length; i++) {
				md5.hashFile(args[i]);
			}
		}
		else {
			md5.hashInputStream(System.in);
		}

        System.out.println(Bits.toHexString(md5.doFinal()));
	}
}
