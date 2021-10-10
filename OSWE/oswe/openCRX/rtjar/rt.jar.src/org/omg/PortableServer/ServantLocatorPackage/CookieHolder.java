/*    */ package org.omg.PortableServer.ServantLocatorPackage;
/*    */ 
/*    */ import org.omg.CORBA.NO_IMPLEMENT;
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
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
/*    */ public final class CookieHolder
/*    */   implements Streamable
/*    */ {
/*    */   public Object value;
/*    */   
/*    */   public CookieHolder() {}
/*    */   
/*    */   public CookieHolder(Object paramObject) {
/* 43 */     this.value = paramObject;
/*    */   }
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 47 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 51 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */   
/*    */   public TypeCode _type() {
/* 55 */     throw new NO_IMPLEMENT();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ServantLocatorPackage/CookieHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */