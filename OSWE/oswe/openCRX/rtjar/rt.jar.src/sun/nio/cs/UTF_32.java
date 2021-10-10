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
/*    */ public class UTF_32
/*    */   extends Unicode
/*    */ {
/*    */   public UTF_32() {
/* 34 */     super("UTF-32", StandardCharsets.aliases_UTF_32);
/*    */   }
/*    */   
/*    */   public String historicalName() {
/* 38 */     return "UTF-32";
/*    */   }
/*    */   
/*    */   public CharsetDecoder newDecoder() {
/* 42 */     return new UTF_32Coder.Decoder(this, 0);
/*    */   }
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 46 */     return new UTF_32Coder.Encoder(this, 1, false);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/cs/UTF_32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */