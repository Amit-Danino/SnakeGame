/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likewise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    public Node first;

    // The number of elements in this list
    public int size;
	
    /** Constructs an empty list. */
    public List() {
        // Starts with a dummy node
        first = new Node(0,0);
        size = 0;
    }
    public void insertLast(double x,double y){
        Node lastOne = new Node(x,y);
        Node curr = this.first;
        while (curr.next!=null){
            curr = curr.next;
        }
        curr.next = lastOne;
        size++;
    }

    public void insertFirst(double x, double y){
        Node firstInsert = new Node(x,y);
        firstInsert.next = this.first.next;
        this.first.next = firstInsert;
        size++;
    }

    public void removeFirst(){
        if (this.first.next==null){
            System.out.println("Nothing to remove, empty list");
            return;
        }
        this.first.next = this.first.next.next;
        size--;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
        return size;
    }


    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
        // If the list is empty, there is nothing to iterate
        if (size == 0) return null;
        // Gets the element in position index of this list
        Node current = first.next; // skips the dummy
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
        ListIterator newIterator = new ListIterator(current);
        return newIterator;
    }

    public ListIterator listIterator() {
        Node newNode = first.next;
        return new ListIterator(newNode);
    }
}