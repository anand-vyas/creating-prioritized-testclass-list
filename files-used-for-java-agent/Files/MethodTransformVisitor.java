import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import java.util.*;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {

    String my_className;
	//String mName;
	int line; 
    

    

    public MethodTransformVisitor(final MethodVisitor mv, String name, String className) {
        super(ASM5, mv);
        //this.mName=name;
        this.my_className = className;
    }

    // // method coverage collection
    // @Override
    // public void visitCode(){
    // 	mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
    // 	mv.visitLdcInsn(mName+" executed");
    // 	mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    // 	super.visitCode();
    // }

    // statement coverage collection - edited to work correctly
    //visitLineNumber - used to track the lines/ statements executed
    //As a statement is visited, it means it is executed. We store this in the global variable line. 
    @Override
    public void visitLineNumber(int line, Label start) {
        this.line = line;
        mv.visitLdcInsn(my_className + ": " + line);
        mv.visitMethodInsn(INVOKESTATIC, "HashSetClass", "addLineToHashSet", "(Ljava/lang/String;)V", false);
        super.visitLineNumber(line, start);
    }

    // collect line coverage for each label 
    // @Override
    // public void visitLabel(Label label){
    //     mv.visitLdcInsn(my_className + ": " + line);
    //     mv.visitMethodInsn(INVOKESTATIC, "HashSetClass", "addLineToHashSet", "(Ljava/lang/String;)V", false);
    //     super.visitLabel(label);
    // }   

}