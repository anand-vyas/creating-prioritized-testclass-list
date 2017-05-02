import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;


public class JUnitExecutionListener extends RunListener {

    static String cwd = Paths.get(".").toAbsolutePath().normalize().toString();

    public void testRunStarted(Description description) throws Exception {
        System.out.println("Number of tests to execute: " + description.testCount());
    }

    public void testRunFinished(Result result) throws Exception {
        
        HashSetClass.printTotalStrategy();
        List<String> testClassList_Total = HashSetClass.returnClassList_Total();

        HashSetClass.printAdditionalStrategy();
        List<String> testClassList_Additive = HashSetClass.returnClassList_Additive();
        
        BufferedWriter buffered_writer = null;
        FileWriter file_writer = null;

        try{
            file_writer = new FileWriter("prioritized-class-list.txt", true);
            buffered_writer = new BufferedWriter(file_writer);

            for(String s:testClassList_Total){
                buffered_writer.write(s);
                buffered_writer.newLine();
            }
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


        BufferedWriter buffered_writer_2 = null;
        FileWriter file_writer_2 = null;

        try{
            file_writer_2 = new FileWriter("additive-class-list.txt", true);
            buffered_writer_2 = new BufferedWriter(file_writer_2);

            for(String s:testClassList_Additive){
                buffered_writer_2.write(s);
                buffered_writer_2.newLine();
            }
        }

        catch(IOException e){
          e.printStackTrace();
        }

        finally {
          
          try {
            if (buffered_writer_2 != null)
              buffered_writer_2.close();
            if (file_writer_2 != null)
              file_writer_2.close();
          } 
          
          catch (IOException e) {
          e.printStackTrace();
          }
        }
        
        System.out.println("Number of tests executed: " + result.getRunCount());
        //Sorting the HashMap by the number of lines covered in each test. 
    }

    public void testStarted(Description description) throws Exception {
            
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(cwd + "/stmt-cov-2.txt", true));
            out.write("[TEST] " + description.getClassName() + ": " + description.getMethodName() + "\n");
            out.close();
        }

        catch(IOException e){
            System.out.println("Error in JUnitExecutionListener - testStarted");
        }
        

    }

    public void testFinished(Description description) throws Exception {
        //System.out.println("Finished: " + description.getMethodName()); 
        String key =  description.getClassName() + ": " + description.getMethodName();
        HashSetClass.writeToHashMap(key, description.getClassName() + ".class,");
        HashSetClass.writeHashSetToFile();      
    }

    public void testFailure(Failure failure) throws Exception {
        System.out.println("Failed: " + failure.getDescription().getMethodName());
    }

    public void testAssumptionFailure(Failure failure) {
        System.out.println("Failed: " + failure.getDescription().getMethodName());
    }

    public void testIgnored(Description description) throws Exception {
        System.out.println("Ignored: " + description.getMethodName());
    }
}