/*     */ package com.sun.corba.se.spi.activation;
/*     */ 
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationHelper;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORB;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORBHelper;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ 
/*     */ public abstract class _LocatorImplBase
/*     */   extends ObjectImpl
/*     */   implements Locator, InvokeHandler
/*     */ {
/*  20 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  23 */     _methods.put("locateServer", new Integer(0));
/*  24 */     _methods.put("locateServerForORB", new Integer(1));
/*  25 */     _methods.put("getEndpoint", new Integer(2));
/*  26 */     _methods.put("getServerPortForType", new Integer(3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*  33 */     OutputStream outputStream = null;
/*  34 */     Integer integer = (Integer)_methods.get(paramString);
/*  35 */     if (integer == null) {
/*  36 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  38 */     switch (integer.intValue()) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  45 */           int i = ServerIdHelper.read(paramInputStream);
/*  46 */           String str = paramInputStream.read_string();
/*  47 */           ServerLocation serverLocation = null;
/*  48 */           serverLocation = locateServer(i, str);
/*  49 */           outputStream = paramResponseHandler.createReply();
/*  50 */           ServerLocationHelper.write(outputStream, serverLocation);
/*  51 */         } catch (NoSuchEndPoint noSuchEndPoint) {
/*  52 */           outputStream = paramResponseHandler.createExceptionReply();
/*  53 */           NoSuchEndPointHelper.write(outputStream, noSuchEndPoint);
/*  54 */         } catch (ServerNotRegistered serverNotRegistered) {
/*  55 */           outputStream = paramResponseHandler.createExceptionReply();
/*  56 */           ServerNotRegisteredHelper.write(outputStream, serverNotRegistered);
/*  57 */         } catch (ServerHeldDown serverHeldDown) {
/*  58 */           outputStream = paramResponseHandler.createExceptionReply();
/*  59 */           ServerHeldDownHelper.write(outputStream, serverHeldDown);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 127 */         return outputStream;case 1: try { int i = ServerIdHelper.read(paramInputStream); String str = ORBidHelper.read(paramInputStream); ServerLocationPerORB serverLocationPerORB = null; serverLocationPerORB = locateServerForORB(i, str); outputStream = paramResponseHandler.createReply(); ServerLocationPerORBHelper.write(outputStream, serverLocationPerORB); } catch (InvalidORBid invalidORBid) { outputStream = paramResponseHandler.createExceptionReply(); InvalidORBidHelper.write(outputStream, invalidORBid); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); }  return outputStream;case 2: try { String str = paramInputStream.read_string(); int i = 0; i = getEndpoint(str); outputStream = paramResponseHandler.createReply(); outputStream.write_long(i); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); }  return outputStream;case 3: try { ServerLocationPerORB serverLocationPerORB = ServerLocationPerORBHelper.read(paramInputStream); String str = paramInputStream.read_string(); int i = 0; i = getServerPortForType(serverLocationPerORB, str); outputStream = paramResponseHandler.createReply(); outputStream.write_long(i); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 131 */   private static String[] __ids = new String[] { "IDL:activation/Locator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 136 */     return (String[])__ids.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_LocatorImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */