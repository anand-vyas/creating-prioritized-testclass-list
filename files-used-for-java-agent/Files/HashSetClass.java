import java.util.*;
import java.io.*;
import java.nio.file.Paths;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HashSetClass {

   public static HashSet<String> hs = new HashSet<String>();
   public static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
   public static Map<String, HashSet<String>> testsWithCoverage = new HashMap<String, HashSet<String>>();
   public static Map<String, HashSet<String>> testClassWithCoverage = new HashMap<String, HashSet<String>>();
   public static Map<String, HashSet<String>> globalCopyOfOrderedMap = new LinkedHashMap<String, HashSet<String>>();
   public static Map<String, HashSet<String>> globalCopyOfOrderedMap_withOnlyClasses = new LinkedHashMap<String, HashSet<String>>();

   public static void addLineToHashSet(String line_coverage){
      hs.add(line_coverage);
   }

   public static void writeHashSetToFile(){

      try{
         StringBuilder sb = new StringBuilder();
   	   Iterator it = hs.iterator(); 
      	
         while(it.hasNext()) {
      	    sb.append(it.next()+"\n");
      	}	
         
         BufferedWriter out = new BufferedWriter(new FileWriter(cwd + "/stmt-cov-2.txt", true));
         out.append(sb);
   	   out.close();
         
         
         hs.clear();

      }

      catch(IOException e){

         System.out.println("Problem in writing HashSet to file in HashSetClass.java file");
         
      }


   }

   public static void writeToHashMap(String key, String className){
      
    HashSet<String> hs_new = new HashSet<String>();
    hs_new.addAll(hs);
    testsWithCoverage.put(key, hs_new); 
    
      if(!testClassWithCoverage.containsKey(className)){    
      testClassWithCoverage.put(className, hs_new);
      }
      else{
      HashSet<String> temp_hs = new HashSet<String>();  
      temp_hs = testClassWithCoverage.get(className);
      
      // Iterator<String> it = hs_new.iterator();  
      //   while(it.hasNext()){
      //     if(!temp_hs.contains(it.next())){
      //       temp_hs.add(it.next());
      //   }
      //   }

      for(String s: hs_new){
        if(!temp_hs.contains(s)){
          temp_hs.add(s);
        }

      }

      testClassWithCoverage.put(className, temp_hs);

      }
    

   }


   public static void printTotalStrategy(){
    //orderedMap will contain the tests with coverage info in decreasing order of line coverage size. 
    //all we have to do it print it in the same order to a file for total strategy. 

      Map<String, HashSet<String>> orderedMap = customSortFunctionForHashMap(testsWithCoverage); 
      globalCopyOfOrderedMap = orderedMap;

      BufferedWriter buffered_writer = null;
      FileWriter file_writer = null;
      StringBuilder mySB = new StringBuilder();
            
        try{
            String testNameAndSize = new String();
                
            for (Map.Entry<String, HashSet<String>> entry : orderedMap.entrySet()) {
              testNameAndSize = entry.getKey() + ":" + entry.getValue().size() + "\n";
              mySB.append(testNameAndSize);
            }

            file_writer = new FileWriter("total-strategy.txt", true);
            buffered_writer = new BufferedWriter(file_writer);
            buffered_writer.write(mySB.toString());
            buffered_writer.newLine();

        }

        catch(IOException e){
            System.out.println("Error in JUnitExecutionListener - testRunFinished");
        }

        finally {
          try {
            if (buffered_writer != null)
              buffered_writer.close();
            if (file_writer != null)
              file_writer.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }    
   }
   
   public static List<String> returnClassList_Total(){
    Map<String, HashSet<String>> orderedMap_withOnlyClasses = customSortFunctionForHashMap(testClassWithCoverage); 
    globalCopyOfOrderedMap_withOnlyClasses = orderedMap_withOnlyClasses;
    ArrayList<String> testClassList = new ArrayList<String>();

    for(Map.Entry<String, HashSet<String>> entry : orderedMap_withOnlyClasses.entrySet()) {
        if (!testClassList.contains(entry.getKey())){
            testClassList.add(entry.getKey());
        }
        
      }
    return testClassList;  
   }


   //Input: unsorted hash map containing the unit test name and the lines covered by it. 
   //Output: sorted hash map containing the same data but sorted in decreasing order of the number of lines covered by each unit test

   //A HashMap does not guarantee ordering. To sort the contents of a hashmap by value we need to write a custom comparator as below. 
   //This comparator function can sort the elements of the map but it needs to store it in a new data structure called linked hash map
   //A hash map cannot guarantee any ordering. Hence, the ordering cannot persist. 

   public static Map<String, HashSet<String>> customSortFunctionForHashMap(Map<String, HashSet<String>> unsorted_Map){

      //storing the entries of the hash map in a linked list. As the only way to sort a hash map by 'value' is by using the collections.sort function
      //which can only be used on a list. 

      List<Entry<String, HashSet<String>>> list = new LinkedList<Entry<String, HashSet<String>>>(unsorted_Map.entrySet());

      // Now, we are using the Collections.sort method. Say the list had integer values. 
      // Then, this function would have been able to sort the list by values. 
      // But, here is consists of map entries in key, value pairs where we want to sort the items by the size of the hash sets. 
      // this requires a custom comparator. 

      //The values returned are -1,0 and 1. Collections.sort() sorts the list in ascending order. 

      //As a result, the tests are sorted in decreasing order of their line coverage - as per our implementation below. 

      Collections.sort(list, new Comparator<Entry<String, HashSet<String>>>()
      {
          public int compare(Entry<String, HashSet<String>> entryX,
                  Entry<String, HashSet<String>> entryY)
          {
             if(entryX.getValue().size() == entryY.getValue().size())
               return 0;
             
             if(entryX.getValue().size() > entryY.getValue().size())
               return -1;
             
             else 
               
               return 1;
             
          }
      });

      // HashMaps do not guarantee order at any point of time. For maintaining order we need to use 
      // a linked hash map. 

      Map<String, HashSet<String>> sortedLinkedHashMap = new LinkedHashMap<String, HashSet<String>>();
      for (Entry<String, HashSet<String>> entry : list)
      {
          sortedLinkedHashMap.put(entry.getKey(), entry.getValue());
      }

      return sortedLinkedHashMap;
   }


   /*
    Additional strategy implementation. 
   */
   public static void printAdditionalStrategy(){
      
      while(globalCopyOfOrderedMap.size()>0){
        String currentTopTestName = new String();
        HashSet<String> lineCoverage = new HashSet<String>();

        //We just want to fetch the top test name and its line coverage information. 
        //The top test will be containing the current maximum number of lines in the hash map. 
        for (Entry<String, HashSet<String>> entry : globalCopyOfOrderedMap.entrySet()){
          currentTopTestName = entry.getKey();
          lineCoverage = entry.getValue();
          break;
        }

        //After we have this info, we print out the test name and the number of lines it covers. 
        BufferedWriter buffered_writer = null;
        FileWriter file_writer = null;
        try{
          file_writer = new FileWriter("additional-strategy.txt", true);
          buffered_writer = new BufferedWriter(file_writer);
          buffered_writer.write(currentTopTestName + ": " + lineCoverage.size());
          buffered_writer.newLine();
        }

        catch(IOException e){
          e.printStackTrace();
        }

        finally {
          
          try {
            if (buffered_writer != null)
              buffered_writer.close();
            if (file_writer != null)
              file_writer.close();
          } 
          
          catch (IOException e) {
          e.printStackTrace();
          }
        }

        //As this test information has been printed to the file, we remove it from our sorted linked hash map. 
        globalCopyOfOrderedMap.remove(currentTopTestName);

        //We need to re-prioritize the tests. 
        //The test which executes the maximum number of yet unexecuted lines, should be the next highest priority test. 

        //The lines covered by the top priority test we just removed after its execution. 
        Iterator<String> it = lineCoverage.iterator(); 

        //consider each line one at a time 
        while(it.hasNext()){

          String lineAlreadyExecuted = it.next();


          //Consider all the current tests in the linked hash map, one at a time, and remove this line if its present 
          //in its current line coverage info. 

          for(Entry<String, HashSet<String>> entry : globalCopyOfOrderedMap.entrySet()){
            HashSet<String> current_lineCoverage = entry.getValue();
            if (current_lineCoverage.contains(lineAlreadyExecuted)){
              current_lineCoverage.remove(lineAlreadyExecuted);
            }
            entry.setValue(current_lineCoverage);

          }

        } //all tests have been updated to only containing the unexecuted lines. 


        //sort the linked hash map according to updated data to give a new prioritized list
        //based on the highest number of line coverage. 

        globalCopyOfOrderedMap = customSortFunctionForHashMap(globalCopyOfOrderedMap);

      }

   }


   public static List<String> returnClassList_Additive(){
      ArrayList<String> testClassList = new ArrayList<String>();
      
      while(globalCopyOfOrderedMap_withOnlyClasses.size()>0){
        String currentTopClassName = new String();
        HashSet<String> lineCoverage = new HashSet<String>();

        //We just want to fetch the top test name and its line coverage information. 
        //The top test will be containing the current maximum number of lines in the hash map. 
        for (Entry<String, HashSet<String>> entry : globalCopyOfOrderedMap_withOnlyClasses.entrySet()){
          currentTopClassName = entry.getKey();
          lineCoverage = entry.getValue();
          break;
        }

        // for(Map.Entry<String, HashSet<String>> entry : globalCopyOfOrderedMap_withOnlyClasses.entrySet()) {
        //     if (!testClassList.contains(entry.getKey())){
        //         testClassList.add(entry.getKey()); //classname added to the list
        //     }
            
        // }

        if (!testClassList.contains(currentTopClassName)){
                testClassList.add(currentTopClassName); //classname added to the list
            }

        //As this test information has been printed to the file, we remove it from our sorted linked hash map. 
        globalCopyOfOrderedMap_withOnlyClasses.remove(currentTopClassName);

        //We need to re-prioritize the tests. 
        //The test which executes the maximum number of yet unexecuted lines, should be the next highest priority test. 

        //The lines covered by the top priority test we just removed after its execution. 
        Iterator<String> it = lineCoverage.iterator(); 

        //consider each line one at a time 
        while(it.hasNext()){

          String lineAlreadyExecuted = it.next();


          //Consider all the current tests in the linked hash map, one at a time, and remove this line if its present 
          //in its current line coverage info. 

          for(Entry<String, HashSet<String>> entry : globalCopyOfOrderedMap_withOnlyClasses.entrySet()){
            HashSet<String> current_lineCoverage = entry.getValue();
            if (current_lineCoverage.contains(lineAlreadyExecuted)){
              current_lineCoverage.remove(lineAlreadyExecuted);
            }
            entry.setValue(current_lineCoverage);

          }

        } //all tests have been updated to only containing the unexecuted lines. 


        //sort the linked hash map according to updated data to give a new prioritized list
        //based on the highest number of line coverage. 

        globalCopyOfOrderedMap_withOnlyClasses = customSortFunctionForHashMap(globalCopyOfOrderedMap_withOnlyClasses);

      }

      return testClassList;

   }

}
