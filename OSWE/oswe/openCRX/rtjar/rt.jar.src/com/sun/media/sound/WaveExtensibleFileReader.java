/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.sound.sampled.AudioFileFormat;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ import javax.sound.sampled.UnsupportedAudioFileException;
/*     */ import javax.sound.sampled.spi.AudioFileReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WaveExtensibleFileReader
/*     */   extends AudioFileReader
/*     */ {
/*     */   private static class GUID
/*     */   {
/*     */     long i1;
/*     */     int s1;
/*     */     int s2;
/*     */     int x1;
/*     */     int x2;
/*     */     int x3;
/*     */     int x4;
/*     */     int x5;
/*     */     int x6;
/*     */     int x7;
/*     */     int x8;
/*     */     
/*     */     private GUID() {}
/*     */     
/*     */     GUID(long param1Long, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8, int param1Int9, int param1Int10) {
/*  79 */       this.i1 = param1Long;
/*  80 */       this.s1 = param1Int1;
/*  81 */       this.s2 = param1Int2;
/*  82 */       this.x1 = param1Int3;
/*  83 */       this.x2 = param1Int4;
/*  84 */       this.x3 = param1Int5;
/*  85 */       this.x4 = param1Int6;
/*  86 */       this.x5 = param1Int7;
/*  87 */       this.x6 = param1Int8;
/*  88 */       this.x7 = param1Int9;
/*  89 */       this.x8 = param1Int10;
/*     */     }
/*     */     
/*     */     public static GUID read(RIFFReader param1RIFFReader) throws IOException {
/*  93 */       GUID gUID = new GUID();
/*  94 */       gUID.i1 = param1RIFFReader.readUnsignedInt();
/*  95 */       gUID.s1 = param1RIFFReader.readUnsignedShort();
/*  96 */       gUID.s2 = param1RIFFReader.readUnsignedShort();
/*  97 */       gUID.x1 = param1RIFFReader.readUnsignedByte();
/*  98 */       gUID.x2 = param1RIFFReader.readUnsignedByte();
/*  99 */       gUID.x3 = param1RIFFReader.readUnsignedByte();
/* 100 */       gUID.x4 = param1RIFFReader.readUnsignedByte();
/* 101 */       gUID.x5 = param1RIFFReader.readUnsignedByte();
/* 102 */       gUID.x6 = param1RIFFReader.readUnsignedByte();
/* 103 */       gUID.x7 = param1RIFFReader.readUnsignedByte();
/* 104 */       gUID.x8 = param1RIFFReader.readUnsignedByte();
/* 105 */       return gUID;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 109 */       return (int)this.i1;
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 113 */       if (!(param1Object instanceof GUID))
/* 114 */         return false; 
/* 115 */       GUID gUID = (GUID)param1Object;
/* 116 */       if (this.i1 != gUID.i1)
/* 117 */         return false; 
/* 118 */       if (this.s1 != gUID.s1)
/* 119 */         return false; 
/* 120 */       if (this.s2 != gUID.s2)
/* 121 */         return false; 
/* 122 */       if (this.x1 != gUID.x1)
/* 123 */         return false; 
/* 124 */       if (this.x2 != gUID.x2)
/* 125 */         return false; 
/* 126 */       if (this.x3 != gUID.x3)
/* 127 */         return false; 
/* 128 */       if (this.x4 != gUID.x4)
/* 129 */         return false; 
/* 130 */       if (this.x5 != gUID.x5)
/* 131 */         return false; 
/* 132 */       if (this.x6 != gUID.x6)
/* 133 */         return false; 
/* 134 */       if (this.x7 != gUID.x7)
/* 135 */         return false; 
/* 136 */       if (this.x8 != gUID.x8)
/* 137 */         return false; 
/* 138 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 143 */   private static final String[] channelnames = new String[] { "FL", "FR", "FC", "LF", "BL", "BR", "FLC", "FLR", "BC", "SL", "SR", "TC", "TFL", "TFC", "TFR", "TBL", "TBC", "TBR" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   private static final String[] allchannelnames = new String[] { "w1", "w2", "w3", "w4", "w5", "w6", "w7", "w8", "w9", "w10", "w11", "w12", "w13", "w14", "w15", "w16", "w17", "w18", "w19", "w20", "w21", "w22", "w23", "w24", "w25", "w26", "w27", "w28", "w29", "w30", "w31", "w32", "w33", "w34", "w35", "w36", "w37", "w38", "w39", "w40", "w41", "w42", "w43", "w44", "w45", "w46", "w47", "w48", "w49", "w50", "w51", "w52", "w53", "w54", "w55", "w56", "w57", "w58", "w59", "w60", "w61", "w62", "w63", "w64" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 158 */   private static final GUID SUBTYPE_PCM = new GUID(1L, 0, 16, 128, 0, 0, 170, 0, 56, 155, 113);
/*     */ 
/*     */   
/* 161 */   private static final GUID SUBTYPE_IEEE_FLOAT = new GUID(3L, 0, 16, 128, 0, 0, 170, 0, 56, 155, 113);
/*     */ 
/*     */   
/*     */   private String decodeChannelMask(long paramLong) {
/* 165 */     StringBuffer stringBuffer = new StringBuffer();
/* 166 */     long l = 1L;
/* 167 */     for (byte b = 0; b < allchannelnames.length; b++) {
/* 168 */       if ((paramLong & l) != 0L) {
/* 169 */         if (b < channelnames.length) {
/* 170 */           stringBuffer.append(channelnames[b] + " ");
/*     */         } else {
/* 172 */           stringBuffer.append(allchannelnames[b] + " ");
/*     */         } 
/*     */       }
/* 175 */       l *= 2L;
/*     */     } 
/* 177 */     if (stringBuffer.length() == 0)
/* 178 */       return null; 
/* 179 */     return stringBuffer.substring(0, stringBuffer.length() - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/* 186 */     paramInputStream.mark(200);
/*     */     
/*     */     try {
/* 189 */       audioFileFormat = internal_getAudioFileFormat(paramInputStream);
/*     */     } finally {
/* 191 */       paramInputStream.reset();
/*     */     } 
/* 193 */     return audioFileFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioFileFormat internal_getAudioFileFormat(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 199 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/* 200 */     if (!rIFFReader.getFormat().equals("RIFF"))
/* 201 */       throw new UnsupportedAudioFileException(); 
/* 202 */     if (!rIFFReader.getType().equals("WAVE")) {
/* 203 */       throw new UnsupportedAudioFileException();
/*     */     }
/* 205 */     boolean bool1 = false;
/* 206 */     boolean bool2 = false;
/*     */     
/* 208 */     int i = 1;
/* 209 */     long l1 = 1L;
/*     */     
/* 211 */     int j = 1;
/* 212 */     int k = 1;
/* 213 */     int m = 1;
/* 214 */     long l2 = 0L;
/* 215 */     GUID gUID = null;
/*     */     
/* 217 */     while (rIFFReader.hasNextChunk()) {
/* 218 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/*     */       
/* 220 */       if (rIFFReader1.getFormat().equals("fmt ")) {
/* 221 */         bool1 = true;
/*     */         
/* 223 */         int n = rIFFReader1.readUnsignedShort();
/* 224 */         if (n != 65534) {
/* 225 */           throw new UnsupportedAudioFileException();
/*     */         }
/* 227 */         i = rIFFReader1.readUnsignedShort();
/* 228 */         l1 = rIFFReader1.readUnsignedInt();
/* 229 */         rIFFReader1.readUnsignedInt();
/* 230 */         j = rIFFReader1.readUnsignedShort();
/* 231 */         k = rIFFReader1.readUnsignedShort();
/* 232 */         int i1 = rIFFReader1.readUnsignedShort();
/* 233 */         if (i1 != 22)
/* 234 */           throw new UnsupportedAudioFileException(); 
/* 235 */         m = rIFFReader1.readUnsignedShort();
/* 236 */         if (m > k)
/* 237 */           throw new UnsupportedAudioFileException(); 
/* 238 */         l2 = rIFFReader1.readUnsignedInt();
/* 239 */         gUID = GUID.read(rIFFReader1);
/*     */       } 
/*     */       
/* 242 */       if (rIFFReader1.getFormat().equals("data")) {
/* 243 */         bool2 = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 248 */     if (!bool1)
/* 249 */       throw new UnsupportedAudioFileException(); 
/* 250 */     if (!bool2) {
/* 251 */       throw new UnsupportedAudioFileException();
/*     */     }
/* 253 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 254 */     String str = decodeChannelMask(l2);
/* 255 */     if (str != null)
/* 256 */       hashMap.put("channelOrder", str); 
/* 257 */     if (l2 != 0L) {
/* 258 */       hashMap.put("channelMask", Long.valueOf(l2));
/*     */     }
/*     */     
/* 261 */     hashMap.put("validBitsPerSample", Integer.valueOf(m));
/*     */     
/* 263 */     AudioFormat audioFormat = null;
/* 264 */     if (gUID.equals(SUBTYPE_PCM)) {
/* 265 */       if (k == 8) {
/* 266 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, (float)l1, k, i, j, (float)l1, false, (Map)hashMap);
/*     */       }
/*     */       else {
/*     */         
/* 270 */         audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float)l1, k, i, j, (float)l1, false, (Map)hashMap);
/*     */       }
/*     */     
/* 273 */     } else if (gUID.equals(SUBTYPE_IEEE_FLOAT)) {
/* 274 */       audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_FLOAT, (float)l1, k, i, j, (float)l1, false, (Map)hashMap);
/*     */     } else {
/*     */       
/* 277 */       throw new UnsupportedAudioFileException();
/*     */     } 
/* 279 */     return new AudioFileFormat(AudioFileFormat.Type.WAVE, audioFormat, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(InputStream paramInputStream) throws UnsupportedAudioFileException, IOException {
/* 288 */     AudioFileFormat audioFileFormat = getAudioFileFormat(paramInputStream);
/* 289 */     RIFFReader rIFFReader = new RIFFReader(paramInputStream);
/* 290 */     if (!rIFFReader.getFormat().equals("RIFF"))
/* 291 */       throw new UnsupportedAudioFileException(); 
/* 292 */     if (!rIFFReader.getType().equals("WAVE"))
/* 293 */       throw new UnsupportedAudioFileException(); 
/* 294 */     while (rIFFReader.hasNextChunk()) {
/* 295 */       RIFFReader rIFFReader1 = rIFFReader.nextChunk();
/* 296 */       if (rIFFReader1.getFormat().equals("data")) {
/* 297 */         return new AudioInputStream(rIFFReader1, audioFileFormat.getFormat(), rIFFReader1
/* 298 */             .getSize());
/*     */       }
/*     */     } 
/* 301 */     throw new UnsupportedAudioFileException();
/*     */   }
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(URL paramURL) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/* 306 */     InputStream inputStream = paramURL.openStream();
/*     */     
/*     */     try {
/* 309 */       audioFileFormat = getAudioFileFormat(new BufferedInputStream(inputStream));
/*     */     } finally {
/* 311 */       inputStream.close();
/*     */     } 
/* 313 */     return audioFileFormat;
/*     */   }
/*     */   
/*     */   public AudioFileFormat getAudioFileFormat(File paramFile) throws UnsupportedAudioFileException, IOException {
/*     */     AudioFileFormat audioFileFormat;
/* 318 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*     */     
/*     */     try {
/* 321 */       audioFileFormat = getAudioFileFormat(new BufferedInputStream(fileInputStream));
/*     */     } finally {
/* 323 */       fileInputStream.close();
/*     */     } 
/* 325 */     return audioFileFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(URL paramURL) throws UnsupportedAudioFileException, IOException {
/* 330 */     return getAudioInputStream(new BufferedInputStream(paramURL.openStream()));
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioInputStream getAudioInputStream(File paramFile) throws UnsupportedAudioFileException, IOException {
/* 335 */     return getAudioInputStream(new BufferedInputStream(new FileInputStream(paramFile)));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/WaveExtensibleFileReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */