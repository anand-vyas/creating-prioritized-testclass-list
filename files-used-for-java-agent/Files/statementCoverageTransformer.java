import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;

public class statementCoverageTransformer implements ClassFileTransformer {


@Override 
  public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) 
  throws IllegalClassFormatException {
      
      byte[] retVal = null;
 
      if (className.contains("org/joda/time")) {
        
        // ClassReader cr = new ClassReader(classfileBuffer);
	    // ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
	    // ClassVisitor cv = new MethodTransformVisitor(cw, className);
	    // cr.accept(cv, 0);

	    ClassReader cr = new ClassReader(classfileBuffer);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassTransformVisitor ca = new ClassTransformVisitor(cw, className);
		cr.accept(ca, 0);
	    retVal = cw.toByteArray();
      
      }

      return retVal;
  }

}