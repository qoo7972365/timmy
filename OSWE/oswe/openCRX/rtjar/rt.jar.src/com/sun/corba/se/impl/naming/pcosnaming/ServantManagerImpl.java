/*     */ package com.sun.corba.se.impl.naming.pcosnaming;
/*     */ 
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.PortableServer.ForwardRequest;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.Servant;
/*     */ import org.omg.PortableServer.ServantLocator;
/*     */ import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServantManagerImpl
/*     */   extends LocalObject
/*     */   implements ServantLocator
/*     */ {
/*     */   private static final long serialVersionUID = 4028710359865748280L;
/*     */   private ORB orb;
/*     */   private NameService theNameService;
/*     */   private File logDir;
/*     */   private Hashtable contexts;
/*     */   private CounterDB counterDb;
/*     */   private int counter;
/*     */   private static final String objKeyPrefix = "NC";
/*     */   
/*     */   ServantManagerImpl(ORB paramORB, File paramFile, NameService paramNameService) {
/*  78 */     this.logDir = paramFile;
/*  79 */     this.orb = paramORB;
/*     */     
/*  81 */     this.counterDb = new CounterDB(paramFile);
/*  82 */     this.contexts = new Hashtable<>();
/*  83 */     this.theNameService = paramNameService;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant preinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, CookieHolder paramCookieHolder) throws ForwardRequest {
/*     */     NamingContextImpl namingContextImpl;
/*  91 */     String str = new String(paramArrayOfbyte);
/*     */     
/*  93 */     Servant servant = (Servant)this.contexts.get(str);
/*     */     
/*  95 */     if (servant == null)
/*     */     {
/*  97 */       namingContextImpl = readInContext(str);
/*     */     }
/*     */     
/* 100 */     return (Servant)namingContextImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, Object paramObject, Servant paramServant) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingContextImpl readInContext(String paramString) {
/* 111 */     NamingContextImpl namingContextImpl = (NamingContextImpl)this.contexts.get(paramString);
/* 112 */     if (namingContextImpl != null)
/*     */     {
/*     */       
/* 115 */       return namingContextImpl;
/*     */     }
/*     */     
/* 118 */     File file = new File(this.logDir, paramString);
/* 119 */     if (file.exists()) {
/*     */       try {
/* 121 */         FileInputStream fileInputStream = new FileInputStream(file);
/* 122 */         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
/* 123 */         namingContextImpl = (NamingContextImpl)objectInputStream.readObject();
/* 124 */         namingContextImpl.setORB(this.orb);
/* 125 */         namingContextImpl.setServantManagerImpl(this);
/* 126 */         namingContextImpl.setRootNameService(this.theNameService);
/* 127 */         objectInputStream.close();
/* 128 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/* 132 */     if (namingContextImpl != null)
/*     */     {
/* 134 */       this.contexts.put(paramString, namingContextImpl);
/*     */     }
/* 136 */     return namingContextImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NamingContextImpl addContext(String paramString, NamingContextImpl paramNamingContextImpl) {
/* 142 */     File file = new File(this.logDir, paramString);
/*     */     
/* 144 */     if (file.exists()) {
/*     */       
/* 146 */       paramNamingContextImpl = readInContext(paramString);
/*     */     } else {
/*     */       
/*     */       try {
/* 150 */         FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 151 */         ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 152 */         objectOutputStream.writeObject(paramNamingContextImpl);
/* 153 */         objectOutputStream.close();
/* 154 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 159 */       this.contexts.remove(paramString);
/*     */     }
/* 161 */     catch (Exception exception) {}
/*     */ 
/*     */     
/* 164 */     this.contexts.put(paramString, paramNamingContextImpl);
/*     */     
/* 166 */     return paramNamingContextImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateContext(String paramString, NamingContextImpl paramNamingContextImpl) {
/* 172 */     File file = new File(this.logDir, paramString);
/* 173 */     if (file.exists()) {
/*     */       
/* 175 */       file.delete();
/* 176 */       file = new File(this.logDir, paramString);
/*     */     } 
/*     */     
/*     */     try {
/* 180 */       FileOutputStream fileOutputStream = new FileOutputStream(file);
/* 181 */       ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
/* 182 */       objectOutputStream.writeObject(paramNamingContextImpl);
/* 183 */       objectOutputStream.close();
/* 184 */     } catch (Exception exception) {
/* 185 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getRootObjectKey() {
/* 191 */     return "NC0";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewObjectKey() {
/* 196 */     return "NC" + this.counterDb.getNextCounter();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/ServantManagerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */