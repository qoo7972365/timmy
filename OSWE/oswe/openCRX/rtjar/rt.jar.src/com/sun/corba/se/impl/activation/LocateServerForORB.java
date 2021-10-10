/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*     */ import com.sun.corba.se.spi.activation.InvalidORBid;
/*     */ import com.sun.corba.se.spi.activation.Locator;
/*     */ import com.sun.corba.se.spi.activation.LocatorHelper;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORB;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LocateServerForORB
/*     */   implements CommandHandler
/*     */ {
/*     */   static final int illegalServerId = -1;
/*     */   
/*     */   public String getCommandName() {
/* 487 */     return "locateperorb";
/*     */   }
/*     */   
/*     */   public void printCommandHelp(PrintStream paramPrintStream, boolean paramBoolean) {
/* 491 */     if (!paramBoolean) {
/* 492 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locateorb"));
/*     */     } else {
/* 494 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locateorb1"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processCommand(String[] paramArrayOfString, ORB paramORB, PrintStream paramPrintStream) {
/* 502 */     int i = -1;
/*     */     
/* 504 */     String str = "";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 509 */       byte b = 0;
/* 510 */       while (b < paramArrayOfString.length) {
/*     */         
/* 512 */         String str1 = paramArrayOfString[b++];
/*     */         
/* 514 */         if (str1.equals("-serverid")) {
/* 515 */           if (b < paramArrayOfString.length) {
/* 516 */             i = Integer.valueOf(paramArrayOfString[b++]).intValue(); continue;
/*     */           } 
/* 518 */           return true;
/* 519 */         }  if (str1.equals("-applicationName")) {
/* 520 */           if (b < paramArrayOfString.length) {
/* 521 */             i = ServerTool.getServerIdForAlias(paramORB, paramArrayOfString[b++]); continue;
/*     */           } 
/* 523 */           return true;
/* 524 */         }  if (str1.equals("-orbid") && 
/* 525 */           b < paramArrayOfString.length) {
/* 526 */           str = paramArrayOfString[b++];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 531 */       if (i == -1) {
/* 532 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 536 */       Locator locator = LocatorHelper.narrow(paramORB
/* 537 */           .resolve_initial_references("ServerLocator"));
/*     */       
/* 539 */       ServerLocationPerORB serverLocationPerORB = locator.locateServerForORB(i, str);
/*     */ 
/*     */ 
/*     */       
/* 543 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.locateorb2", serverLocationPerORB.hostname));
/* 544 */       int j = serverLocationPerORB.ports.length;
/* 545 */       for (b = 0; b < j; b++) {
/* 546 */         EndPointInfo endPointInfo = serverLocationPerORB.ports[b];
/* 547 */         paramPrintStream.println("\t\t" + endPointInfo.port + "\t\t" + endPointInfo.endpointType + "\t\t" + str);
/*     */       } 
/* 549 */     } catch (InvalidORBid invalidORBid) {
/* 550 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.nosuchorb"));
/* 551 */     } catch (ServerHeldDown serverHeldDown) {
/* 552 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.helddown"));
/* 553 */     } catch (ServerNotRegistered serverNotRegistered) {
/* 554 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.nosuchserver"));
/* 555 */     } catch (Exception exception) {
/* 556 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 559 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/LocateServerForORB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */