/*    */ package javax.swing.text;
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
/*    */ public interface Position
/*    */ {
/*    */   int getOffset();
/*    */   
/*    */   public static final class Bias
/*    */   {
/* 72 */     public static final Bias Forward = new Bias("Forward");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     public static final Bias Backward = new Bias("Backward");
/*    */     
/*    */     private String name;
/*    */ 
/*    */     
/*    */     public String toString() {
/* 84 */       return this.name;
/*    */     }
/*    */     
/*    */     private Bias(String param1String) {
/* 88 */       this.name = param1String;
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/Position.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */