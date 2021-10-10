/*     */ package com.sun.xml.internal.ws.server.sei;
/*     */ 
/*     */ import com.sun.xml.internal.ws.model.ParameterImpl;
/*     */ import javax.xml.ws.Holder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EndpointValueSetter
/*     */ {
/*     */   private EndpointValueSetter() {}
/*     */   
/*  68 */   private static final EndpointValueSetter[] POOL = new EndpointValueSetter[16];
/*     */   
/*     */   static {
/*  71 */     for (int i = 0; i < POOL.length; i++) {
/*  72 */       POOL[i] = new Param(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EndpointValueSetter get(ParameterImpl p) {
/*  79 */     int idx = p.getIndex();
/*  80 */     if (p.isIN()) {
/*  81 */       if (idx < POOL.length) {
/*  82 */         return POOL[idx];
/*     */       }
/*  84 */       return new Param(idx);
/*     */     } 
/*     */     
/*  87 */     return new HolderParam(idx);
/*     */   }
/*     */   
/*     */   abstract void put(Object paramObject, Object[] paramArrayOfObject);
/*     */   
/*     */   static class Param
/*     */     extends EndpointValueSetter
/*     */   {
/*     */     protected final int idx;
/*     */     
/*     */     public Param(int idx) {
/*  98 */       this.idx = idx;
/*     */     }
/*     */     
/*     */     void put(Object obj, Object[] args) {
/* 102 */       if (obj != null)
/* 103 */         args[this.idx] = obj; 
/*     */     }
/*     */   }
/*     */   
/*     */   static final class HolderParam
/*     */     extends Param
/*     */   {
/*     */     public HolderParam(int idx) {
/* 111 */       super(idx);
/*     */     }
/*     */ 
/*     */     
/*     */     void put(Object obj, Object[] args) {
/* 116 */       Holder holder = new Holder();
/* 117 */       if (obj != null) {
/* 118 */         holder.value = obj;
/*     */       }
/* 120 */       args[this.idx] = holder;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/sei/EndpointValueSetter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */