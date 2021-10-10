/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NameNode
/*     */ {
/*     */   private String label;
/*  55 */   private Hashtable<String, NameNode> children = null;
/*     */   private boolean isZoneCut = false;
/*  57 */   private int depth = 0;
/*     */   
/*     */   NameNode(String paramString) {
/*  60 */     this.label = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NameNode newNameNode(String paramString) {
/*  69 */     return new NameNode(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getLabel() {
/*  77 */     return this.label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int depth() {
/*  85 */     return this.depth;
/*     */   }
/*     */   
/*     */   boolean isZoneCut() {
/*  89 */     return this.isZoneCut;
/*     */   }
/*     */   
/*     */   void setZoneCut(boolean paramBoolean) {
/*  93 */     this.isZoneCut = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Hashtable<String, NameNode> getChildren() {
/* 101 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NameNode get(String paramString) {
/* 110 */     return (this.children != null) ? this.children
/* 111 */       .get(paramString) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NameNode get(DnsName paramDnsName, int paramInt) {
/* 122 */     NameNode nameNode = this;
/* 123 */     for (int i = paramInt; i < paramDnsName.size() && nameNode != null; i++) {
/* 124 */       nameNode = nameNode.get(paramDnsName.getKey(i));
/*     */     }
/* 126 */     return nameNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   NameNode add(DnsName paramDnsName, int paramInt) {
/* 136 */     NameNode nameNode = this;
/* 137 */     for (int i = paramInt; i < paramDnsName.size(); i++) {
/* 138 */       String str1 = paramDnsName.get(i);
/* 139 */       String str2 = paramDnsName.getKey(i);
/*     */       
/* 141 */       NameNode nameNode1 = null;
/* 142 */       if (nameNode.children == null) {
/* 143 */         nameNode.children = new Hashtable<>();
/*     */       } else {
/* 145 */         nameNode1 = nameNode.children.get(str2);
/*     */       } 
/* 147 */       if (nameNode1 == null) {
/* 148 */         nameNode1 = newNameNode(str1);
/* 149 */         nameNode.depth++;
/* 150 */         nameNode.children.put(str2, nameNode1);
/*     */       } 
/* 152 */       nameNode = nameNode1;
/*     */     } 
/* 154 */     return nameNode;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/NameNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */