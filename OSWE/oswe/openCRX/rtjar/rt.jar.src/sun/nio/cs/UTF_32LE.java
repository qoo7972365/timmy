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
/*    */ public class UTF_32LE
/*    */   extends Unicode
/*    */ {
/*    */   public UTF_32LE() {
/* 35 */     super("UTF-32LE", StandardCharsets.aliases_UTF_32LE);
/*    */   }
/*    */   
/*    */   public String historicalName() {
/* 39 */     return "UTF-32LE";
/*    */   }
/*    */   
/*    */   public CharsetDecoder newDecoder() {
/* 43 */     return new UTF_32Coder.Decoder(this, 2);
/*    */   }
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 47 */     return new UTF_32Coder.Encoder(this, 2, false);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UTF_32LE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */