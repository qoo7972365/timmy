/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.DomLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiTypeLoader;
/*     */ import java.io.IOException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.annotation.DomHandler;
/*     */ import javax.xml.bind.annotation.W3CDomHandler;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
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
/*     */ final class AnyTypeBeanInfo
/*     */   extends JaxBeanInfo<Object>
/*     */   implements AttributeAccessor
/*     */ {
/*     */   private boolean nilIncluded = false;
/*     */   
/*     */   public AnyTypeBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo anyTypeInfo) {
/*  60 */     super(grammar, anyTypeInfo, Object.class, new QName("http://www.w3.org/2001/XMLSchema", "anyType"), false, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     this.substLoader = new XsiTypeLoader(this);
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(Object element) {
/*     */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getElementLocalName(Object element) {
/*     */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Object createInstance(UnmarshallingContext context) {
/*     */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean reset(Object element, UnmarshallingContext context) {
/*     */     return false;
/*     */   }
/*     */   
/*     */   public String getId(Object element, XMLSerializer target) {
/*     */     return null;
/*     */   }
/*     */   
/*     */   public void serializeBody(Object element, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/*     */     NodeList childNodes = ((Element)element).getChildNodes();
/*     */     int len = childNodes.getLength();
/*     */     for (int i = 0; i < len; i++) {
/*     */       Node child = childNodes.item(i);
/*     */       switch (child.getNodeType()) {
/*     */         case 3:
/*     */         case 4:
/*     */           target.text(child.getNodeValue(), (String)null);
/*     */           break;
/*     */         case 1:
/*     */           target.writeDom((Element)child, (DomHandler<Element, ?>)domHandler, null, null);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeAttributes(Object element, XMLSerializer target) throws SAXException {
/*     */     NamedNodeMap al = ((Element)element).getAttributes();
/*     */     int len = al.getLength();
/*     */     for (int i = 0; i < len; i++) {
/*     */       Attr a = (Attr)al.item(i);
/*     */       String uri = a.getNamespaceURI();
/*     */       if (uri == null)
/*     */         uri = ""; 
/*     */       String local = a.getLocalName();
/*     */       String name = a.getName();
/*     */       if (local == null)
/*     */         local = name; 
/*     */       if (uri.equals("http://www.w3.org/2001/XMLSchema-instance") && "nil".equals(local))
/*     */         this.isNilIncluded = true; 
/*     */       if (!name.startsWith("xmlns"))
/*     */         target.attribute(uri, local, a.getValue()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serializeRoot(Object element, XMLSerializer target) throws SAXException {
/*     */     target.reportError((ValidationEvent)new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT.format(new Object[] { element.getClass().getName() }, ), null, null));
/*     */   }
/*     */   
/*     */   public void serializeURIs(Object element, XMLSerializer target) {
/*     */     NamedNodeMap al = ((Element)element).getAttributes();
/*     */     int len = al.getLength();
/*     */     NamespaceContext2 context = target.getNamespaceContext();
/*     */     for (int i = 0; i < len; i++) {
/*     */       Attr a = (Attr)al.item(i);
/*     */       if ("xmlns".equals(a.getPrefix())) {
/*     */         context.force(a.getValue(), a.getLocalName());
/*     */       } else if ("xmlns".equals(a.getName())) {
/*     */         if (element instanceof Element) {
/*     */           context.declareNamespace(a.getValue(), null, false);
/*     */         } else {
/*     */           context.force(a.getValue(), "");
/*     */         } 
/*     */       } else {
/*     */         String nsUri = a.getNamespaceURI();
/*     */         if (nsUri != null && nsUri.length() > 0)
/*     */           context.declareNamespace(nsUri, a.getPrefix(), true); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Transducer<Object> getTransducer() {
/*     */     return null;
/*     */   }
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/*     */     if (typeSubstitutionCapable)
/*     */       return (Loader)this.substLoader; 
/*     */     return (Loader)domLoader;
/*     */   }
/*     */   
/*     */   private static final W3CDomHandler domHandler = new W3CDomHandler();
/*     */   private static final DomLoader domLoader = new DomLoader((DomHandler)domHandler);
/*     */   private final XsiTypeLoader substLoader;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/AnyTypeBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */