/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.FieldLocatable;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeEnumLeafInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RuntimeEnumLeafInfoImpl<T extends Enum<T>, B>
/*     */   extends EnumLeafInfoImpl<Type, Class, Field, Method>
/*     */   implements RuntimeEnumLeafInfo, Transducer<T>
/*     */ {
/*     */   private final Transducer<B> baseXducer;
/*     */   
/*     */   public Transducer<T> getTransducer() {
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final Map<B, T> parseMap = new HashMap<>();
/*     */   private final Map<T, B> printMap;
/*     */   
/*     */   RuntimeEnumLeafInfoImpl(RuntimeModelBuilder builder, Locatable upstream, Class<T> enumType) {
/*  71 */     super(builder, upstream, enumType, enumType);
/*  72 */     this.printMap = new EnumMap<>(enumType);
/*     */     
/*  74 */     this.baseXducer = ((RuntimeNonElement)this.baseType).getTransducer();
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeEnumConstantImpl createEnumConstant(String name, String literal, Field constant, EnumConstantImpl<Type, Class<?>, Field, Method> last) {
/*     */     Enum enum_;
/*     */     try {
/*     */       try {
/*  82 */         constant.setAccessible(true);
/*  83 */       } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       enum_ = (Enum)constant.get(null);
/*  89 */     } catch (IllegalAccessException e) {
/*     */       
/*  91 */       throw new IllegalAccessError(e.getMessage());
/*     */     } 
/*     */     
/*  94 */     B b = null;
/*     */     try {
/*  96 */       b = (B)this.baseXducer.parse(literal);
/*  97 */     } catch (Exception e) {
/*  98 */       this.builder.reportError(new IllegalAnnotationException(Messages.INVALID_XML_ENUM_VALUE
/*  99 */             .format(new Object[] { literal, ((Type)this.baseType.getType()).toString() }, ), e, (Locatable)new FieldLocatable(this, constant, 
/* 100 */               nav())));
/*     */     } 
/*     */     
/* 103 */     this.parseMap.put(b, (T)enum_);
/* 104 */     this.printMap.put((T)enum_, b);
/*     */     
/* 106 */     return new RuntimeEnumConstantImpl(this, name, literal, last);
/*     */   }
/*     */   
/*     */   public QName[] getTypeNames() {
/* 110 */     return new QName[] { getTypeName() };
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getClazz() {
/* 119 */     return this.clazz;
/*     */   }
/*     */   
/*     */   public boolean useNamespace() {
/* 123 */     return this.baseXducer.useNamespace();
/*     */   }
/*     */   
/*     */   public void declareNamespace(T t, XMLSerializer w) throws AccessorException {
/* 127 */     this.baseXducer.declareNamespace(this.printMap.get(t), w);
/*     */   }
/*     */   
/*     */   public CharSequence print(T t) throws AccessorException {
/* 131 */     return this.baseXducer.print(this.printMap.get(t));
/*     */   }
/*     */ 
/*     */   
/*     */   public T parse(CharSequence lexical) throws AccessorException, SAXException {
/*     */     String str;
/* 137 */     B b = (B)this.baseXducer.parse(lexical);
/*     */     
/* 139 */     if (this.tokenStringType) {
/* 140 */       str = ((String)b).trim();
/*     */     }
/*     */     
/* 143 */     return this.parseMap.get(str);
/*     */   }
/*     */   
/*     */   public void writeText(XMLSerializer w, T t, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 147 */     this.baseXducer.writeText(w, this.printMap.get(t), fieldName);
/*     */   }
/*     */   
/*     */   public void writeLeafElement(XMLSerializer w, Name tagName, T o, String fieldName) throws IOException, SAXException, XMLStreamException, AccessorException {
/* 151 */     this.baseXducer.writeLeafElement(w, tagName, this.printMap.get(o), fieldName);
/*     */   }
/*     */   
/*     */   public QName getTypeName(T instance) {
/* 155 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeEnumLeafInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */