/*     */ package com.sun.xml.internal.bind.api;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public final class TypeReference
/*     */ {
/*     */   public final QName tagName;
/*     */   public final Type type;
/*     */   public final Annotation[] annotations;
/*     */   
/*     */   public TypeReference(QName tagName, Type type, Annotation... annotations) {
/*  68 */     if (tagName == null || type == null || annotations == null) {
/*  69 */       String nullArgs = "";
/*     */       
/*  71 */       if (tagName == null) nullArgs = "tagName"; 
/*  72 */       if (type == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", type" : "type"); 
/*  73 */       if (annotations == null) nullArgs = nullArgs + ((nullArgs.length() > 0) ? ", annotations" : "annotations");
/*     */       
/*  75 */       Messages.ARGUMENT_CANT_BE_NULL.format(new Object[] { nullArgs });
/*     */       
/*  77 */       throw new IllegalArgumentException(Messages.ARGUMENT_CANT_BE_NULL.format(new Object[] { nullArgs }));
/*     */     } 
/*     */     
/*  80 */     this.tagName = new QName(tagName.getNamespaceURI().intern(), tagName.getLocalPart().intern(), tagName.getPrefix());
/*  81 */     this.type = type;
/*  82 */     this.annotations = annotations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A get(Class<A> annotationType) {
/*  90 */     for (Annotation a : this.annotations) {
/*  91 */       if (a.annotationType() == annotationType)
/*  92 */         return annotationType.cast(a); 
/*     */     } 
/*  94 */     return null;
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
/*     */   public TypeReference toItemType() {
/* 106 */     Type base = (Type)Utils.REFLECTION_NAVIGATOR.getBaseClass(this.type, Collection.class);
/* 107 */     if (base == null) {
/* 108 */       return this;
/*     */     }
/* 110 */     return new TypeReference(this.tagName, (Type)Utils.REFLECTION_NAVIGATOR.getTypeArgument(base, 0), new Annotation[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 115 */     if (this == o) return true; 
/* 116 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 118 */     TypeReference that = (TypeReference)o;
/*     */     
/* 120 */     if (!Arrays.equals((Object[])this.annotations, (Object[])that.annotations)) return false; 
/* 121 */     if (!this.tagName.equals(that.tagName)) return false; 
/* 122 */     if (!this.type.equals(that.type)) return false;
/*     */     
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     int result = this.tagName.hashCode();
/* 130 */     result = 31 * result + this.type.hashCode();
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.annotations);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/api/TypeReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */