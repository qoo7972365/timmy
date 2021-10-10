/*     */ package com.sun.media.sound;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Arrays;
/*     */ import javax.sound.midi.Patch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SoftTuning
/*     */ {
/*  40 */   private String name = null;
/*  41 */   private final double[] tuning = new double[128];
/*  42 */   private Patch patch = null;
/*     */   
/*     */   public SoftTuning() {
/*  45 */     this.name = "12-TET";
/*  46 */     for (byte b = 0; b < this.tuning.length; b++)
/*  47 */       this.tuning[b] = (b * 100); 
/*     */   }
/*     */   
/*     */   public SoftTuning(byte[] paramArrayOfbyte) {
/*  51 */     for (byte b = 0; b < this.tuning.length; b++)
/*  52 */       this.tuning[b] = (b * 100); 
/*  53 */     load(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   public SoftTuning(Patch paramPatch) {
/*  57 */     this.patch = paramPatch;
/*  58 */     this.name = "12-TET";
/*  59 */     for (byte b = 0; b < this.tuning.length; b++)
/*  60 */       this.tuning[b] = (b * 100); 
/*     */   }
/*     */   
/*     */   public SoftTuning(Patch paramPatch, byte[] paramArrayOfbyte) {
/*  64 */     this.patch = paramPatch;
/*  65 */     for (byte b = 0; b < this.tuning.length; b++)
/*  66 */       this.tuning[b] = (b * 100); 
/*  67 */     load(paramArrayOfbyte);
/*     */   }
/*     */   
/*     */   private boolean checksumOK(byte[] paramArrayOfbyte) {
/*  71 */     int i = paramArrayOfbyte[1] & 0xFF;
/*  72 */     for (byte b = 2; b < paramArrayOfbyte.length - 2; b++)
/*  73 */       i ^= paramArrayOfbyte[b] & 0xFF; 
/*  74 */     return ((paramArrayOfbyte[paramArrayOfbyte.length - 2] & 0xFF) == (i & 0x7F));
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
/*     */   public void load(byte[] paramArrayOfbyte) {
/*  90 */     if ((paramArrayOfbyte[1] & 0xFF) == 126 || (paramArrayOfbyte[1] & 0xFF) == 127) {
/*  91 */       int j, m, arrayOfInt1[]; double[] arrayOfDouble1; int k; byte b1; int n, arrayOfInt2[]; double[] arrayOfDouble2; byte b2; int i = paramArrayOfbyte[3] & 0xFF;
/*  92 */       switch (i) {
/*     */         case 8:
/*  94 */           j = paramArrayOfbyte[4] & 0xFF;
/*  95 */           switch (j) {
/*     */             
/*     */             case 1:
/*     */               
/*     */               try {
/*     */ 
/*     */                 
/* 102 */                 this.name = new String(paramArrayOfbyte, 6, 16, "ascii");
/* 103 */               } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 104 */                 this.name = null;
/*     */               } 
/* 106 */               m = 22;
/* 107 */               for (b1 = 0; b1 < ''; b1++) {
/* 108 */                 int i1 = paramArrayOfbyte[m++] & 0xFF;
/* 109 */                 int i2 = paramArrayOfbyte[m++] & 0xFF;
/* 110 */                 int i3 = paramArrayOfbyte[m++] & 0xFF;
/* 111 */                 if (i1 != 127 || i2 != 127 || i3 != 127) {
/* 112 */                   this.tuning[b1] = 100.0D * (i1 * 16384 + i2 * 128 + i3) / 16384.0D;
/*     */                 }
/*     */               } 
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 2:
/* 120 */               m = paramArrayOfbyte[6] & 0xFF;
/* 121 */               b1 = 7;
/* 122 */               for (n = 0; n < m; n++) {
/* 123 */                 int i1 = paramArrayOfbyte[b1++] & 0xFF;
/* 124 */                 int i2 = paramArrayOfbyte[b1++] & 0xFF;
/* 125 */                 int i3 = paramArrayOfbyte[b1++] & 0xFF;
/* 126 */                 int i4 = paramArrayOfbyte[b1++] & 0xFF;
/* 127 */                 if (i2 != 127 || i3 != 127 || i4 != 127) {
/* 128 */                   this.tuning[i1] = 100.0D * (i2 * 16384 + i3 * 128 + i4) / 16384.0D;
/*     */                 }
/*     */               } 
/*     */               break;
/*     */ 
/*     */             
/*     */             case 4:
/* 135 */               if (!checksumOK(paramArrayOfbyte))
/*     */                 break; 
/*     */               try {
/* 138 */                 this.name = new String(paramArrayOfbyte, 7, 16, "ascii");
/* 139 */               } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 140 */                 this.name = null;
/*     */               } 
/* 142 */               m = 23;
/* 143 */               for (b1 = 0; b1 < ''; b1++) {
/* 144 */                 n = paramArrayOfbyte[m++] & 0xFF;
/* 145 */                 int i1 = paramArrayOfbyte[m++] & 0xFF;
/* 146 */                 int i2 = paramArrayOfbyte[m++] & 0xFF;
/* 147 */                 if (n != 127 || i1 != 127 || i2 != 127) {
/* 148 */                   this.tuning[b1] = 100.0D * (n * 16384 + i1 * 128 + i2) / 16384.0D;
/*     */                 }
/*     */               } 
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 5:
/* 156 */               if (!checksumOK(paramArrayOfbyte))
/*     */                 break; 
/*     */               try {
/* 159 */                 this.name = new String(paramArrayOfbyte, 7, 16, "ascii");
/* 160 */               } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 161 */                 this.name = null;
/*     */               } 
/* 163 */               arrayOfInt1 = new int[12];
/* 164 */               for (b1 = 0; b1 < 12; b1++)
/* 165 */                 arrayOfInt1[b1] = (paramArrayOfbyte[b1 + 23] & 0xFF) - 64; 
/* 166 */               for (b1 = 0; b1 < this.tuning.length; b1++) {
/* 167 */                 this.tuning[b1] = (b1 * 100 + arrayOfInt1[b1 % 12]);
/*     */               }
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 6:
/* 174 */               if (!checksumOK(paramArrayOfbyte))
/*     */                 break; 
/*     */               try {
/* 177 */                 this.name = new String(paramArrayOfbyte, 7, 16, "ascii");
/* 178 */               } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 179 */                 this.name = null;
/*     */               } 
/* 181 */               arrayOfDouble1 = new double[12];
/* 182 */               for (b1 = 0; b1 < 12; b1++) {
/* 183 */                 n = (paramArrayOfbyte[b1 * 2 + 23] & 0xFF) * 128 + (paramArrayOfbyte[b1 * 2 + 24] & 0xFF);
/*     */                 
/* 185 */                 arrayOfDouble1[b1] = (n / 8192.0D - 1.0D) * 100.0D;
/*     */               } 
/* 187 */               for (b1 = 0; b1 < this.tuning.length; b1++) {
/* 188 */                 this.tuning[b1] = (b1 * 100) + arrayOfDouble1[b1 % 12];
/*     */               }
/*     */               break;
/*     */ 
/*     */             
/*     */             case 7:
/* 194 */               k = paramArrayOfbyte[7] & 0xFF;
/* 195 */               b1 = 8;
/* 196 */               for (n = 0; n < k; n++) {
/* 197 */                 int i1 = paramArrayOfbyte[b1++] & 0xFF;
/* 198 */                 int i2 = paramArrayOfbyte[b1++] & 0xFF;
/* 199 */                 int i3 = paramArrayOfbyte[b1++] & 0xFF;
/* 200 */                 int i4 = paramArrayOfbyte[b1++] & 0xFF;
/* 201 */                 if (i2 != 127 || i3 != 127 || i4 != 127) {
/* 202 */                   this.tuning[i1] = 100.0D * (i2 * 16384 + i3 * 128 + i4) / 16384.0D;
/*     */                 }
/*     */               } 
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 8:
/* 210 */               arrayOfInt2 = new int[12];
/* 211 */               for (b2 = 0; b2 < 12; b2++)
/* 212 */                 arrayOfInt2[b2] = (paramArrayOfbyte[b2 + 8] & 0xFF) - 64; 
/* 213 */               for (b2 = 0; b2 < this.tuning.length; b2++) {
/* 214 */                 this.tuning[b2] = (b2 * 100 + arrayOfInt2[b2 % 12]);
/*     */               }
/*     */               break;
/*     */ 
/*     */ 
/*     */             
/*     */             case 9:
/* 221 */               arrayOfDouble2 = new double[12];
/* 222 */               for (b2 = 0; b2 < 12; b2++) {
/* 223 */                 int i1 = (paramArrayOfbyte[b2 * 2 + 8] & 0xFF) * 128 + (paramArrayOfbyte[b2 * 2 + 9] & 0xFF);
/*     */                 
/* 225 */                 arrayOfDouble2[b2] = (i1 / 8192.0D - 1.0D) * 100.0D;
/*     */               } 
/* 227 */               for (b2 = 0; b2 < this.tuning.length; b2++) {
/* 228 */                 this.tuning[b2] = (b2 * 100) + arrayOfDouble2[b2 % 12];
/*     */               }
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getTuning() {
/* 241 */     return Arrays.copyOf(this.tuning, this.tuning.length);
/*     */   }
/*     */   
/*     */   public double getTuning(int paramInt) {
/* 245 */     return this.tuning[paramInt];
/*     */   }
/*     */   
/*     */   public Patch getPatch() {
/* 249 */     return this.patch;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 253 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/* 257 */     this.name = paramString;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/media/sound/SoftTuning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */