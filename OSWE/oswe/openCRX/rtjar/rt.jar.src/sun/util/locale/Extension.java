/*    */ package sun.util.locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Extension
/*    */ {
/*    */   private final char key;
/*    */   private String value;
/*    */   private String id;
/*    */   
/*    */   protected Extension(char paramChar) {
/* 40 */     this.key = paramChar;
/*    */   }
/*    */   
/*    */   Extension(char paramChar, String paramString) {
/* 44 */     this.key = paramChar;
/* 45 */     setValue(paramString);
/*    */   }
/*    */   
/*    */   protected void setValue(String paramString) {
/* 49 */     this.value = paramString;
/* 50 */     this.id = this.key + "-" + paramString;
/*    */   }
/*    */   
/*    */   public char getKey() {
/* 54 */     return this.key;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 58 */     return this.value;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 62 */     return this.id;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     return getID();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/locale/Extension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */