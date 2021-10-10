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
/*     */ 
/*     */ 
/*     */ class UnsafeQualifiedStaticLongFieldAccessorImpl
/*     */   extends UnsafeQualifiedStaticFieldAccessorImpl
/*     */ {
/*     */   UnsafeQualifiedStaticLongFieldAccessorImpl(Field paramField, boolean paramBoolean) {
/*  34 */     super(paramField, paramBoolean);
/*     */   }
/*     */   
/*     */   public Object get(Object paramObject) throws IllegalArgumentException {
/*  38 */     return new Long(getLong(paramObject));
/*     */   }
/*     */   
/*     */   public boolean getBoolean(Object paramObject) throws IllegalArgumentException {
/*  42 */     throw newGetBooleanIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public byte getByte(Object paramObject) throws IllegalArgumentException {
/*  46 */     throw newGetByteIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public char getChar(Object paramObject) throws IllegalArgumentException {
/*  50 */     throw newGetCharIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public short getShort(Object paramObject) throws IllegalArgumentException {
/*  54 */     throw newGetShortIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public int getInt(Object paramObject) throws IllegalArgumentException {
/*  58 */     throw newGetIntIllegalArgumentException();
/*     */   }
/*     */   
/*     */   public long getLong(Object paramObject) throws IllegalArgumentException {
/*  62 */     return unsafe.getLongVolatile(this.base, this.fieldOffset);
/*     */   }
/*     */   
/*     */   public float getFloat(Object paramObject) throws IllegalArgumentException {
/*  66 */     return (float)getLong(paramObject);
/*     */   }
/*     */   
/*     */   public double getDouble(Object paramObject) throws IllegalArgumentException {
/*  70 */     return getLong(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(Object paramObject1, Object paramObject2) throws IllegalArgumentException, IllegalAccessException {
/*  76 */     if (this.isReadOnly) {
/*  77 */       throwFinalFieldIllegalAccessException(paramObject2);
/*     */     }
/*  79 */     if (paramObject2 == null) {
/*  80 */       throwSetIllegalArgumentException(paramObject2);
/*     */     }
/*  82 */     if (paramObject2 instanceof Byte) {
/*  83 */       unsafe.putLongVolatile(this.base, this.fieldOffset, ((Byte)paramObject2).byteValue());
/*     */       return;
/*     */     } 
/*  86 */     if (paramObject2 instanceof Short) {
/*  87 */       unsafe.putLongVolatile(this.base, this.fieldOffset, ((Short)paramObject2).shortValue());
/*     */       return;
/*     */     } 
/*  90 */     if (paramObject2 instanceof Character) {
/*  91 */       unsafe.putLongVolatile(this.base, this.fieldOffset, ((Character)paramObject2).charValue());
/*     */       return;
/*     */     } 
/*  94 */     if (paramObject2 instanceof Integer) {
/*  95 */       unsafe.putLongVolatile(this.base, this.fieldOffset, ((Integer)paramObject2).intValue());
/*     */       return;
/*     */     } 
/*  98 */     if (paramObject2 instanceof Long) {
/*  99 */       unsafe.putLongVolatile(this.base, this.fieldOffset, ((Long)paramObject2).longValue());
/*     */       return;
/*     */     } 
/* 102 */     throwSetIllegalArgumentException(paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(Object paramObject, boolean paramBoolean) throws IllegalArgumentException, IllegalAccessException {
/* 108 */     throwSetIllegalArgumentException(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(Object paramObject, byte paramByte) throws IllegalArgumentException, IllegalAccessException {
/* 114 */     setLong(paramObject, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChar(Object paramObject, char paramChar) throws IllegalArgumentException, IllegalAccessException {
/* 120 */     setLong(paramObject, paramChar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(Object paramObject, short paramShort) throws IllegalArgumentException, IllegalAccessException {
/* 126 */     setLong(paramObject, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInt(Object paramObject, int paramInt) throws IllegalArgumentException, IllegalAccessException {
/* 132 */     setLong(paramObject, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(Object paramObject, long paramLong) throws IllegalArgumentException, IllegalAccessException {
/* 138 */     if (this.isReadOnly) {
/* 139 */       throwFinalFieldIllegalAccessException(paramLong);
/*     */     }
/* 141 */     unsafe.putLongVolatile(this.base, this.fieldOffset, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(Object paramObject, float paramFloat) throws IllegalArgumentException, IllegalAccessException {
/* 147 */     throwSetIllegalArgumentException(paramFloat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(Object paramObject, double paramDouble) throws IllegalArgumentException, IllegalAccessException {
/* 153 */     throwSetIllegalArgumentException(paramDouble);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/UnsafeQualifiedStaticLongFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */