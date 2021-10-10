/*    */ package javax.lang.model.type;
/*    */ 
/*    */ import javax.lang.model.UnknownEntityException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownTypeException
/*    */   extends UnknownEntityException
/*    */ {
/*    */   private static final long serialVersionUID = 269L;
/*    */   private transient TypeMirror type;
/*    */   private transient Object parameter;
/*    */   
/*    */   public UnknownTypeException(TypeMirror paramTypeMirror, Object paramObject) {
/* 61 */     super("Unknown type: " + paramTypeMirror);
/* 62 */     this.type = paramTypeMirror;
/* 63 */     this.parameter = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeMirror getUnknownType() {
/* 74 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getArgument() {
/* 83 */     return this.parameter;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/type/UnknownTypeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */