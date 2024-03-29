/*    */ package java.nio.charset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StandardCharsets
/*    */ {
/*    */   private StandardCharsets() {
/* 38 */     throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/*    */ 
/*    */ 
/*    */   
/* 48 */   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*    */ 
/*    */ 
/*    */   
/* 56 */   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
/*    */ 
/*    */ 
/*    */   
/* 60 */   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   public static final Charset UTF_16 = Charset.forName("UTF-16");
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/charset/StandardCharsets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */