/*     */ package com.sun.xml.internal.ws.api.server;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.Component;
/*     */ import com.sun.xml.internal.ws.api.config.management.Reconfigurable;
/*     */ import com.sun.xml.internal.ws.api.pipe.Codec;
/*     */ import com.sun.xml.internal.ws.util.Pool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Adapter<TK extends Adapter.Toolkit>
/*     */   implements Reconfigurable, Component
/*     */ {
/*     */   protected final WSEndpoint<?> endpoint;
/*     */   
/*     */   public class Toolkit
/*     */   {
/*  93 */     public final Codec codec = Adapter.this.endpoint.createCodec();
/*  94 */     public final WSEndpoint.PipeHead head = Adapter.this.endpoint.createPipeHead();
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
/* 105 */   protected volatile Pool<TK> pool = new Pool<TK>() {
/*     */       protected TK create() {
/* 107 */         return Adapter.this.createToolkit();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Adapter(WSEndpoint<?> endpoint) {
/* 116 */     assert endpoint != null;
/* 117 */     this.endpoint = endpoint;
/*     */     
/* 119 */     endpoint.getComponents().add(getEndpointComponent());
/*     */   }
/*     */   
/*     */   protected Component getEndpointComponent() {
/* 123 */     return new Component() {
/*     */         public <S> S getSPI(Class<S> spiType) {
/* 125 */           if (spiType.isAssignableFrom(Reconfigurable.class)) {
/* 126 */             return spiType.cast(Adapter.this);
/*     */           }
/* 128 */           return null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reconfigure() {
/* 137 */     this.pool = new Pool<TK>() {
/*     */         protected TK create() {
/* 139 */           return Adapter.this.createToolkit();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/* 145 */     if (spiType.isAssignableFrom(Reconfigurable.class)) {
/* 146 */       return spiType.cast(this);
/*     */     }
/* 148 */     if (this.endpoint != null) {
/* 149 */       return this.endpoint.getSPI(spiType);
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSEndpoint<?> getEndpoint() {
/* 161 */     return this.endpoint;
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
/*     */   protected Pool<TK> getPool() {
/* 175 */     return this.pool;
/*     */   }
/*     */   
/*     */   protected abstract TK createToolkit();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/server/Adapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */