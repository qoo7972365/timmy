/*     */ package com.sun.corba.se.spi.activation;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORB;
/*     */ import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocationPerORBHelper;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ 
/*     */ public class _LocatorStub extends ObjectImpl implements Locator {
/*     */   public ServerLocation locateServer(int paramInt, String paramString) throws NoSuchEndPoint, ServerNotRegistered, ServerHeldDown {
/*  18 */     InputStream inputStream = null;
/*     */     try {
/*  20 */       OutputStream outputStream = _request("locateServer", true);
/*  21 */       ServerIdHelper.write(outputStream, paramInt);
/*  22 */       outputStream.write_string(paramString);
/*  23 */       inputStream = _invoke(outputStream);
/*  24 */       ServerLocation serverLocation = ServerLocationHelper.read(inputStream);
/*  25 */       return serverLocation;
/*  26 */     } catch (ApplicationException applicationException) {
/*  27 */       inputStream = applicationException.getInputStream();
/*  28 */       String str = applicationException.getId();
/*  29 */       if (str.equals("IDL:activation/NoSuchEndPoint:1.0"))
/*  30 */         throw NoSuchEndPointHelper.read(inputStream); 
/*  31 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/*  32 */         throw ServerNotRegisteredHelper.read(inputStream); 
/*  33 */       if (str.equals("IDL:activation/ServerHeldDown:1.0")) {
/*  34 */         throw ServerHeldDownHelper.read(inputStream);
/*     */       }
/*  36 */       throw new MARSHAL(str);
/*  37 */     } catch (RemarshalException remarshalException) {
/*  38 */       return locateServer(paramInt, paramString);
/*     */     } finally {
/*  40 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerLocationPerORB locateServerForORB(int paramInt, String paramString) throws InvalidORBid, ServerNotRegistered, ServerHeldDown {
/*  48 */     InputStream inputStream = null;
/*     */     try {
/*  50 */       OutputStream outputStream = _request("locateServerForORB", true);
/*  51 */       ServerIdHelper.write(outputStream, paramInt);
/*  52 */       ORBidHelper.write(outputStream, paramString);
/*  53 */       inputStream = _invoke(outputStream);
/*  54 */       ServerLocationPerORB serverLocationPerORB = ServerLocationPerORBHelper.read(inputStream);
/*  55 */       return serverLocationPerORB;
/*  56 */     } catch (ApplicationException applicationException) {
/*  57 */       inputStream = applicationException.getInputStream();
/*  58 */       String str = applicationException.getId();
/*  59 */       if (str.equals("IDL:activation/InvalidORBid:1.0"))
/*  60 */         throw InvalidORBidHelper.read(inputStream); 
/*  61 */       if (str.equals("IDL:activation/ServerNotRegistered:1.0"))
/*  62 */         throw ServerNotRegisteredHelper.read(inputStream); 
/*  63 */       if (str.equals("IDL:activation/ServerHeldDown:1.0")) {
/*  64 */         throw ServerHeldDownHelper.read(inputStream);
/*     */       }
/*  66 */       throw new MARSHAL(str);
/*  67 */     } catch (RemarshalException remarshalException) {
/*  68 */       return locateServerForORB(paramInt, paramString);
/*     */     } finally {
/*  70 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEndpoint(String paramString) throws NoSuchEndPoint {
/*  78 */     InputStream inputStream = null;
/*     */     try {
/*  80 */       OutputStream outputStream = _request("getEndpoint", true);
/*  81 */       outputStream.write_string(paramString);
/*  82 */       inputStream = _invoke(outputStream);
/*  83 */       int i = TCPPortHelper.read(inputStream);
/*  84 */       return i;
/*  85 */     } catch (ApplicationException applicationException) {
/*  86 */       inputStream = applicationException.getInputStream();
/*  87 */       String str = applicationException.getId();
/*  88 */       if (str.equals("IDL:activation/NoSuchEndPoint:1.0")) {
/*  89 */         throw NoSuchEndPointHelper.read(inputStream);
/*     */       }
/*  91 */       throw new MARSHAL(str);
/*  92 */     } catch (RemarshalException remarshalException) {
/*  93 */       return getEndpoint(paramString);
/*     */     } finally {
/*  95 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServerPortForType(ServerLocationPerORB paramServerLocationPerORB, String paramString) throws NoSuchEndPoint {
/* 103 */     InputStream inputStream = null;
/*     */     try {
/* 105 */       OutputStream outputStream = _request("getServerPortForType", true);
/* 106 */       ServerLocationPerORBHelper.write(outputStream, paramServerLocationPerORB);
/* 107 */       outputStream.write_string(paramString);
/* 108 */       inputStream = _invoke(outputStream);
/* 109 */       int i = TCPPortHelper.read(inputStream);
/* 110 */       return i;
/* 111 */     } catch (ApplicationException applicationException) {
/* 112 */       inputStream = applicationException.getInputStream();
/* 113 */       String str = applicationException.getId();
/* 114 */       if (str.equals("IDL:activation/NoSuchEndPoint:1.0")) {
/* 115 */         throw NoSuchEndPointHelper.read(inputStream);
/*     */       }
/* 117 */       throw new MARSHAL(str);
/* 118 */     } catch (RemarshalException remarshalException) {
/* 119 */       return getServerPortForType(paramServerLocationPerORB, paramString);
/*     */     } finally {
/* 121 */       _releaseReply(inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 126 */   private static String[] __ids = new String[] { "IDL:activation/Locator:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 131 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 136 */     String str = paramObjectInputStream.readUTF();
/* 137 */     String[] arrayOfString = null;
/* 138 */     Properties properties = null;
/* 139 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 141 */       Object object = oRB.string_to_object(str);
/* 142 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 143 */       _set_delegate(delegate);
/*     */     } finally {
/* 145 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 151 */     String[] arrayOfString = null;
/* 152 */     Properties properties = null;
/* 153 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 155 */       String str = oRB.object_to_string(this);
/* 156 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 158 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_LocatorStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */