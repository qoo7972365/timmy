/*     */ package com.sun.xml.internal.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.message.Message;
/*     */ import com.sun.xml.internal.ws.api.message.Messages;
/*     */ import com.sun.xml.internal.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import com.sun.xml.internal.ws.model.WrapperParameter;
/*     */ import com.sun.xml.internal.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.internal.ws.spi.db.DatabindingException;
/*     */ import com.sun.xml.internal.ws.spi.db.PropertyAccessor;
/*     */ import com.sun.xml.internal.ws.spi.db.WrapperComposite;
/*     */ import com.sun.xml.internal.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointResponseMessageBuilder
/*     */ {
/*  54 */   public static final EndpointResponseMessageBuilder EMPTY_SOAP11 = new Empty(SOAPVersion.SOAP_11);
/*  55 */   public static final EndpointResponseMessageBuilder EMPTY_SOAP12 = new Empty(SOAPVersion.SOAP_12);
/*     */   
/*     */   public abstract Message createMessage(Object[] paramArrayOfObject, Object paramObject);
/*     */   
/*     */   private static final class Empty extends EndpointResponseMessageBuilder {
/*     */     public Empty(SOAPVersion soapVersion) {
/*  61 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     private final SOAPVersion soapVersion;
/*     */     public Message createMessage(Object[] methodArgs, Object returnValue) {
/*  65 */       return Messages.createEmpty(this.soapVersion);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class JAXB
/*     */     extends EndpointResponseMessageBuilder
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */ 
/*     */     
/*     */     private final SOAPVersion soapVersion;
/*     */ 
/*     */ 
/*     */     
/*     */     protected JAXB(XMLBridge bridge, SOAPVersion soapVersion) {
/*  82 */       assert bridge != null;
/*  83 */       this.bridge = bridge;
/*  84 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     
/*     */     public final Message createMessage(Object[] methodArgs, Object returnValue) {
/*  88 */       return JAXBMessage.create(this.bridge, build(methodArgs, returnValue), this.soapVersion);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract Object build(Object[] param1ArrayOfObject, Object param1Object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Bare
/*     */     extends JAXB
/*     */   {
/*     */     private final int methodPos;
/*     */ 
/*     */ 
/*     */     
/*     */     private final ValueGetter getter;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Bare(ParameterImpl p, SOAPVersion soapVersion) {
/* 113 */       super(p.getXMLBridge(), soapVersion);
/* 114 */       this.methodPos = p.getIndex();
/* 115 */       this.getter = ValueGetter.get(p);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 122 */       if (this.methodPos == -1) {
/* 123 */         return returnValue;
/*     */       }
/* 125 */       return this.getter.get(methodArgs[this.methodPos]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class Wrapped
/*     */     extends JAXB
/*     */   {
/*     */     protected final int[] indices;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final ValueGetter[] getters;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected XMLBridge[] parameterBridges;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<ParameterImpl> children;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Wrapped(WrapperParameter wp, SOAPVersion soapVersion) {
/* 157 */       super(wp.getXMLBridge(), soapVersion);
/*     */       
/* 159 */       this.children = wp.getWrapperChildren();
/*     */       
/* 161 */       this.indices = new int[this.children.size()];
/* 162 */       this.getters = new ValueGetter[this.children.size()];
/* 163 */       for (int i = 0; i < this.indices.length; i++) {
/* 164 */         ParameterImpl p = this.children.get(i);
/* 165 */         this.indices[i] = p.getIndex();
/* 166 */         this.getters[i] = ValueGetter.get(p);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     WrapperComposite buildWrapperComposite(Object[] methodArgs, Object returnValue) {
/* 174 */       WrapperComposite cs = new WrapperComposite();
/* 175 */       cs.bridges = this.parameterBridges;
/* 176 */       cs.values = new Object[this.parameterBridges.length];
/*     */ 
/*     */       
/* 179 */       for (int i = this.indices.length - 1; i >= 0; i--) {
/*     */         Object v;
/* 181 */         if (this.indices[i] == -1) {
/* 182 */           v = this.getters[i].get(returnValue);
/*     */         } else {
/* 184 */           v = this.getters[i].get(methodArgs[this.indices[i]]);
/*     */         } 
/* 186 */         if (v == null) {
/* 187 */           throw new WebServiceException("Method Parameter: " + ((ParameterImpl)this.children
/* 188 */               .get(i)).getName() + " cannot be null. This is BP 1.1 R2211 violation.");
/*     */         }
/* 190 */         cs.values[i] = v;
/*     */       } 
/*     */       
/* 193 */       return cs;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class DocLit
/*     */     extends Wrapped
/*     */   {
/*     */     private final PropertyAccessor[] accessors;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class wrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private BindingContext bindingContext;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DocLit(WrapperParameter wp, SOAPVersion soapVersion) {
/* 224 */       super(wp, soapVersion);
/* 225 */       this.bindingContext = wp.getOwner().getBindingContext();
/* 226 */       this.wrapper = (Class)(wp.getXMLBridge().getTypeInfo()).type;
/* 227 */       this.dynamicWrapper = WrapperComposite.class.equals(this.wrapper);
/* 228 */       this.children = wp.getWrapperChildren();
/* 229 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 230 */       this.accessors = new PropertyAccessor[this.children.size()];
/* 231 */       for (int i = 0; i < this.accessors.length; i++) {
/* 232 */         ParameterImpl p = this.children.get(i);
/* 233 */         QName name = p.getName();
/* 234 */         if (this.dynamicWrapper) {
/* 235 */           this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getInlinedRepeatedElementBridge();
/* 236 */           if (this.parameterBridges[i] == null) this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */         } else {
/*     */           try {
/* 239 */             this.accessors[i] = this.dynamicWrapper ? null : p
/* 240 */               .getOwner().getBindingContext().getElementPropertyAccessor(this.wrapper, name
/* 241 */                 .getNamespaceURI(), name.getLocalPart());
/* 242 */           } catch (JAXBException e) {
/* 243 */             throw new WebServiceException(this.wrapper + " do not have a property of the name " + name, e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 255 */       if (this.dynamicWrapper) return buildWrapperComposite(methodArgs, returnValue);
/*     */       
/*     */       try {
/* 258 */         Object bean = this.bindingContext.newWrapperInstace(this.wrapper);
/*     */ 
/*     */         
/* 261 */         for (int i = this.indices.length - 1; i >= 0; i--) {
/* 262 */           if (this.indices[i] == -1) {
/* 263 */             this.accessors[i].set(bean, returnValue);
/*     */           } else {
/* 265 */             this.accessors[i].set(bean, this.getters[i].get(methodArgs[this.indices[i]]));
/*     */           } 
/*     */         } 
/*     */         
/* 269 */         return bean;
/* 270 */       } catch (InstantiationException e) {
/*     */         
/* 272 */         Error x = new InstantiationError(e.getMessage());
/* 273 */         x.initCause(e);
/* 274 */         throw x;
/* 275 */       } catch (IllegalAccessException e) {
/*     */         
/* 277 */         Error x = new IllegalAccessError(e.getMessage());
/* 278 */         x.initCause(e);
/* 279 */         throw x;
/* 280 */       } catch (DatabindingException e) {
/*     */         
/* 282 */         throw new WebServiceException(e);
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
/*     */   public static final class RpcLit
/*     */     extends Wrapped
/*     */   {
/*     */     public RpcLit(WrapperParameter wp, SOAPVersion soapVersion) {
/* 302 */       super(wp, soapVersion);
/*     */       
/* 304 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 306 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 307 */       for (int i = 0; i < this.parameterBridges.length; i++) {
/* 308 */         this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs, Object returnValue) {
/* 315 */       return buildWrapperComposite(methodArgs, returnValue);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/EndpointResponseMessageBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */