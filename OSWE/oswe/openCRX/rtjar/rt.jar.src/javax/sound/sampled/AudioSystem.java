/*      */ package javax.sound.sampled;
/*      */ 
/*      */ import com.sun.media.sound.JDK13Services;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import javax.sound.sampled.spi.AudioFileReader;
/*      */ import javax.sound.sampled.spi.AudioFileWriter;
/*      */ import javax.sound.sampled.spi.FormatConversionProvider;
/*      */ import javax.sound.sampled.spi.MixerProvider;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class AudioSystem
/*      */ {
/*      */   public static final int NOT_SPECIFIED = -1;
/*      */   
/*      */   public static Mixer.Info[] getMixerInfo() {
/*  199 */     List list = getMixerInfoList();
/*  200 */     return (Mixer.Info[])list.toArray((Object[])new Mixer.Info[list.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Mixer getMixer(Mixer.Info paramInfo) {
/*  218 */     Object object = null;
/*  219 */     List<MixerProvider> list = getMixerProviders();
/*      */     byte b;
/*  221 */     for (b = 0; b < list.size(); b++) {
/*      */ 
/*      */       
/*  224 */       try { return ((MixerProvider)list.get(b)).getMixer(paramInfo); }
/*      */       
/*  226 */       catch (IllegalArgumentException illegalArgumentException) {  }
/*  227 */       catch (NullPointerException nullPointerException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  238 */     if (paramInfo == null) {
/*  239 */       for (b = 0; b < list.size(); b++) {
/*      */         
/*  241 */         try { MixerProvider mixerProvider = list.get(b);
/*  242 */           Mixer.Info[] arrayOfInfo = mixerProvider.getMixerInfo();
/*      */           
/*  244 */           for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*      */             try {
/*  246 */               return mixerProvider.getMixer(arrayOfInfo[b1]);
/*  247 */             } catch (IllegalArgumentException illegalArgumentException) {}
/*      */           }
/*      */            }
/*      */         
/*  251 */         catch (IllegalArgumentException illegalArgumentException) {  }
/*  252 */         catch (NullPointerException nullPointerException) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  258 */     throw new IllegalArgumentException("Mixer not supported: " + ((paramInfo != null) ? paramInfo
/*  259 */         .toString() : "null"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Line.Info[] getSourceLineInfo(Line.Info paramInfo) {
/*  277 */     Vector<Line.Info> vector = new Vector();
/*      */ 
/*      */ 
/*      */     
/*  281 */     Object object = null;
/*  282 */     Mixer.Info[] arrayOfInfo = getMixerInfo();
/*      */     
/*  284 */     for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*      */       
/*  286 */       Mixer mixer = getMixer(arrayOfInfo[b1]);
/*      */       
/*  288 */       Line.Info[] arrayOfInfo2 = mixer.getSourceLineInfo(paramInfo);
/*  289 */       for (byte b = 0; b < arrayOfInfo2.length; b++) {
/*  290 */         vector.addElement(arrayOfInfo2[b]);
/*      */       }
/*      */     } 
/*      */     
/*  294 */     Line.Info[] arrayOfInfo1 = new Line.Info[vector.size()];
/*      */     
/*  296 */     for (byte b2 = 0; b2 < arrayOfInfo1.length; b2++) {
/*  297 */       arrayOfInfo1[b2] = vector.get(b2);
/*      */     }
/*      */     
/*  300 */     return arrayOfInfo1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Line.Info[] getTargetLineInfo(Line.Info paramInfo) {
/*  317 */     Vector<Line.Info> vector = new Vector();
/*      */ 
/*      */ 
/*      */     
/*  321 */     Object object = null;
/*  322 */     Mixer.Info[] arrayOfInfo = getMixerInfo();
/*      */     
/*  324 */     for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*      */       
/*  326 */       Mixer mixer = getMixer(arrayOfInfo[b1]);
/*      */       
/*  328 */       Line.Info[] arrayOfInfo2 = mixer.getTargetLineInfo(paramInfo);
/*  329 */       for (byte b = 0; b < arrayOfInfo2.length; b++) {
/*  330 */         vector.addElement(arrayOfInfo2[b]);
/*      */       }
/*      */     } 
/*      */     
/*  334 */     Line.Info[] arrayOfInfo1 = new Line.Info[vector.size()];
/*      */     
/*  336 */     for (byte b2 = 0; b2 < arrayOfInfo1.length; b2++) {
/*  337 */       arrayOfInfo1[b2] = vector.get(b2);
/*      */     }
/*      */     
/*  340 */     return arrayOfInfo1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLineSupported(Line.Info paramInfo) {
/*  357 */     Mixer.Info[] arrayOfInfo = getMixerInfo();
/*      */     
/*  359 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/*      */       
/*  361 */       if (arrayOfInfo[b] != null) {
/*  362 */         Mixer mixer = getMixer(arrayOfInfo[b]);
/*  363 */         if (mixer.isLineSupported(paramInfo)) {
/*  364 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  369 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Line getLine(Line.Info paramInfo) throws LineUnavailableException {
/*  410 */     LineUnavailableException lineUnavailableException = null;
/*  411 */     List<MixerProvider> list = getMixerProviders();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  416 */       Mixer mixer = getDefaultMixer(list, paramInfo);
/*  417 */       if (mixer != null && mixer.isLineSupported(paramInfo)) {
/*  418 */         return mixer.getLine(paramInfo);
/*      */       }
/*  420 */     } catch (LineUnavailableException lineUnavailableException1) {
/*  421 */       lineUnavailableException = lineUnavailableException1;
/*  422 */     } catch (IllegalArgumentException illegalArgumentException) {}
/*      */ 
/*      */ 
/*      */     
/*      */     byte b;
/*      */ 
/*      */     
/*  429 */     for (b = 0; b < list.size(); b++) {
/*  430 */       MixerProvider mixerProvider = list.get(b);
/*  431 */       Mixer.Info[] arrayOfInfo = mixerProvider.getMixerInfo();
/*      */       
/*  433 */       for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*      */         try {
/*  435 */           Mixer mixer = mixerProvider.getMixer(arrayOfInfo[b1]);
/*      */           
/*  437 */           if (isAppropriateMixer(mixer, paramInfo, true)) {
/*  438 */             return mixer.getLine(paramInfo);
/*      */           }
/*  440 */         } catch (LineUnavailableException lineUnavailableException1) {
/*  441 */           lineUnavailableException = lineUnavailableException1;
/*  442 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  451 */     for (b = 0; b < list.size(); b++) {
/*  452 */       MixerProvider mixerProvider = list.get(b);
/*  453 */       Mixer.Info[] arrayOfInfo = mixerProvider.getMixerInfo();
/*  454 */       for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/*      */         try {
/*  456 */           Mixer mixer = mixerProvider.getMixer(arrayOfInfo[b1]);
/*      */           
/*  458 */           if (isAppropriateMixer(mixer, paramInfo, false)) {
/*  459 */             return mixer.getLine(paramInfo);
/*      */           }
/*  461 */         } catch (LineUnavailableException lineUnavailableException1) {
/*  462 */           lineUnavailableException = lineUnavailableException1;
/*  463 */         } catch (IllegalArgumentException illegalArgumentException) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  472 */     if (lineUnavailableException != null) {
/*  473 */       throw lineUnavailableException;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  478 */     throw new IllegalArgumentException("No line matching " + paramInfo
/*  479 */         .toString() + " is supported.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Clip getClip() throws LineUnavailableException {
/*  517 */     AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, 16, 2, 4, -1.0F, true);
/*      */ 
/*      */ 
/*      */     
/*  521 */     DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
/*  522 */     return (Clip)getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Clip getClip(Mixer.Info paramInfo) throws LineUnavailableException {
/*  552 */     AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, -1.0F, 16, 2, 4, -1.0F, true);
/*      */ 
/*      */ 
/*      */     
/*  556 */     DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
/*  557 */     Mixer mixer = getMixer(paramInfo);
/*  558 */     return (Clip)mixer.getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SourceDataLine getSourceDataLine(AudioFormat paramAudioFormat) throws LineUnavailableException {
/*  605 */     DataLine.Info info = new DataLine.Info(SourceDataLine.class, paramAudioFormat);
/*  606 */     return (SourceDataLine)getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SourceDataLine getSourceDataLine(AudioFormat paramAudioFormat, Mixer.Info paramInfo) throws LineUnavailableException {
/*  650 */     DataLine.Info info = new DataLine.Info(SourceDataLine.class, paramAudioFormat);
/*  651 */     Mixer mixer = getMixer(paramInfo);
/*  652 */     return (SourceDataLine)mixer.getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TargetDataLine getTargetDataLine(AudioFormat paramAudioFormat) throws LineUnavailableException {
/*  701 */     DataLine.Info info = new DataLine.Info(TargetDataLine.class, paramAudioFormat);
/*  702 */     return (TargetDataLine)getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static TargetDataLine getTargetDataLine(AudioFormat paramAudioFormat, Mixer.Info paramInfo) throws LineUnavailableException {
/*  748 */     DataLine.Info info = new DataLine.Info(TargetDataLine.class, paramAudioFormat);
/*  749 */     Mixer mixer = getMixer(paramInfo);
/*  750 */     return (TargetDataLine)mixer.getLine(info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFormat.Encoding[] getTargetEncodings(AudioFormat.Encoding paramEncoding) {
/*  767 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*  768 */     Vector<AudioFormat.Encoding> vector = new Vector();
/*      */     
/*  770 */     AudioFormat.Encoding[] arrayOfEncoding = null;
/*      */ 
/*      */     
/*  773 */     for (byte b = 0; b < list.size(); b++) {
/*  774 */       FormatConversionProvider formatConversionProvider = list.get(b);
/*  775 */       if (formatConversionProvider.isSourceEncodingSupported(paramEncoding)) {
/*  776 */         arrayOfEncoding = formatConversionProvider.getTargetEncodings();
/*  777 */         for (byte b1 = 0; b1 < arrayOfEncoding.length; b1++) {
/*  778 */           vector.addElement(arrayOfEncoding[b1]);
/*      */         }
/*      */       } 
/*      */     } 
/*  782 */     return vector.<AudioFormat.Encoding>toArray(new AudioFormat.Encoding[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat) {
/*  802 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*  803 */     Vector<AudioFormat.Encoding[]> vector = new Vector();
/*      */     
/*  805 */     int i = 0;
/*  806 */     byte b1 = 0;
/*  807 */     AudioFormat.Encoding[] arrayOfEncoding1 = null;
/*      */ 
/*      */ 
/*      */     
/*  811 */     for (byte b2 = 0; b2 < list.size(); b2++) {
/*  812 */       arrayOfEncoding1 = ((FormatConversionProvider)list.get(b2)).getTargetEncodings(paramAudioFormat);
/*  813 */       i += arrayOfEncoding1.length;
/*  814 */       vector.addElement(arrayOfEncoding1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  819 */     AudioFormat.Encoding[] arrayOfEncoding2 = new AudioFormat.Encoding[i];
/*  820 */     for (byte b3 = 0; b3 < vector.size(); b3++) {
/*  821 */       arrayOfEncoding1 = vector.get(b3);
/*  822 */       for (byte b = 0; b < arrayOfEncoding1.length; b++) {
/*  823 */         arrayOfEncoding2[b1++] = arrayOfEncoding1[b];
/*      */       }
/*      */     } 
/*  826 */     return arrayOfEncoding2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConversionSupported(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/*  842 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*      */     
/*  844 */     for (byte b = 0; b < list.size(); b++) {
/*  845 */       FormatConversionProvider formatConversionProvider = list.get(b);
/*  846 */       if (formatConversionProvider.isConversionSupported(paramEncoding, paramAudioFormat)) {
/*  847 */         return true;
/*      */       }
/*      */     } 
/*  850 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream) {
/*  869 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*      */     
/*  871 */     for (byte b = 0; b < list.size(); b++) {
/*  872 */       FormatConversionProvider formatConversionProvider = list.get(b);
/*  873 */       if (formatConversionProvider.isConversionSupported(paramEncoding, paramAudioInputStream.getFormat())) {
/*  874 */         return formatConversionProvider.getAudioInputStream(paramEncoding, paramAudioInputStream);
/*      */       }
/*      */     } 
/*      */     
/*  878 */     throw new IllegalArgumentException("Unsupported conversion: " + paramEncoding + " from " + paramAudioInputStream.getFormat());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/*  893 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*  894 */     Vector<AudioFormat[]> vector = new Vector();
/*      */     
/*  896 */     int i = 0;
/*  897 */     byte b1 = 0;
/*  898 */     AudioFormat[] arrayOfAudioFormat1 = null;
/*      */ 
/*      */ 
/*      */     
/*  902 */     for (byte b2 = 0; b2 < list.size(); b2++) {
/*  903 */       FormatConversionProvider formatConversionProvider = list.get(b2);
/*  904 */       arrayOfAudioFormat1 = formatConversionProvider.getTargetFormats(paramEncoding, paramAudioFormat);
/*  905 */       i += arrayOfAudioFormat1.length;
/*  906 */       vector.addElement(arrayOfAudioFormat1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  911 */     AudioFormat[] arrayOfAudioFormat2 = new AudioFormat[i];
/*  912 */     for (byte b3 = 0; b3 < vector.size(); b3++) {
/*  913 */       arrayOfAudioFormat1 = vector.get(b3);
/*  914 */       for (byte b = 0; b < arrayOfAudioFormat1.length; b++) {
/*  915 */         arrayOfAudioFormat2[b1++] = arrayOfAudioFormat1[b];
/*      */       }
/*      */     } 
/*  918 */     return arrayOfAudioFormat2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConversionSupported(AudioFormat paramAudioFormat1, AudioFormat paramAudioFormat2) {
/*  933 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*      */     
/*  935 */     for (byte b = 0; b < list.size(); b++) {
/*  936 */       FormatConversionProvider formatConversionProvider = list.get(b);
/*  937 */       if (formatConversionProvider.isConversionSupported(paramAudioFormat1, paramAudioFormat2)) {
/*  938 */         return true;
/*      */       }
/*      */     } 
/*  941 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream) {
/*  960 */     if (paramAudioInputStream.getFormat().matches(paramAudioFormat)) {
/*  961 */       return paramAudioInputStream;
/*      */     }
/*      */     
/*  964 */     List<FormatConversionProvider> list = getFormatConversionProviders();
/*      */     
/*  966 */     for (byte b = 0; b < list.size(); b++) {
/*  967 */       FormatConversionProvider formatConversionProvider = list.get(b);
/*  968 */       if (formatConversionProvider.isConversionSupported(paramAudioFormat, paramAudioInputStream.getFormat())) {
/*  969 */         return formatConversionProvider.getAudioInputStream(paramAudioFormat, paramAudioInputStream);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  974 */     throw new IllegalArgumentException("Unsupported conversion: " + paramAudioFormat + " from " + paramAudioInputStream.getFormat());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*  998 */     List<AudioFileReader> list = getAudioFileReaders();
/*  999 */     AudioFileFormat audioFileFormat = null;
/*      */     
/* 1001 */     for (byte b = 0; b < list.size(); b++) {
/* 1002 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1004 */         audioFileFormat = audioFileReader.getAudioFileFormat(paramInputStream);
/*      */         break;
/* 1006 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1011 */     if (audioFileFormat == null) {
/* 1012 */       throw new UnsupportedAudioFileException("file is not a supported file type");
/*      */     }
/* 1014 */     return audioFileFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 1031 */     List<AudioFileReader> list = getAudioFileReaders();
/* 1032 */     AudioFileFormat audioFileFormat = null;
/*      */     
/* 1034 */     for (byte b = 0; b < list.size(); b++) {
/* 1035 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1037 */         audioFileFormat = audioFileReader.getAudioFileFormat(paramURL);
/*      */         break;
/* 1039 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1044 */     if (audioFileFormat == null) {
/* 1045 */       throw new UnsupportedAudioFileException("file is not a supported file type");
/*      */     }
/* 1047 */     return audioFileFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 1064 */     List<AudioFileReader> list = getAudioFileReaders();
/* 1065 */     AudioFileFormat audioFileFormat = null;
/*      */     
/* 1067 */     for (byte b = 0; b < list.size(); b++) {
/* 1068 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1070 */         audioFileFormat = audioFileReader.getAudioFileFormat(paramFile);
/*      */         break;
/* 1072 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1077 */     if (audioFileFormat == null) {
/* 1078 */       throw new UnsupportedAudioFileException("file is not a supported file type");
/*      */     }
/* 1080 */     return audioFileFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 1107 */     List<AudioFileReader> list = getAudioFileReaders();
/* 1108 */     AudioInputStream audioInputStream = null;
/*      */     
/* 1110 */     for (byte b = 0; b < list.size(); b++) {
/* 1111 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1113 */         audioInputStream = audioFileReader.getAudioInputStream(paramInputStream);
/*      */         break;
/* 1115 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1120 */     if (audioInputStream == null) {
/* 1121 */       throw new UnsupportedAudioFileException("could not get audio input stream from input stream");
/*      */     }
/* 1123 */     return audioInputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 1141 */     List<AudioFileReader> list = getAudioFileReaders();
/* 1142 */     AudioInputStream audioInputStream = null;
/*      */     
/* 1144 */     for (byte b = 0; b < list.size(); b++) {
/* 1145 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1147 */         audioInputStream = audioFileReader.getAudioInputStream(paramURL);
/*      */         break;
/* 1149 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1154 */     if (audioInputStream == null) {
/* 1155 */       throw new UnsupportedAudioFileException("could not get audio input stream from input URL");
/*      */     }
/* 1157 */     return audioInputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 1175 */     List<AudioFileReader> list = getAudioFileReaders();
/* 1176 */     AudioInputStream audioInputStream = null;
/*      */     
/* 1178 */     for (byte b = 0; b < list.size(); b++) {
/* 1179 */       AudioFileReader audioFileReader = list.get(b);
/*      */       try {
/* 1181 */         audioInputStream = audioFileReader.getAudioInputStream(paramFile);
/*      */         break;
/* 1183 */       } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     if (audioInputStream == null) {
/* 1189 */       throw new UnsupportedAudioFileException("could not get audio input stream from input file");
/*      */     }
/* 1191 */     return audioInputStream;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFileFormat.Type[] getAudioFileTypes() {
/* 1202 */     List<AudioFileWriter> list = getAudioFileWriters();
/* 1203 */     HashSet<AudioFileFormat.Type> hashSet = new HashSet();
/*      */     
/* 1205 */     for (byte b = 0; b < list.size(); b++) {
/* 1206 */       AudioFileWriter audioFileWriter = list.get(b);
/* 1207 */       AudioFileFormat.Type[] arrayOfType = audioFileWriter.getAudioFileTypes();
/* 1208 */       for (byte b1 = 0; b1 < arrayOfType.length; b1++) {
/* 1209 */         hashSet.add(arrayOfType[b1]);
/*      */       }
/*      */     } 
/* 1212 */     return hashSet
/* 1213 */       .<AudioFileFormat.Type>toArray(new AudioFileFormat.Type[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFileTypeSupported(AudioFileFormat.Type paramType) {
/* 1227 */     List<AudioFileWriter> list = getAudioFileWriters();
/*      */     
/* 1229 */     for (byte b = 0; b < list.size(); b++) {
/* 1230 */       AudioFileWriter audioFileWriter = list.get(b);
/* 1231 */       if (audioFileWriter.isFileTypeSupported(paramType)) {
/* 1232 */         return true;
/*      */       }
/*      */     } 
/* 1235 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AudioFileFormat.Type[] getAudioFileTypes(AudioInputStream paramAudioInputStream) {
/* 1248 */     List<AudioFileWriter> list = getAudioFileWriters();
/* 1249 */     HashSet<AudioFileFormat.Type> hashSet = new HashSet();
/*      */     
/* 1251 */     for (byte b = 0; b < list.size(); b++) {
/* 1252 */       AudioFileWriter audioFileWriter = list.get(b);
/* 1253 */       AudioFileFormat.Type[] arrayOfType = audioFileWriter.getAudioFileTypes(paramAudioInputStream);
/* 1254 */       for (byte b1 = 0; b1 < arrayOfType.length; b1++) {
/* 1255 */         hashSet.add(arrayOfType[b1]);
/*      */       }
/*      */     } 
/* 1258 */     return hashSet
/* 1259 */       .<AudioFileFormat.Type>toArray(new AudioFileFormat.Type[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFileTypeSupported(AudioFileFormat.Type paramType, AudioInputStream paramAudioInputStream) {
/* 1275 */     List<AudioFileWriter> list = getAudioFileWriters();
/*      */     
/* 1277 */     for (byte b = 0; b < list.size(); b++) {
/* 1278 */       AudioFileWriter audioFileWriter = list.get(b);
/* 1279 */       if (audioFileWriter.isFileTypeSupported(paramType, paramAudioInputStream)) {
/* 1280 */         return true;
/*      */       }
/*      */     } 
/* 1283 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, OutputStream paramOutputStream) throws IOException {
/* 1309 */     List<AudioFileWriter> list = getAudioFileWriters();
/* 1310 */     int i = 0;
/* 1311 */     boolean bool = false;
/*      */     
/* 1313 */     for (byte b = 0; b < list.size(); b++) {
/* 1314 */       AudioFileWriter audioFileWriter = list.get(b);
/*      */       try {
/* 1316 */         i = audioFileWriter.write(paramAudioInputStream, paramType, paramOutputStream);
/* 1317 */         bool = true;
/*      */         break;
/* 1319 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1324 */     if (!bool) {
/* 1325 */       throw new IllegalArgumentException("could not write audio file: file type not supported: " + paramType);
/*      */     }
/* 1327 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int write(AudioInputStream paramAudioInputStream, AudioFileFormat.Type paramType, File paramFile) throws IOException {
/* 1349 */     List<AudioFileWriter> list = getAudioFileWriters();
/* 1350 */     int i = 0;
/* 1351 */     boolean bool = false;
/*      */     
/* 1353 */     for (byte b = 0; b < list.size(); b++) {
/* 1354 */       AudioFileWriter audioFileWriter = list.get(b);
/*      */       try {
/* 1356 */         i = audioFileWriter.write(paramAudioInputStream, paramType, paramFile);
/* 1357 */         bool = true;
/*      */         break;
/* 1359 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1364 */     if (!bool) {
/* 1365 */       throw new IllegalArgumentException("could not write audio file: file type not supported: " + paramType);
/*      */     }
/* 1367 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getMixerProviders() {
/* 1378 */     return getProviders(MixerProvider.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getFormatConversionProviders() {
/* 1393 */     return getProviders(FormatConversionProvider.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getAudioFileReaders() {
/* 1406 */     return getProviders(AudioFileReader.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getAudioFileWriters() {
/* 1418 */     return getProviders(AudioFileWriter.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Mixer getDefaultMixer(List paramList, Line.Info paramInfo) {
/* 1432 */     Class<?> clazz = paramInfo.getLineClass();
/* 1433 */     String str1 = JDK13Services.getDefaultProviderClassName(clazz);
/* 1434 */     String str2 = JDK13Services.getDefaultInstanceName(clazz);
/*      */ 
/*      */     
/* 1437 */     if (str1 != null) {
/* 1438 */       MixerProvider mixerProvider = getNamedProvider(str1, paramList);
/* 1439 */       if (mixerProvider != null) {
/* 1440 */         if (str2 != null) {
/* 1441 */           Mixer mixer = getNamedMixer(str2, mixerProvider, paramInfo);
/* 1442 */           if (mixer != null) {
/* 1443 */             return mixer;
/*      */           }
/*      */         } else {
/* 1446 */           Mixer mixer = getFirstMixer(mixerProvider, paramInfo, false);
/*      */           
/* 1448 */           if (mixer != null) {
/* 1449 */             return mixer;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1459 */     if (str2 != null) {
/* 1460 */       Mixer mixer = getNamedMixer(str2, paramList, paramInfo);
/* 1461 */       if (mixer != null) {
/* 1462 */         return mixer;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1469 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MixerProvider getNamedProvider(String paramString, List<MixerProvider> paramList) {
/* 1485 */     for (byte b = 0; b < paramList.size(); b++) {
/* 1486 */       MixerProvider mixerProvider = paramList.get(b);
/* 1487 */       if (mixerProvider.getClass().getName().equals(paramString)) {
/* 1488 */         return mixerProvider;
/*      */       }
/*      */     } 
/* 1491 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Mixer getNamedMixer(String paramString, MixerProvider paramMixerProvider, Line.Info paramInfo) {
/* 1507 */     Mixer.Info[] arrayOfInfo = paramMixerProvider.getMixerInfo();
/* 1508 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 1509 */       if (arrayOfInfo[b].getName().equals(paramString)) {
/* 1510 */         Mixer mixer = paramMixerProvider.getMixer(arrayOfInfo[b]);
/* 1511 */         if (isAppropriateMixer(mixer, paramInfo, false)) {
/* 1512 */           return mixer;
/*      */         }
/*      */       } 
/*      */     } 
/* 1516 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Mixer getNamedMixer(String paramString, List<MixerProvider> paramList, Line.Info paramInfo) {
/* 1531 */     for (byte b = 0; b < paramList.size(); b++) {
/* 1532 */       MixerProvider mixerProvider = paramList.get(b);
/* 1533 */       Mixer mixer = getNamedMixer(paramString, mixerProvider, paramInfo);
/* 1534 */       if (mixer != null) {
/* 1535 */         return mixer;
/*      */       }
/*      */     } 
/* 1538 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Mixer getFirstMixer(MixerProvider paramMixerProvider, Line.Info paramInfo, boolean paramBoolean) {
/* 1555 */     Mixer.Info[] arrayOfInfo = paramMixerProvider.getMixerInfo();
/* 1556 */     for (byte b = 0; b < arrayOfInfo.length; b++) {
/* 1557 */       Mixer mixer = paramMixerProvider.getMixer(arrayOfInfo[b]);
/* 1558 */       if (isAppropriateMixer(mixer, paramInfo, paramBoolean)) {
/* 1559 */         return mixer;
/*      */       }
/*      */     } 
/* 1562 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAppropriateMixer(Mixer paramMixer, Line.Info paramInfo, boolean paramBoolean) {
/* 1578 */     if (!paramMixer.isLineSupported(paramInfo)) {
/* 1579 */       return false;
/*      */     }
/* 1581 */     Class<?> clazz = paramInfo.getLineClass();
/* 1582 */     if (paramBoolean && (SourceDataLine.class
/* 1583 */       .isAssignableFrom(clazz) || Clip.class
/* 1584 */       .isAssignableFrom(clazz))) {
/* 1585 */       int i = paramMixer.getMaxLines(paramInfo);
/* 1586 */       return (i == -1 || i > 1);
/*      */     } 
/* 1588 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getMixerInfoList() {
/* 1597 */     List list = getMixerProviders();
/* 1598 */     return getMixerInfoList(list);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getMixerInfoList(List<MixerProvider> paramList) {
/* 1606 */     ArrayList<Mixer.Info> arrayList = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1611 */     for (byte b = 0; b < paramList.size(); b++) {
/*      */       
/* 1613 */       Mixer.Info[] arrayOfInfo = ((MixerProvider)paramList.get(b)).getMixerInfo();
/*      */       
/* 1615 */       for (byte b1 = 0; b1 < arrayOfInfo.length; b1++) {
/* 1616 */         arrayList.add(arrayOfInfo[b1]);
/*      */       }
/*      */     } 
/*      */     
/* 1620 */     return arrayList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List getProviders(Class<?> paramClass) {
/* 1631 */     return JDK13Services.getProviders(paramClass);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/AudioSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */