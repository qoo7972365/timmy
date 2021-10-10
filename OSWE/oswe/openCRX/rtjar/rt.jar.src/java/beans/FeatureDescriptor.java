/*     */ package java.beans;
/*     */ 
/*     */ import com.sun.beans.TypeResolver;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FeatureDescriptor
/*     */ {
/*     */   private static final String TRANSIENT = "transient";
/*     */   private Reference<? extends Class<?>> classRef;
/*     */   private boolean expert;
/*     */   private boolean hidden;
/*     */   private boolean preferred;
/*     */   private String shortDescription;
/*     */   private String name;
/*     */   private String displayName;
/*     */   private Hashtable<String, Object> table;
/*     */   
/*     */   public FeatureDescriptor() {}
/*     */   
/*     */   public String getName() {
/*  68 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String paramString) {
/*  77 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/*  87 */     if (this.displayName == null) {
/*  88 */       return getName();
/*     */     }
/*  90 */     return this.displayName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayName(String paramString) {
/* 100 */     this.displayName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExpert() {
/* 110 */     return this.expert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpert(boolean paramBoolean) {
/* 120 */     this.expert = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 130 */     return this.hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHidden(boolean paramBoolean) {
/* 140 */     this.hidden = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPreferred() {
/* 150 */     return this.preferred;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPreferred(boolean paramBoolean) {
/* 161 */     this.preferred = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortDescription() {
/* 171 */     if (this.shortDescription == null) {
/* 172 */       return getDisplayName();
/*     */     }
/* 174 */     return this.shortDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShortDescription(String paramString) {
/* 184 */     this.shortDescription = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String paramString, Object paramObject) {
/* 194 */     getTable().put(paramString, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(String paramString) {
/* 205 */     return (this.table != null) ? this.table
/* 206 */       .get(paramString) : null;
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
/*     */   public Enumeration<String> attributeNames() {
/* 218 */     return getTable().keys();
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
/*     */   FeatureDescriptor(FeatureDescriptor paramFeatureDescriptor1, FeatureDescriptor paramFeatureDescriptor2) {
/* 232 */     paramFeatureDescriptor1.expert |= paramFeatureDescriptor2.expert;
/* 233 */     paramFeatureDescriptor1.hidden |= paramFeatureDescriptor2.hidden;
/* 234 */     paramFeatureDescriptor1.preferred |= paramFeatureDescriptor2.preferred;
/* 235 */     this.name = paramFeatureDescriptor2.name;
/* 236 */     this.shortDescription = paramFeatureDescriptor1.shortDescription;
/* 237 */     if (paramFeatureDescriptor2.shortDescription != null) {
/* 238 */       this.shortDescription = paramFeatureDescriptor2.shortDescription;
/*     */     }
/* 240 */     this.displayName = paramFeatureDescriptor1.displayName;
/* 241 */     if (paramFeatureDescriptor2.displayName != null) {
/* 242 */       this.displayName = paramFeatureDescriptor2.displayName;
/*     */     }
/* 244 */     this.classRef = paramFeatureDescriptor1.classRef;
/* 245 */     if (paramFeatureDescriptor2.classRef != null) {
/* 246 */       this.classRef = paramFeatureDescriptor2.classRef;
/*     */     }
/* 248 */     addTable(paramFeatureDescriptor1.table);
/* 249 */     addTable(paramFeatureDescriptor2.table);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   FeatureDescriptor(FeatureDescriptor paramFeatureDescriptor) {
/* 257 */     this.expert = paramFeatureDescriptor.expert;
/* 258 */     this.hidden = paramFeatureDescriptor.hidden;
/* 259 */     this.preferred = paramFeatureDescriptor.preferred;
/* 260 */     this.name = paramFeatureDescriptor.name;
/* 261 */     this.shortDescription = paramFeatureDescriptor.shortDescription;
/* 262 */     this.displayName = paramFeatureDescriptor.displayName;
/* 263 */     this.classRef = paramFeatureDescriptor.classRef;
/*     */     
/* 265 */     addTable(paramFeatureDescriptor.table);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTable(Hashtable<String, Object> paramHashtable) {
/* 275 */     if (paramHashtable != null && !paramHashtable.isEmpty()) {
/* 276 */       getTable().putAll(paramHashtable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Hashtable<String, Object> getTable() {
/* 286 */     if (this.table == null) {
/* 287 */       this.table = new Hashtable<>();
/*     */     }
/* 289 */     return this.table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setTransient(Transient paramTransient) {
/* 300 */     if (paramTransient != null && null == getValue("transient")) {
/* 301 */       setValue("transient", Boolean.valueOf(paramTransient.value()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isTransient() {
/* 312 */     Object object = getValue("transient");
/* 313 */     return (object instanceof Boolean) ? ((Boolean)object)
/* 314 */       .booleanValue() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setClass0(Class<?> paramClass) {
/* 321 */     this.classRef = getWeakReference(paramClass);
/*     */   }
/*     */   
/*     */   Class<?> getClass0() {
/* 325 */     return (this.classRef != null) ? this.classRef
/* 326 */       .get() : null;
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
/*     */   static <T> Reference<T> getSoftReference(T paramT) {
/* 338 */     return (paramT != null) ? new SoftReference<>(paramT) : null;
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
/*     */   static <T> Reference<T> getWeakReference(T paramT) {
/* 351 */     return (paramT != null) ? new WeakReference<>(paramT) : null;
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
/*     */   static Class<?> getReturnType(Class<?> paramClass, Method paramMethod) {
/* 367 */     if (paramClass == null) {
/* 368 */       paramClass = paramMethod.getDeclaringClass();
/*     */     }
/* 370 */     return TypeResolver.erase(TypeResolver.resolveInClass(paramClass, paramMethod.getGenericReturnType()));
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
/*     */   static Class<?>[] getParameterTypes(Class<?> paramClass, Method paramMethod) {
/* 384 */     if (paramClass == null) {
/* 385 */       paramClass = paramMethod.getDeclaringClass();
/*     */     }
/* 387 */     return TypeResolver.erase(TypeResolver.resolveInClass(paramClass, paramMethod.getGenericParameterTypes()));
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
/*     */   public String toString() {
/* 406 */     StringBuilder stringBuilder = new StringBuilder(getClass().getName());
/* 407 */     stringBuilder.append("[name=").append(this.name);
/* 408 */     appendTo(stringBuilder, "displayName", this.displayName);
/* 409 */     appendTo(stringBuilder, "shortDescription", this.shortDescription);
/* 410 */     appendTo(stringBuilder, "preferred", this.preferred);
/* 411 */     appendTo(stringBuilder, "hidden", this.hidden);
/* 412 */     appendTo(stringBuilder, "expert", this.expert);
/* 413 */     if (this.table != null && !this.table.isEmpty()) {
/* 414 */       stringBuilder.append("; values={");
/* 415 */       for (Map.Entry<String, Object> entry : this.table.entrySet()) {
/* 416 */         stringBuilder.append((String)entry.getKey()).append("=").append(entry.getValue()).append("; ");
/*     */       }
/* 418 */       stringBuilder.setLength(stringBuilder.length() - 2);
/* 419 */       stringBuilder.append("}");
/*     */     } 
/* 421 */     appendTo(stringBuilder);
/* 422 */     return stringBuilder.append("]").toString();
/*     */   }
/*     */ 
/*     */   
/*     */   void appendTo(StringBuilder paramStringBuilder) {}
/*     */   
/*     */   static void appendTo(StringBuilder paramStringBuilder, String paramString, Reference<?> paramReference) {
/* 429 */     if (paramReference != null) {
/* 430 */       appendTo(paramStringBuilder, paramString, paramReference.get());
/*     */     }
/*     */   }
/*     */   
/*     */   static void appendTo(StringBuilder paramStringBuilder, String paramString, Object paramObject) {
/* 435 */     if (paramObject != null) {
/* 436 */       paramStringBuilder.append("; ").append(paramString).append("=").append(paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   static void appendTo(StringBuilder paramStringBuilder, String paramString, boolean paramBoolean) {
/* 441 */     if (paramBoolean)
/* 442 */       paramStringBuilder.append("; ").append(paramString); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/beans/FeatureDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */