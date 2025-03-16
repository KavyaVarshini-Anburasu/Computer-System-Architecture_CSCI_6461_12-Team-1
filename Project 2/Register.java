package csci6461;

import java.util.BitSet;


public class Register extends BitSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -972511712136575293L;
	
	private int size;
	
	public Register(int size) {
		super(size);
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

}
