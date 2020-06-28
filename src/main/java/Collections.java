import java.util.*;

public class Collections {

    //Using HashSets
    public void HashSets() {

        HashSet<String> strHashSet = new HashSet<String>();
        strHashSet.add("First entry in HashSet");

        //Print entire HashSet
        System.out.println(strHashSet);
    }

    //Using Sets
    public void Sets() {

        //'Set' is abstract and needs to be indirectly instantiated
        Set<String> strSet = new HashSet<String>();
        strSet.add("First entry in Set");

        //Method to print set contents using for-each
        for(String setValue : strSet){
            System.out.println(setValue);
        }
        //Method to print set contents using do-while
        Iterator itr_do_while = strSet.iterator();
        if (itr_do_while.hasNext()) {
            do {
                System.out.println(itr_do_while.next());
            } while (itr_do_while.hasNext());
        }

        //Method to print set contents using do-while
        Iterator itr_while = strSet.iterator();
        while(itr_while.hasNext()){
            System.out.println(itr_while.next());
        }
    }

    //Using HashMaps
    public void HashMaps(){

        //'Map' is abstract and needs to be indirectly instantiated
        Map<String,String> strMap = new HashMap<String,String>();
        strMap.put("This is object key","This is object value");

        //Print all Map contents
        System.out.println(strMap);

        //Print specific value from Map
        System.out.println(strMap.get("This is object key"));


    }

}
