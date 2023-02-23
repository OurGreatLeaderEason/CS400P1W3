// --== CS400 Project One File Header ==--
// Name: Eason Xiao
// CSL Username: eason
// Email: xiao227@wisc.edu
// Lecture #: Heimrl 4
// Notes to Grader: GLHF
import java.util.NoSuchElementException;

/**
 * Implements a Hashmap
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{

    protected Object[] array;
    private int size;
    private double load;

    /**
     * Class that combines the key and value into one object
     */
    protected class HashObject<KeyType, ValueType>{

        private KeyType key;
        private ValueType value;

        public HashObject(KeyType key, ValueType value){
            this.key=key;
            this.value=value;
        }

        public Object getKey(){
            return this.key;
        }

        public Object getValue(){
            return this.value;
        }

        public void setKey(KeyType newKey){
            this.key=newKey;
        }

        public void setValue(ValueType newValue){
            this.value=newValue;
        }

        @Override
        public String toString(){
            return key+", "+value;
        }
    }

    /**
     * Placeholder for deleted values
     */
    protected class Delete{
        @Override
        public String toString(){
            return "Delete";
        }
    }

    /**
     * Creates a new hashtable with given capacity
     * @param capacity size of the hasharray
     */
    public HashtableMap(int capacity){
        this.array=new Object[capacity];
        this.size=capacity;
        this.load=0;
    }

    /**
     * No argument constructor, assume size 8
     */
    public HashtableMap(){
        this.array=new Object[100000000];
        this.size=8;
        this.load=0;
    }


    /**
     * Inserts values into the hasharray, updates load 
     * @param key the key of the value
     * @param value the value itself
     */
    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if(containsKey(key)){
            throw new IllegalArgumentException("Can't have duplicate keys!");
        }
        int index=Math.abs(key.hashCode()%(int)size);
        if(this.array[index]==null || !(this.array[index] instanceof HashtableMap.HashObject)){
            array[index]=new HashObject(key, value);
            this.load++;
        }
        else{
            while(array[index%(int)this.size]!=null && this.array[index] instanceof HashtableMap.HashObject){
                index++;
            }
            array[(index)%(int)this.size]=new HashObject(key, value);
            this.load++;
        }
        this.expand();
    }

    /**
     * Checks if the value associated with key is in the hasharray
     * @param key the key we want to check
     */
    @Override
    public boolean containsKey(KeyType key) {
        for(int i=0; i<size; i++){
            if(this.array[i]!=null && this.array[i] instanceof HashtableMap.HashObject && ((HashtableMap.HashObject) this.array[i]).getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * returns the value of the key
     * @param key the key we want to check
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        if(!this.containsKey(key)){
            throw new NoSuchElementException("Key doesn't exist!");
        }
        for(int i=0; i<size; i++){
            if(this.array[i]!=null && this.array[i] instanceof HashtableMap.HashObject && ((HashtableMap.HashObject) this.array[i]).getKey().equals(key)){
                return (ValueType) ((HashtableMap.HashObject) this.array[i]).getValue();
            }
        }
        return null;
      
    }

    /**
     * removes an element from the hasharray
     * @param key the key of the value that we want to remove
     */
    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if(!this.containsKey(key)){
            throw new NoSuchElementException("Key doesn't exist!");
        }
        for(int i=0; i<size; i++){
            if(this.array[i]!=null && this.array[i] instanceof HashtableMap.HashObject && ((HashtableMap.HashObject) this.array[i]).getKey().equals(key)){
                this.array[i]=new Delete();
                this.load--;
            }
        }
        return null;
    }

    /**
     * resets the hasharray, deletes all elements
     */
    @Override
    public void clear() {
        this.array=new Object[(int)size];
        this.load=0;
    }

    /**
     * returns the number of values stored
     * @return number of values stored
     */
    @Override
    public int getSize() {
        return (int) this.load;
    }

    /**
     * returns the size of the hasharray
     * @return the size of the hasharray
     */
    @Override
    public int getCapacity() {
        return (int) this.size;
    }

    /**
     * private helper method that doubles the size of the hasharray when load factor is >=0.7
     */
    private void expand(){
        double loadFactor=this.load/(double)this.size;
        if(loadFactor>=0.7){
            Object[] duplicateArray=new Object[(int)size];
            for(int i=0; i<this.size; i++){
                duplicateArray[i]=this.array[i];
            }
            this.array=new Object[(int)(size*2)];
            int oldSize=(int) this.size;
            this.size*=2;
            for(int i=0; i<oldSize; i++){
                if(duplicateArray[i]!=null && (duplicateArray[i] instanceof HashtableMap.HashObject)){
                    this.putWithoutExpand((KeyType)((HashtableMap.HashObject) duplicateArray[i]).getKey(), (ValueType)((HashtableMap.HashObject) duplicateArray[i]).getValue());
                }
            }
        }
    }

    /**
     * returns a string representation of the hasharray, helps with debugging
     * @return a string representation of the hasharray
     */
    @Override
    public String toString(){
        String ret="";
        for(int i=0; i<this.size; i++){
            if(this.array[i]==null){
                ret+="\nnull";
            }
            else{
                ret+="\n"+this.array[i].toString();
            }
        }
        return ret;
    }

    /**
     * private helper method that inserts elements into a hasharray, only used when copying elements from one array to the other
     * @param key key of the value
     * @param value the value itself
     * @throws IllegalArgumentException thrown when attemtping to put duplicate keys into the hasharray
     */
    private void putWithoutExpand(KeyType key, ValueType value) throws IllegalArgumentException {
        if(containsKey(key)){
            throw new IllegalAccessError("Can't have duplicate keys!");
        }
        int index=Math.abs(key.hashCode()%(int)size);
        if(this.array[index]==null || !(this.array[index] instanceof HashtableMap.HashObject)){
            array[index]=new HashObject(key, value);
        }
        else{
            while(array[index%(int)this.size]!=null && this.array[index] instanceof HashtableMap.HashObject){
                index++;
            }
            array[index%(int)this.size]=new HashObject(key, value);
        }
    }
}