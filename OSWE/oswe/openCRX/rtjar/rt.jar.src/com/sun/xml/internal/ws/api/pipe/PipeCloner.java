/*    */ package com.sun.xml.internal.ws.api.pipe;
/*    */ 
/*    */ import java.util.Map;
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
/*    */ public abstract class PipeCloner
/*    */   extends TubeCloner
/*    */ {
/*    */   public static Pipe clone(Pipe p) {
/* 47 */     return (new PipeClonerImpl()).copy(p);
/*    */   }
/*    */ 
/*    */   
/*    */   PipeCloner(Map<Object, Object> master2copy) {
/* 52 */     super(master2copy);
/*    */   }
/*    */   
/*    */   public abstract <T extends Pipe> T copy(T paramT);
/*    */   
/*    */   public abstract void add(Pipe paramPipe1, Pipe paramPipe2);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/PipeCloner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */