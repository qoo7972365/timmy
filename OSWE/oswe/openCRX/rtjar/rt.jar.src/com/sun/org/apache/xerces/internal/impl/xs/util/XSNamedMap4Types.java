/*     */ package com.sun.org.apache.xerces.internal.impl.xs.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.SymbolHash;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSObject;
/*     */ import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XSNamedMap4Types
/*     */   extends XSNamedMapImpl
/*     */ {
/*     */   private final short fType;
/*     */   
/*     */   public XSNamedMap4Types(String namespace, SymbolHash map, short type) {
/*  50 */     super(namespace, map);
/*  51 */     this.fType = type;
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
/*     */   public XSNamedMap4Types(String[] namespaces, SymbolHash[] maps, int num, short type) {
/*  63 */     super(namespaces, maps, num);
/*  64 */     this.fType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getLength() {
/*  73 */     if (this.fLength == -1) {
/*     */       
/*  75 */       int length = 0;
/*  76 */       for (int i = 0; i < this.fNSNum; i++) {
/*  77 */         length += this.fMaps[i].getLength();
/*     */       }
/*     */       
/*  80 */       int pos = 0;
/*  81 */       XSObject[] array = new XSObject[length];
/*  82 */       for (int j = 0; j < this.fNSNum; j++) {
/*  83 */         pos += this.fMaps[j].getValues((Object[])array, pos);
/*     */       }
/*     */ 
/*     */       
/*  87 */       this.fLength = 0;
/*  88 */       this.fArray = new XSObject[length];
/*     */       
/*  90 */       for (int k = 0; k < length; k++) {
/*  91 */         XSTypeDefinition type = (XSTypeDefinition)array[k];
/*  92 */         if (type.getTypeCategory() == this.fType) {
/*  93 */           this.fArray[this.fLength++] = type;
/*     */         }
/*     */       } 
/*     */     } 
/*  97 */     return this.fLength;
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
/*     */   public XSObject itemByName(String namespace, String localName) {
/* 111 */     for (int i = 0; i < this.fNSNum; i++) {
/* 112 */       if (isEqual(namespace, this.fNamespaces[i])) {
/* 113 */         XSTypeDefinition type = (XSTypeDefinition)this.fMaps[i].get(localName);
/*     */         
/* 115 */         if (type != null && type.getTypeCategory() == this.fType) {
/* 116 */           return type;
/*     */         }
/* 118 */         return null;
/*     */       } 
/*     */     } 
/* 121 */     return null;
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
/*     */   public synchronized XSObject item(int index) {
/* 135 */     if (this.fArray == null) {
/* 136 */       getLength();
/*     */     }
/* 138 */     if (index < 0 || index >= this.fLength) {
/* 139 */       return null;
/*     */     }
/* 141 */     return this.fArray[index];
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/impl/xs/util/XSNamedMap4Types.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */