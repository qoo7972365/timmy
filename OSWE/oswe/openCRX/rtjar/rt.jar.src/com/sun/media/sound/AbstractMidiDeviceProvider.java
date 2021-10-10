/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import javax.sound.midi.MidiDevice;
/*     */ import javax.sound.midi.spi.MidiDeviceProvider;
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
/*     */ public abstract class AbstractMidiDeviceProvider
/*     */   extends MidiDeviceProvider
/*     */ {
/*     */   static {
/*  46 */     Platform.initialize();
/*  47 */   } private static final boolean enabled = Platform.isMidiIOEnabled();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final synchronized void readDeviceInfos() {
/*  56 */     Info[] arrayOfInfo = getInfoCache();
/*  57 */     MidiDevice[] arrayOfMidiDevice = getDeviceCache();
/*  58 */     if (!enabled) {
/*  59 */       if (arrayOfInfo == null || arrayOfInfo.length != 0) {
/*  60 */         setInfoCache(new Info[0]);
/*     */       }
/*  62 */       if (arrayOfMidiDevice == null || arrayOfMidiDevice.length != 0) {
/*  63 */         setDeviceCache(new MidiDevice[0]);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  68 */     int i = (arrayOfInfo == null) ? -1 : arrayOfInfo.length;
/*  69 */     int j = getNumDevices();
/*  70 */     if (i != j) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  76 */       Info[] arrayOfInfo1 = new Info[j];
/*  77 */       MidiDevice[] arrayOfMidiDevice1 = new MidiDevice[j];
/*     */       byte b;
/*  79 */       for (b = 0; b < j; b++) {
/*  80 */         Info info = createInfo(b);
/*     */ 
/*     */ 
/*     */         
/*  84 */         if (arrayOfInfo != null) {
/*  85 */           for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*  86 */             Info info1 = arrayOfInfo[b1];
/*  87 */             if (info1 != null && info1.equalStrings(info)) {
/*     */               
/*  89 */               arrayOfInfo1[b] = info1;
/*  90 */               info1.setIndex(b);
/*  91 */               arrayOfInfo[b1] = null;
/*  92 */               arrayOfMidiDevice1[b] = arrayOfMidiDevice[b1];
/*  93 */               arrayOfMidiDevice[b1] = null;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*  98 */         if (arrayOfInfo1[b] == null) {
/*  99 */           arrayOfInfo1[b] = info;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 104 */       if (arrayOfInfo != null) {
/* 105 */         for (b = 0; b < arrayOfInfo.length; b++) {
/* 106 */           if (arrayOfInfo[b] != null)
/*     */           {
/* 108 */             arrayOfInfo[b].setIndex(-1);
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 115 */       setInfoCache(arrayOfInfo1);
/* 116 */       setDeviceCache(arrayOfMidiDevice1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final MidiDevice.Info[] getDeviceInfo() {
/* 122 */     readDeviceInfos();
/* 123 */     Info[] arrayOfInfo = getInfoCache();
/* 124 */     MidiDevice.Info[] arrayOfInfo1 = new MidiDevice.Info[arrayOfInfo.length];
/* 125 */     System.arraycopy(arrayOfInfo, 0, arrayOfInfo1, 0, arrayOfInfo.length);
/* 126 */     return arrayOfInfo1;
/*     */   }
/*     */ 
/*     */   
/*     */   public final MidiDevice getDevice(MidiDevice.Info paramInfo) {
/* 131 */     if (paramInfo instanceof Info) {
/* 132 */       readDeviceInfos();
/* 133 */       MidiDevice[] arrayOfMidiDevice = getDeviceCache();
/* 134 */       Info[] arrayOfInfo = getInfoCache();
/* 135 */       Info info = (Info)paramInfo;
/* 136 */       int i = info.getIndex();
/* 137 */       if (i >= 0 && i < arrayOfMidiDevice.length && arrayOfInfo[i] == paramInfo) {
/* 138 */         if (arrayOfMidiDevice[i] == null) {
/* 139 */           arrayOfMidiDevice[i] = createDevice(info);
/*     */         }
/* 141 */         if (arrayOfMidiDevice[i] != null) {
/* 142 */           return arrayOfMidiDevice[i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 147 */     throw new IllegalArgumentException("MidiDevice " + paramInfo.toString() + " not supported by this provider.");
/*     */   }
/*     */   abstract int getNumDevices();
/*     */   abstract MidiDevice[] getDeviceCache();
/*     */   
/*     */   abstract void setDeviceCache(MidiDevice[] paramArrayOfMidiDevice);
/*     */   
/*     */   abstract Info[] getInfoCache();
/*     */   
/*     */   abstract void setInfoCache(Info[] paramArrayOfInfo);
/*     */   
/*     */   abstract Info createInfo(int paramInt);
/*     */   
/*     */   abstract MidiDevice createDevice(Info paramInfo);
/*     */   
/*     */   static class Info extends MidiDevice.Info { Info(String param1String1, String param1String2, String param1String3, String param1String4, int param1Int) {
/* 163 */       super(param1String1, param1String2, param1String3, param1String4);
/* 164 */       this.index = param1Int;
/*     */     }
/*     */     private int index;
/*     */     final boolean equalStrings(Info param1Info) {
/* 168 */       return (param1Info != null && 
/* 169 */         getName().equals(param1Info.getName()) && 
/* 170 */         getVendor().equals(param1Info.getVendor()) && 
/* 171 */         getDescription().equals(param1Info.getDescription()) && 
/* 172 */         getVersion().equals(param1Info.getVersion()));
/*     */     }
/*     */     
/*     */     final int getIndex() {
/* 176 */       return this.index;
/*     */     }
/*     */     
/*     */     final void setIndex(int param1Int) {
/* 180 */       this.index = param1Int;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/AbstractMidiDeviceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */