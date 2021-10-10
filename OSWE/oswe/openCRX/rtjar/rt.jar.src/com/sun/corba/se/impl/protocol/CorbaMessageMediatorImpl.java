/*      */ package com.sun.corba.se.impl.protocol;
/*      */ 
/*      */ import com.sun.corba.se.impl.corba.RequestImpl;
/*      */ import com.sun.corba.se.impl.encoding.BufferManagerReadStream;
/*      */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*      */ import com.sun.corba.se.impl.encoding.CDROutputObject;
/*      */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*      */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*      */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*      */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.AddressingDispositionHelper;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_1;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.FragmentMessage_1_2;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_0;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_1;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyMessage_1_2;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyOrReplyMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_0;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_1;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateRequestMessage_1_2;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageHandler;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_0;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_1;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage_1_2;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_0;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_1;
/*      */ import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage_1_2;
/*      */ import com.sun.corba.se.pept.broker.Broker;
/*      */ import com.sun.corba.se.pept.encoding.InputObject;
/*      */ import com.sun.corba.se.pept.encoding.OutputObject;
/*      */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*      */ import com.sun.corba.se.pept.protocol.ProtocolHandler;
/*      */ import com.sun.corba.se.pept.transport.Connection;
/*      */ import com.sun.corba.se.pept.transport.ContactInfo;
/*      */ import com.sun.corba.se.pept.transport.EventHandler;
/*      */ import com.sun.corba.se.spi.ior.IOR;
/*      */ import com.sun.corba.se.spi.ior.ObjectKey;
/*      */ import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
/*      */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*      */ import com.sun.corba.se.spi.ior.iiop.IIOPProfileTemplate;
/*      */ import com.sun.corba.se.spi.ior.iiop.MaxStreamFormatVersionComponent;
/*      */ import com.sun.corba.se.spi.oa.OAInvocationInfo;
/*      */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*      */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*      */ import com.sun.corba.se.spi.protocol.CorbaProtocolHandler;
/*      */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*      */ import com.sun.corba.se.spi.protocol.ForwardException;
/*      */ import com.sun.corba.se.spi.servicecontext.MaxStreamFormatVersionServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ORBVersionServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*      */ import com.sun.corba.se.spi.servicecontext.UEInfoServiceContext;
/*      */ import com.sun.corba.se.spi.servicecontext.UnknownServiceContext;
/*      */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*      */ import com.sun.corba.se.spi.transport.CorbaContactInfo;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintWriter;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.EmptyStackException;
/*      */ import java.util.Iterator;
/*      */ import org.omg.CORBA.Any;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.ExceptionList;
/*      */ import org.omg.CORBA.Request;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CORBA.TypeCode;
/*      */ import org.omg.CORBA.UNKNOWN;
/*      */ import org.omg.CORBA.UnknownUserException;
/*      */ import org.omg.CORBA.portable.InputStream;
/*      */ import org.omg.CORBA.portable.OutputStream;
/*      */ import org.omg.CORBA.portable.UnknownException;
/*      */ import org.omg.CORBA_2_3.portable.InputStream;
/*      */ import org.omg.CORBA_2_3.portable.OutputStream;
/*      */ import sun.corba.OutputStreamFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CorbaMessageMediatorImpl
/*      */   implements CorbaMessageMediator, CorbaProtocolHandler, MessageHandler
/*      */ {
/*      */   protected ORB orb;
/*      */   protected ORBUtilSystemException wrapper;
/*      */   protected InterceptorsSystemException interceptorWrapper;
/*      */   protected CorbaContactInfo contactInfo;
/*      */   protected CorbaConnection connection;
/*      */   protected short addrDisposition;
/*      */   protected CDROutputObject outputObject;
/*      */   protected CDRInputObject inputObject;
/*      */   protected Message messageHeader;
/*      */   protected RequestMessage requestHeader;
/*      */   protected LocateReplyOrReplyMessage replyHeader;
/*      */   protected String replyExceptionDetailMessage;
/*      */   protected IOR replyIOR;
/*      */   protected Integer requestIdInteger;
/*      */   protected Message dispatchHeader;
/*      */   protected ByteBuffer dispatchByteBuffer;
/*      */   protected byte streamFormatVersion;
/*      */   protected boolean streamFormatVersionSet = false;
/*      */   protected Request diiRequest;
/*      */   protected boolean cancelRequestAlreadySent = false;
/*      */   protected ProtocolHandler protocolHandler;
/*      */   protected boolean _executeReturnServantInResponseConstructor = false;
/*      */   protected boolean _executeRemoveThreadInfoInResponseConstructor = false;
/*      */   protected boolean _executePIInResponseConstructor = false;
/*      */   protected boolean isThreadDone;
/*      */   
/*      */   public CorbaMessageMediatorImpl(ORB paramORB, ContactInfo paramContactInfo, Connection paramConnection, GIOPVersion paramGIOPVersion, IOR paramIOR, int paramInt, short paramShort, String paramString, boolean paramBoolean) {
/*  181 */     this(paramORB, paramConnection);
/*      */     
/*  183 */     this.contactInfo = (CorbaContactInfo)paramContactInfo;
/*  184 */     this.addrDisposition = paramShort;
/*      */     
/*  186 */     this
/*  187 */       .streamFormatVersion = getStreamFormatVersionForThisRequest(this.contactInfo
/*  188 */         .getEffectiveTargetIOR(), paramGIOPVersion);
/*      */     
/*  190 */     this.streamFormatVersionSet = true;
/*      */     
/*  192 */     this.requestHeader = MessageBase.createRequest(this.orb, paramGIOPVersion, 
/*      */ 
/*      */         
/*  195 */         ORBUtility.getEncodingVersion(paramORB, paramIOR), paramInt, !paramBoolean, this.contactInfo
/*      */ 
/*      */         
/*  198 */         .getEffectiveTargetIOR(), this.addrDisposition, paramString, new ServiceContexts(paramORB), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediatorImpl(ORB paramORB, CorbaConnection paramCorbaConnection, Message paramMessage, ByteBuffer paramByteBuffer) {
/*  232 */     this(paramORB, (Connection)paramCorbaConnection);
/*  233 */     this.dispatchHeader = paramMessage;
/*  234 */     this.dispatchByteBuffer = paramByteBuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Broker getBroker() {
/*  244 */     return (Broker)this.orb;
/*      */   }
/*      */ 
/*      */   
/*      */   public ContactInfo getContactInfo() {
/*  249 */     return (ContactInfo)this.contactInfo;
/*      */   }
/*      */ 
/*      */   
/*      */   public Connection getConnection() {
/*  254 */     return (Connection)this.connection;
/*      */   }
/*      */ 
/*      */   
/*      */   public void initializeMessage() {
/*  259 */     getRequestHeader().write((OutputStream)this.outputObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void finishSendingRequest() {
/*  265 */     this.outputObject.finishSendingMessage();
/*      */   }
/*      */ 
/*      */   
/*      */   public InputObject waitForResponse() {
/*  270 */     if (getRequestHeader().isResponseExpected()) {
/*  271 */       return this.connection.waitForResponse((MessageMediator)this);
/*      */     }
/*  273 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOutputObject(OutputObject paramOutputObject) {
/*  278 */     this.outputObject = (CDROutputObject)paramOutputObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public OutputObject getOutputObject() {
/*  283 */     return (OutputObject)this.outputObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInputObject(InputObject paramInputObject) {
/*  288 */     this.inputObject = (CDRInputObject)paramInputObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public InputObject getInputObject() {
/*  293 */     return (InputObject)this.inputObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReplyHeader(LocateReplyOrReplyMessage paramLocateReplyOrReplyMessage) {
/*  303 */     this.replyHeader = paramLocateReplyOrReplyMessage;
/*  304 */     this.replyIOR = paramLocateReplyOrReplyMessage.getIOR();
/*      */   }
/*      */ 
/*      */   
/*      */   public LocateReplyMessage getLocateReplyHeader() {
/*  309 */     return (LocateReplyMessage)this.replyHeader;
/*      */   }
/*      */ 
/*      */   
/*      */   public ReplyMessage getReplyHeader() {
/*  314 */     return (ReplyMessage)this.replyHeader;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReplyExceptionDetailMessage(String paramString) {
/*  319 */     this.replyExceptionDetailMessage = paramString;
/*      */   }
/*      */ 
/*      */   
/*      */   public RequestMessage getRequestHeader() {
/*  324 */     return this.requestHeader;
/*      */   }
/*      */ 
/*      */   
/*      */   public GIOPVersion getGIOPVersion() {
/*  329 */     if (this.messageHeader != null) {
/*  330 */       return this.messageHeader.getGIOPVersion();
/*      */     }
/*  332 */     return getRequestHeader().getGIOPVersion();
/*      */   }
/*      */   
/*      */   public byte getEncodingVersion() {
/*  336 */     if (this.messageHeader != null) {
/*  337 */       return this.messageHeader.getEncodingVersion();
/*      */     }
/*  339 */     return getRequestHeader().getEncodingVersion();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRequestId() {
/*  344 */     return getRequestHeader().getRequestId();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getRequestIdInteger() {
/*  349 */     if (this.requestIdInteger == null) {
/*  350 */       this.requestIdInteger = new Integer(getRequestHeader().getRequestId());
/*      */     }
/*  352 */     return this.requestIdInteger;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOneWay() {
/*  357 */     return !getRequestHeader().isResponseExpected();
/*      */   }
/*      */ 
/*      */   
/*      */   public short getAddrDisposition() {
/*  362 */     return this.addrDisposition;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getOperationName() {
/*  367 */     return getRequestHeader().getOperation();
/*      */   }
/*      */ 
/*      */   
/*      */   public ServiceContexts getRequestServiceContexts() {
/*  372 */     return getRequestHeader().getServiceContexts();
/*      */   }
/*      */ 
/*      */   
/*      */   public ServiceContexts getReplyServiceContexts() {
/*  377 */     return getReplyHeader().getServiceContexts();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendCancelRequestIfFinalFragmentNotSent() {
/*  382 */     if (!sentFullMessage() && sentFragment() && !this.cancelRequestAlreadySent) {
/*      */       
/*      */       try {
/*      */         
/*  386 */         if (this.orb.subcontractDebugFlag) {
/*  387 */           dprint(".sendCancelRequestIfFinalFragmentNotSent->: " + 
/*  388 */               opAndId(this));
/*      */         }
/*  390 */         this.connection.sendCancelRequestWithLock(getGIOPVersion(), 
/*  391 */             getRequestId());
/*      */ 
/*      */ 
/*      */         
/*  395 */         this.cancelRequestAlreadySent = true;
/*  396 */       } catch (IOException iOException) {
/*  397 */         if (this.orb.subcontractDebugFlag) {
/*  398 */           dprint(".sendCancelRequestIfFinalFragmentNotSent: !ERROR : " + opAndId(this), iOException);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  404 */         throw this.interceptorWrapper.ioexceptionDuringCancelRequest(CompletionStatus.COMPLETED_MAYBE, iOException);
/*      */       } finally {
/*      */         
/*  407 */         if (this.orb.subcontractDebugFlag) {
/*  408 */           dprint(".sendCancelRequestIfFinalFragmentNotSent<-: " + 
/*  409 */               opAndId(this));
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean sentFullMessage() {
/*  417 */     return this.outputObject.getBufferManager().sentFullMessage();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean sentFragment() {
/*  422 */     return this.outputObject.getBufferManager().sentFragment();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDIIInfo(Request paramRequest) {
/*  427 */     this.diiRequest = paramRequest;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDIIRequest() {
/*  432 */     return (this.diiRequest != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public Exception unmarshalDIIUserException(String paramString, InputStream paramInputStream) {
/*  437 */     if (!isDIIRequest()) {
/*  438 */       return null;
/*      */     }
/*      */     
/*  441 */     ExceptionList exceptionList = this.diiRequest.exceptions();
/*      */ 
/*      */     
/*      */     try {
/*  445 */       for (byte b = 0; b < exceptionList.count(); b++) {
/*  446 */         TypeCode typeCode = exceptionList.item(b);
/*  447 */         if (typeCode.id().equals(paramString)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  452 */           Any any = this.orb.create_any();
/*  453 */           any.read_value((InputStream)paramInputStream, typeCode);
/*      */           
/*  455 */           return (Exception)new UnknownUserException(any);
/*      */         } 
/*      */       } 
/*  458 */     } catch (Exception exception) {
/*  459 */       throw this.wrapper.unexpectedDiiException(exception);
/*      */     } 
/*      */ 
/*      */     
/*  463 */     return (Exception)this.wrapper.unknownCorbaExc(CompletionStatus.COMPLETED_MAYBE);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDIIException(Exception paramException) {
/*  468 */     this.diiRequest.env().exception(paramException);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleDIIReply(InputStream paramInputStream) {
/*  473 */     if (!isDIIRequest()) {
/*      */       return;
/*      */     }
/*  476 */     ((RequestImpl)this.diiRequest).unmarshalReply((InputStream)paramInputStream);
/*      */   }
/*      */ 
/*      */   
/*      */   public Message getDispatchHeader() {
/*  481 */     return this.dispatchHeader;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDispatchHeader(Message paramMessage) {
/*  486 */     this.dispatchHeader = paramMessage;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer getDispatchBuffer() {
/*  491 */     return this.dispatchByteBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDispatchBuffer(ByteBuffer paramByteBuffer) {
/*  496 */     this.dispatchByteBuffer = paramByteBuffer;
/*      */   }
/*      */   
/*      */   public int getThreadPoolToUse() {
/*  500 */     int i = 0;
/*  501 */     Message message = getDispatchHeader();
/*      */ 
/*      */     
/*  504 */     if (message != null) {
/*  505 */       i = message.getThreadPoolToUse();
/*      */     }
/*  507 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getStreamFormatVersion() {
/*  517 */     if (this.streamFormatVersionSet) {
/*  518 */       return this.streamFormatVersion;
/*      */     }
/*  520 */     return getStreamFormatVersionForReply();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getStreamFormatVersionForReply() {
/*  535 */     ServiceContexts serviceContexts = getRequestServiceContexts();
/*      */ 
/*      */     
/*  538 */     MaxStreamFormatVersionServiceContext maxStreamFormatVersionServiceContext = (MaxStreamFormatVersionServiceContext)serviceContexts.get(17);
/*      */ 
/*      */     
/*  541 */     if (maxStreamFormatVersionServiceContext != null) {
/*  542 */       byte b1 = ORBUtility.getMaxStreamFormatVersion();
/*  543 */       byte b2 = maxStreamFormatVersionServiceContext.getMaximumStreamFormatVersion();
/*      */       
/*  545 */       return (byte)Math.min(b1, b2);
/*      */     } 
/*      */ 
/*      */     
/*  549 */     if (getGIOPVersion().lessThan(GIOPVersion.V1_3)) {
/*  550 */       return 1;
/*      */     }
/*  552 */     return 2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSystemExceptionReply() {
/*  558 */     return (this.replyHeader.getReplyStatus() == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUserExceptionReply() {
/*  563 */     return (this.replyHeader.getReplyStatus() == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLocationForwardReply() {
/*  568 */     return (this.replyHeader.getReplyStatus() == 3 || this.replyHeader
/*  569 */       .getReplyStatus() == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDifferentAddrDispositionRequestedReply() {
/*  575 */     return (this.replyHeader.getReplyStatus() == 5);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getAddrDispositionReply() {
/*  580 */     return this.replyHeader.getAddrDisposition();
/*      */   }
/*      */ 
/*      */   
/*      */   public IOR getForwardedIOR() {
/*  585 */     return this.replyHeader.getIOR();
/*      */   }
/*      */ 
/*      */   
/*      */   public SystemException getSystemExceptionReply() {
/*  590 */     return this.replyHeader.getSystemException(this.replyExceptionDetailMessage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectKey getObjectKey() {
/*  600 */     return getRequestHeader().getObjectKey();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProtocolHandler(CorbaProtocolHandler paramCorbaProtocolHandler) {
/*  605 */     throw this.wrapper.methodShouldNotBeCalled();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaProtocolHandler getProtocolHandler() {
/*  611 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream createReply() {
/*  623 */     getProtocolHandler().createResponse(this, (ServiceContexts)null);
/*  624 */     return (OutputStream)getOutputObject();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public OutputStream createExceptionReply() {
/*  631 */     getProtocolHandler().createUserExceptionResponse(this, (ServiceContexts)null);
/*  632 */     return (OutputStream)getOutputObject();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean executeReturnServantInResponseConstructor() {
/*  637 */     return this._executeReturnServantInResponseConstructor;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExecuteReturnServantInResponseConstructor(boolean paramBoolean) {
/*  643 */     this._executeReturnServantInResponseConstructor = paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean executeRemoveThreadInfoInResponseConstructor() {
/*  648 */     return this._executeRemoveThreadInfoInResponseConstructor;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExecuteRemoveThreadInfoInResponseConstructor(boolean paramBoolean) {
/*  653 */     this._executeRemoveThreadInfoInResponseConstructor = paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean executePIInResponseConstructor() {
/*  658 */     return this._executePIInResponseConstructor;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExecutePIInResponseConstructor(boolean paramBoolean) {
/*  663 */     this._executePIInResponseConstructor = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte getStreamFormatVersionForThisRequest(IOR paramIOR, GIOPVersion paramGIOPVersion) {
/*  671 */     byte b1 = ORBUtility.getMaxStreamFormatVersion();
/*      */ 
/*      */     
/*  674 */     IOR iOR = this.contactInfo.getEffectiveTargetIOR();
/*      */     
/*  676 */     IIOPProfileTemplate iIOPProfileTemplate = (IIOPProfileTemplate)iOR.getProfile().getTaggedProfileTemplate();
/*  677 */     Iterator<MaxStreamFormatVersionComponent> iterator = iIOPProfileTemplate.iteratorById(38);
/*  678 */     if (!iterator.hasNext()) {
/*      */ 
/*      */       
/*  681 */       if (paramGIOPVersion.lessThan(GIOPVersion.V1_3)) {
/*  682 */         return 1;
/*      */       }
/*  684 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/*  688 */     byte b2 = ((MaxStreamFormatVersionComponent)iterator.next()).getMaxStreamFormatVersion();
/*      */     
/*  690 */     return (byte)Math.min(b1, b2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediatorImpl(ORB paramORB, Connection paramConnection) {
/*  702 */     this.isThreadDone = false;
/*      */     this.orb = paramORB;
/*      */     this.connection = (CorbaConnection)paramConnection;
/*      */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*      */     this.interceptorWrapper = InterceptorsSystemException.get(paramORB, "rpc.protocol");
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean handleRequest(MessageMediator paramMessageMediator) {
/*      */     try {
/*  712 */       this.dispatchHeader.callback(this);
/*  713 */     } catch (IOException iOException) {}
/*      */ 
/*      */ 
/*      */     
/*  717 */     return this.isThreadDone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setWorkThenPoolOrResumeSelect(Message paramMessage) {
/*  727 */     if (getConnection().getEventHandler().shouldUseSelectThreadToWait()) {
/*  728 */       resumeSelect(paramMessage);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  733 */       this.isThreadDone = true;
/*      */ 
/*      */       
/*  736 */       this.orb.getTransportManager().getSelector(0)
/*  737 */         .unregisterForEvent(getConnection().getEventHandler());
/*      */       
/*  739 */       this.orb.getTransportManager().getSelector(0)
/*  740 */         .registerForEvent(getConnection().getEventHandler());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setWorkThenReadOrResumeSelect(Message paramMessage) {
/*  746 */     if (getConnection().getEventHandler().shouldUseSelectThreadToWait()) {
/*  747 */       resumeSelect(paramMessage);
/*      */     }
/*      */     else {
/*      */       
/*  751 */       this.isThreadDone = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resumeSelect(Message paramMessage) {
/*  761 */     if (transportDebug()) {
/*  762 */       dprint(".resumeSelect:->");
/*      */       
/*  764 */       String str = "?";
/*  765 */       if (paramMessage instanceof RequestMessage) {
/*      */ 
/*      */         
/*  768 */         str = (new Integer(((RequestMessage)paramMessage).getRequestId())).toString();
/*  769 */       } else if (paramMessage instanceof ReplyMessage) {
/*      */ 
/*      */         
/*  772 */         str = (new Integer(((ReplyMessage)paramMessage).getRequestId())).toString();
/*  773 */       } else if (paramMessage instanceof FragmentMessage_1_2) {
/*      */ 
/*      */         
/*  776 */         str = (new Integer(((FragmentMessage_1_2)paramMessage).getRequestId())).toString();
/*      */       } 
/*  778 */       dprint(".resumeSelect: id/" + str + " " + 
/*      */           
/*  780 */           getConnection());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  789 */     EventHandler eventHandler = getConnection().getEventHandler();
/*  790 */     this.orb.getTransportManager().getSelector(0).registerInterestOps(eventHandler);
/*      */     
/*  792 */     if (transportDebug()) {
/*  793 */       dprint(".resumeSelect:<-");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setInputObject() {
/*  802 */     if (getConnection().getContactInfo() != null) {
/*  803 */       this
/*      */         
/*  805 */         .inputObject = (CDRInputObject)getConnection().getContactInfo().createInputObject((Broker)this.orb, (MessageMediator)this);
/*  806 */     } else if (getConnection().getAcceptor() != null) {
/*  807 */       this
/*      */         
/*  809 */         .inputObject = (CDRInputObject)getConnection().getAcceptor().createInputObject((Broker)this.orb, (MessageMediator)this);
/*      */     } else {
/*  811 */       throw new RuntimeException("CorbaMessageMediatorImpl.setInputObject");
/*      */     } 
/*  813 */     this.inputObject.setMessageMediator((MessageMediator)this);
/*  814 */     setInputObject((InputObject)this.inputObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void signalResponseReceived() {
/*  822 */     this.connection.getResponseWaitingRoom()
/*  823 */       .responseReceived((InputObject)this.inputObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(Message paramMessage) throws IOException {
/*      */     try {
/*  830 */       this.messageHeader = paramMessage;
/*      */       
/*  832 */       if (transportDebug()) {
/*  833 */         dprint(".handleInput->: " + 
/*  834 */             MessageBase.typeToString(paramMessage.getType()));
/*      */       }
/*  836 */       setWorkThenReadOrResumeSelect(paramMessage);
/*      */       
/*  838 */       switch (paramMessage.getType()) {
/*      */         
/*      */         case 5:
/*  841 */           if (transportDebug()) {
/*  842 */             dprint(".handleInput: CloseConnection: purging");
/*      */           }
/*  844 */           this.connection.purgeCalls((SystemException)this.wrapper.connectionRebind(), true, false);
/*      */           break;
/*      */         case 6:
/*  847 */           if (transportDebug()) {
/*  848 */             dprint(".handleInput: MessageError: purging");
/*      */           }
/*  850 */           this.connection.purgeCalls((SystemException)this.wrapper.recvMsgError(), true, false);
/*      */           break;
/*      */         default:
/*  853 */           if (transportDebug()) {
/*  854 */             dprint(".handleInput: ERROR: " + 
/*  855 */                 MessageBase.typeToString(paramMessage.getType()));
/*      */           }
/*  857 */           throw this.wrapper.badGiopRequestType();
/*      */       } 
/*  859 */       releaseByteBufferToPool();
/*      */     } finally {
/*  861 */       if (transportDebug()) {
/*  862 */         dprint(".handleInput<-: " + 
/*  863 */             MessageBase.typeToString(paramMessage.getType()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleInput(RequestMessage_1_0 paramRequestMessage_1_0) throws IOException {
/*      */     try {
/*  871 */       if (transportDebug()) dprint(".REQUEST 1.0->: " + paramRequestMessage_1_0); 
/*      */       try {
/*  873 */         this.messageHeader = (Message)(this.requestHeader = (RequestMessage)paramRequestMessage_1_0);
/*  874 */         setInputObject();
/*      */       } finally {
/*  876 */         setWorkThenPoolOrResumeSelect((Message)paramRequestMessage_1_0);
/*      */       } 
/*  878 */       getProtocolHandler().handleRequest((RequestMessage)paramRequestMessage_1_0, this);
/*  879 */     } catch (Throwable throwable) {
/*  880 */       if (transportDebug()) {
/*  881 */         dprint(".REQUEST 1.0: !!ERROR!!: " + paramRequestMessage_1_0, throwable);
/*      */       }
/*      */     } finally {
/*  884 */       if (transportDebug()) dprint(".REQUEST 1.0<-: " + paramRequestMessage_1_0);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(RequestMessage_1_1 paramRequestMessage_1_1) throws IOException {
/*      */     try {
/*  891 */       if (transportDebug()) dprint(".REQUEST 1.1->: " + paramRequestMessage_1_1); 
/*      */       try {
/*  893 */         this.messageHeader = (Message)(this.requestHeader = (RequestMessage)paramRequestMessage_1_1);
/*  894 */         setInputObject();
/*  895 */         this.connection.serverRequest_1_1_Put((MessageMediator)this);
/*      */       } finally {
/*  897 */         setWorkThenPoolOrResumeSelect((Message)paramRequestMessage_1_1);
/*      */       } 
/*  899 */       getProtocolHandler().handleRequest((RequestMessage)paramRequestMessage_1_1, this);
/*  900 */     } catch (Throwable throwable) {
/*  901 */       if (transportDebug()) {
/*  902 */         dprint(".REQUEST 1.1: !!ERROR!!: " + paramRequestMessage_1_1, throwable);
/*      */       }
/*      */     } finally {
/*  905 */       if (transportDebug()) dprint(".REQUEST 1.1<-: " + paramRequestMessage_1_1);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(RequestMessage_1_2 paramRequestMessage_1_2) throws IOException {
/*      */     try {
/*      */       try {
/*  915 */         this.messageHeader = (Message)(this.requestHeader = (RequestMessage)paramRequestMessage_1_2);
/*      */         
/*  917 */         paramRequestMessage_1_2.unmarshalRequestID(this.dispatchByteBuffer);
/*  918 */         setInputObject();
/*      */         
/*  920 */         if (transportDebug()) dprint(".REQUEST 1.2->: id/" + paramRequestMessage_1_2
/*  921 */               .getRequestId() + ": " + paramRequestMessage_1_2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  931 */         this.connection.serverRequestMapPut(paramRequestMessage_1_2.getRequestId(), this);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/*  937 */         setWorkThenPoolOrResumeSelect((Message)paramRequestMessage_1_2);
/*      */       } 
/*      */       
/*  940 */       getProtocolHandler().handleRequest((RequestMessage)paramRequestMessage_1_2, this);
/*  941 */     } catch (Throwable throwable) {
/*  942 */       if (transportDebug()) dprint(".REQUEST 1.2: id/" + paramRequestMessage_1_2
/*  943 */             .getRequestId() + ": !!ERROR!!: " + paramRequestMessage_1_2, throwable);
/*      */       
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/*  949 */       this.connection.serverRequestMapRemove(paramRequestMessage_1_2.getRequestId());
/*      */       
/*  951 */       if (transportDebug()) dprint(".REQUEST 1.2<-: id/" + paramRequestMessage_1_2
/*  952 */             .getRequestId() + ": " + paramRequestMessage_1_2);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(ReplyMessage_1_0 paramReplyMessage_1_0) throws IOException {
/*      */     try {
/*      */       try {
/*  962 */         if (transportDebug()) dprint(".REPLY 1.0->: " + paramReplyMessage_1_0); 
/*  963 */         this.messageHeader = (Message)(this.replyHeader = (LocateReplyOrReplyMessage)paramReplyMessage_1_0);
/*  964 */         setInputObject();
/*      */ 
/*      */         
/*  967 */         this.inputObject.unmarshalHeader();
/*      */         
/*  969 */         signalResponseReceived();
/*      */       } finally {
/*  971 */         setWorkThenReadOrResumeSelect((Message)paramReplyMessage_1_0);
/*      */       } 
/*  973 */     } catch (Throwable throwable) {
/*  974 */       if (transportDebug()) dprint(".REPLY 1.0: !!ERROR!!: " + paramReplyMessage_1_0, throwable);
/*      */     
/*      */     } finally {
/*  977 */       if (transportDebug()) dprint(".REPLY 1.0<-: " + paramReplyMessage_1_0);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(ReplyMessage_1_1 paramReplyMessage_1_1) throws IOException {
/*      */     try {
/*  984 */       if (transportDebug()) dprint(".REPLY 1.1->: " + paramReplyMessage_1_1); 
/*  985 */       this.messageHeader = (Message)(this.replyHeader = (LocateReplyOrReplyMessage)paramReplyMessage_1_1);
/*  986 */       setInputObject();
/*      */       
/*  988 */       if (paramReplyMessage_1_1.moreFragmentsToFollow()) {
/*      */ 
/*      */ 
/*      */         
/*  992 */         this.connection.clientReply_1_1_Put((MessageMediator)this);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  998 */         setWorkThenPoolOrResumeSelect((Message)paramReplyMessage_1_1);
/*      */ 
/*      */ 
/*      */         
/* 1002 */         this.inputObject.unmarshalHeader();
/*      */         
/* 1004 */         signalResponseReceived();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1015 */         this.inputObject.unmarshalHeader();
/*      */         
/* 1017 */         signalResponseReceived();
/*      */         
/* 1019 */         setWorkThenReadOrResumeSelect((Message)paramReplyMessage_1_1);
/*      */       } 
/* 1021 */     } catch (Throwable throwable) {
/* 1022 */       if (transportDebug()) dprint(".REPLY 1.1: !!ERROR!!: " + paramReplyMessage_1_1);
/*      */     
/*      */     } finally {
/* 1025 */       if (transportDebug()) dprint(".REPLY 1.1<-: " + paramReplyMessage_1_1);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(ReplyMessage_1_2 paramReplyMessage_1_2) throws IOException {
/*      */     try {
/*      */       try {
/* 1033 */         this.messageHeader = (Message)(this.replyHeader = (LocateReplyOrReplyMessage)paramReplyMessage_1_2);
/*      */ 
/*      */         
/* 1036 */         paramReplyMessage_1_2.unmarshalRequestID(this.dispatchByteBuffer);
/*      */         
/* 1038 */         if (transportDebug()) {
/* 1039 */           dprint(".REPLY 1.2->: id/" + paramReplyMessage_1_2
/* 1040 */               .getRequestId() + ": more?: " + paramReplyMessage_1_2
/* 1041 */               .moreFragmentsToFollow() + ": " + paramReplyMessage_1_2);
/*      */         }
/*      */ 
/*      */         
/* 1045 */         setInputObject();
/*      */         
/* 1047 */         signalResponseReceived();
/*      */       } finally {
/* 1049 */         setWorkThenReadOrResumeSelect((Message)paramReplyMessage_1_2);
/*      */       } 
/* 1051 */     } catch (Throwable throwable) {
/* 1052 */       if (transportDebug()) dprint(".REPLY 1.2: id/" + paramReplyMessage_1_2
/* 1053 */             .getRequestId() + ": !!ERROR!!: " + paramReplyMessage_1_2, throwable);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1058 */       if (transportDebug()) dprint(".REPLY 1.2<-: id/" + paramReplyMessage_1_2
/* 1059 */             .getRequestId() + ": " + paramReplyMessage_1_2);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(LocateRequestMessage_1_0 paramLocateRequestMessage_1_0) throws IOException {
/*      */     try {
/* 1068 */       if (transportDebug())
/* 1069 */         dprint(".LOCATE_REQUEST 1.0->: " + paramLocateRequestMessage_1_0); 
/*      */       try {
/* 1071 */         this.messageHeader = (Message)paramLocateRequestMessage_1_0;
/* 1072 */         setInputObject();
/*      */       } finally {
/* 1074 */         setWorkThenPoolOrResumeSelect((Message)paramLocateRequestMessage_1_0);
/*      */       } 
/* 1076 */       getProtocolHandler().handleRequest((LocateRequestMessage)paramLocateRequestMessage_1_0, this);
/* 1077 */     } catch (Throwable throwable) {
/* 1078 */       if (transportDebug()) {
/* 1079 */         dprint(".LOCATE_REQUEST 1.0: !!ERROR!!: " + paramLocateRequestMessage_1_0, throwable);
/*      */       }
/*      */     } finally {
/* 1082 */       if (transportDebug()) {
/* 1083 */         dprint(".LOCATE_REQUEST 1.0<-: " + paramLocateRequestMessage_1_0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleInput(LocateRequestMessage_1_1 paramLocateRequestMessage_1_1) throws IOException {
/*      */     try {
/* 1091 */       if (transportDebug())
/* 1092 */         dprint(".LOCATE_REQUEST 1.1->: " + paramLocateRequestMessage_1_1); 
/*      */       try {
/* 1094 */         this.messageHeader = (Message)paramLocateRequestMessage_1_1;
/* 1095 */         setInputObject();
/*      */       } finally {
/* 1097 */         setWorkThenPoolOrResumeSelect((Message)paramLocateRequestMessage_1_1);
/*      */       } 
/* 1099 */       getProtocolHandler().handleRequest((LocateRequestMessage)paramLocateRequestMessage_1_1, this);
/* 1100 */     } catch (Throwable throwable) {
/* 1101 */       if (transportDebug()) {
/* 1102 */         dprint(".LOCATE_REQUEST 1.1: !!ERROR!!: " + paramLocateRequestMessage_1_1, throwable);
/*      */       }
/*      */     } finally {
/* 1105 */       if (transportDebug()) {
/* 1106 */         dprint(".LOCATE_REQUEST 1.1<-:" + paramLocateRequestMessage_1_1);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(LocateRequestMessage_1_2 paramLocateRequestMessage_1_2) throws IOException {
/*      */     try {
/*      */       try {
/* 1114 */         this.messageHeader = (Message)paramLocateRequestMessage_1_2;
/*      */         
/* 1116 */         paramLocateRequestMessage_1_2.unmarshalRequestID(this.dispatchByteBuffer);
/* 1117 */         setInputObject();
/*      */         
/* 1119 */         if (transportDebug()) {
/* 1120 */           dprint(".LOCATE_REQUEST 1.2->: id/" + paramLocateRequestMessage_1_2
/* 1121 */               .getRequestId() + ": " + paramLocateRequestMessage_1_2);
/*      */         }
/*      */ 
/*      */         
/* 1125 */         if (paramLocateRequestMessage_1_2.moreFragmentsToFollow()) {
/* 1126 */           this.connection.serverRequestMapPut(paramLocateRequestMessage_1_2.getRequestId(), this);
/*      */         }
/*      */       } finally {
/* 1129 */         setWorkThenPoolOrResumeSelect((Message)paramLocateRequestMessage_1_2);
/*      */       } 
/* 1131 */       getProtocolHandler().handleRequest((LocateRequestMessage)paramLocateRequestMessage_1_2, this);
/* 1132 */     } catch (Throwable throwable) {
/* 1133 */       if (transportDebug()) {
/* 1134 */         dprint(".LOCATE_REQUEST 1.2: id/" + paramLocateRequestMessage_1_2
/* 1135 */             .getRequestId() + ": !!ERROR!!: " + paramLocateRequestMessage_1_2, throwable);
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/* 1140 */       if (transportDebug()) {
/* 1141 */         dprint(".LOCATE_REQUEST 1.2<-: id/" + paramLocateRequestMessage_1_2
/* 1142 */             .getRequestId() + ": " + paramLocateRequestMessage_1_2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(LocateReplyMessage_1_0 paramLocateReplyMessage_1_0) throws IOException {
/*      */     try {
/* 1151 */       if (transportDebug())
/* 1152 */         dprint(".LOCATE_REPLY 1.0->:" + paramLocateReplyMessage_1_0); 
/*      */       try {
/* 1154 */         this.messageHeader = (Message)paramLocateReplyMessage_1_0;
/* 1155 */         setInputObject();
/* 1156 */         this.inputObject.unmarshalHeader();
/* 1157 */         signalResponseReceived();
/*      */       } finally {
/* 1159 */         setWorkThenReadOrResumeSelect((Message)paramLocateReplyMessage_1_0);
/*      */       } 
/* 1161 */     } catch (Throwable throwable) {
/* 1162 */       if (transportDebug()) {
/* 1163 */         dprint(".LOCATE_REPLY 1.0: !!ERROR!!: " + paramLocateReplyMessage_1_0, throwable);
/*      */       }
/*      */     } finally {
/* 1166 */       if (transportDebug()) {
/* 1167 */         dprint(".LOCATE_REPLY 1.0<-: " + paramLocateReplyMessage_1_0);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(LocateReplyMessage_1_1 paramLocateReplyMessage_1_1) throws IOException {
/*      */     try {
/* 1174 */       if (transportDebug()) dprint(".LOCATE_REPLY 1.1->: " + paramLocateReplyMessage_1_1); 
/*      */       try {
/* 1176 */         this.messageHeader = (Message)paramLocateReplyMessage_1_1;
/* 1177 */         setInputObject();
/*      */         
/* 1179 */         this.inputObject.unmarshalHeader();
/* 1180 */         signalResponseReceived();
/*      */       } finally {
/* 1182 */         setWorkThenReadOrResumeSelect((Message)paramLocateReplyMessage_1_1);
/*      */       } 
/* 1184 */     } catch (Throwable throwable) {
/* 1185 */       if (transportDebug()) {
/* 1186 */         dprint(".LOCATE_REPLY 1.1: !!ERROR!!: " + paramLocateReplyMessage_1_1, throwable);
/*      */       }
/*      */     } finally {
/* 1189 */       if (transportDebug()) dprint(".LOCATE_REPLY 1.1<-: " + paramLocateReplyMessage_1_1);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(LocateReplyMessage_1_2 paramLocateReplyMessage_1_2) throws IOException {
/*      */     try {
/*      */       try {
/* 1197 */         this.messageHeader = (Message)paramLocateReplyMessage_1_2;
/*      */ 
/*      */         
/* 1200 */         paramLocateReplyMessage_1_2.unmarshalRequestID(this.dispatchByteBuffer);
/*      */         
/* 1202 */         setInputObject();
/*      */         
/* 1204 */         if (transportDebug()) dprint(".LOCATE_REPLY 1.2->: id/" + paramLocateReplyMessage_1_2
/* 1205 */               .getRequestId() + ": " + paramLocateReplyMessage_1_2);
/*      */ 
/*      */ 
/*      */         
/* 1209 */         signalResponseReceived();
/*      */       } finally {
/* 1211 */         setWorkThenPoolOrResumeSelect((Message)paramLocateReplyMessage_1_2);
/*      */       } 
/* 1213 */     } catch (Throwable throwable) {
/* 1214 */       if (transportDebug()) {
/* 1215 */         dprint(".LOCATE_REPLY 1.2: id/" + paramLocateReplyMessage_1_2
/* 1216 */             .getRequestId() + ": !!ERROR!!: " + paramLocateReplyMessage_1_2, throwable);
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/* 1221 */       if (transportDebug()) dprint(".LOCATE_REPLY 1.2<-: id/" + paramLocateReplyMessage_1_2
/* 1222 */             .getRequestId() + ": " + paramLocateReplyMessage_1_2);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(FragmentMessage_1_1 paramFragmentMessage_1_1) throws IOException {
/*      */     try {
/* 1231 */       if (transportDebug()) {
/* 1232 */         dprint(".FRAGMENT 1.1->: more?: " + paramFragmentMessage_1_1
/* 1233 */             .moreFragmentsToFollow() + ": " + paramFragmentMessage_1_1);
/*      */       }
/*      */       
/*      */       try {
/* 1237 */         this.messageHeader = (Message)paramFragmentMessage_1_1;
/* 1238 */         MessageMediator messageMediator = null;
/* 1239 */         CDRInputObject cDRInputObject = null;
/*      */         
/* 1241 */         if (this.connection.isServer()) {
/* 1242 */           messageMediator = this.connection.serverRequest_1_1_Get();
/*      */         } else {
/* 1244 */           messageMediator = this.connection.clientReply_1_1_Get();
/*      */         } 
/* 1246 */         if (messageMediator != null) {
/* 1247 */           cDRInputObject = (CDRInputObject)messageMediator.getInputObject();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1259 */         if (cDRInputObject == null) {
/* 1260 */           if (transportDebug()) {
/* 1261 */             dprint(".FRAGMENT 1.1: ++++DISCARDING++++: " + paramFragmentMessage_1_1);
/*      */           }
/*      */           
/* 1264 */           releaseByteBufferToPool();
/*      */           
/*      */           return;
/*      */         } 
/* 1268 */         cDRInputObject.getBufferManager()
/* 1269 */           .processFragment(this.dispatchByteBuffer, (FragmentMessage)paramFragmentMessage_1_1);
/*      */         
/* 1271 */         if (!paramFragmentMessage_1_1.moreFragmentsToFollow()) {
/* 1272 */           if (this.connection.isServer()) {
/* 1273 */             this.connection.serverRequest_1_1_Remove();
/*      */           } else {
/* 1275 */             this.connection.clientReply_1_1_Remove();
/*      */           }
/*      */         
/*      */         }
/*      */       } finally {
/*      */         
/* 1281 */         setWorkThenReadOrResumeSelect((Message)paramFragmentMessage_1_1);
/*      */       } 
/* 1283 */     } catch (Throwable throwable) {
/* 1284 */       if (transportDebug()) {
/* 1285 */         dprint(".FRAGMENT 1.1: !!ERROR!!: " + paramFragmentMessage_1_1, throwable);
/*      */       }
/*      */     } finally {
/* 1288 */       if (transportDebug()) dprint(".FRAGMENT 1.1<-: " + paramFragmentMessage_1_1); 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleInput(FragmentMessage_1_2 paramFragmentMessage_1_2) throws IOException {
/*      */     try {
/*      */       try {
/*      */         MessageMediator messageMediator;
/* 1296 */         this.messageHeader = (Message)paramFragmentMessage_1_2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1303 */         paramFragmentMessage_1_2.unmarshalRequestID(this.dispatchByteBuffer);
/*      */         
/* 1305 */         if (transportDebug()) {
/* 1306 */           dprint(".FRAGMENT 1.2->: id/" + paramFragmentMessage_1_2
/* 1307 */               .getRequestId() + ": more?: " + paramFragmentMessage_1_2
/* 1308 */               .moreFragmentsToFollow() + ": " + paramFragmentMessage_1_2);
/*      */         }
/*      */ 
/*      */         
/* 1312 */         CorbaMessageMediator corbaMessageMediator = null;
/* 1313 */         InputObject inputObject = null;
/*      */         
/* 1315 */         if (this.connection.isServer()) {
/*      */           
/* 1317 */           corbaMessageMediator = this.connection.serverRequestMapGet(paramFragmentMessage_1_2.getRequestId());
/*      */         } else {
/*      */           
/* 1320 */           messageMediator = this.connection.clientRequestMapGet(paramFragmentMessage_1_2.getRequestId());
/*      */         } 
/* 1322 */         if (messageMediator != null) {
/* 1323 */           inputObject = messageMediator.getInputObject();
/*      */         }
/*      */         
/* 1326 */         if (inputObject == null) {
/* 1327 */           if (transportDebug()) {
/* 1328 */             dprint(".FRAGMENT 1.2: id/" + paramFragmentMessage_1_2
/* 1329 */                 .getRequestId() + ": ++++DISCARDING++++: " + paramFragmentMessage_1_2);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1335 */           releaseByteBufferToPool();
/*      */           return;
/*      */         } 
/* 1338 */         ((CDRInputObject)inputObject)
/* 1339 */           .getBufferManager().processFragment(this.dispatchByteBuffer, (FragmentMessage)paramFragmentMessage_1_2);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1344 */         if (!this.connection.isServer());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1356 */         setWorkThenReadOrResumeSelect((Message)paramFragmentMessage_1_2);
/*      */       } 
/* 1358 */     } catch (Throwable throwable) {
/* 1359 */       if (transportDebug()) {
/* 1360 */         dprint(".FRAGMENT 1.2: id/" + paramFragmentMessage_1_2
/* 1361 */             .getRequestId() + ": !!ERROR!!: " + paramFragmentMessage_1_2, throwable);
/*      */       }
/*      */     }
/*      */     finally {
/*      */       
/* 1366 */       if (transportDebug()) dprint(".FRAGMENT 1.2<-: id/" + paramFragmentMessage_1_2
/* 1367 */             .getRequestId() + ": " + paramFragmentMessage_1_2);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleInput(CancelRequestMessage paramCancelRequestMessage) throws IOException {
/*      */     try {
/*      */       try {
/* 1377 */         this.messageHeader = (Message)paramCancelRequestMessage;
/* 1378 */         setInputObject();
/*      */ 
/*      */         
/* 1381 */         this.inputObject.unmarshalHeader();
/*      */         
/* 1383 */         if (transportDebug()) dprint(".CANCEL->: id/" + paramCancelRequestMessage
/* 1384 */               .getRequestId() + ": " + paramCancelRequestMessage
/* 1385 */               .getGIOPVersion() + ": " + paramCancelRequestMessage);
/*      */ 
/*      */         
/* 1388 */         processCancelRequest(paramCancelRequestMessage.getRequestId());
/* 1389 */         releaseByteBufferToPool();
/*      */       } finally {
/* 1391 */         setWorkThenReadOrResumeSelect((Message)paramCancelRequestMessage);
/*      */       } 
/* 1393 */     } catch (Throwable throwable) {
/* 1394 */       if (transportDebug()) dprint(".CANCEL: id/" + paramCancelRequestMessage
/* 1395 */             .getRequestId() + ": !!ERROR!!: " + paramCancelRequestMessage, throwable);
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1400 */       if (transportDebug()) dprint(".CANCEL<-: id/" + paramCancelRequestMessage
/* 1401 */             .getRequestId() + ": " + paramCancelRequestMessage
/* 1402 */             .getGIOPVersion() + ": " + paramCancelRequestMessage);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void throwNotImplemented() {
/* 1409 */     this.isThreadDone = false;
/* 1410 */     throwNotImplemented("");
/*      */   }
/*      */ 
/*      */   
/*      */   private void throwNotImplemented(String paramString) {
/* 1415 */     throw new RuntimeException("CorbaMessageMediatorImpl: not implemented " + paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   private void dprint(String paramString, Throwable paramThrowable) {
/* 1420 */     dprint(paramString);
/* 1421 */     paramThrowable.printStackTrace(System.out);
/*      */   }
/*      */ 
/*      */   
/*      */   private void dprint(String paramString) {
/* 1426 */     ORBUtility.dprint("CorbaMessageMediatorImpl", paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String opAndId(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1431 */     return ORBUtility.operationNameAndRequestId(paramCorbaMessageMediator);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean transportDebug() {
/* 1436 */     return this.orb.transportDebugFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final void processCancelRequest(int paramInt) {
/*      */     MessageMediator messageMediator;
/* 1474 */     if (!this.connection.isServer()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1483 */     CorbaMessageMediator corbaMessageMediator = this.connection.serverRequestMapGet(paramInt);
/*      */     
/* 1485 */     if (corbaMessageMediator == null) {
/*      */       
/* 1487 */       messageMediator = this.connection.serverRequest_1_1_Get();
/* 1488 */       if (messageMediator == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1497 */       int i = ((CorbaMessageMediator)messageMediator).getRequestId();
/*      */       
/* 1499 */       if (i != paramInt) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1505 */       if (i == 0)
/*      */       {
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 1519 */       int i = ((CorbaMessageMediator)messageMediator).getRequestId();
/*      */     } 
/*      */     
/* 1522 */     RequestMessage requestMessage = ((CorbaMessageMediator)messageMediator).getRequestHeader();
/* 1523 */     if (requestMessage.getType() != 0)
/*      */     {
/*      */       
/* 1526 */       this.wrapper.badMessageTypeForCancel();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1543 */     BufferManagerReadStream bufferManagerReadStream = (BufferManagerReadStream)((CDRInputObject)messageMediator.getInputObject()).getBufferManager();
/* 1544 */     bufferManagerReadStream.cancelProcessing(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleRequest(RequestMessage paramRequestMessage, CorbaMessageMediator paramCorbaMessageMediator) {
/*      */     
/* 1556 */     try { beginRequest(paramCorbaMessageMediator);
/*      */       try {
/* 1558 */         handleRequestRequest(paramCorbaMessageMediator);
/* 1559 */         if (paramCorbaMessageMediator.isOneWay()) {
/*      */           return;
/*      */         }
/* 1562 */       } catch (Throwable throwable) {
/* 1563 */         if (paramCorbaMessageMediator.isOneWay()) {
/*      */           return;
/*      */         }
/* 1566 */         handleThrowableDuringServerDispatch(paramCorbaMessageMediator, throwable, CompletionStatus.COMPLETED_MAYBE);
/*      */       } 
/*      */       
/* 1569 */       sendResponse(paramCorbaMessageMediator);
/*      */ 
/*      */ 
/*      */       
/* 1573 */       endRequest(paramCorbaMessageMediator); } catch (Throwable throwable) { dispatchError(paramCorbaMessageMediator, "RequestMessage", throwable); } finally { endRequest(paramCorbaMessageMediator); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleRequest(LocateRequestMessage paramLocateRequestMessage, CorbaMessageMediator paramCorbaMessageMediator) {
/*      */     try {
/* 1581 */       beginRequest(paramCorbaMessageMediator);
/*      */       try {
/* 1583 */         handleLocateRequest(paramCorbaMessageMediator);
/* 1584 */       } catch (Throwable throwable) {
/* 1585 */         handleThrowableDuringServerDispatch(paramCorbaMessageMediator, throwable, CompletionStatus.COMPLETED_MAYBE);
/*      */       } 
/*      */       
/* 1588 */       sendResponse(paramCorbaMessageMediator);
/* 1589 */     } catch (Throwable throwable) {
/* 1590 */       dispatchError(paramCorbaMessageMediator, "LocateRequestMessage", throwable);
/*      */     } finally {
/* 1592 */       endRequest(paramCorbaMessageMediator);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void beginRequest(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1598 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/* 1599 */     if (oRB.subcontractDebugFlag) {
/* 1600 */       dprint(".handleRequest->:");
/*      */     }
/* 1602 */     this.connection.serverRequestProcessingBegins();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void dispatchError(CorbaMessageMediator paramCorbaMessageMediator, String paramString, Throwable paramThrowable) {
/* 1608 */     if (this.orb.subcontractDebugFlag) {
/* 1609 */       dprint(".handleRequest: " + opAndId(paramCorbaMessageMediator) + ": !!ERROR!!: " + paramString, paramThrowable);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendResponse(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1620 */     if (this.orb.subcontractDebugFlag) {
/* 1621 */       dprint(".handleRequest: " + opAndId(paramCorbaMessageMediator) + ": sending response");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1626 */     CDROutputObject cDROutputObject = (CDROutputObject)paramCorbaMessageMediator.getOutputObject();
/* 1627 */     if (cDROutputObject != null)
/*      */     {
/* 1629 */       cDROutputObject.finishSendingMessage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void endRequest(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1635 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/* 1636 */     if (oRB.subcontractDebugFlag) {
/* 1637 */       dprint(".handleRequest<-: " + opAndId(paramCorbaMessageMediator));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1643 */       OutputObject outputObject = paramCorbaMessageMediator.getOutputObject();
/* 1644 */       if (outputObject != null) {
/* 1645 */         outputObject.close();
/*      */       }
/* 1647 */       InputObject inputObject = paramCorbaMessageMediator.getInputObject();
/* 1648 */       if (inputObject != null) {
/* 1649 */         inputObject.close();
/*      */       }
/* 1651 */     } catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */       
/* 1655 */       if (oRB.subcontractDebugFlag) {
/* 1656 */         dprint(".endRequest: IOException:" + iOException.getMessage(), iOException);
/*      */       }
/*      */     } finally {
/* 1659 */       ((CorbaConnection)paramCorbaMessageMediator.getConnection()).serverRequestProcessingEnds();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleRequestRequest(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1666 */     ((CDRInputObject)paramCorbaMessageMediator.getInputObject()).unmarshalHeader();
/*      */     
/* 1668 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/* 1669 */     synchronized (oRB) {
/* 1670 */       oRB.checkShutdownState();
/*      */     } 
/*      */     
/* 1673 */     ObjectKey objectKey = paramCorbaMessageMediator.getObjectKey();
/* 1674 */     if (oRB.subcontractDebugFlag) {
/* 1675 */       ObjectKeyTemplate objectKeyTemplate = objectKey.getTemplate();
/* 1676 */       dprint(".handleRequest: " + opAndId(paramCorbaMessageMediator) + ": dispatching to scid: " + objectKeyTemplate
/* 1677 */           .getSubcontractId());
/*      */     } 
/*      */     
/* 1680 */     CorbaServerRequestDispatcher corbaServerRequestDispatcher = objectKey.getServerRequestDispatcher(oRB);
/*      */     
/* 1682 */     if (oRB.subcontractDebugFlag) {
/* 1683 */       dprint(".handleRequest: " + opAndId(paramCorbaMessageMediator) + ": dispatching to sc: " + corbaServerRequestDispatcher);
/*      */     }
/*      */ 
/*      */     
/* 1687 */     if (corbaServerRequestDispatcher == null) {
/* 1688 */       throw this.wrapper.noServerScInDispatch();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1699 */       oRB.startingDispatch();
/* 1700 */       corbaServerRequestDispatcher.dispatch((MessageMediator)paramCorbaMessageMediator);
/*      */     } finally {
/* 1702 */       oRB.finishedDispatch();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleLocateRequest(CorbaMessageMediator paramCorbaMessageMediator) {
/* 1708 */     ORB oRB = (ORB)paramCorbaMessageMediator.getBroker();
/*      */     
/* 1710 */     LocateRequestMessage locateRequestMessage = (LocateRequestMessage)paramCorbaMessageMediator.getDispatchHeader();
/* 1711 */     IOR iOR = null;
/* 1712 */     LocateReplyMessage locateReplyMessage = null;
/* 1713 */     short s = -1;
/*      */     
/*      */     try {
/* 1716 */       ((CDRInputObject)paramCorbaMessageMediator.getInputObject()).unmarshalHeader();
/*      */       
/* 1718 */       CorbaServerRequestDispatcher corbaServerRequestDispatcher = locateRequestMessage.getObjectKey().getServerRequestDispatcher(oRB);
/* 1719 */       if (corbaServerRequestDispatcher == null) {
/*      */         return;
/*      */       }
/*      */       
/* 1723 */       iOR = corbaServerRequestDispatcher.locate(locateRequestMessage.getObjectKey());
/*      */       
/* 1725 */       if (iOR == null) {
/* 1726 */         locateReplyMessage = MessageBase.createLocateReply(oRB, locateRequestMessage
/* 1727 */             .getGIOPVersion(), locateRequestMessage
/* 1728 */             .getEncodingVersion(), locateRequestMessage
/* 1729 */             .getRequestId(), 1, null);
/*      */       }
/*      */       else {
/*      */         
/* 1733 */         locateReplyMessage = MessageBase.createLocateReply(oRB, locateRequestMessage
/* 1734 */             .getGIOPVersion(), locateRequestMessage
/* 1735 */             .getEncodingVersion(), locateRequestMessage
/* 1736 */             .getRequestId(), 2, iOR);
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1741 */     catch (AddressingDispositionException addressingDispositionException) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1746 */       locateReplyMessage = MessageBase.createLocateReply(oRB, locateRequestMessage
/* 1747 */           .getGIOPVersion(), locateRequestMessage
/* 1748 */           .getEncodingVersion(), locateRequestMessage
/* 1749 */           .getRequestId(), 5, null);
/*      */ 
/*      */       
/* 1752 */       s = addressingDispositionException.expectedAddrDisp();
/*      */     }
/* 1754 */     catch (RequestCanceledException requestCanceledException) {
/*      */       
/*      */       return;
/*      */     }
/* 1758 */     catch (Exception exception) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1766 */       locateReplyMessage = MessageBase.createLocateReply(oRB, locateRequestMessage
/* 1767 */           .getGIOPVersion(), locateRequestMessage
/* 1768 */           .getEncodingVersion(), locateRequestMessage
/* 1769 */           .getRequestId(), 0, null);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1774 */     CDROutputObject cDROutputObject = createAppropriateOutputObject(paramCorbaMessageMediator, (Message)locateRequestMessage, locateReplyMessage);
/*      */     
/* 1776 */     paramCorbaMessageMediator.setOutputObject((OutputObject)cDROutputObject);
/* 1777 */     cDROutputObject.setMessageMediator((MessageMediator)paramCorbaMessageMediator);
/*      */     
/* 1779 */     locateReplyMessage.write((OutputStream)cDROutputObject);
/*      */     
/* 1781 */     if (iOR != null) {
/* 1782 */       iOR.write((OutputStream)cDROutputObject);
/*      */     }
/* 1784 */     if (s != -1) {
/* 1785 */       AddressingDispositionHelper.write((OutputStream)cDROutputObject, s);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CDROutputObject createAppropriateOutputObject(CorbaMessageMediator paramCorbaMessageMediator, Message paramMessage, LocateReplyMessage paramLocateReplyMessage) {
/*      */     CDROutputObject cDROutputObject;
/* 1795 */     if (paramMessage.getGIOPVersion().lessThan(GIOPVersion.V1_2)) {
/*      */       
/* 1797 */       cDROutputObject = OutputStreamFactory.newCDROutputObject((ORB)paramCorbaMessageMediator
/* 1798 */           .getBroker(), this, GIOPVersion.V1_0, (CorbaConnection)paramCorbaMessageMediator
/*      */ 
/*      */           
/* 1801 */           .getConnection(), (Message)paramLocateReplyMessage, (byte)1);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1806 */       cDROutputObject = OutputStreamFactory.newCDROutputObject((ORB)paramCorbaMessageMediator
/* 1807 */           .getBroker(), (MessageMediator)paramCorbaMessageMediator, (Message)paramLocateReplyMessage, (byte)1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1812 */     return cDROutputObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleThrowableDuringServerDispatch(CorbaMessageMediator paramCorbaMessageMediator, Throwable paramThrowable, CompletionStatus paramCompletionStatus) {
/* 1820 */     if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 1821 */       dprint(".handleThrowableDuringServerDispatch: " + 
/* 1822 */           opAndId(paramCorbaMessageMediator) + ": " + paramThrowable);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1839 */     handleThrowableDuringServerDispatch(paramCorbaMessageMediator, paramThrowable, paramCompletionStatus, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleThrowableDuringServerDispatch(CorbaMessageMediator paramCorbaMessageMediator, Throwable paramThrowable, CompletionStatus paramCompletionStatus, int paramInt) {
/* 1854 */     if (paramInt > 10) {
/* 1855 */       if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 1856 */         dprint(".handleThrowableDuringServerDispatch: " + 
/* 1857 */             opAndId(paramCorbaMessageMediator) + ": cannot handle: " + paramThrowable);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1863 */       RuntimeException runtimeException = new RuntimeException("handleThrowableDuringServerDispatch: cannot create response.");
/*      */ 
/*      */       
/* 1866 */       runtimeException.initCause(paramThrowable);
/* 1867 */       throw runtimeException;
/*      */     } 
/*      */     
/*      */     try {
/* 1871 */       if (paramThrowable instanceof ForwardException) {
/* 1872 */         ForwardException forwardException = (ForwardException)paramThrowable;
/* 1873 */         createLocationForward(paramCorbaMessageMediator, forwardException.getIOR(), null);
/*      */         
/*      */         return;
/*      */       } 
/* 1877 */       if (paramThrowable instanceof AddressingDispositionException) {
/* 1878 */         handleAddressingDisposition(paramCorbaMessageMediator, (AddressingDispositionException)paramThrowable);
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1887 */       SystemException systemException = convertThrowableToSystemException(paramThrowable, paramCompletionStatus);
/*      */       
/* 1889 */       createSystemExceptionResponse(paramCorbaMessageMediator, systemException, null);
/*      */       
/*      */       return;
/* 1892 */     } catch (Throwable throwable) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1898 */       handleThrowableDuringServerDispatch(paramCorbaMessageMediator, throwable, paramCompletionStatus, paramInt + 1);
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SystemException convertThrowableToSystemException(Throwable paramThrowable, CompletionStatus paramCompletionStatus) {
/* 1910 */     if (paramThrowable instanceof SystemException) {
/* 1911 */       return (SystemException)paramThrowable;
/*      */     }
/*      */     
/* 1914 */     if (paramThrowable instanceof RequestCanceledException)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1920 */       return (SystemException)this.wrapper.requestCanceled(paramThrowable);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1936 */     return (SystemException)this.wrapper.runtimeexception(CompletionStatus.COMPLETED_MAYBE, paramThrowable);
/*      */   }
/*      */   
/*      */   protected void handleAddressingDisposition(CorbaMessageMediator paramCorbaMessageMediator, AddressingDispositionException paramAddressingDispositionException) {
/*      */     ReplyMessage replyMessage;
/*      */     CDROutputObject cDROutputObject;
/*      */     LocateReplyMessage locateReplyMessage;
/*      */     IOR iOR;
/* 1944 */     short s = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1950 */     switch (paramCorbaMessageMediator.getRequestHeader().getType()) {
/*      */       case 0:
/* 1952 */         replyMessage = MessageBase.createReply((ORB)paramCorbaMessageMediator
/* 1953 */             .getBroker(), paramCorbaMessageMediator
/* 1954 */             .getGIOPVersion(), paramCorbaMessageMediator
/* 1955 */             .getEncodingVersion(), paramCorbaMessageMediator
/* 1956 */             .getRequestId(), 5, null, null);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1961 */         cDROutputObject = OutputStreamFactory.newCDROutputObject((ORB)paramCorbaMessageMediator
/* 1962 */             .getBroker(), this, paramCorbaMessageMediator
/*      */             
/* 1964 */             .getGIOPVersion(), (CorbaConnection)paramCorbaMessageMediator
/* 1965 */             .getConnection(), (Message)replyMessage, (byte)1);
/*      */ 
/*      */         
/* 1968 */         paramCorbaMessageMediator.setOutputObject((OutputObject)cDROutputObject);
/* 1969 */         cDROutputObject.setMessageMediator((MessageMediator)paramCorbaMessageMediator);
/* 1970 */         replyMessage.write((OutputStream)cDROutputObject);
/* 1971 */         AddressingDispositionHelper.write((OutputStream)cDROutputObject, paramAddressingDispositionException
/* 1972 */             .expectedAddrDisp());
/*      */         return;
/*      */       
/*      */       case 3:
/* 1976 */         locateReplyMessage = MessageBase.createLocateReply((ORB)paramCorbaMessageMediator
/* 1977 */             .getBroker(), paramCorbaMessageMediator
/* 1978 */             .getGIOPVersion(), paramCorbaMessageMediator
/* 1979 */             .getEncodingVersion(), paramCorbaMessageMediator
/* 1980 */             .getRequestId(), 5, null);
/*      */ 
/*      */ 
/*      */         
/* 1984 */         s = paramAddressingDispositionException.expectedAddrDisp();
/*      */ 
/*      */ 
/*      */         
/* 1988 */         cDROutputObject = createAppropriateOutputObject(paramCorbaMessageMediator, (Message)paramCorbaMessageMediator
/* 1989 */             .getRequestHeader(), locateReplyMessage);
/*      */         
/* 1991 */         paramCorbaMessageMediator.setOutputObject((OutputObject)cDROutputObject);
/* 1992 */         cDROutputObject.setMessageMediator((MessageMediator)paramCorbaMessageMediator);
/* 1993 */         locateReplyMessage.write((OutputStream)cDROutputObject);
/* 1994 */         iOR = null;
/* 1995 */         if (iOR != null) {
/* 1996 */           iOR.write((OutputStream)cDROutputObject);
/*      */         }
/* 1998 */         if (s != -1) {
/* 1999 */           AddressingDispositionHelper.write((OutputStream)cDROutputObject, s);
/*      */         }
/*      */         return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator createResponse(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts) {
/* 2013 */     return createResponseHelper(paramCorbaMessageMediator, 
/*      */         
/* 2015 */         getServiceContextsForReply(paramCorbaMessageMediator, null));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator createUserExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts) {
/* 2022 */     return createResponseHelper(paramCorbaMessageMediator, 
/*      */         
/* 2024 */         getServiceContextsForReply(paramCorbaMessageMediator, null), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator createUnknownExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, UnknownException paramUnknownException) {
/* 2033 */     ServiceContexts serviceContexts = null;
/* 2034 */     UNKNOWN uNKNOWN = new UNKNOWN(0, CompletionStatus.COMPLETED_MAYBE);
/*      */     
/* 2036 */     serviceContexts = new ServiceContexts((ORB)paramCorbaMessageMediator.getBroker());
/* 2037 */     UEInfoServiceContext uEInfoServiceContext = new UEInfoServiceContext((Throwable)uNKNOWN);
/* 2038 */     serviceContexts.put((ServiceContext)uEInfoServiceContext);
/* 2039 */     return createSystemExceptionResponse(paramCorbaMessageMediator, (SystemException)uNKNOWN, serviceContexts);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator createSystemExceptionResponse(CorbaMessageMediator paramCorbaMessageMediator, SystemException paramSystemException, ServiceContexts paramServiceContexts) {
/* 2047 */     if (paramCorbaMessageMediator.getConnection() != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2059 */       CorbaMessageMediatorImpl corbaMessageMediatorImpl = (CorbaMessageMediatorImpl)((CorbaConnection)paramCorbaMessageMediator.getConnection()).serverRequestMapGet(paramCorbaMessageMediator.getRequestId());
/*      */       
/* 2061 */       OutputObject outputObject = null;
/* 2062 */       if (corbaMessageMediatorImpl != null) {
/* 2063 */         outputObject = corbaMessageMediatorImpl.getOutputObject();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2068 */       if (outputObject != null && corbaMessageMediatorImpl
/* 2069 */         .sentFragment() && 
/* 2070 */         !corbaMessageMediatorImpl.sentFullMessage())
/*      */       {
/* 2072 */         return corbaMessageMediatorImpl;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2079 */     if (paramCorbaMessageMediator.executePIInResponseConstructor())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2086 */       ((ORB)paramCorbaMessageMediator.getBroker()).getPIHandler().setServerPIInfo((Exception)paramSystemException);
/*      */     }
/*      */     
/* 2089 */     if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag && paramSystemException != null)
/*      */     {
/*      */       
/* 2092 */       dprint(".createSystemExceptionResponse: " + 
/* 2093 */           opAndId(paramCorbaMessageMediator), (Throwable)paramSystemException);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2098 */     ServiceContexts serviceContexts = getServiceContextsForReply(paramCorbaMessageMediator, paramServiceContexts);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2104 */     addExceptionDetailMessage(paramCorbaMessageMediator, paramSystemException, serviceContexts);
/*      */ 
/*      */     
/* 2107 */     CorbaMessageMediator corbaMessageMediator = createResponseHelper(paramCorbaMessageMediator, serviceContexts, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2113 */     ORBUtility.writeSystemException(paramSystemException, (OutputStream)corbaMessageMediator
/* 2114 */         .getOutputObject());
/*      */     
/* 2116 */     return corbaMessageMediator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addExceptionDetailMessage(CorbaMessageMediator paramCorbaMessageMediator, SystemException paramSystemException, ServiceContexts paramServiceContexts) {
/* 2123 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 2124 */     PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
/* 2125 */     paramSystemException.printStackTrace(printWriter);
/* 2126 */     printWriter.flush();
/*      */     
/* 2128 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream((ORB)paramCorbaMessageMediator.getBroker());
/* 2129 */     encapsOutputStream.putEndian();
/* 2130 */     encapsOutputStream.write_wstring(byteArrayOutputStream.toString());
/*      */ 
/*      */     
/* 2133 */     UnknownServiceContext unknownServiceContext = new UnknownServiceContext(14, encapsOutputStream.toByteArray());
/* 2134 */     paramServiceContexts.put((ServiceContext)unknownServiceContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CorbaMessageMediator createLocationForward(CorbaMessageMediator paramCorbaMessageMediator, IOR paramIOR, ServiceContexts paramServiceContexts) {
/* 2141 */     ReplyMessage replyMessage = MessageBase.createReply((ORB)paramCorbaMessageMediator
/* 2142 */         .getBroker(), paramCorbaMessageMediator
/* 2143 */         .getGIOPVersion(), paramCorbaMessageMediator
/* 2144 */         .getEncodingVersion(), paramCorbaMessageMediator
/* 2145 */         .getRequestId(), 3, 
/*      */         
/* 2147 */         getServiceContextsForReply(paramCorbaMessageMediator, paramServiceContexts), paramIOR);
/*      */ 
/*      */     
/* 2150 */     return createResponseHelper(paramCorbaMessageMediator, replyMessage, paramIOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CorbaMessageMediator createResponseHelper(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts) {
/* 2157 */     ReplyMessage replyMessage = MessageBase.createReply((ORB)paramCorbaMessageMediator
/* 2158 */         .getBroker(), paramCorbaMessageMediator
/* 2159 */         .getGIOPVersion(), paramCorbaMessageMediator
/* 2160 */         .getEncodingVersion(), paramCorbaMessageMediator
/* 2161 */         .getRequestId(), 0, paramServiceContexts, null);
/*      */ 
/*      */ 
/*      */     
/* 2165 */     return createResponseHelper(paramCorbaMessageMediator, replyMessage, (IOR)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CorbaMessageMediator createResponseHelper(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts, boolean paramBoolean) {
/* 2172 */     ReplyMessage replyMessage = MessageBase.createReply((ORB)paramCorbaMessageMediator
/* 2173 */         .getBroker(), paramCorbaMessageMediator
/* 2174 */         .getGIOPVersion(), paramCorbaMessageMediator
/* 2175 */         .getEncodingVersion(), paramCorbaMessageMediator
/* 2176 */         .getRequestId(), paramBoolean ? 1 : 2, paramServiceContexts, null);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2181 */     return createResponseHelper(paramCorbaMessageMediator, replyMessage, (IOR)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CorbaMessageMediator createResponseHelper(CorbaMessageMediator paramCorbaMessageMediator, ReplyMessage paramReplyMessage, IOR paramIOR) {
/*      */     OutputObject outputObject;
/* 2189 */     runServantPostInvoke(paramCorbaMessageMediator);
/* 2190 */     runInterceptors(paramCorbaMessageMediator, paramReplyMessage);
/* 2191 */     runRemoveThreadInfo(paramCorbaMessageMediator);
/*      */     
/* 2193 */     if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 2194 */       dprint(".createResponseHelper: " + 
/* 2195 */           opAndId(paramCorbaMessageMediator) + ": " + paramReplyMessage);
/*      */     }
/*      */ 
/*      */     
/* 2199 */     paramCorbaMessageMediator.setReplyHeader((LocateReplyOrReplyMessage)paramReplyMessage);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2204 */     if (paramCorbaMessageMediator.getConnection() == null) {
/*      */       
/* 2206 */       CDROutputObject cDROutputObject = OutputStreamFactory.newCDROutputObject(this.orb, (MessageMediator)paramCorbaMessageMediator, (Message)paramCorbaMessageMediator
/* 2207 */           .getReplyHeader(), paramCorbaMessageMediator
/* 2208 */           .getStreamFormatVersion(), 0);
/*      */     }
/*      */     else {
/*      */       
/* 2212 */       outputObject = paramCorbaMessageMediator.getConnection().getAcceptor().createOutputObject(paramCorbaMessageMediator.getBroker(), (MessageMediator)paramCorbaMessageMediator);
/*      */     } 
/* 2214 */     paramCorbaMessageMediator.setOutputObject(outputObject);
/* 2215 */     paramCorbaMessageMediator.getOutputObject().setMessageMediator((MessageMediator)paramCorbaMessageMediator);
/*      */     
/* 2217 */     paramReplyMessage.write((OutputStream)paramCorbaMessageMediator.getOutputObject());
/* 2218 */     if (paramReplyMessage.getIOR() != null) {
/* 2219 */       paramReplyMessage.getIOR().write((OutputStream)paramCorbaMessageMediator.getOutputObject());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2226 */     return paramCorbaMessageMediator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void runServantPostInvoke(CorbaMessageMediator paramCorbaMessageMediator) {
/* 2236 */     ORB oRB = null;
/*      */ 
/*      */     
/* 2239 */     if (paramCorbaMessageMediator.executeReturnServantInResponseConstructor()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2245 */       paramCorbaMessageMediator.setExecuteReturnServantInResponseConstructor(false);
/* 2246 */       paramCorbaMessageMediator.setExecuteRemoveThreadInfoInResponseConstructor(true);
/*      */       
/*      */       try {
/* 2249 */         oRB = (ORB)paramCorbaMessageMediator.getBroker();
/* 2250 */         OAInvocationInfo oAInvocationInfo = oRB.peekInvocationInfo();
/* 2251 */         ObjectAdapter objectAdapter = oAInvocationInfo.oa();
/*      */         try {
/* 2253 */           objectAdapter.returnServant();
/* 2254 */         } catch (Throwable throwable) {
/* 2255 */           this.wrapper.unexpectedException(throwable);
/*      */           
/* 2257 */           if (throwable instanceof Error)
/* 2258 */             throw (Error)throwable; 
/* 2259 */           if (throwable instanceof RuntimeException)
/* 2260 */             throw (RuntimeException)throwable; 
/*      */         } finally {
/* 2262 */           objectAdapter.exit();
/*      */         } 
/* 2264 */       } catch (EmptyStackException emptyStackException) {
/* 2265 */         throw this.wrapper.emptyStackRunServantPostInvoke(emptyStackException);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void runInterceptors(CorbaMessageMediator paramCorbaMessageMediator, ReplyMessage paramReplyMessage) {
/* 2273 */     if (paramCorbaMessageMediator.executePIInResponseConstructor()) {
/*      */ 
/*      */ 
/*      */       
/* 2277 */       ((ORB)paramCorbaMessageMediator.getBroker()).getPIHandler()
/* 2278 */         .invokeServerPIEndingPoint(paramReplyMessage);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2283 */       ((ORB)paramCorbaMessageMediator.getBroker()).getPIHandler()
/* 2284 */         .cleanupServerPIRequest();
/*      */ 
/*      */       
/* 2287 */       paramCorbaMessageMediator.setExecutePIInResponseConstructor(false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void runRemoveThreadInfo(CorbaMessageMediator paramCorbaMessageMediator) {
/* 2295 */     if (paramCorbaMessageMediator.executeRemoveThreadInfoInResponseConstructor()) {
/* 2296 */       paramCorbaMessageMediator.setExecuteRemoveThreadInfoInResponseConstructor(false);
/* 2297 */       ((ORB)paramCorbaMessageMediator.getBroker()).popInvocationInfo();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ServiceContexts getServiceContextsForReply(CorbaMessageMediator paramCorbaMessageMediator, ServiceContexts paramServiceContexts) {
/* 2304 */     CorbaConnection corbaConnection = (CorbaConnection)paramCorbaMessageMediator.getConnection();
/*      */     
/* 2306 */     if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 2307 */       dprint(".getServiceContextsForReply: " + 
/* 2308 */           opAndId(paramCorbaMessageMediator) + ": " + corbaConnection);
/*      */     }
/*      */ 
/*      */     
/* 2312 */     if (paramServiceContexts == null) {
/* 2313 */       paramServiceContexts = new ServiceContexts((ORB)paramCorbaMessageMediator.getBroker());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2318 */     if (corbaConnection != null && !corbaConnection.isPostInitialContexts()) {
/* 2319 */       corbaConnection.setPostInitialContexts();
/*      */ 
/*      */       
/* 2322 */       SendingContextServiceContext sendingContextServiceContext = new SendingContextServiceContext(((ORB)paramCorbaMessageMediator.getBroker()).getFVDCodeBaseIOR());
/*      */       
/* 2324 */       if (paramServiceContexts.get(sendingContextServiceContext.getId()) != null) {
/* 2325 */         throw this.wrapper.duplicateSendingContextServiceContext();
/*      */       }
/* 2327 */       paramServiceContexts.put((ServiceContext)sendingContextServiceContext);
/*      */       
/* 2329 */       if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 2330 */         dprint(".getServiceContextsForReply: " + 
/* 2331 */             opAndId(paramCorbaMessageMediator) + ": added SendingContextServiceContext");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2338 */     ORBVersionServiceContext oRBVersionServiceContext = new ORBVersionServiceContext(ORBVersionFactory.getORBVersion());
/*      */     
/* 2340 */     if (paramServiceContexts.get(oRBVersionServiceContext.getId()) != null) {
/* 2341 */       throw this.wrapper.duplicateOrbVersionServiceContext();
/*      */     }
/* 2343 */     paramServiceContexts.put((ServiceContext)oRBVersionServiceContext);
/*      */     
/* 2345 */     if (((ORB)paramCorbaMessageMediator.getBroker()).subcontractDebugFlag) {
/* 2346 */       dprint(".getServiceContextsForReply: " + 
/* 2347 */           opAndId(paramCorbaMessageMediator) + ": added ORB version service context");
/*      */     }
/*      */     
/* 2350 */     return paramServiceContexts;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void releaseByteBufferToPool() {
/* 2357 */     if (this.dispatchByteBuffer != null) {
/* 2358 */       this.orb.getByteBufferPool().releaseByteBuffer(this.dispatchByteBuffer);
/* 2359 */       if (transportDebug()) {
/* 2360 */         int i = System.identityHashCode(this.dispatchByteBuffer);
/* 2361 */         StringBuffer stringBuffer = new StringBuffer();
/* 2362 */         stringBuffer.append(".handleInput: releasing ByteBuffer (" + i + ") to ByteBufferPool");
/*      */         
/* 2364 */         dprint(stringBuffer.toString());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/CorbaMessageMediatorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */