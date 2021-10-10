/*    */ package javax.lang.model.element;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public enum Modifier
/*    */ {
/* 52 */   PUBLIC,
/* 53 */   PROTECTED,
/* 54 */   PRIVATE,
/* 55 */   ABSTRACT,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   DEFAULT,
/* 61 */   STATIC,
/* 62 */   FINAL,
/* 63 */   TRANSIENT,
/* 64 */   VOLATILE,
/* 65 */   SYNCHRONIZED,
/* 66 */   NATIVE,
/* 67 */   STRICTFP;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 73 */     return name().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/lang/model/element/Modifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */