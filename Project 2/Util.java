package csci6461;

import java.util.BitSet;


/**
 * General Purpose utility class to implement BitSet and word conversions
 * 
 *
 *
 */
public class Util {
	
		
	public static int bitSet2Int(BitSet bitSet) {
        int intValue = 0;
        for (int bit = 0; bit < bitSet.length(); bit++) {
            if (bitSet.get(bit)) {
                intValue |= (1 << bit);
            }
        }
        return intValue;
    }
	
	public static int bitSet2IntSigned(BitSet bitSet) {
        int intValue = 0;
        int maxValue = 1 << bitSet.length()-1;
        boolean sign = bitSet.get(15);
        bitSet.set(15, false);
        for (int bit = 0; bit < bitSet.length(); bit++) {
            if (bitSet.get(bit)) {
                intValue |= (1 << bit);
            }
        }
        if (sign) {
        	bitSet.set(15);
        	return intValue-maxValue;
        }
        return intValue;
    }
	
	public static float bitSet2Float(BitSet bitSet) {
		assert(bitSet.length() == 16);
        int mantissaRep = 0;
        double mantissa = 0.0;
        int exponent = 0;
        double result = 0.0f;
        int maxValue = (1 << 8) - 1;
        boolean sign = bitSet.get(15);
        bitSet.set(15, false);
        for (int bit = 0; bit < 8; bit++) {
            if (bitSet.get(bit)) {
                mantissaRep |= (1 << bit);
            }
        }
        for (int bit = 8; bit < 15; bit++) {
            if (bitSet.get(bit)) {
                exponent |= (1 << (bit - 8));
            }
        }
        exponent -= 63; // bias
        if (sign) {
        	bitSet.set(15);
        	mantissaRep -= maxValue;
        	mantissa = (-1 + mantissaRep * Math.pow(2, -8.0));
        } else {
        	mantissa = (1 + mantissaRep * Math.pow(2, -8.0));
        }
        
        result = mantissa * Math.pow(2, exponent);
        return (float)result;
    }
	
	public static BitSet int2BitSet(int value) {
		BitSet bits = new BitSet();
	    int index = 0;
	    while (value != 0) {
	      if (value % 2 != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	}
	
	public static BitSet int2BitSetSigned(int value) {
		BitSet bits = new BitSet();
		if (value < 0) {
			bits.set(15);
			//value = - 32768 - value; // 2's complement
		} 
	    int index = 0;
	    while (value != 0) {
	      if (value % 2 != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	}
	
	public static BitSet float2BitSet(float value) {
		BitSet bits = new BitSet();
		int intbits = Float.floatToIntBits(value);
		String binaryString = Integer.toBinaryString(intbits);
		// append leading 0's
		
		while (binaryString.length() < 32) {
			binaryString = "0" + binaryString;
		}
		// signed bit
		if (binaryString.charAt(0) == '1') bits.set(15);
		
		String exponentString = binaryString.substring(1, 9);
		
		while (exponentString.length() < 8) {
			exponentString = "0" + exponentString;
		}
		int exponent = Integer.parseInt(exponentString, 2) - 127 + 63;
		exponentString = Integer.toBinaryString(exponent);
		while (exponentString.length() < 7) {
			exponentString = "0" + exponentString;
		}
		
		String mantissaString = binaryString.substring(9, 17);
		
		for (int i = 1; i < 8; i++) {
            if (exponentString.charAt(i - 1) == '1') {
            	// 15 [14 13 12 11 10 9 8] 7 6 5 4 3 2 1 0
                bits.set(15 - i);
            }
        }
        for (int i = 8; i < 16; i++) {
        	// 15 14 13 12 11 10 9 8 [7 6 5 4 3 2 1 0]
            if (mantissaString.charAt(i - 8) == '1') {
            	bits.set(15 - i);
            }
        }
		return bits;
		
	}
	
	public static Word int2Word(int value) {
		Word w = new Word();
	    int index = 0;
	    while (value != 0) {
	      if (value % 2 != 0) {
	        w.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return w;
	}
	public static Word int2WordSigned(int value) {
		Word bits = new Word();
		if (value < 0) {
			bits.set(15);
			//value = - 32768 - value; // 2's complement
		} 
	    int index = 0;
	    while (value != 0) {
	      if (value % 2 != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	}
	public static void bitSetDeepCopy(BitSet source, int sourceBits,
			BitSet destination, int destinationBits) {
		if (sourceBits <= destinationBits) {
			destination.clear();
			for (int i = 0; i < sourceBits; i++) {
				destination.set(i, source.get(i));
			}
		} else {
			destination.clear();
			for (int i = 0; i < destinationBits; i++) {
				destination.set(i, source.get(i));
			}
		}
	}
}
