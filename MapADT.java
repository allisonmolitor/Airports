import java.util.NoSuchElementException;


public interface MapADT<KeyType, ValueType> {


    public void put(KeyType key, ValueType value) throws IllegalArgumentException;


    public boolean containsKey(KeyType key);

    
    public ValueType get(KeyType key) throws NoSuchElementException;

    
    public ValueType remove(KeyType key) throws NoSuchElementException;

  
    public void clear();


    public int getSize();


    public int getCapacity();

}
