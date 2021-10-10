/*     */ package com.sun.corba.se.spi.activation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ 
/*     */ public class _ServerStub
/*     */   extends ObjectImpl
/*     */   implements Server
/*     */ {
/*     */   public void shutdown() {
/*  22 */     InputStream inputStream = null;
/*     */     try {
/*  24 */       OutputStream outputStream = _request("shutdown", true);
/*  25 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  27 */     } catch (ApplicationException applicationException) {
/*  28 */       inputStream = applicationException.getInputStream();
/*  29 */       String str = applicationException.getId();
/*  30 */       throw new MARSHAL(str);
/*  31 */     } catch (RemarshalException remarshalException) {
/*  32 */       shutdown();
/*     */     } finally {
/*  34 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void install() {
/*  44 */     InputStream inputStream = null;
/*     */     try {
/*  46 */       OutputStream outputStream = _request("install", true);
/*  47 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  49 */     } catch (ApplicationException applicationException) {
/*  50 */       inputStream = applicationException.getInputStream();
/*  51 */       String str = applicationException.getId();
/*  52 */       throw new MARSHAL(str);
/*  53 */     } catch (RemarshalException remarshalException) {
/*  54 */       install();
/*     */     } finally {
/*  56 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstall() {
/*  66 */     InputStream inputStream = null;
/*     */     try {
/*  68 */       OutputStream outputStream = _request("uninstall", true);
/*  69 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  71 */     } catch (ApplicationException applicationException) {
/*  72 */       inputStream = applicationException.getInputStream();
/*  73 */       String str = applicationException.getId();
/*  74 */       throw new MARSHAL(str);
/*  75 */     } catch (RemarshalException remarshalException) {
/*  76 */       uninstall();
/*     */     } finally {
/*  78 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  83 */   private static String[] __ids = new String[] { "IDL:activation/Server:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/*  88 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/*  93 */     String str = paramObjectInputStream.readUTF();
/*  94 */     String[] arrayOfString = null;
/*  95 */     Properties properties = null;
/*  96 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/*  98 */       Object object = oRB.string_to_object(str);
/*  99 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 100 */       _set_delegate(delegate);
/*     */     } finally {
/* 102 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 108 */     String[] arrayOfString = null;
/* 109 */     Properties properties = null;
/* 110 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 112 */       String str = oRB.object_to_string(this);
/* 113 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 115 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_ServerStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */