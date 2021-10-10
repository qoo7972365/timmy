/*    */ package sun.awt.motif;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetDecoder;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ import sun.nio.cs.ext.JIS_X_0208;
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
/*    */ public class X11JIS0208
/*    */   extends Charset
/*    */ {
/* 35 */   private static Charset jis0208 = new JIS_X_0208();
/*    */   
/*    */   public X11JIS0208() {
/* 38 */     super("X11JIS0208", null);
/*    */   }
/*    */   
/*    */   public CharsetEncoder newEncoder() {
/* 42 */     return jis0208.newEncoder();
/*    */   }
/*    */   
/*    */   public CharsetDecoder newDecoder() {
/* 46 */     return jis0208.newDecoder();
/*    */   }
/*    */   
/*    */   public boolean contains(Charset paramCharset) {
/* 50 */     return paramCharset instanceof X11JIS0208;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/X11JIS0208.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */