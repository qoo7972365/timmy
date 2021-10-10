/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Coordinator;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*     */ final class AdaptedLister<BeanT, PropT, InMemItemT, OnWireItemT, PackT>
/*     */   extends Lister<BeanT, PropT, OnWireItemT, PackT>
/*     */ {
/*     */   private final Lister<BeanT, PropT, InMemItemT, PackT> core;
/*     */   private final Class<? extends XmlAdapter<OnWireItemT, InMemItemT>> adapter;
/*     */   
/*     */   AdaptedLister(Lister<BeanT, PropT, InMemItemT, PackT> core, Class<? extends XmlAdapter<OnWireItemT, InMemItemT>> adapter) {
/*  48 */     this.core = core;
/*  49 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   private XmlAdapter<OnWireItemT, InMemItemT> getAdapter() {
/*  53 */     return Coordinator._getInstance().getAdapter(this.adapter);
/*     */   }
/*     */   
/*     */   public ListIterator<OnWireItemT> iterator(PropT prop, XMLSerializer context) {
/*  57 */     return new ListIteratorImpl(this.core.iterator(prop, context), context);
/*     */   }
/*     */   
/*     */   public PackT startPacking(BeanT bean, Accessor<BeanT, PropT> accessor) throws AccessorException {
/*  61 */     return this.core.startPacking(bean, accessor);
/*     */   }
/*     */   
/*     */   public void addToPack(PackT pack, OnWireItemT item) throws AccessorException {
/*     */     InMemItemT r;
/*     */     try {
/*  67 */       r = (InMemItemT)getAdapter().unmarshal(item);
/*  68 */     } catch (Exception e) {
/*  69 */       throw new AccessorException(e);
/*     */     } 
/*  71 */     this.core.addToPack(pack, r);
/*     */   }
/*     */   
/*     */   public void endPacking(PackT pack, BeanT bean, Accessor<BeanT, PropT> accessor) throws AccessorException {
/*  75 */     this.core.endPacking(pack, bean, accessor);
/*     */   }
/*     */   
/*     */   public void reset(BeanT bean, Accessor<BeanT, PropT> accessor) throws AccessorException {
/*  79 */     this.core.reset(bean, accessor);
/*     */   }
/*     */   
/*     */   private final class ListIteratorImpl implements ListIterator<OnWireItemT> {
/*     */     private final ListIterator<InMemItemT> core;
/*     */     private final XMLSerializer serializer;
/*     */     
/*     */     public ListIteratorImpl(ListIterator<InMemItemT> core, XMLSerializer serializer) {
/*  87 */       this.core = core;
/*  88 */       this.serializer = serializer;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/*  92 */       return this.core.hasNext();
/*     */     }
/*     */     
/*     */     public OnWireItemT next() throws SAXException, JAXBException {
/*  96 */       InMemItemT next = this.core.next();
/*     */       try {
/*  98 */         return (OnWireItemT)AdaptedLister.this.getAdapter().marshal(next);
/*  99 */       } catch (Exception e) {
/* 100 */         this.serializer.reportError(null, e);
/* 101 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/AdaptedLister.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */