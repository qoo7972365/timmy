/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TypeInfo
/*     */ {
/*     */   public final QName tagName;
/*     */   public Type type;
/*     */   public final Annotation[] annotations;
/*  72 */   private Map<String, Object> properties = new HashMap<>();
/*     */   
/*     */   private boolean isGlobalElement = true;
/*     */   
/*     */   private TypeInfo parentCollectionType;
/*     */   
/*     */   private Type genericType;
/*     */   
/*     */   private boolean nillable = true;
/*     */   
/*     */   public TypeInfo(QName tagName, Type type, Annotation... annotations) {
/*  83 */     if (tagName == null || type == null || annotations == null) {
/*  84 */       String nullArgs = "";
/*     */       
/*  86 */       if (tagName == null) nullArgs = "tagName"; 
/*  87 */       if (type == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", type" : "type"); 
/*  88 */       if (annotations == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", annotations" : "annotations");
/*     */ 
/*     */ 
/*     */       
/*  92 */       throw new IllegalArgumentException("Argument(s) \"" + nullArgs + "\" can''t be null.)");
/*     */     } 
/*     */     
/*  95 */     this.tagName = new QName(tagName.getNamespaceURI().intern(), tagName.getLocalPart().intern(), tagName.getPrefix());
/*  96 */     this.type = type;
/*  97 */     if (type instanceof Class && ((Class)type).isPrimitive()) this.nillable = false; 
/*  98 */     this.annotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A get(Class<A> annotationType) {
/* 106 */     for (Annotation a : this.annotations) {
/* 107 */       if (a.annotationType() == annotationType)
/* 108 */         return annotationType.cast(a); 
/*     */     } 
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfo toItemType() {
/* 121 */     Type t = (this.genericType != null) ? this.genericType : this.type;
/* 122 */     Type base = (Type)Utils.REFLECTION_NAVIGATOR.getBaseClass(t, Collection.class);
/* 123 */     if (base == null) {
/* 124 */       return this;
/*     */     }
/* 126 */     return new TypeInfo(this.tagName, (Type)Utils.REFLECTION_NAVIGATOR.getTypeArgument(base, 0), new Annotation[0]);
/*     */   }
/*     */   
/*     */   public Map<String, Object> properties() {
/* 130 */     return this.properties;
/*     */   }
/*     */   
/*     */   public boolean isGlobalElement() {
/* 134 */     return this.isGlobalElement;
/*     */   }
/*     */   
/*     */   public void setGlobalElement(boolean isGlobalElement) {
/* 138 */     this.isGlobalElement = isGlobalElement;
/*     */   }
/*     */   
/*     */   public TypeInfo getParentCollectionType() {
/* 142 */     return this.parentCollectionType;
/*     */   }
/*     */   
/*     */   public void setParentCollectionType(TypeInfo parentCollectionType) {
/* 146 */     this.parentCollectionType = parentCollectionType;
/*     */   }
/*     */   
/*     */   public boolean isRepeatedElement() {
/* 150 */     return (this.parentCollectionType != null);
/*     */   }
/*     */   
/*     */   public Type getGenericType() {
/* 154 */     return this.genericType;
/*     */   }
/*     */   
/*     */   public void setGenericType(Type genericType) {
/* 158 */     this.genericType = genericType;
/*     */   }
/*     */   
/*     */   public boolean isNillable() {
/* 162 */     return this.nillable;
/*     */   }
/*     */   
/*     */   public void setNillable(boolean nillable) {
/* 166 */     this.nillable = nillable;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 170 */     return "TypeInfo: Type = " + this.type + ", tag = " + 
/* 171 */       this.tagName;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getItemType() {
/* 176 */     if (this.type instanceof Class && ((Class)this.type).isArray() && !byte[].class.equals(this.type)) {
/* 177 */       Type<?> componentType = ((Class)this.type).getComponentType();
/* 178 */       Type genericComponentType = null;
/* 179 */       if (this.genericType != null && this.genericType instanceof GenericArrayType) {
/* 180 */         GenericArrayType arrayType = (GenericArrayType)this.type;
/* 181 */         genericComponentType = arrayType.getGenericComponentType();
/* 182 */         componentType = arrayType.getGenericComponentType();
/*     */       } 
/* 184 */       TypeInfo ti = new TypeInfo(this.tagName, componentType, this.annotations);
/* 185 */       if (genericComponentType != null) ti.setGenericType(genericComponentType); 
/* 186 */       return ti;
/*     */     } 
/*     */     
/* 189 */     Type t = (this.genericType != null) ? this.genericType : this.type;
/* 190 */     Type base = (Type)Utils.REFLECTION_NAVIGATOR.getBaseClass(t, Collection.class);
/* 191 */     if (base != null) {
/* 192 */       return new TypeInfo(this.tagName, (Type)Utils.REFLECTION_NAVIGATOR.getTypeArgument(base, 0), this.annotations);
/*     */     }
/* 194 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/TypeInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */