/*     */ package org.omg.CosNaming;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _BindingIteratorStub
/*     */   extends ObjectImpl
/*     */   implements BindingIterator
/*     */ {
/*     */   public boolean next_one(BindingHolder paramBindingHolder) {
/*  32 */     InputStream inputStream = null;
/*     */     try {
/*  34 */       OutputStream outputStream = _request("next_one", true);
/*  35 */       inputStream = _invoke(outputStream);
/*  36 */       boolean bool = inputStream.read_boolean();
/*  37 */       paramBindingHolder.value = BindingHelper.read(inputStream);
/*  38 */       return bool;
/*  39 */     } catch (ApplicationException applicationException) {
/*  40 */       inputStream = applicationException.getInputStream();
/*  41 */       String str = applicationException.getId();
/*  42 */       throw new MARSHAL(str);
/*  43 */     } catch (RemarshalException remarshalException) {
/*  44 */       return next_one(paramBindingHolder);
/*     */     } finally {
/*  46 */       _releaseReply(inputStream);
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
/*     */   public boolean next_n(int paramInt, BindingListHolder paramBindingListHolder) {
/*  60 */     InputStream inputStream = null;
/*     */     try {
/*  62 */       OutputStream outputStream = _request("next_n", true);
/*  63 */       outputStream.write_ulong(paramInt);
/*  64 */       inputStream = _invoke(outputStream);
/*  65 */       boolean bool = inputStream.read_boolean();
/*  66 */       paramBindingListHolder.value = BindingListHelper.read(inputStream);
/*  67 */       return bool;
/*  68 */     } catch (ApplicationException applicationException) {
/*  69 */       inputStream = applicationException.getInputStream();
/*  70 */       String str = applicationException.getId();
/*  71 */       throw new MARSHAL(str);
/*  72 */     } catch (RemarshalException remarshalException) {
/*  73 */       return next_n(paramInt, paramBindingListHolder);
/*     */     } finally {
/*  75 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/*  85 */     InputStream inputStream = null;
/*     */     try {
/*  87 */       OutputStream outputStream = _request("destroy", true);
/*  88 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  90 */     } catch (ApplicationException applicationException) {
/*  91 */       inputStream = applicationException.getInputStream();
/*  92 */       String str = applicationException.getId();
/*  93 */       throw new MARSHAL(str);
/*  94 */     } catch (RemarshalException remarshalException) {
/*  95 */       destroy();
/*     */     } finally {
/*  97 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 102 */   private static String[] __ids = new String[] { "IDL:omg.org/CosNaming/BindingIterator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 107 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 112 */     String str = paramObjectInputStream.readUTF();
/* 113 */     String[] arrayOfString = null;
/* 114 */     Properties properties = null;
/* 115 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 117 */       Object object = oRB.string_to_object(str);
/* 118 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 119 */       _set_delegate(delegate);
/*     */     } finally {
/* 121 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 127 */     String[] arrayOfString = null;
/* 128 */     Properties properties = null;
/* 129 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 131 */       String str = oRB.object_to_string(this);
/* 132 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 134 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CosNaming/_BindingIteratorStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */