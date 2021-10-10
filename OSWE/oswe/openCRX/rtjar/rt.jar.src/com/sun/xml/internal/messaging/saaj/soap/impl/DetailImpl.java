/*     */ package com.sun.xml.internal.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.internal.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.DetailEntry;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DetailImpl
/*     */   extends FaultElementImpl
/*     */   implements Detail
/*     */ {
/*     */   public DetailImpl(SOAPDocumentImpl ownerDoc, NameImpl detailName) {
/*  41 */     super(ownerDoc, detailName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract DetailEntry createDetailEntry(Name paramName);
/*     */   
/*     */   public DetailEntry addDetailEntry(Name name) throws SOAPException {
/*  48 */     DetailEntry entry = createDetailEntry(name);
/*  49 */     addNode((Node)entry);
/*  50 */     return entry;
/*     */   }
/*     */   protected abstract DetailEntry createDetailEntry(QName paramQName);
/*     */   public DetailEntry addDetailEntry(QName qname) throws SOAPException {
/*  54 */     DetailEntry entry = createDetailEntry(qname);
/*  55 */     addNode((Node)entry);
/*  56 */     return entry;
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(Name name) throws SOAPException {
/*  60 */     return (SOAPElement)addDetailEntry(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement addElement(QName name) throws SOAPException {
/*  64 */     return (SOAPElement)addDetailEntry(name);
/*     */   }
/*     */   
/*     */   protected SOAPElement convertToSoapElement(Element element) {
/*  68 */     if (element instanceof DetailEntry) {
/*  69 */       return (SOAPElement)element;
/*     */     }
/*     */     
/*  72 */     DetailEntry detailEntry = createDetailEntry(NameImpl.copyElementName(element));
/*  73 */     return replaceElementWithSOAPElement(element, (ElementImpl)detailEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getDetailEntries() {
/*  80 */     return new Iterator() {
/*  81 */         Iterator eachNode = DetailImpl.this.getChildElementNodes();
/*  82 */         SOAPElement next = null;
/*  83 */         SOAPElement last = null;
/*     */         
/*     */         public boolean hasNext() {
/*  86 */           if (this.next == null) {
/*  87 */             while (this.eachNode.hasNext()) {
/*  88 */               this.next = this.eachNode.next();
/*  89 */               if (this.next instanceof DetailEntry) {
/*     */                 break;
/*     */               }
/*  92 */               this.next = null;
/*     */             } 
/*     */           }
/*  95 */           return (this.next != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/*  99 */           if (!hasNext()) {
/* 100 */             throw new NoSuchElementException();
/*     */           }
/* 102 */           this.last = this.next;
/* 103 */           this.next = null;
/* 104 */           return this.last;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 108 */           if (this.last == null) {
/* 109 */             throw new IllegalStateException();
/*     */           }
/* 111 */           SOAPElement target = this.last;
/* 112 */           DetailImpl.this.removeChild((Node)target);
/* 113 */           this.last = null;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected boolean isStandardFaultElement() {
/* 119 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/messaging/saaj/soap/impl/DetailImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */