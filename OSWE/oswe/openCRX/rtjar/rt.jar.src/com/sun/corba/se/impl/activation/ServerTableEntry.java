/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ActivationSystemException;
/*     */ import com.sun.corba.se.spi.activation.EndPointInfo;
/*     */ import com.sun.corba.se.spi.activation.InvalidORBid;
/*     */ import com.sun.corba.se.spi.activation.ORBAlreadyRegistered;
/*     */ import com.sun.corba.se.spi.activation.ORBPortInfo;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.Server;
/*     */ import com.sun.corba.se.spi.activation.ServerHeldDown;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class ServerTableEntry
/*     */ {
/*     */   private static final int DE_ACTIVATED = 0;
/*     */   private static final int ACTIVATING = 1;
/*     */   private static final int ACTIVATED = 2;
/*     */   private static final int RUNNING = 3;
/*     */   private static final int HELD_DOWN = 4;
/*     */   private static final long waitTime = 2000L;
/*     */   private static final int ActivationRetryMax = 5;
/*     */   private int state;
/*     */   private int serverId;
/*     */   private HashMap orbAndPortInfo;
/*     */   private Server serverObj;
/*     */   private ServerDef serverDef;
/*     */   private Process process;
/*     */   
/*     */   private String printState() {
/*  65 */     String str = "UNKNOWN";
/*     */     
/*  67 */     switch (this.state) { case 0:
/*  68 */         str = "DE_ACTIVATED"; break;
/*  69 */       case 1: str = "ACTIVATING  "; break;
/*  70 */       case 2: str = "ACTIVATED   "; break;
/*  71 */       case 3: str = "RUNNING     "; break;
/*  72 */       case 4: str = "HELD_DOWN   ";
/*     */         break; }
/*     */ 
/*     */     
/*  76 */     return str;
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
/*     */ 
/*     */   
/*  89 */   private int activateRetryCount = 0;
/*     */   private String activationCmd;
/*     */   private ActivationSystemException wrapper;
/*     */   
/*     */   public String toString() {
/*  94 */     return "ServerTableEntry[state=" + printState() + " serverId=" + this.serverId + " activateRetryCount=" + this.activateRetryCount + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private static String javaHome = System.getProperty("java.home");
/* 104 */   private static String classPath = System.getProperty("java.class.path");
/* 105 */   private static String fileSep = System.getProperty("file.separator");
/* 106 */   private static String pathSep = System.getProperty("path.separator");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int verify() {
/*     */     try {
/* 170 */       if (this.debug) {
/* 171 */         System.out.println("Server being verified w/" + this.activationCmd);
/*     */       }
/* 173 */       this.process = Runtime.getRuntime().exec(this.activationCmd);
/* 174 */       int i = this.process.waitFor();
/* 175 */       if (this.debug)
/* 176 */         printDebug("verify", "returns " + ServerMain.printResult(i)); 
/* 177 */       return i;
/* 178 */     } catch (Exception exception) {
/* 179 */       if (this.debug) {
/* 180 */         printDebug("verify", "returns unknown error because of exception " + exception);
/*     */       }
/* 182 */       return 4;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printDebug(String paramString1, String paramString2) {
/* 188 */     System.out.println("ServerTableEntry: method  =" + paramString1);
/* 189 */     System.out.println("ServerTableEntry: server  =" + this.serverId);
/* 190 */     System.out.println("ServerTableEntry: state   =" + printState());
/* 191 */     System.out.println("ServerTableEntry: message =" + paramString2);
/* 192 */     System.out.println();
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void activate() throws SystemException {
/* 197 */     this.state = 2;
/*     */     
/*     */     try {
/* 200 */       if (this.debug)
/* 201 */         printDebug("activate", "activating server"); 
/* 202 */       this.process = Runtime.getRuntime().exec(this.activationCmd);
/* 203 */     } catch (Exception exception) {
/* 204 */       deActivate();
/* 205 */       if (this.debug)
/* 206 */         printDebug("activate", "throwing premature process exit"); 
/* 207 */       throw this.wrapper.unableToStartProcess();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void register(Server paramServer) {
/* 213 */     if (this.state == 2) {
/*     */       
/* 215 */       this.serverObj = paramServer;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       if (this.debug) {
/* 221 */         printDebug("register", "process registered back");
/*     */       }
/*     */     } else {
/*     */       
/* 225 */       if (this.debug)
/* 226 */         printDebug("register", "throwing premature process exit"); 
/* 227 */       throw this.wrapper.serverNotExpectedToRegister();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void registerPorts(String paramString, EndPointInfo[] paramArrayOfEndPointInfo) throws ORBAlreadyRegistered {
/* 236 */     if (this.orbAndPortInfo.containsKey(paramString)) {
/* 237 */       throw new ORBAlreadyRegistered(paramString);
/*     */     }
/*     */ 
/*     */     
/* 241 */     int i = paramArrayOfEndPointInfo.length;
/* 242 */     EndPointInfo[] arrayOfEndPointInfo = new EndPointInfo[i];
/*     */     
/* 244 */     for (byte b = 0; b < i; b++) {
/* 245 */       arrayOfEndPointInfo[b] = new EndPointInfo((paramArrayOfEndPointInfo[b]).endpointType, (paramArrayOfEndPointInfo[b]).port);
/* 246 */       if (this.debug) {
/* 247 */         System.out.println("registering type: " + (arrayOfEndPointInfo[b]).endpointType + "  port  " + (arrayOfEndPointInfo[b]).port);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 252 */     this.orbAndPortInfo.put(paramString, arrayOfEndPointInfo);
/* 253 */     if (this.state == 2) {
/* 254 */       this.state = 3;
/* 255 */       notifyAll();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 260 */     if (this.debug) {
/* 261 */       printDebug("registerPorts", "process registered Ports");
/*     */     }
/*     */   }
/*     */   
/*     */   void install() {
/* 266 */     Server server = null;
/* 267 */     synchronized (this) {
/* 268 */       if (this.state == 3) {
/* 269 */         server = this.serverObj;
/*     */       } else {
/* 271 */         throw this.wrapper.serverNotRunning();
/*     */       } 
/* 273 */     }  if (server != null) {
/* 274 */       server.install();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void uninstall() {
/* 281 */     Server server = null;
/* 282 */     Process process = null;
/*     */     
/* 284 */     synchronized (this) {
/* 285 */       server = this.serverObj;
/* 286 */       process = this.process;
/*     */       
/* 288 */       if (this.state == 3) {
/*     */         
/* 290 */         deActivate();
/*     */       } else {
/*     */         
/* 293 */         throw this.wrapper.serverNotRunning();
/*     */       } 
/*     */     } 
/*     */     try {
/* 297 */       if (server != null) {
/* 298 */         server.shutdown();
/* 299 */         server.uninstall();
/*     */       } 
/*     */       
/* 302 */       if (process != null) {
/* 303 */         process.destroy();
/*     */       }
/* 305 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void holdDown() {
/* 312 */     this.state = 4;
/*     */     
/* 314 */     if (this.debug) {
/* 315 */       printDebug("holdDown", "server held down");
/*     */     }
/* 317 */     notifyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized void deActivate() {
/* 322 */     this.state = 0;
/*     */     
/* 324 */     if (this.debug) {
/* 325 */       printDebug("deActivate", "server deactivated");
/*     */     }
/* 327 */     notifyAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void checkProcessHealth() {
/* 334 */     if (this.state == 3) {
/*     */       try {
/* 336 */         int i = this.process.exitValue();
/* 337 */       } catch (IllegalThreadStateException illegalThreadStateException) {
/*     */         return;
/*     */       } 
/* 340 */       synchronized (this) {
/*     */         
/* 342 */         this.orbAndPortInfo.clear();
/*     */ 
/*     */         
/* 345 */         deActivate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized boolean isValid() {
/* 352 */     if (this.state == 1 || this.state == 4) {
/* 353 */       if (this.debug) {
/* 354 */         printDebug("isValid", "returns true");
/*     */       }
/* 356 */       return true;
/*     */     } 
/*     */     
/*     */     try {
/* 360 */       int i = this.process.exitValue();
/* 361 */     } catch (IllegalThreadStateException illegalThreadStateException) {
/* 362 */       return true;
/*     */     } 
/*     */     
/* 365 */     if (this.state == 2) {
/* 366 */       if (this.activateRetryCount < 5) {
/* 367 */         if (this.debug)
/* 368 */           printDebug("isValid", "reactivating server"); 
/* 369 */         this.activateRetryCount++;
/* 370 */         activate();
/* 371 */         return true;
/*     */       } 
/*     */       
/* 374 */       if (this.debug) {
/* 375 */         printDebug("isValid", "holding server down");
/*     */       }
/* 377 */       holdDown();
/* 378 */       return true;
/*     */     } 
/*     */     
/* 381 */     deActivate();
/* 382 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized ORBPortInfo[] lookup(String paramString) throws ServerHeldDown {
/* 387 */     while (this.state == 1 || this.state == 2) {
/*     */       
/* 389 */       try { wait(2000L);
/* 390 */         if (!isValid())
/* 391 */           break;  } catch (Exception exception) {}
/*     */     } 
/* 393 */     ORBPortInfo[] arrayOfORBPortInfo = null;
/*     */     
/* 395 */     if (this.state == 3) {
/* 396 */       arrayOfORBPortInfo = new ORBPortInfo[this.orbAndPortInfo.size()];
/* 397 */       Iterator<String> iterator = this.orbAndPortInfo.keySet().iterator();
/*     */       
/*     */       try {
/* 400 */         byte b = 0;
/*     */ 
/*     */         
/* 403 */         while (iterator.hasNext()) {
/* 404 */           String str = iterator.next();
/*     */           
/* 406 */           EndPointInfo[] arrayOfEndPointInfo = (EndPointInfo[])this.orbAndPortInfo.get(str);
/* 407 */           int i = -1;
/*     */           
/* 409 */           for (byte b1 = 0; b1 < arrayOfEndPointInfo.length; b1++) {
/* 410 */             if (this.debug) {
/* 411 */               System.out.println("lookup num-ports " + arrayOfEndPointInfo.length + "   " + (arrayOfEndPointInfo[b1]).endpointType + "   " + (arrayOfEndPointInfo[b1]).port);
/*     */             }
/*     */             
/* 414 */             if ((arrayOfEndPointInfo[b1]).endpointType.equals(paramString)) {
/* 415 */               i = (arrayOfEndPointInfo[b1]).port;
/*     */               break;
/*     */             } 
/*     */           } 
/* 419 */           arrayOfORBPortInfo[b] = new ORBPortInfo(str, i);
/* 420 */           b++;
/*     */         } 
/* 422 */       } catch (NoSuchElementException noSuchElementException) {}
/*     */ 
/*     */       
/* 425 */       return arrayOfORBPortInfo;
/*     */     } 
/*     */     
/* 428 */     if (this.debug) {
/* 429 */       printDebug("lookup", "throwing server held down error");
/*     */     }
/* 431 */     throw new ServerHeldDown(this.serverId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized EndPointInfo[] lookupForORB(String paramString) throws ServerHeldDown, InvalidORBid {
/* 437 */     while (this.state == 1 || this.state == 2) {
/*     */       
/* 439 */       try { wait(2000L);
/* 440 */         if (!isValid())
/* 441 */           break;  } catch (Exception exception) {}
/*     */     } 
/* 443 */     EndPointInfo[] arrayOfEndPointInfo = null;
/*     */     
/* 445 */     if (this.state == 3) {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 450 */         EndPointInfo[] arrayOfEndPointInfo1 = (EndPointInfo[])this.orbAndPortInfo.get(paramString);
/*     */         
/* 452 */         arrayOfEndPointInfo = new EndPointInfo[arrayOfEndPointInfo1.length];
/*     */         
/* 454 */         for (byte b = 0; b < arrayOfEndPointInfo1.length; b++) {
/* 455 */           if (this.debug) {
/* 456 */             System.out.println("lookup num-ports " + arrayOfEndPointInfo1.length + "   " + (arrayOfEndPointInfo1[b]).endpointType + "   " + (arrayOfEndPointInfo1[b]).port);
/*     */           }
/*     */           
/* 459 */           arrayOfEndPointInfo[b] = new EndPointInfo((arrayOfEndPointInfo1[b]).endpointType, (arrayOfEndPointInfo1[b]).port);
/*     */         } 
/* 461 */       } catch (NoSuchElementException noSuchElementException) {
/*     */         
/* 463 */         throw new InvalidORBid();
/*     */       } 
/* 465 */       return arrayOfEndPointInfo;
/*     */     } 
/*     */     
/* 468 */     if (this.debug) {
/* 469 */       printDebug("lookup", "throwing server held down error");
/*     */     }
/* 471 */     throw new ServerHeldDown(this.serverId);
/*     */   }
/*     */ 
/*     */   
/*     */   synchronized String[] getORBList() {
/* 476 */     String[] arrayOfString = new String[this.orbAndPortInfo.size()];
/* 477 */     Iterator<String> iterator = this.orbAndPortInfo.keySet().iterator();
/*     */     
/*     */     try {
/* 480 */       byte b = 0;
/* 481 */       while (iterator.hasNext()) {
/* 482 */         String str = iterator.next();
/* 483 */         arrayOfString[b++] = str;
/*     */       } 
/* 485 */     } catch (NoSuchElementException noSuchElementException) {}
/*     */ 
/*     */     
/* 488 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   int getServerId() {
/* 493 */     return this.serverId;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isActive() {
/* 498 */     return (this.state == 3 || this.state == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void destroy() {
/* 504 */     Server server = null;
/* 505 */     Process process = null;
/*     */     
/* 507 */     synchronized (this) {
/* 508 */       server = this.serverObj;
/* 509 */       process = this.process;
/*     */       
/* 511 */       deActivate();
/*     */     } 
/*     */     
/*     */     try {
/* 515 */       if (server != null) {
/* 516 */         server.shutdown();
/*     */       }
/* 518 */       if (this.debug)
/* 519 */         printDebug("destroy", "server shutdown successfully"); 
/* 520 */     } catch (Exception exception) {
/* 521 */       if (this.debug) {
/* 522 */         printDebug("destroy", "server shutdown threw exception" + exception);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 528 */       if (process != null) {
/* 529 */         process.destroy();
/*     */       }
/* 531 */       if (this.debug)
/* 532 */         printDebug("destroy", "process destroyed successfully"); 
/* 533 */     } catch (Exception exception) {
/* 534 */       if (this.debug) {
/* 535 */         printDebug("destroy", "process destroy threw exception" + exception);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   ServerTableEntry(ActivationSystemException paramActivationSystemException, int paramInt1, ServerDef paramServerDef, int paramInt2, String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 542 */     this.debug = false;
/*     */     this.wrapper = paramActivationSystemException;
/*     */     this.serverId = paramInt1;
/*     */     this.serverDef = paramServerDef;
/*     */     this.debug = paramBoolean2;
/*     */     this.orbAndPortInfo = new HashMap<>(255);
/*     */     this.activateRetryCount = 0;
/*     */     this.state = 1;
/*     */     this.activationCmd = javaHome + fileSep + "bin" + fileSep + "java " + paramServerDef.serverVmArgs + " -Dioser=" + System.getProperty("ioser") + " -D" + "org.omg.CORBA.ORBInitialPort" + "=" + paramInt2 + " -D" + "com.sun.CORBA.activation.DbDir" + "=" + paramString + " -D" + "com.sun.CORBA.POA.ORBActivated" + "=true -D" + "com.sun.CORBA.POA.ORBServerId" + "=" + paramInt1 + " -D" + "com.sun.CORBA.POA.ORBServerName" + "=" + paramServerDef.serverName + " " + (paramBoolean1 ? "-Dcom.sun.CORBA.activation.ORBServerVerify=true " : "") + "-classpath " + classPath + ((paramServerDef.serverClassPath.equals("") == true) ? "" : pathSep) + paramServerDef.serverClassPath + " com.sun.corba.se.impl.activation.ServerMain " + paramServerDef.serverArgs + (paramBoolean2 ? " -debug" : "");
/*     */     if (paramBoolean2)
/*     */       System.out.println("ServerTableEntry constructed with activation command " + this.activationCmd); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/ServerTableEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */