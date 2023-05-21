

/** Represents a node. 
 *  A node has an int value,
 *  and a pointer to another node. */
public class Node {
   //x and y coordinates of a snake part.
   double x;  // data
   double y;
   Node next;  // pointer

   /** Constructs a node with the given value.
    *  The new node will point to the given
    *  next node. */
   public Node(double x,double y, Node next) {
      this.x = x;
      this.y = y;
      this.next = next;
   }
	    
   /** Constructs a node with the given value.
    *  The new node will point to null. */
   public Node(double x,double y) {
      this(x,y, null);
   }

}