/*     */ package org.omg.PortableServer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.ServantObject;
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
/*     */ public class _ServantLocatorStub
/*     */   extends ObjectImpl
/*     */   implements ServantLocator
/*     */ {
/*  32 */   public static final Class _opsClass = ServantLocatorOperations.class;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant preinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, CookieHolder paramCookieHolder) throws ForwardRequest {
/*  57 */     ServantObject servantObject = _servant_preinvoke("preinvoke", _opsClass);
/*  58 */     ServantLocatorOperations servantLocatorOperations = (ServantLocatorOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  61 */       return servantLocatorOperations.preinvoke(paramArrayOfbyte, paramPOA, paramString, paramCookieHolder);
/*     */     } finally {
/*  63 */       _servant_postinvoke(servantObject);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postinvoke(byte[] paramArrayOfbyte, POA paramPOA, String paramString, Object paramObject, Servant paramServant) {
/*  82 */     ServantObject servantObject = _servant_preinvoke("postinvoke", _opsClass);
/*  83 */     ServantLocatorOperations servantLocatorOperations = (ServantLocatorOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  86 */       servantLocatorOperations.postinvoke(paramArrayOfbyte, paramPOA, paramString, paramObject, paramServant);
/*     */     } finally {
/*  88 */       _servant_postinvoke(servantObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  93 */   private static String[] __ids = new String[] { "IDL:omg.org/PortableServer/ServantLocator:1.0", "IDL:omg.org/PortableServer/ServantManager:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  99 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 104 */     String str = paramObjectInputStream.readUTF();
/* 105 */     String[] arrayOfString = null;
/* 106 */     Properties properties = null;
/* 107 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 109 */       Object object = oRB.string_to_object(str);
/* 110 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 111 */       _set_delegate(delegate);
/*     */     } finally {
/* 113 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 119 */     String[] arrayOfString = null;
/* 120 */     Properties properties = null;
/* 121 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 123 */       String str = oRB.object_to_string(this);
/* 124 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 126 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/_ServantLocatorStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */