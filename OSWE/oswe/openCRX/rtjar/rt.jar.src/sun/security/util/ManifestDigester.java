/*     */ package sun.security.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManifestDigester
/*     */ {
/*     */   public static final String MF_MAIN_ATTRS = "Manifest-Main-Attributes";
/*     */   private byte[] rawBytes;
/*     */   private HashMap<String, Entry> entries;
/*     */   
/*     */   static class Position
/*     */   {
/*     */     int endOfFirstLine;
/*     */     int endOfSection;
/*     */     int startOfNext;
/*     */   }
/*     */   
/*     */   private boolean findSection(int paramInt, Position paramPosition) {
/*  70 */     int i = paramInt, j = this.rawBytes.length;
/*  71 */     int k = paramInt;
/*     */     
/*  73 */     boolean bool = true;
/*     */     
/*  75 */     paramPosition.endOfFirstLine = -1;
/*     */     
/*  77 */     while (i < j) {
/*  78 */       byte b = this.rawBytes[i];
/*  79 */       switch (b) {
/*     */         case 13:
/*  81 */           if (paramPosition.endOfFirstLine == -1)
/*  82 */             paramPosition.endOfFirstLine = i - 1; 
/*  83 */           if (i < j && this.rawBytes[i + 1] == 10) {
/*  84 */             i++;
/*     */           }
/*     */         case 10:
/*  87 */           if (paramPosition.endOfFirstLine == -1)
/*  88 */             paramPosition.endOfFirstLine = i - 1; 
/*  89 */           if (bool || i == j - 1) {
/*  90 */             if (i == j - 1) {
/*  91 */               paramPosition.endOfSection = i;
/*     */             } else {
/*  93 */               paramPosition.endOfSection = k;
/*  94 */             }  paramPosition.startOfNext = i + 1;
/*  95 */             return true;
/*     */           } 
/*     */ 
/*     */           
/*  99 */           k = i;
/* 100 */           bool = true;
/*     */           break;
/*     */         
/*     */         default:
/* 104 */           bool = false;
/*     */           break;
/*     */       } 
/* 107 */       i++;
/*     */     } 
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ManifestDigester(byte[] paramArrayOfbyte) {
/* 114 */     this.rawBytes = paramArrayOfbyte;
/* 115 */     this.entries = new HashMap<>();
/*     */     
/* 117 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/* 119 */     Position position = new Position();
/*     */     
/* 121 */     if (!findSection(0, position)) {
/*     */       return;
/*     */     }
/*     */     
/* 125 */     this.entries.put("Manifest-Main-Attributes", (new Entry()).addSection(new Section(0, position.endOfSection + 1, position.startOfNext, this.rawBytes)));
/*     */ 
/*     */     
/* 128 */     int i = position.startOfNext;
/* 129 */     while (findSection(i, position)) {
/* 130 */       int j = position.endOfFirstLine - i + 1;
/* 131 */       int k = position.endOfSection - i + 1;
/* 132 */       int m = position.startOfNext - i;
/*     */       
/* 134 */       if (j > 6 && 
/* 135 */         isNameAttr(paramArrayOfbyte, i)) {
/* 136 */         StringBuilder stringBuilder = new StringBuilder(k);
/*     */         
/*     */         try {
/* 139 */           stringBuilder.append(new String(paramArrayOfbyte, i + 6, j - 6, "UTF8"));
/*     */ 
/*     */           
/* 142 */           int n = i + j;
/* 143 */           if (n - i < k) {
/* 144 */             if (paramArrayOfbyte[n] == 13) {
/* 145 */               n += 2;
/*     */             } else {
/* 147 */               n++;
/*     */             } 
/*     */           }
/*     */           
/* 151 */           while (n - i < k && 
/* 152 */             paramArrayOfbyte[n++] == 32) {
/*     */             
/* 154 */             int i2, i1 = n;
/* 155 */             while (n - i < k && paramArrayOfbyte[n++] != 10);
/*     */             
/* 157 */             if (paramArrayOfbyte[n - 1] != 10) {
/*     */               return;
/*     */             }
/* 160 */             if (paramArrayOfbyte[n - 2] == 13) {
/* 161 */               i2 = n - i1 - 2;
/*     */             } else {
/* 163 */               i2 = n - i1 - 1;
/*     */             } 
/* 165 */             stringBuilder.append(new String(paramArrayOfbyte, i1, i2, "UTF8"));
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 172 */           Entry entry = this.entries.get(stringBuilder.toString());
/* 173 */           if (entry == null) {
/* 174 */             this.entries.put(stringBuilder.toString(), (new Entry())
/* 175 */                 .addSection(new Section(i, k, m, this.rawBytes)));
/*     */           } else {
/*     */             
/* 178 */             entry.addSection(new Section(i, k, m, this.rawBytes));
/*     */           }
/*     */         
/*     */         }
/* 182 */         catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 183 */           throw new IllegalStateException("UTF8 not available on platform");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 188 */       i = position.startOfNext;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isNameAttr(byte[] paramArrayOfbyte, int paramInt) {
/* 194 */     return ((paramArrayOfbyte[paramInt] == 78 || paramArrayOfbyte[paramInt] == 110) && (paramArrayOfbyte[paramInt + 1] == 97 || paramArrayOfbyte[paramInt + 1] == 65) && (paramArrayOfbyte[paramInt + 2] == 109 || paramArrayOfbyte[paramInt + 2] == 77) && (paramArrayOfbyte[paramInt + 3] == 101 || paramArrayOfbyte[paramInt + 3] == 69) && paramArrayOfbyte[paramInt + 4] == 58 && paramArrayOfbyte[paramInt + 5] == 32);
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
/*     */   public static class Entry
/*     */   {
/* 208 */     private List<ManifestDigester.Section> sections = new ArrayList<>();
/*     */     
/*     */     boolean oldStyle;
/*     */     
/*     */     private Entry addSection(ManifestDigester.Section param1Section) {
/* 213 */       this.sections.add(param1Section);
/* 214 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] digest(MessageDigest param1MessageDigest) {
/* 219 */       param1MessageDigest.reset();
/* 220 */       for (ManifestDigester.Section section : this.sections) {
/* 221 */         if (this.oldStyle) {
/* 222 */           ManifestDigester.Section.doOldStyle(param1MessageDigest, section.rawBytes, section.offset, section.lengthWithBlankLine); continue;
/*     */         } 
/* 224 */         param1MessageDigest.update(section.rawBytes, section.offset, section.lengthWithBlankLine);
/*     */       } 
/*     */       
/* 227 */       return param1MessageDigest.digest();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] digestWorkaround(MessageDigest param1MessageDigest) {
/* 234 */       param1MessageDigest.reset();
/* 235 */       for (ManifestDigester.Section section : this.sections) {
/* 236 */         param1MessageDigest.update(section.rawBytes, section.offset, section.length);
/*     */       }
/* 238 */       return param1MessageDigest.digest();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Section
/*     */   {
/*     */     int offset;
/*     */     int length;
/*     */     int lengthWithBlankLine;
/*     */     byte[] rawBytes;
/*     */     
/*     */     public Section(int param1Int1, int param1Int2, int param1Int3, byte[] param1ArrayOfbyte) {
/* 251 */       this.offset = param1Int1;
/* 252 */       this.length = param1Int2;
/* 253 */       this.lengthWithBlankLine = param1Int3;
/* 254 */       this.rawBytes = param1ArrayOfbyte;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static void doOldStyle(MessageDigest param1MessageDigest, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 267 */       int i = param1Int1;
/* 268 */       int j = param1Int1;
/* 269 */       int k = param1Int1 + param1Int2;
/* 270 */       byte b = -1;
/* 271 */       while (i < k) {
/* 272 */         if (param1ArrayOfbyte[i] == 13 && b == 32) {
/* 273 */           param1MessageDigest.update(param1ArrayOfbyte, j, i - j - 1);
/* 274 */           j = i;
/*     */         } 
/* 276 */         b = param1ArrayOfbyte[i];
/* 277 */         i++;
/*     */       } 
/* 279 */       param1MessageDigest.update(param1ArrayOfbyte, j, i - j);
/*     */     }
/*     */   }
/*     */   
/*     */   public Entry get(String paramString, boolean paramBoolean) {
/* 284 */     Entry entry = this.entries.get(paramString);
/* 285 */     if (entry != null)
/* 286 */       entry.oldStyle = paramBoolean; 
/* 287 */     return entry;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] manifestDigest(MessageDigest paramMessageDigest) {
/* 292 */     paramMessageDigest.reset();
/* 293 */     paramMessageDigest.update(this.rawBytes, 0, this.rawBytes.length);
/* 294 */     return paramMessageDigest.digest();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/util/ManifestDigester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */