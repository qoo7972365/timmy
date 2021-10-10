/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.MidiDevice;
/*    */ import javax.sound.midi.MidiDeviceTransmitter;
/*    */ import javax.sound.midi.Receiver;
/*    */ import javax.sound.midi.Transmitter;
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
/*    */ public final class MidiDeviceTransmitterEnvelope
/*    */   implements MidiDeviceTransmitter
/*    */ {
/*    */   private final MidiDevice device;
/*    */   private final Transmitter transmitter;
/*    */   
/*    */   public MidiDeviceTransmitterEnvelope(MidiDevice paramMidiDevice, Transmitter paramTransmitter) {
/* 50 */     if (paramMidiDevice == null || paramTransmitter == null) {
/* 51 */       throw new NullPointerException();
/*    */     }
/* 53 */     this.device = paramMidiDevice;
/* 54 */     this.transmitter = paramTransmitter;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setReceiver(Receiver paramReceiver) {
/* 59 */     this.transmitter.setReceiver(paramReceiver);
/*    */   }
/*    */   
/*    */   public Receiver getReceiver() {
/* 63 */     return this.transmitter.getReceiver();
/*    */   }
/*    */   
/*    */   public void close() {
/* 67 */     this.transmitter.close();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MidiDevice getMidiDevice() {
/* 73 */     return this.device;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transmitter getTransmitter() {
/* 83 */     return this.transmitter;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiDeviceTransmitterEnvelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */