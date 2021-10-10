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
/*    */ 
/*    */ 
/*    */ public final class SunPageSelection
/*    */   implements PrintRequestAttribute
/*    */ {
/* 35 */   public static final SunPageSelection ALL = new SunPageSelection(0);
/* 36 */   public static final SunPageSelection RANGE = new SunPageSelection(1);
/* 37 */   public static final SunPageSelection SELECTION = new SunPageSelection(2);
/*    */   
/*    */   private int pages;
/*    */   
/*    */   public SunPageSelection(int paramInt) {
/* 42 */     this.pages = paramInt;
/*    */   }
/*    */   
/*    */   public final Class getCategory() {
/* 46 */     return SunPageSelection.class;
/*    */   }
/*    */   
/*    */   public final String getName() {
/* 50 */     return "sun-page-selection";
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return "page-selection: " + this.pages;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/SunPageSelection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */