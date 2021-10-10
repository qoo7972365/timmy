/*    */ package sun.print;
/*    */ 
/*    */ import javax.print.attribute.PrintRequestAttribute;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SunMinMaxPage
/*    */   implements PrintRequestAttribute
/*    */ {
/*    */   private int page_max;
/*    */   private int page_min;
/*    */   
/*    */   public SunMinMaxPage(int paramInt1, int paramInt2) {
/* 37 */     this.page_min = paramInt1;
/* 38 */     this.page_max = paramInt2;
/*    */   }
/*    */ 
/*    */   
/*    */   public final Class getCategory() {
/* 43 */     return SunMinMaxPage.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public final int getMin() {
/* 48 */     return this.page_min;
/*    */   }
/*    */   
/*    */   public final int getMax() {
/* 52 */     return this.page_max;
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 57 */     return "sun-page-minmax";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/SunMinMaxPage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */