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
/*     */ public final class PortMixerProvider
/*     */   extends MixerProvider
/*     */ {
/*     */   private static PortMixerInfo[] infos;
/*     */   private static PortMixer[] devices;
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
/*     */   public PortMixerProvider() {
/*  67 */     synchronized (PortMixerProvider.class) {
/*  68 */       if (Platform.isPortsEnabled()) {
/*  69 */         init();
/*     */       } else {
/*  71 */         infos = new PortMixerInfo[0];
/*  72 */         devices = new PortMixer[0];
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
/*  84 */       infos = new PortMixerInfo[i];
/*  85 */       devices = new PortMixer[i];
/*     */ 
/*     */ 
/*     */       
/*  89 */       for (byte b = 0; b < infos.length; b++) {
/*  90 */         infos[b] = nNewPortMixerInfo(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Mixer.Info[] getMixerInfo() {
/*  97 */     synchronized (PortMixerProvider.class) {
/*  98 */       Mixer.Info[] arrayOfInfo = new Mixer.Info[infos.length];
/*  99 */       System.arraycopy(infos, 0, arrayOfInfo, 0, infos.length);
/* 100 */       return arrayOfInfo;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Mixer getMixer(Mixer.Info paramInfo) {
/* 106 */     synchronized (PortMixerProvider.class) {
/* 107 */       for (byte b = 0; b < infos.length; b++) {
/* 108 */         if (infos[b].equals(paramInfo)) {
/* 109 */           return getDevice(infos[b]);
/*     */         }
/*     */       } 
/*     */     } 
/* 113 */     throw new IllegalArgumentException("Mixer " + paramInfo.toString() + " not supported by this provider.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Mixer getDevice(PortMixerInfo paramPortMixerInfo) {
/* 119 */     int i = paramPortMixerInfo.getIndex();
/* 120 */     if (devices[i] == null) {
/* 121 */       devices[i] = new PortMixer(paramPortMixerInfo);
/*     */     }
/* 123 */     return devices[i];
/*     */   }
/*     */ 
/*     */   
/*     */   private static native int nGetNumDevices();
/*     */ 
/*     */   
/*     */   private static native PortMixerInfo nNewPortMixerInfo(int paramInt);
/*     */   
/*     */   static final class PortMixerInfo
/*     */     extends Mixer.Info
/*     */   {
/*     */     private final int index;
/*     */     
/*     */     private PortMixerInfo(int param1Int, String param1String1, String param1String2, String param1String3, String param1String4) {
/* 138 */       super("Port " + param1String1, param1String2, param1String3, param1String4);
/* 139 */       this.index = param1Int;
/*     */     }
/*     */     
/*     */     int getIndex() {
/* 143 */       return this.index;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/PortMixerProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */