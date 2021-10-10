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
/*  62 */     _set_delegate(paramDelegate);
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeCode type() {
/*  67 */     InputStream inputStream = null;
/*     */     try {
/*  69 */       OutputStream outputStream = _request("_get_type", true);
/*  70 */       inputStream = _invoke(outputStream);
/*  71 */       TypeCode typeCode = inputStream.read_TypeCode();
/*  72 */       return typeCode;
/*  73 */     } catch (ApplicationException applicationException) {
/*  74 */       inputStream = applicationException.getInputStream();
/*  75 */       String str = applicationException.getId();
/*  76 */       throw new MARSHAL(str);
/*  77 */     } catch (RemarshalException remarshalException) {
/*  78 */       return type();
/*     */     } finally {
/*  80 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefinitionKind def_kind() {
/*  88 */     InputStream inputStream = null;
/*     */     try {
/*  90 */       OutputStream outputStream = _request("_get_def_kind", true);
/*  91 */       inputStream = _invoke(outputStream);
/*  92 */       DefinitionKind definitionKind = DefinitionKindHelper.read(inputStream);
/*  93 */       return definitionKind;
/*  94 */     } catch (ApplicationException applicationException) {
/*  95 */       inputStream = applicationException.getInputStream();
/*  96 */       String str = applicationException.getId();
/*  97 */       throw new MARSHAL(str);
/*  98 */     } catch (RemarshalException remarshalException) {
/*  99 */       return def_kind();
/*     */     } finally {
/* 101 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 109 */     InputStream inputStream = null;
/*     */     try {
/* 111 */       OutputStream outputStream = _request("destroy", true);
/* 112 */       inputStream = _invoke(outputStream);
/* 113 */     } catch (ApplicationException applicationException) {
/* 114 */       inputStream = applicationException.getInputStream();
/* 115 */       String str = applicationException.getId();
/* 116 */       throw new MARSHAL(str);
/* 117 */     } catch (RemarshalException remarshalException) {
/* 118 */       destroy();
/*     */     } finally {
/* 120 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 125 */   private static String[] __ids = new String[] { "IDL:omg.org/CORBA/IDLType:1.0", "IDL:omg.org/CORBA/IRObject:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 131 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) {
/*     */     try {
/* 138 */       String str = paramObjectInputStream.readUTF();
/* 139 */       Object object = ORB.init().string_to_object(str);
/* 140 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 141 */       _set_delegate(delegate);
/* 142 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) {
/*     */     try {
/* 149 */       String str = ORB.init().object_to_string(this);
/* 150 */       paramObjectOutputStream.writeUTF(str);
/* 151 */     } catch (IOException iOException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/_IDLTypeStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */