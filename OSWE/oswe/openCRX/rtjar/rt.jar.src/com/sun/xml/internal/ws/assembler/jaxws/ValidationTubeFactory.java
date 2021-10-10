/*    */ package com.sun.xml.internal.ws.assembler.jaxws;
/*    */ 
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.assembler.dev.ClientTubelineAssemblyContext;
/*    */ import com.sun.xml.internal.ws.assembler.dev.ServerTubelineAssemblyContext;
/*    */ import com.sun.xml.internal.ws.assembler.dev.TubeFactory;
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public final class ValidationTubeFactory
/*    */   implements TubeFactory
/*    */ {
/*    */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 44 */     return context.getWrappedContext().createValidationTube(context.getTubelineHead());
/*    */   }
/*    */ 
/*    */   
/*    */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 49 */     return context.getWrappedContext().createValidationTube(context.getTubelineHead());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/assembler/jaxws/ValidationTubeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */