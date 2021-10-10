/*    */ package org.omg.IOP;
/*    */ 
/*    */ import org.omg.CORBA.Any;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.StructMember;
/*    */ import org.omg.CORBA.TCKind;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ public abstract class TaggedProfileHelper
/*    */ {
/* 13 */   private static String _id = "IDL:omg.org/IOP/TaggedProfile:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, TaggedProfile paramTaggedProfile) {
/* 17 */     OutputStream outputStream = paramAny.create_output_stream();
/* 18 */     paramAny.type(type());
/* 19 */     write(outputStream, paramTaggedProfile);
/* 20 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static TaggedProfile extract(Any paramAny) {
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
/* 45 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_ulong);
/* 46 */           typeCode = ORB.init().create_alias_tc(ProfileIdHelper.id(), "ProfileId", typeCode);
/* 47 */           arrayOfStructMember[0] = new StructMember("tag", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 51 */           typeCode = ORB.init().get_primitive_tc(TCKind.tk_octet);
/* 52 */           typeCode = ORB.init().create_sequence_tc(0, typeCode);
/* 53 */           arrayOfStructMember[1] = new StructMember("profile_data", typeCode, null);
/*    */ 
/*    */ 
/*    */           
/* 57 */           __typeCode = ORB.init().create_struct_tc(id(), "TaggedProfile", arrayOfStructMember);
/* 58 */           __active = false;
/*    */         } 
/*    */       } 
/*    */     }
/* 62 */     return __typeCode;
/*    */   }
/*    */ 
/*    */   
/*    */   public static String id() {
/* 67 */     return _id;
/*    */   }
/*    */ 
/*    */   
/*    */   public static TaggedProfile read(InputStream paramInputStream) {
/* 72 */     TaggedProfile taggedProfile = new TaggedProfile();
/* 73 */     taggedProfile.tag = paramInputStream.read_ulong();
/* 74 */     int i = paramInputStream.read_long();
/* 75 */     taggedProfile.profile_data = new byte[i];
/* 76 */     paramInputStream.read_octet_array(taggedProfile.profile_data, 0, i);
/* 77 */     return taggedProfile;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, TaggedProfile paramTaggedProfile) {
/* 82 */     paramOutputStream.write_ulong(paramTaggedProfile.tag);
/* 83 */     paramOutputStream.write_long(paramTaggedProfile.profile_data.length);
/* 84 */     paramOutputStream.write_octet_array(paramTaggedProfile.profile_data, 0, paramTaggedProfile.profile_data.length);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/IOP/TaggedProfileHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */