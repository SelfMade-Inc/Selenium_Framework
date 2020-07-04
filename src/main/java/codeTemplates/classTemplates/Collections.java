package codeTemplates.classTemplates;

import java.util.*;

public class Collections {

    /*HashSet is an implementation of Set in which the elements are not sorted or ordered.
    It is faster than a TreeSet.
    The HashSet is an implementation of a Set.*/
    public void HashSets() {

        HashSet<String> strHashSet = new HashSet<String> ();
        strHashSet.add ("First entry in HashSet");

        //Print entire HashSet
        System.out.println (strHashSet);
    }

    /*Set is a generic set of values with no duplicate elements.
    A TreeSet is a set where the elements are sorted
    Set is a parent interface of all set classes like TreeSet, HashSet, etc.*/
    public void Sets() {

        //'Set' is abstract and needs to be indirectly instantiated
        Set<String> strSet = new HashSet<String> ();
        strSet.add ("First entry in Set");

        //Method to print set contents using for-each
        for (String setValue : strSet) {
            System.out.println (setValue);
        }
        //Method to print set contents using do-while
        Iterator itr_do_while = strSet.iterator ();
        if (itr_do_while.hasNext ()) {
            do {
                System.out.println (itr_do_while.next ());
            } while (itr_do_while.hasNext ());
        }

        //Method to print set contents using do-while
        Iterator itr_while = strSet.iterator ();
        while (itr_while.hasNext ()) {
            System.out.println (itr_while.next ());
        }
    }

    //Using HashMaps when you want to be secure about the data exposure
    //HashMaps are saved in <key><key_refrences_to_data> format
    public void HashMaps() {

        //'Map' is abstract and needs to be indirectly instantiated using HashMap
        //Map can contain Lists and other Maps as a parameter
        Map<String, List<String>> strMap = new HashMap<String, List<String>> ();

        //Implementing a List to be added to the Map
        List<String> list_Emails = new ArrayList<String> ();
        list_Emails.add ("email.email1@email.com");
        list_Emails.add ("email.email2@email.com");
        list_Emails.add ("email.email3@email.com");
        list_Emails.add ("email.email4@email.com");
        list_Emails.add ("email.email5@email.com");

        //Adding the List to Map
        strMap.put ("Emails", list_Emails);

        //Print all Map contents
        System.out.println (strMap);

        //Print key > value representation from Map
        Set<String> keys_HashMap = strMap.keySet ();
        for (String key : keys_HashMap) {
            System.out.println ("Key from the HashMap >> " + key + " represents value " + strMap.get (key));
        }
    }
}
