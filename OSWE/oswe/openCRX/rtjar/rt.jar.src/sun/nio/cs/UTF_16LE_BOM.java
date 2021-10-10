/*    */ package sun.nio.cs;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetDecoder;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class UTF_16LE_BOM
/*    */   extends Unicode
/*    */ {
/*    */   public UTF_16LE_BOM() {
/* 36 */     super("x-UTF-16LE-BOM", StandardCharsets.aliases_UTF_16LE_BOM);
/*    */   }
/*    */   
/*    */   public String historicalName() {
/* 40 */     return "UnicodeLittle";
/*    */   }
/*    */   
/*    */   public CharsetDecoder newDecoder() {
/* 44 */     return new Decoder(this);
/*    */   }
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 48 */     return new Encoder(this);
/*    */   }
/*    */   
/*    */   private static class Decoder
/*    */     extends UnicodeDecoder {
/*    */     public Decoder(Charset param1Charset) {
/* 54 */       super(param1Charset, 0, 2);
/*    */     }
/*    */   }
/*    */   
/*    */   private static class Encoder
/*    */     extends UnicodeEncoder {
/*    */     public Encoder(Charset param1Charset) {
/* 61 */       super(param1Charset, 1, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UTF_16LE_BOM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */