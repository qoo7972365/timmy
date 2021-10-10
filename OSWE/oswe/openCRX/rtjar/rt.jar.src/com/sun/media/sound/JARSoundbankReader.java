/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import javax.sound.midi.InvalidMidiDataException;
/*     */ import javax.sound.midi.Soundbank;
/*     */ import javax.sound.midi.spi.SoundbankReader;
/*     */ import sun.reflect.misc.ReflectUtil;
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
/*     */ public final class JARSoundbankReader
/*     */   extends SoundbankReader
/*     */ {
/*     */   private static boolean isZIP(URL paramURL) {
/*  49 */     boolean bool = false;
/*     */     try {
/*  51 */       InputStream inputStream = paramURL.openStream();
/*     */       try {
/*  53 */         byte[] arrayOfByte = new byte[4];
/*  54 */         bool = (inputStream.read(arrayOfByte) == 4) ? true : false;
/*  55 */         if (bool) {
/*  56 */           bool = (arrayOfByte[0] == 80 && arrayOfByte[1] == 75 && arrayOfByte[2] == 3 && arrayOfByte[3] == 4) ? true : false;
/*     */         
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/*  62 */         inputStream.close();
/*     */       } 
/*  64 */     } catch (IOException iOException) {}
/*     */     
/*  66 */     return bool;
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(URL paramURL) throws InvalidMidiDataException, IOException {
/*  71 */     if (!isZIP(paramURL))
/*  72 */       return null; 
/*  73 */     ArrayList<Soundbank> arrayList = new ArrayList();
/*  74 */     URLClassLoader uRLClassLoader = URLClassLoader.newInstance(new URL[] { paramURL });
/*  75 */     InputStream inputStream = uRLClassLoader.getResourceAsStream("META-INF/services/javax.sound.midi.Soundbank");
/*     */     
/*  77 */     if (inputStream == null) {
/*  78 */       return null;
/*     */     }
/*     */     try {
/*  81 */       BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
/*  82 */       String str = bufferedReader.readLine();
/*  83 */       while (str != null) {
/*  84 */         if (!str.startsWith("#")) {
/*     */           
/*  86 */           try { Class<?> clazz = Class.forName(str.trim(), false, uRLClassLoader);
/*  87 */             if (Soundbank.class.isAssignableFrom(clazz)) {
/*  88 */               Object object = ReflectUtil.newInstance(clazz);
/*  89 */               arrayList.add((Soundbank)object);
/*     */             }  }
/*  91 */           catch (ClassNotFoundException classNotFoundException) {  }
/*  92 */           catch (InstantiationException instantiationException) {  }
/*  93 */           catch (IllegalAccessException illegalAccessException) {}
/*     */         }
/*     */         
/*  96 */         str = bufferedReader.readLine();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 101 */       inputStream.close();
/*     */     } 
/* 103 */     if (arrayList.size() == 0)
/* 104 */       return null; 
/* 105 */     if (arrayList.size() == 1)
/* 106 */       return arrayList.get(0); 
/* 107 */     SimpleSoundbank simpleSoundbank = new SimpleSoundbank();
/* 108 */     for (Soundbank soundbank : arrayList)
/* 109 */       simpleSoundbank.addAllInstruments(soundbank); 
/* 110 */     return simpleSoundbank;
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(InputStream paramInputStream) throws InvalidMidiDataException, IOException {
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Soundbank getSoundbank(File paramFile) throws InvalidMidiDataException, IOException {
/* 120 */     return getSoundbank(paramFile.toURI().toURL());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/JARSoundbankReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */