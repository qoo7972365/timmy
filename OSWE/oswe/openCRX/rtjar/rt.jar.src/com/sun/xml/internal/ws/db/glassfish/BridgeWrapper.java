/*     */ package com.sun.xml.internal.ws.db.glassfish;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.Bridge;
/*     */ import com.sun.xml.internal.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.internal.ws.spi.db.TypeInfo;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class BridgeWrapper<T>
/*     */   implements XMLBridge<T>
/*     */ {
/*     */   private JAXBRIContextWrapper parent;
/*     */   private Bridge<T> bridge;
/*     */   
/*     */   public BridgeWrapper(JAXBRIContextWrapper p, Bridge<T> b) {
/*  57 */     this.parent = p;
/*  58 */     this.bridge = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public BindingContext context() {
/*  63 */     return this.parent;
/*     */   }
/*     */   
/*     */   Bridge getBridge() {
/*  67 */     return this.bridge;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  72 */     return this.bridge.equals(obj);
/*     */   }
/*     */   
/*     */   public JAXBRIContext getContext() {
/*  76 */     return this.bridge.getContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeInfo getTypeInfo() {
/*  81 */     return this.parent.typeInfo(this.bridge.getTypeReference());
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  86 */     return this.bridge.hashCode();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(Marshaller m, T object, ContentHandler contentHandler) throws JAXBException {
/* 109 */     this.bridge.marshal(m, object, contentHandler);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, Node output) throws JAXBException {
/* 113 */     this.bridge.marshal(m, object, output);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 117 */     this.bridge.marshal(m, object, output, nsContext);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, Result result) throws JAXBException {
/* 121 */     this.bridge.marshal(m, object, result);
/*     */   }
/*     */   
/*     */   public void marshal(Marshaller m, T object, XMLStreamWriter output) throws JAXBException {
/* 125 */     this.bridge.marshal(m, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 132 */     this.bridge.marshal(object, contentHandler, am);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, ContentHandler contentHandler) throws JAXBException {
/* 148 */     this.bridge.marshal(object, contentHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, Node output) throws JAXBException {
/* 154 */     this.bridge.marshal(object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 161 */     this.bridge.marshal(object, output, nsContext, am);
/*     */   }
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 165 */     this.bridge.marshal(object, output, nsContext);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 171 */     this.bridge.marshal(object, result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/* 177 */     this.bridge.marshal(object, output, am);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output) throws JAXBException {
/* 182 */     this.bridge.marshal(object, output);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 187 */     return BridgeWrapper.class.getName() + " : " + this.bridge.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(InputStream in) throws JAXBException {
/* 211 */     return (T)this.bridge.unmarshal(in);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Node n, AttachmentUnmarshaller au) throws JAXBException {
/* 217 */     return (T)this.bridge.unmarshal(n, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(Node n) throws JAXBException {
/* 221 */     return (T)this.bridge.unmarshal(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(Source in, AttachmentUnmarshaller au) throws JAXBException {
/* 227 */     return (T)this.bridge.unmarshal(in, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(Source in) throws DatabindingException {
/*     */     try {
/* 232 */       return (T)this.bridge.unmarshal(in);
/* 233 */     } catch (JAXBException e) {
/* 234 */       throw new DatabindingException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, InputStream in) throws JAXBException {
/* 239 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller context, Node n) throws JAXBException {
/* 243 */     return (T)this.bridge.unmarshal(context, n);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, Source in) throws JAXBException {
/* 247 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */   
/*     */   public T unmarshal(Unmarshaller u, XMLStreamReader in) throws JAXBException {
/* 251 */     return (T)this.bridge.unmarshal(u, in);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in, AttachmentUnmarshaller au) throws JAXBException {
/* 257 */     return (T)this.bridge.unmarshal(in, au);
/*     */   }
/*     */   
/*     */   public final T unmarshal(XMLStreamReader in) throws JAXBException {
/* 261 */     return (T)this.bridge.unmarshal(in);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportOutputStream() {
/* 266 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/db/glassfish/BridgeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */