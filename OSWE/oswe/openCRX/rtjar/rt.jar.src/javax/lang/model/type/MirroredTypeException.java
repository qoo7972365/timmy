/*    */ package javax.lang.model.type;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MirroredTypeException
/*    */   extends MirroredTypesException
/*    */ {
/*    */   private static final long serialVersionUID = 269L;
/*    */   private transient TypeMirror type;
/*    */   
/*    */   public MirroredTypeException(TypeMirror paramTypeMirror) {
/* 56 */     super("Attempt to access Class object for TypeMirror " + paramTypeMirror.toString(), paramTypeMirror);
/* 57 */     this.type = paramTypeMirror;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeMirror getTypeMirror() {
/* 68 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 76 */     paramObjectInputStream.defaultReadObject();
/* 77 */     this.type = null;
/* 78 */     this.types = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/MirroredTypeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */