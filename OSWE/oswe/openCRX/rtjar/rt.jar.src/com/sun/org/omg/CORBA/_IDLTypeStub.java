/*     */ package com.sun.org.omg.CORBA;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.omg.CORBA.DefinitionKind;
/*     */ import org.omg.CORBA.IDLType;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
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
/*     */ public class _IDLTypeStub
/*     */   extends ObjectImpl
/*     */   implements IDLType
/*     */ {
/*     */   public _IDLTypeStub() {}
/*     */   
/*     */   public _IDLTypeStub(Delegate paramDelegate) {
/*  53 */     _set_delegate(paramDelegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode type() {
/*  58 */     InputStream inputStream = null;
/*     */     try {
/*  60 */       OutputStream outputStream = _request("_get_type", true);
/*  61 */       inputStream = _invoke(outputStream);
/*  62 */       TypeCode typeCode = inputStream.read_TypeCode();
/*  63 */       return typeCode;
/*  64 */     } catch (ApplicationException applicationException) {
/*  65 */       inputStream = applicationException.getInputStream();
/*  66 */       String str = applicationException.getId();
/*  67 */       throw new MARSHAL(str);
/*  68 */     } catch (RemarshalException remarshalException) {
/*  69 */       return type();
/*     */     } finally {
/*  71 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefinitionKind def_kind() {
/*  81 */     InputStream inputStream = null;
/*     */     try {
/*  83 */       OutputStream outputStream = _request("_get_def_kind", true);
/*  84 */       inputStream = _invoke(outputStream);
/*     */ 
/*     */       
/*  87 */       DefinitionKind definitionKind = DefinitionKindHelper.read(inputStream);
/*  88 */       return definitionKind;
/*  89 */     } catch (ApplicationException applicationException) {
/*  90 */       inputStream = applicationException.getInputStream();
/*  91 */       String str = applicationException.getId();
/*  92 */       throw new MARSHAL(str);
/*  93 */     } catch (RemarshalException remarshalException) {
/*  94 */       return def_kind();
/*     */     } finally {
/*  96 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 104 */     InputStream inputStream = null;
/*     */     try {
/* 106 */       OutputStream outputStream = _request("destroy", true);
/* 107 */       inputStream = _invoke(outputStream);
/* 108 */     } catch (ApplicationException applicationException) {
/* 109 */       inputStream = applicationException.getInputStream();
/* 110 */       String str = applicationException.getId();
/* 111 */       throw new MARSHAL(str);
/* 112 */     } catch (RemarshalException remarshalException) {
/* 113 */       destroy();
/*     */     } finally {
/* 115 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 120 */   private static String[] __ids = new String[] { "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 126 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) {
/*     */     try {
/* 133 */       String str = paramObjectInputStream.readUTF();
/* 134 */       Object object = ORB.init().string_to_object(str);
/* 135 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 136 */       _set_delegate(delegate);
/* 137 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) {
/*     */     try {
/* 144 */       String str = ORB.init().object_to_string((Object)this);
/* 145 */       paramObjectOutputStream.writeUTF(str);
/* 146 */     } catch (IOException iOException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/CORBA/_IDLTypeStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */