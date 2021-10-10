/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Hdr
/*     */   implements Header
/*     */ {
/*     */   String name;
/*     */   String line;
/*     */   
/*     */   Hdr(String l) {
/* 187 */     int i = l.indexOf(':');
/* 188 */     if (i < 0) {
/*     */       
/* 190 */       this.name = l.trim();
/*     */     } else {
/* 192 */       this.name = l.substring(0, i).trim();
/*     */     } 
/* 194 */     this.line = l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Hdr(String n, String v) {
/* 201 */     this.name = n;
/* 202 */     this.line = n + ": " + v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 210 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 218 */     int j, i = this.line.indexOf(':');
/* 219 */     if (i < 0) {
/* 220 */       return this.line;
/*     */     }
/*     */ 
/*     */     
/* 224 */     if (this.name.equalsIgnoreCase("Content-Description")) {
/*     */ 
/*     */       
/* 227 */       for (j = i + 1; j < this.line.length(); j++) {
/* 228 */         char c = this.line.charAt(j);
/* 229 */         if (c != '\t' && c != '\r' && c != '\n') {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 235 */       for (j = i + 1; j < this.line.length(); j++) {
/* 236 */         char c = this.line.charAt(j);
/* 237 */         if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 242 */     return this.line.substring(j);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/Hdr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */