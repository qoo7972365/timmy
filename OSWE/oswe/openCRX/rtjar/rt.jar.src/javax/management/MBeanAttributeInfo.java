/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import com.sun.jmx.mbeanserver.Introspector;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanAttributeInfo
/*     */   extends MBeanFeatureInfo
/*     */   implements Cloneable
/*     */ {
/*     */   private static final long serialVersionUID;
/*     */   
/*     */   static {
/*  58 */     long l = 8644704819898565848L;
/*     */     try {
/*  60 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.serial.form");
/*  61 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*  62 */       if ("1.0".equals(str))
/*  63 */         l = 7043855487133450673L; 
/*  64 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  67 */     serialVersionUID = l;
/*     */   }
/*     */   
/*  70 */   static final MBeanAttributeInfo[] NO_ATTRIBUTES = new MBeanAttributeInfo[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String attributeType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isWrite;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isRead;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean is;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanAttributeInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 116 */     this(paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2, paramBoolean3, (Descriptor)null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanAttributeInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Descriptor paramDescriptor) {
/* 147 */     super(paramString1, paramString3, paramDescriptor);
/*     */     
/* 149 */     this.attributeType = paramString2;
/* 150 */     this.isRead = paramBoolean1;
/* 151 */     this.isWrite = paramBoolean2;
/* 152 */     if (paramBoolean3 && !paramBoolean1) {
/* 153 */       throw new IllegalArgumentException("Cannot have an \"is\" getter for a non-readable attribute");
/*     */     }
/*     */     
/* 156 */     if (paramBoolean3 && !paramString2.equals("java.lang.Boolean") && 
/* 157 */       !paramString2.equals("boolean")) {
/* 158 */       throw new IllegalArgumentException("Cannot have an \"is\" getter for a non-boolean attribute");
/*     */     }
/*     */     
/* 161 */     this.is = paramBoolean3;
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
/*     */ 
/*     */   
/*     */   public MBeanAttributeInfo(String paramString1, String paramString2, Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 184 */     this(paramString1, 
/* 185 */         attributeType(paramMethod1, paramMethod2), paramString2, (paramMethod1 != null), (paramMethod2 != null), 
/*     */ 
/*     */ 
/*     */         
/* 189 */         isIs(paramMethod1), 
/* 190 */         ImmutableDescriptor.union(new Descriptor[] { Introspector.descriptorForElement(paramMethod1), 
/* 191 */             Introspector.descriptorForElement(paramMethod2) }));
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
/*     */   public Object clone() {
/*     */     try {
/* 206 */       return super.clone();
/* 207 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 209 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 219 */     return this.attributeType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReadable() {
/* 228 */     return this.isRead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWritable() {
/* 237 */     return this.isWrite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIs() {
/* 246 */     return this.is;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     String str;
/* 251 */     if (isReadable()) {
/* 252 */       if (isWritable())
/* 253 */       { str = "read/write"; }
/*     */       else
/* 255 */       { str = "read-only"; } 
/* 256 */     } else if (isWritable()) {
/* 257 */       str = "write-only";
/*     */     } else {
/* 259 */       str = "no-access";
/*     */     } 
/* 261 */     return 
/* 262 */       getClass().getName() + "[description=" + 
/* 263 */       getDescription() + ", name=" + 
/* 264 */       getName() + ", type=" + 
/* 265 */       getType() + ", " + str + ", " + (
/*     */       
/* 267 */       isIs() ? "isIs, " : "") + "descriptor=" + 
/* 268 */       getDescriptor() + "]";
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
/*     */   public boolean equals(Object paramObject) {
/* 284 */     if (paramObject == this)
/* 285 */       return true; 
/* 286 */     if (!(paramObject instanceof MBeanAttributeInfo))
/* 287 */       return false; 
/* 288 */     MBeanAttributeInfo mBeanAttributeInfo = (MBeanAttributeInfo)paramObject;
/* 289 */     return (Objects.equals(mBeanAttributeInfo.getName(), getName()) && 
/* 290 */       Objects.equals(mBeanAttributeInfo.getType(), getType()) && 
/* 291 */       Objects.equals(mBeanAttributeInfo.getDescription(), getDescription()) && 
/* 292 */       Objects.equals(mBeanAttributeInfo.getDescriptor(), getDescriptor()) && mBeanAttributeInfo
/* 293 */       .isReadable() == isReadable() && mBeanAttributeInfo
/* 294 */       .isWritable() == isWritable() && mBeanAttributeInfo
/* 295 */       .isIs() == isIs());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 305 */     return Objects.hash(new Object[] { getName(), getType() });
/*     */   }
/*     */   
/*     */   private static boolean isIs(Method paramMethod) {
/* 309 */     return (paramMethod != null && paramMethod
/* 310 */       .getName().startsWith("is") && (paramMethod
/* 311 */       .getReturnType().equals(boolean.class) || paramMethod
/* 312 */       .getReturnType().equals(Boolean.class)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String attributeType(Method paramMethod1, Method paramMethod2) throws IntrospectionException {
/* 320 */     Class<?> clazz = null;
/*     */     
/* 322 */     if (paramMethod1 != null) {
/* 323 */       if ((paramMethod1.getParameterTypes()).length != 0) {
/* 324 */         throw new IntrospectionException("bad getter arg count");
/*     */       }
/* 326 */       clazz = paramMethod1.getReturnType();
/* 327 */       if (clazz == void.class) {
/* 328 */         throw new IntrospectionException("getter " + paramMethod1.getName() + " returns void");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 333 */     if (paramMethod2 != null) {
/* 334 */       Class[] arrayOfClass = paramMethod2.getParameterTypes();
/* 335 */       if (arrayOfClass.length != 1) {
/* 336 */         throw new IntrospectionException("bad setter arg count");
/*     */       }
/* 338 */       if (clazz == null) {
/* 339 */         clazz = arrayOfClass[0];
/* 340 */       } else if (clazz != arrayOfClass[0]) {
/* 341 */         throw new IntrospectionException("type mismatch between getter and setter");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 346 */     if (clazz == null) {
/* 347 */       throw new IntrospectionException("getter and setter cannot both be null");
/*     */     }
/*     */ 
/*     */     
/* 351 */     return clazz.getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanAttributeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */