/*     */ package com.sun.xml.internal.ws.encoding;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ParameterList
/*     */ {
/*     */   private final Map<String, String> list;
/*     */   
/*     */   ParameterList(String s) {
/*  55 */     HeaderTokenizer h = new HeaderTokenizer(s, "()<>@,;:\\\"\t []/?=");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     this.list = new HashMap<>();
/*     */     while (true) {
/*  62 */       HeaderTokenizer.Token tk = h.next();
/*  63 */       int type = tk.getType();
/*     */       
/*  65 */       if (type == -4) {
/*     */         return;
/*     */       }
/*  68 */       if ((char)type == ';') {
/*     */         
/*  70 */         tk = h.next();
/*     */         
/*  72 */         if (tk.getType() == -4) {
/*     */           return;
/*     */         }
/*  75 */         if (tk.getType() != -1)
/*  76 */           throw new WebServiceException(); 
/*  77 */         String name = tk.getValue().toLowerCase();
/*     */ 
/*     */         
/*  80 */         tk = h.next();
/*  81 */         if ((char)tk.getType() != '=') {
/*  82 */           throw new WebServiceException();
/*     */         }
/*     */         
/*  85 */         tk = h.next();
/*  86 */         type = tk.getType();
/*     */         
/*  88 */         if (type != -1 && type != -2)
/*     */         {
/*  90 */           throw new WebServiceException();
/*     */         }
/*  92 */         this.list.put(name, tk.getValue()); continue;
/*     */       }  break;
/*  94 */     }  throw new WebServiceException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size() {
/* 104 */     return this.list.size();
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
/*     */   String get(String name) {
/* 117 */     return this.list.get(name.trim().toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Iterator<String> getNames() {
/* 128 */     return this.list.keySet().iterator();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/encoding/ParameterList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */