/*    */ package com.sun.xml.internal.ws.dump;
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
/*    */ public final class MessageDumpingTubeFactory
/*    */   implements TubeFactory
/*    */ {
/*    */   public Tube createTube(ClientTubelineAssemblyContext context) throws WebServiceException {
/* 38 */     MessageDumpingFeature messageDumpingFeature = (MessageDumpingFeature)context.getBinding().getFeature(MessageDumpingFeature.class);
/* 39 */     if (messageDumpingFeature != null) {
/* 40 */       return (Tube)new MessageDumpingTube(context.getTubelineHead(), messageDumpingFeature);
/*    */     }
/*    */     
/* 43 */     return context.getTubelineHead();
/*    */   }
/*    */   
/*    */   public Tube createTube(ServerTubelineAssemblyContext context) throws WebServiceException {
/* 47 */     MessageDumpingFeature messageDumpingFeature = (MessageDumpingFeature)context.getEndpoint().getBinding().getFeature(MessageDumpingFeature.class);
/* 48 */     if (messageDumpingFeature != null) {
/* 49 */       return (Tube)new MessageDumpingTube(context.getTubelineHead(), messageDumpingFeature);
/*    */     }
/*    */     
/* 52 */     return context.getTubelineHead();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/dump/MessageDumpingTubeFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */