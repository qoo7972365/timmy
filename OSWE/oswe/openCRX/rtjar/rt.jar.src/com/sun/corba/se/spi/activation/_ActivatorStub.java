/*     */ package com.sun.corba.se.spi.activation;
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
/*     */ public class _ActivatorStub extends ObjectImpl implements Activator {
/*     */   public void active(int paramInt, Server paramServer) throws ServerNotRegistered {
/*  18 */     InputStream inputStream = null;
/*     */     try {
/*  20 */       OutputStream outputStream = _request("active", true);
/*  21 */       ServerIdHelper.write(outputStream, paramInt);
/*  22 */       ServerHelper.write(outputStream, paramServer);
/*  23 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  25 */     } catch (ApplicationException applicationException) {
/*  26 */       inputStream = applicationException.getInputStream();
/*  27 */       String str = applicationException.getId();
/*  28 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/*  29 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/*  31 */       throw new MARSHAL(str);
/*  32 */     } catch (RemarshalException remarshalException) {
/*  33 */       active(paramInt, paramServer);
/*     */     } finally {
/*  35 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEndpoints(int paramInt, String paramString, EndPointInfo[] paramArrayOfEndPointInfo) throws ServerNotRegistered, NoSuchEndPoint, ORBAlreadyRegistered {
/*  43 */     InputStream inputStream = null;
/*     */     try {
/*  45 */       OutputStream outputStream = _request("registerEndpoints", true);
/*  46 */       ServerIdHelper.write(outputStream, paramInt);
/*  47 */       ORBidHelper.write(outputStream, paramString);
/*  48 */       EndpointInfoListHelper.write(outputStream, paramArrayOfEndPointInfo);
/*  49 */       inputStream = _invoke(outputStream);
/*     */       return;
/*  51 */     } catch (ApplicationException applicationException) {
/*  52 */       inputStream = applicationException.getInputStream();
/*  53 */       String str = applicationException.getId();
/*  54 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/*  55 */         throw ServerNotRegisteredHelper.read(inputStream); 
/*  56 */       if (str.equals("IDL:activation/NoSuchEndPoint:1.0"))
/*  57 */         throw NoSuchEndPointHelper.read(inputStream); 
/*  58 */       if (str.equals("IDL:activation/ORBAlreadyRegistered:1.0")) {
/*  59 */         throw ORBAlreadyRegisteredHelper.read(inputStream);
/*     */       }
/*  61 */       throw new MARSHAL(str);
/*  62 */     } catch (RemarshalException remarshalException) {
/*  63 */       registerEndpoints(paramInt, paramString, paramArrayOfEndPointInfo);
/*     */     } finally {
/*  65 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getActiveServers() {
/*  73 */     InputStream inputStream = null;
/*     */     try {
/*  75 */       OutputStream outputStream = _request("getActiveServers", true);
/*  76 */       inputStream = _invoke(outputStream);
/*  77 */       int[] arrayOfInt = ServerIdsHelper.read(inputStream);
/*  78 */       return arrayOfInt;
/*  79 */     } catch (ApplicationException applicationException) {
/*  80 */       inputStream = applicationException.getInputStream();
/*  81 */       String str = applicationException.getId();
/*  82 */       throw new MARSHAL(str);
/*  83 */     } catch (RemarshalException remarshalException) {
/*  84 */       return getActiveServers();
/*     */     } finally {
/*  86 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate(int paramInt) throws ServerAlreadyActive, ServerNotRegistered, ServerHeldDown {
/*  94 */     InputStream inputStream = null;
/*     */     try {
/*  96 */       OutputStream outputStream = _request("activate", true);
/*  97 */       ServerIdHelper.write(outputStream, paramInt);
/*  98 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 100 */     } catch (ApplicationException applicationException) {
/* 101 */       inputStream = applicationException.getInputStream();
/* 102 */       String str = applicationException.getId();
/* 103 */       if (str.equals("IDL:activation/ServerAlreadyActive:1.0"))
/* 104 */         throw ServerAlreadyActiveHelper.read(inputStream); 
/* 105 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/* 106 */         throw ServerNotRegisteredHelper.read(inputStream); 
/* 107 */       if (str.equals("IDL:activation/ServerHeldDown:1.0")) {
/* 108 */         throw ServerHeldDownHelper.read(inputStream);
/*     */       }
/* 110 */       throw new MARSHAL(str);
/* 111 */     } catch (RemarshalException remarshalException) {
/* 112 */       activate(paramInt);
/*     */     } finally {
/* 114 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown(int paramInt) throws ServerNotActive, ServerNotRegistered {
/* 122 */     InputStream inputStream = null;
/*     */     try {
/* 124 */       OutputStream outputStream = _request("shutdown", true);
/* 125 */       ServerIdHelper.write(outputStream, paramInt);
/* 126 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 128 */     } catch (ApplicationException applicationException) {
/* 129 */       inputStream = applicationException.getInputStream();
/* 130 */       String str = applicationException.getId();
/* 131 */       if (str.equals("IDL:activation/ServerNotActive:1.0"))
/* 132 */         throw ServerNotActiveHelper.read(inputStream); 
/* 133 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/* 134 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/* 136 */       throw new MARSHAL(str);
/* 137 */     } catch (RemarshalException remarshalException) {
/* 138 */       shutdown(paramInt);
/*     */     } finally {
/* 140 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void install(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyInstalled {
/* 148 */     InputStream inputStream = null;
/*     */     try {
/* 150 */       OutputStream outputStream = _request("install", true);
/* 151 */       ServerIdHelper.write(outputStream, paramInt);
/* 152 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 154 */     } catch (ApplicationException applicationException) {
/* 155 */       inputStream = applicationException.getInputStream();
/* 156 */       String str = applicationException.getId();
/* 157 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/* 158 */         throw ServerNotRegisteredHelper.read(inputStream); 
/* 159 */       if (str.equals("IDL:activation/ServerHeldDown:1.0"))
/* 160 */         throw ServerHeldDownHelper.read(inputStream); 
/* 161 */       if (str.equals("IDL:activation/ServerAlreadyInstalled:1.0")) {
/* 162 */         throw ServerAlreadyInstalledHelper.read(inputStream);
/*     */       }
/* 164 */       throw new MARSHAL(str);
/* 165 */     } catch (RemarshalException remarshalException) {
/* 166 */       install(paramInt);
/*     */     } finally {
/* 168 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getORBNames(int paramInt) throws ServerNotRegistered {
/* 176 */     InputStream inputStream = null;
/*     */     try {
/* 178 */       OutputStream outputStream = _request("getORBNames", true);
/* 179 */       ServerIdHelper.write(outputStream, paramInt);
/* 180 */       inputStream = _invoke(outputStream);
/* 181 */       String[] arrayOfString = ORBidListHelper.read(inputStream);
/* 182 */       return arrayOfString;
/* 183 */     } catch (ApplicationException applicationException) {
/* 184 */       inputStream = applicationException.getInputStream();
/* 185 */       String str = applicationException.getId();
/* 186 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0")) {
/* 187 */         throw ServerNotRegisteredHelper.read(inputStream);
/*     */       }
/* 189 */       throw new MARSHAL(str);
/* 190 */     } catch (RemarshalException remarshalException) {
/* 191 */       return getORBNames(paramInt);
/*     */     } finally {
/* 193 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninstall(int paramInt) throws ServerNotRegistered, ServerHeldDown, ServerAlreadyUninstalled {
/* 201 */     InputStream inputStream = null;
/*     */     try {
/* 203 */       OutputStream outputStream = _request("uninstall", true);
/* 204 */       ServerIdHelper.write(outputStream, paramInt);
/* 205 */       inputStream = _invoke(outputStream);
/*     */       return;
/* 207 */     } catch (ApplicationException applicationException) {
/* 208 */       inputStream = applicationException.getInputStream();
/* 209 */       String str = applicationException.getId();
/* 210 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/* 211 */         throw ServerNotRegisteredHelper.read(inputStream); 
/* 212 */       if (str.equals("IDL:activation/ServerHeldDown:1.0"))
/* 213 */         throw ServerHeldDownHelper.read(inputStream); 
/* 214 */       if (str.equals("IDL:activation/ServerAlreadyUninstalled:1.0")) {
/* 215 */         throw ServerAlreadyUninstalledHelper.read(inputStream);
/*     */       }
/* 217 */       throw new MARSHAL(str);
/* 218 */     } catch (RemarshalException remarshalException) {
/* 219 */       uninstall(paramInt);
/*     */     } finally {
/* 221 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 226 */   private static String[] __ids = new String[] { "IDL:activation/Activator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 231 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 236 */     String str = paramObjectInputStream.readUTF();
/* 237 */     String[] arrayOfString = null;
/* 238 */     Properties properties = null;
/* 239 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 241 */       Object object = oRB.string_to_object(str);
/* 242 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 243 */       _set_delegate(delegate);
/*     */     } finally {
/* 245 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 251 */     String[] arrayOfString = null;
/* 252 */     Properties properties = null;
/* 253 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 255 */       String str = oRB.object_to_string(this);
/* 256 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 258 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_ActivatorStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */