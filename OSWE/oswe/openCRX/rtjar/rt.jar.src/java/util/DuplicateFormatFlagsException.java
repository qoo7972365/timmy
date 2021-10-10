/*    */ package java.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuplicateFormatFlagsException
/*    */   extends IllegalFormatException
/*    */ {
/*    */   private static final long serialVersionUID = 18890531L;
/*    */   private String flags;
/*    */   
/*    */   public DuplicateFormatFlagsException(String paramString) {
/* 51 */     if (paramString == null)
/* 52 */       throw new NullPointerException(); 
/* 53 */     this.flags = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFlags() {
/* 62 */     return this.flags;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 66 */     return String.format("Flags = '%s'", new Object[] { this.flags });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/DuplicateFormatFlagsException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */