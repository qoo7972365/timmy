/*     */ package com.sun.xml.internal.bind.v2.runtime;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.ClassFactory;
/*     */ import java.util.HashMap;
/*     */ import javax.xml.bind.ValidationEvent;
/*     */ import javax.xml.bind.ValidationEventHandler;
/*     */ import javax.xml.bind.ValidationEventLocator;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
/*     */ import javax.xml.bind.helpers.ValidationEventImpl;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Coordinator
/*     */   implements ErrorHandler, ValidationEventHandler
/*     */ {
/*  67 */   private final HashMap<Class<? extends XmlAdapter>, XmlAdapter> adapters = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public final XmlAdapter putAdapter(Class<? extends XmlAdapter> c, XmlAdapter a) {
/*  72 */     if (a == null) {
/*  73 */       return this.adapters.remove(c);
/*     */     }
/*  75 */     return this.adapters.put(c, a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T extends XmlAdapter> T getAdapter(Class<T> key) {
/*  85 */     XmlAdapter xmlAdapter = (XmlAdapter)key.cast(this.adapters.get(key));
/*  86 */     if (xmlAdapter == null) {
/*  87 */       xmlAdapter = (XmlAdapter)ClassFactory.create(key);
/*  88 */       putAdapter(key, xmlAdapter);
/*     */     } 
/*  90 */     return (T)xmlAdapter;
/*     */   }
/*     */   
/*     */   public <T extends XmlAdapter> boolean containsAdapter(Class<T> type) {
/*  94 */     return this.adapters.containsKey(type);
/*     */   }
/*     */ 
/*     */   
/*  98 */   private static final ThreadLocal<Coordinator> activeTable = new ThreadLocal<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Coordinator old;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void pushCoordinator() {
/* 109 */     this.old = activeTable.get();
/* 110 */     activeTable.set(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void popCoordinator() {
/* 117 */     if (this.old != null) {
/* 118 */       activeTable.set(this.old);
/*     */     } else {
/* 120 */       activeTable.remove();
/* 121 */     }  this.old = null;
/*     */   }
/*     */   
/*     */   public static Coordinator _getInstance() {
/* 125 */     return activeTable.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ValidationEventLocator getLocation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void error(SAXParseException exception) throws SAXException {
/* 139 */     propagateEvent(1, exception);
/*     */   }
/*     */   
/*     */   public final void warning(SAXParseException exception) throws SAXException {
/* 143 */     propagateEvent(0, exception);
/*     */   }
/*     */   
/*     */   public final void fatalError(SAXParseException exception) throws SAXException {
/* 147 */     propagateEvent(2, exception);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void propagateEvent(int severity, SAXParseException saxException) throws SAXException {
/* 154 */     ValidationEventImpl ve = new ValidationEventImpl(severity, saxException.getMessage(), getLocation());
/*     */     
/* 156 */     Exception e = saxException.getException();
/* 157 */     if (e != null) {
/* 158 */       ve.setLinkedException(e);
/*     */     } else {
/* 160 */       ve.setLinkedException(saxException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 165 */     boolean result = handleEvent((ValidationEvent)ve);
/* 166 */     if (!result)
/*     */     {
/*     */       
/* 169 */       throw saxException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/bind/v2/runtime/Coordinator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */