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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FieldWriter
/*     */   extends FieldVisitor
/*     */ {
/*     */   private final ClassWriter cw;
/*     */   private final int access;
/*     */   private final int name;
/*     */   private final int desc;
/*     */   private int signature;
/*     */   private int value;
/*     */   private AnnotationWriter anns;
/*     */   private AnnotationWriter ianns;
/*     */   private AnnotationWriter tanns;
/*     */   private AnnotationWriter itanns;
/*     */   private Attribute attrs;
/*     */   
/*     */   FieldWriter(ClassWriter paramClassWriter, int paramInt, String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 150 */     super(327680);
/* 151 */     if (paramClassWriter.firstField == null) {
/* 152 */       paramClassWriter.firstField = this;
/*     */     } else {
/* 154 */       paramClassWriter.lastField.fv = this;
/*     */     } 
/* 156 */     paramClassWriter.lastField = this;
/* 157 */     this.cw = paramClassWriter;
/* 158 */     this.access = paramInt;
/* 159 */     this.name = paramClassWriter.newUTF8(paramString1);
/* 160 */     this.desc = paramClassWriter.newUTF8(paramString2);
/* 161 */     if (paramString3 != null) {
/* 162 */       this.signature = paramClassWriter.newUTF8(paramString3);
/*     */     }
/* 164 */     if (paramObject != null) {
/* 165 */       this.value = (paramClassWriter.newConstItem(paramObject)).index;
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
/*     */   public AnnotationVisitor visitAnnotation(String paramString, boolean paramBoolean) {
/* 179 */     ByteVector byteVector = new ByteVector();
/*     */     
/* 181 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/* 182 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
/* 183 */     if (paramBoolean) {
/* 184 */       annotationWriter.next = this.anns;
/* 185 */       this.anns = annotationWriter;
/*     */     } else {
/* 187 */       annotationWriter.next = this.ianns;
/* 188 */       this.ianns = annotationWriter;
/*     */     } 
/* 190 */     return annotationWriter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int paramInt, TypePath paramTypePath, String paramString, boolean paramBoolean) {
/* 199 */     ByteVector byteVector = new ByteVector();
/*     */     
/* 201 */     AnnotationWriter.putTarget(paramInt, paramTypePath, byteVector);
/*     */     
/* 203 */     byteVector.putShort(this.cw.newUTF8(paramString)).putShort(0);
/* 204 */     AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
/*     */     
/* 206 */     if (paramBoolean) {
/* 207 */       annotationWriter.next = this.tanns;
/* 208 */       this.tanns = annotationWriter;
/*     */     } else {
/* 210 */       annotationWriter.next = this.itanns;
/* 211 */       this.itanns = annotationWriter;
/*     */     } 
/* 213 */     return annotationWriter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute paramAttribute) {
/* 218 */     paramAttribute.next = this.attrs;
/* 219 */     this.attrs = paramAttribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getSize() {
/* 236 */     int i = 8;
/* 237 */     if (this.value != 0) {
/* 238 */       this.cw.newUTF8("ConstantValue");
/* 239 */       i += 8;
/*     */     } 
/* 241 */     if ((this.access & 0x1000) != 0 && ((
/* 242 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
/*     */       
/* 244 */       this.cw.newUTF8("Synthetic");
/* 245 */       i += 6;
/*     */     } 
/*     */     
/* 248 */     if ((this.access & 0x20000) != 0) {
/* 249 */       this.cw.newUTF8("Deprecated");
/* 250 */       i += 6;
/*     */     } 
/* 252 */     if (this.signature != 0) {
/* 253 */       this.cw.newUTF8("Signature");
/* 254 */       i += 8;
/*     */     } 
/* 256 */     if (this.anns != null) {
/* 257 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 258 */       i += 8 + this.anns.getSize();
/*     */     } 
/* 260 */     if (this.ianns != null) {
/* 261 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 262 */       i += 8 + this.ianns.getSize();
/*     */     } 
/* 264 */     if (this.tanns != null) {
/* 265 */       this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 266 */       i += 8 + this.tanns.getSize();
/*     */     } 
/* 268 */     if (this.itanns != null) {
/* 269 */       this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 270 */       i += 8 + this.itanns.getSize();
/*     */     } 
/* 272 */     if (this.attrs != null) {
/* 273 */       i += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*     */     }
/* 275 */     return i;
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
/* 286 */     int i = 0x60000 | (this.access & 0x40000) / 64;
/*     */     
/* 288 */     paramByteVector.putShort(this.access & (i ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.desc);
/* 289 */     int j = 0;
/* 290 */     if (this.value != 0) {
/* 291 */       j++;
/*     */     }
/* 293 */     if ((this.access & 0x1000) != 0 && ((
/* 294 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*     */     {
/* 296 */       j++;
/*     */     }
/*     */     
/* 299 */     if ((this.access & 0x20000) != 0) {
/* 300 */       j++;
/*     */     }
/* 302 */     if (this.signature != 0) {
/* 303 */       j++;
/*     */     }
/* 305 */     if (this.anns != null) {
/* 306 */       j++;
/*     */     }
/* 308 */     if (this.ianns != null) {
/* 309 */       j++;
/*     */     }
/* 311 */     if (this.tanns != null) {
/* 312 */       j++;
/*     */     }
/* 314 */     if (this.itanns != null) {
/* 315 */       j++;
/*     */     }
/* 317 */     if (this.attrs != null) {
/* 318 */       j += this.attrs.getCount();
/*     */     }
/* 320 */     paramByteVector.putShort(j);
/* 321 */     if (this.value != 0) {
/* 322 */       paramByteVector.putShort(this.cw.newUTF8("ConstantValue"));
/* 323 */       paramByteVector.putInt(2).putShort(this.value);
/*     */     } 
/* 325 */     if ((this.access & 0x1000) != 0 && ((
/* 326 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*     */     {
/* 328 */       paramByteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*     */     }
/*     */     
/* 331 */     if ((this.access & 0x20000) != 0) {
/* 332 */       paramByteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*     */     }
/* 334 */     if (this.signature != 0) {
/* 335 */       paramByteVector.putShort(this.cw.newUTF8("Signature"));
/* 336 */       paramByteVector.putInt(2).putShort(this.signature);
/*     */     } 
/* 338 */     if (this.anns != null) {
/* 339 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 340 */       this.anns.put(paramByteVector);
/*     */     } 
/* 342 */     if (this.ianns != null) {
/* 343 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 344 */       this.ianns.put(paramByteVector);
/*     */     } 
/* 346 */     if (this.tanns != null) {
/* 347 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 348 */       this.tanns.put(paramByteVector);
/*     */     } 
/* 350 */     if (this.itanns != null) {
/* 351 */       paramByteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 352 */       this.itanns.put(paramByteVector);
/*     */     } 
/* 354 */     if (this.attrs != null)
/* 355 */       this.attrs.put(this.cw, null, 0, -1, -1, paramByteVector); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/FieldWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */