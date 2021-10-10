/*    */ package javax.annotation.processing;
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
/*    */ public class Completions
/*    */ {
/*    */   private static class SimpleCompletion
/*    */     implements Completion
/*    */   {
/*    */     private String value;
/*    */     private String message;
/*    */     
/*    */     SimpleCompletion(String param1String1, String param1String2) {
/* 45 */       if (param1String1 == null || param1String2 == null)
/* 46 */         throw new NullPointerException("Null completion strings not accepted."); 
/* 47 */       this.value = param1String1;
/* 48 */       this.message = param1String2;
/*    */     }
/*    */     
/*    */     public String getValue() {
/* 52 */       return this.value;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getMessage() {
/* 57 */       return this.message;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 62 */       return "[\"" + this.value + "\", \"" + this.message + "\"]";
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Completion of(String paramString1, String paramString2) {
/* 75 */     return new SimpleCompletion(paramString1, paramString2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Completion of(String paramString) {
/* 85 */     return new SimpleCompletion(paramString, "");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/annotation/processing/Completions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */