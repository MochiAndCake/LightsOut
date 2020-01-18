/*
  Faye Lin
  Ann Soong
*/

public class QueueImplementation<E> implements Queue<E> {

 private static class Elem<E> {
   private E value;
   private Elem<E> next;

   private Elem(E v, Elem<E> n){
     value = v;
     next = n;
   }
 }

 private Elem<E> front, rear;

 @SuppressWarnings("unchecked")

 //Constructor.
 public QueueImplementation(){
   front = null;
   rear = null;
 }

 @SuppressWarnings("unchecked")

 //Enqueues element of type E
 public void enqueue(E value){
   if(value == null){
     throw new NullPointerException("The value given was null.");
   }

   if(isEmpty()){
     front = rear = new Elem<E>(value, null);
   }
   else{
     rear.next = new Elem<E>(value, null);
     rear = rear.next;
   }
 }

 @SuppressWarnings("unchecked")
 //Dequeues an element of type E
 public E dequeue(){
   if(isEmpty()){
     throw new IllegalStateException("Cannot dequeue when queue is empty.");
   }
   else{
     E saved = front.value;

     if(front.next == null){
       front = null;
       rear = null;
     }
     else{
       Elem<E> hold = front;
       front = front.next;
       hold.next = null;
       hold = null;
     }

     return saved;
   }
 }

 //Returns the first element of type E without removing it from the queue.
 public E peek(){
   if(isEmpty()){
     throw new IllegalStateException("Cannot peek when queue is empty.");
   }
   return front.value;
 }

 public boolean isEmpty(){
   return front == null && rear == null;
 }
}
