/*     */ package org.omg.DynamicAny;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Properties;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.ServantObject;
/*     */ import org.omg.DynamicAny.DynAnyFactoryPackage.InconsistentTypeCode;
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
/*     */ public class _DynAnyFactoryStub
/*     */   extends ObjectImpl
/*     */   implements DynAnyFactory
/*     */ {
/*  35 */   public static final Class _opsClass = DynAnyFactoryOperations.class;
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
/*     */   public DynAny create_dyn_any(Any paramAny) throws InconsistentTypeCode {
/*  51 */     ServantObject servantObject = _servant_preinvoke("create_dyn_any", _opsClass);
/*  52 */     DynAnyFactoryOperations dynAnyFactoryOperations = (DynAnyFactoryOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  55 */       return dynAnyFactoryOperations.create_dyn_any(paramAny);
/*     */     } finally {
/*  57 */       _servant_postinvoke(servantObject);
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
/*     */   public DynAny create_dyn_any_from_type_code(TypeCode paramTypeCode) throws InconsistentTypeCode {
/*  95 */     ServantObject servantObject = _servant_preinvoke("create_dyn_any_from_type_code", _opsClass);
/*  96 */     DynAnyFactoryOperations dynAnyFactoryOperations = (DynAnyFactoryOperations)servantObject.servant;
/*     */     
/*     */     try {
/*  99 */       return dynAnyFactoryOperations.create_dyn_any_from_type_code(paramTypeCode);
/*     */     } finally {
/* 101 */       _servant_postinvoke(servantObject);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 106 */   private static String[] __ids = new String[] { "IDL:omg.org/DynamicAny/DynAnyFactory:1.0" };
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 111 */     return (String[])__ids.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException {
/* 116 */     String str = paramObjectInputStream.readUTF();
/* 117 */     String[] arrayOfString = null;
/* 118 */     Properties properties = null;
/* 119 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 121 */       Object object = oRB.string_to_object(str);
/* 122 */       Delegate delegate = ((ObjectImpl)object)._get_delegate();
/* 123 */       _set_delegate(delegate);
/*     */     } finally {
/* 125 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 131 */     String[] arrayOfString = null;
/* 132 */     Properties properties = null;
/* 133 */     ORB oRB = ORB.init(arrayOfString, properties);
/*     */     try {
/* 135 */       String str = oRB.object_to_string(this);
/* 136 */       paramObjectOutputStream.writeUTF(str);
/*     */     } finally {
/* 138 */       oRB.destroy();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/DynamicAny/_DynAnyFactoryStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */