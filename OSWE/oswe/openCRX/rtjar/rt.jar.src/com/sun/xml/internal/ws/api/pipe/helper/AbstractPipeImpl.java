/*    */ package com.sun.xml.internal.ws.api.pipe.helper;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.pipe.Pipe;
/*    */ import com.sun.xml.internal.ws.api.pipe.PipeCloner;
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
/*    */ public abstract class AbstractPipeImpl
/*    */   implements Pipe
/*    */ {
/*    */   protected AbstractPipeImpl() {}
/*    */   
/*    */   protected AbstractPipeImpl(Pipe that, PipeCloner cloner) {
/* 57 */     cloner.add(that, this);
/*    */   }
/*    */   
/*    */   public void preDestroy() {}
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/helper/AbstractPipeImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */