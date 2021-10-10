/*    */ package sun.text;
/*    */ 
/*    */ import sun.text.normalizer.NormalizerBase;
/*    */ import sun.text.normalizer.NormalizerImpl;
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
/*    */ public final class Normalizer
/*    */ {
/*    */   public static final int UNICODE_3_2 = 262432;
/*    */   
/*    */   public static String normalize(CharSequence paramCharSequence, java.text.Normalizer.Form paramForm, int paramInt) {
/* 66 */     return NormalizerBase.normalize(paramCharSequence.toString(), paramForm, paramInt);
/*    */   }
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
/*    */   public static boolean isNormalized(CharSequence paramCharSequence, java.text.Normalizer.Form paramForm, int paramInt) {
/* 87 */     return NormalizerBase.isNormalized(paramCharSequence.toString(), paramForm, paramInt);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final int getCombiningClass(int paramInt) {
/* 96 */     return NormalizerImpl.getCombiningClass(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/Normalizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */