/*     */ package com.sun.corba.se.spi.activation;
/*     */ 
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDefHelper;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.StringSeqHelper;
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
/*     */ public abstract class _RepositoryImplBase
/*     */   extends ObjectImpl
/*     */   implements Repository, InvokeHandler
/*     */ {
/*  20 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  23 */     _methods.put("registerServer", new Integer(0));
/*  24 */     _methods.put("unregisterServer", new Integer(1));
/*  25 */     _methods.put("getServer", new Integer(2));
/*  26 */     _methods.put("isInstalled", new Integer(3));
/*  27 */     _methods.put("install", new Integer(4));
/*  28 */     _methods.put("uninstall", new Integer(5));
/*  29 */     _methods.put("listRegisteredServers", new Integer(6));
/*  30 */     _methods.put("getApplicationNames", new Integer(7));
/*  31 */     _methods.put("getServerID", new Integer(8));
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     int[] arrayOfInt;
/*     */     String[] arrayOfString;
/*  38 */     OutputStream outputStream = null;
/*  39 */     Integer integer = (Integer)_methods.get(paramString);
/*  40 */     if (integer == null) {
/*  41 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  43 */     switch (integer.intValue()) {
/*     */       
/*     */       case 0:
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  50 */           ServerDef serverDef = ServerDefHelper.read(paramInputStream);
/*  51 */           int i = 0;
/*  52 */           i = registerServer(serverDef);
/*  53 */           outputStream = paramResponseHandler.createReply();
/*  54 */           outputStream.write_long(i);
/*  55 */         } catch (ServerAlreadyRegistered serverAlreadyRegistered) {
/*  56 */           outputStream = paramResponseHandler.createExceptionReply();
/*  57 */           ServerAlreadyRegisteredHelper.write(outputStream, serverAlreadyRegistered);
/*  58 */         } catch (BadServerDefinition badServerDefinition) {
/*  59 */           outputStream = paramResponseHandler.createExceptionReply();
/*  60 */           BadServerDefinitionHelper.write(outputStream, badServerDefinition);
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
/* 193 */         return outputStream;case 1: try { int i = ServerIdHelper.read(paramInputStream); unregisterServer(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 2: try { int i = ServerIdHelper.read(paramInputStream); ServerDef serverDef = null; serverDef = getServer(i); outputStream = paramResponseHandler.createReply(); ServerDefHelper.write(outputStream, serverDef); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 3: try { int i = ServerIdHelper.read(paramInputStream); boolean bool = false; bool = isInstalled(i); outputStream = paramResponseHandler.createReply(); outputStream.write_boolean(bool); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;case 4: try { int i = ServerIdHelper.read(paramInputStream); install(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerAlreadyInstalled serverAlreadyInstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyInstalledHelper.write(outputStream, serverAlreadyInstalled); }  return outputStream;case 5: try { int i = ServerIdHelper.read(paramInputStream); uninstall(i); outputStream = paramResponseHandler.createReply(); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); } catch (ServerAlreadyUninstalled serverAlreadyUninstalled) { outputStream = paramResponseHandler.createExceptionReply(); ServerAlreadyUninstalledHelper.write(outputStream, serverAlreadyUninstalled); }  return outputStream;case 6: arrayOfInt = null; arrayOfInt = listRegisteredServers(); outputStream = paramResponseHandler.createReply(); ServerIdsHelper.write(outputStream, arrayOfInt); return outputStream;case 7: arrayOfInt = null; arrayOfString = getApplicationNames(); outputStream = paramResponseHandler.createReply(); StringSeqHelper.write(outputStream, arrayOfString); return outputStream;case 8: try { String str = paramInputStream.read_string(); int i = 0; i = getServerID(str); outputStream = paramResponseHandler.createReply(); outputStream.write_long(i); } catch (ServerNotRegistered serverNotRegistered) { outputStream = paramResponseHandler.createExceptionReply(); ServerNotRegisteredHelper.write(outputStream, serverNotRegistered); }  return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 197 */   private static String[] __ids = new String[] { "IDL:activation/Repository:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 202 */     return (String[])__ids.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_RepositoryImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */