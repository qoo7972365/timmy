/*     */ package com.sun.corba.se.impl.protocol;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.MarshalInputStream;
/*     */ import com.sun.corba.se.impl.encoding.MarshalOutputStream;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.pept.protocol.MessageMediator;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.ObjectKey;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import com.sun.corba.se.spi.protocol.CorbaServerRequestDispatcher;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.Object;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BootstrapServerRequestDispatcher
/*     */   implements CorbaServerRequestDispatcher
/*     */ {
/*     */   private ORB orb;
/*     */   ORBUtilSystemException wrapper;
/*     */   private static final boolean debug = false;
/*     */   
/*     */   public BootstrapServerRequestDispatcher(ORB paramORB) {
/*  71 */     this.orb = paramORB;
/*  72 */     this.wrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispatch(MessageMediator paramMessageMediator) {
/*  82 */     CorbaMessageMediator corbaMessageMediator1 = (CorbaMessageMediator)paramMessageMediator;
/*  83 */     CorbaMessageMediator corbaMessageMediator2 = null;
/*     */ 
/*     */     
/*     */     try {
/*  87 */       MarshalInputStream marshalInputStream = (MarshalInputStream)corbaMessageMediator1.getInputObject();
/*  88 */       String str = corbaMessageMediator1.getOperationName();
/*  89 */       corbaMessageMediator2 = corbaMessageMediator1.getProtocolHandler().createResponse(corbaMessageMediator1, null);
/*     */       
/*  91 */       MarshalOutputStream marshalOutputStream = (MarshalOutputStream)corbaMessageMediator2.getOutputObject();
/*     */       
/*  93 */       if (str.equals("get")) {
/*     */         
/*  95 */         String str1 = marshalInputStream.read_string();
/*     */ 
/*     */ 
/*     */         
/*  99 */         Object object = this.orb.getLocalResolver().resolve(str1);
/*     */ 
/*     */         
/* 102 */         marshalOutputStream.write_Object(object);
/* 103 */       } else if (str.equals("list")) {
/* 104 */         Set set = this.orb.getLocalResolver().list();
/* 105 */         marshalOutputStream.write_long(set.size());
/* 106 */         Iterator<String> iterator = set.iterator();
/* 107 */         while (iterator.hasNext()) {
/* 108 */           String str1 = iterator.next();
/* 109 */           marshalOutputStream.write_string(str1);
/*     */         } 
/*     */       } else {
/* 112 */         throw this.wrapper.illegalBootstrapOperation(str);
/*     */       }
/*     */     
/* 115 */     } catch (SystemException systemException) {
/*     */       
/* 117 */       corbaMessageMediator2 = corbaMessageMediator1.getProtocolHandler().createSystemExceptionResponse(corbaMessageMediator1, systemException, null);
/*     */     }
/* 119 */     catch (RuntimeException runtimeException) {
/*     */       
/* 121 */       BAD_PARAM bAD_PARAM = this.wrapper.bootstrapRuntimeException(runtimeException);
/* 122 */       corbaMessageMediator2 = corbaMessageMediator1.getProtocolHandler().createSystemExceptionResponse(corbaMessageMediator1, (SystemException)bAD_PARAM, null);
/*     */     }
/* 124 */     catch (Exception exception) {
/*     */       
/* 126 */       BAD_PARAM bAD_PARAM = this.wrapper.bootstrapException(exception);
/* 127 */       corbaMessageMediator2 = corbaMessageMediator1.getProtocolHandler().createSystemExceptionResponse(corbaMessageMediator1, (SystemException)bAD_PARAM, null);
/*     */     } 
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
/*     */   public IOR locate(ObjectKey paramObjectKey) {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 147 */     throw this.wrapper.genericNoImpl();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/BootstrapServerRequestDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */