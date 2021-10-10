/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EmptyStackException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NamespaceSupport
/*     */ {
/*     */   public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
/*     */   public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
/* 144 */   private static final Enumeration EMPTY_ENUMERATION = Collections.enumeration(new ArrayList());
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
/* 157 */     reset();
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
/* 180 */     this.contexts = new Context[32];
/* 181 */     this.namespaceDeclUris = false;
/* 182 */     this.contextPos = 0;
/* 183 */     this.contexts[this.contextPos] = this.currentContext = new Context();
/* 184 */     this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/* 226 */     int max = this.contexts.length;
/*     */     
/* 228 */     this.contextPos++;
/*     */ 
/*     */     
/* 231 */     if (this.contextPos >= max) {
/* 232 */       Context[] newContexts = new Context[max * 2];
/* 233 */       System.arraycopy(this.contexts, 0, newContexts, 0, max);
/* 234 */       max *= 2;
/* 235 */       this.contexts = newContexts;
/*     */     } 
/*     */ 
/*     */     
/* 239 */     this.currentContext = this.contexts[this.contextPos];
/* 240 */     if (this.currentContext == null) {
/* 241 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/*     */     }
/*     */ 
/*     */     
/* 245 */     if (this.contextPos > 0) {
/* 246 */       this.currentContext.setParent(this.contexts[this.contextPos - 1]);
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
/* 266 */     this.contexts[this.contextPos].clear();
/* 267 */     this.contextPos--;
/* 268 */     if (this.contextPos < 0) {
/* 269 */       throw new EmptyStackException();
/*     */     }
/* 271 */     this.currentContext = this.contexts[this.contextPos];
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
/* 316 */     if (prefix.equals("xml") || prefix.equals("xmlns")) {
/* 317 */       return false;
/*     */     }
/* 319 */     this.currentContext.declarePrefix(prefix, uri);
/* 320 */     return true;
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
/* 368 */     String[] myParts = this.currentContext.processName(qName, isAttribute);
/* 369 */     if (myParts == null) {
/* 370 */       return null;
/*     */     }
/* 372 */     parts[0] = myParts[0];
/* 373 */     parts[1] = myParts[1];
/* 374 */     parts[2] = myParts[2];
/* 375 */     return parts;
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
/* 394 */     return this.currentContext.getURI(prefix);
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
/* 414 */     return this.currentContext.getPrefixes();
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
/* 439 */     return this.currentContext.getPrefix(uri);
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
/* 468 */     List<String> prefixes = new ArrayList<>();
/* 469 */     Enumeration<String> allPrefixes = getPrefixes();
/* 470 */     while (allPrefixes.hasMoreElements()) {
/* 471 */       String prefix = allPrefixes.nextElement();
/* 472 */       if (uri.equals(getURI(prefix))) {
/* 473 */         prefixes.add(prefix);
/*     */       }
/*     */     } 
/* 476 */     return Collections.enumeration(prefixes);
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
/* 494 */     return this.currentContext.getDeclaredPrefixes();
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
/* 510 */     if (this.contextPos != 0)
/* 511 */       throw new IllegalStateException(); 
/* 512 */     if (value == this.namespaceDeclUris)
/*     */       return; 
/* 514 */     this.namespaceDeclUris = value;
/* 515 */     if (value) {
/* 516 */       this.currentContext.declarePrefix("xmlns", "http://www.w3.org/xmlns/2000/");
/*     */     } else {
/* 518 */       this.contexts[this.contextPos] = this.currentContext = new Context();
/* 519 */       this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
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
/* 530 */     return this.namespaceDeclUris;
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
/*     */     Map<String, String> prefixTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Map<String, String> uriTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Map<String, String[]> elementNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Map<String, String[]> attributeNameTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */     private List<String> declarations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 836 */       this.defaultNS = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 844 */       this.declarations = null;
/* 845 */       this.declSeen = false;
/* 846 */       this.parent = null;
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
/*     */       this.defaultNS = null;
/*     */     }
/*     */     
/*     */     void declarePrefix(String prefix, String uri) {
/*     */       if (!this.declSeen)
/*     */         copyTables(); 
/*     */       if (this.declarations == null)
/*     */         this.declarations = new ArrayList<>(); 
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
/*     */         table = this.attributeNameTable;
/*     */       } else {
/*     */         table = this.elementNameTable;
/*     */       } 
/*     */       String[] name = table.get(qName);
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
/*     */         } else if (this.defaultNS == null) {
/*     */           name[0] = "";
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
/*     */           uri = this.prefixTable.get(prefix);
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
/*     */       return this.prefixTable.get(prefix);
/*     */     }
/*     */     
/*     */     String getPrefix(String uri) {
/*     */       if (this.uriTable == null)
/*     */         return null; 
/*     */       return this.uriTable.get(uri);
/*     */     }
/*     */     
/*     */     Enumeration getDeclaredPrefixes() {
/*     */       if (this.declarations == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return Collections.enumeration(this.declarations);
/*     */     }
/*     */     
/*     */     Enumeration getPrefixes() {
/*     */       if (this.prefixTable == null)
/*     */         return NamespaceSupport.EMPTY_ENUMERATION; 
/*     */       return Collections.enumeration(this.prefixTable.keySet());
/*     */     }
/*     */     
/*     */     private void copyTables() {
/*     */       if (this.prefixTable != null) {
/*     */         this.prefixTable = new HashMap<>(this.prefixTable);
/*     */       } else {
/*     */         this.prefixTable = new HashMap<>();
/*     */       } 
/*     */       if (this.uriTable != null) {
/*     */         this.uriTable = new HashMap<>(this.uriTable);
/*     */       } else {
/*     */         this.uriTable = new HashMap<>();
/*     */       } 
/*     */       this.elementNameTable = (Map)new HashMap<>();
/*     */       this.attributeNameTable = (Map)new HashMap<>();
/*     */       this.declSeen = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/helpers/NamespaceSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */