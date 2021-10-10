/*    */ package com.sun.corba.se.spi.activation;
/*    */ import com.sun.corba.se.spi.activation.InitialNameServicePackage.NameAlreadyBound;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.util.Properties;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.Object;
/*    */ import org.omg.CORBA.ObjectHelper;
/*    */ import org.omg.CORBA.portable.ApplicationException;
/*    */ import org.omg.CORBA.portable.Delegate;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.ObjectImpl;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public class _InitialNameServiceStub extends ObjectImpl implements InitialNameService {
/*    */   public void bind(String paramString, Object paramObject, boolean paramBoolean) throws NameAlreadyBound {
/* 18 */     InputStream inputStream = null;
/*    */     try {
/* 20 */       OutputStream outputStream = _request("bind", true);
/* 21 */       outputStream.write_string(paramString);
/* 22 */       ObjectHelper.write(outputStream, paramObject);
/* 23 */       outputStream.write_boolean(paramBoolean);
/* 24 */       inputStream = _invoke(outputStream);
/*    */       return;
/* 26 */     } catch (ApplicationException applicationException) {
/* 27 */       inputStream = applicationException.getInputStream();
/* 28 */       String str = applicationException.getId();
/* 29 */       if (str.equals("IDL:activation/InitialNameService/NameAlreadyBound:1.0")) {
/* 30 */         throw NameAlreadyBoundHelper.read(inputStream);
/*    */       }
/* 32 */       throw new MARSHAL(str);
/* 33 */     } catch (RemarshalException remarshalException) {
/* 34 */       bind(paramString, paramObject, paramBoolean);
/*    */     } finally {
/* 36 */       _releaseReply(inputStream);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 41 */   private static String[] __ids = new String[] { "IDL:activation/InitialNameService:1.0" };
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] _ids() {
/* 46 */     return (String[])__ids.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 51 */     String str = paramObjectInputStream.readUTF();
/* 52 */     String[] arrayOfString = null;
/* 53 */     Properties properties = null;
/* 54 */     ORB oRB = ORB.init(arrayOfString, properties);
/*    */     try {
/* 56 */       Object object = oRB.string_to_object(str);
/* 57 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 58 */       _set_delegate(delegate);
/*    */     } finally {
/* 60 */       oRB.destroy();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 66 */     String[] arrayOfString = null;
/* 67 */     Properties properties = null;
/* 68 */     ORB oRB = ORB.init(arrayOfString, properties);
/*    */     try {
/* 70 */       String str = oRB.object_to_string(this);
/* 71 */       paramObjectOutputStream.writeUTF(str);
/*    */     } finally {
/* 73 */       oRB.destroy();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/_InitialNameServiceStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */