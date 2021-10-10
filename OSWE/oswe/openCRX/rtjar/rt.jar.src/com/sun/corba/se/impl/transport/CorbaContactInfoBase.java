/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*     */ import com.sun.corba.se.impl.encoding.CDROutputObject;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.CorbaMessageMediatorImpl;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
/*     */ import com.sun.corba.se.pept.broker.Broker;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.protocol.ClientRequestDispatcher;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.pept.transport.Connection;
/*     */ import com.sun.corba.se.pept.transport.ContactInfo;
/*     */ import com.sun.corba.se.pept.transport.ContactInfoList;
/*     */ import com.sun.corba.se.pept.transport.OutboundConnectionCache;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.ior.iiop.IIOPProfile;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*     */ import com.sun.corba.se.spi.transport.CorbaContactInfoList;
/*     */ import java.nio.ByteBuffer;
/*     */ import sun.corba.OutputStreamFactory;
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
/*     */ public abstract class CorbaContactInfoBase
/*     */   implements CorbaContactInfo
/*     */ {
/*     */   protected ORB orb;
/*     */   protected CorbaContactInfoList contactInfoList;
/*     */   protected IOR effectiveTargetIOR;
/*     */   protected short addressingDisposition;
/*     */   protected OutboundConnectionCache connectionCache;
/*     */   
/*     */   public Broker getBroker() {
/*  90 */     return (Broker)this.orb;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContactInfoList getContactInfoList() {
/*  95 */     return (ContactInfoList)this.contactInfoList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientRequestDispatcher getClientRequestDispatcher() {
/* 101 */     int i = getEffectiveProfile().getObjectKeyTemplate().getSubcontractId();
/* 102 */     RequestDispatcherRegistry requestDispatcherRegistry = this.orb.getRequestDispatcherRegistry();
/* 103 */     return requestDispatcherRegistry.getClientRequestDispatcher(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionCache(OutboundConnectionCache paramOutboundConnectionCache) {
/* 110 */     this.connectionCache = paramOutboundConnectionCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public OutboundConnectionCache getConnectionCache() {
/* 115 */     return this.connectionCache;
/*     */   }
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
/*     */   public MessageMediator createMessageMediator(Broker paramBroker, ContactInfo paramContactInfo, Connection paramConnection, String paramString, boolean paramBoolean) {
/* 130 */     return (MessageMediator)new CorbaMessageMediatorImpl((ORB)paramBroker, paramContactInfo, paramConnection, 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         GIOPVersion.chooseRequestVersion((ORB)paramBroker, this.effectiveTargetIOR), this.effectiveTargetIOR, ((CorbaConnection)paramConnection)
/*     */ 
/*     */         
/* 138 */         .getNextRequestId(), 
/* 139 */         getAddressingDisposition(), paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageMediator createMessageMediator(Broker paramBroker, Connection paramConnection) {
/* 149 */     ORB oRB = (ORB)paramBroker;
/* 150 */     CorbaConnection corbaConnection = (CorbaConnection)paramConnection;
/*     */     
/* 152 */     if (oRB.transportDebugFlag) {
/* 153 */       if (corbaConnection.shouldReadGiopHeaderOnly()) {
/* 154 */         dprint(".createMessageMediator: waiting for message header on connection: " + corbaConnection);
/*     */       }
/*     */       else {
/*     */         
/* 158 */         dprint(".createMessageMediator: waiting for message on connection: " + corbaConnection);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 164 */     MessageBase messageBase = null;
/*     */     
/* 166 */     if (corbaConnection.shouldReadGiopHeaderOnly()) {
/*     */       
/* 168 */       messageBase = MessageBase.readGIOPHeader(oRB, corbaConnection);
/*     */     } else {
/*     */       
/* 171 */       messageBase = MessageBase.readGIOPMessage(oRB, corbaConnection);
/*     */     } 
/*     */     
/* 174 */     ByteBuffer byteBuffer = messageBase.getByteBuffer();
/* 175 */     messageBase.setByteBuffer(null);
/* 176 */     return (MessageMediator)new CorbaMessageMediatorImpl(oRB, corbaConnection, (Message)messageBase, byteBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageMediator finishCreatingMessageMediator(Broker paramBroker, Connection paramConnection, MessageMediator paramMessageMediator) {
/* 186 */     ORB oRB = (ORB)paramBroker;
/* 187 */     CorbaConnection corbaConnection = (CorbaConnection)paramConnection;
/* 188 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */ 
/*     */     
/* 191 */     if (oRB.transportDebugFlag) {
/* 192 */       dprint(".finishCreatingMessageMediator: waiting for message body on connection: " + corbaConnection);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 197 */     Message message = corbaMessageMediator.getDispatchHeader();
/* 198 */     message.setByteBuffer(corbaMessageMediator.getDispatchBuffer());
/*     */ 
/*     */     
/* 201 */     message = MessageBase.readGIOPBody(oRB, corbaConnection, message);
/*     */     
/* 203 */     ByteBuffer byteBuffer = message.getByteBuffer();
/* 204 */     message.setByteBuffer(null);
/* 205 */     corbaMessageMediator.setDispatchHeader(message);
/* 206 */     corbaMessageMediator.setDispatchBuffer(byteBuffer);
/*     */     
/* 208 */     return (MessageMediator)corbaMessageMediator;
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputObject createOutputObject(MessageMediator paramMessageMediator) {
/* 213 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */ 
/*     */ 
/*     */     
/* 217 */     CDROutputObject cDROutputObject = OutputStreamFactory.newCDROutputObject(this.orb, paramMessageMediator, (Message)corbaMessageMediator
/* 218 */         .getRequestHeader(), corbaMessageMediator
/* 219 */         .getStreamFormatVersion());
/*     */     
/* 221 */     paramMessageMediator.setOutputObject((OutputObject)cDROutputObject);
/* 222 */     return (OutputObject)cDROutputObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputObject createInputObject(Broker paramBroker, MessageMediator paramMessageMediator) {
/* 229 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     
/* 231 */     return (InputObject)new CDRInputObject((ORB)paramBroker, (CorbaConnection)paramMessageMediator
/* 232 */         .getConnection(), corbaMessageMediator
/* 233 */         .getDispatchBuffer(), corbaMessageMediator
/* 234 */         .getDispatchHeader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getAddressingDisposition() {
/* 244 */     return this.addressingDisposition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAddressingDisposition(short paramShort) {
/* 249 */     this.addressingDisposition = paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IOR getTargetIOR() {
/* 255 */     return this.contactInfoList.getTargetIOR();
/*     */   }
/*     */ 
/*     */   
/*     */   public IOR getEffectiveTargetIOR() {
/* 260 */     return this.effectiveTargetIOR;
/*     */   }
/*     */ 
/*     */   
/*     */   public IIOPProfile getEffectiveProfile() {
/* 265 */     return this.effectiveTargetIOR.getProfile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 275 */     return "CorbaContactInfoBase[]";
/*     */   }
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
/*     */   protected void dprint(String paramString) {
/* 288 */     ORBUtility.dprint("CorbaContactInfoBase", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaContactInfoBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */