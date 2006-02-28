package cbare.md5;

import junit.framework.TestCase;

public class TestRotate extends TestCase {

    public void testRotate() {
        int k = 0x98765432;
        System.out.println(Integer.toBinaryString(k));
        
        for (int i=0; i<=32; i++) {
            System.out.println(String.format("%02d %32s", i, Integer.toBinaryString(Bits.leftRotate(k,i))));
        }            
    }
}
