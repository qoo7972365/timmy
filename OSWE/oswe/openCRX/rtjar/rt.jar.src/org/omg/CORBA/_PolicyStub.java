/*     */ package org.omg.CORBA;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _PolicyStub
/*     */   extends ObjectImpl
/*     */   implements Policy
/*     */ {
/*     */   public _PolicyStub() {}
/*     */   
/*     */   public _PolicyStub(Delegate paramDelegate) {
/*  58 */     _set_delegate(paramDelegate);
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
/*     */   public int policy_type() {
/*  74 */     InputStream inputStream = null;
/*     */     try {
/*  76 */       OutputStream outputStream = _request("_get_policy_type", true);
/*  77 */       inputStream = _invoke(outputStream);
/*  78 */       int i = PolicyTypeHelper.read(inputStream);
/*  79 */       return i;
/*  80 */     } catch (ApplicationException applicationException) {
/*  81 */       inputStream = applicationException.getInputStream();
/*  82 */       String str = applicationException.getId();
/*  83 */       throw new MARSHAL(str);
/*  84 */     } catch (RemarshalException remarshalException) {
/*  85 */       return policy_type();
/*     */     } finally {
/*  87 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Policy copy() {
/*  98 */     InputStream inputStream = null;
/*     */     try {
/* 100 */       OutputStream outputStream = _request("copy", true);
/* 101 */       inputStream = _invoke(outputStream);
/* 102 */       Policy policy = PolicyHelper.read(inputStream);
/* 103 */       return policy;
/* 104 */     } catch (ApplicationException applicationException) {
/* 105 */       inputStream = applicationException.getInputStream();
/* 106 */       String str = applicationException.getId();
/* 107 */       throw new MARSHAL(str);
/* 108 */     } catch (RemarshalException remarshalException) {
/* 109 */       return copy();
/*     */     } finally {
/* 111 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 121 */     InputStream inputStream = null;
/*     */     try {
/* 123 */       OutputStream outputStream = _request("destroy", true);
/* 124 */       inputStream = _invoke(outputStream);
/* 125 */     } catch (ApplicationException applicationException) {
/* 126 */       inputStream = applicationException.getInputStream();
/* 127 */       String str = applicationException.getId();
/* 128 */       throw new MARSHAL(str);
/* 129 */     } catch (RemarshalException remarshalException) {
/* 130 */       destroy();
/*     */     } finally {
/* 132 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 137 */   private static String[] __ids = new String[] { "IDL:omg.org/CORBA/Policy:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 142 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) {
/*     */     try {
/* 149 */       String str = paramObjectInputStream.readUTF();
/* 150 */       Object object = ORB.init().string_to_object(str);
/* 151 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 152 */       _set_delegate(delegate);
/* 153 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) {
/*     */     try {
/* 160 */       String str = ORB.init().object_to_string(this);
/* 161 */       paramObjectOutputStream.writeUTF(str);
/* 162 */     } catch (IOException iOException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/_PolicyStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */