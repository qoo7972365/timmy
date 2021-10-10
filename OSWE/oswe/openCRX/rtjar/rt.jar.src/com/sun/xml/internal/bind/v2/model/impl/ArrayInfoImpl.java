/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ArrayInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Location;
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
/*     */ public class ArrayInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   extends TypeInfoImpl<TypeT, ClassDeclT, FieldT, MethodT>
/*     */   implements ArrayInfo<TypeT, ClassDeclT>, Location
/*     */ {
/*     */   private final NonElement<TypeT, ClassDeclT> itemType;
/*     */   private final QName typeName;
/*     */   private final TypeT arrayType;
/*     */   
/*     */   public ArrayInfoImpl(ModelBuilder<TypeT, ClassDeclT, FieldT, MethodT> builder, Locatable upstream, TypeT arrayType) {
/*  59 */     super(builder, upstream);
/*  60 */     this.arrayType = arrayType;
/*  61 */     TypeT componentType = (TypeT)nav().getComponentType(arrayType);
/*  62 */     this.itemType = builder.getTypeInfo(componentType, this);
/*     */     
/*  64 */     QName n = this.itemType.getTypeName();
/*  65 */     if (n == null) {
/*  66 */       builder.reportError(new IllegalAnnotationException(Messages.ANONYMOUS_ARRAY_ITEM.format(new Object[] {
/*  67 */                 nav().getTypeName(componentType) }, ), this));
/*  68 */       n = new QName("#dummy");
/*     */     } 
/*  70 */     this.typeName = ArrayInfoUtil.calcArrayTypeName(n);
/*     */   }
/*     */   
/*     */   public NonElement<TypeT, ClassDeclT> getItemType() {
/*  74 */     return this.itemType;
/*     */   }
/*     */   
/*     */   public QName getTypeName() {
/*  78 */     return this.typeName;
/*     */   }
/*     */   
/*     */   public boolean isSimpleType() {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public TypeT getType() {
/*  86 */     return this.arrayType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean canBeReferencedByIDREF() {
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 100 */     return this;
/*     */   }
/*     */   public String toString() {
/* 103 */     return nav().getTypeName(this.arrayType);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/ArrayInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */