/*    */ package com.sun.xml.internal.ws.assembler;
/*    */ 
/*    */ import com.sun.istack.internal.logging.Logger;
/*    */ import com.sun.xml.internal.ws.api.pipe.Pipe;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.helper.PipeAdapter;
/*    */ import com.sun.xml.internal.ws.assembler.dev.TubelineAssemblyContext;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class TubelineAssemblyContextImpl
/*    */   implements TubelineAssemblyContext
/*    */ {
/* 46 */   private static final Logger LOGGER = Logger.getLogger(TubelineAssemblyContextImpl.class);
/*    */   
/*    */   private Tube head;
/*    */   private Pipe adaptedHead;
/* 50 */   private List<Tube> tubes = new LinkedList<>();
/*    */ 
/*    */   
/*    */   public Tube getTubelineHead() {
/* 54 */     return this.head;
/*    */   }
/*    */ 
/*    */   
/*    */   public Pipe getAdaptedTubelineHead() {
/* 59 */     if (this.adaptedHead == null) {
/* 60 */       this.adaptedHead = PipeAdapter.adapt(this.head);
/*    */     }
/* 62 */     return this.adaptedHead;
/*    */   }
/*    */   
/*    */   boolean setTubelineHead(Tube newHead) {
/* 66 */     if (newHead == this.head || newHead == this.adaptedHead) {
/* 67 */       return false;
/*    */     }
/*    */     
/* 70 */     this.head = newHead;
/* 71 */     this.tubes.add(this.head);
/* 72 */     this.adaptedHead = null;
/*    */     
/* 74 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 75 */       LOGGER.finer(MessageFormat.format("Added '{0}' tube instance to the tubeline.", new Object[] { (newHead == null) ? null : newHead.getClass().getName() }));
/*    */     }
/*    */     
/* 78 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> T getImplementation(Class<T> type) {
/* 83 */     for (Tube tube : this.tubes) {
/* 84 */       if (type.isInstance(tube)) {
/* 85 */         return type.cast(tube);
/*    */       }
/*    */     } 
/* 88 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/TubelineAssemblyContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */