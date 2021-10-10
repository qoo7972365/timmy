/*     */ package com.sun.xml.internal.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.internal.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.internal.api.message.PropertySet.Property;
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.xml.internal.ws.api.SOAPVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.internal.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.internal.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.internal.ws.api.message.Header;
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public class WsaPropertyBag
/*     */   extends BasePropertySet
/*     */ {
/*     */   public static final String WSA_REPLYTO_FROM_REQUEST = "com.sun.xml.internal.ws.addressing.WsaPropertyBag.ReplyToFromRequest";
/*     */   public static final String WSA_FAULTTO_FROM_REQUEST = "com.sun.xml.internal.ws.addressing.WsaPropertyBag.FaultToFromRequest";
/*     */   public static final String WSA_MSGID_FROM_REQUEST = "com.sun.xml.internal.ws.addressing.WsaPropertyBag.MessageIdFromRequest";
/*     */   public static final String WSA_TO = "com.sun.xml.internal.ws.addressing.WsaPropertyBag.To";
/*     */   @NotNull
/*     */   private final AddressingVersion addressingVersion;
/*     */   @NotNull
/*     */   private final SOAPVersion soapVersion;
/*     */   @NotNull
/*     */   private final Packet packet;
/*     */   
/*     */   public WsaPropertyBag(AddressingVersion addressingVersion, SOAPVersion soapVersion, Packet packet) {
/* 163 */     this._replyToFromRequest = null;
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
/* 174 */     this._faultToFromRequest = null;
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
/* 185 */     this._msgIdFromRequest = null; this.addressingVersion = addressingVersion; this.soapVersion = soapVersion; this.packet = packet;
/*     */   }
/*     */   @Property({"com.sun.xml.internal.ws.api.addressing.to"}) public String getTo() throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getHeaders().get(this.addressingVersion.toTag, false); if (h == null) return null;  return h.getStringContent(); }
/*     */   @Property({"com.sun.xml.internal.ws.addressing.WsaPropertyBag.To"}) public WSEndpointReference getToAsReference() throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getHeaders().get(this.addressingVersion.toTag, false); if (h == null) return null;  return new WSEndpointReference(h.getStringContent(), this.addressingVersion); }
/* 189 */   @Property({"com.sun.xml.internal.ws.api.addressing.from"}) public WSEndpointReference getFrom() throws XMLStreamException { return getEPR(this.addressingVersion.fromTag); } @Property({"com.sun.xml.internal.ws.api.addressing.action"}) public String getAction() { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getHeaders().get(this.addressingVersion.actionTag, false); if (h == null) return null;  return h.getStringContent(); } @Property({"com.sun.xml.internal.ws.api.addressing.messageId", "com.sun.xml.internal.ws.addressing.request.messageID"}) public String getMessageID() { if (this.packet.getMessage() == null) return null;  return AddressingUtils.getMessageID(this.packet.getMessage().getHeaders(), this.addressingVersion, this.soapVersion); } private WSEndpointReference getEPR(QName tag) throws XMLStreamException { if (this.packet.getMessage() == null) return null;  Header h = this.packet.getMessage().getHeaders().get(tag, false); if (h == null) return null;  return h.readAsEPR(this.addressingVersion); } protected BasePropertySet.PropertyMap getPropertyMap() { return model; } @Property({"com.sun.xml.internal.ws.addressing.WsaPropertyBag.MessageIdFromRequest"}) public String getMessageIdFromRequest() { return this._msgIdFromRequest; }
/*     */   private static final BasePropertySet.PropertyMap model = parse(WsaPropertyBag.class);
/*     */   private WSEndpointReference _replyToFromRequest;
/*     */   private WSEndpointReference _faultToFromRequest;
/* 193 */   private String _msgIdFromRequest; @Property({"com.sun.xml.internal.ws.addressing.WsaPropertyBag.ReplyToFromRequest"}) public WSEndpointReference getReplyToFromRequest() { return this._replyToFromRequest; } public void setReplyToFromRequest(WSEndpointReference ref) { this._replyToFromRequest = ref; } @Property({"com.sun.xml.internal.ws.addressing.WsaPropertyBag.FaultToFromRequest"}) public WSEndpointReference getFaultToFromRequest() { return this._faultToFromRequest; } public void setFaultToFromRequest(WSEndpointReference ref) { this._faultToFromRequest = ref; } public void setMessageIdFromRequest(String id) { this._msgIdFromRequest = id; }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/addressing/WsaPropertyBag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */