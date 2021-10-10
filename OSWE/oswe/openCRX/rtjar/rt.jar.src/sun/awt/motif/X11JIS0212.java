/*    */ package sun.awt.motif;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import java.nio.charset.CharsetDecoder;
/*    */ import java.nio.charset.CharsetEncoder;
/*    */ import sun.nio.cs.ext.JIS_X_0212;
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
/*    */ public class X11JIS0212
/*    */   extends Charset
/*    */ {
/* 34 */   private static Charset jis0212 = new JIS_X_0212();
/*    */   
/*    */   public X11JIS0212() {
/* 37 */     super("X11JIS0212", null);
/*    */   }
/*    */   public CharsetEncoder newEncoder() {
/* 40 */     return jis0212.newEncoder();
/*    */   }
/*    */   public CharsetDecoder newDecoder() {
/* 43 */     return jis0212.newDecoder();
/*    */   }
/*    */   public boolean contains(Charset paramCharset) {
/* 46 */     return paramCharset instanceof X11JIS0212;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/motif/X11JIS0212.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */