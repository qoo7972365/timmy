/*     */ package com.sun.org.apache.xml.internal.security.c14n.implementations;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NameSpaceSymbTable
/*     */ {
/*     */   private static final String XMLNS = "xmlns";
/*  43 */   private static final SymbMap initialMap = new SymbMap();
/*     */   
/*     */   static {
/*  46 */     NameSpaceSymbEntry nameSpaceSymbEntry = new NameSpaceSymbEntry("", null, true, "xmlns");
/*  47 */     nameSpaceSymbEntry.lastrendered = "";
/*  48 */     initialMap.put("xmlns", nameSpaceSymbEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private SymbMap symb;
/*     */ 
/*     */   
/*     */   private List<SymbMap> level;
/*     */   
/*     */   private boolean cloned = true;
/*     */ 
/*     */   
/*     */   public NameSpaceSymbTable() {
/*  62 */     this.level = new ArrayList<>();
/*     */     
/*  64 */     this.symb = (SymbMap)initialMap.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getUnrenderedNodes(Collection<Attr> paramCollection) {
/*  73 */     Iterator<NameSpaceSymbEntry> iterator = this.symb.entrySet().iterator();
/*  74 */     while (iterator.hasNext()) {
/*  75 */       NameSpaceSymbEntry nameSpaceSymbEntry = iterator.next();
/*     */       
/*  77 */       if (!nameSpaceSymbEntry.rendered && nameSpaceSymbEntry.n != null) {
/*  78 */         nameSpaceSymbEntry = (NameSpaceSymbEntry)nameSpaceSymbEntry.clone();
/*  79 */         needsClone();
/*  80 */         this.symb.put(nameSpaceSymbEntry.prefix, nameSpaceSymbEntry);
/*  81 */         nameSpaceSymbEntry.lastrendered = nameSpaceSymbEntry.uri;
/*  82 */         nameSpaceSymbEntry.rendered = true;
/*     */         
/*  84 */         paramCollection.add(nameSpaceSymbEntry.n);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outputNodePush() {
/*  94 */     push();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outputNodePop() {
/* 101 */     pop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void push() {
/* 110 */     this.level.add(null);
/* 111 */     this.cloned = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pop() {
/* 119 */     int i = this.level.size() - 1;
/* 120 */     SymbMap symbMap = (SymbMap)this.level.remove(i);
/* 121 */     if (symbMap != null) {
/* 122 */       this.symb = symbMap;
/* 123 */       if (i == 0) {
/* 124 */         this.cloned = false;
/*     */       } else {
/* 126 */         this.cloned = (this.level.get(i - 1) != this.symb);
/*     */       } 
/*     */     } else {
/* 129 */       this.cloned = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   final void needsClone() {
/* 134 */     if (!this.cloned) {
/* 135 */       this.level.set(this.level.size() - 1, this.symb);
/* 136 */       this.symb = (SymbMap)this.symb.clone();
/* 137 */       this.cloned = true;
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
/*     */   public Attr getMapping(String paramString) {
/* 149 */     NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
/* 150 */     if (nameSpaceSymbEntry == null)
/*     */     {
/* 152 */       return null;
/*     */     }
/* 154 */     if (nameSpaceSymbEntry.rendered)
/*     */     {
/* 156 */       return null;
/*     */     }
/*     */     
/* 159 */     nameSpaceSymbEntry = (NameSpaceSymbEntry)nameSpaceSymbEntry.clone();
/* 160 */     needsClone();
/* 161 */     this.symb.put(paramString, nameSpaceSymbEntry);
/* 162 */     nameSpaceSymbEntry.rendered = true;
/* 163 */     nameSpaceSymbEntry.lastrendered = nameSpaceSymbEntry.uri;
/*     */     
/* 165 */     return nameSpaceSymbEntry.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr getMappingWithoutRendered(String paramString) {
/* 175 */     NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
/* 176 */     if (nameSpaceSymbEntry == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     if (nameSpaceSymbEntry.rendered) {
/* 180 */       return null;
/*     */     }
/* 182 */     return nameSpaceSymbEntry.n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addMapping(String paramString1, String paramString2, Attr paramAttr) {
/* 193 */     NameSpaceSymbEntry nameSpaceSymbEntry1 = this.symb.get(paramString1);
/* 194 */     if (nameSpaceSymbEntry1 != null && paramString2.equals(nameSpaceSymbEntry1.uri))
/*     */     {
/* 196 */       return false;
/*     */     }
/*     */     
/* 199 */     NameSpaceSymbEntry nameSpaceSymbEntry2 = new NameSpaceSymbEntry(paramString2, paramAttr, false, paramString1);
/* 200 */     needsClone();
/* 201 */     this.symb.put(paramString1, nameSpaceSymbEntry2);
/* 202 */     if (nameSpaceSymbEntry1 != null) {
/*     */ 
/*     */       
/* 205 */       nameSpaceSymbEntry2.lastrendered = nameSpaceSymbEntry1.lastrendered;
/* 206 */       if (nameSpaceSymbEntry1.lastrendered != null && nameSpaceSymbEntry1.lastrendered.equals(paramString2))
/*     */       {
/* 208 */         nameSpaceSymbEntry2.rendered = true;
/*     */       }
/*     */     } 
/* 211 */     return true;
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
/*     */   public Node addMappingAndRender(String paramString1, String paramString2, Attr paramAttr) {
/* 223 */     NameSpaceSymbEntry nameSpaceSymbEntry1 = this.symb.get(paramString1);
/*     */     
/* 225 */     if (nameSpaceSymbEntry1 != null && paramString2.equals(nameSpaceSymbEntry1.uri)) {
/* 226 */       if (!nameSpaceSymbEntry1.rendered) {
/* 227 */         nameSpaceSymbEntry1 = (NameSpaceSymbEntry)nameSpaceSymbEntry1.clone();
/* 228 */         needsClone();
/* 229 */         this.symb.put(paramString1, nameSpaceSymbEntry1);
/* 230 */         nameSpaceSymbEntry1.lastrendered = paramString2;
/* 231 */         nameSpaceSymbEntry1.rendered = true;
/* 232 */         return nameSpaceSymbEntry1.n;
/*     */       } 
/* 234 */       return null;
/*     */     } 
/*     */     
/* 237 */     NameSpaceSymbEntry nameSpaceSymbEntry2 = new NameSpaceSymbEntry(paramString2, paramAttr, true, paramString1);
/* 238 */     nameSpaceSymbEntry2.lastrendered = paramString2;
/* 239 */     needsClone();
/* 240 */     this.symb.put(paramString1, nameSpaceSymbEntry2);
/* 241 */     if (nameSpaceSymbEntry1 != null && nameSpaceSymbEntry1.lastrendered != null && nameSpaceSymbEntry1.lastrendered.equals(paramString2)) {
/* 242 */       nameSpaceSymbEntry2.rendered = true;
/* 243 */       return null;
/*     */     } 
/* 245 */     return nameSpaceSymbEntry2.n;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 249 */     return this.level.size();
/*     */   }
/*     */   
/*     */   public void removeMapping(String paramString) {
/* 253 */     NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
/*     */     
/* 255 */     if (nameSpaceSymbEntry != null) {
/* 256 */       needsClone();
/* 257 */       this.symb.put(paramString, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeMappingIfNotRender(String paramString) {
/* 262 */     NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
/*     */     
/* 264 */     if (nameSpaceSymbEntry != null && !nameSpaceSymbEntry.rendered) {
/* 265 */       needsClone();
/* 266 */       this.symb.put(paramString, null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean removeMappingIfRender(String paramString) {
/* 271 */     NameSpaceSymbEntry nameSpaceSymbEntry = this.symb.get(paramString);
/*     */     
/* 273 */     if (nameSpaceSymbEntry != null && nameSpaceSymbEntry.rendered) {
/* 274 */       needsClone();
/* 275 */       this.symb.put(paramString, null);
/*     */     } 
/* 277 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/c14n/implementations/NameSpaceSymbTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */