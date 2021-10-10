/*     */ package com.sun.xml.internal.ws.model.wsdl;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtensible;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLExtension;
/*     */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLObject;
/*     */ import com.sun.xml.internal.ws.resources.UtilMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.xml.sax.Locator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractExtensibleImpl
/*     */   extends AbstractObjectImpl
/*     */   implements WSDLExtensible
/*     */ {
/*  52 */   protected final Set<WSDLExtension> extensions = new HashSet<>();
/*     */ 
/*     */   
/*  55 */   protected List<UnknownWSDLExtension> notUnderstoodExtensions = new ArrayList<>();
/*     */ 
/*     */   
/*     */   protected AbstractExtensibleImpl(XMLStreamReader xsr) {
/*  59 */     super(xsr);
/*     */   }
/*     */   
/*     */   protected AbstractExtensibleImpl(String systemId, int lineNumber) {
/*  63 */     super(systemId, lineNumber);
/*     */   }
/*     */   
/*     */   public final Iterable<WSDLExtension> getExtensions() {
/*  67 */     return this.extensions;
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T extends WSDLExtension> Iterable<T> getExtensions(Class<T> type) {
/*  72 */     List<T> r = new ArrayList<>(this.extensions.size());
/*  73 */     for (WSDLExtension e : this.extensions) {
/*  74 */       if (type.isInstance(e))
/*  75 */         r.add(type.cast(e)); 
/*     */     } 
/*  77 */     return r;
/*     */   }
/*     */   
/*     */   public <T extends WSDLExtension> T getExtension(Class<T> type) {
/*  81 */     for (WSDLExtension e : this.extensions) {
/*  82 */       if (type.isInstance(e))
/*  83 */         return type.cast(e); 
/*     */     } 
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public void addExtension(WSDLExtension ex) {
/*  89 */     if (ex == null)
/*     */     {
/*  91 */       throw new IllegalArgumentException(); } 
/*  92 */     this.extensions.add(ex);
/*     */   }
/*     */   
/*     */   public List<? extends UnknownWSDLExtension> getNotUnderstoodExtensions() {
/*  96 */     return this.notUnderstoodExtensions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotUnderstoodExtension(QName extnEl, Locator locator) {
/* 106 */     this.notUnderstoodExtensions.add(new UnknownWSDLExtension(extnEl, locator));
/*     */   }
/*     */   
/*     */   protected static class UnknownWSDLExtension implements WSDLExtension, WSDLObject { private final QName extnEl;
/*     */     private final Locator locator;
/*     */     
/*     */     public UnknownWSDLExtension(QName extnEl, Locator locator) {
/* 113 */       this.extnEl = extnEl;
/* 114 */       this.locator = locator;
/*     */     }
/*     */     public QName getName() {
/* 117 */       return this.extnEl;
/*     */     } @NotNull
/*     */     public Locator getLocation() {
/* 120 */       return this.locator;
/*     */     }
/*     */     public String toString() {
/* 123 */       return this.extnEl + " " + UtilMessages.UTIL_LOCATION(Integer.valueOf(this.locator.getLineNumber()), this.locator.getSystemId());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areRequiredExtensionsUnderstood() {
/* 132 */     if (this.notUnderstoodExtensions.size() != 0) {
/* 133 */       StringBuilder buf = new StringBuilder("Unknown WSDL extensibility elements:");
/* 134 */       for (UnknownWSDLExtension extn : this.notUnderstoodExtensions)
/* 135 */         buf.append('\n').append(extn.toString()); 
/* 136 */       throw new WebServiceException(buf.toString());
/*     */     } 
/* 138 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/model/wsdl/AbstractExtensibleImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */