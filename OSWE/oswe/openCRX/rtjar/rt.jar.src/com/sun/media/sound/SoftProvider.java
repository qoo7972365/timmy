/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.sound.midi.MidiDevice;
/*    */ import javax.sound.midi.spi.MidiDeviceProvider;
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
/*    */ public final class SoftProvider
/*    */   extends MidiDeviceProvider
/*    */ {
/* 39 */   static final MidiDevice.Info softinfo = SoftSynthesizer.info;
/* 40 */   private static final MidiDevice.Info[] softinfos = new MidiDevice.Info[] { softinfo };
/*    */   
/*    */   public MidiDevice.Info[] getDeviceInfo() {
/* 43 */     return Arrays.<MidiDevice.Info>copyOf(softinfos, softinfos.length);
/*    */   }
/*    */   
/*    */   public MidiDevice getDevice(MidiDevice.Info paramInfo) {
/* 47 */     if (paramInfo == softinfo) {
/* 48 */       return new SoftSynthesizer();
/*    */     }
/* 50 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */