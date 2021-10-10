/*     */ package com.sun.corba.se.impl.transport;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.BufferManagerReadStream;
/*     */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.LocateReplyOrReplyMessage;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.transport.CorbaConnection;
/*     */ import com.sun.corba.se.spi.transport.CorbaResponseWaitingRoom;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.SystemException;
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
/*     */ public class CorbaResponseWaitingRoomImpl
/*     */   implements CorbaResponseWaitingRoom
/*     */ {
/*     */   private ORB orb;
/*     */   private ORBUtilSystemException wrapper;
/*     */   private CorbaConnection connection;
/*     */   private final Map<Integer, OutCallDesc> out_calls;
/*     */   
/*     */   static final class OutCallDesc
/*     */   {
/*  62 */     Object done = new Object();
/*     */ 
/*     */     
/*     */     Thread thread;
/*     */ 
/*     */     
/*     */     MessageMediator messageMediator;
/*     */ 
/*     */     
/*     */     SystemException exception;
/*     */     
/*     */     InputObject inputObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public CorbaResponseWaitingRoomImpl(ORB paramORB, CorbaConnection paramCorbaConnection) {
/*  78 */     this.orb = paramORB;
/*  79 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.transport");
/*     */     
/*  81 */     this.connection = paramCorbaConnection;
/*  82 */     this
/*  83 */       .out_calls = Collections.synchronizedMap(new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerWaiter(MessageMediator paramMessageMediator) {
/*  93 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     
/*  95 */     if (this.orb.transportDebugFlag) {
/*  96 */       dprint(".registerWaiter: " + opAndId(corbaMessageMediator));
/*     */     }
/*     */     
/*  99 */     Integer integer = corbaMessageMediator.getRequestIdInteger();
/*     */     
/* 101 */     OutCallDesc outCallDesc = new OutCallDesc();
/* 102 */     outCallDesc.thread = Thread.currentThread();
/* 103 */     outCallDesc.messageMediator = (MessageMediator)corbaMessageMediator;
/* 104 */     this.out_calls.put(integer, outCallDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterWaiter(MessageMediator paramMessageMediator) {
/* 109 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */     
/* 111 */     if (this.orb.transportDebugFlag) {
/* 112 */       dprint(".unregisterWaiter: " + opAndId(corbaMessageMediator));
/*     */     }
/*     */     
/* 115 */     Integer integer = corbaMessageMediator.getRequestIdInteger();
/*     */     
/* 117 */     this.out_calls.remove(integer);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputObject waitForResponse(MessageMediator paramMessageMediator) {
/* 122 */     CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)paramMessageMediator;
/*     */ 
/*     */     
/*     */     try {
/* 126 */       InputObject inputObject = null;
/*     */       
/* 128 */       if (this.orb.transportDebugFlag) {
/* 129 */         dprint(".waitForResponse->: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */       
/* 132 */       Integer integer = corbaMessageMediator.getRequestIdInteger();
/*     */       
/* 134 */       if (corbaMessageMediator.isOneWay()) {
/*     */ 
/*     */ 
/*     */         
/* 138 */         if (this.orb.transportDebugFlag) {
/* 139 */           dprint(".waitForResponse: one way - not waiting: " + 
/* 140 */               opAndId(corbaMessageMediator));
/*     */         }
/*     */         
/* 143 */         return null;
/*     */       } 
/*     */       
/* 146 */       OutCallDesc outCallDesc = this.out_calls.get(integer);
/* 147 */       if (outCallDesc == null) {
/* 148 */         throw this.wrapper.nullOutCall(CompletionStatus.COMPLETED_MAYBE);
/*     */       }
/*     */       
/* 151 */       synchronized (outCallDesc.done) {
/*     */         
/* 153 */         while (outCallDesc.inputObject == null && outCallDesc.exception == null) {
/*     */ 
/*     */           
/*     */           try {
/*     */             
/* 158 */             if (this.orb.transportDebugFlag) {
/* 159 */               dprint(".waitForResponse: waiting: " + 
/* 160 */                   opAndId(corbaMessageMediator));
/*     */             }
/* 162 */             outCallDesc.done.wait();
/* 163 */           } catch (InterruptedException interruptedException) {}
/*     */         } 
/*     */         
/* 166 */         if (outCallDesc.exception != null) {
/* 167 */           if (this.orb.transportDebugFlag) {
/* 168 */             dprint(".waitForResponse: exception: " + 
/* 169 */                 opAndId(corbaMessageMediator));
/*     */           }
/* 171 */           throw outCallDesc.exception;
/*     */         } 
/*     */         
/* 174 */         inputObject = outCallDesc.inputObject;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (inputObject != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         ((CDRInputObject)inputObject).unmarshalHeader();
/*     */       }
/*     */       
/* 189 */       return inputObject;
/*     */     } finally {
/*     */       
/* 192 */       if (this.orb.transportDebugFlag) {
/* 193 */         dprint(".waitForResponse<-: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void responseReceived(InputObject paramInputObject) {
/* 200 */     CDRInputObject cDRInputObject = (CDRInputObject)paramInputObject;
/*     */     
/* 202 */     LocateReplyOrReplyMessage locateReplyOrReplyMessage = (LocateReplyOrReplyMessage)cDRInputObject.getMessageHeader();
/* 203 */     Integer integer = new Integer(locateReplyOrReplyMessage.getRequestId());
/* 204 */     OutCallDesc outCallDesc = this.out_calls.get(integer);
/*     */     
/* 206 */     if (this.orb.transportDebugFlag) {
/* 207 */       dprint(".responseReceived: id/" + integer + ": " + locateReplyOrReplyMessage);
/*     */     }
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
/* 220 */     if (outCallDesc == null) {
/* 221 */       if (this.orb.transportDebugFlag) {
/* 222 */         dprint(".responseReceived: id/" + integer + ": no waiter: " + locateReplyOrReplyMessage);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     synchronized (outCallDesc.done) {
/* 236 */       CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)outCallDesc.messageMediator;
/*     */ 
/*     */       
/* 239 */       if (this.orb.transportDebugFlag) {
/* 240 */         dprint(".responseReceived: " + 
/* 241 */             opAndId(corbaMessageMediator) + ": notifying waiters");
/*     */       }
/*     */ 
/*     */       
/* 245 */       corbaMessageMediator.setReplyHeader(locateReplyOrReplyMessage);
/* 246 */       corbaMessageMediator.setInputObject(paramInputObject);
/* 247 */       cDRInputObject.setMessageMediator((MessageMediator)corbaMessageMediator);
/* 248 */       outCallDesc.inputObject = paramInputObject;
/* 249 */       outCallDesc.done.notify();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int numberRegistered() {
/* 255 */     return this.out_calls.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void signalExceptionToAllWaiters(SystemException paramSystemException) {
/* 266 */     if (this.orb.transportDebugFlag) {
/* 267 */       dprint(".signalExceptionToAllWaiters: " + paramSystemException);
/*     */     }
/*     */     
/* 270 */     synchronized (this.out_calls) {
/* 271 */       if (this.orb.transportDebugFlag) {
/* 272 */         dprint(".signalExceptionToAllWaiters: out_calls size :" + this.out_calls
/* 273 */             .size());
/*     */       }
/*     */       
/* 276 */       for (OutCallDesc outCallDesc : this.out_calls.values()) {
/* 277 */         if (this.orb.transportDebugFlag) {
/* 278 */           dprint(".signalExceptionToAllWaiters: signaling " + outCallDesc);
/*     */         }
/*     */         
/* 281 */         synchronized (outCallDesc.done) {
/*     */ 
/*     */           
/*     */           try {
/* 285 */             CorbaMessageMediator corbaMessageMediator = (CorbaMessageMediator)outCallDesc.messageMediator;
/*     */ 
/*     */             
/* 288 */             CDRInputObject cDRInputObject = (CDRInputObject)corbaMessageMediator.getInputObject();
/*     */ 
/*     */             
/* 291 */             if (cDRInputObject != null) {
/*     */               
/* 293 */               BufferManagerReadStream bufferManagerReadStream = (BufferManagerReadStream)cDRInputObject.getBufferManager();
/* 294 */               int i = corbaMessageMediator.getRequestId();
/* 295 */               bufferManagerReadStream.cancelProcessing(i);
/*     */             } 
/* 297 */           } catch (Exception exception) {
/*     */           
/*     */           } finally {
/* 300 */             outCallDesc.inputObject = null;
/* 301 */             outCallDesc.exception = paramSystemException;
/* 302 */             outCallDesc.done.notifyAll();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageMediator getMessageMediator(int paramInt) {
/* 311 */     Integer integer = new Integer(paramInt);
/* 312 */     OutCallDesc outCallDesc = this.out_calls.get(integer);
/* 313 */     if (outCallDesc == null)
/*     */     {
/*     */       
/* 316 */       return null;
/*     */     }
/* 318 */     return outCallDesc.messageMediator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 328 */     ORBUtility.dprint("CorbaResponseWaitingRoomImpl", paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String opAndId(CorbaMessageMediator paramCorbaMessageMediator) {
/* 333 */     return ORBUtility.operationNameAndRequestId(paramCorbaMessageMediator);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/transport/CorbaResponseWaitingRoomImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */