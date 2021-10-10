/*     */ package com.sun.xml.internal.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.CompositeStructure;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Source;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrapperBridge<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   private JAXBRIContextWrapper parent;
/*     */   private Bridge<T> bridge;
/*     */   
/*     */   public WrapperBridge(JAXBRIContextWrapper p, Bridge<T> b) {
/*  56 */     this.parent = p;
/*  57 */     this.bridge = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  62 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  67 */     return this.bridge.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  72 */     return this.parent.typeInfo(this.bridge.getTypeReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  77 */     return this.bridge.hashCode();
/*     */   }
/*     */   
/*     */   static CompositeStructure convert(Object o) {
/*  81 */     WrapperComposite w = (WrapperComposite)o;
/*  82 */     CompositeStructure cs = new CompositeStructure();
/*  83 */     cs.values = w.values;
/*  84 */     cs.bridges = new Bridge[w.bridges.length];
/*  85 */     for (int i = 0; i < cs.bridges.length; i++) {
/*  86 */       cs.bridges[i] = ((BridgeWrapper)w.bridges[i]).getBridge();
/*     */     }
/*  88 */     return cs;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/*  93 */     this.bridge.marshal(convert(object), contentHandler, am);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/*  99 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 106 */     this.bridge.marshal(convert(object), output, nsContext, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 117 */     this.bridge.marshal(convert(object), output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     return BridgeWrapper.class.getName() + " : " + this.bridge.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 135 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 149 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 155 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/WrapperBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */