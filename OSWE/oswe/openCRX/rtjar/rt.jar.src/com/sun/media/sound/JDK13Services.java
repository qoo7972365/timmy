/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.sound.midi.Receiver;
/*     */ import javax.sound.midi.Sequencer;
/*     */ import javax.sound.midi.Synthesizer;
/*     */ import javax.sound.midi.Transmitter;
/*     */ import javax.sound.midi.spi.MidiDeviceProvider;
/*     */ import javax.sound.midi.spi.MidiFileReader;
/*     */ import javax.sound.midi.spi.MidiFileWriter;
/*     */ import javax.sound.midi.spi.SoundbankReader;
/*     */ import javax.sound.sampled.Clip;
/*     */ import javax.sound.sampled.Port;
/*     */ import javax.sound.sampled.SourceDataLine;
/*     */ import javax.sound.sampled.TargetDataLine;
/*     */ import javax.sound.sampled.spi.AudioFileReader;
/*     */ import javax.sound.sampled.spi.AudioFileWriter;
/*     */ import javax.sound.sampled.spi.FormatConversionProvider;
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
/*     */ public final class JDK13Services
/*     */ {
/*     */   private static final String PROPERTIES_FILENAME = "sound.properties";
/*     */   private static Properties properties;
/*     */   
/*     */   public static List<?> getProviders(Class<?> paramClass) {
/*     */     List<?> list;
/*  99 */     if (!MixerProvider.class.equals(paramClass) && 
/* 100 */       !FormatConversionProvider.class.equals(paramClass) && 
/* 101 */       !AudioFileReader.class.equals(paramClass) && 
/* 102 */       !AudioFileWriter.class.equals(paramClass) && 
/* 103 */       !MidiDeviceProvider.class.equals(paramClass) && 
/* 104 */       !SoundbankReader.class.equals(paramClass) && 
/* 105 */       !MidiFileWriter.class.equals(paramClass) && 
/* 106 */       !MidiFileReader.class.equals(paramClass)) {
/* 107 */       list = new ArrayList(0);
/*     */     } else {
/* 109 */       list = JSSecurityManager.getProviders(paramClass);
/*     */     } 
/* 111 */     return Collections.unmodifiableList(list);
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
/*     */   public static synchronized String getDefaultProviderClassName(Class paramClass) {
/* 124 */     String str1 = null;
/* 125 */     String str2 = getDefaultProvider(paramClass);
/* 126 */     if (str2 != null) {
/* 127 */       int i = str2.indexOf('#');
/* 128 */       if (i != 0)
/*     */       {
/* 130 */         if (i > 0) {
/* 131 */           str1 = str2.substring(0, i);
/*     */         } else {
/* 133 */           str1 = str2;
/*     */         }  } 
/*     */     } 
/* 136 */     return str1;
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
/*     */   public static synchronized String getDefaultInstanceName(Class paramClass) {
/* 150 */     String str1 = null;
/* 151 */     String str2 = getDefaultProvider(paramClass);
/* 152 */     if (str2 != null) {
/* 153 */       int i = str2.indexOf('#');
/* 154 */       if (i >= 0 && i < str2.length() - 1) {
/* 155 */         str1 = str2.substring(i + 1);
/*     */       }
/*     */     } 
/* 158 */     return str1;
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
/*     */   private static synchronized String getDefaultProvider(Class paramClass) {
/* 171 */     if (!SourceDataLine.class.equals(paramClass) && 
/* 172 */       !TargetDataLine.class.equals(paramClass) && 
/* 173 */       !Clip.class.equals(paramClass) && 
/* 174 */       !Port.class.equals(paramClass) && 
/* 175 */       !Receiver.class.equals(paramClass) && 
/* 176 */       !Transmitter.class.equals(paramClass) && 
/* 177 */       !Synthesizer.class.equals(paramClass) && 
/* 178 */       !Sequencer.class.equals(paramClass)) {
/* 179 */       return null;
/*     */     }
/* 181 */     String str1 = paramClass.getName();
/* 182 */     String str2 = AccessController.<String>doPrivileged(() -> System.getProperty(paramString));
/*     */     
/* 184 */     if (str2 == null) {
/* 185 */       str2 = getProperties().getProperty(str1);
/*     */     }
/* 187 */     if ("".equals(str2)) {
/* 188 */       str2 = null;
/*     */     }
/* 190 */     return str2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized Properties getProperties() {
/* 199 */     if (properties == null) {
/* 200 */       properties = new Properties();
/* 201 */       JSSecurityManager.loadProperties(properties, "sound.properties");
/*     */     } 
/* 203 */     return properties;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/JDK13Services.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */