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
/*     */ public abstract class _ServerManagerImplBase
/*     */   extends ObjectImpl
/*     */   implements ServerManager, InvokeHandler
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
/*  31 */     _methods.put("locateServer", new Integer(8));
/*  32 */     _methods.put("locateServerForORB", new Integer(9));
/*  33 */     _methods.put("getEndpoint", new Integer(10));
/*  34 */     _methods.put("getServerPortForType", new Integer(11));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     int[] arrayOfInt;
/*  41 */     OutputStream outputStream = null;
/*  42 */     Integer integer = (Integer)_methods.get(paramString);
/*  43 */     if (integer == null) {
/*  44 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  46 */     switch (integer.intValue()) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  53 */           int i = ServerIdHelper.read(paramInputStream);
/*  54 */           Server server = ServerHelper.read(paramInputStream);
/*  55 */           active(i, server);
/*  56 */           outputStream = paramResponseHandler.createReply();
/*  57 */         } catch (ServerNotRegistered serverNotRegistered) {
/*  58 */           outputStream = paramResponseHandler.createExceptionReply();
/*  59 */           ServerNotRegisteredHelper.write(outputStream, serverNotRegistered);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 283 */         return outputStream;case 1: try { int i = ServerIdHelper.read(paramInputStream); String str = ORBidHelper.read(paramInputStream); EndPointInfo[] arrayOfEndPointInfo = EndpointInfoListHelper.read(paramInputStream); registerEndpoints(i, str, arrayOfEndPointInfo); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); } catch (ORBAlreadyRegistered oRBAlreadyRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ORBAlreadyRegisteredHelper.write(outputStream, oRBAlreadyRegistered); }  return outputStream;case 2: arrayOfInt = null; arrayOfInt = getActiveServers(); outputStream = paramResponseHandler.createReply(); ServerIdsHelper.write(outputStream, arrayOfInt); return outputStream;case 3: try { int i = ServerIdHelper.read(paramInputStream); activate(i); outputStream = paramResponseHandler.createReply(); } catch (ServerAlreadyActive serverAlreadyActive) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyActiveHelper.write(outputStream, serverAlreadyActive); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); }  return outputStream;case 4: try { int i = ServerIdHelper.read(paramInputStream); shutdown(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotActive serverNotActive) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotActiveHelper.write(outputStream, serverNotActive); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 5: try { int i = ServerIdHelper.read(paramInputStream); install(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); } catch (ServerAlreadyInstalled serverAlreadyInstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyInstalledHelper.write(outputStream, serverAlreadyInstalled); }  return outputStream;case 6: try { int i = ServerIdHelper.read(paramInputStream); String[] arrayOfString = null; arrayOfString = getORBNames(i); outputStream = paramResponseHandler.createReply(); ORBidListHelper.write(outputStream, arrayOfString); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 7: try { int i = ServerIdHelper.read(paramInputStream); uninstall(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); } catch (ServerAlreadyUninstalled serverAlreadyUninstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyUninstalledHelper.write(outputStream, serverAlreadyUninstalled); }  return outputStream;case 8: try { int i = ServerIdHelper.read(paramInputStream); String str = paramInputStream.read_string(); ServerLocation serverLocation = null; serverLocation = locateServer(i, str); outputStream = paramResponseHandler.createReply(); ServerLocationHelper.write(outputStream, serverLocation); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); }  return outputStream;case 9: try { int i = ServerIdHelper.read(paramInputStream); String str = ORBidHelper.read(paramInputStream); ServerLocationPerORB serverLocationPerORB = null; serverLocationPerORB = locateServerForORB(i, str); outputStream = paramResponseHandler.createReply(); ServerLocationPerORBHelper.write(outputStream, serverLocationPerORB); } catch (InvalidORBid invalidORBid) { outputStream = paramResponseHandler.createExceptionReply(); InvalidORBidHelper.write(outputStream, invalidORBid); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerHeldDown serverHeldDown) { outputStream = paramResponseHandler.createExceptionReply(); ServerHeldDownHelper.write(outputStream, serverHeldDown); }  return outputStream;case 10: try { String str = paramInputStream.read_string(); int i = 0; i = getEndpoint(str); outputStream = paramResponseHandler.createReply(); outputStream.write_long(i); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); }  return outputStream;case 11: try { ServerLocationPerORB serverLocationPerORB = ServerLocationPerORBHelper.read(paramInputStream); String str = paramInputStream.read_string(); int i = 0; i = getServerPortForType(serverLocationPerORB, str); outputStream = paramResponseHandler.createReply(); outputStream.write_long(i); } catch (NoSuchEndPoint noSuchEndPoint) { outputStream = paramResponseHandler.createExceptionReply(); NoSuchEndPointHelper.write(outputStream, noSuchEndPoint); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 287 */   private static String[] __ids = new String[] { "IDL:activation/ServerManager:1.0", "IDL:activation/Activator:1.0", "IDL:activation/Locator:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 294 */     return (String[])__ids.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_ServerManagerImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */