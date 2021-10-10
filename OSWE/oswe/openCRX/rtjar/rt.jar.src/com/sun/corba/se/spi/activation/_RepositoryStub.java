/*     */ package com.sun.corba.se.spi.activation;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDef;
/*     */ import com.sun.corba.se.spi.activation.RepositoryPackage.ServerDefHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ 
/*     */ public class _RepositoryStub extends ObjectImpl implements Repository {
/*     */   public int registerServer(ServerDef paramServerDef) throws ServerAlreadyRegistered, BadServerDefinition {
/*  18 */     InputStream inputStream = null;
/*     */     try {
/*  20 */       OutputStream outputStream = _request("registerServer", true);
/*  21 */       ServerDefHelper.write(outputStream, paramServerDef);
/*  22 */       inputStream = _invoke(outputStream);
/*  23 */       int i = ServerIdHelper.read(inputStream);
/*  24 */       return i;
/*  25 */     } catch (ApplicationException applicationException) {
/*  26 */       inputStream = applicationException.getInputStream();
/*  27 */       String str = applicationException.getId();
/*  28 */       if (str.equals("IDL:activation/ServerAlreadyRegistered:1.0"))
/*  29 */         throw ServerAlreadyRegisteredHelper.read(inputStream); 
/*  30 */       if (str.equals("IDL:activation/BadServerDefinition:1.0")) {
/*  31 */         throw BadServerDefinitionHelper.read(inputStream);
/*     */       }
/*  33 */       throw new MARSHAL(str);
/*  34 */     } catch (RemarshalException remarshalException) {
/*  35 */       return registerServer(paramServerDef);
/*     */     } finally {
/*  37 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterServer(int paramInt) throws ServerNotRegistered {
/*  45 */     InputStream inputStream = null;
/*     */     try {
/*  47 */       OutputStream outputStream = _request("unregisterServer", true);
/*  48 */       ServerIdHelper.write(outputStream, paramInt);
/*  49 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  51 */     } catch (ApplicationException applicationException) {
/*  52 */       inputStream = applicationException.getInputStream();
/*  53 */       String str = applicationException.getId();
/*  54 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/*  55 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/*  57 */       throw new MARSHAL(str);
/*  58 */     } catch (RemarshalException remarshalException) {
/*  59 */       unregisterServer(paramInt);
/*     */     } finally {
/*  61 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerDef getServer(int paramInt) throws ServerNotRegistered {
/*  69 */     InputStream inputStream = null;
/*     */     try {
/*  71 */       OutputStream outputStream = _request("getServer", true);
/*  72 */       ServerIdHelper.write(outputStream, paramInt);
/*  73 */       inputStream = _invoke(outputStream);
/*  74 */       ServerDef serverDef = ServerDefHelper.read(inputStream);
/*  75 */       return serverDef;
/*  76 */     } catch (ApplicationException applicationException) {
/*  77 */       inputStream = applicationException.getInputStream();
/*  78 */       String str = applicationException.getId();
/*  79 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/*  80 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/*  82 */       throw new MARSHAL(str);
/*  83 */     } catch (RemarshalException remarshalException) {
/*  84 */       return getServer(paramInt);
/*     */     } finally {
/*  86 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstalled(int paramInt) throws ServerNotRegistered {
/*  94 */     InputStream inputStream = null;
/*     */     try {
/*  96 */       OutputStream outputStream = _request("isInstalled", true);
/*  97 */       ServerIdHelper.write(outputStream, paramInt);
/*  98 */       inputStream = _invoke(outputStream);
/*  99 */       boolean bool = inputStream.read_boolean();
/* 100 */       return bool;
/* 101 */     } catch (ApplicationException applicationException) {
/* 102 */       inputStream = applicationException.getInputStream();
/* 103 */       String str = applicationException.getId();
/* 104 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/* 105 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/* 107 */       throw new MARSHAL(str);
/* 108 */     } catch (RemarshalException remarshalException) {
/* 109 */       return isInstalled(paramInt);
/*     */     } finally {
/* 111 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void install(int paramInt) throws ServerNotRegistered, ServerAlreadyInstalled {
/* 119 */     InputStream inputStream = null;
/*     */     try {
/* 121 */       OutputStream outputStream = _request("install", true);
/* 122 */       ServerIdHelper.write(outputStream, paramInt);
/* 123 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 125 */     } catch (ApplicationException applicationException) {
/* 126 */       inputStream = applicationException.getInputStream();
/* 127 */       String str = applicationException.getId();
/* 128 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/* 129 */         throw ServerNotRegisteredHelper.read(inputStream); 
/* 130 */       if (str.equals("IDL:activation/ServerAlreadyInstalled:1.0")) {
/* 131 */         throw ServerAlreadyInstalledHelper.read(inputStream);
/*     */       }
/* 133 */       throw new MARSHAL(str);
/* 134 */     } catch (RemarshalException remarshalException) {
/* 135 */       install(paramInt);
/*     */     } finally {
/* 137 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstall(int paramInt) throws ServerNotRegistered, ServerAlreadyUninstalled {
/* 145 */     InputStream inputStream = null;
/*     */     try {
/* 147 */       OutputStream outputStream = _request("uninstall", true);
/* 148 */       ServerIdHelper.write(outputStream, paramInt);
/* 149 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 151 */     } catch (ApplicationException applicationException) {
/* 152 */       inputStream = applicationException.getInputStream();
/* 153 */       String str = applicationException.getId();
/* 154 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/* 155 */         throw ServerNotRegisteredHelper.read(inputStream); 
/* 156 */       if (str.equals("IDL:activation/ServerAlreadyUninstalled:1.0")) {
/* 157 */         throw ServerAlreadyUninstalledHelper.read(inputStream);
/*     */       }
/* 159 */       throw new MARSHAL(str);
/* 160 */     } catch (RemarshalException remarshalException) {
/* 161 */       uninstall(paramInt);
/*     */     } finally {
/* 163 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] listRegisteredServers() {
/* 171 */     InputStream inputStream = null;
/*     */     try {
/* 173 */       OutputStream outputStream = _request("listRegisteredServers", true);
/* 174 */       inputStream = _invoke(outputStream);
/* 175 */       int[] arrayOfInt = ServerIdsHelper.read(inputStream);
/* 176 */       return arrayOfInt;
/* 177 */     } catch (ApplicationException applicationException) {
/* 178 */       inputStream = applicationException.getInputStream();
/* 179 */       String str = applicationException.getId();
/* 180 */       throw new MARSHAL(str);
/* 181 */     } catch (RemarshalException remarshalException) {
/* 182 */       return listRegisteredServers();
/*     */     } finally {
/* 184 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getApplicationNames() {
/* 192 */     InputStream inputStream = null;
/*     */     try {
/* 194 */       OutputStream outputStream = _request("getApplicationNames", true);
/* 195 */       inputStream = _invoke(outputStream);
/* 196 */       String[] arrayOfString = StringSeqHelper.read(inputStream);
/* 197 */       return arrayOfString;
/* 198 */     } catch (ApplicationException applicationException) {
/* 199 */       inputStream = applicationException.getInputStream();
/* 200 */       String str = applicationException.getId();
/* 201 */       throw new MARSHAL(str);
/* 202 */     } catch (RemarshalException remarshalException) {
/* 203 */       return getApplicationNames();
/*     */     } finally {
/* 205 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServerID(String paramString) throws ServerNotRegistered {
/* 213 */     InputStream inputStream = null;
/*     */     try {
/* 215 */       OutputStream outputStream = _request("getServerID", true);
/* 216 */       outputStream.write_string(paramString);
/* 217 */       inputStream = _invoke(outputStream);
/* 218 */       int i = ServerIdHelper.read(inputStream);
/* 219 */       return i;
/* 220 */     } catch (ApplicationException applicationException) {
/* 221 */       inputStream = applicationException.getInputStream();
/* 222 */       String str = applicationException.getId();
/* 223 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/* 224 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/* 226 */       throw new MARSHAL(str);
/* 227 */     } catch (RemarshalException remarshalException) {
/* 228 */       return getServerID(paramString);
/*     */     } finally {
/* 230 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 235 */   private static String[] __ids = new String[] { "IDL:activation/Repository:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 240 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 245 */     String str = paramObjectInputStream.readUTF();
/* 246 */     String[] arrayOfString = null;
/* 247 */     Properties properties = null;
/* 248 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 250 */       Object object = oRB.string_to_object(str);
/* 251 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 252 */       _set_delegate(delegate);
/*     */     } finally {
/* 254 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 260 */     String[] arrayOfString = null;
/* 261 */     Properties properties = null;
/* 262 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 264 */       String str = oRB.object_to_string(this);
/* 265 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 267 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_RepositoryStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */