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
/*    */ public abstract class TubeCloner
/*    */ {
/*    */   public final Map<Object, Object> master2copy;
/*    */   
/*    */   public static Tube clone(Tube p) {
/* 60 */     return (new PipeClonerImpl()).copy(p);
/*    */   }
/*    */ 
/*    */   
/*    */   TubeCloner(Map<Object, Object> master2copy) {
/* 65 */     this.master2copy = master2copy;
/*    */   }
/*    */   
/*    */   public abstract <T extends Tube> T copy(T paramT);
/*    */   
/*    */   public abstract void add(Tube paramTube1, Tube paramTube2);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/TubeCloner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */