/*     */ package com.sun.xml.internal.ws.spi.db;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.istack.internal.Nullable;
/*     */ import com.sun.xml.internal.bind.api.BridgeContext;
/*     */ import com.sun.xml.internal.bind.v2.runtime.BridgeContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OldBridge<T>
/*     */ {
/*     */   protected final JAXBContextImpl context;
/*     */   
/*     */   protected OldBridge(JAXBContextImpl context) {
/*  70 */     this.context = context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public BindingContext getContext() {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, XMLStreamWriter output) throws JAXBException {
/*  93 */     marshal(object, output, (AttachmentMarshaller)null);
/*     */   }
/*     */   public final void marshal(T object, XMLStreamWriter output, AttachmentMarshaller am) throws JAXBException {
/*  96 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/*  97 */     m.setAttachmentMarshaller(am);
/*  98 */     marshal(m, object, output);
/*  99 */     m.setAttachmentMarshaller(null);
/* 100 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, XMLStreamWriter output) throws JAXBException {
/* 104 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, XMLStreamWriter paramXMLStreamWriter) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 124 */     marshal(object, output, nsContext, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshal(T object, OutputStream output, NamespaceContext nsContext, AttachmentMarshaller am) throws JAXBException {
/* 130 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 131 */     m.setAttachmentMarshaller(am);
/* 132 */     marshal(m, object, output, nsContext);
/* 133 */     m.setAttachmentMarshaller(null);
/* 134 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, OutputStream output, NamespaceContext nsContext) throws JAXBException {
/* 138 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output, nsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, OutputStream paramOutputStream, NamespaceContext paramNamespaceContext) throws JAXBException;
/*     */   
/*     */   public final void marshal(T object, Node output) throws JAXBException {
/* 145 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 146 */     marshal(m, object, output);
/* 147 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Node output) throws JAXBException {
/* 151 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, output);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Node paramNode) throws JAXBException;
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler) throws JAXBException {
/* 161 */     marshal(object, contentHandler, (AttachmentMarshaller)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void marshal(T object, ContentHandler contentHandler, AttachmentMarshaller am) throws JAXBException {
/* 167 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 168 */     m.setAttachmentMarshaller(am);
/* 169 */     marshal(m, object, contentHandler);
/* 170 */     m.setAttachmentMarshaller(null);
/* 171 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, ContentHandler contentHandler) throws JAXBException {
/* 174 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, contentHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, ContentHandler paramContentHandler) throws JAXBException;
/*     */ 
/*     */   
/*     */   public final void marshal(T object, Result result) throws JAXBException {
/* 182 */     Marshaller m = (Marshaller)this.context.marshallerPool.take();
/* 183 */     marshal(m, object, result);
/* 184 */     this.context.marshallerPool.recycle(m);
/*     */   }
/*     */   public final void marshal(@NotNull BridgeContext context, T object, Result result) throws JAXBException {
/* 187 */     marshal((Marshaller)((BridgeContextImpl)context).marshaller, object, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void marshal(@NotNull Marshaller paramMarshaller, T paramT, Result paramResult) throws JAXBException;
/*     */   
/*     */   private T exit(T r, Unmarshaller u) {
/* 194 */     u.setAttachmentUnmarshaller(null);
/* 195 */     this.context.unmarshallerPool.recycle(u);
/* 196 */     return r;
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
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull XMLStreamReader in) throws JAXBException {
/* 216 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull XMLStreamReader in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 222 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 223 */     u.setAttachmentUnmarshaller(au);
/* 224 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull XMLStreamReader in) throws JAXBException {
/* 227 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull XMLStreamReader paramXMLStreamReader) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Source in) throws JAXBException {
/* 248 */     return unmarshal(in, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Source in, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 254 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 255 */     u.setAttachmentUnmarshaller(au);
/* 256 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Source in) throws JAXBException {
/* 259 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull Source paramSource) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull InputStream in) throws JAXBException {
/* 280 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 281 */     return exit(unmarshal(u, in), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull InputStream in) throws JAXBException {
/* 284 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, in);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull InputStream paramInputStream) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Node n) throws JAXBException {
/* 303 */     return unmarshal(n, (AttachmentUnmarshaller)null);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public final T unmarshal(@NotNull Node n, @Nullable AttachmentUnmarshaller au) throws JAXBException {
/* 309 */     Unmarshaller u = (Unmarshaller)this.context.unmarshallerPool.take();
/* 310 */     u.setAttachmentUnmarshaller(au);
/* 311 */     return exit(unmarshal(u, n), u);
/*     */   } @NotNull
/*     */   public final T unmarshal(@NotNull BridgeContext context, @NotNull Node n) throws JAXBException {
/* 314 */     return unmarshal((Unmarshaller)((BridgeContextImpl)context).unmarshaller, n);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public abstract T unmarshal(@NotNull Unmarshaller paramUnmarshaller, @NotNull Node paramNode) throws JAXBException;
/*     */   
/*     */   public abstract TypeInfo getTypeReference();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/spi/db/OldBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */