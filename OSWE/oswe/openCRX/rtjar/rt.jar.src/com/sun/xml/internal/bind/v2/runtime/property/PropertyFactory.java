/*     */ package com.sun.xml.internal.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PropertyFactory
/*     */ {
/*     */   private static final Constructor<? extends Property>[] propImpls;
/*     */   
/*     */   static {
/*  57 */     Class[] arrayOfClass = { SingleElementLeafProperty.class, null, null, ArrayElementLeafProperty.class, null, null, SingleElementNodeProperty.class, SingleReferenceNodeProperty.class, SingleMapNodeProperty.class, ArrayElementNodeProperty.class, ArrayReferenceNodeProperty.class, null };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     propImpls = (Constructor<? extends Property>[])new Constructor[arrayOfClass.length];
/*  76 */     for (int i = 0; i < propImpls.length; i++) {
/*  77 */       if (arrayOfClass[i] != null)
/*     */       {
/*  79 */         propImpls[i] = (Constructor)arrayOfClass[i].getConstructors()[0];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Property create(JAXBContextImpl grammar, RuntimePropertyInfo info) {
/*  89 */     PropertyKind kind = info.kind();
/*     */     
/*  91 */     switch (kind) {
/*     */       case ATTRIBUTE:
/*  93 */         return new AttributeProperty(grammar, (RuntimeAttributePropertyInfo)info);
/*     */       case VALUE:
/*  95 */         return new ValueProperty(grammar, (RuntimeValuePropertyInfo)info);
/*     */       case ELEMENT:
/*  97 */         if (((RuntimeElementPropertyInfo)info).isValueList()) {
/*  98 */           return new ListElementProperty<>(grammar, (RuntimeElementPropertyInfo)info);
/*     */         }
/*     */         break;
/*     */       case REFERENCE:
/*     */       case MAP:
/*     */         break;
/*     */       default:
/*     */         assert false;
/*     */         break;
/*     */     } 
/* 108 */     boolean isCollection = info.isCollection();
/* 109 */     boolean isLeaf = isLeaf(info);
/*     */     
/* 111 */     Constructor<? extends Property> c = propImpls[(isLeaf ? 0 : 6) + (isCollection ? 3 : 0) + kind.propertyIndex];
/*     */     try {
/* 113 */       return c.newInstance(new Object[] { grammar, info });
/* 114 */     } catch (InstantiationException e) {
/* 115 */       throw new InstantiationError(e.getMessage());
/* 116 */     } catch (IllegalAccessException e) {
/* 117 */       throw new IllegalAccessError(e.getMessage());
/* 118 */     } catch (InvocationTargetException e) {
/* 119 */       Throwable t = e.getCause();
/* 120 */       if (t instanceof Error)
/* 121 */         throw (Error)t; 
/* 122 */       if (t instanceof RuntimeException) {
/* 123 */         throw (RuntimeException)t;
/*     */       }
/* 125 */       throw new AssertionError(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isLeaf(RuntimePropertyInfo info) {
/* 134 */     Collection<? extends RuntimeTypeInfo> types = info.ref();
/* 135 */     if (types.size() != 1) return false;
/*     */     
/* 137 */     RuntimeTypeInfo rti = types.iterator().next();
/* 138 */     if (!(rti instanceof RuntimeNonElement)) return false;
/*     */     
/* 140 */     if (info.id() == ID.IDREF)
/*     */     {
/* 142 */       return true;
/*     */     }
/* 144 */     if (((RuntimeNonElement)rti).getTransducer() == null)
/*     */     {
/*     */ 
/*     */       
/* 148 */       return false;
/*     */     }
/* 150 */     if (!info.getIndividualType().equals(rti.getType())) {
/* 151 */       return false;
/*     */     }
/* 153 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/PropertyFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */