/*    */ package com.sun.media.sound;
/*    */ 
/*    */ import javax.sound.midi.MidiDevice;
/*    */ import javax.sound.midi.MidiDeviceReceiver;
/*    */ import javax.sound.midi.MidiMessage;
/*    */ import javax.sound.midi.Receiver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MidiDeviceReceiverEnvelope
/*    */   implements MidiDeviceReceiver
/*    */ {
/*    */   private final MidiDevice device;
/*    */   private final Receiver receiver;
/*    */   
/*    */   public MidiDeviceReceiverEnvelope(MidiDevice paramMidiDevice, Receiver paramReceiver) {
/* 50 */     if (paramMidiDevice == null || paramReceiver == null) {
/* 51 */       throw new NullPointerException();
/*    */     }
/* 53 */     this.device = paramMidiDevice;
/* 54 */     this.receiver = paramReceiver;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 59 */     this.receiver.close();
/*    */   }
/*    */   
/*    */   public void send(MidiMessage paramMidiMessage, long paramLong) {
/* 63 */     this.receiver.send(paramMidiMessage, paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public MidiDevice getMidiDevice() {
/* 68 */     return this.device;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Receiver getReceiver() {
/* 78 */     return this.receiver;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiDeviceReceiverEnvelope.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */