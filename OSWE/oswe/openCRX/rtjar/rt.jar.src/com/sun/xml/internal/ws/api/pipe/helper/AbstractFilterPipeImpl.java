/*     */ package com.sun.xml.internal.ws.api.pipe.helper;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import com.sun.xml.internal.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.internal.ws.api.pipe.PipeCloner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFilterPipeImpl
/*     */   extends AbstractPipeImpl
/*     */ {
/*     */   protected final Pipe next;
/*     */   
/*     */   protected AbstractFilterPipeImpl(Pipe next) {
/* 103 */     this.next = next;
/* 104 */     assert next != null;
/*     */   }
/*     */   
/*     */   protected AbstractFilterPipeImpl(AbstractFilterPipeImpl that, PipeCloner cloner) {
/* 108 */     super(that, cloner);
/* 109 */     this.next = cloner.copy(that.next);
/* 110 */     assert this.next != null;
/*     */   }
/*     */   
/*     */   public Packet process(Packet packet) {
/* 114 */     return this.next.process(packet);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 119 */     this.next.preDestroy();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/helper/AbstractFilterPipeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */