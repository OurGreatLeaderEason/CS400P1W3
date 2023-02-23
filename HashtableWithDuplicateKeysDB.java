import java.util.NoSuchElementException;

public class HashtableWithDuplicateKeysDB<KeyType, ValueType> implements HashtableWithDuplicateKeysInterface{


    public HashtableWithDuplicateKeysDB(int capacity){}
    public HashtableWithDuplicateKeysDB(){}

    @Override
    public void put(Object key, Object value) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object get(Object key) throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object remove(Object key) throws NoSuchElementException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCapacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void putOne(Object key, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeOne(Object key, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getNumberOfValues() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
