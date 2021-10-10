/*     */ package com.sun.xml.internal.ws.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/* 106 */   private static final Iterable<String> EMPTY_ENUMERATION = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private Context[] contexts;
/*     */   
/*     */   private Context currentContext;
/*     */   
/*     */   private int contextPos;
/*     */ 
/*     */   
/*     */   public NamespaceSupport() {
/* 117 */     reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceSupport(NamespaceSupport that) {
/* 122 */     this.contexts = new Context[that.contexts.length];
/* 123 */     this.currentContext = null;
/* 124 */     this.contextPos = that.contextPos;
/*     */     
/* 126 */     Context currentParent = null;
/*     */     
/* 128 */     for (int i = 0; i < that.contexts.length; i++) {
/* 129 */       Context thatContext = that.contexts[i];
/*     */       
/* 131 */       if (thatContext == null) {
/* 132 */         this.contexts[i] = null;
/*     */       }
/*     */       else {
/*     */         
/* 136 */         Context thisContext = new Context(thatContext, currentParent);
/* 137 */         this.contexts[i] = thisContext;
/* 138 */         if (that.currentContext == thatContext) {
/* 139 */           this.currentContext = thisContext;
/*     */         }
/*     */         
/* 142 */         currentParent = thisContext;
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
/*     */   public void reset() {
/* 157 */     this.contexts = new Context[32];
/* 158 */     this.contextPos = 0;
/* 159 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 160 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/*     */   public void pushContext() {
/* 178 */     int max = this.contexts.length;
/* 179 */     this.contextPos++;
/*     */ 
/*     */     
/* 182 */     if (this.contextPos >= max) {
/* 183 */       Context[] newContexts = new Context[max * 2];
/* 184 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 185 */       this.contexts = newContexts;
/*     */     } 
/*     */ 
/*     */     
/* 189 */     this.currentContext = this.contexts[this.contextPos];
/* 190 */     if (this.currentContext == null) {
/* 191 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 195 */     if (this.contextPos > 0) {
/* 196 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/*     */ 
/*     */   
/*     */   public void popContext() {
/* 214 */     this.contextPos--;
/* 215 */     if (this.contextPos < 0) {
/* 216 */       throw new EmptyStackException();
/*     */     }
/* 218 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextUp() {
/* 226 */     this.contextPos--;
/* 227 */     this.currentContext = this.contexts[this.contextPos];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slideContextDown() {
/* 235 */     this.contextPos++;
/*     */     
/* 237 */     if (this.contexts[this.contextPos] == null)
/*     */     {
/* 239 */       this.contexts[this.contextPos] = this.contexts[this.contextPos - 1];
/*     */     }
/*     */     
/* 242 */     this.currentContext = this.contexts[this.contextPos];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean declarePrefix(String prefix, String uri) {
/* 279 */     if ((prefix.equals("xml") && !uri.equals("http://www.w3.org/XML/1998/namespace")) || prefix
/* 280 */       .equals("xmlns")) {
/* 281 */       return false;
/*     */     }
/* 283 */     this.currentContext.declarePrefix(prefix, uri);
/* 284 */     return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] processName(String qName, String[] parts, boolean isAttribute) {
/* 330 */     String[] myParts = this.currentContext.processName(qName, isAttribute);
/* 331 */     if (myParts == null) {
/* 332 */       return null;
/*     */     }
/* 334 */     parts[0] = myParts[0];
/* 335 */     parts[1] = myParts[1];
/* 336 */     parts[2] = myParts[2];
/* 337 */     return parts;
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
/*     */   public String getURI(String prefix) {
/* 354 */     return this.currentContext.getURI(prefix);
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
/*     */   public Iterable<String> getPrefixes() {
/* 371 */     return this.currentContext.getPrefixes();
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
/*     */   public String getPrefix(String uri) {
/* 394 */     return this.currentContext.getPrefix(uri);
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
/*     */   public Iterator getPrefixes(String uri) {
/* 419 */     List<String> prefixes = new ArrayList();
/* 420 */     for (String prefix : getPrefixes()) {
/* 421 */       if (uri.equals(getURI(prefix))) {
/* 422 */         prefixes.add(prefix);
/*     */       }
/*     */     } 
/* 425 */     return prefixes.iterator();
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
/*     */   public Iterable<String> getDeclaredPrefixes() {
/* 441 */     return this.currentContext.getDeclaredPrefixes();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Context
/*     */   {
/*     */     HashMap prefixTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap uriTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap elementNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     HashMap attributeNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String defaultNS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ArrayList declarations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean tablesDirty;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Context parent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Context() {
/* 735 */       this.defaultNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 742 */       this.declarations = null;
/* 743 */       this.tablesDirty = false;
/* 744 */       this.parent = null; copyTables(); } Context(Context that, Context newParent) { this.defaultNS = null; this.declarations = null; this.tablesDirty = false; this.parent = null;
/*     */       if (that == null) {
/*     */         copyTables();
/*     */         return;
/*     */       } 
/*     */       if (newParent != null && !that.tablesDirty) {
/*     */         this.prefixTable = (that.prefixTable == that.parent.prefixTable) ? newParent.prefixTable : (HashMap)that.prefixTable.clone();
/*     */         this.uriTable = (that.uriTable == that.parent.uriTable) ? newParent.uriTable : (HashMap)that.uriTable.clone();
/*     */         this.elementNameTable = (that.elementNameTable == that.parent.elementNameTable) ? newParent.elementNameTable : (HashMap)that.elementNameTable.clone();
/*     */         this.attributeNameTable = (that.attributeNameTable == that.parent.attributeNameTable) ? newParent.attributeNameTable : (HashMap)that.attributeNameTable.clone();
/*     */         this.defaultNS = (that.defaultNS == that.parent.defaultNS) ? newParent.defaultNS : that.defaultNS;
/*     */       } else {
/*     */         this.prefixTable = (HashMap)that.prefixTable.clone();
/*     */         this.uriTable = (HashMap)that.uriTable.clone();
/*     */         this.elementNameTable = (HashMap)that.elementNameTable.clone();
/*     */         this.attributeNameTable = (HashMap)that.attributeNameTable.clone();
/*     */         this.defaultNS = that.defaultNS;
/*     */       } 
/*     */       this.tablesDirty = that.tablesDirty;
/*     */       this.parent = newParent;
/*     */       this.declarations = (that.declarations == null) ? null : (ArrayList)that.declarations.clone(); }
/*     */ 
/*     */     
/*     */     void setParent(Context parent) {
/*     */       this.parent = parent;
/*     */       this.declarations = null;
/*     */       this.prefixTable = parent.prefixTable;
/*     */       this.uriTable = parent.uriTable;
/*     */       this.elementNameTable = parent.elementNameTable;
/*     */       this.attributeNameTable = parent.attributeNameTable;
/*     */       this.defaultNS = parent.defaultNS;
/*     */       this.tablesDirty = false;
/*     */     }
/*     */     
/*     */     void declarePrefix(String prefix, String uri) {
/*     */       if (!this.tablesDirty)
/*     */         copyTables(); 
/*     */       if (this.declarations == null)
/*     */         this.declarations = new ArrayList(); 
/*     */       prefix = prefix.intern();
/*     */       uri = uri.intern();
/*     */       if ("".equals(prefix)) {
/*     */         if ("".equals(uri)) {
/*     */           this.defaultNS = null;
/*     */         } else {
/*     */           this.defaultNS = uri;
/*     */         } 
/*     */       } else {
/*     */         this.prefixTable.put(prefix, uri);
/*     */         this.uriTable.put(uri, prefix);
/*     */       } 
/*     */       this.declarations.add(prefix);
/*     */     }
/*     */     
/*     */     String[] processName(String qName, boolean isAttribute) {
/*     */       Map<String, String[]> table;
/*     */       if (isAttribute) {
/*     */         table = this.elementNameTable;
/*     */       } else {
/*     */         table = this.attributeNameTable;
/*     */       } 
/*     */       String[] name = (String[])table.get(qName);
/*     */       if (name != null)
/*     */         return name; 
/*     */       name = new String[3];
/*     */       int index = qName.indexOf(':');
/*     */       if (index == -1) {
/*     */         if (isAttribute || this.defaultNS == null) {
/*     */           name[0] = "";
/*     */         } else {
/*     */           name[0] = this.defaultNS;
/*     */         } 
/*     */         name[1] = qName.intern();
/*     */         name[2] = name[1];
/*     */       } else {
/*     */         String uri, prefix = qName.substring(0, index);
/*     */         String local = qName.substring(index + 1);
/*     */         if ("".equals(prefix)) {
/*     */           uri = this.defaultNS;
/*     */         } else {
/*     */           uri = (String)this.prefixTable.get(prefix);
/*     */         } 
/*     */         if (uri == null)
/*     */           return null; 
/*     */         name[0] = uri;
/*     */         name[1] = local.intern();
/*     */         name[2] = qName.intern();
/*     */       } 
/*     */       table.put(name[2], name);
/*     */       this.tablesDirty = true;
/*     */       return name;
/*     */     }
/*     */     
/*     */     String getURI(String prefix) {
/*     */       if ("".equals(prefix))
/*     */         return this.defaultNS; 
/*     */       if (this.prefixTable == null)
/*     */         return null; 
/*     */       return (String)this.prefixTable.get(prefix);
/*     */     }
/*     */     
/*     */     String getPrefix(String uri) {
/*     */       if (this.uriTable == null)
/*     */         return null; 
/*     */       return (String)this.uriTable.get(uri);
/*     */     }
/*     */     
/*     */     Iterable<String> getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.declarations;
/*     */     }
/*     */     
/*     */     Iterable<String> getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.prefixTable.keySet();
/*     */     }
/*     */     
/*     */     private void copyTables() {
/*     */       if (this.prefixTable != null) {
/*     */         this.prefixTable = (HashMap)this.prefixTable.clone();
/*     */       } else {
/*     */         this.prefixTable = new HashMap<>();
/*     */       } 
/*     */       if (this.uriTable != null) {
/*     */         this.uriTable = (HashMap)this.uriTable.clone();
/*     */       } else {
/*     */         this.uriTable = new HashMap<>();
/*     */       } 
/*     */       this.elementNameTable = new HashMap<>();
/*     */       this.attributeNameTable = new HashMap<>();
/*     */       this.tablesDirty = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/NamespaceSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */