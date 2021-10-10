/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.sampled.Mixer;
/*     */ import javax.sound.sampled.spi.MixerProvider;
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
/*     */ public final class DirectAudioDeviceProvider
/*     */   extends MixerProvider
/*     */ {
/*     */   private static DirectAudioDeviceInfo[] infos;
/*     */   private static DirectAudioDevice[] devices;
/*     */   
/*     */   static {
/*  56 */     Platform.initialize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectAudioDeviceProvider() {
/*  67 */     synchronized (DirectAudioDeviceProvider.class) {
/*  68 */       if (Platform.isDirectAudioEnabled()) {
/*  69 */         init();
/*     */       } else {
/*  71 */         infos = new DirectAudioDeviceInfo[0];
/*  72 */         devices = new DirectAudioDevice[0];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void init() {
/*  79 */     int i = nGetNumDevices();
/*     */     
/*  81 */     if (infos == null || infos.length != i) {
/*     */ 
/*     */       
/*  84 */       infos = new DirectAudioDeviceInfo[i];
/*  85 */       devices = new DirectAudioDevice[i];
/*     */ 
/*     */       
/*  88 */       for (byte b = 0; b < infos.length; b++) {
/*  89 */         infos[b] = nNewDirectAudioDeviceInfo(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Mixer.Info[] getMixerInfo() {
/*  96 */     synchronized (DirectAudioDeviceProvider.class) {
/*  97 */       Mixer.Info[] arrayOfInfo = new Mixer.Info[infos.length];
/*  98 */       System.arraycopy(infos, 0, arrayOfInfo, 0, infos.length);
/*  99 */       return arrayOfInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Mixer getMixer(Mixer.Info paramInfo) {
/* 105 */     synchronized (DirectAudioDeviceProvider.class) {
/*     */ 
/*     */       
/* 108 */       if (paramInfo == null) {
/* 109 */         for (byte b1 = 0; b1 < infos.length; b1++) {
/* 110 */           Mixer mixer = getDevice(infos[b1]);
/* 111 */           if ((mixer.getSourceLineInfo()).length > 0) {
/* 112 */             return mixer;
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 118 */       for (byte b = 0; b < infos.length; b++) {
/* 119 */         if (infos[b].equals(paramInfo)) {
/* 120 */           return getDevice(infos[b]);
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     throw new IllegalArgumentException("Mixer " + paramInfo.toString() + " not supported by this provider.");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Mixer getDevice(DirectAudioDeviceInfo paramDirectAudioDeviceInfo) {
/* 129 */     int i = paramDirectAudioDeviceInfo.getIndex();
/* 130 */     if (devices[i] == null) {
/* 131 */       devices[i] = new DirectAudioDevice(paramDirectAudioDeviceInfo);
/*     */     }
/* 133 */     return devices[i];
/*     */   }
/*     */ 
/*     */   
/*     */   private static native int nGetNumDevices();
/*     */ 
/*     */   
/*     */   private static native DirectAudioDeviceInfo nNewDirectAudioDeviceInfo(int paramInt);
/*     */ 
/*     */   
/*     */   static final class DirectAudioDeviceInfo
/*     */     extends Mixer.Info
/*     */   {
/*     */     private final int index;
/*     */     
/*     */     private final int maxSimulLines;
/*     */     
/*     */     private final int deviceID;
/*     */ 
/*     */     
/*     */     private DirectAudioDeviceInfo(int param1Int1, int param1Int2, int param1Int3, String param1String1, String param1String2, String param1String3, String param1String4) {
/* 154 */       super(param1String1, param1String2, "Direct Audio Device: " + param1String3, param1String4);
/* 155 */       this.index = param1Int1;
/* 156 */       this.maxSimulLines = param1Int3;
/* 157 */       this.deviceID = param1Int2;
/*     */     }
/*     */     
/*     */     int getIndex() {
/* 161 */       return this.index;
/*     */     }
/*     */     
/*     */     int getMaxSimulLines() {
/* 165 */       return this.maxSimulLines;
/*     */     }
/*     */     
/*     */     int getDeviceID() {
/* 169 */       return this.deviceID;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/DirectAudioDeviceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */