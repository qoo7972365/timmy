/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeLeafInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TextLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiTypeLoader;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LeafBeanInfoImpl<BeanT>
/*     */   extends JaxBeanInfo<BeanT>
/*     */ {
/*     */   private final Loader loader;
/*     */   private final Loader loaderWithSubst;
/*     */   private final Transducer<BeanT> xducer;
/*     */   private final Name tagName;
/*     */   
/*     */   public LeafBeanInfoImpl(JAXBContextImpl grammar, RuntimeLeafInfo li) {
/*  70 */     super(grammar, (RuntimeTypeInfo)li, li.getClazz(), li.getTypeNames(), li.isElement(), true, false);
/*     */     
/*  72 */     this.xducer = li.getTransducer();
/*  73 */     this.loader = (Loader)new TextLoader(this.xducer);
/*  74 */     this.loaderWithSubst = (Loader)new XsiTypeLoader(this);
/*     */     
/*  76 */     if (isElement()) {
/*  77 */       this.tagName = grammar.nameBuilder.createElementName(li.getElementName());
/*     */     } else {
/*  79 */       this.tagName = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public QName getTypeName(BeanT instance) {
/*  84 */     QName tn = this.xducer.getTypeName(instance);
/*  85 */     if (tn != null) return tn;
/*     */     
/*  87 */     return super.getTypeName(instance);
/*     */   }
/*     */   
/*     */   public final String getElementNamespaceURI(BeanT t) {
/*  91 */     return this.tagName.nsUri;
/*     */   }
/*     */   
/*     */   public final String getElementLocalName(BeanT t) {
/*  95 */     return this.tagName.localName;
/*     */   }
/*     */   
/*     */   public BeanT createInstance(UnmarshallingContext context) {
/*  99 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final boolean reset(BeanT bean, UnmarshallingContext context) {
/* 103 */     return false;
/*     */   }
/*     */   
/*     */   public final String getId(BeanT bean, XMLSerializer target) {
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeBody(BeanT bean, XMLSerializer w) throws SAXException, IOException, XMLStreamException {
/*     */     try {
/* 115 */       this.xducer.writeText(w, bean, null);
/* 116 */     } catch (AccessorException e) {
/* 117 */       w.reportError(null, (Throwable)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void serializeAttributes(BeanT bean, XMLSerializer target) {}
/*     */ 
/*     */   
/*     */   public final void serializeRoot(BeanT bean, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/* 126 */     if (this.tagName == null) {
/* 127 */       target.reportError((ValidationEvent)new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT
/*     */ 
/*     */             
/* 130 */             .format(new Object[] { bean.getClass().getName() }, ), null, null));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 135 */       target.startElement(this.tagName, bean);
/* 136 */       target.childAsSoleContent(bean, null);
/* 137 */       target.endElement();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serializeURIs(BeanT bean, XMLSerializer target) throws SAXException {
/* 144 */     if (this.xducer.useNamespace()) {
/*     */       try {
/* 146 */         this.xducer.declareNamespace(bean, target);
/* 147 */       } catch (AccessorException e) {
/* 148 */         target.reportError(null, (Throwable)e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public final Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/* 154 */     if (typeSubstitutionCapable) {
/* 155 */       return this.loaderWithSubst;
/*     */     }
/* 157 */     return this.loader;
/*     */   }
/*     */   
/*     */   public Transducer<BeanT> getTransducer() {
/* 161 */     return this.xducer;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/LeafBeanInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */