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
/*    */ class UTF_16LE
/*    */   extends Unicode
/*    */ {
/*    */   public UTF_16LE() {
/* 36 */     super("UTF-16LE", StandardCharsets.aliases_UTF_16LE);
/*    */   }
/*    */   
/*    */   public String historicalName() {
/* 40 */     return "UnicodeLittleUnmarked";
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
/* 54 */       super(param1Charset, 2);
/*    */     }
/*    */   }
/*    */   
/*    */   private static class Encoder
/*    */     extends UnicodeEncoder {
/*    */     public Encoder(Charset param1Charset) {
/* 61 */       super(param1Charset, 1, false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UTF_16LE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */