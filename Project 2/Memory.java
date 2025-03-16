/**
 * 
 */
package csci6461;

import java.util.ArrayList;


/**
 * Represents the memory of the CISC simulator, contains
 * 2048 words, expandable to 4096 words.
 * Implemented using singleton design pattern.
 * 
 *
 *
 */
public class Memory {
	
	private static final Memory INSTANCE = new Memory();
	private static Word[] memory;
	private static Cache cache;
	
	//	private constructor to prevent initialization from outside the class
	private Memory() {
		memory = new Word[4096];
		for (int i = 0; i < 4096; i++) {
			memory[i] = new Word();
		}
//		Arrays.fill(memory, new Word());
		cache = new Cache();
	}
	public static Memory getInstance() {
		return INSTANCE;
	}
	
	public Word read(int address) {
		//return memory[address];
		return cache.read(address);
	}
	
	private class Cache {
		public ArrayList<Integer> address;
		public ArrayList<Word> content;
		public int length;
		public Cache() {
			address = new ArrayList<>();
			content = new ArrayList<>();
			length = 0;
		}
		public void add(int addr, Word cont) {
			if (length >= 16) {
				address.remove(0);
				content.remove(0);
				length--;
			}
			address.add(Integer.valueOf(addr));
			content.add(cont);
			length++;
			MainFrame.updateCacheUI(address, content);
		}
		public Word read(int addr) {
			for (int i = 0; i < length; i++) {
				if (address.get(i).intValue() == addr) {
					// HIT
					content.set(i, memory[addr]);
					return content.get(i);
				}
			}
			// MISS
			this.add(addr, memory[addr]);
			return memory[addr];
		}
		
	}
	
	public void write(Word inp, int address) {
		memory[address] = inp;
	}
	
	public void write(int inp, int address) {
		memory[address] = Util.int2Word(inp);
	}
}
