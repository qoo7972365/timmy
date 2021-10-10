/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.model.core.PropertyKind;
/*     */ import com.sun.xml.internal.bind.v2.runtime.ClassBeanInfoImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.internal.bind.v2.runtime.JaxBeanInfo;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.AttributeProperty;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.Property;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.StructureLoaderBuilder;
/*     */ import com.sun.xml.internal.bind.v2.runtime.property.UnmarshallerChain;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.internal.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import com.sun.xml.internal.bind.v2.util.QNameMap;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public final class StructureLoader
/*     */   extends Loader
/*     */ {
/*  67 */   private final QNameMap<ChildLoader> childUnmarshallers = new QNameMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChildLoader catchAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChildLoader textHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QNameMap<TransducedAccessor> attUnmarshallers;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Accessor<Object, Map<QName, String>> attCatchAll;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JaxBeanInfo beanInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int frameSize;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureLoader(ClassBeanInfoImpl beanInfo) {
/* 103 */     super(true);
/* 104 */     this.beanInfo = (JaxBeanInfo)beanInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(JAXBContextImpl context, ClassBeanInfoImpl beanInfo, Accessor<?, Map<QName, String>> attWildcard) {
/* 115 */     UnmarshallerChain chain = new UnmarshallerChain(context);
/* 116 */     for (ClassBeanInfoImpl bi = beanInfo; bi != null; bi = bi.superClazz) {
/* 117 */       for (int i = bi.properties.length - 1; i >= 0; i--) {
/* 118 */         AttributeProperty ap; Property p = bi.properties[i];
/*     */         
/* 120 */         switch (p.getKind()) {
/*     */           case ATTRIBUTE:
/* 122 */             if (this.attUnmarshallers == null)
/* 123 */               this.attUnmarshallers = new QNameMap(); 
/* 124 */             ap = (AttributeProperty)p;
/* 125 */             this.attUnmarshallers.put(ap.attName.toQName(), ap.xacc);
/*     */             break;
/*     */           case ELEMENT:
/*     */           case REFERENCE:
/*     */           case MAP:
/*     */           case VALUE:
/* 131 */             p.buildChildElementUnmarshallers(chain, this.childUnmarshallers);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/*     */     } 
/* 137 */     this.frameSize = chain.getScopeSize();
/*     */     
/* 139 */     this.textHandler = (ChildLoader)this.childUnmarshallers.get(StructureLoaderBuilder.TEXT_HANDLER);
/* 140 */     this.catchAll = (ChildLoader)this.childUnmarshallers.get(StructureLoaderBuilder.CATCH_ALL);
/*     */     
/* 142 */     if (attWildcard != null) {
/* 143 */       this.attCatchAll = (Accessor)attWildcard;
/*     */ 
/*     */       
/* 146 */       if (this.attUnmarshallers == null)
/* 147 */         this.attUnmarshallers = EMPTY; 
/*     */     } else {
/* 149 */       this.attCatchAll = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 155 */     UnmarshallingContext context = state.getContext();
/*     */ 
/*     */ 
/*     */     
/* 159 */     assert !this.beanInfo.isImmutable();
/*     */ 
/*     */     
/* 162 */     Object child = context.getInnerPeer();
/*     */     
/* 164 */     if (child != null && this.beanInfo.jaxbType != child.getClass()) {
/* 165 */       child = null;
/*     */     }
/* 167 */     if (child != null) {
/* 168 */       this.beanInfo.reset(child, context);
/*     */     }
/* 170 */     if (child == null) {
/* 171 */       child = context.createInstance(this.beanInfo);
/*     */     }
/* 173 */     context.recordInnerPeer(child);
/*     */     
/* 175 */     state.setTarget(child);
/*     */     
/* 177 */     fireBeforeUnmarshal(this.beanInfo, child, state);
/*     */ 
/*     */     
/* 180 */     context.startScope(this.frameSize);
/*     */     
/* 182 */     if (this.attUnmarshallers != null) {
/* 183 */       Attributes atts = ea.atts;
/* 184 */       for (int i = 0; i < atts.getLength(); i++) {
/* 185 */         String auri = atts.getURI(i);
/*     */         
/* 187 */         String alocal = atts.getLocalName(i);
/* 188 */         if ("".equals(alocal)) {
/* 189 */           alocal = atts.getQName(i);
/*     */         }
/* 191 */         String avalue = atts.getValue(i);
/* 192 */         TransducedAccessor xacc = (TransducedAccessor)this.attUnmarshallers.get(auri, alocal);
/*     */         try {
/* 194 */           if (xacc != null) {
/* 195 */             xacc.parse(child, avalue);
/* 196 */           } else if (this.attCatchAll != null) {
/* 197 */             String qname = atts.getQName(i);
/* 198 */             if (!atts.getURI(i).equals("http://www.w3.org/2001/XMLSchema-instance"))
/*     */             { String prefix;
/* 200 */               Object o = state.getTarget();
/* 201 */               Map<QName, String> map = (Map<QName, String>)this.attCatchAll.get(o);
/* 202 */               if (map == null) {
/*     */ 
/*     */ 
/*     */                 
/* 206 */                 if (this.attCatchAll.valueType.isAssignableFrom(HashMap.class)) {
/* 207 */                   map = new HashMap<>();
/*     */                 }
/*     */                 else {
/*     */                   
/* 211 */                   context.handleError(Messages.UNABLE_TO_CREATE_MAP.format(new Object[] { this.attCatchAll.valueType }));
/*     */                   return;
/*     */                 } 
/* 214 */                 this.attCatchAll.set(o, map);
/*     */               } 
/*     */ 
/*     */               
/* 218 */               int idx = qname.indexOf(':');
/* 219 */               if (idx < 0) { prefix = ""; }
/* 220 */               else { prefix = qname.substring(0, idx); }
/*     */               
/* 222 */               map.put(new QName(auri, alocal, prefix), avalue); } 
/*     */           } 
/* 224 */         } catch (AccessorException e) {
/* 225 */           handleGenericException((Exception)e, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void childElement(UnmarshallingContext.State state, TagName arg) throws SAXException {
/* 233 */     ChildLoader child = (ChildLoader)this.childUnmarshallers.get(arg.uri, arg.local);
/* 234 */     if (child == null) {
/* 235 */       child = this.catchAll;
/* 236 */       if (child == null) {
/* 237 */         super.childElement(state, arg);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 242 */     state.setLoader(child.loader);
/* 243 */     state.setReceiver(child.receiver);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedChildElements() {
/* 248 */     return this.childUnmarshallers.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<QName> getExpectedAttributes() {
/* 253 */     return this.attUnmarshallers.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void text(UnmarshallingContext.State state, CharSequence text) throws SAXException {
/* 258 */     if (this.textHandler != null) {
/* 259 */       this.textHandler.loader.text(state, text);
/*     */     }
/*     */   }
/*     */   
/*     */   public void leaveElement(UnmarshallingContext.State state, TagName ea) throws SAXException {
/* 264 */     state.getContext().endScope(this.frameSize);
/* 265 */     fireAfterUnmarshal(this.beanInfo, state.getTarget(), state.getPrev());
/*     */   }
/*     */   
/* 268 */   private static final QNameMap<TransducedAccessor> EMPTY = new QNameMap();
/*     */   
/*     */   public JaxBeanInfo getBeanInfo() {
/* 271 */     return this.beanInfo;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/unmarshaller/StructureLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */