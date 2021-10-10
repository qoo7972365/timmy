/*    */ package java.lang.annotation;
/*    */ 
/*    */ import java.lang.annotation.AnnotationTypeMismatchException;
/*    */ import java.lang.reflect.Method;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotationTypeMismatchException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 8125925355765570191L;
/*    */   private final Method element;
/*    */   private final String foundType;
/*    */   
/*    */   public AnnotationTypeMismatchException(Method paramMethod, String paramString) {
/* 66 */     super("Incorrectly typed data found for annotation element " + paramMethod + " (Found data of type " + paramString + ")");
/*    */     
/* 68 */     this.element = paramMethod;
/* 69 */     this.foundType = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Method element() {
/* 78 */     return this.element;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String foundType() {
/* 89 */     return this.foundType;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/annotation/AnnotationTypeMismatchException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */