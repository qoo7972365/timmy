/*     */ package com.sun.xml.internal.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.Locatable;
/*     */ import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.core.TypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*     */ import com.sun.xml.internal.bind.v2.runtime.FilterTransducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.IllegalAnnotationException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.InlineBinaryTransducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.MimeTypedTransducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.SchemaTypeTransducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import javax.activation.MimeType;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RuntimeModelBuilder
/*     */   extends ModelBuilder<Type, Class, Field, Method>
/*     */ {
/*     */   @Nullable
/*     */   public final JAXBContextImpl context;
/*     */   
/*     */   public RuntimeModelBuilder(JAXBContextImpl context, RuntimeAnnotationReader annotationReader, Map<Class<?>, Class<?>> subclassReplacements, String defaultNamespaceRemap) {
/*  77 */     super((AnnotationReader<Type, Class, Field, Method>)annotationReader, Utils.REFLECTION_NAVIGATOR, subclassReplacements, defaultNamespaceRemap);
/*  78 */     this.context = context;
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeNonElement getClassInfo(Class clazz, Locatable upstream) {
/*  83 */     return (RuntimeNonElement)super.getClassInfo(clazz, upstream);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeNonElement getClassInfo(Class clazz, boolean searchForSuperClass, Locatable upstream) {
/*  88 */     return (RuntimeNonElement)super.getClassInfo(clazz, searchForSuperClass, upstream);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeEnumLeafInfoImpl createEnumLeafInfo(Class<Enum> clazz, Locatable upstream) {
/*  93 */     return new RuntimeEnumLeafInfoImpl<>(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeClassInfoImpl createClassInfo(Class clazz, Locatable upstream) {
/*  98 */     return new RuntimeClassInfoImpl(this, upstream, clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeElementInfoImpl createElementInfo(RegistryInfoImpl<Type, Class<?>, Field, Method> registryInfo, Method method) throws IllegalAnnotationException {
/* 103 */     return new RuntimeElementInfoImpl(this, registryInfo, method);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeArrayInfoImpl createArrayInfo(Locatable upstream, Type arrayType) {
/* 108 */     return new RuntimeArrayInfoImpl(this, upstream, (Class)arrayType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeTypeInfoSetImpl createTypeInfoSet() {
/* 113 */     return new RuntimeTypeInfoSetImpl(this.reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public RuntimeTypeInfoSet link() {
/* 118 */     return (RuntimeTypeInfoSet)super.link();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Transducer createTransducer(RuntimeNonElementRef ref) {
/*     */     IDTransducerImpl iDTransducerImpl;
/*     */     MimeTypedTransducer mimeTypedTransducer;
/*     */     InlineBinaryTransducer inlineBinaryTransducer;
/*     */     SchemaTypeTransducer schemaTypeTransducer;
/* 130 */     Transducer<?> t = ref.getTarget().getTransducer();
/* 131 */     RuntimePropertyInfo src = ref.getSource();
/* 132 */     ID id = src.id();
/*     */     
/* 134 */     if (id == ID.IDREF) {
/* 135 */       return RuntimeBuiltinLeafInfoImpl.STRING;
/*     */     }
/* 137 */     if (id == ID.ID) {
/* 138 */       iDTransducerImpl = new IDTransducerImpl(t);
/*     */     }
/* 140 */     MimeType emt = src.getExpectedMimeType();
/* 141 */     if (emt != null) {
/* 142 */       mimeTypedTransducer = new MimeTypedTransducer((Transducer)iDTransducerImpl, emt);
/*     */     }
/* 144 */     if (src.inlineBinaryData()) {
/* 145 */       inlineBinaryTransducer = new InlineBinaryTransducer((Transducer)mimeTypedTransducer);
/*     */     }
/* 147 */     if (src.getSchemaType() != null) {
/* 148 */       if (src.getSchemaType().equals(createXSSimpleType())) {
/* 149 */         return RuntimeBuiltinLeafInfoImpl.STRING;
/*     */       }
/* 151 */       schemaTypeTransducer = new SchemaTypeTransducer((Transducer)inlineBinaryTransducer, src.getSchemaType());
/*     */     } 
/*     */     
/* 154 */     return (Transducer)schemaTypeTransducer;
/*     */   }
/*     */   
/*     */   private static QName createXSSimpleType() {
/* 158 */     return new QName("http://www.w3.org/2001/XMLSchema", "anySimpleType");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class IDTransducerImpl<ValueT>
/*     */     extends FilterTransducer<ValueT>
/*     */   {
/*     */     public IDTransducerImpl(Transducer<ValueT> core) {
/* 169 */       super(core);
/*     */     }
/*     */ 
/*     */     
/*     */     public ValueT parse(CharSequence lexical) throws AccessorException, SAXException {
/* 174 */       String value = WhiteSpaceProcessor.trim(lexical).toString();
/* 175 */       UnmarshallingContext.getInstance().addToIdTable(value);
/* 176 */       return (ValueT)this.core.parse(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/model/impl/RuntimeModelBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */