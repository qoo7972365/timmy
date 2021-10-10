/*     */ package com.sun.xml.internal.ws.client.sei;
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
/*     */ abstract class BodyBuilder
/*     */ {
/*  54 */   static final BodyBuilder EMPTY_SOAP11 = new Empty(SOAPVersion.SOAP_11);
/*  55 */   static final BodyBuilder EMPTY_SOAP12 = new Empty(SOAPVersion.SOAP_12);
/*     */   
/*     */   abstract Message createMessage(Object[] paramArrayOfObject);
/*     */   
/*     */   private static final class Empty extends BodyBuilder {
/*     */     public Empty(SOAPVersion soapVersion) {
/*  61 */       this.soapVersion = soapVersion;
/*     */     }
/*     */     private final SOAPVersion soapVersion;
/*     */     Message createMessage(Object[] methodArgs) {
/*  65 */       return Messages.createEmpty(this.soapVersion);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class JAXB
/*     */     extends BodyBuilder
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
/*     */     final Message createMessage(Object[] methodArgs) {
/*  88 */       return JAXBMessage.create(this.bridge, build(methodArgs), this.soapVersion);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract Object build(Object[] param1ArrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Bare
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
/*     */     Bare(ParameterImpl p, SOAPVersion soapVersion, ValueGetter getter) {
/* 113 */       super(p.getXMLBridge(), soapVersion);
/* 114 */       this.methodPos = p.getIndex();
/* 115 */       this.getter = getter;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object build(Object[] methodArgs) {
/* 122 */       return this.getter.get(methodArgs[this.methodPos]);
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
/*     */     
/*     */     protected Wrapped(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 155 */       super(wp.getXMLBridge(), soapVersion);
/* 156 */       this.children = wp.getWrapperChildren();
/* 157 */       this.indices = new int[this.children.size()];
/* 158 */       this.getters = new ValueGetter[this.children.size()];
/* 159 */       for (int i = 0; i < this.indices.length; i++) {
/* 160 */         ParameterImpl p = this.children.get(i);
/* 161 */         this.indices[i] = p.getIndex();
/* 162 */         this.getters[i] = getter.get(p);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected WrapperComposite buildWrapperComposite(Object[] methodArgs) {
/* 170 */       WrapperComposite cs = new WrapperComposite();
/* 171 */       cs.bridges = this.parameterBridges;
/* 172 */       cs.values = new Object[this.parameterBridges.length];
/*     */ 
/*     */       
/* 175 */       for (int i = this.indices.length - 1; i >= 0; i--) {
/* 176 */         Object arg = this.getters[i].get(methodArgs[this.indices[i]]);
/* 177 */         if (arg == null) {
/* 178 */           throw new WebServiceException("Method Parameter: " + ((ParameterImpl)this.children
/* 179 */               .get(i)).getName() + " cannot be null. This is BP 1.1 R2211 violation.");
/*     */         }
/* 181 */         cs.values[i] = arg;
/*     */       } 
/*     */       
/* 184 */       return cs;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class DocLit
/*     */     extends Wrapped
/*     */   {
/*     */     private final PropertyAccessor[] accessors;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Class wrapper;
/*     */ 
/*     */ 
/*     */     
/*     */     private BindingContext bindingContext;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean dynamicWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DocLit(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 213 */       super(wp, soapVersion, getter);
/* 214 */       this.bindingContext = wp.getOwner().getBindingContext();
/* 215 */       this.wrapper = (Class)(wp.getXMLBridge().getTypeInfo()).type;
/* 216 */       this.dynamicWrapper = WrapperComposite.class.equals(this.wrapper);
/* 217 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 218 */       this.accessors = new PropertyAccessor[this.children.size()];
/* 219 */       for (int i = 0; i < this.accessors.length; i++) {
/* 220 */         ParameterImpl p = this.children.get(i);
/* 221 */         QName name = p.getName();
/* 222 */         if (this.dynamicWrapper) {
/* 223 */           this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getInlinedRepeatedElementBridge();
/* 224 */           if (this.parameterBridges[i] == null) this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */         } else {
/*     */           try {
/* 227 */             this.accessors[i] = p.getOwner().getBindingContext().getElementPropertyAccessor(this.wrapper, name
/* 228 */                 .getNamespaceURI(), name.getLocalPart());
/* 229 */           } catch (JAXBException e) {
/* 230 */             throw new WebServiceException(this.wrapper + " do not have a property of the name " + name, e);
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
/*     */     Object build(Object[] methodArgs) {
/* 242 */       if (this.dynamicWrapper) return buildWrapperComposite(methodArgs);
/*     */       
/*     */       try {
/* 245 */         Object bean = this.bindingContext.newWrapperInstace(this.wrapper);
/*     */ 
/*     */         
/* 248 */         for (int i = this.indices.length - 1; i >= 0; i--) {
/* 249 */           this.accessors[i].set(bean, this.getters[i].get(methodArgs[this.indices[i]]));
/*     */         }
/*     */         
/* 252 */         return bean;
/* 253 */       } catch (InstantiationException e) {
/*     */         
/* 255 */         Error x = new InstantiationError(e.getMessage());
/* 256 */         x.initCause(e);
/* 257 */         throw x;
/* 258 */       } catch (IllegalAccessException e) {
/*     */         
/* 260 */         Error x = new IllegalAccessError(e.getMessage());
/* 261 */         x.initCause(e);
/* 262 */         throw x;
/* 263 */       } catch (DatabindingException e) {
/*     */         
/* 265 */         throw new WebServiceException(e);
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
/*     */   static final class RpcLit
/*     */     extends Wrapped
/*     */   {
/*     */     RpcLit(WrapperParameter wp, SOAPVersion soapVersion, ValueGetterFactory getter) {
/* 285 */       super(wp, soapVersion, getter);
/*     */       
/* 287 */       assert (wp.getTypeInfo()).type == WrapperComposite.class;
/*     */       
/* 289 */       this.parameterBridges = new XMLBridge[this.children.size()];
/* 290 */       for (int i = 0; i < this.parameterBridges.length; i++)
/* 291 */         this.parameterBridges[i] = ((ParameterImpl)this.children.get(i)).getXMLBridge(); 
/*     */     }
/*     */     
/*     */     Object build(Object[] methodArgs) {
/* 295 */       return buildWrapperComposite(methodArgs);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/client/sei/BodyBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */