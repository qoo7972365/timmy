/*    */ package javax.sound.sampled.spi;
/*    */ 
/*    */ import javax.sound.sampled.Mixer;
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
/*    */ public abstract class MixerProvider
/*    */ {
/*    */   public boolean isMixerSupported(Mixer.Info paramInfo) {
/* 57 */     Mixer.Info[] arrayOfInfo = getMixerInfo();
/*    */     
/* 59 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 60 */       if (paramInfo.equals(arrayOfInfo[b])) {
/* 61 */         return true;
/*    */       }
/*    */     } 
/* 64 */     return false;
/*    */   }
/*    */   
/*    */   public abstract Mixer.Info[] getMixerInfo();
/*    */   
/*    */   public abstract Mixer getMixer(Mixer.Info paramInfo);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/spi/MixerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */