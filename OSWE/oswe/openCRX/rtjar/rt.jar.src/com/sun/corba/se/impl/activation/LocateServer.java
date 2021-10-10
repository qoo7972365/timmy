/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.activation.Locator;
/*     */ import com.sun.corba.se.spi.activation.LocatorHelper;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;
/*     */ import com.sun.corba.se.spi.activation.NoSuchEndPoint;
/*     */ import com.sun.corba.se.spi.activation.ORBPortInfo;
/*     */ import com.sun.corba.se.spi.activation.ServerHeldDown;
/*     */ import com.sun.corba.se.spi.activation.ServerNotRegistered;
/*     */ import java.io.PrintStream;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LocateServer
/*     */   implements CommandHandler
/*     */ {
/*     */   static final int illegalServerId = -1;
/*     */   
/*     */   public String getCommandName() {
/* 411 */     return "locate";
/*     */   }
/*     */   
/*     */   public void printCommandHelp(PrintStream paramPrintStream, boolean paramBoolean) {
/* 415 */     if (!paramBoolean) {
/* 416 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locate"));
/*     */     } else {
/* 418 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locate1"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processCommand(String[] paramArrayOfString, ORB paramORB, PrintStream paramPrintStream) {
/* 426 */     int i = -1;
/*     */     
/* 428 */     String str = "IIOP_CLEAR_TEXT";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     try { byte b = 0;
/* 434 */       while (b < paramArrayOfString.length) {
/*     */         
/* 436 */         String str1 = paramArrayOfString[b++];
/*     */         
/* 438 */         if (str1.equals("-serverid")) {
/* 439 */           if (b < paramArrayOfString.length) {
/* 440 */             i = Integer.valueOf(paramArrayOfString[b++]).intValue(); continue;
/*     */           } 
/* 442 */           return true;
/* 443 */         }  if (str1.equals("-applicationName")) {
/* 444 */           if (b < paramArrayOfString.length) {
/* 445 */             i = ServerTool.getServerIdForAlias(paramORB, paramArrayOfString[b++]); continue;
/*     */           } 
/* 447 */           return true;
/* 448 */         }  if (str1.equals("-endpointType") && 
/* 449 */           b < paramArrayOfString.length) {
/* 450 */           str = paramArrayOfString[b++];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 455 */       if (i == -1) {
/* 456 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 460 */       Locator locator = LocatorHelper.narrow(paramORB
/* 461 */           .resolve_initial_references("ServerLocator"));
/*     */       
/* 463 */       ServerLocation serverLocation = locator.locateServer(i, str);
/*     */ 
/*     */       
/* 466 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locate2", serverLocation.hostname));
/* 467 */       int j = serverLocation.ports.length;
/* 468 */       for (b = 0; b < j; b++) {
/* 469 */         ORBPortInfo oRBPortInfo = serverLocation.ports[b];
/* 470 */         paramPrintStream.println("\t\t" + oRBPortInfo.port + "\t\t" + str + "\t\t" + oRBPortInfo.orbId);
/*     */       }  }
/* 472 */     catch (NoSuchEndPoint noSuchEndPoint) {  }
/* 473 */     catch (ServerHeldDown serverHeldDown)
/* 474 */     { paramPrintStream.println(CorbaResourceUtil.getText("servertool.helddown")); }
/* 475 */     catch (ServerNotRegistered serverNotRegistered)
/* 476 */     { paramPrintStream.println(CorbaResourceUtil.getText("servertool.nosuchserver")); }
/* 477 */     catch (Exception exception)
/* 478 */     { exception.printStackTrace(); }
/*     */ 
/*     */     
/* 481 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/LocateServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */