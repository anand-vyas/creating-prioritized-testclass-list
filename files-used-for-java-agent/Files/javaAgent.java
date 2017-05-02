import java.lang.instrument.Instrumentation;

public class javaAgent{

	public static void premain(String args, Instrumentation inst) throws Exception {
        inst.addTransformer(new statementCoverageTransformer());      
    }
}