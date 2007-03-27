package cbare.md5;

public class Options {
	String input;
	String expected;
	String[] filenames;
	boolean done;

	public Options(String[] args) {
		try {
			parse(args);
		}
		catch (OptionsException e) {
			usage();
			System.out.println("ERROR>>> " + e.getMessage());
			done = true;
		}
	}
	

	public static void usage() {
		String ln = System.getProperty("line.separator");
		System.out.println(
			"=====" + ln +
			" MD5" + ln +
			"=====" + ln + ln +
			"> java -jar md5.jar [opts] [filename] [filename2] ..." + ln + ln +
			"Implements the MD5 cryptographic hash function. If filename(s) are given," + ln +
			"the program computes the hash code of the given file(s). If no filename is" + ln +
			"given, the program reads input from the standard input stream. The program" + ln +
			"then prints the MD5 hash code to the standard output stream. ");
		System.out.println( ln +
				"**Warning: MD5 is no longer recommended and this implementation is not" + ln +
				"well tested, so don't rely on it for security.");
		System.out.println( ln +
				"Options:" + ln +
				"-------" + ln +
				"-? -h --help               display help." + ln + ln + 
				"-s [input string]          compute the hash of the given string" + ln + 
				"                           (in US-ASCII encoding)." + ln + ln + 
				"-v --verify [expected]     verifies that the computed hash equals" + ln +
				"                           an expected value." + ln);
	}


	private String getArgument(String[] args, int i) {
		if (i<args.length) {
			return args[i];
		}
		else {
			throw new OptionsException("Option " + args[i-1] + " requires an argument.");
		}
	}

	public void parse(String[] args) {
		int i = 0;
		for (; i<args.length; i++) {
			if (args[i].startsWith("-") || args[i].startsWith("?")) {
				if ("?".equals(args[i]) || "-?".equals(args[i]) || "-h".equals(args[i]) || "--help".equals(args[i])) {
					usage();
					done = true;
					return;
				}
				else if ("-v".equals(args[i]) || "--verify".equals(args[i])) {
					expected = getArgument(args, ++i);
				}
				else if ("-s".equals(args[i])) {
					input = getArgument(args, ++i);
				}
				else {
					throw new OptionsException("Unrecognized option \"" + args[i] + "\".");
				}
			}
			else {
				break;
			}
		}
		int j = 0;
		filenames = new String[args.length - i];
		for (;i<args.length; i++, j++) {
			filenames[j] = args[i];
		}
	}

	public boolean verify() {
		return expected != null;
	}

	public boolean stringInput() {
		return input != null;
	}
	
	public boolean hasFilenames() {
		return filenames!=null && filenames.length > 0;
	}

	public boolean done() {
		return done;
	}
	
	public String[] getFilenames() {
		return filenames;
	}

	public String getExpected() {
		return expected;
	}

	public String getInput() {
		return input;
	}
	
}

class OptionsException extends RuntimeException {
	public OptionsException(String msg) {
		super(msg);
	}
}
