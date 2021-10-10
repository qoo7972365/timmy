/*    */ package javax.sound.midi.spi;
/*    */ 
/*    */ import javax.sound.midi.MidiDevice;
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
/*    */ public abstract class MidiDeviceProvider
/*    */ {
/*    */   public boolean isDeviceSupported(MidiDevice.Info paramInfo) {
/* 50 */     MidiDevice.Info[] arrayOfInfo = getDeviceInfo();
/*    */     
/* 52 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 53 */       if (paramInfo.equals(arrayOfInfo[b])) {
/* 54 */         return true;
/*    */       }
/*    */     } 
/* 57 */     return false;
/*    */   }
/*    */   
/*    */   public abstract MidiDevice.Info[] getDeviceInfo();
/*    */   
/*    */   public abstract MidiDevice getDevice(MidiDevice.Info paramInfo);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/midi/spi/MidiDeviceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */