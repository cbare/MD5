package cbare.md5;

/**
 * needs update & finalize
 * md5 state
 * @author christopherbare
 *
 */
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

		int i = 0, k;
        
        {
            StringBuilder sb = new StringBuilder("[");
            for (int m=0; m<x.length; m++) {
                sb.append(String.format("%08x-",Bits.rev(x[m])));
            }
            sb.append("]");
            System.out.println(sb.toString());
        }
        
        //if (1==1) return;
        
        {
        System.out.println("------------");
        
        int tf = f(b,c,d);
        System.out.printf("f(...) = %08x = %s\n", tf, Integer.toBinaryString(tf));
        System.out.printf("a = %08x = %s\n", a, Integer.toBinaryString(a));
        System.out.printf("b = %08x = %s\n", b, Integer.toBinaryString(b));
        System.out.printf("ac[0] = %08x = %s\n", ac[0], Integer.toBinaryString(ac[0]));
        System.out.printf("x[0] = %08x = %s\n", x[0], Integer.toBinaryString(x[0]));
        int tz = a + tf + ac[0] + x[0];
        System.out.printf("tz = %08x\n", tz);
        System.out.println(Integer.toBinaryString(tz));
        int tzr = Bits.leftRotate(tz, s[0]);
        System.out.printf("tzr = %08x\n", tzr);
        System.out.println(Integer.toBinaryString(tzr));

        System.out.println("------------");
        }
        
        // Round 1
		for (; i<16; i++) {
            k = offset + i;
            temp = Bits.rev(x[k]);
            temp = Bits.leftRotate(a + f(b,c,d) + ac[i] + Bits.rev(x[k]), s[i]) + b;
            a = d;
            d = c;
            c = b;
            b = temp;
            System.out.printf("a,b,c,d = %d, %d, %d, %d\n", (long)a&0xFFFFFFFFL, (long)b&0xFFFFFFFFL, (long)c&0xFFFFFFFFL, (long)d&0xFFFFFFFFL);
		}

        // Round 2
		for (; i<32; i++) {
            k = offset + ((5*i + 1) % 16);
            temp = Bits.leftRotate(a + g(b,c,d) + ac[i] + Bits.rev(x[k]), s[i]) + b;
            a = d;
            d = c;
            c = b;
            b = temp;
            System.out.printf("a,b,c,d = %d, %d, %d, %d\n", (long)a&0xFFFFFFFFL, (long)b&0xFFFFFFFFL, (long)c&0xFFFFFFFFL, (long)d&0xFFFFFFFFL);
		}

        // Round 3
		for (; i<48; i++) {
            k = offset + ((3*i + 5) % 16);
            temp = Bits.leftRotate(a + h(b,c,d) + ac[i] + Bits.rev(x[k]), s[i]) + b;
            a = d;
            d = c;
            c = b;
            b = temp;
            System.out.printf("a,b,c,d = %d, %d, %d, %d\n", (long)a&0xFFFFFFFFL, (long)b&0xFFFFFFFFL, (long)c&0xFFFFFFFFL, (long)d&0xFFFFFFFFL);
		}

        // Round 4
		for (; i<64; i++) {
            k = offset + ((7*i) % 16);
            temp = Bits.leftRotate(a + i(b,c,d) + ac[i] + Bits.rev(x[k]), s[i]) + b;
            a = d;
            d = c;
            c = b;
            b = temp;
            System.out.printf("a,b,c,d = %d, %d, %d, %d\n", (long)a&0xFFFFFFFFL, (long)b&0xFFFFFFFFL, (long)c&0xFFFFFFFFL, (long)d&0xFFFFFFFFL);
		}

		h[0] += a;
		h[1] += b;
		h[2] += c;
		h[3] += d;
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
