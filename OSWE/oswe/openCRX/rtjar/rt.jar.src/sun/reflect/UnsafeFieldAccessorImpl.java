/*     */ package sun.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class UnsafeFieldAccessorImpl
/*     */   extends FieldAccessorImpl
/*     */ {
/*  40 */   static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */   
/*     */   protected final Field field;
/*     */   protected final long fieldOffset;
/*     */   protected final boolean isFinal;
/*     */   
/*     */   UnsafeFieldAccessorImpl(Field paramField) {
/*  47 */     this.field = paramField;
/*  48 */     if (Modifier.isStatic(paramField.getModifiers())) {
/*  49 */       this.fieldOffset = unsafe.staticFieldOffset(paramField);
/*     */     } else {
/*  51 */       this.fieldOffset = unsafe.objectFieldOffset(paramField);
/*  52 */     }  this.isFinal = Modifier.isFinal(paramField.getModifiers());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void ensureObj(Object paramObject) {
/*  57 */     if (!this.field.getDeclaringClass().isAssignableFrom(paramObject.getClass())) {
/*  58 */       throwSetIllegalArgumentException(paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getQualifiedFieldName() {
/*  63 */     return this.field.getDeclaringClass().getName() + "." + this.field.getName();
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetIllegalArgumentException(String paramString) {
/*  67 */     return new IllegalArgumentException("Attempt to get " + this.field
/*  68 */         .getType().getName() + " field \"" + 
/*  69 */         getQualifiedFieldName() + "\" with illegal data type conversion to " + paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(String paramString1, String paramString2) throws IllegalAccessException {
/*  76 */     throw new IllegalAccessException(getSetMessage(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(Object paramObject) throws IllegalAccessException {
/*  80 */     throwFinalFieldIllegalAccessException((paramObject != null) ? paramObject.getClass().getName() : "", "");
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(boolean paramBoolean) throws IllegalAccessException {
/*  84 */     throwFinalFieldIllegalAccessException("boolean", Boolean.toString(paramBoolean));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(char paramChar) throws IllegalAccessException {
/*  88 */     throwFinalFieldIllegalAccessException("char", Character.toString(paramChar));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(byte paramByte) throws IllegalAccessException {
/*  92 */     throwFinalFieldIllegalAccessException("byte", Byte.toString(paramByte));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(short paramShort) throws IllegalAccessException {
/*  96 */     throwFinalFieldIllegalAccessException("short", Short.toString(paramShort));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(int paramInt) throws IllegalAccessException {
/* 100 */     throwFinalFieldIllegalAccessException("int", Integer.toString(paramInt));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(long paramLong) throws IllegalAccessException {
/* 104 */     throwFinalFieldIllegalAccessException("long", Long.toString(paramLong));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(float paramFloat) throws IllegalAccessException {
/* 108 */     throwFinalFieldIllegalAccessException("float", Float.toString(paramFloat));
/*     */   }
/*     */   
/*     */   protected void throwFinalFieldIllegalAccessException(double paramDouble) throws IllegalAccessException {
/* 112 */     throwFinalFieldIllegalAccessException("double", Double.toString(paramDouble));
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetBooleanIllegalArgumentException() {
/* 116 */     return newGetIllegalArgumentException("boolean");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetByteIllegalArgumentException() {
/* 120 */     return newGetIllegalArgumentException("byte");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetCharIllegalArgumentException() {
/* 124 */     return newGetIllegalArgumentException("char");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetShortIllegalArgumentException() {
/* 128 */     return newGetIllegalArgumentException("short");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetIntIllegalArgumentException() {
/* 132 */     return newGetIllegalArgumentException("int");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetLongIllegalArgumentException() {
/* 136 */     return newGetIllegalArgumentException("long");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetFloatIllegalArgumentException() {
/* 140 */     return newGetIllegalArgumentException("float");
/*     */   }
/*     */   
/*     */   protected IllegalArgumentException newGetDoubleIllegalArgumentException() {
/* 144 */     return newGetIllegalArgumentException("double");
/*     */   }
/*     */   
/*     */   protected String getSetMessage(String paramString1, String paramString2) {
/* 148 */     String str = "Can not set";
/* 149 */     if (Modifier.isStatic(this.field.getModifiers()))
/* 150 */       str = str + " static"; 
/* 151 */     if (this.isFinal)
/* 152 */       str = str + " final"; 
/* 153 */     str = str + " " + this.field.getType().getName() + " field " + getQualifiedFieldName() + " to ";
/* 154 */     if (paramString2.length() > 0) {
/* 155 */       str = str + "(" + paramString1 + ")" + paramString2;
/*     */     }
/* 157 */     else if (paramString1.length() > 0) {
/* 158 */       str = str + paramString1;
/*     */     } else {
/* 160 */       str = str + "null value";
/*     */     } 
/* 162 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void throwSetIllegalArgumentException(String paramString1, String paramString2) {
/* 167 */     throw new IllegalArgumentException(getSetMessage(paramString1, paramString2));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(Object paramObject) {
/* 171 */     throwSetIllegalArgumentException((paramObject != null) ? paramObject.getClass().getName() : "", "");
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(boolean paramBoolean) {
/* 175 */     throwSetIllegalArgumentException("boolean", Boolean.toString(paramBoolean));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(byte paramByte) {
/* 179 */     throwSetIllegalArgumentException("byte", Byte.toString(paramByte));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(char paramChar) {
/* 183 */     throwSetIllegalArgumentException("char", Character.toString(paramChar));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(short paramShort) {
/* 187 */     throwSetIllegalArgumentException("short", Short.toString(paramShort));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(int paramInt) {
/* 191 */     throwSetIllegalArgumentException("int", Integer.toString(paramInt));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(long paramLong) {
/* 195 */     throwSetIllegalArgumentException("long", Long.toString(paramLong));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(float paramFloat) {
/* 199 */     throwSetIllegalArgumentException("float", Float.toString(paramFloat));
/*     */   }
/*     */   
/*     */   protected void throwSetIllegalArgumentException(double paramDouble) {
/* 203 */     throwSetIllegalArgumentException("double", Double.toString(paramDouble));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/reflect/UnsafeFieldAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */