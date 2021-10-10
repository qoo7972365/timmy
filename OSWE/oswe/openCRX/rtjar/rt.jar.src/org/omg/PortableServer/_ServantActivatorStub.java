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
/*     */ 
/*     */ 
/*     */ public class _ServantActivatorStub
/*     */   extends ObjectImpl
/*     */   implements ServantActivator
/*     */ {
/*  18 */   public static final Class _opsClass = ServantActivatorOperations.class;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Servant incarnate(byte[] paramArrayOfbyte, POA paramPOA) throws ForwardRequest {
/*  41 */     ServantObject servantObject = _servant_preinvoke("incarnate", _opsClass);
/*  42 */     ServantActivatorOperations servantActivatorOperations = (ServantActivatorOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  45 */       return servantActivatorOperations.incarnate(paramArrayOfbyte, paramPOA);
/*     */     } finally {
/*  47 */       _servant_postinvoke(servantObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void etherealize(byte[] paramArrayOfbyte, POA paramPOA, Servant paramServant, boolean paramBoolean1, boolean paramBoolean2) {
/*  75 */     ServantObject servantObject = _servant_preinvoke("etherealize", _opsClass);
/*  76 */     ServantActivatorOperations servantActivatorOperations = (ServantActivatorOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  79 */       servantActivatorOperations.etherealize(paramArrayOfbyte, paramPOA, paramServant, paramBoolean1, paramBoolean2);
/*     */     } finally {
/*  81 */       _servant_postinvoke(servantObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  86 */   private static String[] __ids = new String[] { "IDL:omg.org/PortableServer/ServantActivator:2.3", "IDL:omg.org/PortableServer/ServantManager:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  92 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/*  97 */     String str = paramObjectInputStream.readUTF();
/*  98 */     String[] arrayOfString = null;
/*  99 */     Properties properties = null;
/* 100 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 102 */       Object object = oRB.string_to_object(str);
/* 103 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 104 */       _set_delegate(delegate);
/*     */     } finally {
/* 106 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 112 */     String[] arrayOfString = null;
/* 113 */     Properties properties = null;
/* 114 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 116 */       String str = oRB.object_to_string(this);
/* 117 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 119 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/_ServantActivatorStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */