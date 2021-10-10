/*     */ package com.sun.xml.internal.txw2;
/*     */ 
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/*     */   public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
/* 140 */   private static final Enumeration EMPTY_ENUMERATION = (new Vector())
/* 141 */     .elements();
/*     */ 
/*     */   
/*     */   private Context[] contexts;
/*     */   
/*     */   private Context currentContext;
/*     */   
/*     */   private int contextPos;
/*     */   
/*     */   private boolean namespaceDeclUris;
/*     */ 
/*     */   
/*     */   public NamespaceSupport() {
/* 154 */     reset();
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
/*     */   public void reset() {
/* 177 */     this.contexts = new Context[32];
/* 178 */     this.namespaceDeclUris = false;
/* 179 */     this.contextPos = 0;
/* 180 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 181 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/*     */   public void pushContext() {
/* 223 */     int max = this.contexts.length;
/*     */     
/* 225 */     this.contextPos++;
/*     */ 
/*     */     
/* 228 */     if (this.contextPos >= max) {
/* 229 */       Context[] newContexts = new Context[max * 2];
/* 230 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 231 */       max *= 2;
/* 232 */       this.contexts = newContexts;
/*     */     } 
/*     */ 
/*     */     
/* 236 */     this.currentContext = this.contexts[this.contextPos];
/* 237 */     if (this.currentContext == null) {
/* 238 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (this.contextPos > 0) {
/* 243 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/*     */ 
/*     */   
/*     */   public void popContext() {
/* 263 */     this.contexts[this.contextPos].clear();
/* 264 */     this.contextPos--;
/* 265 */     if (this.contextPos < 0) {
/* 266 */       throw new EmptyStackException();
/*     */     }
/* 268 */     this.currentContext = this.contexts[this.contextPos];
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
/*     */   public boolean declarePrefix(String prefix, String uri) {
/* 313 */     if (prefix.equals("xml") || prefix.equals("xmlns")) {
/* 314 */       return false;
/*     */     }
/* 316 */     this.currentContext.declarePrefix(prefix, uri);
/* 317 */     return true;
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
/*     */ 
/*     */   
/*     */   public String[] processName(String qName, String[] parts, boolean isAttribute) {
/* 365 */     String[] myParts = this.currentContext.processName(qName, isAttribute);
/* 366 */     if (myParts == null) {
/* 367 */       return null;
/*     */     }
/* 369 */     parts[0] = myParts[0];
/* 370 */     parts[1] = myParts[1];
/* 371 */     parts[2] = myParts[2];
/* 372 */     return parts;
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
/*     */   public String getURI(String prefix) {
/* 391 */     return this.currentContext.getURI(prefix);
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
/*     */   public Enumeration getPrefixes() {
/* 411 */     return this.currentContext.getPrefixes();
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
/*     */   public String getPrefix(String uri) {
/* 436 */     return this.currentContext.getPrefix(uri);
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
/*     */   public Enumeration getPrefixes(String uri) {
/* 465 */     Vector<String> prefixes = new Vector();
/* 466 */     Enumeration<String> allPrefixes = getPrefixes();
/* 467 */     while (allPrefixes.hasMoreElements()) {
/* 468 */       String prefix = allPrefixes.nextElement();
/* 469 */       if (uri.equals(getURI(prefix))) {
/* 470 */         prefixes.addElement(prefix);
/*     */       }
/*     */     } 
/* 473 */     return prefixes.elements();
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
/*     */   public Enumeration getDeclaredPrefixes() {
/* 491 */     return this.currentContext.getDeclaredPrefixes();
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
/*     */   public void setNamespaceDeclUris(boolean value) {
/* 507 */     if (this.contextPos != 0)
/* 508 */       throw new IllegalStateException(); 
/* 509 */     if (value == this.namespaceDeclUris)
/*     */       return; 
/* 511 */     this.namespaceDeclUris = value;
/* 512 */     if (value) {
/* 513 */       this.currentContext.declarePrefix("xmlns", "http://www.w3.org/xmlns/2000/");
/*     */     } else {
/* 515 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/* 516 */       this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNamespaceDeclUris() {
/* 527 */     return this.namespaceDeclUris;
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
/*     */   final class Context
/*     */   {
/*     */     Hashtable prefixTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable uriTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable elementNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Hashtable attributeNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     
/*     */     private Vector declarations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean declSeen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     
/*     */     Context() {
/* 831 */       this.defaultNS = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 839 */       this.declarations = null;
/* 840 */       this.declSeen = false;
/* 841 */       this.parent = null;
/*     */       copyTables();
/*     */     }
/*     */     
/*     */     void setParent(Context parent) {
/*     */       this.parent = parent;
/*     */       this.declarations = null;
/*     */       this.prefixTable = parent.prefixTable;
/*     */       this.uriTable = parent.uriTable;
/*     */       this.elementNameTable = parent.elementNameTable;
/*     */       this.attributeNameTable = parent.attributeNameTable;
/*     */       this.defaultNS = parent.defaultNS;
/*     */       this.declSeen = false;
/*     */     }
/*     */     
/*     */     void clear() {
/*     */       this.parent = null;
/*     */       this.prefixTable = null;
/*     */       this.uriTable = null;
/*     */       this.elementNameTable = null;
/*     */       this.attributeNameTable = null;
/*     */       this.defaultNS = "";
/*     */     }
/*     */     
/*     */     void declarePrefix(String prefix, String uri) {
/*     */       if (!this.declSeen)
/*     */         copyTables(); 
/*     */       if (this.declarations == null)
/*     */         this.declarations = new Vector(); 
/*     */       prefix = prefix.intern();
/*     */       uri = uri.intern();
/*     */       if ("".equals(prefix)) {
/*     */         this.defaultNS = uri;
/*     */       } else {
/*     */         this.prefixTable.put(prefix, uri);
/*     */         this.uriTable.put(uri, prefix);
/*     */       } 
/*     */       this.declarations.addElement(prefix);
/*     */     }
/*     */     
/*     */     String[] processName(String qName, boolean isAttribute) {
/*     */       Hashtable<String, String[]> table;
/*     */       if (isAttribute) {
/*     */         table = this.attributeNameTable;
/*     */       } else {
/*     */         table = this.elementNameTable;
/*     */       } 
/*     */       String[] name = (String[])table.get(qName);
/*     */       if (name != null)
/*     */         return name; 
/*     */       name = new String[3];
/*     */       name[2] = qName.intern();
/*     */       int index = qName.indexOf(':');
/*     */       if (index == -1) {
/*     */         if (isAttribute) {
/*     */           if (qName == "xmlns" && NamespaceSupport.this.namespaceDeclUris) {
/*     */             name[0] = "http://www.w3.org/xmlns/2000/";
/*     */           } else {
/*     */             name[0] = "";
/*     */           } 
/*     */         } else {
/*     */           name[0] = this.defaultNS;
/*     */         } 
/*     */         name[1] = name[2];
/*     */       } else {
/*     */         String uri, prefix = qName.substring(0, index);
/*     */         String local = qName.substring(index + 1);
/*     */         if ("".equals(prefix)) {
/*     */           uri = this.defaultNS;
/*     */         } else {
/*     */           uri = (String)this.prefixTable.get(prefix);
/*     */         } 
/*     */         if (uri == null || (!isAttribute && "xmlns".equals(prefix)))
/*     */           return null; 
/*     */         name[0] = uri;
/*     */         name[1] = local.intern();
/*     */       } 
/*     */       table.put(name[2], name);
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
/*     */       if (this.uriTable != null) {
/*     */         String uriPrefix = (String)this.uriTable.get(uri);
/*     */         if (uriPrefix == null)
/*     */           return null; 
/*     */         String verifyNamespace = (String)this.prefixTable.get(uriPrefix);
/*     */         if (uri.equals(verifyNamespace))
/*     */           return uriPrefix; 
/*     */       } 
/*     */       return null;
/*     */     }
/*     */     
/*     */     Enumeration getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.declarations.elements();
/*     */     }
/*     */     
/*     */     Enumeration getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return this.prefixTable.keys();
/*     */     }
/*     */     
/*     */     private void copyTables() {
/*     */       if (this.prefixTable != null) {
/*     */         this.prefixTable = (Hashtable)this.prefixTable.clone();
/*     */       } else {
/*     */         this.prefixTable = new Hashtable<>();
/*     */       } 
/*     */       if (this.uriTable != null) {
/*     */         this.uriTable = (Hashtable)this.uriTable.clone();
/*     */       } else {
/*     */         this.uriTable = new Hashtable<>();
/*     */       } 
/*     */       this.elementNameTable = new Hashtable<>();
/*     */       this.attributeNameTable = new Hashtable<>();
/*     */       this.declSeen = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/txw2/NamespaceSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */