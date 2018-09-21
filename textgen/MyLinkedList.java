package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		
		
		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// TODO: Implement this method
		//no element
		if (element == null) {
			throw new NullPointerException("remember last object can not store null pointer");//!!!remember to check the value
		}
		LLNode<E> newNode = new LLNode<E>(element);
		if (size == 0) {
			newNode.prev = head;//this will help the add and remove more easy.
			newNode.next = tail;
			head.next = newNode;
			tail.prev = newNode;//head and tail will point to this new node
			
			size +=1;
		}
		else {
			newNode.prev = tail.prev;
			newNode.next = tail;
			tail.prev.next = newNode;// make sure the tail.prev.next should point to next
			tail.prev = newNode;//need to check out the bi-direction.
			
			size += 1;
		}
		return true;
	}
	public LLNode<E> getNode(int idx, int lower, int upper){
		LLNode<E> p;
		if (idx < lower || idx > upper ) {
			throw new IndexOutOfBoundsException("get Node index out of bound");
			
		}
		if (idx < size / 2) {
			p = head.next;
			for (int i = 0; i < idx; i ++) {
				p = p.next;
			}
			
		}
		else {
			p = tail.prev;
			for (int i = 0; i < size - idx - 1; i ++) {
				p = p.prev;
			}
		}
		return p;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		LLNode<E> p = getNode(index, 0, size - 1);
		
		return p.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("remember not to add an empty element.");
		}
		LLNode<E> p = new LLNode<E>(element);
		if (index == size ) {
			this.add(element);
			return;
		}
		
		LLNode<E> originalNode = getNode(index, 0, size - 1);//!!! if we want to add at the end, then index will be out of bound.
		p.prev = originalNode.prev;
		p.next = originalNode;
		originalNode.prev.next = p;
		originalNode.prev = p;
	

		size += 1;
		
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		LLNode<E> p = getNode(index, 0, size - 1);
//		System.out.println("we remove idx: " + index + " value: " + p.data);
		p.next.prev= p.prev;
		p.prev.next = p.next; //if size = 1, then tail.prev = head, head.next = tail				
		size --;
		return p.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) {
			throw new NullPointerException("remember element should not be null");
		}
		LLNode<E> p = getNode(index, 0, size - 1);
		E oldVal = p.data;
		p.data = element;
		return oldVal;
	}   
	public String toString() {
		String a = "";
		LLNode<E> p = head.next;
		for (int i = 0; i < size; i ++) {
			a += p.data + " ";
			p = p.next;
		}
		return a;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
