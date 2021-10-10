/*     */ package org.omg.PortableServer;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.MARSHAL;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
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
/*     */ public abstract class ServantLocatorHelper
/*     */ {
/*  32 */   private static String _id = "IDL:omg.org/PortableServer/ServantLocator:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, ServantLocator paramServantLocator) {
/*  36 */     OutputStream outputStream = paramAny.create_output_stream();
/*  37 */     paramAny.type(type());
/*  38 */     write(outputStream, paramServantLocator);
/*  39 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServantLocator extract(Any paramAny) {
/*  44 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  47 */   private static TypeCode __typeCode = null;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  50 */     if (__typeCode == null)
/*     */     {
/*  52 */       __typeCode = ORB.init().create_interface_tc(id(), "ServantLocator");
/*     */     }
/*  54 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  59 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServantLocator read(InputStream paramInputStream) {
/*  64 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, ServantLocator paramServantLocator) {
/*  69 */     throw new MARSHAL();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ServantLocator narrow(Object paramObject) {
/*  74 */     if (paramObject == null)
/*  75 */       return null; 
/*  76 */     if (paramObject instanceof ServantLocator)
/*  77 */       return (ServantLocator)paramObject; 
/*  78 */     if (!paramObject._is_a(id())) {
/*  79 */       throw new BAD_PARAM();
/*     */     }
/*     */     
/*  82 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/*  83 */     _ServantLocatorStub _ServantLocatorStub = new _ServantLocatorStub();
/*  84 */     _ServantLocatorStub._set_delegate(delegate);
/*  85 */     return _ServantLocatorStub;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServantLocator unchecked_narrow(Object paramObject) {
/*  91 */     if (paramObject == null)
/*  92 */       return null; 
/*  93 */     if (paramObject instanceof ServantLocator) {
/*  94 */       return (ServantLocator)paramObject;
/*     */     }
/*     */     
/*  97 */     Delegate delegate = ((ObjectImpl)paramObject)._get_delegate();
/*  98 */     _ServantLocatorStub _ServantLocatorStub = new _ServantLocatorStub();
/*  99 */     _ServantLocatorStub._set_delegate(delegate);
/* 100 */     return _ServantLocatorStub;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ServantLocatorHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */