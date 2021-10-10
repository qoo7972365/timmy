/*     */ package com.sun.corba.se.impl.protocol.giopmsgheaders;
/*     */ 
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.StructMember;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.IOP.IORHelper;
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
/*     */ public abstract class IORAddressingInfoHelper
/*     */ {
/*  38 */   private static String _id = "IDL:messages/IORAddressingInfo:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, IORAddressingInfo paramIORAddressingInfo) {
/*  42 */     OutputStream outputStream = paramAny.create_output_stream();
/*  43 */     paramAny.type(type());
/*  44 */     write(outputStream, paramIORAddressingInfo);
/*  45 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORAddressingInfo extract(Any paramAny) {
/*  50 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  53 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  57 */     if (__typeCode == null)
/*     */     {
/*  59 */       synchronized (TypeCode.class) {
/*     */         
/*  61 */         if (__typeCode == null) {
/*     */           
/*  63 */           if (__active)
/*     */           {
/*  65 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  67 */           __active = true;
/*  68 */           StructMember[] arrayOfStructMember = new StructMember[2];
/*  69 */           TypeCode typeCode = null;
/*  70 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_ulong);
/*  71 */           arrayOfStructMember[0] = new StructMember("selected_profile_index", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  75 */           typeCode = IORHelper.type();
/*  76 */           arrayOfStructMember[1] = new StructMember("ior", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  80 */           __typeCode = ORB.init().create_struct_tc(id(), "IORAddressingInfo", arrayOfStructMember);
/*  81 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  85 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  90 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IORAddressingInfo read(InputStream paramInputStream) {
/*  95 */     IORAddressingInfo iORAddressingInfo = new IORAddressingInfo();
/*  96 */     iORAddressingInfo.selected_profile_index = paramInputStream.read_ulong();
/*  97 */     iORAddressingInfo.ior = IORHelper.read(paramInputStream);
/*  98 */     return iORAddressingInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, IORAddressingInfo paramIORAddressingInfo) {
/* 103 */     paramOutputStream.write_ulong(paramIORAddressingInfo.selected_profile_index);
/* 104 */     IORHelper.write(paramOutputStream, paramIORAddressingInfo.ior);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/protocol/giopmsgheaders/IORAddressingInfoHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */