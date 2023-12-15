//This is Heap.java


import java.util.ArrayList;

public class Heap<HeapMemberGeneric extends HeapMember> {
    private ArrayList<HeapMemberGeneric> minHeap;
    private int size;

    public Heap() {
        minHeap = new ArrayList<HeapMemberGeneric>();
    }

    private void heapifyUp(int i)
    {
        int parent;
        if (i == 0) {
            parent = 0;
        }else parent = (i-1)/2;

        if (minHeap.get(parent).getValue() > minHeap.get(i).getValue() && i > 0)
        {
            HeapMemberGeneric swapedHeapMember = minHeap.get(i);
            swapedHeapMember.setIndex(parent);
            minHeap.get(parent).setIndex(i);
            minHeap.set(i,minHeap.get(parent));
            minHeap.set(parent,swapedHeapMember);

            heapifyUp(parent);
        }
    }

    private void heapifyDown(int i)
    {
        int minimum = i;
        int left = 2*i+1;
        int right = 2*i+2;
        int size = minHeap.size();

        if (left < size && minHeap.get(left).getValue() < minHeap.get(minimum).getValue())
            minimum = left;

        if (right < size && minHeap.get(right).getValue() < minHeap.get(minimum).getValue())
            minimum = right;

        if (minimum != i) {
            HeapMemberGeneric swapedHeapMember = minHeap.get(i);
            swapedHeapMember.setIndex(minimum);
            minHeap.get(minimum).setIndex(i);
            minHeap.set(i,minHeap.get(minimum));
            minHeap.set(minimum,swapedHeapMember);

            heapifyDown(minimum);
        }
    }

    /**
     * buildHeap(ArrayList<HeapMember> heap_members)
     * Given an ArrayList of Cities, build a min-heap keyed on each HeapMember's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param heap_members
     */
    public void buildHeap(ArrayList<HeapMemberGeneric> heap_members) {

        for(int i = 0; i < heap_members.size(); i++){
            heap_members.get(i).setIndex(i);
            minHeap.add(heap_members.get(i));
        }
        int firstIndex = (heap_members.size()/2)-1;

        for(int k = firstIndex; k > -1; k--){
            heapifyDown(k);
        }
    }

    /**
     * insertNode(HeapMemberGeneric in)
     * Insert a HeapMemberGeneric into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the HeapMemberGeneric to insert.
     */
    public void insertNode(HeapMemberGeneric in) {
        minHeap.add(in);
        heapifyUp(minHeap.size()-1);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public HeapMemberGeneric findMin() {
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public HeapMemberGeneric extractMin() {
        HeapMemberGeneric minHeapMember = findMin();
        delete(minHeap.get(0));
        return minHeapMember;
    }

    /**
     * delete(HeapMemberGeneric gen)
     * Deletes an element in the min-heap given a member to delete.
     * Sets index to -1 to indicate that the HeapMemberGeneric is no longer in the heap.
     * Time Complexity - O(log(n))
     *
     * @param gen - the member to be deleted in the min-heap.
     */
    public void delete(HeapMemberGeneric gen) {

        int index = gen.getIndex();
        HeapMemberGeneric heapMemberTemp = minHeap.get(minHeap.size()-1);
        HeapMemberGeneric deletedHeapMember = minHeap.get(index);
        deletedHeapMember.setIndex(-1);

        minHeap.remove(minHeap.size()-1);
        if(index == minHeap.size()) return;
        heapMemberTemp.setIndex(index);

        if(minHeap.size() == 0) return;

        minHeap.set(index, heapMemberTemp);

        if(heapMemberTemp.getValue() > deletedHeapMember.getValue())
            heapifyDown(index);
        else
            heapifyUp(index);
    }

    /**
     * changeKey(HeapMemberGeneric r, int newDist)
     * Changes minDist of HeapMemberGeneric s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the HeapMemberGeneric in the heap that needs to be updated.
     * @param newKey - the new cost of HeapMemberGeneric r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(HeapMemberGeneric r, int newKey) {
        int oldDistance = r.getValue();
        int i = r.getIndex();
        r.setValue(newKey);

        if(r.getIndex() == -1) return;

        if(oldDistance < newKey){
            heapifyDown(i);
        }else{
            heapifyUp(i);
        }
    }

    /*
     *  contains(HeapMemberGeneric m)
     *  Checks if the HeapMemberGeneric m is in the heap.
     *  HeapMemberGeneric m, must have been added to the heap previously.
     *  Time Complexity - O(1)
     *
     *  @param m - the HeapMemberGeneric to check if it is in the heap.
     *
     */

    public boolean contains(HeapMemberGeneric m) {
        if(m.getIndex() == -1) return false;
        return true;
    }

    public int getSize() {
        return minHeap.size();
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getId() + " ";
        }
        return output;
    }


    /* Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */

    public ArrayList<HeapMemberGeneric> toArrayList() {
        return minHeap;
    }

    

}
