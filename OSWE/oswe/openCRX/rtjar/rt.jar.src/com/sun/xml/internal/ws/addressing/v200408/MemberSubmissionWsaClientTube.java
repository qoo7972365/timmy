/*    */ package com.sun.xml.internal.ws.addressing.v200408;
/*    */ 
/*    */ import com.sun.xml.internal.ws.addressing.WsaClientTube;
/*    */ import com.sun.xml.internal.ws.addressing.model.MissingAddressingHeaderException;
/*    */ import com.sun.xml.internal.ws.api.WSBinding;
/*    */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*    */ import com.sun.xml.internal.ws.api.message.Packet;
/*    */ import com.sun.xml.internal.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.internal.ws.api.pipe.Tube;
/*    */ import com.sun.xml.internal.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.internal.ws.api.pipe.helper.AbstractTubeImpl;
/*    */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
/*    */ import com.sun.xml.internal.ws.developer.MemberSubmissionAddressingFeature;
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
/*    */ public class MemberSubmissionWsaClientTube
/*    */   extends WsaClientTube
/*    */ {
/*    */   private final MemberSubmissionAddressing.Validation validation;
/*    */   
/*    */   public MemberSubmissionWsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/* 46 */     super(wsdlPort, binding, next);
/* 47 */     this.validation = ((MemberSubmissionAddressingFeature)binding.getFeature(MemberSubmissionAddressingFeature.class)).getValidation();
/*    */   }
/*    */ 
/*    */   
/*    */   public MemberSubmissionWsaClientTube(MemberSubmissionWsaClientTube that, TubeCloner cloner) {
/* 52 */     super(that, cloner);
/* 53 */     this.validation = that.validation;
/*    */   }
/*    */   
/*    */   public MemberSubmissionWsaClientTube copy(TubeCloner cloner) {
/* 57 */     return new MemberSubmissionWsaClientTube(this, cloner);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageID, boolean foundRelatesTo) {
/* 63 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageID, foundRelatesTo);
/*    */ 
/*    */     
/* 66 */     if (!foundTo) {
/* 67 */       throw new MissingAddressingHeaderException(this.addressingVersion.toTag, packet);
/*    */     }
/*    */     
/* 70 */     if (!this.validation.equals(MemberSubmissionAddressing.Validation.LAX))
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 75 */       if (this.expectReply && packet.getMessage() != null && !foundRelatesTo) {
/* 76 */         String action = AddressingUtils.getAction(packet.getMessage().getHeaders(), this.addressingVersion, this.soapVersion);
/*    */ 
/*    */         
/* 79 */         if (!packet.getMessage().isFault() || !action.equals(this.addressingVersion.getDefaultFaultAction()))
/* 80 */           throw new MissingAddressingHeaderException(this.addressingVersion.relatesToTag, packet); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/v200408/MemberSubmissionWsaClientTube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */