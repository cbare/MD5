=====
 MD5
=====

This is an implementation of the MD5 cryptographic hash algorithm described
in rfc1321. I wrote this implementation in honor of a cryptography course I
took in Winter of 2006.

You can run it either of these ways:

> java -jar md5.jar [opts] [filename] [filename2] ...

> java cbare.md5.MD5 [opts] [filename] [filename2] ...


Implements the MD5 cryptographic hash function. If filename(s) are given,
the program computes the hash code of the given file(s). If no filename is
given, the program reads input from the standard input stream. The program
then prints the MD5 hash code to the standard output stream. 

**Warning: MD5 is no longer recommended and this implementation is not
well tested, so don't rely on it for security.

Options:
-------
-? -h --help               display help.

-s [input string]          compute the hash of the given string
                           (in US-ASCII encoding).

-v --verify [expected]     verifies that the computed hash equals
                           an expected value.

