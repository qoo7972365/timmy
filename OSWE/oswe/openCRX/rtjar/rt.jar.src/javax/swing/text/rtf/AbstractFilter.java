/*     */ package javax.swing.text.rtf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractFilter
/*     */   extends OutputStream
/*     */ {
/*     */   protected char[] translationTable;
/*     */   protected boolean[] specialsTable;
/*     */   static final char[] latin1TranslationTable;
/*  67 */   static final boolean[] noSpecialsTable = new boolean[256]; static { byte b;
/*  68 */     for (b = 0; b < 'Ā'; b++)
/*  69 */       noSpecialsTable[b] = false;  }
/*     */   
/*  71 */   static final boolean[] allSpecialsTable = new boolean[256]; static {
/*  72 */     for (b = 0; b < 'Ā'; b++) {
/*  73 */       allSpecialsTable[b] = true;
/*     */     }
/*  75 */     latin1TranslationTable = new char[256];
/*  76 */     for (b = 0; b < 'Ā'; b++) {
/*  77 */       latin1TranslationTable[b] = (char)b;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromStream(InputStream paramInputStream) throws IOException {
/*  99 */     byte[] arrayOfByte = new byte[16384];
/*     */     
/*     */     while (true) {
/* 102 */       int i = paramInputStream.read(arrayOfByte);
/* 103 */       if (i < 0) {
/*     */         break;
/*     */       }
/* 106 */       write(arrayOfByte, 0, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromReader(Reader paramReader) throws IOException {
/* 116 */     char[] arrayOfChar = new char[2048];
/*     */     
/*     */     while (true) {
/* 119 */       int i = paramReader.read(arrayOfChar);
/* 120 */       if (i < 0)
/*     */         break; 
/* 122 */       for (byte b = 0; b < i; b++) {
/* 123 */         write(arrayOfChar[b]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractFilter() {
/* 130 */     this.translationTable = latin1TranslationTable;
/* 131 */     this.specialsTable = noSpecialsTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int paramInt) throws IOException {
/* 141 */     if (paramInt < 0)
/* 142 */       paramInt += 256; 
/* 143 */     if (this.specialsTable[paramInt]) {
/* 144 */       writeSpecial(paramInt);
/*     */     } else {
/* 146 */       char c = this.translationTable[paramInt];
/* 147 */       if (c != '\000') {
/* 148 */         write(c);
/*     */       }
/*     */     } 
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 163 */     StringBuilder stringBuilder = null;
/* 164 */     while (paramInt2 > 0) {
/* 165 */       short s = (short)paramArrayOfbyte[paramInt1];
/*     */ 
/*     */       
/* 168 */       if (s < 0) {
/* 169 */         s = (short)(s + 256);
/*     */       }
/* 171 */       if (this.specialsTable[s]) {
/* 172 */         if (stringBuilder != null) {
/* 173 */           write(stringBuilder.toString());
/* 174 */           stringBuilder = null;
/*     */         } 
/* 176 */         writeSpecial(s);
/*     */       } else {
/* 178 */         char c = this.translationTable[s];
/* 179 */         if (c != '\000') {
/* 180 */           if (stringBuilder == null)
/* 181 */             stringBuilder = new StringBuilder(); 
/* 182 */           stringBuilder.append(c);
/*     */         } 
/*     */       } 
/*     */       
/* 186 */       paramInt2--;
/* 187 */       paramInt1++;
/*     */     } 
/*     */     
/* 190 */     if (stringBuilder != null) {
/* 191 */       write(stringBuilder.toString());
/*     */     }
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
/*     */   public void write(String paramString) throws IOException {
/* 207 */     int i = paramString.length();
/* 208 */     for (byte b = 0; b < i; b++)
/* 209 */       write(paramString.charAt(b)); 
/*     */   }
/*     */   
/*     */   protected abstract void write(char paramChar) throws IOException;
/*     */   
/*     */   protected abstract void writeSpecial(int paramInt) throws IOException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/rtf/AbstractFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */