package cbare.md5;

public class Bits {


	public static int toInt(byte[] b, int offset) {
		return ((b[offset] & 0xFF) << 24)
		| ((b[offset+1] & 0xFF) << 16)
		| ((b[offset+2] & 0xFF) << 8)
		| (b[offset+3] & 0xFF);
	}

	/**
	 * convert a byte array to an int in most-significant-byte
	 * first order
	 */
	public static int toBigEndianInt(byte[] bytes, int offset) {
		return ((bytes[offset+3] & 0xFF) << 24)
		| ((bytes[offset+2] & 0xFF) << 16)
		| ((bytes[offset+1] & 0xFF) << 8)
		| (bytes[offset] & 0xFF);
	}

	public static byte[] toBytes(long l) {
		byte[] b = new byte[8];
		for (int i=7; i>=0; i--) {
			b[i] = (byte)(l & 0xFFL);
			l = l >> 8;
		}
		return b;
	}

	public static void toBytes(long l, byte[] b, int offset) {
		for (int i=7; i>=0; i--) {
			b[offset + i] = (byte)(l & 0xFFL);
			l = l >> 8;
		}
	}

	public static byte[] toBytes(int a) {
		byte[] b = new byte[4];
		for (int i=3; i>=0; i--) {
			b[i] = (byte)(a & 0x000000FF);
			a = a >>> 8;
		}
		return b;
	}

	public static void toBytes(int a, byte[] b, int offset) {
		for (int i=3; i>=0; i--) {
			b[offset+i] = (byte)(a & 0x000000FF);
			a = a >>> 8;
		}
	}

	public static void toBytesBigEndian(int a, byte[] bytes, int offset) {
		bytes[offset+3]   = (byte)((a >>> 24) & 0x000000FF);
		bytes[offset+2] = (byte)((a >>> 16) & 0x000000FF);
		bytes[offset+1] = (byte)((a >>> 8) & 0x000000FF);
		bytes[offset] = (byte)((a) & 0x000000FF);
	}

	public static void toBytesBigEndian(long a, byte[] bytes, int offset) {
		for (int i=0; i<8; i++) {
			bytes[offset+i] = (byte)(a & 0x000000FF);
			a = a >>> 8;
		}
	}

    public static int leftRotate(int a, int s) {
        return (a << s) | (a >>> (32-s));
    }


	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<b.length; i++) {
			sb.append(String.format("%02x",b[i]));
		}
		return sb.toString();
	}

    public static Object toHexString(int[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<data.length; i++) {
            sb.append(String.format("%08x",data[i]));
        }
        return sb.toString();
    }

    public static String arrayToString(int[] a) {
        StringBuilder sb = new StringBuilder("[");
        if (a.length > 0) {
            sb.append(Integer.toHexString(rev(a[0])));
        }
        for (int i=1; i<a.length; i++) {
            sb.append(", ").append(Integer.toHexString(rev(a[i])));
        }
        sb.append("]");
        return sb.toString();
    }

    public static int rev(int x) {
        return (x>>>24) | ((x>>>8) &0x0000FF00) | ((x<<8) & 0x00FF0000) | (x<<24);
    }

    public static String arrayToStringBigEndian(int[] a) {
        StringBuilder sb = new StringBuilder("[");
        if (a.length > 0) {
            sb.append(Integer.toHexString(a[0]));
        }
        for (int i=1; i<a.length; i++) {
            sb.append(", ").append(Integer.toHexString(a[i]));
        }
        sb.append("]");
        return sb.toString();
    }

	public static String arrayToString(byte[] a) {
		StringBuilder sb = new StringBuilder("[");
		if (a.length > 0) {
			sb.append(Integer.toHexString(a[0]));
		}
		for (int i=1; i<a.length; i++) {
			sb.append(", ").append(Integer.toHexString(a[i]));
		}
		sb.append("]");
		return sb.toString();
	}

	public static byte[] hexStringToByteArray(String hex) {
		int len = hex.length() / 2;
		byte[] bytes = new byte[len];

		for (int i=0; i<len; i++) {
			bytes[i] = Byte.parseByte(hex.substring(i*2,i*2+2));
		}
		return bytes;
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
