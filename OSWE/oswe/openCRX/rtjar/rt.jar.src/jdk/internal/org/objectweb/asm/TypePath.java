/*     */ package jdk.internal.org.objectweb.asm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypePath
/*     */ {
/*     */   public static final int ARRAY_ELEMENT = 0;
/*     */   public static final int INNER_TYPE = 1;
/*     */   public static final int WILDCARD_BOUND = 2;
/*     */   public static final int TYPE_ARGUMENT = 3;
/*     */   byte[] b;
/*     */   int offset;
/*     */   
/*     */   TypePath(byte[] paramArrayOfbyte, int paramInt) {
/* 114 */     this.b = paramArrayOfbyte;
/* 115 */     this.offset = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLength() {
/* 124 */     return this.b[this.offset];
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
/*     */   public int getStep(int paramInt) {
/* 137 */     return this.b[this.offset + 2 * paramInt + 1];
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
/*     */   public int getStepArgument(int paramInt) {
/* 151 */     return this.b[this.offset + 2 * paramInt + 2];
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
/*     */   public static TypePath fromString(String paramString) {
/* 164 */     if (paramString == null || paramString.length() == 0) {
/* 165 */       return null;
/*     */     }
/* 167 */     int i = paramString.length();
/* 168 */     ByteVector byteVector = new ByteVector(i);
/* 169 */     byteVector.putByte(0);
/* 170 */     for (byte b = 0; b < i; ) {
/* 171 */       char c = paramString.charAt(b++);
/* 172 */       if (c == '[') {
/* 173 */         byteVector.put11(0, 0); continue;
/* 174 */       }  if (c == '.') {
/* 175 */         byteVector.put11(1, 0); continue;
/* 176 */       }  if (c == '*') {
/* 177 */         byteVector.put11(2, 0); continue;
/* 178 */       }  if (c >= '0' && c <= '9') {
/* 179 */         int j = c - 48;
/* 180 */         while (b < i && (c = paramString.charAt(b)) >= '0' && c <= '9') {
/* 181 */           j = j * 10 + c - 48;
/* 182 */           b++;
/*     */         } 
/* 184 */         if (b < i && paramString.charAt(b) == ';') {
/* 185 */           b++;
/*     */         }
/* 187 */         byteVector.put11(3, j);
/*     */       } 
/*     */     } 
/* 190 */     byteVector.data[0] = (byte)(byteVector.length / 2);
/* 191 */     return new TypePath(byteVector.data, 0);
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
/*     */   public String toString() {
/* 203 */     int i = getLength();
/* 204 */     StringBuilder stringBuilder = new StringBuilder(i * 2);
/* 205 */     for (byte b = 0; b < i; b++) {
/* 206 */       switch (getStep(b)) {
/*     */         case 0:
/* 208 */           stringBuilder.append('[');
/*     */           break;
/*     */         case 1:
/* 211 */           stringBuilder.append('.');
/*     */           break;
/*     */         case 2:
/* 214 */           stringBuilder.append('*');
/*     */           break;
/*     */         case 3:
/* 217 */           stringBuilder.append(getStepArgument(b)).append(';');
/*     */           break;
/*     */         default:
/* 220 */           stringBuilder.append('_'); break;
/*     */       } 
/*     */     } 
/* 223 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/TypePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */