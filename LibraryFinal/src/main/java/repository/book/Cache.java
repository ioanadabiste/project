package repository.book;

import java.util.List;

//
//generic classes=>
// cat mai versatila: sa poata fi fol si pt book si user samd
public class Cache<T> {
    public List<T> storage;

    public List<T> load(){
        return storage;
    }
    public void save(List<T> storage){
        this.storage = storage;
    }

    public boolean hasResult(){
        return storage!=null;
    }
    public void invalidateCache(){
        storage=null;
    }
}
