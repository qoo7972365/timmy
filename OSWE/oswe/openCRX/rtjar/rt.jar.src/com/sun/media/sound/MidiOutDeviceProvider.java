/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.MidiDevice;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MidiOutDeviceProvider
/*     */   extends AbstractMidiDeviceProvider
/*     */ {
/*  40 */   private static AbstractMidiDeviceProvider.Info[] infos = null;
/*     */ 
/*     */   
/*  43 */   private static MidiDevice[] devices = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  51 */     Platform.initialize();
/*  52 */   } private static final boolean enabled = Platform.isMidiIOEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AbstractMidiDeviceProvider.Info createInfo(int paramInt) {
/*  67 */     if (!enabled) {
/*  68 */       return null;
/*     */     }
/*  70 */     return new MidiOutDeviceInfo(paramInt, MidiOutDeviceProvider.class);
/*     */   }
/*     */   
/*     */   MidiDevice createDevice(AbstractMidiDeviceProvider.Info paramInfo) {
/*  74 */     if (enabled && paramInfo instanceof MidiOutDeviceInfo) {
/*  75 */       return new MidiOutDevice(paramInfo);
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   int getNumDevices() {
/*  81 */     if (!enabled)
/*     */     {
/*  83 */       return 0;
/*     */     }
/*  85 */     return nGetNumDevices();
/*     */   }
/*     */   
/*  88 */   MidiDevice[] getDeviceCache() { return devices; }
/*  89 */   void setDeviceCache(MidiDevice[] paramArrayOfMidiDevice) { this; devices = paramArrayOfMidiDevice; }
/*  90 */   AbstractMidiDeviceProvider.Info[] getInfoCache() { return infos; } void setInfoCache(AbstractMidiDeviceProvider.Info[] paramArrayOfInfo) {
/*  91 */     this; infos = paramArrayOfInfo;
/*     */   }
/*     */   
/*     */   private static native int nGetNumDevices();
/*     */   
/*     */   private static native String nGetName(int paramInt);
/*     */   
/*     */   private static native String nGetVendor(int paramInt);
/*     */   
/*     */   private static native String nGetDescription(int paramInt);
/*     */   
/*     */   private static native String nGetVersion(int paramInt);
/*     */   
/*     */   static final class MidiOutDeviceInfo
/*     */     extends AbstractMidiDeviceProvider.Info
/*     */   {
/*     */     private final Class providerClass;
/*     */     
/*     */     private MidiOutDeviceInfo(int param1Int, Class param1Class) {
/* 110 */       super(MidiOutDeviceProvider.nGetName(param1Int), MidiOutDeviceProvider.nGetVendor(param1Int), MidiOutDeviceProvider.nGetDescription(param1Int), MidiOutDeviceProvider.nGetVersion(param1Int), param1Int);
/* 111 */       this.providerClass = param1Class;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiOutDeviceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */