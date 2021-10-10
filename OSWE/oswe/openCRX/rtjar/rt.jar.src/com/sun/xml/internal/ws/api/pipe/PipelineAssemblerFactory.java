/*    */ package com.sun.xml.internal.ws.api.pipe;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.BindingID;
/*    */ import com.sun.xml.internal.ws.util.ServiceFinder;
/*    */ import com.sun.xml.internal.ws.util.pipe.StandalonePipeAssembler;
/*    */ import java.util.logging.Logger;
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
/*    */ public abstract class PipelineAssemblerFactory
/*    */ {
/*    */   public abstract PipelineAssembler doCreate(BindingID paramBindingID);
/*    */   
/*    */   public static PipelineAssembler create(ClassLoader classLoader, BindingID bindingId) {
/* 82 */     for (PipelineAssemblerFactory factory : ServiceFinder.find(PipelineAssemblerFactory.class, classLoader)) {
/* 83 */       PipelineAssembler assembler = factory.doCreate(bindingId);
/* 84 */       if (assembler != null) {
/* 85 */         logger.fine(factory.getClass() + " successfully created " + assembler);
/* 86 */         return assembler;
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 92 */     return (PipelineAssembler)new StandalonePipeAssembler();
/*    */   }
/*    */   
/* 95 */   private static final Logger logger = Logger.getLogger(PipelineAssemblerFactory.class.getName());
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/PipelineAssemblerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */