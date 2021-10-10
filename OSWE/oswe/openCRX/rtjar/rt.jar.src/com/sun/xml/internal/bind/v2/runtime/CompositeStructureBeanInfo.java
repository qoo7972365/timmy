/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.CompositeStructure;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
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
/*     */ public class CompositeStructureBeanInfo
/*     */   extends JaxBeanInfo<CompositeStructure>
/*     */ {
/*     */   public CompositeStructureBeanInfo(JAXBContextImpl context) {
/*  46 */     super(context, null, CompositeStructure.class, false, true, false);
/*     */   }
/*     */   
/*     */   public String getElementNamespaceURI(CompositeStructure o) {
/*  50 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getElementLocalName(CompositeStructure o) {
/*  54 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public CompositeStructure createInstance(UnmarshallingContext context) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException {
/*  58 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean reset(CompositeStructure o, UnmarshallingContext context) throws SAXException {
/*  62 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getId(CompositeStructure o, XMLSerializer target) throws SAXException {
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Loader getLoader(JAXBContextImpl context, boolean typeSubstitutionCapable) {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void serializeRoot(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/*  75 */     target.reportError((ValidationEvent)new ValidationEventImpl(1, Messages.UNABLE_TO_MARSHAL_NON_ELEMENT
/*     */ 
/*     */           
/*  78 */           .format(new Object[] { o.getClass().getName() }, ), null, null));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeURIs(CompositeStructure o, XMLSerializer target) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeAttributes(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeBody(CompositeStructure o, XMLSerializer target) throws SAXException, IOException, XMLStreamException {
/*  92 */     int len = o.bridges.length;
/*  93 */     for (int i = 0; i < len; i++) {
/*  94 */       Object value = o.values[i];
/*  95 */       InternalBridge<Object> bi = (InternalBridge)o.bridges[i];
/*  96 */       bi.marshal(value, target);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Transducer<CompositeStructure> getTransducer() {
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/CompositeStructureBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */