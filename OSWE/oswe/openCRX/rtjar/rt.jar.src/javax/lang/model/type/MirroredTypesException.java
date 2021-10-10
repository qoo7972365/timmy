/*    */ package javax.lang.model.type;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public class MirroredTypesException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 269L;
/*    */   transient List<? extends TypeMirror> types;
/*    */   
/*    */   MirroredTypesException(String paramString, TypeMirror paramTypeMirror) {
/* 57 */     super(paramString);
/* 58 */     ArrayList<TypeMirror> arrayList = new ArrayList();
/* 59 */     arrayList.add(paramTypeMirror);
/* 60 */     this.types = Collections.unmodifiableList(arrayList);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MirroredTypesException(List<? extends TypeMirror> paramList) {
/* 69 */     super("Attempt to access Class objects for TypeMirrors " + (paramList = new ArrayList<>(paramList))
/*    */         
/* 71 */         .toString());
/* 72 */     this.types = Collections.unmodifiableList(paramList);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<? extends TypeMirror> getTypeMirrors() {
/* 83 */     return this.types;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 91 */     paramObjectInputStream.defaultReadObject();
/* 92 */     this.types = null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/MirroredTypesException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */