/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.CorbaResourceUtil;
/*     */ import com.sun.corba.se.spi.activation.Activator;
/*     */ import com.sun.corba.se.spi.activation.ActivatorHelper;
/*     */ import com.sun.corba.se.spi.activation.BadServerDefinition;
/*     */ import com.sun.corba.se.spi.activation.Repository;
/*     */ import com.sun.corba.se.spi.activation.RepositoryHelper;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyActive;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyRegistered;
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
/*     */ class RegisterServer
/*     */   implements CommandHandler
/*     */ {
/*     */   public String getCommandName() {
/* 264 */     return "register";
/*     */   }
/*     */   
/*     */   public void printCommandHelp(PrintStream paramPrintStream, boolean paramBoolean) {
/* 268 */     if (!paramBoolean) {
/* 269 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.register"));
/*     */     } else {
/* 271 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.register1"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processCommand(String[] paramArrayOfString, ORB paramORB, PrintStream paramPrintStream) {
/* 277 */     byte b = 0;
/* 278 */     String str1 = "";
/* 279 */     String str2 = "";
/* 280 */     String str3 = "";
/* 281 */     String str4 = "";
/* 282 */     String str5 = "";
/* 283 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/* 287 */     while (b < paramArrayOfString.length) {
/*     */       
/* 289 */       String str = paramArrayOfString[b++];
/*     */       
/* 291 */       if (str.equals("-server")) {
/* 292 */         if (b < paramArrayOfString.length) { str2 = paramArrayOfString[b++]; continue; }
/* 293 */          return true;
/* 294 */       }  if (str.equals("-applicationName")) {
/* 295 */         if (b < paramArrayOfString.length) { str1 = paramArrayOfString[b++]; continue; }
/* 296 */          return true;
/* 297 */       }  if (str.equals("-classpath")) {
/* 298 */         if (b < paramArrayOfString.length) { str3 = paramArrayOfString[b++]; continue; }
/* 299 */          return true;
/* 300 */       }  if (str.equals("-args")) {
/* 301 */         while (b < paramArrayOfString.length && !paramArrayOfString[b].equals("-vmargs")) {
/* 302 */           str4 = str4.equals("") ? paramArrayOfString[b] : (str4 + " " + paramArrayOfString[b]);
/*     */           
/* 304 */           b++;
/*     */         } 
/* 306 */         if (str4.equals("")) return true;  continue;
/* 307 */       }  if (str.equals("-vmargs")) {
/* 308 */         while (b < paramArrayOfString.length && !paramArrayOfString[b].equals("-args")) {
/* 309 */           str5 = str5.equals("") ? paramArrayOfString[b] : (str5 + " " + paramArrayOfString[b]);
/*     */           
/* 311 */           b++;
/*     */         } 
/* 313 */         if (str5.equals("")) return true;  continue;
/* 314 */       }  return true;
/*     */     } 
/*     */ 
/*     */     
/* 318 */     if (str2.equals("")) return true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     try { Repository repository = RepositoryHelper.narrow(paramORB
/* 324 */           .resolve_initial_references("ServerRepository"));
/*     */       
/* 326 */       ServerDef serverDef = new ServerDef(str1, str2, str3, str4, str5);
/* 327 */       i = repository.registerServer(serverDef);
/*     */ 
/*     */       
/* 330 */       Activator activator = ActivatorHelper.narrow(paramORB
/* 331 */           .resolve_initial_references("ServerActivator"));
/* 332 */       activator.activate(i);
/* 333 */       activator.install(i);
/*     */ 
/*     */       
/* 336 */       paramPrintStream.println(CorbaResourceUtil.getText("servertool.register2", i)); }
/* 337 */     catch (ServerNotRegistered serverNotRegistered) {  }
/* 338 */     catch (ServerAlreadyActive serverAlreadyActive) {  }
/* 339 */     catch (ServerHeldDown serverHeldDown)
/* 340 */     { paramPrintStream.println(CorbaResourceUtil.getText("servertool.register3", i)); }
/* 341 */     catch (ServerAlreadyRegistered serverAlreadyRegistered)
/* 342 */     { paramPrintStream.println(CorbaResourceUtil.getText("servertool.register4", i)); }
/* 343 */     catch (BadServerDefinition badServerDefinition)
/* 344 */     { paramPrintStream.println(CorbaResourceUtil.getText("servertool.baddef", badServerDefinition.reason)); }
/* 345 */     catch (Exception exception)
/* 346 */     { exception.printStackTrace(); }
/*     */ 
/*     */     
/* 349 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/RegisterServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */