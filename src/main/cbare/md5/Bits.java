package cbare.md5;

public class Bits {

	
	public static byte[] toBytes(int a) {
		byte[] b = new byte[4];
		for (int i=3; i>=0; i--) {
			b[i] = (byte)(a & 0x000000FF);
			a = a >>> 8; 
		}
		return b;
	}

	public static int toInt(byte[] b, int i) {
		return ((b[i] & 0xFF) << 24)
		| ((b[i+1] & 0xFF) << 16)
		| ((b[i+2] & 0xFF) << 8)
		| (b[i+3] & 0xFF);
	}
	
	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder("[");
		if (b.length>0)
			sb.append(String.format("%2x", b[0]));
		for (int i=1; i<b.length; i++) {
			sb.append(", ").append(String.format("%2x",b[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
		int i = 0xFF2A8199;
		System.out.println(Integer.toHexString(i));
		byte[] b = toBytes(i);
		System.out.println(toHexString(b));
		int j = toInt(b,0);
		System.out.println(Integer.toHexString(j));
	}

}
