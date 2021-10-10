/*     */ package com.sun.xml.internal.bind.v2.runtime.reflect;
/*     */ 
/*     */ import com.sun.xml.internal.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.internal.bind.api.AccessorException;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Transducer;
/*     */ import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
/*     */ import javax.xml.bind.JAXBException;
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
/*     */ public final class ListTransducedAccessorImpl<BeanT, ListT, ItemT, PackT>
/*     */   extends DefaultTransducedAccessor<BeanT>
/*     */ {
/*     */   private final Transducer<ItemT> xducer;
/*     */   private final Lister<BeanT, ListT, ItemT, PackT> lister;
/*     */   private final Accessor<BeanT, ListT> acc;
/*     */   
/*     */   public ListTransducedAccessorImpl(Transducer<ItemT> xducer, Accessor<BeanT, ListT> acc, Lister<BeanT, ListT, ItemT, PackT> lister) {
/*  57 */     this.xducer = xducer;
/*  58 */     this.lister = lister;
/*  59 */     this.acc = acc;
/*     */   }
/*     */   
/*     */   public boolean useNamespace() {
/*  63 */     return this.xducer.useNamespace();
/*     */   }
/*     */   
/*     */   public void declareNamespace(BeanT bean, XMLSerializer w) throws AccessorException, SAXException {
/*  67 */     ListT list = this.acc.get(bean);
/*     */     
/*  69 */     if (list != null) {
/*  70 */       ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */       
/*  72 */       while (itr.hasNext()) {
/*     */         try {
/*  74 */           ItemT item = itr.next();
/*  75 */           if (item != null) {
/*  76 */             this.xducer.declareNamespace(item, w);
/*     */           }
/*  78 */         } catch (JAXBException e) {
/*  79 */           w.reportError(null, (Throwable)e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String print(BeanT o) throws AccessorException, SAXException {
/*  89 */     ListT list = this.acc.get(o);
/*     */     
/*  91 */     if (list == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     StringBuilder buf = new StringBuilder();
/*  95 */     XMLSerializer w = XMLSerializer.getInstance();
/*  96 */     ListIterator<ItemT> itr = this.lister.iterator(list, w);
/*     */     
/*  98 */     while (itr.hasNext()) {
/*     */       try {
/* 100 */         ItemT item = itr.next();
/* 101 */         if (item != null) {
/* 102 */           if (buf.length() > 0) buf.append(' '); 
/* 103 */           buf.append(this.xducer.print(item));
/*     */         } 
/* 105 */       } catch (JAXBException e) {
/* 106 */         w.reportError(null, (Throwable)e);
/*     */       } 
/*     */     } 
/* 109 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void processValue(BeanT bean, CharSequence s) throws AccessorException, SAXException {
/* 113 */     PackT pack = this.lister.startPacking(bean, this.acc);
/*     */     
/* 115 */     int idx = 0;
/* 116 */     int len = s.length();
/*     */     
/*     */     while (true) {
/* 119 */       int p = idx;
/* 120 */       while (p < len && !WhiteSpaceProcessor.isWhiteSpace(s.charAt(p))) {
/* 121 */         p++;
/*     */       }
/* 123 */       CharSequence token = s.subSequence(idx, p);
/* 124 */       if (!token.equals("")) {
/* 125 */         this.lister.addToPack(pack, (ItemT)this.xducer.parse(token));
/*     */       }
/* 127 */       if (p == len)
/*     */         break; 
/* 129 */       while (p < len && WhiteSpaceProcessor.isWhiteSpace(s.charAt(p)))
/* 130 */         p++; 
/* 131 */       if (p == len)
/*     */         break; 
/* 133 */       idx = p;
/*     */     } 
/*     */     
/* 136 */     this.lister.endPacking(pack, bean, this.acc);
/*     */   }
/*     */   
/*     */   public void parse(BeanT bean, CharSequence lexical) throws AccessorException, SAXException {
/* 140 */     processValue(bean, lexical);
/*     */   }
/*     */   
/*     */   public boolean hasValue(BeanT bean) throws AccessorException {
/* 144 */     return (this.acc.get(bean) != null);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/reflect/ListTransducedAccessorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */