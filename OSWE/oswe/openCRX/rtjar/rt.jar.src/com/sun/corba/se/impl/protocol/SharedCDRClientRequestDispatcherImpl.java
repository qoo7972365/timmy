/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
/*     */ import com.sun.corba.se.impl.encoding.CDRInputObject;
/*     */ import com.sun.corba.se.impl.encoding.CDROutputObject;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
/*     */ import com.sun.corba.se.pept.encoding.InputObject;
/*     */ import com.sun.corba.se.pept.encoding.OutputObject;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.RemarshalException;
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
/*     */ public class SharedCDRClientRequestDispatcherImpl
/*     */   extends CorbaClientRequestDispatcherImpl
/*     */ {
/*     */   public InputObject marshalingComplete(Object paramObject, OutputObject paramOutputObject) throws ApplicationException, RemarshalException {
/* 141 */     ORB oRB = null;
/* 142 */     CorbaMessageMediator corbaMessageMediator = null;
/*     */     
/*     */     try {
/* 145 */       corbaMessageMediator = (CorbaMessageMediator)paramOutputObject.getMessageMediator();
/*     */       
/* 147 */       oRB = (ORB)corbaMessageMediator.getBroker();
/*     */       
/* 149 */       if (oRB.subcontractDebugFlag) {
/* 150 */         dprint(".marshalingComplete->: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */       
/* 153 */       CDROutputObject cDROutputObject = (CDROutputObject)paramOutputObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       ByteBufferWithInfo byteBufferWithInfo = cDROutputObject.getByteBufferWithInfo();
/* 160 */       cDROutputObject.getMessageHeader().setSize(byteBufferWithInfo.byteBuffer, byteBufferWithInfo.getSize());
/* 161 */       final ORB inOrb = oRB;
/* 162 */       final ByteBuffer inBuffer = byteBufferWithInfo.byteBuffer;
/* 163 */       final Message inMsg = cDROutputObject.getMessageHeader();
/*     */       
/* 165 */       CDRInputObject cDRInputObject1 = AccessController.<CDRInputObject>doPrivileged(new PrivilegedAction<CDRInputObject>()
/*     */           {
/*     */             public CDRInputObject run() {
/* 168 */               return new CDRInputObject(inOrb, null, inBuffer, inMsg);
/*     */             }
/*     */           });
/*     */       
/* 172 */       corbaMessageMediator.setInputObject((InputObject)cDRInputObject1);
/* 173 */       cDRInputObject1.setMessageMediator((MessageMediator)corbaMessageMediator);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       ((CorbaMessageMediatorImpl)corbaMessageMediator).handleRequestRequest(corbaMessageMediator);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 186 */         cDRInputObject1.close();
/* 187 */       } catch (IOException iOException) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 192 */         if (oRB.transportDebugFlag) {
/* 193 */           dprint(".marshalingComplete: ignoring IOException - " + iOException.toString());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       cDROutputObject = (CDROutputObject)corbaMessageMediator.getOutputObject();
/* 202 */       byteBufferWithInfo = cDROutputObject.getByteBufferWithInfo();
/* 203 */       cDROutputObject.getMessageHeader().setSize(byteBufferWithInfo.byteBuffer, byteBufferWithInfo.getSize());
/* 204 */       final ORB inOrb2 = oRB;
/* 205 */       final ByteBuffer inBuffer2 = byteBufferWithInfo.byteBuffer;
/* 206 */       final Message inMsg2 = cDROutputObject.getMessageHeader();
/*     */       
/* 208 */       cDRInputObject1 = AccessController.<CDRInputObject>doPrivileged(new PrivilegedAction<CDRInputObject>()
/*     */           {
/*     */             public CDRInputObject run() {
/* 211 */               return new CDRInputObject(inOrb2, null, inBuffer2, inMsg2);
/*     */             }
/*     */           });
/*     */       
/* 215 */       corbaMessageMediator.setInputObject((InputObject)cDRInputObject1);
/* 216 */       cDRInputObject1.setMessageMediator((MessageMediator)corbaMessageMediator);
/*     */       
/* 218 */       cDRInputObject1.unmarshalHeader();
/*     */       
/* 220 */       CDRInputObject cDRInputObject2 = cDRInputObject1;
/*     */       
/* 222 */       return processResponse(oRB, corbaMessageMediator, (InputObject)cDRInputObject2);
/*     */     } finally {
/*     */       
/* 225 */       if (oRB.subcontractDebugFlag) {
/* 226 */         dprint(".marshalingComplete<-: " + opAndId(corbaMessageMediator));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dprint(String paramString) {
/* 233 */     ORBUtility.dprint("SharedCDRClientRequestDispatcherImpl", paramString);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/SharedCDRClientRequestDispatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */