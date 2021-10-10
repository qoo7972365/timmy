/*     */ package javax.management.openmbean;
/*     */ 
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.ImmutableDescriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OpenType<T>
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -9195195325186646468L;
/*  96 */   public static final List<String> ALLOWED_CLASSNAMES_LIST = Collections.unmodifiableList(
/*  97 */       Arrays.asList(new String[] {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           "java.lang.Void", "java.lang.Boolean", "java.lang.Character", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.String",
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 112 */           "java.math.BigDecimal", "java.math.BigInteger", "java.util.Date", "javax.management.ObjectName", CompositeData.class.getName(), TabularData.class
/* 113 */           .getName()
/*     */         }));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 120 */   public static final String[] ALLOWED_CLASSNAMES = ALLOWED_CLASSNAMES_LIST
/* 121 */     .<String>toArray(new String[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String className;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String description;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String typeName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient boolean isArray = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Descriptor descriptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OpenType(String paramString1, String paramString2, String paramString3) throws OpenDataException {
/* 180 */     checkClassNameOverride();
/* 181 */     this.typeName = valid("typeName", paramString2);
/* 182 */     this.description = valid("description", paramString3);
/* 183 */     this.className = validClassName(paramString1);
/* 184 */     this.isArray = (this.className != null && this.className.startsWith("["));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   OpenType(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
/* 190 */     this.className = valid("className", paramString1);
/* 191 */     this.typeName = valid("typeName", paramString2);
/* 192 */     this.description = valid("description", paramString3);
/* 193 */     this.isArray = paramBoolean;
/*     */   }
/*     */   
/*     */   private void checkClassNameOverride() throws SecurityException {
/* 197 */     if (getClass().getClassLoader() == null)
/*     */       return; 
/* 199 */     if (overridesGetClassName(getClass())) {
/* 200 */       GetPropertyAction getPropertyAction = new GetPropertyAction("jmx.extend.open.types");
/*     */       
/* 202 */       if (AccessController.doPrivileged(getPropertyAction) == null) {
/* 203 */         throw new SecurityException("Cannot override getClassName() unless -Djmx.extend.open.types");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean overridesGetClassName(final Class<?> c) {
/* 210 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Boolean run() {
/*     */             try {
/* 213 */               return Boolean.valueOf((c.getMethod("getClassName", new Class[0]).getDeclaringClass() != OpenType.class));
/*     */             }
/* 215 */             catch (Exception exception) {
/* 216 */               return Boolean.valueOf(true);
/*     */             } 
/*     */           }
/*     */         })).booleanValue();
/*     */   }
/*     */   private static String validClassName(String paramString) throws OpenDataException {
/*     */     String str;
/* 223 */     paramString = valid("className", paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     byte b = 0;
/* 229 */     while (paramString.startsWith("[", b)) {
/* 230 */       b++;
/*     */     }
/*     */     
/* 233 */     boolean bool = false;
/* 234 */     if (b > 0) {
/* 235 */       if (paramString.startsWith("L", b) && paramString.endsWith(";")) {
/*     */ 
/*     */         
/* 238 */         str = paramString.substring(b + 1, paramString.length() - 1);
/* 239 */       } else if (b == paramString.length() - 1) {
/*     */         
/* 241 */         str = paramString.substring(b, paramString.length());
/* 242 */         bool = true;
/*     */       } else {
/* 244 */         throw new OpenDataException("Argument className=\"" + paramString + "\" is not a valid class name");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 249 */       str = paramString;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 254 */     boolean bool1 = false;
/* 255 */     if (bool) {
/* 256 */       bool1 = ArrayType.isPrimitiveContentType(str);
/*     */     } else {
/* 258 */       bool1 = ALLOWED_CLASSNAMES_LIST.contains(str);
/*     */     } 
/* 260 */     if (!bool1) {
/* 261 */       throw new OpenDataException("Argument className=\"" + paramString + "\" is not one of the allowed Java class names for open data.");
/*     */     }
/*     */ 
/*     */     
/* 265 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String valid(String paramString1, String paramString2) {
/* 271 */     if (paramString2 == null || (paramString2 = paramString2.trim()).equals("")) {
/* 272 */       throw new IllegalArgumentException("Argument " + paramString1 + " cannot be null or empty");
/*     */     }
/* 274 */     return paramString2;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized Descriptor getDescriptor() {
/* 279 */     if (this.descriptor == null) {
/* 280 */       this.descriptor = new ImmutableDescriptor(new String[] { "openType" }, new Object[] { this });
/*     */     }
/*     */     
/* 283 */     return this.descriptor;
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
/*     */   public String getClassName() {
/* 307 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   String safeGetClassName() {
/* 313 */     return this.className;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeName() {
/* 323 */     return this.typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 333 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArray() {
/* 344 */     return this.isArray;
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
/*     */   public abstract boolean isValue(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isAssignableFrom(OpenType<?> paramOpenType) {
/* 367 */     return equals(paramOpenType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean equals(Object paramObject);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int hashCode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String toString();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*     */     String str1, str2, str3;
/* 396 */     checkClassNameOverride();
/* 397 */     ObjectInputStream.GetField getField = paramObjectInputStream.readFields();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 403 */       str1 = validClassName((String)getField.get("className", (Object)null));
/*     */       
/* 405 */       str2 = valid("description", (String)getField.get("description", (Object)null));
/*     */       
/* 407 */       str3 = valid("typeName", (String)getField.get("typeName", (Object)null));
/* 408 */     } catch (Exception exception) {
/* 409 */       InvalidObjectException invalidObjectException = new InvalidObjectException(exception.getMessage());
/* 410 */       invalidObjectException.initCause(exception);
/* 411 */       throw invalidObjectException;
/*     */     } 
/* 413 */     this.className = str1;
/* 414 */     this.description = str2;
/* 415 */     this.typeName = str3;
/* 416 */     this.isArray = this.className.startsWith("[");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/openmbean/OpenType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */