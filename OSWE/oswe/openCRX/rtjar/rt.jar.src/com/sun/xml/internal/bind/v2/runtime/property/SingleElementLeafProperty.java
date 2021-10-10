/*     */ package com.sun.xml.internal.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.core.ID;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeNonElementRef;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeRef;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LeafPropertyLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LeafPropertyXsiLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
/*     */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SingleElementLeafProperty<BeanT>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Name tagName;
/*     */   private final boolean nillable;
/*     */   private final Accessor acc;
/*     */   private final String defaultValue;
/*     */   private final TransducedAccessor<BeanT> xacc;
/*     */   private final boolean improvedXsiTypeHandling;
/*     */   private final boolean idRef;
/*     */   
/*     */   public SingleElementLeafProperty(JAXBContextImpl context, RuntimeElementPropertyInfo prop) {
/*  71 */     super(context, (RuntimePropertyInfo)prop);
/*  72 */     RuntimeTypeRef ref = prop.getTypes().get(0);
/*  73 */     this.tagName = context.nameBuilder.createElementName(ref.getTagName());
/*  74 */     assert this.tagName != null;
/*  75 */     this.nillable = ref.isNillable();
/*  76 */     this.defaultValue = ref.getDefaultValue();
/*  77 */     this.acc = prop.getAccessor().optimize(context);
/*     */     
/*  79 */     this.xacc = TransducedAccessor.get(context, (RuntimeNonElementRef)ref);
/*  80 */     assert this.xacc != null;
/*     */     
/*  82 */     this.improvedXsiTypeHandling = context.improvedXsiTypeHandling;
/*  83 */     this.idRef = (ref.getSource().id() == ID.IDREF);
/*     */   }
/*     */   
/*     */   public void reset(BeanT o) throws AccessorException {
/*  87 */     this.acc.set(o, null);
/*     */   }
/*     */   
/*     */   public String getIdValue(BeanT bean) throws AccessorException, SAXException {
/*  91 */     return this.xacc.print(bean).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/*  96 */     boolean hasValue = this.xacc.hasValue(o);
/*     */     
/*  98 */     Object obj = null;
/*     */     
/*     */     try {
/* 101 */       obj = this.acc.getUnadapted(o);
/* 102 */     } catch (AccessorException accessorException) {}
/*     */ 
/*     */ 
/*     */     
/* 106 */     Class valueType = this.acc.getValueType();
/*     */ 
/*     */     
/* 109 */     if (xsiTypeNeeded(o, w, obj, valueType)) {
/* 110 */       w.startElement(this.tagName, outerPeer);
/* 111 */       w.childAsXsiType(obj, this.fieldName, w.grammar.getBeanInfo(valueType), false);
/* 112 */       w.endElement();
/*     */     }
/* 114 */     else if (hasValue) {
/* 115 */       this.xacc.writeLeafElement(w, this.tagName, o, this.fieldName);
/* 116 */     } else if (this.nillable) {
/* 117 */       w.startElement(this.tagName, null);
/* 118 */       w.writeXsiNilTrue();
/* 119 */       w.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean xsiTypeNeeded(BeanT bean, XMLSerializer w, Object value, Class valueTypeClass) {
/* 128 */     if (!this.improvedXsiTypeHandling)
/* 129 */       return false; 
/* 130 */     if (this.acc.isAdapted())
/* 131 */       return false; 
/* 132 */     if (value == null)
/* 133 */       return false; 
/* 134 */     if (value.getClass().equals(valueTypeClass))
/* 135 */       return false; 
/* 136 */     if (this.idRef)
/* 137 */       return false; 
/* 138 */     if (valueTypeClass.isPrimitive())
/* 139 */       return false; 
/* 140 */     return (this.acc.isValueTypeAbstractable() || isNillableAbstract(bean, w.grammar, value, valueTypeClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNillableAbstract(BeanT bean, JAXBContextImpl context, Object value, Class<Object> valueTypeClass) {
/* 147 */     if (!this.nillable)
/* 148 */       return false; 
/* 149 */     if (valueTypeClass != Object.class)
/* 150 */       return false; 
/* 151 */     if (bean.getClass() != JAXBElement.class)
/* 152 */       return false; 
/* 153 */     JAXBElement jaxbElement = (JAXBElement)bean;
/* 154 */     Class<?> valueClass = value.getClass();
/* 155 */     Class declaredTypeClass = jaxbElement.getDeclaredType();
/* 156 */     if (declaredTypeClass.equals(valueClass))
/* 157 */       return false; 
/* 158 */     if (!declaredTypeClass.isAssignableFrom(valueClass))
/* 159 */       return false; 
/* 160 */     if (!Modifier.isAbstract(declaredTypeClass.getModifiers()))
/* 161 */       return false; 
/* 162 */     return this.acc.isAbstractable(declaredTypeClass); } public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) {
/*     */     DefaultValueLoaderDecorator defaultValueLoaderDecorator;
/*     */     XsiNilLoader.Single single;
/*     */     LeafPropertyXsiLoader leafPropertyXsiLoader;
/* 166 */     LeafPropertyLoader leafPropertyLoader = new LeafPropertyLoader(this.xacc);
/* 167 */     if (this.defaultValue != null)
/* 168 */       defaultValueLoaderDecorator = new DefaultValueLoaderDecorator((Loader)leafPropertyLoader, this.defaultValue); 
/* 169 */     if (this.nillable || chain.context.allNillable) {
/* 170 */       single = new XsiNilLoader.Single((Loader)defaultValueLoaderDecorator, this.acc);
/*     */     }
/*     */     
/* 173 */     if (this.improvedXsiTypeHandling) {
/* 174 */       leafPropertyXsiLoader = new LeafPropertyXsiLoader((Loader)single, this.xacc, this.acc);
/*     */     }
/* 176 */     handlers.put(this.tagName, new ChildLoader((Loader)leafPropertyXsiLoader, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public PropertyKind getKind() {
/* 181 */     return PropertyKind.ELEMENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 186 */     if (this.tagName.equals(nsUri, localName)) {
/* 187 */       return this.acc;
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/SingleElementLeafProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */