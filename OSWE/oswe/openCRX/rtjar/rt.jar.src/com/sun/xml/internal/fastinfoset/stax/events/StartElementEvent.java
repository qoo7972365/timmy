/*     */ package com.sun.xml.internal.fastinfoset.stax.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.events.Attribute;
/*     */ import javax.xml.stream.events.Namespace;
/*     */ import javax.xml.stream.events.StartElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartElementEvent
/*     */   extends EventBase
/*     */   implements StartElement
/*     */ {
/*     */   private Map _attributes;
/*     */   private List _namespaces;
/*  49 */   private NamespaceContext _context = null;
/*     */   private QName _qname;
/*     */   
/*     */   public void reset() {
/*  53 */     if (this._attributes != null) this._attributes.clear(); 
/*  54 */     if (this._namespaces != null) this._namespaces.clear(); 
/*  55 */     if (this._context != null) this._context = null; 
/*     */   }
/*     */   
/*     */   public StartElementEvent() {
/*  59 */     init();
/*     */   }
/*     */   
/*     */   public StartElementEvent(String prefix, String uri, String localpart) {
/*  63 */     init();
/*  64 */     if (uri == null) uri = ""; 
/*  65 */     if (prefix == null) prefix = ""; 
/*  66 */     this._qname = new QName(uri, localpart, prefix);
/*  67 */     setEventType(1);
/*     */   }
/*     */   
/*     */   public StartElementEvent(QName qname) {
/*  71 */     init();
/*  72 */     this._qname = qname;
/*     */   }
/*     */   
/*     */   public StartElementEvent(StartElement startelement) {
/*  76 */     this(startelement.getName());
/*  77 */     addAttributes(startelement.getAttributes());
/*  78 */     addNamespaces(startelement.getNamespaces());
/*     */   }
/*     */   
/*     */   protected void init() {
/*  82 */     setEventType(1);
/*  83 */     this._attributes = new HashMap<>();
/*  84 */     this._namespaces = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getName() {
/*  93 */     return this._qname;
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
/*     */   public Iterator getAttributes() {
/* 106 */     if (this._attributes != null) {
/* 107 */       Collection coll = this._attributes.values();
/* 108 */       return new ReadIterator(coll.iterator());
/*     */     } 
/* 110 */     return EmptyIterator.getInstance();
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
/*     */   public Iterator getNamespaces() {
/* 135 */     if (this._namespaces != null) {
/* 136 */       return new ReadIterator(this._namespaces.iterator());
/*     */     }
/* 138 */     return EmptyIterator.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attribute getAttributeByName(QName qname) {
/* 147 */     if (qname == null)
/* 148 */       return null; 
/* 149 */     return (Attribute)this._attributes.get(qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 160 */     return this._context;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(QName qname) {
/* 165 */     this._qname = qname;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespace() {
/* 170 */     return this._qname.getNamespaceURI();
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
/*     */   public String getNamespaceURI(String prefix) {
/* 182 */     if (getNamespace() != null) return getNamespace();
/*     */     
/* 184 */     if (this._context != null)
/* 185 */       return this._context.getNamespaceURI(prefix); 
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 190 */     StringBuilder sb = new StringBuilder(64);
/*     */     
/* 192 */     sb.append('<').append(nameAsString());
/*     */     
/* 194 */     if (this._attributes != null) {
/* 195 */       Iterator<Attribute> it = getAttributes();
/* 196 */       Attribute attr = null;
/* 197 */       while (it.hasNext()) {
/* 198 */         attr = it.next();
/* 199 */         sb.append(' ').append(attr.toString());
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     if (this._namespaces != null) {
/* 204 */       Iterator<Namespace> it = this._namespaces.iterator();
/* 205 */       Namespace attr = null;
/* 206 */       while (it.hasNext()) {
/* 207 */         attr = it.next();
/* 208 */         sb.append(' ').append(attr.toString());
/*     */       } 
/*     */     } 
/* 211 */     sb.append('>');
/* 212 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nameAsString() {
/* 219 */     if ("".equals(this._qname.getNamespaceURI()))
/* 220 */       return this._qname.getLocalPart(); 
/* 221 */     if (this._qname.getPrefix() != null) {
/* 222 */       return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getPrefix() + ":" + this._qname.getLocalPart();
/*     */     }
/* 224 */     return "['" + this._qname.getNamespaceURI() + "']:" + this._qname.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) {
/* 229 */     this._context = context;
/*     */   }
/*     */   
/*     */   public void addAttribute(Attribute attr) {
/* 233 */     this._attributes.put(attr.getName(), attr);
/*     */   }
/*     */   
/*     */   public void addAttributes(Iterator<Attribute> attrs) {
/* 237 */     if (attrs != null) {
/* 238 */       while (attrs.hasNext()) {
/* 239 */         Attribute attr = attrs.next();
/* 240 */         this._attributes.put(attr.getName(), attr);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addNamespace(Namespace namespace) {
/* 246 */     if (namespace != null) {
/* 247 */       this._namespaces.add(namespace);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addNamespaces(Iterator<Namespace> namespaces) {
/* 252 */     if (namespaces != null)
/* 253 */       while (namespaces.hasNext()) {
/* 254 */         Namespace namespace = namespaces.next();
/* 255 */         this._namespaces.add(namespace);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/fastinfoset/stax/events/StartElementEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */