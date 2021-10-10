/*    */ package java.text;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DontCareFieldPosition
/*    */   extends FieldPosition
/*    */ {
/* 35 */   static final FieldPosition INSTANCE = new DontCareFieldPosition();
/*    */   
/* 37 */   private final Format.FieldDelegate noDelegate = new Format.FieldDelegate()
/*    */     {
/*    */       public void formatted(Format.Field param1Field, Object param1Object, int param1Int1, int param1Int2, StringBuffer param1StringBuffer) {}
/*    */ 
/*    */       
/*    */       public void formatted(int param1Int1, Format.Field param1Field, Object param1Object, int param1Int2, int param1Int3, StringBuffer param1StringBuffer) {}
/*    */     };
/*    */ 
/*    */   
/*    */   private DontCareFieldPosition() {
/* 47 */     super(0);
/*    */   }
/*    */   
/*    */   Format.FieldDelegate getFieldDelegate() {
/* 51 */     return this.noDelegate;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/text/DontCareFieldPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */