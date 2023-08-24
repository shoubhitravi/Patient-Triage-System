package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      //TODO: Implement this method.
      numElements = 0;
      heap = (T[]) new Object[INIT_SIZE];
      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
  }

  private void swapIndices(int index1, int index2){
    T tempValue = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = tempValue;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleUp(int index) {
      //TODO: Implement this method.
      //throw exception if index is invalid
      if(index < 0 || index >= numElements) throw new IndexOutOfBoundsException();

    // max heap case
      if(isMaxHeap){
        // loops until reaches root at worst case
        while(index > 0){
          int parIndex = (index - 1) / 2;
          T cur = heap[index];
          T par = heap[parIndex];
          // if current element maintains maxHeap invariant, then all elements do, so return
          if(comparator.compare(cur, par) <= 0){
            return;
          } 
          // swaps current with parent if current is greater than parent
          else {
            swapIndices(index, parIndex);
            index = parIndex;
          }
        }
      }
    // min heap case
      else {
        // loops until reaches root at worst case
        while(index > 0){
          int parIndex = (index - 1) / 2;
          T cur = heap[index];
          T par = heap[parIndex];
        //if current element maintains minHeap variant, then all elements do, so return
          if(comparator.compare(cur, par) >= 0){
            return;
          }
        // swaps current with parent if current is less than parent
          else {
            swapIndices(index, parIndex);
            index = parIndex;
          }
        }
      }
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleDown(int index) {
      //TODO: Implement this method.
      if(index < 0 || index >= numElements) throw new IndexOutOfBoundsException();

      // store left child index 
      int childIndex = 2 * index + 1;
      // store value of current node
      T value = heap[index];

      
      // max heap case
      if(isMaxHeap){
        while(childIndex < numElements){
          // find max value of current node and its children
          T max = value;
          int maxIndex = -1;
          for(int i = 0; i < 2 && i + childIndex < numElements; i++){
            if(comparator.compare(heap[i + childIndex], max) > 0){
              max = heap[i + childIndex];
              maxIndex = i + childIndex;
            }
          }

          // if max value is the current node, heap property is maintained
          if(comparator.compare(max, value) == 0){
            return;
          }

          // heap property isn't maintained, so swap current node and its greater child and continue bubbling down on the new child
          else {
            swapIndices(index, maxIndex);
            index = maxIndex;
            childIndex = 2 * index + 1;
          }
        }
      }
      // min heap case
      else {
        while(childIndex < numElements){
          // find min value of current node and its children
          T min = value;
          int minIndex = -1;
          for(int i = 0; i < 2 && i + childIndex < numElements; i++){
            if(comparator.compare(heap[i + childIndex], min) < 0){
              min = heap[i + childIndex];
              minIndex = i + childIndex;
            }
          }
          // min value is current node, heap property is maintained
          if(comparator.compare(min, value) == 0){
            return;
          }
          // minHeap property isn't maintained, so swap current node and its smaller child and continue bubbling down on new child
           else {
            swapIndices(index, minIndex);
            index = minIndex;
            childIndex = 2 * index + 1;
           }
        }
      }
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
    //TODO: Implement this method.
    if(numElements== 0){
      isEmpty = true;
    }

    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int getSize(){
    int size = -100;
    //TODO: Implement this method.
    if(isEmpty()){
      size = 0;
    } else {
      size = numElements;
    }
    return size;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a variable, compareSign, 
   * based on the state of the boolean variable isMaxHeap. It then calls the compare method from the 
   * comparator object and multiplies its output by compareSign.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * negative int otherwise (if isMaxHeap),
   * return negative int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * positive int otherwise (if ! isMinHeap).
   */
  public int compareElements(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
    T data = null;
    //TODO: Implement this method.
    if(isEmpty()){
      throw new QueueUnderflowException();
    } else {
      data = heap[0];
    }
    return data;
  }  

  
  private void expandCapacity(){
    T[] newHeap = (T[]) new Object[numElements * 2];
    for(int i = 0; i < numElements; i++){
      newHeap[i] = heap[i];
    }

    heap = newHeap;
  }
 
  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeueElement() throws QueueUnderflowException{
    T data = null;
    //TODO: Implement this method.
    if(isEmpty()) throw new QueueUnderflowException();

    if(numElements == 1){
      data = heap[0];
      heap[0] = null;
      numElements--;
      return data;
    }
    else {
      int curIndex = 0;
      return dequeueElementHelper(curIndex);
    } 
  }

  private T dequeueElementHelper(int curIndex){
    int rootIndex = 0;
    int lastIndex = numElements - 1;

    swapIndices(rootIndex, lastIndex);
    T result = heap[lastIndex];
    heap[lastIndex] = null;
    numElements--;

    bubbleDown(rootIndex);

    return result;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueueElement(T newElement) {
      //TODO: Implement this method.

      numElements++;
      if(numElements == heap.length){
        expandCapacity();
      }
      
      heap[numElements - 1] = newElement;
      bubbleUp(numElements - 1);
  }


}