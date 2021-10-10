/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ public abstract class IORHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/IOP/IOR:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, IOR paramIOR) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramIOR);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static IOR extract(Any paramAny) {
/* 25 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 28 */   private static TypeCode __typeCode = null;
/*    */   private static boolean __active = false;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 32 */     if (__typeCode == null)
/*    */     {
/* 34 */       synchronized (TypeCode.class) {
/*    */         
/* 36 */         if (__typeCode == null) {
/*    */           
/* 38 */           if (__active)
/*    */           {
/* 40 */             return ORB.init().create_recursive_tc(_id);
/*    */           }
/* 42 */           __active = true;
/* 43 */           StructMember[] arrayOfStructMember = new StructMember[2];
/* 44 */           TypeCode typeCode = null;
/* 45 */           typeCode = ORB.init().create_string_tc(0);
/* 46 */           arrayOfStructMember[0] = new StructMember("type_id", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 50 */           typeCode = TaggedProfileHelper.type();
/* 51 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 52 */           arrayOfStructMember[1] = new StructMember("profiles", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 56 */           __typeCode = ORB.init().create_struct_tc(id(), "IOR", arrayOfStructMember);
/* 57 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 61 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 66 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static IOR read(InputStream paramInputStream) {
/* 71 */     IOR iOR = new IOR();
/* 72 */     iOR.type_id = paramInputStream.read_string();
/* 73 */     int i = paramInputStream.read_long();
/* 74 */     iOR.profiles = new TaggedProfile[i];
/* 75 */     for (byte b = 0; b < iOR.profiles.length; b++)
/* 76 */       iOR.profiles[b] = TaggedProfileHelper.read(paramInputStream); 
/* 77 */     return iOR;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, IOR paramIOR) {
/* 82 */     paramOutputStream.write_string(paramIOR.type_id);
/* 83 */     paramOutputStream.write_long(paramIOR.profiles.length);
/* 84 */     for (byte b = 0; b < paramIOR.profiles.length; b++)
/* 85 */       TaggedProfileHelper.write(paramOutputStream, paramIOR.profiles[b]); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/IORHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */