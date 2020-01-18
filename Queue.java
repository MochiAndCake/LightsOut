/*
  Faye Lin
  Ann Soong
*/

public interface Queue<E> {
	public abstract void enqueue(E value); // Adds an element to the end of the queue
	public abstract E dequeue(); // removes the first element in the queue and returns its value
	public abstract boolean isEmpty(); // checks to see if the queue is empty
}
