/*     */ package com.sun.org.omg.SendingContext;
/*     */ 
/*     */ import com.sun.org.omg.CORBA.Repository;
/*     */ import com.sun.org.omg.CORBA.RepositoryHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdSeqHelper;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
/*     */ import com.sun.org.omg.SendingContext.CodeBasePackage.URLHelper;
/*     */ import com.sun.org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
/*     */ import com.sun.org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _CodeBaseStub
/*     */   extends ObjectImpl
/*     */   implements CodeBase
/*     */ {
/*     */   public _CodeBaseStub() {}
/*     */   
/*     */   public _CodeBaseStub(Delegate paramDelegate) {
/*  49 */     _set_delegate(paramDelegate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Repository get_ir() {
/*  56 */     InputStream inputStream = null;
/*     */     try {
/*  58 */       OutputStream outputStream = _request("get_ir", true);
/*  59 */       inputStream = _invoke(outputStream);
/*  60 */       Repository repository = RepositoryHelper.read(inputStream);
/*  61 */       return repository;
/*  62 */     } catch (ApplicationException applicationException) {
/*  63 */       inputStream = applicationException.getInputStream();
/*  64 */       String str = applicationException.getId();
/*  65 */       throw new MARSHAL(str);
/*  66 */     } catch (RemarshalException remarshalException) {
/*  67 */       return get_ir();
/*     */     } finally {
/*  69 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String implementation(String paramString) {
/*  77 */     InputStream inputStream = null;
/*     */     try {
/*  79 */       OutputStream outputStream = _request("implementation", true);
/*  80 */       RepositoryIdHelper.write(outputStream, paramString);
/*  81 */       inputStream = _invoke(outputStream);
/*  82 */       String str = URLHelper.read(inputStream);
/*  83 */       return str;
/*  84 */     } catch (ApplicationException applicationException) {
/*  85 */       inputStream = applicationException.getInputStream();
/*  86 */       String str = applicationException.getId();
/*  87 */       throw new MARSHAL(str);
/*  88 */     } catch (RemarshalException remarshalException) {
/*  89 */       return implementation(paramString);
/*     */     } finally {
/*  91 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] implementations(String[] paramArrayOfString) {
/*  97 */     InputStream inputStream = null;
/*     */     try {
/*  99 */       OutputStream outputStream = _request("implementations", true);
/* 100 */       RepositoryIdSeqHelper.write(outputStream, paramArrayOfString);
/* 101 */       inputStream = _invoke(outputStream);
/* 102 */       String[] arrayOfString = URLSeqHelper.read(inputStream);
/* 103 */       return arrayOfString;
/* 104 */     } catch (ApplicationException applicationException) {
/* 105 */       inputStream = applicationException.getInputStream();
/* 106 */       String str = applicationException.getId();
/* 107 */       throw new MARSHAL(str);
/* 108 */     } catch (RemarshalException remarshalException) {
/* 109 */       return implementations(paramArrayOfString);
/*     */     } finally {
/* 111 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FullValueDescription meta(String paramString) {
/* 119 */     InputStream inputStream = null;
/*     */     try {
/* 121 */       OutputStream outputStream = _request("meta", true);
/* 122 */       RepositoryIdHelper.write(outputStream, paramString);
/* 123 */       inputStream = _invoke(outputStream);
/* 124 */       FullValueDescription fullValueDescription = FullValueDescriptionHelper.read(inputStream);
/* 125 */       return fullValueDescription;
/* 126 */     } catch (ApplicationException applicationException) {
/* 127 */       inputStream = applicationException.getInputStream();
/* 128 */       String str = applicationException.getId();
/* 129 */       throw new MARSHAL(str);
/* 130 */     } catch (RemarshalException remarshalException) {
/* 131 */       return meta(paramString);
/*     */     } finally {
/* 133 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public FullValueDescription[] metas(String[] paramArrayOfString) {
/* 139 */     InputStream inputStream = null;
/*     */     try {
/* 141 */       OutputStream outputStream = _request("metas", true);
/* 142 */       RepositoryIdSeqHelper.write(outputStream, paramArrayOfString);
/* 143 */       inputStream = _invoke(outputStream);
/* 144 */       FullValueDescription[] arrayOfFullValueDescription = ValueDescSeqHelper.read(inputStream);
/* 145 */       return arrayOfFullValueDescription;
/* 146 */     } catch (ApplicationException applicationException) {
/* 147 */       inputStream = applicationException.getInputStream();
/* 148 */       String str = applicationException.getId();
/* 149 */       throw new MARSHAL(str);
/* 150 */     } catch (RemarshalException remarshalException) {
/* 151 */       return metas(paramArrayOfString);
/*     */     } finally {
/* 153 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] bases(String paramString) {
/* 161 */     InputStream inputStream = null;
/*     */     try {
/* 163 */       OutputStream outputStream = _request("bases", true);
/* 164 */       RepositoryIdHelper.write(outputStream, paramString);
/* 165 */       inputStream = _invoke(outputStream);
/* 166 */       String[] arrayOfString = RepositoryIdSeqHelper.read(inputStream);
/* 167 */       return arrayOfString;
/* 168 */     } catch (ApplicationException applicationException) {
/* 169 */       inputStream = applicationException.getInputStream();
/* 170 */       String str = applicationException.getId();
/* 171 */       throw new MARSHAL(str);
/* 172 */     } catch (RemarshalException remarshalException) {
/* 173 */       return bases(paramString);
/*     */     } finally {
/* 175 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 180 */   private static String[] __ids = new String[] { "IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 186 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) {
/*     */     try {
/* 193 */       String str = paramObjectInputStream.readUTF();
/* 194 */       Object object = ORB.init().string_to_object(str);
/* 195 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 196 */       _set_delegate(delegate);
/* 197 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) {
/*     */     try {
/* 204 */       String str = ORB.init().object_to_string((Object)this);
/* 205 */       paramObjectOutputStream.writeUTF(str);
/* 206 */     } catch (IOException iOException) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/_CodeBaseStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */