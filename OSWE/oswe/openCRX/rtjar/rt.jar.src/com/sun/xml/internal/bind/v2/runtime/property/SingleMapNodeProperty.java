/*     */ package com.sun.xml.internal.bind.v2.runtime.property;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeMapPropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.ChildLoader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Receiver;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.TagName;
/*     */ import com.sun.xml.internal.bind.v2.runtime.unmarshaller.UnmarshallingContext;
/*     */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
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
/*     */ final class SingleMapNodeProperty<BeanT, ValueT extends Map>
/*     */   extends PropertyImpl<BeanT>
/*     */ {
/*     */   private final Accessor<BeanT, ValueT> acc;
/*     */   private final Name tagName;
/*     */   private final Name entryTag;
/*     */   private final Name keyTag;
/*     */   private final Name valueTag;
/*     */   private final boolean nillable;
/*     */   private JaxBeanInfo keyBeanInfo;
/*     */   private JaxBeanInfo valueBeanInfo;
/*     */   private final Class<? extends ValueT> mapImplClass;
/*     */   
/*     */   public SingleMapNodeProperty(JAXBContextImpl context, RuntimeMapPropertyInfo prop) {
/*  89 */     super(context, (RuntimePropertyInfo)prop);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     this.itemsLoader = new Loader(false)
/*     */       {
/* 143 */         private ThreadLocal<BeanT> target = new ThreadLocal<>();
/* 144 */         private ThreadLocal<ValueT> map = new ThreadLocal<>();
/* 145 */         private int depthCounter = 0;
/*     */ 
/*     */ 
/*     */         
/*     */         public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/*     */           try {
/* 151 */             this.target.set((BeanT)state.getPrev().getTarget());
/* 152 */             this.map.set((ValueT)SingleMapNodeProperty.this.acc.get(this.target.get()));
/* 153 */             this.depthCounter++;
/* 154 */             if (this.map.get() == null) {
/* 155 */               this.map.set((ValueT)ClassFactory.create(SingleMapNodeProperty.this.mapImplClass));
/*     */             }
/* 157 */             ((Map)this.map.get()).clear();
/* 158 */             state.setTarget(this.map.get());
/* 159 */           } catch (AccessorException e) {
/*     */             
/* 161 */             handleGenericException((Exception)e, true);
/* 162 */             state.setTarget(new HashMap<>());
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 168 */           super.leaveElement(state, ea);
/*     */           try {
/* 170 */             SingleMapNodeProperty.this.acc.set(this.target.get(), this.map.get());
/* 171 */             if (--this.depthCounter == 0) {
/* 172 */               this.target.remove();
/* 173 */               this.map.remove();
/*     */             } 
/* 175 */           } catch (AccessorException ex) {
/* 176 */             handleGenericException((Exception)ex, true);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 182 */           if (ea.matches(SingleMapNodeProperty.this.entryTag)) {
/* 183 */             state.setLoader(SingleMapNodeProperty.this.entryLoader);
/*     */           } else {
/* 185 */             super.childElement(state, ea);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public Collection<QName> getExpectedChildElements() {
/* 191 */           return Collections.singleton(SingleMapNodeProperty.this.entryTag.toQName());
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     this.entryLoader = new Loader(false)
/*     */       {
/*     */         public void startElement(UnmarshallingContext.State state, TagName ea) {
/* 203 */           state.setTarget(new Object[2]);
/*     */         }
/*     */ 
/*     */         
/*     */         public void leaveElement(UnmarshallingContext.State state, TagName ea) {
/* 208 */           Object[] keyValue = (Object[])state.getTarget();
/* 209 */           Map<Object, Object> map = (Map)state.getPrev().getTarget();
/* 210 */           map.put(keyValue[0], keyValue[1]);
/*     */         }
/*     */ 
/*     */         
/*     */         public void childElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 215 */           if (ea.matches(SingleMapNodeProperty.this.keyTag)) {
/* 216 */             state.setLoader(SingleMapNodeProperty.this.keyLoader);
/* 217 */             state.setReceiver(SingleMapNodeProperty.keyReceiver);
/*     */             return;
/*     */           } 
/* 220 */           if (ea.matches(SingleMapNodeProperty.this.valueTag)) {
/* 221 */             state.setLoader(SingleMapNodeProperty.this.valueLoader);
/* 222 */             state.setReceiver(SingleMapNodeProperty.valueReceiver);
/*     */             return;
/*     */           } 
/* 225 */           super.childElement(state, ea);
/*     */         }
/*     */         
/*     */         public Collection<QName> getExpectedChildElements()
/*     */         {
/* 230 */           return Arrays.asList(new QName[] { SingleMapNodeProperty.access$400(this.this$0).toQName(), SingleMapNodeProperty.access$700(this.this$0).toQName() }); }
/*     */       }; this.acc = prop.getAccessor().optimize(context); this.tagName = context.nameBuilder.createElementName(prop.getXmlName()); this.entryTag = context.nameBuilder.createElementName("", "entry"); this.keyTag = context.nameBuilder.createElementName("", "key"); this.valueTag = context.nameBuilder.createElementName("", "value"); this.nillable = prop.isCollectionNillable(); this.keyBeanInfo = context.getOrCreate((RuntimeTypeInfo)prop.getKeyType()); this.valueBeanInfo = context.getOrCreate((RuntimeTypeInfo)prop.getValueType()); Class<ValueT> sig = (Class<ValueT>)Utils.REFLECTION_NAVIGATOR.erasure(prop.getRawType());
/*     */     this.mapImplClass = ClassFactory.inferImplClass(sig, knownImplClasses);
/*     */   } private static final Class[] knownImplClasses = new Class[] { HashMap.class, TreeMap.class, LinkedHashMap.class }; private Loader keyLoader; private Loader valueLoader; private final Loader itemsLoader; private final Loader entryLoader; public void reset(BeanT bean) throws AccessorException { this.acc.set(bean, null); } public String getIdValue(BeanT bean) { return null; } public PropertyKind getKind() { return PropertyKind.MAP; } public void buildChildElementUnmarshallers(UnmarshallerChain chain, QNameMap<ChildLoader> handlers) { this.keyLoader = this.keyBeanInfo.getLoader(chain.context, true);
/*     */     this.valueLoader = this.valueBeanInfo.getLoader(chain.context, true);
/*     */     handlers.put(this.tagName, new ChildLoader(this.itemsLoader, null)); } private static final class ReceiverImpl implements Receiver
/*     */   {
/* 237 */     public ReceiverImpl(int index) { this.index = index; }
/*     */      private final int index;
/*     */     public void receive(UnmarshallingContext.State state, Object o) {
/* 240 */       ((Object[])state.getTarget())[this.index] = o;
/*     */     }
/*     */   }
/*     */   
/* 244 */   private static final Receiver keyReceiver = new ReceiverImpl(0);
/* 245 */   private static final Receiver valueReceiver = new ReceiverImpl(1);
/*     */ 
/*     */   
/*     */   public void serializeBody(BeanT o, XMLSerializer w, Object outerPeer) throws SAXException, AccessorException, IOException, XMLStreamException {
/* 249 */     Map map = (Map)this.acc.get(o);
/* 250 */     if (map != null) {
/* 251 */       bareStartTag(w, this.tagName, map);
/* 252 */       for (Map.Entry e : map.entrySet()) {
/* 253 */         bareStartTag(w, this.entryTag, null);
/*     */         
/* 255 */         Object key = e.getKey();
/* 256 */         if (key != null) {
/* 257 */           w.startElement(this.keyTag, key);
/* 258 */           w.childAsXsiType(key, this.fieldName, this.keyBeanInfo, false);
/* 259 */           w.endElement();
/*     */         } 
/*     */         
/* 262 */         Object value = e.getValue();
/* 263 */         if (value != null) {
/* 264 */           w.startElement(this.valueTag, value);
/* 265 */           w.childAsXsiType(value, this.fieldName, this.valueBeanInfo, false);
/* 266 */           w.endElement();
/*     */         } 
/*     */         
/* 269 */         w.endElement();
/*     */       } 
/* 271 */       w.endElement();
/*     */     }
/* 273 */     else if (this.nillable) {
/* 274 */       w.startElement(this.tagName, null);
/* 275 */       w.writeXsiNilTrue();
/* 276 */       w.endElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void bareStartTag(XMLSerializer w, Name tagName, Object peer) throws IOException, XMLStreamException, SAXException {
/* 281 */     w.startElement(tagName, peer);
/* 282 */     w.endNamespaceDecls(peer);
/* 283 */     w.endAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Accessor getElementPropertyAccessor(String nsUri, String localName) {
/* 288 */     if (this.tagName.equals(nsUri, localName))
/* 289 */       return this.acc; 
/* 290 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/property/SingleMapNodeProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */