/*     */ package com.sun.corba.se.impl.activation;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ActivationSystemException;
/*     */ import com.sun.corba.se.spi.activation.BadServerDefinition;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyInstalled;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyRegistered;
/*     */ import com.sun.corba.se.spi.activation.ServerAlreadyUninstalled;
/*     */ import com.sun.corba.se.spi.activation.ServerNotRegistered;
/*     */ import com.sun.corba.se.spi.activation._RepositoryImplBase;
/*     */ import com.sun.corba.se.spi.legacy.connection.LegacyServerSocketEndPointInfo;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.transport.SocketOrChannelAcceptor;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.Vector;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RepositoryImpl
/*     */   extends _RepositoryImplBase
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8458417785209341858L;
/*     */   private transient boolean debug;
/*     */   static final int illegalServerId = -1;
/*     */   private transient RepositoryDB db;
/*     */   transient ORB orb;
/*     */   transient ActivationSystemException wrapper;
/*     */   
/*     */   RepositoryImpl(ORB paramORB, File paramFile, boolean paramBoolean) {
/* 395 */     this.debug = false;
/*     */ 
/*     */ 
/*     */     
/* 399 */     this.db = null;
/*     */     
/* 401 */     this.orb = null; this.debug = paramBoolean; this.orb = paramORB; this.wrapper = ActivationSystemException.get(paramORB, "orbd.repository"); File file = new File(paramFile, "servers.db"); if (!file.exists()) { this.db = new RepositoryDB(file); this.db.flush(); } else { try { FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream); this.db = (RepositoryDB)objectInputStream.readObject(); objectInputStream.close(); } catch (Exception exception) { throw this.wrapper.cannotReadRepositoryDb(exception); }  }  paramORB.connect((Object)this);
/*     */   }
/*     */   private String printServerDef(ServerDef paramServerDef) { return "ServerDef[applicationName=" + paramServerDef.applicationName + " serverName=" + paramServerDef.serverName + " serverClassPath=" + paramServerDef.serverClassPath + " serverArgs=" + paramServerDef.serverArgs + " serverVmArgs=" + paramServerDef.serverVmArgs + "]"; }
/*     */   public int registerServer(ServerDef paramServerDef, int paramInt) throws ServerAlreadyRegistered { DBServerDef dBServerDef = null; synchronized (this.db) { int i; Enumeration<DBServerDef> enumeration = this.db.serverTable.elements(); while (enumeration.hasMoreElements()) { dBServerDef = enumeration.nextElement(); if (paramServerDef.applicationName.equals(dBServerDef.applicationName)) { if (this.debug) System.out.println("RepositoryImpl: registerServer called to register ServerDef " + printServerDef(paramServerDef) + " with " + ((paramInt == -1) ? "a new server Id" : ("server Id " + paramInt)) + " FAILED because it is already registered.");  throw new ServerAlreadyRegistered(dBServerDef.id); }  }  if (paramInt == -1) { i = this.db.incrementServerIdCounter(); } else { i = paramInt; }  dBServerDef = new DBServerDef(paramServerDef, i); this.db.serverTable.put(new Integer(i), dBServerDef); this.db.flush(); if (this.debug) if (paramInt == -1) { System.out.println("RepositoryImpl: registerServer called to register ServerDef " + printServerDef(paramServerDef) + " with new serverId " + i); } else { System.out.println("RepositoryImpl: registerServer called to register ServerDef " + printServerDef(paramServerDef) + " with assigned serverId " + i); }   return i; }  }
/*     */   public int registerServer(ServerDef paramServerDef) throws ServerAlreadyRegistered, BadServerDefinition { LegacyServerSocketEndPointInfo legacyServerSocketEndPointInfo = this.orb.getLegacyServerSocketManager().legacyGetEndpoint("BOOT_NAMING"); int i = ((SocketOrChannelAcceptor)legacyServerSocketEndPointInfo).getServerSocket().getLocalPort(); ServerTableEntry serverTableEntry = new ServerTableEntry(this.wrapper, -1, paramServerDef, i, "", true, this.debug); switch (serverTableEntry.verify()) { case 0: return registerServer(paramServerDef, -1);case 1: throw new BadServerDefinition("main class not found.");case 2: throw new BadServerDefinition("no main method found.");case 3: throw new BadServerDefinition("server application error."); }  throw new BadServerDefinition("unknown Exception."); }
/*     */   public void unregisterServer(int paramInt) throws ServerNotRegistered { DBServerDef dBServerDef = null; Integer integer = new Integer(paramInt); synchronized (this.db) { dBServerDef = (DBServerDef)this.db.serverTable.get(integer); if (dBServerDef == null) { if (this.debug) System.out.println("RepositoryImpl: unregisterServer for serverId " + paramInt + " called: server not registered");  throw new ServerNotRegistered(); }  this.db.serverTable.remove(integer); this.db.flush(); }  if (this.debug) System.out.println("RepositoryImpl: unregisterServer for serverId " + paramInt + " called");  }
/*     */   private DBServerDef getDBServerDef(int paramInt) throws ServerNotRegistered { Integer integer = new Integer(paramInt); DBServerDef dBServerDef = (DBServerDef)this.db.serverTable.get(integer); if (dBServerDef == null) throw new ServerNotRegistered(paramInt);  return dBServerDef; }
/*     */   public ServerDef getServer(int paramInt) throws ServerNotRegistered { DBServerDef dBServerDef = getDBServerDef(paramInt); ServerDef serverDef = new ServerDef(dBServerDef.applicationName, dBServerDef.name, dBServerDef.classPath, dBServerDef.args, dBServerDef.vmArgs); if (this.debug) System.out.println("RepositoryImpl: getServer for serverId " + paramInt + " returns " + printServerDef(serverDef));  return serverDef; }
/*     */   public boolean isInstalled(int paramInt) throws ServerNotRegistered { DBServerDef dBServerDef = getDBServerDef(paramInt); return dBServerDef.isInstalled; }
/*     */   public void install(int paramInt) throws ServerNotRegistered, ServerAlreadyInstalled { DBServerDef dBServerDef = getDBServerDef(paramInt); if (dBServerDef.isInstalled) throw new ServerAlreadyInstalled(paramInt);  dBServerDef.isInstalled = true; this.db.flush(); }
/*     */   public void uninstall(int paramInt) throws ServerNotRegistered, ServerAlreadyUninstalled { DBServerDef dBServerDef = getDBServerDef(paramInt); if (!dBServerDef.isInstalled) throw new ServerAlreadyUninstalled(paramInt);  dBServerDef.isInstalled = false; this.db.flush(); } public int[] listRegisteredServers() { synchronized (this.db) { byte b = 0; int[] arrayOfInt = new int[this.db.serverTable.size()]; Enumeration<DBServerDef> enumeration = this.db.serverTable.elements(); while (enumeration.hasMoreElements()) { DBServerDef dBServerDef = enumeration.nextElement(); arrayOfInt[b++] = dBServerDef.id; }  if (this.debug) { StringBuffer stringBuffer = new StringBuffer(); for (byte b1 = 0; b1 < arrayOfInt.length; b1++) { stringBuffer.append(' '); stringBuffer.append(arrayOfInt[b1]); }  System.out.println("RepositoryImpl: listRegisteredServers returns" + stringBuffer.toString()); }  return arrayOfInt; }  } public int getServerID(String paramString) throws ServerNotRegistered { synchronized (this.db) { int i = -1; Enumeration<Integer> enumeration = this.db.serverTable.keys(); while (enumeration.hasMoreElements()) { Integer integer = enumeration.nextElement(); DBServerDef dBServerDef = (DBServerDef)this.db.serverTable.get(integer); if (dBServerDef.applicationName.equals(paramString)) { i = integer.intValue(); break; }  }  if (this.debug) System.out.println("RepositoryImpl: getServerID for " + paramString + " is " + i);  if (i == -1) throw new ServerNotRegistered();  return i; }  } public String[] getApplicationNames() { synchronized (this.db) { Vector<String> vector = new Vector(); Enumeration<Integer> enumeration = this.db.serverTable.keys(); while (enumeration.hasMoreElements()) { Integer integer = enumeration.nextElement(); DBServerDef dBServerDef = (DBServerDef)this.db.serverTable.get(integer); if (!dBServerDef.applicationName.equals("")) vector.addElement(dBServerDef.applicationName);  }  String[] arrayOfString = new String[vector.size()]; for (byte b = 0; b < vector.size(); b++) arrayOfString[b] = vector.elementAt(b);  if (this.debug) { StringBuffer stringBuffer = new StringBuffer(); for (byte b1 = 0; b1 < arrayOfString.length; b1++) { stringBuffer.append(' '); stringBuffer.append(arrayOfString[b1]); }  System.out.println("RepositoryImpl: getApplicationNames returns " + stringBuffer.toString()); }  return arrayOfString; }  } public static void main(String[] paramArrayOfString) { boolean bool = false; for (byte b = 0; b < paramArrayOfString.length; b++) { if (paramArrayOfString[b].equals("-debug")) bool = true;  }  try { Properties properties = new Properties(); properties.put("org.omg.CORBA.ORBClass", "com.sun.corba.se.impl.orb.ORBImpl"); ORB oRB = (ORB)ORB.init(paramArrayOfString, properties); String str = System.getProperty("com.sun.CORBA.activation.db", "db"); RepositoryImpl repositoryImpl = new RepositoryImpl(oRB, new File(str), bool); oRB.run(); } catch (Exception exception) { exception.printStackTrace(); }  } class RepositoryDB implements Serializable
/*     */   {
/* 413 */     File db; RepositoryDB(File param1File) { this.db = param1File;
/*     */ 
/*     */ 
/*     */       
/* 417 */       this.serverTable = new Hashtable<>(255);
/* 418 */       this.serverIdCounter = new Integer(256); }
/*     */     
/*     */     Hashtable serverTable; Integer serverIdCounter;
/*     */     
/*     */     int incrementServerIdCounter() {
/* 423 */       int i = this.serverIdCounter.intValue();
/* 424 */       this.serverIdCounter = new Integer(++i);
/*     */       
/* 426 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     void flush() {
/*     */       try {
/* 432 */         this.db.delete();
/* 433 */         FileOutputStream fileOutputStream = new FileOutputStream(this.db);
/* 434 */         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 435 */         objectOutputStream.writeObject(this);
/* 436 */         objectOutputStream.flush();
/* 437 */         objectOutputStream.close();
/* 438 */       } catch (Exception exception) {
/* 439 */         throw RepositoryImpl.this.wrapper.cannotWriteRepositoryDb(exception);
/*     */       } 
/*     */     } }
/*     */   class DBServerDef implements Serializable { String applicationName; String name; String classPath; String args; String vmArgs;
/*     */     boolean isInstalled;
/*     */     int id;
/*     */     
/*     */     public String toString() {
/* 447 */       return "DBServerDef(applicationName=" + this.applicationName + ", name=" + this.name + ", classPath=" + this.classPath + ", args=" + this.args + ", vmArgs=" + this.vmArgs + ", id=" + this.id + ", isInstalled=" + this.isInstalled + ")";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DBServerDef(ServerDef param1ServerDef, int param1Int) {
/* 457 */       this.applicationName = param1ServerDef.applicationName;
/* 458 */       this.name = param1ServerDef.serverName;
/* 459 */       this.classPath = param1ServerDef.serverClassPath;
/* 460 */       this.args = param1ServerDef.serverArgs;
/* 461 */       this.vmArgs = param1ServerDef.serverVmArgs;
/* 462 */       this.id = param1Int;
/* 463 */       this.isInstalled = false;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/activation/RepositoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */