/*     */ package org.omg.CORBA;
/*     */ 
/*     */ import org.omg.CORBA.portable.InputStream;
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
/*     */ public abstract class UnknownUserExceptionHelper
/*     */ {
/*  40 */   private static String _id = "IDL:omg.org/CORBA/UnknownUserException:1.0";
/*     */ 
/*     */   
/*     */   public static void insert(Any paramAny, UnknownUserException paramUnknownUserException) {
/*  44 */     OutputStream outputStream = paramAny.create_output_stream();
/*  45 */     paramAny.type(type());
/*  46 */     write(outputStream, paramUnknownUserException);
/*  47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*     */   }
/*     */ 
/*     */   
/*     */   public static UnknownUserException extract(Any paramAny) {
/*  52 */     return read(paramAny.create_input_stream());
/*     */   }
/*     */   
/*  55 */   private static TypeCode __typeCode = null;
/*     */   private static boolean __active = false;
/*     */   
/*     */   public static synchronized TypeCode type() {
/*  59 */     if (__typeCode == null)
/*     */     {
/*  61 */       synchronized (TypeCode.class) {
/*     */         
/*  63 */         if (__typeCode == null) {
/*     */           
/*  65 */           if (__active)
/*     */           {
/*  67 */             return ORB.init().create_recursive_tc(_id);
/*     */           }
/*  69 */           __active = true;
/*  70 */           StructMember[] arrayOfStructMember = new StructMember[1];
/*  71 */           TypeCode typeCode = null;
/*  72 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_any);
/*  73 */           arrayOfStructMember[0] = new StructMember("except", typeCode, null);
/*     */ 
/*     */ 
/*     */           
/*  77 */           __typeCode = ORB.init().create_exception_tc(id(), "UnknownUserException", arrayOfStructMember);
/*  78 */           __active = false;
/*     */         } 
/*     */       } 
/*     */     }
/*  82 */     return __typeCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String id() {
/*  87 */     return _id;
/*     */   }
/*     */ 
/*     */   
/*     */   public static UnknownUserException read(InputStream paramInputStream) {
/*  92 */     UnknownUserException unknownUserException = new UnknownUserException();
/*     */     
/*  94 */     paramInputStream.read_string();
/*  95 */     unknownUserException.except = paramInputStream.read_any();
/*  96 */     return unknownUserException;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(OutputStream paramOutputStream, UnknownUserException paramUnknownUserException) {
/* 102 */     paramOutputStream.write_string(id());
/* 103 */     paramOutputStream.write_any(paramUnknownUserException.except);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/UnknownUserExceptionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */