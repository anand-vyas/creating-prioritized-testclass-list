import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class ClassTransformVisitor extends ClassVisitor implements Opcodes {

	String my_class;

    public ClassTransformVisitor(final ClassVisitor cv, String className) {
        super(ASM5, cv);
        this.my_class = className;
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
            final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return mv == null ? null : new MethodTransformVisitor(mv, name, my_class);
    }
}


