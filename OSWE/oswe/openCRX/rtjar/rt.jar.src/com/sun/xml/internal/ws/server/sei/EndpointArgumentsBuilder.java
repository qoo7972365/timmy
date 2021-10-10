/*     */ package com.sun.xml.internal.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Attachment;
/*     */ import com.sun.xml.internal.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.model.ParameterBinding;
/*     */ import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.internal.ws.encoding.DataHandlerDataSource;
/*     */ import com.sun.xml.internal.ws.encoding.StringDataContentHandler;
/*     */ import com.sun.xml.internal.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import com.sun.xml.internal.ws.resources.ServerMessages;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.internal.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.internal.ws.spi.db.RepeatedElementBridge;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil;
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.jws.WebParam;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointArgumentsBuilder
/*     */ {
/*     */   static final class None
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private None() {}
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) {
/* 101 */       msg.consume();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   public static final EndpointArgumentsBuilder NONE = new None();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getVMUninitializedValue(Type type) {
/* 121 */     return primitiveUninitializedValues.get(type);
/*     */   }
/*     */   
/* 124 */   private static final Map<Class, Object> primitiveUninitializedValues = (Map)new HashMap<>();
/*     */   
/*     */   static {
/* 127 */     Map<Class<?>, Object> m = primitiveUninitializedValues;
/* 128 */     m.put(int.class, Integer.valueOf(0));
/* 129 */     m.put(char.class, Character.valueOf(false));
/* 130 */     m.put(byte.class, Byte.valueOf((byte)0));
/* 131 */     m.put(short.class, Short.valueOf((short)0));
/* 132 */     m.put(long.class, Long.valueOf(0L));
/* 133 */     m.put(float.class, Float.valueOf(0.0F));
/* 134 */     m.put(double.class, Double.valueOf(0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName wrapperName;
/*     */ 
/*     */   
/*     */   static final class WrappedPartBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */     
/*     */     public WrappedPartBuilder(XMLBridge bridge, EndpointValueSetter setter) {
/* 150 */       this.bridge = bridge;
/* 151 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     void readRequest(Object[] args, XMLStreamReader r, AttachmentSet att) throws JAXBException {
/* 155 */       Object obj = null;
/* 156 */       AttachmentUnmarshallerImpl au = (att != null) ? new AttachmentUnmarshallerImpl(att) : null;
/* 157 */       if (this.bridge instanceof RepeatedElementBridge) {
/* 158 */         RepeatedElementBridge rbridge = (RepeatedElementBridge)this.bridge;
/* 159 */         ArrayList<Object> list = new ArrayList();
/* 160 */         QName name = r.getName();
/* 161 */         while (r.getEventType() == 1 && name.equals(r.getName())) {
/* 162 */           list.add(rbridge.unmarshal(r, (AttachmentUnmarshaller)au));
/* 163 */           XMLStreamReaderUtil.toNextTag(r, name);
/*     */         } 
/* 165 */         obj = rbridge.collectionHandler().convert(list);
/*     */       } else {
/* 167 */         obj = this.bridge.unmarshal(r, (AttachmentUnmarshaller)au);
/*     */       } 
/* 169 */       this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */   
/* 173 */   protected Map<QName, WrappedPartBuilder> wrappedParts = null;
/*     */   
/*     */   protected void readWrappedRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 176 */     if (!msg.hasPayload()) {
/* 177 */       throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */     }
/* 179 */     XMLStreamReader reader = msg.readPayload();
/* 180 */     XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 181 */     reader.nextTag();
/* 182 */     while (reader.getEventType() == 1) {
/*     */       
/* 184 */       QName name = reader.getName();
/* 185 */       WrappedPartBuilder part = this.wrappedParts.get(name);
/* 186 */       if (part == null) {
/*     */         
/* 188 */         XMLStreamReaderUtil.skipElement(reader);
/* 189 */         reader.nextTag();
/*     */       } else {
/* 191 */         part.readRequest(args, reader, msg.getAttachments());
/*     */       } 
/* 193 */       XMLStreamReaderUtil.toNextTag(reader, name);
/*     */     } 
/*     */ 
/*     */     
/* 197 */     reader.close();
/* 198 */     XMLStreamReaderFactory.recycle(reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NullSetter
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final EndpointValueSetter setter;
/*     */     private final Object nullValue;
/*     */     
/*     */     public NullSetter(EndpointValueSetter setter, Object nullValue) {
/* 209 */       assert setter != null;
/* 210 */       this.nullValue = nullValue;
/* 211 */       this.setter = setter;
/*     */     }
/*     */     public void readRequest(Message msg, Object[] args) {
/* 214 */       this.setter.put(this.nullValue, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Composite
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final EndpointArgumentsBuilder[] builders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Composite(EndpointArgumentsBuilder... builders) {
/* 237 */       this.builders = builders;
/*     */     }
/*     */     
/*     */     public Composite(Collection<? extends EndpointArgumentsBuilder> builders) {
/* 241 */       this(builders.<EndpointArgumentsBuilder>toArray(new EndpointArgumentsBuilder[builders.size()]));
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 245 */       for (EndpointArgumentsBuilder builder : this.builders) {
/* 246 */         builder.readRequest(msg, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentBuilder
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     protected final EndpointValueSetter setter;
/*     */     
/*     */     protected final ParameterImpl param;
/*     */     protected final String pname;
/*     */     protected final String pname1;
/*     */     
/*     */     AttachmentBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 262 */       this.setter = setter;
/* 263 */       this.param = param;
/* 264 */       this.pname = param.getPartName();
/* 265 */       this.pname1 = "<" + this.pname;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static EndpointArgumentsBuilder createAttachmentBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 278 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 279 */       if (DataHandler.class.isAssignableFrom(type))
/* 280 */         return new EndpointArgumentsBuilder.DataHandlerBuilder(param, setter); 
/* 281 */       if (byte[].class == type)
/* 282 */         return new EndpointArgumentsBuilder.ByteArrayBuilder(param, setter); 
/* 283 */       if (Source.class.isAssignableFrom(type))
/* 284 */         return new EndpointArgumentsBuilder.SourceBuilder(param, setter); 
/* 285 */       if (Image.class.isAssignableFrom(type))
/* 286 */         return new EndpointArgumentsBuilder.ImageBuilder(param, setter); 
/* 287 */       if (InputStream.class == type)
/* 288 */         return new EndpointArgumentsBuilder.InputStreamBuilder(param, setter); 
/* 289 */       if (EndpointArgumentsBuilder.isXMLMimeType(param.getBinding().getMimeType()))
/* 290 */         return new EndpointArgumentsBuilder.JAXBBuilder(param, setter); 
/* 291 */       if (String.class.isAssignableFrom(type)) {
/* 292 */         return new EndpointArgumentsBuilder.StringBuilder(param, setter);
/*     */       }
/* 294 */       throw new UnsupportedOperationException("Unknown Type=" + type + " Attachment is not mapped.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 299 */       boolean foundAttachment = false;
/*     */       
/* 301 */       for (Attachment att : msg.getAttachments()) {
/* 302 */         String part = getWSDLPartName(att);
/* 303 */         if (part == null) {
/*     */           continue;
/*     */         }
/* 306 */         if (part.equals(this.pname) || part.equals(this.pname1)) {
/* 307 */           foundAttachment = true;
/* 308 */           mapAttachment(att, args);
/*     */           break;
/*     */         } 
/*     */       } 
/* 312 */       if (!foundAttachment)
/* 313 */         throw new WebServiceException("Missing Attachment for " + this.pname); 
/*     */     }
/*     */     
/*     */     abstract void mapAttachment(Attachment param1Attachment, Object[] param1ArrayOfObject) throws JAXBException;
/*     */   }
/*     */   
/*     */   private static final class DataHandlerBuilder
/*     */     extends AttachmentBuilder {
/*     */     DataHandlerBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 322 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 326 */       this.setter.put(att.asDataHandler(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ByteArrayBuilder extends AttachmentBuilder {
/*     */     ByteArrayBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 332 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 336 */       this.setter.put(att.asByteArray(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SourceBuilder extends AttachmentBuilder {
/*     */     SourceBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 342 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 346 */       this.setter.put(att.asSource(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ImageBuilder extends AttachmentBuilder {
/*     */     ImageBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 352 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/*     */       Image image;
/* 357 */       InputStream is = null;
/*     */       try {
/* 359 */         is = att.asInputStream();
/* 360 */         image = ImageIO.read(is);
/* 361 */       } catch (IOException ioe) {
/* 362 */         throw new WebServiceException(ioe);
/*     */       } finally {
/* 364 */         if (is != null) {
/*     */           try {
/* 366 */             is.close();
/* 367 */           } catch (IOException ioe) {
/* 368 */             throw new WebServiceException(ioe);
/*     */           } 
/*     */         }
/*     */       } 
/* 372 */       this.setter.put(image, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InputStreamBuilder extends AttachmentBuilder {
/*     */     InputStreamBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 378 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 382 */       this.setter.put(att.asInputStream(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JAXBBuilder extends AttachmentBuilder {
/*     */     JAXBBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 388 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) throws JAXBException {
/* 392 */       Object obj = this.param.getXMLBridge().unmarshal(att.asInputStream());
/* 393 */       this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringBuilder extends AttachmentBuilder {
/*     */     StringBuilder(ParameterImpl param, EndpointValueSetter setter) {
/* 399 */       super(param, setter);
/*     */     }
/*     */     
/*     */     void mapAttachment(Attachment att, Object[] args) {
/* 403 */       att.getContentType();
/* 404 */       StringDataContentHandler sdh = new StringDataContentHandler();
/*     */       try {
/* 406 */         String str = (String)sdh.getContent((DataSource)new DataHandlerDataSource(att.asDataHandler()));
/* 407 */         this.setter.put(str, args);
/* 408 */       } catch (Exception e) {
/* 409 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String getWSDLPartName(Attachment att) {
/* 440 */     String cId = att.getContentId();
/*     */     
/* 442 */     int index = cId.lastIndexOf('@', cId.length());
/* 443 */     if (index == -1) {
/* 444 */       return null;
/*     */     }
/* 446 */     String localPart = cId.substring(0, index);
/* 447 */     index = localPart.lastIndexOf('=', localPart.length());
/* 448 */     if (index == -1) {
/* 449 */       return null;
/*     */     }
/*     */     try {
/* 452 */       return URLDecoder.decode(localPart.substring(0, index), "UTF-8");
/* 453 */     } catch (UnsupportedEncodingException e) {
/* 454 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */     
/*     */     private final QName headerName;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     public Header(SOAPVersion soapVersion, QName name, XMLBridge<?> bridge, EndpointValueSetter setter) {
/* 479 */       this.soapVersion = soapVersion;
/* 480 */       this.headerName = name;
/* 481 */       this.bridge = bridge;
/* 482 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Header(SOAPVersion soapVersion, ParameterImpl param, EndpointValueSetter setter) {
/* 486 */       this(soapVersion, 
/*     */           
/* 488 */           (param.getTypeInfo()).tagName, param
/* 489 */           .getXMLBridge(), setter);
/*     */       
/* 491 */       assert param.getOutBinding() == ParameterBinding.HEADER;
/*     */     }
/*     */     
/*     */     private SOAPFaultException createDuplicateHeaderException() {
/*     */       try {
/* 496 */         SOAPFault fault = this.soapVersion.getSOAPFactory().createFault();
/* 497 */         fault.setFaultCode(this.soapVersion.faultCodeClient);
/* 498 */         fault.setFaultString(ServerMessages.DUPLICATE_PORT_KNOWN_HEADER(this.headerName));
/* 499 */         return new SOAPFaultException(fault);
/* 500 */       } catch (SOAPException e) {
/* 501 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException {
/* 506 */       com.sun.xml.internal.ws.api.message.Header header = null;
/*     */       
/* 508 */       Iterator<com.sun.xml.internal.ws.api.message.Header> it = msg.getHeaders().getHeaders(this.headerName, true);
/* 509 */       if (it.hasNext()) {
/* 510 */         header = it.next();
/* 511 */         if (it.hasNext()) {
/* 512 */           throw createDuplicateHeaderException();
/*     */         }
/*     */       } 
/*     */       
/* 516 */       if (header != null) {
/* 517 */         this.setter.put(header.readAsJAXB(this.bridge), args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Body
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final EndpointValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Body(XMLBridge<?> bridge, EndpointValueSetter setter) {
/* 538 */       this.bridge = bridge;
/* 539 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException {
/* 543 */       this.setter.put(msg.readPayloadAsJAXB(this.bridge), args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     private final PartBuilder[] parts;
/*     */ 
/*     */     
/*     */     private final XMLBridge wrapper;
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, WebParam.Mode skipMode) {
/* 561 */       this.wrapperName = wp.getName();
/* 562 */       this.wrapper = wp.getXMLBridge();
/* 563 */       Class wrapperType = (Class)(this.wrapper.getTypeInfo()).type;
/* 564 */       this.dynamicWrapper = WrapperComposite.class.equals(wrapperType);
/* 565 */       List<PartBuilder> parts = new ArrayList<>();
/* 566 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 567 */       for (ParameterImpl p : children) {
/* 568 */         if (p.getMode() == skipMode) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 575 */         QName name = p.getName();
/*     */         try {
/* 577 */           if (this.dynamicWrapper) {
/* 578 */             if (this.wrappedParts == null) this.wrappedParts = new HashMap<>(); 
/* 579 */             XMLBridge xmlBridge = p.getInlinedRepeatedElementBridge();
/* 580 */             if (xmlBridge == null) xmlBridge = p.getXMLBridge(); 
/* 581 */             this.wrappedParts.put(p.getName(), new EndpointArgumentsBuilder.WrappedPartBuilder(xmlBridge, EndpointValueSetter.get(p))); continue;
/*     */           } 
/* 583 */           parts.add(new PartBuilder(wp
/* 584 */                 .getOwner().getBindingContext().getElementPropertyAccessor(wrapperType, name
/*     */                   
/* 586 */                   .getNamespaceURI(), p
/* 587 */                   .getName().getLocalPart()), 
/* 588 */                 EndpointValueSetter.get(p)));
/*     */ 
/*     */ 
/*     */           
/* 592 */           assert p.getBinding() == ParameterBinding.BODY;
/*     */         }
/* 594 */         catch (JAXBException e) {
/* 595 */           throw new WebServiceException(wrapperType + " do not have a property of the name " + name, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 600 */       this.parts = parts.<PartBuilder>toArray(new PartBuilder[parts.size()]);
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 604 */       if (this.dynamicWrapper) {
/* 605 */         readWrappedRequest(msg, args);
/*     */       }
/* 607 */       else if (this.parts.length > 0) {
/* 608 */         if (!msg.hasPayload()) {
/* 609 */           throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */         }
/* 611 */         XMLStreamReader reader = msg.readPayload();
/* 612 */         XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 613 */         Object wrapperBean = this.wrapper.unmarshal(reader, (msg.getAttachments() != null) ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(msg
/* 614 */               .getAttachments()) : null);
/*     */         
/*     */         try {
/* 617 */           for (PartBuilder part : this.parts) {
/* 618 */             part.readRequest(args, wrapperBean);
/*     */           }
/* 620 */         } catch (DatabindingException e) {
/*     */           
/* 622 */           throw new WebServiceException(e);
/*     */         } 
/*     */ 
/*     */         
/* 626 */         reader.close();
/* 627 */         XMLStreamReaderFactory.recycle(reader);
/*     */       } else {
/* 629 */         msg.consume();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static final class PartBuilder
/*     */     {
/*     */       private final PropertyAccessor accessor;
/*     */ 
/*     */ 
/*     */       
/*     */       private final EndpointValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public PartBuilder(PropertyAccessor accessor, EndpointValueSetter setter) {
/* 649 */         this.accessor = accessor;
/* 650 */         this.setter = setter;
/* 651 */         assert accessor != null && setter != null;
/*     */       }
/*     */       
/*     */       final void readRequest(Object[] args, Object wrapperBean) {
/* 655 */         Object obj = this.accessor.get(wrapperBean);
/* 656 */         this.setter.put(obj, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RpcLit
/*     */     extends EndpointArgumentsBuilder
/*     */   {
/*     */     public RpcLit(WrapperParameter wp) {
/* 669 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 671 */       this.wrapperName = wp.getName();
/* 672 */       this.wrappedParts = new HashMap<>();
/* 673 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 674 */       for (ParameterImpl p : children) {
/* 675 */         this.wrappedParts.put(p.getName(), new EndpointArgumentsBuilder.WrappedPartBuilder(p
/* 676 */               .getXMLBridge(), EndpointValueSetter.get(p)));
/*     */ 
/*     */ 
/*     */         
/* 680 */         assert p.getBinding() == ParameterBinding.BODY;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void readRequest(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 685 */       readWrappedRequest(msg, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 690 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract void readRequest(Message paramMessage, Object[] paramArrayOfObject) throws JAXBException, XMLStreamException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/EndpointArgumentsBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */