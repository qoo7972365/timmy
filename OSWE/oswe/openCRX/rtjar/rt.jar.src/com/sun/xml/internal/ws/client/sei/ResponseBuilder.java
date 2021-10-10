/*     */ package com.sun.xml.internal.ws.client.sei;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ResponseBuilder
/*     */ {
/*     */   static final class WrappedPartBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueSetter setter;
/*     */     
/*     */     public WrappedPartBuilder(XMLBridge bridge, ValueSetter setter) {
/* 103 */       this.bridge = bridge;
/* 104 */       this.setter = setter;
/*     */     }
/*     */     final Object readResponse(Object[] args, XMLStreamReader r, AttachmentSet att) throws JAXBException {
/*     */       Object obj;
/* 108 */       AttachmentUnmarshallerImpl au = (att != null) ? new AttachmentUnmarshallerImpl(att) : null;
/* 109 */       if (this.bridge instanceof RepeatedElementBridge) {
/* 110 */         RepeatedElementBridge rbridge = (RepeatedElementBridge)this.bridge;
/* 111 */         ArrayList<Object> list = new ArrayList();
/* 112 */         QName name = r.getName();
/* 113 */         while (r.getEventType() == 1 && name.equals(r.getName())) {
/* 114 */           list.add(rbridge.unmarshal(r, (AttachmentUnmarshaller)au));
/* 115 */           XMLStreamReaderUtil.toNextTag(r, name);
/*     */         } 
/* 117 */         obj = rbridge.collectionHandler().convert(list);
/*     */       } else {
/* 119 */         obj = this.bridge.unmarshal(r, (AttachmentUnmarshaller)au);
/*     */       } 
/* 121 */       return this.setter.put(obj, args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 127 */   protected Map<QName, WrappedPartBuilder> wrappedParts = null;
/*     */   protected QName wrapperName;
/*     */   
/*     */   protected Object readWrappedResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 131 */     Object retVal = null;
/*     */     
/* 133 */     if (!msg.hasPayload()) {
/* 134 */       throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */     }
/* 136 */     XMLStreamReader reader = msg.readPayload();
/* 137 */     XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 138 */     reader.nextTag();
/*     */     
/* 140 */     while (reader.getEventType() == 1) {
/*     */       
/* 142 */       WrappedPartBuilder part = this.wrappedParts.get(reader.getName());
/* 143 */       if (part == null) {
/*     */         
/* 145 */         XMLStreamReaderUtil.skipElement(reader);
/* 146 */         reader.nextTag();
/*     */       } else {
/* 148 */         Object o = part.readResponse(args, reader, msg.getAttachments());
/*     */         
/* 150 */         if (o != null) {
/* 151 */           assert retVal == null;
/* 152 */           retVal = o;
/*     */         } 
/*     */       } 
/*     */       
/* 156 */       if (reader.getEventType() != 1 && reader
/* 157 */         .getEventType() != 2) {
/* 158 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 163 */     reader.close();
/* 164 */     XMLStreamReaderFactory.recycle(reader);
/*     */     
/* 166 */     return retVal;
/*     */   }
/*     */   
/*     */   static final class None
/*     */     extends ResponseBuilder {
/*     */     private None() {}
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) {
/* 174 */       msg.consume();
/* 175 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 183 */   public static final ResponseBuilder NONE = new None();
/*     */ 
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
/* 195 */     return primitiveUninitializedValues.get(type);
/*     */   }
/*     */   
/* 198 */   private static final Map<Class, Object> primitiveUninitializedValues = (Map)new HashMap<>();
/*     */   
/*     */   static {
/* 201 */     Map<Class<?>, Object> m = primitiveUninitializedValues;
/* 202 */     m.put(int.class, Integer.valueOf(0));
/* 203 */     m.put(char.class, Character.valueOf(false));
/* 204 */     m.put(byte.class, Byte.valueOf((byte)0));
/* 205 */     m.put(short.class, Short.valueOf((short)0));
/* 206 */     m.put(long.class, Long.valueOf(0L));
/* 207 */     m.put(float.class, Float.valueOf(0.0F));
/* 208 */     m.put(double.class, Double.valueOf(0.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class NullSetter
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final ValueSetter setter;
/*     */     private final Object nullValue;
/*     */     
/*     */     public NullSetter(ValueSetter setter, Object nullValue) {
/* 219 */       assert setter != null;
/* 220 */       this.nullValue = nullValue;
/* 221 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) {
/* 225 */       return this.setter.put(this.nullValue, args);
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
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final ResponseBuilder[] builders;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Composite(ResponseBuilder... builders) {
/* 248 */       this.builders = builders;
/*     */     }
/*     */     
/*     */     public Composite(Collection<? extends ResponseBuilder> builders) {
/* 252 */       this(builders.<ResponseBuilder>toArray(new ResponseBuilder[builders.size()]));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 257 */       Object retVal = null;
/* 258 */       for (ResponseBuilder builder : this.builders) {
/* 259 */         Object r = builder.readResponse(msg, args);
/*     */         
/* 261 */         if (r != null) {
/* 262 */           assert retVal == null;
/* 263 */           retVal = r;
/*     */         } 
/*     */       } 
/* 266 */       return retVal;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentBuilder
/*     */     extends ResponseBuilder
/*     */   {
/*     */     protected final ValueSetter setter;
/*     */     protected final ParameterImpl param;
/*     */     private final String pname;
/*     */     private final String pname1;
/*     */     
/*     */     AttachmentBuilder(ParameterImpl param, ValueSetter setter) {
/* 280 */       this.setter = setter;
/* 281 */       this.param = param;
/* 282 */       this.pname = param.getPartName();
/* 283 */       this.pname1 = "<" + this.pname;
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
/*     */     public static ResponseBuilder createAttachmentBuilder(ParameterImpl param, ValueSetter setter) {
/* 296 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 297 */       if (DataHandler.class.isAssignableFrom(type))
/* 298 */         return new ResponseBuilder.DataHandlerBuilder(param, setter); 
/* 299 */       if (byte[].class == type)
/* 300 */         return new ResponseBuilder.ByteArrayBuilder(param, setter); 
/* 301 */       if (Source.class.isAssignableFrom(type))
/* 302 */         return new ResponseBuilder.SourceBuilder(param, setter); 
/* 303 */       if (Image.class.isAssignableFrom(type))
/* 304 */         return new ResponseBuilder.ImageBuilder(param, setter); 
/* 305 */       if (InputStream.class == type)
/* 306 */         return new ResponseBuilder.InputStreamBuilder(param, setter); 
/* 307 */       if (ResponseBuilder.isXMLMimeType(param.getBinding().getMimeType()))
/* 308 */         return new ResponseBuilder.JAXBBuilder(param, setter); 
/* 309 */       if (String.class.isAssignableFrom(type)) {
/* 310 */         return new ResponseBuilder.StringBuilder(param, setter);
/*     */       }
/* 312 */       throw new UnsupportedOperationException("Unexpected Attachment type =" + type);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 319 */       for (Attachment att : msg.getAttachments()) {
/* 320 */         String part = getWSDLPartName(att);
/* 321 */         if (part == null) {
/*     */           continue;
/*     */         }
/* 324 */         if (part.equals(this.pname) || part.equals(this.pname1)) {
/* 325 */           return mapAttachment(att, args);
/*     */         }
/*     */       } 
/* 328 */       return null;
/*     */     }
/*     */     
/*     */     abstract Object mapAttachment(Attachment param1Attachment, Object[] param1ArrayOfObject) throws JAXBException;
/*     */   }
/*     */   
/*     */   private static final class DataHandlerBuilder extends AttachmentBuilder {
/*     */     DataHandlerBuilder(ParameterImpl param, ValueSetter setter) {
/* 336 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 341 */       return this.setter.put(att.asDataHandler(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class StringBuilder extends AttachmentBuilder {
/*     */     StringBuilder(ParameterImpl param, ValueSetter setter) {
/* 347 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 352 */       att.getContentType();
/* 353 */       StringDataContentHandler sdh = new StringDataContentHandler();
/*     */       try {
/* 355 */         String str = (String)sdh.getContent((DataSource)new DataHandlerDataSource(att.asDataHandler()));
/* 356 */         return this.setter.put(str, args);
/* 357 */       } catch (Exception e) {
/* 358 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ByteArrayBuilder
/*     */     extends AttachmentBuilder {
/*     */     ByteArrayBuilder(ParameterImpl param, ValueSetter setter) {
/* 366 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 371 */       return this.setter.put(att.asByteArray(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SourceBuilder extends AttachmentBuilder {
/*     */     SourceBuilder(ParameterImpl param, ValueSetter setter) {
/* 377 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 382 */       return this.setter.put(att.asSource(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ImageBuilder extends AttachmentBuilder {
/*     */     ImageBuilder(ParameterImpl param, ValueSetter setter) {
/* 388 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/*     */       Image image;
/* 394 */       InputStream is = null;
/*     */       try {
/* 396 */         is = att.asInputStream();
/* 397 */         image = ImageIO.read(is);
/* 398 */       } catch (IOException ioe) {
/* 399 */         throw new WebServiceException(ioe);
/*     */       } finally {
/* 401 */         if (is != null) {
/*     */           try {
/* 403 */             is.close();
/* 404 */           } catch (IOException ioe) {
/* 405 */             throw new WebServiceException(ioe);
/*     */           } 
/*     */         }
/*     */       } 
/* 409 */       return this.setter.put(image, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class InputStreamBuilder extends AttachmentBuilder {
/*     */     InputStreamBuilder(ParameterImpl param, ValueSetter setter) {
/* 415 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) {
/* 420 */       return this.setter.put(att.asInputStream(), args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JAXBBuilder extends AttachmentBuilder {
/*     */     JAXBBuilder(ParameterImpl param, ValueSetter setter) {
/* 426 */       super(param, setter);
/*     */     }
/*     */ 
/*     */     
/*     */     Object mapAttachment(Attachment att, Object[] args) throws JAXBException {
/* 431 */       Object obj = this.param.getXMLBridge().unmarshal(att.asInputStream());
/* 432 */       return this.setter.put(obj, args);
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
/*     */   
/*     */   public static final String getWSDLPartName(Attachment att) {
/* 463 */     String cId = att.getContentId();
/*     */     
/* 465 */     int index = cId.lastIndexOf('@', cId.length());
/* 466 */     if (index == -1) {
/* 467 */       return null;
/*     */     }
/* 469 */     String localPart = cId.substring(0, index);
/* 470 */     index = localPart.lastIndexOf('=', localPart.length());
/* 471 */     if (index == -1) {
/* 472 */       return null;
/*     */     }
/*     */     try {
/* 475 */       return URLDecoder.decode(localPart.substring(0, index), "UTF-8");
/* 476 */     } catch (UnsupportedEncodingException e) {
/* 477 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */ 
/*     */     
/*     */     private final ValueSetter setter;
/*     */ 
/*     */     
/*     */     private final QName headerName;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     public Header(SOAPVersion soapVersion, QName name, XMLBridge<?> bridge, ValueSetter setter) {
/* 502 */       this.soapVersion = soapVersion;
/* 503 */       this.headerName = name;
/* 504 */       this.bridge = bridge;
/* 505 */       this.setter = setter;
/*     */     }
/*     */     
/*     */     public Header(SOAPVersion soapVersion, ParameterImpl param, ValueSetter setter) {
/* 509 */       this(soapVersion, 
/* 510 */           (param.getTypeInfo()).tagName, param
/* 511 */           .getXMLBridge(), setter);
/*     */       
/* 513 */       assert param.getOutBinding() == ParameterBinding.HEADER;
/*     */     }
/*     */     
/*     */     private SOAPFaultException createDuplicateHeaderException() {
/*     */       try {
/* 518 */         SOAPFault fault = this.soapVersion.getSOAPFactory().createFault();
/* 519 */         fault.setFaultCode(this.soapVersion.faultCodeServer);
/* 520 */         fault.setFaultString(ServerMessages.DUPLICATE_PORT_KNOWN_HEADER(this.headerName));
/* 521 */         return new SOAPFaultException(fault);
/* 522 */       } catch (SOAPException e) {
/* 523 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException {
/* 529 */       com.sun.xml.internal.ws.api.message.Header header = null;
/*     */       
/* 531 */       Iterator<com.sun.xml.internal.ws.api.message.Header> it = msg.getHeaders().getHeaders(this.headerName, true);
/* 532 */       if (it.hasNext()) {
/* 533 */         header = it.next();
/* 534 */         if (it.hasNext()) {
/* 535 */           throw createDuplicateHeaderException();
/*     */         }
/*     */       } 
/*     */       
/* 539 */       if (header != null) {
/* 540 */         return this.setter.put(header.readAsJAXB(this.bridge), args);
/*     */       }
/*     */       
/* 543 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Body
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final XMLBridge<?> bridge;
/*     */ 
/*     */     
/*     */     private final ValueSetter setter;
/*     */ 
/*     */ 
/*     */     
/*     */     public Body(XMLBridge<?> bridge, ValueSetter setter) {
/* 561 */       this.bridge = bridge;
/* 562 */       this.setter = setter;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException {
/* 567 */       return this.setter.put(msg.readPayloadAsJAXB(this.bridge), args);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends ResponseBuilder
/*     */   {
/*     */     private final PartBuilder[] parts;
/*     */ 
/*     */     
/*     */     private final XMLBridge wrapper;
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, ValueSetterFactory setterFactory) {
/* 586 */       this.wrapperName = wp.getName();
/* 587 */       this.wrapper = wp.getXMLBridge();
/* 588 */       Class wrapperType = (Class)(this.wrapper.getTypeInfo()).type;
/* 589 */       this.dynamicWrapper = WrapperComposite.class.equals(wrapperType);
/*     */       
/* 591 */       List<PartBuilder> tempParts = new ArrayList<>();
/*     */       
/* 593 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 594 */       for (ParameterImpl p : children) {
/* 595 */         if (p.isIN())
/*     */           continue; 
/* 597 */         QName name = p.getName();
/* 598 */         if (this.dynamicWrapper) {
/* 599 */           if (this.wrappedParts == null) this.wrappedParts = new HashMap<>(); 
/* 600 */           XMLBridge xmlBridge = p.getInlinedRepeatedElementBridge();
/* 601 */           if (xmlBridge == null) xmlBridge = p.getXMLBridge(); 
/* 602 */           this.wrappedParts.put(p.getName(), new ResponseBuilder.WrappedPartBuilder(xmlBridge, setterFactory.get(p))); continue;
/*     */         } 
/*     */         try {
/* 605 */           tempParts.add(new PartBuilder(wp
/* 606 */                 .getOwner().getBindingContext().getElementPropertyAccessor(wrapperType, name
/*     */                   
/* 608 */                   .getNamespaceURI(), p
/* 609 */                   .getName().getLocalPart()), setterFactory
/* 610 */                 .get(p)));
/*     */ 
/*     */ 
/*     */           
/* 614 */           assert p.getBinding() == ParameterBinding.BODY;
/* 615 */         } catch (JAXBException e) {
/* 616 */           throw new WebServiceException(wrapperType + " do not have a property of the name " + name, e);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 621 */       this.parts = tempParts.<PartBuilder>toArray(new PartBuilder[tempParts.size()]);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 626 */       if (this.dynamicWrapper) return readWrappedResponse(msg, args); 
/* 627 */       Object retVal = null;
/*     */       
/* 629 */       if (this.parts.length > 0) {
/* 630 */         if (!msg.hasPayload()) {
/* 631 */           throw new WebServiceException("No payload. Expecting payload with " + this.wrapperName + " element");
/*     */         }
/* 633 */         XMLStreamReader reader = msg.readPayload();
/* 634 */         XMLStreamReaderUtil.verifyTag(reader, this.wrapperName);
/* 635 */         Object wrapperBean = this.wrapper.unmarshal(reader, (msg.getAttachments() != null) ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(msg
/* 636 */               .getAttachments()) : null);
/*     */         
/*     */         try {
/* 639 */           for (PartBuilder part : this.parts) {
/* 640 */             Object o = part.readResponse(args, wrapperBean);
/*     */ 
/*     */             
/* 643 */             if (o != null) {
/* 644 */               assert retVal == null;
/* 645 */               retVal = o;
/*     */             } 
/*     */           } 
/* 648 */         } catch (DatabindingException e) {
/*     */           
/* 650 */           throw new WebServiceException(e);
/*     */         } 
/*     */ 
/*     */         
/* 654 */         reader.close();
/* 655 */         XMLStreamReaderFactory.recycle(reader);
/*     */       } else {
/* 657 */         msg.consume();
/*     */       } 
/*     */       
/* 660 */       return retVal;
/*     */     }
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
/*     */       private final ValueSetter setter;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public PartBuilder(PropertyAccessor accessor, ValueSetter setter) {
/* 678 */         this.accessor = accessor;
/* 679 */         this.setter = setter;
/* 680 */         assert accessor != null && setter != null;
/*     */       }
/*     */       
/*     */       final Object readResponse(Object[] args, Object wrapperBean) {
/* 684 */         Object obj = this.accessor.get(wrapperBean);
/* 685 */         return this.setter.put(obj, args);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class RpcLit
/*     */     extends ResponseBuilder
/*     */   {
/*     */     public RpcLit(WrapperParameter wp, ValueSetterFactory setterFactory) {
/* 698 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/* 699 */       this.wrapperName = wp.getName();
/* 700 */       this.wrappedParts = new HashMap<>();
/* 701 */       List<ParameterImpl> children = wp.getWrapperChildren();
/* 702 */       for (ParameterImpl p : children) {
/* 703 */         this.wrappedParts.put(p.getName(), new ResponseBuilder.WrappedPartBuilder(p
/* 704 */               .getXMLBridge(), setterFactory.get(p)));
/*     */ 
/*     */ 
/*     */         
/* 708 */         assert p.getBinding() == ParameterBinding.BODY;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Object readResponse(Message msg, Object[] args) throws JAXBException, XMLStreamException {
/* 714 */       return readWrappedResponse(msg, args);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 719 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract Object readResponse(Message paramMessage, Object[] paramArrayOfObject) throws JAXBException, XMLStreamException;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/ResponseBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */