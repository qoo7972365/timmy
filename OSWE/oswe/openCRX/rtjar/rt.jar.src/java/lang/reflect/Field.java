/*      */ package java.lang.reflect;
/*      */ 
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.AccessibleObject;
/*      */ import java.lang.reflect.AnnotatedType;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Member;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.lang.reflect.Type;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.FieldAccessor;
/*      */ import sun.reflect.Reflection;
/*      */ import sun.reflect.annotation.AnnotationParser;
/*      */ import sun.reflect.annotation.AnnotationSupport;
/*      */ import sun.reflect.annotation.TypeAnnotation;
/*      */ import sun.reflect.annotation.TypeAnnotationParser;
/*      */ import sun.reflect.generics.factory.CoreReflectionFactory;
/*      */ import sun.reflect.generics.factory.GenericsFactory;
/*      */ import sun.reflect.generics.repository.FieldRepository;
/*      */ import sun.reflect.generics.scope.ClassScope;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Field
/*      */   extends AccessibleObject
/*      */   implements Member
/*      */ {
/*      */   private Class<?> clazz;
/*      */   private int slot;
/*      */   private String name;
/*      */   private Class<?> type;
/*      */   private int modifiers;
/*      */   private transient String signature;
/*      */   private transient FieldRepository genericInfo;
/*      */   private byte[] annotations;
/*      */   private FieldAccessor fieldAccessor;
/*      */   private FieldAccessor overrideFieldAccessor;
/*      */   private Field root;
/*      */   private volatile transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;
/*      */   
/*      */   private String getGenericSignature() {
/*   91 */     return this.signature;
/*      */   }
/*      */   
/*      */   private GenericsFactory getFactory() {
/*   95 */     Class<?> clazz = getDeclaringClass();
/*      */     
/*   97 */     return CoreReflectionFactory.make(clazz, ClassScope.make(clazz));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldRepository getGenericInfo() {
/*  103 */     if (this.genericInfo == null)
/*      */     {
/*  105 */       this.genericInfo = FieldRepository.make(getGenericSignature(), 
/*  106 */           getFactory());
/*      */     }
/*  108 */     return this.genericInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Field(Class<?> paramClass1, String paramString1, Class<?> paramClass2, int paramInt1, int paramInt2, String paramString2, byte[] paramArrayOfbyte) {
/*  125 */     this.clazz = paramClass1;
/*  126 */     this.name = paramString1;
/*  127 */     this.type = paramClass2;
/*  128 */     this.modifiers = paramInt1;
/*  129 */     this.slot = paramInt2;
/*  130 */     this.signature = paramString2;
/*  131 */     this.annotations = paramArrayOfbyte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Field copy() {
/*  147 */     if (this.root != null) {
/*  148 */       throw new IllegalArgumentException("Can not copy a non-root Field");
/*      */     }
/*  150 */     Field field = new Field(this.clazz, this.name, this.type, this.modifiers, this.slot, this.signature, this.annotations);
/*  151 */     field.root = this;
/*      */     
/*  153 */     field.fieldAccessor = this.fieldAccessor;
/*  154 */     field.overrideFieldAccessor = this.overrideFieldAccessor;
/*      */     
/*  156 */     return field;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getDeclaringClass() {
/*  164 */     return this.clazz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  171 */     return this.name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getModifiers() {
/*  182 */     return this.modifiers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEnumConstant() {
/*  194 */     return ((getModifiers() & 0x4000) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSynthetic() {
/*  206 */     return Modifier.isSynthetic(getModifiers());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Class<?> getType() {
/*  218 */     return this.type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Type getGenericType() {
/*  246 */     if (getGenericSignature() != null) {
/*  247 */       return getGenericInfo().getGenericType();
/*      */     }
/*  249 */     return getType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  260 */     if (paramObject != null && paramObject instanceof Field) {
/*  261 */       Field field = (Field)paramObject;
/*  262 */       return (getDeclaringClass() == field.getDeclaringClass() && 
/*  263 */         getName() == field.getName() && 
/*  264 */         getType() == field.getType());
/*      */     } 
/*  266 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  275 */     return getDeclaringClass().getName().hashCode() ^ getName().hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  300 */     int i = getModifiers();
/*  301 */     return ((i == 0) ? "" : (Modifier.toString(i) + " ")) + 
/*  302 */       getType().getTypeName() + " " + 
/*  303 */       getDeclaringClass().getTypeName() + "." + 
/*  304 */       getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toGenericString() {
/*  328 */     int i = getModifiers();
/*  329 */     Type type = getGenericType();
/*  330 */     return ((i == 0) ? "" : (Modifier.toString(i) + " ")) + type
/*  331 */       .getTypeName() + " " + 
/*  332 */       getDeclaringClass().getTypeName() + "." + 
/*  333 */       getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public Object get(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  387 */     if (!this.override && 
/*  388 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  389 */       Class<?> clazz = Reflection.getCallerClass();
/*  390 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  393 */     return getFieldAccessor(paramObject).get(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public boolean getBoolean(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  422 */     if (!this.override && 
/*  423 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  424 */       Class<?> clazz = Reflection.getCallerClass();
/*  425 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  428 */     return getFieldAccessor(paramObject).getBoolean(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public byte getByte(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  457 */     if (!this.override && 
/*  458 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  459 */       Class<?> clazz = Reflection.getCallerClass();
/*  460 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  463 */     return getFieldAccessor(paramObject).getByte(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public char getChar(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  494 */     if (!this.override && 
/*  495 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  496 */       Class<?> clazz = Reflection.getCallerClass();
/*  497 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  500 */     return getFieldAccessor(paramObject).getChar(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public short getShort(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  531 */     if (!this.override && 
/*  532 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  533 */       Class<?> clazz = Reflection.getCallerClass();
/*  534 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  537 */     return getFieldAccessor(paramObject).getShort(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public int getInt(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  568 */     if (!this.override && 
/*  569 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  570 */       Class<?> clazz = Reflection.getCallerClass();
/*  571 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  574 */     return getFieldAccessor(paramObject).getInt(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public long getLong(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  605 */     if (!this.override && 
/*  606 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  607 */       Class<?> clazz = Reflection.getCallerClass();
/*  608 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  611 */     return getFieldAccessor(paramObject).getLong(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public float getFloat(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  642 */     if (!this.override && 
/*  643 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  644 */       Class<?> clazz = Reflection.getCallerClass();
/*  645 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  648 */     return getFieldAccessor(paramObject).getFloat(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public double getDouble(Object paramObject) throws IllegalArgumentException, IllegalAccessException {
/*  679 */     if (!this.override && 
/*  680 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  681 */       Class<?> clazz = Reflection.getCallerClass();
/*  682 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  685 */     return getFieldAccessor(paramObject).getDouble(paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void set(Object paramObject1, Object paramObject2) throws IllegalArgumentException, IllegalAccessException {
/*  758 */     if (!this.override && 
/*  759 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  760 */       Class<?> clazz = Reflection.getCallerClass();
/*  761 */       checkAccess(clazz, this.clazz, paramObject1, this.modifiers);
/*      */     } 
/*      */     
/*  764 */     getFieldAccessor(paramObject1).set(paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setBoolean(Object paramObject, boolean paramBoolean) throws IllegalArgumentException, IllegalAccessException {
/*  795 */     if (!this.override && 
/*  796 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  797 */       Class<?> clazz = Reflection.getCallerClass();
/*  798 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  801 */     getFieldAccessor(paramObject).setBoolean(paramObject, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setByte(Object paramObject, byte paramByte) throws IllegalArgumentException, IllegalAccessException {
/*  832 */     if (!this.override && 
/*  833 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  834 */       Class<?> clazz = Reflection.getCallerClass();
/*  835 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  838 */     getFieldAccessor(paramObject).setByte(paramObject, paramByte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setChar(Object paramObject, char paramChar) throws IllegalArgumentException, IllegalAccessException {
/*  869 */     if (!this.override && 
/*  870 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  871 */       Class<?> clazz = Reflection.getCallerClass();
/*  872 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  875 */     getFieldAccessor(paramObject).setChar(paramObject, paramChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setShort(Object paramObject, short paramShort) throws IllegalArgumentException, IllegalAccessException {
/*  906 */     if (!this.override && 
/*  907 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  908 */       Class<?> clazz = Reflection.getCallerClass();
/*  909 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  912 */     getFieldAccessor(paramObject).setShort(paramObject, paramShort);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setInt(Object paramObject, int paramInt) throws IllegalArgumentException, IllegalAccessException {
/*  943 */     if (!this.override && 
/*  944 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  945 */       Class<?> clazz = Reflection.getCallerClass();
/*  946 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  949 */     getFieldAccessor(paramObject).setInt(paramObject, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setLong(Object paramObject, long paramLong) throws IllegalArgumentException, IllegalAccessException {
/*  980 */     if (!this.override && 
/*  981 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/*  982 */       Class<?> clazz = Reflection.getCallerClass();
/*  983 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/*  986 */     getFieldAccessor(paramObject).setLong(paramObject, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setFloat(Object paramObject, float paramFloat) throws IllegalArgumentException, IllegalAccessException {
/* 1017 */     if (!this.override && 
/* 1018 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/* 1019 */       Class<?> clazz = Reflection.getCallerClass();
/* 1020 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/* 1023 */     getFieldAccessor(paramObject).setFloat(paramObject, paramFloat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CallerSensitive
/*      */   public void setDouble(Object paramObject, double paramDouble) throws IllegalArgumentException, IllegalAccessException {
/* 1054 */     if (!this.override && 
/* 1055 */       !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
/* 1056 */       Class<?> clazz = Reflection.getCallerClass();
/* 1057 */       checkAccess(clazz, this.clazz, paramObject, this.modifiers);
/*      */     } 
/*      */     
/* 1060 */     getFieldAccessor(paramObject).setDouble(paramObject, paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldAccessor getFieldAccessor(Object paramObject) throws IllegalAccessException {
/* 1067 */     boolean bool = this.override;
/* 1068 */     FieldAccessor fieldAccessor = bool ? this.overrideFieldAccessor : this.fieldAccessor;
/* 1069 */     return (fieldAccessor != null) ? fieldAccessor : acquireFieldAccessor(bool);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldAccessor acquireFieldAccessor(boolean paramBoolean) {
/* 1079 */     FieldAccessor fieldAccessor = null;
/* 1080 */     if (this.root != null) fieldAccessor = this.root.getFieldAccessor(paramBoolean); 
/* 1081 */     if (fieldAccessor != null) {
/* 1082 */       if (paramBoolean) {
/* 1083 */         this.overrideFieldAccessor = fieldAccessor;
/*      */       } else {
/* 1085 */         this.fieldAccessor = fieldAccessor;
/*      */       } 
/*      */     } else {
/* 1088 */       fieldAccessor = reflectionFactory.newFieldAccessor(this, paramBoolean);
/* 1089 */       setFieldAccessor(fieldAccessor, paramBoolean);
/*      */     } 
/*      */     
/* 1092 */     return fieldAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldAccessor getFieldAccessor(boolean paramBoolean) {
/* 1098 */     return paramBoolean ? this.overrideFieldAccessor : this.fieldAccessor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFieldAccessor(FieldAccessor paramFieldAccessor, boolean paramBoolean) {
/* 1104 */     if (paramBoolean) {
/* 1105 */       this.overrideFieldAccessor = paramFieldAccessor;
/*      */     } else {
/* 1107 */       this.fieldAccessor = paramFieldAccessor;
/*      */     } 
/* 1109 */     if (this.root != null) {
/* 1110 */       this.root.setFieldAccessor(paramFieldAccessor, paramBoolean);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Annotation> T getAnnotation(Class<T> paramClass) {
/* 1119 */     Objects.requireNonNull(paramClass);
/* 1120 */     return paramClass.cast(declaredAnnotations().get(paramClass));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends Annotation> T[] getAnnotationsByType(Class<T> paramClass) {
/* 1130 */     Objects.requireNonNull(paramClass);
/*      */     
/* 1132 */     return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), paramClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Annotation[] getDeclaredAnnotations() {
/* 1139 */     return AnnotationParser.toArray(declaredAnnotations());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
/*      */     Map<Class<? extends Annotation>, Annotation> map;
/* 1146 */     if ((map = this.declaredAnnotations) == null) {
/* 1147 */       synchronized (this) {
/* 1148 */         if ((map = this.declaredAnnotations) == null) {
/* 1149 */           Field field = this.root;
/* 1150 */           if (field != null) {
/* 1151 */             map = field.declaredAnnotations();
/*      */           } else {
/* 1153 */             map = AnnotationParser.parseAnnotations(this.annotations, 
/*      */                 
/* 1155 */                 SharedSecrets.getJavaLangAccess()
/* 1156 */                 .getConstantPool(getDeclaringClass()), 
/* 1157 */                 getDeclaringClass());
/*      */           } 
/* 1159 */           this.declaredAnnotations = map;
/*      */         } 
/*      */       } 
/*      */     }
/* 1163 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private native byte[] getTypeAnnotationBytes0();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotatedType getAnnotatedType() {
/* 1177 */     return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(), 
/* 1178 */         SharedSecrets.getJavaLangAccess()
/* 1179 */         .getConstantPool(getDeclaringClass()), this, 
/*      */         
/* 1181 */         getDeclaringClass(), 
/* 1182 */         getGenericType(), TypeAnnotation.TypeAnnotationTarget.FIELD);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/reflect/Field.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */