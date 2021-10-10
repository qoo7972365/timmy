/*     */ package sun.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnsafeDoubleFieldAccessorImpl
/*     */   extends UnsafeFieldAccessorImpl
/*     */ {
/*     */   UnsafeDoubleFieldAccessorImpl(Field paramField) {
/*  32 */     super(paramField);
/*     */   }
/*     */   
/*     */   public Object get(Object paramObject) throws IllegalArgumentException {
/*  36 */     return new Double(getDouble(paramObject));
/*     */   }
/*     */   
/*     */   public boolean getBoolean(Object paramObject) throws IllegalArgumentException {
/*  40 */     throw newGetBooleanIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public byte getByte(Object paramObject) throws IllegalArgumentException {
/*  44 */     throw newGetByteIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public char getChar(Object paramObject) throws IllegalArgumentException {
/*  48 */     throw newGetCharIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public short getShort(Object paramObject) throws IllegalArgumentException {
/*  52 */     throw newGetShortIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public int getInt(Object paramObject) throws IllegalArgumentException {
/*  56 */     throw newGetIntIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public long getLong(Object paramObject) throws IllegalArgumentException {
/*  60 */     throw newGetLongIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public float getFloat(Object paramObject) throws IllegalArgumentException {
/*  64 */     throw newGetFloatIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public double getDouble(Object paramObject) throws IllegalArgumentException {
/*  68 */     ensureObj(paramObject);
/*  69 */     return unsafe.getDouble(paramObject, this.fieldOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object paramObject1, Object paramObject2) throws IllegalArgumentException, IllegalAccessException {
/*  75 */     ensureObj(paramObject1);
/*  76 */     if (this.isFinal) {
/*  77 */       throwFinalFieldIllegalAccessException(paramObject2);
/*     */     }
/*  79 */     if (paramObject2 == null) {
/*  80 */       throwSetIllegalArgumentException(paramObject2);
/*     */     }
/*  82 */     if (paramObject2 instanceof Byte) {
/*  83 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Byte)paramObject2).byteValue());
/*     */       return;
/*     */     } 
/*  86 */     if (paramObject2 instanceof Short) {
/*  87 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Short)paramObject2).shortValue());
/*     */       return;
/*     */     } 
/*  90 */     if (paramObject2 instanceof Character) {
/*  91 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Character)paramObject2).charValue());
/*     */       return;
/*     */     } 
/*  94 */     if (paramObject2 instanceof Integer) {
/*  95 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Integer)paramObject2).intValue());
/*     */       return;
/*     */     } 
/*  98 */     if (paramObject2 instanceof Long) {
/*  99 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Long)paramObject2).longValue());
/*     */       return;
/*     */     } 
/* 102 */     if (paramObject2 instanceof Float) {
/* 103 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Float)paramObject2).floatValue());
/*     */       return;
/*     */     } 
/* 106 */     if (paramObject2 instanceof Double) {
/* 107 */       unsafe.putDouble(paramObject1, this.fieldOffset, ((Double)paramObject2).doubleValue());
/*     */       return;
/*     */     } 
/* 110 */     throwSetIllegalArgumentException(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(Object paramObject, boolean paramBoolean) throws IllegalArgumentException, IllegalAccessException {
/* 116 */     throwSetIllegalArgumentException(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(Object paramObject, byte paramByte) throws IllegalArgumentException, IllegalAccessException {
/* 122 */     setDouble(paramObject, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(Object paramObject, char paramChar) throws IllegalArgumentException, IllegalAccessException {
/* 128 */     setDouble(paramObject, paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(Object paramObject, short paramShort) throws IllegalArgumentException, IllegalAccessException {
/* 134 */     setDouble(paramObject, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(Object paramObject, int paramInt) throws IllegalArgumentException, IllegalAccessException {
/* 140 */     setDouble(paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(Object paramObject, long paramLong) throws IllegalArgumentException, IllegalAccessException {
/* 146 */     setDouble(paramObject, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(Object paramObject, float paramFloat) throws IllegalArgumentException, IllegalAccessException {
/* 152 */     setDouble(paramObject, paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(Object paramObject, double paramDouble) throws IllegalArgumentException, IllegalAccessException {
/* 158 */     ensureObj(paramObject);
/* 159 */     if (this.isFinal) {
/* 160 */       throwFinalFieldIllegalAccessException(paramDouble);
/*     */     }
/* 162 */     unsafe.putDouble(paramObject, this.fieldOffset, paramDouble);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/UnsafeDoubleFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */