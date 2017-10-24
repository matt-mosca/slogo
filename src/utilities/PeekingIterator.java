package utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PeekingIterator<E> {

	private Iterator<E> it;
	private E peekedElement;
	
	public PeekingIterator(Iterator<E> it) {
		this.it = it;
	}
	
	public E peek() throws NoSuchElementException {
		if (peekedElement != null) {
			return peekedElement;
		}
		peekedElement = it.next();
		return peekedElement;
	}
	
	public E next() throws NoSuchElementException {
		if (peekedElement != null) {
			E nextVal = peekedElement;
			peekedElement = null;
			return nextVal;
		}
		return it.next();
	}
	
	public boolean hasNext() {
		return it.hasNext() || peekedElement != null;
	}
	
}
