/*     */ package jdk.internal.org.objectweb.asm.commons;
/*     */ 
/*     */ import jdk.internal.org.objectweb.asm.AnnotationVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Label;
/*     */ import jdk.internal.org.objectweb.asm.MethodVisitor;
/*     */ import jdk.internal.org.objectweb.asm.Opcodes;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.TypePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalVariablesSorter
/*     */   extends MethodVisitor
/*     */ {
/*  83 */   private static final Type OBJECT_TYPE = Type.getObjectType("java/lang/Object");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private int[] mapping = new int[40];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private Object[] newLocals = new Object[20];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int firstLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextLocal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean changed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalVariablesSorter(int paramInt, String paramString, MethodVisitor paramMethodVisitor) {
/* 128 */     this(327680, paramInt, paramString, paramMethodVisitor);
/* 129 */     if (getClass() != LocalVariablesSorter.class) {
/* 130 */       throw new IllegalStateException();
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
/*     */   protected LocalVariablesSorter(int paramInt1, int paramInt2, String paramString, MethodVisitor paramMethodVisitor) {
/* 149 */     super(paramInt1, paramMethodVisitor);
/* 150 */     Type[] arrayOfType = Type.getArgumentTypes(paramString);
/* 151 */     this.nextLocal = ((0x8 & paramInt2) == 0) ? 1 : 0;
/* 152 */     for (byte b = 0; b < arrayOfType.length; b++) {
/* 153 */       this.nextLocal += arrayOfType[b].getSize();
/*     */     }
/* 155 */     this.firstLocal = this.nextLocal;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int paramInt1, int paramInt2) {
/*     */     Type type;
/* 161 */     switch (paramInt1) {
/*     */       case 22:
/*     */       case 55:
/* 164 */         type = Type.LONG_TYPE;
/*     */         break;
/*     */       
/*     */       case 24:
/*     */       case 57:
/* 169 */         type = Type.DOUBLE_TYPE;
/*     */         break;
/*     */       
/*     */       case 23:
/*     */       case 56:
/* 174 */         type = Type.FLOAT_TYPE;
/*     */         break;
/*     */       
/*     */       case 21:
/*     */       case 54:
/* 179 */         type = Type.INT_TYPE;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       default:
/* 186 */         type = OBJECT_TYPE;
/*     */         break;
/*     */     } 
/* 189 */     this.mv.visitVarInsn(paramInt1, remap(paramInt2, type));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int paramInt1, int paramInt2) {
/* 194 */     this.mv.visitIincInsn(remap(paramInt1, Type.INT_TYPE), paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int paramInt1, int paramInt2) {
/* 199 */     this.mv.visitMaxs(paramInt1, this.nextLocal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String paramString1, String paramString2, String paramString3, Label paramLabel1, Label paramLabel2, int paramInt) {
/* 206 */     int i = remap(paramInt, Type.getType(paramString2));
/* 207 */     this.mv.visitLocalVariable(paramString1, paramString2, paramString3, paramLabel1, paramLabel2, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int paramInt, TypePath paramTypePath, Label[] paramArrayOfLabel1, Label[] paramArrayOfLabel2, int[] paramArrayOfint, String paramString, boolean paramBoolean) {
/* 214 */     Type type = Type.getType(paramString);
/* 215 */     int[] arrayOfInt = new int[paramArrayOfint.length];
/* 216 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 217 */       arrayOfInt[b] = remap(paramArrayOfint[b], type);
/*     */     }
/* 219 */     return this.mv.visitLocalVariableAnnotation(paramInt, paramTypePath, paramArrayOfLabel1, paramArrayOfLabel2, arrayOfInt, paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int paramInt1, int paramInt2, Object[] paramArrayOfObject1, int paramInt3, Object[] paramArrayOfObject2) {
/* 226 */     if (paramInt1 != -1) {
/* 227 */       throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
/*     */     }
/*     */ 
/*     */     
/* 231 */     if (!this.changed) {
/* 232 */       this.mv.visitFrame(paramInt1, paramInt2, paramArrayOfObject1, paramInt3, paramArrayOfObject2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 237 */     Object[] arrayOfObject = new Object[this.newLocals.length];
/* 238 */     System.arraycopy(this.newLocals, 0, arrayOfObject, 0, arrayOfObject.length);
/*     */     
/* 240 */     updateNewLocals(this.newLocals);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     int i = 0;
/* 246 */     int j = 0;
/* 247 */     for (; j < paramInt2; j++) {
/* 248 */       Object object = paramArrayOfObject1[j];
/* 249 */       byte b1 = (object == Opcodes.LONG || object == Opcodes.DOUBLE) ? 2 : 1;
/* 250 */       if (object != Opcodes.TOP) {
/* 251 */         Type type = OBJECT_TYPE;
/* 252 */         if (object == Opcodes.INTEGER) {
/* 253 */           type = Type.INT_TYPE;
/* 254 */         } else if (object == Opcodes.FLOAT) {
/* 255 */           type = Type.FLOAT_TYPE;
/* 256 */         } else if (object == Opcodes.LONG) {
/* 257 */           type = Type.LONG_TYPE;
/* 258 */         } else if (object == Opcodes.DOUBLE) {
/* 259 */           type = Type.DOUBLE_TYPE;
/* 260 */         } else if (object instanceof String) {
/* 261 */           type = Type.getObjectType((String)object);
/*     */         } 
/* 263 */         setFrameLocal(remap(i, type), object);
/*     */       } 
/* 265 */       i += b1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 270 */     i = 0;
/* 271 */     j = 0;
/* 272 */     for (byte b = 0; i < this.newLocals.length; b++) {
/* 273 */       Object object = this.newLocals[i++];
/* 274 */       if (object != null && object != Opcodes.TOP) {
/* 275 */         this.newLocals[b] = object;
/* 276 */         j = b + 1;
/* 277 */         if (object == Opcodes.LONG || object == Opcodes.DOUBLE) {
/* 278 */           i++;
/*     */         }
/*     */       } else {
/* 281 */         this.newLocals[b] = Opcodes.TOP;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 286 */     this.mv.visitFrame(paramInt1, j, this.newLocals, paramInt3, paramArrayOfObject2);
/*     */ 
/*     */     
/* 289 */     this.newLocals = arrayOfObject;
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
/*     */   public int newLocal(Type paramType) {
/*     */     Integer integer;
/* 303 */     switch (paramType.getSort())
/*     */     { case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 309 */         integer = Opcodes.INTEGER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 328 */         i = newLocalMapping(paramType);
/* 329 */         setLocalType(i, paramType);
/* 330 */         setFrameLocal(i, integer);
/* 331 */         this.changed = true;
/* 332 */         return i;case 6: integer = Opcodes.FLOAT; i = newLocalMapping(paramType); setLocalType(i, paramType); setFrameLocal(i, integer); this.changed = true; return i;case 7: integer = Opcodes.LONG; i = newLocalMapping(paramType); setLocalType(i, paramType); setFrameLocal(i, integer); this.changed = true; return i;case 8: integer = Opcodes.DOUBLE; i = newLocalMapping(paramType); setLocalType(i, paramType); setFrameLocal(i, integer); this.changed = true; return i;case 9: str = paramType.getDescriptor(); i = newLocalMapping(paramType); setLocalType(i, paramType); setFrameLocal(i, str); this.changed = true; return i; }  String str = paramType.getInternalName(); int i = newLocalMapping(paramType); setLocalType(i, paramType); setFrameLocal(i, str); this.changed = true; return i;
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
/*     */   protected void updateNewLocals(Object[] paramArrayOfObject) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setLocalType(int paramInt, Type paramType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFrameLocal(int paramInt, Object paramObject) {
/* 371 */     int i = this.newLocals.length;
/* 372 */     if (paramInt >= i) {
/* 373 */       Object[] arrayOfObject = new Object[Math.max(2 * i, paramInt + 1)];
/* 374 */       System.arraycopy(this.newLocals, 0, arrayOfObject, 0, i);
/* 375 */       this.newLocals = arrayOfObject;
/*     */     } 
/* 377 */     this.newLocals[paramInt] = paramObject;
/*     */   }
/*     */   
/*     */   private int remap(int paramInt, Type paramType) {
/* 381 */     if (paramInt + paramType.getSize() <= this.firstLocal) {
/* 382 */       return paramInt;
/*     */     }
/* 384 */     int i = 2 * paramInt + paramType.getSize() - 1;
/* 385 */     int j = this.mapping.length;
/* 386 */     if (i >= j) {
/* 387 */       int[] arrayOfInt = new int[Math.max(2 * j, i + 1)];
/* 388 */       System.arraycopy(this.mapping, 0, arrayOfInt, 0, j);
/* 389 */       this.mapping = arrayOfInt;
/*     */     } 
/* 391 */     int k = this.mapping[i];
/* 392 */     if (k == 0) {
/* 393 */       k = newLocalMapping(paramType);
/* 394 */       setLocalType(k, paramType);
/* 395 */       this.mapping[i] = k + 1;
/*     */     } else {
/* 397 */       k--;
/*     */     } 
/* 399 */     if (k != paramInt) {
/* 400 */       this.changed = true;
/*     */     }
/* 402 */     return k;
/*     */   }
/*     */   
/*     */   protected int newLocalMapping(Type paramType) {
/* 406 */     int i = this.nextLocal;
/* 407 */     this.nextLocal += paramType.getSize();
/* 408 */     return i;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/internal/org/objectweb/asm/commons/LocalVariablesSorter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */