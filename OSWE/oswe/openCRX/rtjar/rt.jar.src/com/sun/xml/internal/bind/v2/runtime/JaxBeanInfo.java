/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.bind.Util;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallerImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ 
/*     */ public abstract class JaxBeanInfo<BeanT>
/*     */ {
/*     */   protected boolean isNilIncluded = false;
/*     */   protected short flag;
/*     */   private static final short FLAG_IS_ELEMENT = 1;
/*     */   private static final short FLAG_IS_IMMUTABLE = 2;
/*     */   private static final short FLAG_HAS_ELEMENT_ONLY_CONTENTMODEL = 4;
/*     */   private static final short FLAG_HAS_BEFORE_UNMARSHAL_METHOD = 8;
/*     */   private static final short FLAG_HAS_AFTER_UNMARSHAL_METHOD = 16;
/*     */   private static final short FLAG_HAS_BEFORE_MARSHAL_METHOD = 32;
/*     */   private static final short FLAG_HAS_AFTER_MARSHAL_METHOD = 64;
/*     */   private static final short FLAG_HAS_LIFECYCLE_EVENTS = 128;
/*     */   private LifecycleMethods lcm;
/*     */   public final Class<BeanT> jaxbType;
/*     */   private final Object typeName;
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, QName[] typeNames, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/*  85 */     this(grammar, rti, jaxbType, typeNames, isElement, isImmutable, hasLifecycleEvents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, QName typeName, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/*  92 */     this(grammar, rti, jaxbType, typeName, isElement, isImmutable, hasLifecycleEvents);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/*  99 */     this(grammar, rti, jaxbType, (Object)null, isElement, isImmutable, hasLifecycleEvents);
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
/*     */   private JaxBeanInfo(JAXBContextImpl grammar, RuntimeTypeInfo rti, Class<BeanT> jaxbType, Object typeName, boolean isElement, boolean isImmutable, boolean hasLifecycleEvents) {
/* 127 */     this.lcm = null;
/*     */     grammar.beanInfos.put(rti, this);
/*     */     this.jaxbType = jaxbType;
/*     */     this.typeName = typeName;
/*     */     this.flag = (short)((isElement ? 1 : 0) | (isImmutable ? 2 : 0) | (hasLifecycleEvents ? 128 : 0));
/*     */   } public final boolean hasBeforeUnmarshalMethod() {
/* 133 */     return ((this.flag & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasAfterUnmarshalMethod() {
/* 140 */     return ((this.flag & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasBeforeMarshalMethod() {
/* 147 */     return ((this.flag & 0x20) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasAfterMarshalMethod() {
/* 154 */     return ((this.flag & 0x40) != 0);
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
/*     */   public final boolean isElement() {
/* 177 */     return ((this.flag & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isImmutable() {
/* 188 */     return ((this.flag & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasElementOnlyContentModel() {
/* 198 */     return ((this.flag & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void hasElementOnlyContentModel(boolean value) {
/* 208 */     if (value) {
/* 209 */       this.flag = (short)(this.flag | 0x4);
/*     */     } else {
/* 211 */       this.flag = (short)(this.flag & 0xFFFFFFFB);
/*     */     } 
/*     */   }
/*     */   public boolean isNilIncluded() {
/* 215 */     return this.isNilIncluded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lookForLifecycleMethods() {
/* 226 */     return ((this.flag & 0x80) != 0);
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
/*     */   public abstract String getElementNamespaceURI(BeanT paramBeanT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getElementLocalName(BeanT paramBeanT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<QName> getTypeNames() {
/* 275 */     if (this.typeName == null) return Collections.emptyList(); 
/* 276 */     if (this.typeName instanceof QName) return Collections.singletonList((QName)this.typeName); 
/* 277 */     return Arrays.asList((QName[])this.typeName);
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
/*     */   public QName getTypeName(@NotNull BeanT instance) {
/* 289 */     if (this.typeName == null) return null; 
/* 290 */     if (this.typeName instanceof QName) return (QName)this.typeName; 
/* 291 */     return ((QName[])this.typeName)[0];
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
/*     */   public abstract BeanT createInstance(UnmarshallingContext paramUnmarshallingContext) throws IllegalAccessException, InvocationTargetException, InstantiationException, SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean reset(BeanT paramBeanT, UnmarshallingContext paramUnmarshallingContext) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getId(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeBody(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeAttributes(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeRoot(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException, IOException, XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void serializeURIs(BeanT paramBeanT, XMLSerializer paramXMLSerializer) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Loader getLoader(JAXBContextImpl paramJAXBContextImpl, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Transducer<BeanT> getTransducer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void link(JAXBContextImpl grammar) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void wrapUp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 415 */   private static final Class[] unmarshalEventParams = new Class[] { Unmarshaller.class, Object.class };
/* 416 */   private static Class[] marshalEventParams = new Class[] { Marshaller.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setLifecycleFlags() {
/*     */     try {
/* 424 */       Class<BeanT> jt = this.jaxbType;
/*     */       
/* 426 */       if (this.lcm == null) {
/* 427 */         this.lcm = new LifecycleMethods();
/*     */       }
/*     */       
/* 430 */       while (jt != null) {
/* 431 */         for (Method m : jt.getDeclaredMethods()) {
/* 432 */           String name = m.getName();
/*     */           
/* 434 */           if (this.lcm.beforeUnmarshal == null && 
/* 435 */             name.equals("beforeUnmarshal") && 
/* 436 */             match(m, unmarshalEventParams)) {
/* 437 */             cacheLifecycleMethod(m, (short)8);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 442 */           if (this.lcm.afterUnmarshal == null && 
/* 443 */             name.equals("afterUnmarshal") && 
/* 444 */             match(m, unmarshalEventParams)) {
/* 445 */             cacheLifecycleMethod(m, (short)16);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 450 */           if (this.lcm.beforeMarshal == null && 
/* 451 */             name.equals("beforeMarshal") && 
/* 452 */             match(m, marshalEventParams)) {
/* 453 */             cacheLifecycleMethod(m, (short)32);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 458 */           if (this.lcm.afterMarshal == null && 
/* 459 */             name.equals("afterMarshal") && 
/* 460 */             match(m, marshalEventParams)) {
/* 461 */             cacheLifecycleMethod(m, (short)64);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 466 */         jt = (Class)jt.getSuperclass();
/*     */       } 
/* 468 */     } catch (SecurityException e) {
/*     */       
/* 470 */       logger.log(Level.WARNING, Messages.UNABLE_TO_DISCOVER_EVENTHANDLER.format(new Object[] { this.jaxbType
/* 471 */               .getName(), e }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean match(Method m, Class[] params) {
/* 476 */     return Arrays.equals((Object[])m.getParameterTypes(), (Object[])params);
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
/*     */   private void cacheLifecycleMethod(Method m, short lifecycleFlag) {
/* 489 */     if (this.lcm == null) {
/* 490 */       this.lcm = new LifecycleMethods();
/*     */     }
/*     */ 
/*     */     
/* 494 */     m.setAccessible(true);
/*     */     
/* 496 */     this.flag = (short)(this.flag | lifecycleFlag);
/*     */     
/* 498 */     switch (lifecycleFlag) {
/*     */       case 8:
/* 500 */         this.lcm.beforeUnmarshal = m;
/*     */         break;
/*     */       case 16:
/* 503 */         this.lcm.afterUnmarshal = m;
/*     */         break;
/*     */       case 32:
/* 506 */         this.lcm.beforeMarshal = m;
/*     */         break;
/*     */       case 64:
/* 509 */         this.lcm.afterMarshal = m;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final LifecycleMethods getLifecycleMethods() {
/* 520 */     return this.lcm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void invokeBeforeUnmarshalMethod(UnmarshallerImpl unm, Object child, Object parent) throws SAXException {
/* 527 */     Method m = (getLifecycleMethods()).beforeUnmarshal;
/* 528 */     invokeUnmarshallCallback(m, child, unm, parent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void invokeAfterUnmarshalMethod(UnmarshallerImpl unm, Object child, Object parent) throws SAXException {
/* 535 */     Method m = (getLifecycleMethods()).afterUnmarshal;
/* 536 */     invokeUnmarshallCallback(m, child, unm, parent);
/*     */   }
/*     */   
/*     */   private void invokeUnmarshallCallback(Method m, Object child, UnmarshallerImpl unm, Object parent) throws SAXException {
/*     */     try {
/* 541 */       m.invoke(child, new Object[] { unm, parent });
/* 542 */     } catch (IllegalAccessException e) {
/* 543 */       UnmarshallingContext.getInstance().handleError(e, false);
/* 544 */     } catch (InvocationTargetException e) {
/* 545 */       UnmarshallingContext.getInstance().handleError(e, false);
/*     */     } 
/*     */   }
/*     */   
/* 549 */   private static final Logger logger = Util.getClassLogger();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/JaxBeanInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */