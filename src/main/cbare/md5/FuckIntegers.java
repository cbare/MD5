package cbare.md5;

public class FuckIntegers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0x98badcfe + 0x67452301 + 0xd76aa478 + 0x00000080;
		System.out.println(Integer.toHexString(i));

		long g = 0x98badcfeL + 0x67452301L + 0xd76aa478L + 0x80000000L;
		System.out.println(Long.toHexString(g));
		System.out.println(Long.toHexString(g & 0xFFFFFFFFL));
		
		System.out.println(Integer.toHexString(0xefcdab89 & 0x98badcfe | ((~0xefcdab89) & 0x10325476)));
		System.out.println(Long.toHexString(0xefcdab89L & 0x98badcfeL | ((~0xefcdab89L) & 0x10325476L)));
	}

}
