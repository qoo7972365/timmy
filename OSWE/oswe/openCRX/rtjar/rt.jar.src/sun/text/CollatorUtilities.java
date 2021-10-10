/*    */ package sun.text;
/*    */ 
/*    */ import sun.text.normalizer.NormalizerBase;
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
/*    */ public class CollatorUtilities
/*    */ {
/*    */   public static int toLegacyMode(NormalizerBase.Mode paramMode) {
/* 35 */     int i = legacyModeMap.length;
/* 36 */     while (i > 0) {
/* 37 */       i--;
/* 38 */       if (legacyModeMap[i] == paramMode) {
/*    */         break;
/*    */       }
/*    */     } 
/* 42 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static NormalizerBase.Mode toNormalizerMode(int paramInt) {
/*    */     NormalizerBase.Mode mode;
/*    */     try {
/* 49 */       mode = legacyModeMap[paramInt];
/*    */     }
/* 51 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
/* 52 */       mode = NormalizerBase.NONE;
/*    */     } 
/* 54 */     return mode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 59 */   static NormalizerBase.Mode[] legacyModeMap = new NormalizerBase.Mode[] { NormalizerBase.NONE, NormalizerBase.NFD, NormalizerBase.NFKD };
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/text/CollatorUtilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */