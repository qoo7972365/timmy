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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AnnotationWriter
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   private final ClassWriter cw;
/*     */   private int size;
/*     */   private final boolean named;
/*     */   private final ByteVector bv;
/*     */   private final ByteVector parent;
/*     */   private final int offset;
/*     */   AnnotationWriter next;
/*     */   AnnotationWriter prev;
/*     */   
/*     */   AnnotationWriter(ClassWriter paramClassWriter, boolean paramBoolean, ByteVector paramByteVector1, ByteVector paramByteVector2, int paramInt) {
/* 136 */     super(327680);
/* 137 */     this.cw = paramClassWriter;
/* 138 */     this.named = paramBoolean;
/* 139 */     this.bv = paramByteVector1;
/* 140 */     this.parent = paramByteVector2;
/* 141 */     this.offset = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String paramString, Object paramObject) {
/* 150 */     this.size++;
/* 151 */     if (this.named) {
/* 152 */       this.bv.putShort(this.cw.newUTF8(paramString));
/*     */     }
/* 154 */     if (paramObject instanceof String) {
/* 155 */       this.bv.put12(115, this.cw.newUTF8((String)paramObject));
/* 156 */     } else if (paramObject instanceof Byte) {
/* 157 */       this.bv.put12(66, (this.cw.newInteger(((Byte)paramObject).byteValue())).index);
/* 158 */     } else if (paramObject instanceof Boolean) {
/* 159 */       boolean bool = ((Boolean)paramObject).booleanValue() ? true : false;
/* 160 */       this.bv.put12(90, (this.cw.newInteger(bool)).index);
/* 161 */     } else if (paramObject instanceof Character) {
/* 162 */       this.bv.put12(67, (this.cw.newInteger(((Character)paramObject).charValue())).index);
/* 163 */     } else if (paramObject instanceof Short) {
/* 164 */       this.bv.put12(83, (this.cw.newInteger(((Short)paramObject).shortValue())).index);
/* 165 */     } else if (paramObject instanceof Type) {
/* 166 */       this.bv.put12(99, this.cw.newUTF8(((Type)paramObject).getDescriptor()));
/* 167 */     } else if (paramObject instanceof byte[]) {
/* 168 */       byte[] arrayOfByte = (byte[])paramObject;
/* 169 */       this.bv.put12(91, arrayOfByte.length);
/* 170 */       for (byte b = 0; b < arrayOfByte.length; b++) {
/* 171 */         this.bv.put12(66, (this.cw.newInteger(arrayOfByte[b])).index);
/*     */       }
/* 173 */     } else if (paramObject instanceof boolean[]) {
/* 174 */       boolean[] arrayOfBoolean = (boolean[])paramObject;
/* 175 */       this.bv.put12(91, arrayOfBoolean.length);
/* 176 */       for (byte b = 0; b < arrayOfBoolean.length; b++) {
/* 177 */         this.bv.put12(90, (this.cw.newInteger(arrayOfBoolean[b] ? 1 : 0)).index);
/*     */       }
/* 179 */     } else if (paramObject instanceof short[]) {
/* 180 */       short[] arrayOfShort = (short[])paramObject;
/* 181 */       this.bv.put12(91, arrayOfShort.length);
/* 182 */       for (byte b = 0; b < arrayOfShort.length; b++) {
/* 183 */         this.bv.put12(83, (this.cw.newInteger(arrayOfShort[b])).index);
/*     */       }
/* 185 */     } else if (paramObject instanceof char[]) {
/* 186 */       char[] arrayOfChar = (char[])paramObject;
/* 187 */       this.bv.put12(91, arrayOfChar.length);
/* 188 */       for (byte b = 0; b < arrayOfChar.length; b++) {
/* 189 */         this.bv.put12(67, (this.cw.newInteger(arrayOfChar[b])).index);
/*     */       }
/* 191 */     } else if (paramObject instanceof int[]) {
/* 192 */       int[] arrayOfInt = (int[])paramObject;
/* 193 */       this.bv.put12(91, arrayOfInt.length);
/* 194 */       for (byte b = 0; b < arrayOfInt.length; b++) {
/* 195 */         this.bv.put12(73, (this.cw.newInteger(arrayOfInt[b])).index);
/*     */       }
/* 197 */     } else if (paramObject instanceof long[]) {
/* 198 */       long[] arrayOfLong = (long[])paramObject;
/* 199 */       this.bv.put12(91, arrayOfLong.length);
/* 200 */       for (byte b = 0; b < arrayOfLong.length; b++) {
/* 201 */         this.bv.put12(74, (this.cw.newLong(arrayOfLong[b])).index);
/*     */       }
/* 203 */     } else if (paramObject instanceof float[]) {
/* 204 */       float[] arrayOfFloat = (float[])paramObject;
/* 205 */       this.bv.put12(91, arrayOfFloat.length);
/* 206 */       for (byte b = 0; b < arrayOfFloat.length; b++) {
/* 207 */         this.bv.put12(70, (this.cw.newFloat(arrayOfFloat[b])).index);
/*     */       }
/* 209 */     } else if (paramObject instanceof double[]) {
/* 210 */       double[] arrayOfDouble = (double[])paramObject;
/* 211 */       this.bv.put12(91, arrayOfDouble.length);
/* 212 */       for (byte b = 0; b < arrayOfDouble.length; b++) {
/* 213 */         this.bv.put12(68, (this.cw.newDouble(arrayOfDouble[b])).index);
/*     */       }
/*     */     } else {
/* 216 */       Item item = this.cw.newConstItem(paramObject);
/* 217 */       this.bv.put12(".s.IFJDCS".charAt(item.type), item.index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String paramString1, String paramString2, String paramString3) {
/* 224 */     this.size++;
/* 225 */     if (this.named) {
/* 226 */       this.bv.putShort(this.cw.newUTF8(paramString1));
/*     */     }
/* 228 */     this.bv.put12(101, this.cw.newUTF8(paramString2)).putShort(this.cw.newUTF8(paramString3));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String paramString1, String paramString2) {
/* 234 */     this.size++;
/* 235 */     if (this.named) {
/* 236 */       this.bv.putShort(this.cw.newUTF8(paramString1));
/*     */     }
/*     */     
/* 239 */     this.bv.put12(64, this.cw.newUTF8(paramString2)).putShort(0);
/* 240 */     return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String paramString) {
/* 245 */     this.size++;
/* 246 */     if (this.named) {
/* 247 */       this.bv.putShort(this.cw.newUTF8(paramString));
/*     */     }
/*     */     
/* 250 */     this.bv.put12(91, 0);
/* 251 */     return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitEnd() {
/* 256 */     if (this.parent != null) {
/* 257 */       byte[] arrayOfByte = this.parent.data;
/* 258 */       arrayOfByte[this.offset] = (byte)(this.size >>> 8);
/* 259 */       arrayOfByte[this.offset + 1] = (byte)this.size;
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
/*     */   int getSize() {
/* 273 */     int i = 0;
/* 274 */     AnnotationWriter annotationWriter = this;
/* 275 */     while (annotationWriter != null) {
/* 276 */       i += annotationWriter.bv.length;
/* 277 */       annotationWriter = annotationWriter.next;
/*     */     } 
/* 279 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void put(ByteVector paramByteVector) {
/* 290 */     byte b = 0;
/* 291 */     int i = 2;
/* 292 */     AnnotationWriter annotationWriter1 = this;
/* 293 */     AnnotationWriter annotationWriter2 = null;
/* 294 */     while (annotationWriter1 != null) {
/* 295 */       b++;
/* 296 */       i += annotationWriter1.bv.length;
/* 297 */       annotationWriter1.visitEnd();
/* 298 */       annotationWriter1.prev = annotationWriter2;
/* 299 */       annotationWriter2 = annotationWriter1;
/* 300 */       annotationWriter1 = annotationWriter1.next;
/*     */     } 
/* 302 */     paramByteVector.putInt(i);
/* 303 */     paramByteVector.putShort(b);
/* 304 */     annotationWriter1 = annotationWriter2;
/* 305 */     while (annotationWriter1 != null) {
/* 306 */       paramByteVector.putByteArray(annotationWriter1.bv.data, 0, annotationWriter1.bv.length);
/* 307 */       annotationWriter1 = annotationWriter1.prev;
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
/*     */   static void put(AnnotationWriter[] paramArrayOfAnnotationWriter, int paramInt, ByteVector paramByteVector) {
/* 323 */     int i = 1 + 2 * (paramArrayOfAnnotationWriter.length - paramInt); int j;
/* 324 */     for (j = paramInt; j < paramArrayOfAnnotationWriter.length; j++) {
/* 325 */       i += (paramArrayOfAnnotationWriter[j] == null) ? 0 : paramArrayOfAnnotationWriter[j].getSize();
/*     */     }
/* 327 */     paramByteVector.putInt(i).putByte(paramArrayOfAnnotationWriter.length - paramInt);
/* 328 */     for (j = paramInt; j < paramArrayOfAnnotationWriter.length; j++) {
/* 329 */       AnnotationWriter annotationWriter1 = paramArrayOfAnnotationWriter[j];
/* 330 */       AnnotationWriter annotationWriter2 = null;
/* 331 */       byte b = 0;
/* 332 */       while (annotationWriter1 != null) {
/* 333 */         b++;
/* 334 */         annotationWriter1.visitEnd();
/* 335 */         annotationWriter1.prev = annotationWriter2;
/* 336 */         annotationWriter2 = annotationWriter1;
/* 337 */         annotationWriter1 = annotationWriter1.next;
/*     */       } 
/* 339 */       paramByteVector.putShort(b);
/* 340 */       annotationWriter1 = annotationWriter2;
/* 341 */       while (annotationWriter1 != null) {
/* 342 */         paramByteVector.putByteArray(annotationWriter1.bv.data, 0, annotationWriter1.bv.length);
/* 343 */         annotationWriter1 = annotationWriter1.prev;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putTarget(int paramInt, TypePath paramTypePath, ByteVector paramByteVector) {
/* 362 */     switch (paramInt >>> 24) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 22:
/* 366 */         paramByteVector.putShort(paramInt >>> 16);
/*     */         break;
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/* 371 */         paramByteVector.putByte(paramInt >>> 24);
/*     */         break;
/*     */       case 71:
/*     */       case 72:
/*     */       case 73:
/*     */       case 74:
/*     */       case 75:
/* 378 */         paramByteVector.putInt(paramInt);
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 390 */         paramByteVector.put12(paramInt >>> 24, (paramInt & 0xFFFF00) >> 8);
/*     */         break;
/*     */     } 
/* 393 */     if (paramTypePath == null) {
/* 394 */       paramByteVector.putByte(0);
/*     */     } else {
/* 396 */       int i = paramTypePath.b[paramTypePath.offset] * 2 + 1;
/* 397 */       paramByteVector.putByteArray(paramTypePath.b, paramTypePath.offset, i);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/AnnotationWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */