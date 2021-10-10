/*     */ package com.sun.xml.internal.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NamespaceContexHelper
/*     */   implements NamespaceContextEx
/*     */ {
/*  63 */   private static int DEFAULT_SIZE = 8;
/*     */ 
/*     */   
/*  66 */   private String[] prefixes = new String[DEFAULT_SIZE];
/*     */   
/*  68 */   private String[] namespaceURIs = new String[DEFAULT_SIZE];
/*     */ 
/*     */   
/*     */   private int namespacePosition;
/*     */   
/*  73 */   private int[] contexts = new int[DEFAULT_SIZE];
/*     */ 
/*     */ 
/*     */   
/*     */   private int contextPosition;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContexHelper() {
/*  83 */     this.prefixes[0] = "xml";
/*  84 */     this.namespaceURIs[0] = "http://www.w3.org/XML/1998/namespace";
/*  85 */     this.prefixes[1] = "xmlns";
/*  86 */     this.namespaceURIs[1] = "http://www.w3.org/2000/xmlns/";
/*     */     
/*  88 */     this.namespacePosition = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  95 */     if (prefix == null) throw new IllegalArgumentException();
/*     */     
/*  97 */     prefix = prefix.intern();
/*     */     
/*  99 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 100 */       String declaredPrefix = this.prefixes[i];
/* 101 */       if (declaredPrefix == prefix) {
/* 102 */         return this.namespaceURIs[i];
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return "";
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) {
/* 110 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */     
/* 112 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 113 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 114 */       if (declaredNamespaceURI == namespaceURI || declaredNamespaceURI.equals(namespaceURI)) {
/* 115 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 118 */         for (; ++i < this.namespacePosition; i++) {
/* 119 */           if (declaredPrefix == this.prefixes[i])
/* 120 */             return null; 
/*     */         } 
/* 122 */         return declaredPrefix;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public Iterator getPrefixes(String namespaceURI) {
/* 130 */     if (namespaceURI == null) throw new IllegalArgumentException();
/*     */     
/* 132 */     List<String> l = new ArrayList<>();
/*     */     
/* 134 */     for (int i = this.namespacePosition - 1; i >= 0; i--) {
/* 135 */       String declaredNamespaceURI = this.namespaceURIs[i];
/* 136 */       if (declaredNamespaceURI == namespaceURI || declaredNamespaceURI.equals(namespaceURI)) {
/* 137 */         String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */         
/* 140 */         int j = i + 1; while (true) { if (j < this.namespacePosition) {
/* 141 */             if (declaredPrefix == this.prefixes[j])
/*     */               break;  j++; continue;
/*     */           } 
/* 144 */           l.add(declaredPrefix); break; }
/*     */       
/*     */       } 
/*     */     } 
/* 148 */     return l.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<NamespaceContextEx.Binding> iterator() {
/* 154 */     if (this.namespacePosition == 2) {
/* 155 */       return Collections.EMPTY_LIST.iterator();
/*     */     }
/* 157 */     List<NamespaceContextEx.Binding> namespaces = new ArrayList<>(this.namespacePosition);
/*     */ 
/*     */     
/* 160 */     for (int i = this.namespacePosition - 1; i >= 2; i--) {
/* 161 */       String declaredPrefix = this.prefixes[i];
/*     */ 
/*     */       
/* 164 */       for (int j = i + 1; j < this.namespacePosition && 
/* 165 */         declaredPrefix != this.prefixes[j]; j++)
/*     */       {
/*     */         
/* 168 */         namespaces.add(new NamespaceBindingImpl(i));
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return namespaces.iterator();
/*     */   }
/*     */   
/*     */   private final class NamespaceBindingImpl implements NamespaceContextEx.Binding {
/*     */     int index;
/*     */     
/*     */     NamespaceBindingImpl(int index) {
/* 179 */       this.index = index;
/*     */     }
/*     */     
/*     */     public String getPrefix() {
/* 183 */       return NamespaceContexHelper.this.prefixes[this.index];
/*     */     }
/*     */     
/*     */     public String getNamespaceURI() {
/* 187 */       return NamespaceContexHelper.this.namespaceURIs[this.index];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareDefaultNamespace(String namespaceURI) {
/* 197 */     declareNamespace("", namespaceURI);
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
/*     */   public void declareNamespace(String prefix, String namespaceURI) {
/* 219 */     if (prefix == null) throw new IllegalArgumentException();
/*     */     
/* 221 */     prefix = prefix.intern();
/*     */     
/* 223 */     if (prefix == "xml" || prefix == "xmlns") {
/*     */       return;
/*     */     }
/*     */     
/* 227 */     if (namespaceURI != null) {
/* 228 */       namespaceURI = namespaceURI.intern();
/*     */     }
/* 230 */     if (this.namespacePosition == this.namespaceURIs.length) {
/* 231 */       resizeNamespaces();
/*     */     }
/*     */     
/* 234 */     this.prefixes[this.namespacePosition] = prefix;
/* 235 */     this.namespaceURIs[this.namespacePosition++] = namespaceURI;
/*     */   }
/*     */   
/*     */   private void resizeNamespaces() {
/* 239 */     int newLength = this.namespaceURIs.length * 3 / 2 + 1;
/*     */     
/* 241 */     String[] newPrefixes = new String[newLength];
/* 242 */     System.arraycopy(this.prefixes, 0, newPrefixes, 0, this.prefixes.length);
/* 243 */     this.prefixes = newPrefixes;
/*     */     
/* 245 */     String[] newNamespaceURIs = new String[newLength];
/* 246 */     System.arraycopy(this.namespaceURIs, 0, newNamespaceURIs, 0, this.namespaceURIs.length);
/* 247 */     this.namespaceURIs = newNamespaceURIs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pushContext() {
/* 254 */     if (this.contextPosition == this.contexts.length) {
/* 255 */       resizeContexts();
/*     */     }
/* 257 */     this.contexts[this.contextPosition++] = this.namespacePosition;
/*     */   }
/*     */   
/*     */   private void resizeContexts() {
/* 261 */     int[] newContexts = new int[this.contexts.length * 3 / 2 + 1];
/* 262 */     System.arraycopy(this.contexts, 0, newContexts, 0, this.contexts.length);
/* 263 */     this.contexts = newContexts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void popContext() {
/* 273 */     if (this.contextPosition > 0) {
/* 274 */       this.namespacePosition = this.contexts[--this.contextPosition];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetContexts() {
/* 284 */     this.namespacePosition = 2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/stream/buffer/stax/NamespaceContexHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */