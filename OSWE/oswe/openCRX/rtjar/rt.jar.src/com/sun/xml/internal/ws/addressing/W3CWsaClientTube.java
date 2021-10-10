/*    */ package com.sun.xml.internal.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
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
/*    */ public class W3CWsaClientTube
/*    */   extends WsaClientTube
/*    */ {
/*    */   public W3CWsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/* 41 */     super(wsdlPort, binding, next);
/*    */   }
/*    */   
/*    */   public W3CWsaClientTube(WsaClientTube that, TubeCloner cloner) {
/* 45 */     super(that, cloner);
/*    */   }
/*    */ 
/*    */   
/*    */   public W3CWsaClientTube copy(TubeCloner cloner) {
/* 50 */     return new W3CWsaClientTube(this, cloner);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageID, boolean foundRelatesTo) {
/* 56 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageID, foundRelatesTo);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     if (this.expectReply && packet.getMessage() != null && !foundRelatesTo) {
/* 62 */       String action = AddressingUtils.getAction(packet.getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*    */ 
/*    */       
/* 65 */       if (!packet.getMessage().isFault() || !action.equals(this.addressingVersion.getDefaultFaultAction()))
/* 66 */         throw new MissingAddressingHeaderException(this.addressingVersion.relatesToTag, packet); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/W3CWsaClientTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */