/*     */ package com.sun.corba.se.spi.activation;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class _ActivatorImplBase
/*     */   extends ObjectImpl
/*     */   implements Activator, InvokeHandler
/*     */ {
/*  20 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  23 */     _methods.put("active", new Integer(0));
/*  24 */     _methods.put("registerEndpoints", new Integer(1));
/*  25 */     _methods.put("getActiveServers", new Integer(2));
/*  26 */     _methods.put("activate", new Integer(3));
/*  27 */     _methods.put("shutdown", new Integer(4));
/*  28 */     _methods.put("install", new Integer(5));
/*  29 */     _methods.put("getORBNames", new Integer(6));
/*  30 */     _methods.put("uninstall", new Integer(7));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     int[] arrayOfInt;
/*  37 */     OutputStream outputStream = null;
/*  38 */     Integer integer = (Integer)_methods.get(paramString);
/*  39 */     if (integer == null) {
/*  40 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  42 */     switch (integer.intValue()) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  49 */           int i = ServerIdHelper.read(paramInputStream);
/*  50 */           Server server = ServerHelper.read(paramInputStream);
/*  51 */           active(i, server);
/*  52 */           outputStream = paramResponseHandler.createReply();
/*  53 */         } catch (ServerNotRegistered serverNotRegistered) {
/*  54 */           outputStream = paramResponseHandler.createExceptionReply();
/*  55 */           ServerNotRegisteredHelper.write(outputStream, serverNotRegistered);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 196 */         return outputStream;case 1: try { int i = ServerIdHelper.read(paramInputStream); String str = ORBidHelper.read(paramInputStream); EndPointInfo[] arrayOfEndPointInfo = EndpointInfoListHelper.read(paramInputStream); registerEndpoints(i, str, arrayOfEndPointInfo); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); } catch (ORBAlreadyRegistered oRBAlreadyRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ORBAlreadyRegisteredHelper.write(outputStream, oRBAlreadyRegistered); }  return outputStream;case 2: arrayOfInt = null; arrayOfInt = getActiveServers(); outputStream = paramResponseHandler.createReply(); ServerIdsHelper.write(outputStream, arrayOfInt); return outputStream;case 3: try { int i = ServerIdHelper.read(paramInputStream); activate(i); outputStream = paramResponseHandler.createReply(); } catch (ServerAlreadyActive serverAlreadyActive) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyActiveHelper.write(outputStream, serverAlreadyActive); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); }  return outputStream;case 4: try { int i = ServerIdHelper.read(paramInputStream); shutdown(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotActive serverNotActive) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotActiveHelper.write(outputStream, serverNotActive); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 5: try { int i = ServerIdHelper.read(paramInputStream); install(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); } catch (ServerAlreadyInstalled serverAlreadyInstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyInstalledHelper.write(outputStream, serverAlreadyInstalled); }  return outputStream;case 6: try { int i = ServerIdHelper.read(paramInputStream); String[] arrayOfString = null; arrayOfString = getORBNames(i); outputStream = paramResponseHandler.createReply(); ORBidListHelper.write(outputStream, arrayOfString); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 7: try { int i = ServerIdHelper.read(paramInputStream); uninstall(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); } catch (ServerAlreadyUninstalled serverAlreadyUninstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyUninstalledHelper.write(outputStream, serverAlreadyUninstalled); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 200 */   private static String[] __ids = new String[] { "IDL:activation/Activator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 205 */     return (String[])__ids.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_ActivatorImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */