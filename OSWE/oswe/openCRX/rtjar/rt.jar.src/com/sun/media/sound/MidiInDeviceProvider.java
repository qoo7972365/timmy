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
/*     */ public final class MidiInDeviceProvider
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
/*  70 */     return new MidiInDeviceInfo(paramInt, MidiInDeviceProvider.class);
/*     */   }
/*     */   
/*     */   MidiDevice createDevice(AbstractMidiDeviceProvider.Info paramInfo) {
/*  74 */     if (enabled && paramInfo instanceof MidiInDeviceInfo) {
/*  75 */       return new MidiInDevice(paramInfo);
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
/*     */   
/*     */   MidiDevice[] getDeviceCache() {
/*  90 */     return devices; }
/*  91 */   void setDeviceCache(MidiDevice[] paramArrayOfMidiDevice) { this; devices = paramArrayOfMidiDevice; }
/*  92 */   AbstractMidiDeviceProvider.Info[] getInfoCache() { return infos; } void setInfoCache(AbstractMidiDeviceProvider.Info[] paramArrayOfInfo) {
/*  93 */     this; infos = paramArrayOfInfo;
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
/*     */   static final class MidiInDeviceInfo
/*     */     extends AbstractMidiDeviceProvider.Info
/*     */   {
/*     */     private final Class providerClass;
/*     */     
/*     */     private MidiInDeviceInfo(int param1Int, Class param1Class) {
/* 112 */       super(MidiInDeviceProvider.nGetName(param1Int), MidiInDeviceProvider.nGetVendor(param1Int), MidiInDeviceProvider.nGetDescription(param1Int), MidiInDeviceProvider.nGetVersion(param1Int), param1Int);
/* 113 */       this.providerClass = param1Class;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/MidiInDeviceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */