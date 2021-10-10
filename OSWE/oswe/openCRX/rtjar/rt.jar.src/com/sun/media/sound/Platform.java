/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.util.StringTokenizer;
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
/*     */ final class Platform
/*     */ {
/*     */   private static final String libNameMain = "jsound";
/*     */   private static final String libNameALSA = "jsoundalsa";
/*     */   private static final String libNameDSound = "jsoundds";
/*     */   public static final int LIB_MAIN = 1;
/*     */   public static final int LIB_ALSA = 2;
/*     */   public static final int LIB_DSOUND = 4;
/*  56 */   private static int loadedLibs = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FEATURE_MIDIIO = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FEATURE_PORTS = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FEATURE_DIRECT_AUDIO = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean signed8;
/*     */ 
/*     */   
/*     */   private static boolean bigEndian;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  80 */     loadLibraries();
/*  81 */     readProperties();
/*     */   }
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
/*     */   static void initialize() {}
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
/*     */   static boolean isBigEndian() {
/* 109 */     return bigEndian;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSigned8() {
/* 118 */     return signed8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadLibraries() {
/* 130 */     AccessController.doPrivileged(() -> {
/*     */           System.loadLibrary("jsound");
/*     */           
/*     */           return null;
/*     */         });
/* 135 */     loadedLibs |= 0x1;
/*     */ 
/*     */ 
/*     */     
/* 139 */     String str = nGetExtraLibraries();
/*     */     
/* 141 */     StringTokenizer stringTokenizer = new StringTokenizer(str);
/* 142 */     while (stringTokenizer.hasMoreTokens()) {
/* 143 */       String str1 = stringTokenizer.nextToken();
/*     */       try {
/* 145 */         AccessController.doPrivileged(() -> {
/*     */               System.loadLibrary(paramString);
/*     */               
/*     */               return null;
/*     */             });
/* 150 */         if (str1.equals("jsoundalsa")) {
/* 151 */           loadedLibs |= 0x2; continue;
/*     */         } 
/* 153 */         if (str1.equals("jsoundds")) {
/* 154 */           loadedLibs |= 0x4;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 159 */       catch (Throwable throwable) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isMidiIOEnabled() {
/* 167 */     return isFeatureLibLoaded(1);
/*     */   }
/*     */   
/*     */   static boolean isPortsEnabled() {
/* 171 */     return isFeatureLibLoaded(2);
/*     */   }
/*     */   
/*     */   static boolean isDirectAudioEnabled() {
/* 175 */     return isFeatureLibLoaded(3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isFeatureLibLoaded(int paramInt) {
/* 180 */     int i = nGetLibraryForFeature(paramInt);
/* 181 */     return (i != 0 && (loadedLibs & i) == i);
/*     */   }
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
/*     */   private static void readProperties() {
/* 198 */     bigEndian = nIsBigEndian();
/* 199 */     signed8 = nIsSigned8();
/*     */   }
/*     */   
/*     */   private static native boolean nIsBigEndian();
/*     */   
/*     */   private static native boolean nIsSigned8();
/*     */   
/*     */   private static native String nGetExtraLibraries();
/*     */   
/*     */   private static native int nGetLibraryForFeature(int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */