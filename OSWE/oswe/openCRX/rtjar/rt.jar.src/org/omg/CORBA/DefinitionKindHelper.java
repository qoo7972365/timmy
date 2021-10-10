/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DefinitionKindHelper
/*    */ {
/* 40 */   private static String _id = "IDL:omg.org/CORBA/DefinitionKind:1.0";
/*    */ 
/*    */   
/*    */   public static void insert(Any paramAny, DefinitionKind paramDefinitionKind) {
/* 44 */     OutputStream outputStream = paramAny.create_output_stream();
/* 45 */     paramAny.type(type());
/* 46 */     write(outputStream, paramDefinitionKind);
/* 47 */     paramAny.read_value(outputStream.create_input_stream(), type());
/*    */   }
/*    */ 
/*    */   
/*    */   public static DefinitionKind extract(Any paramAny) {
/* 52 */     return read(paramAny.create_input_stream());
/*    */   }
/*    */   
/* 55 */   private static TypeCode __typeCode = null;
/*    */   
/*    */   public static synchronized TypeCode type() {
/* 58 */     if (__typeCode == null)
/*    */     {
/* 60 */       __typeCode = ORB.init().create_enum_tc(id(), "DefinitionKind", new String[] { "dk_none", "dk_all", "dk_Attribute", "dk_Constant", "dk_Exception", "dk_Interface", "dk_Module", "dk_Operation", "dk_Typedef", "dk_Alias", "dk_Struct", "dk_Union", "dk_Enum", "dk_Primitive", "dk_String", "dk_Sequence", "dk_Array", "dk_Repository", "dk_Wstring", "dk_Fixed", "dk_Value", "dk_ValueBox", "dk_ValueMember", "dk_Native" });
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
/*    */   public static DefinitionKind read(InputStream paramInputStream) {
/* 72 */     return DefinitionKind.from_int(paramInputStream.read_long());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(OutputStream paramOutputStream, DefinitionKind paramDefinitionKind) {
/* 77 */     paramOutputStream.write_long(paramDefinitionKind.value());
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/DefinitionKindHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */