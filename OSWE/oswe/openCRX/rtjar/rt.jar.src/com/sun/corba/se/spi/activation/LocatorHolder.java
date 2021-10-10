/*    */ package com.sun.corba.se.spi.activation;
/*    */ 
/*    */ import org.omg.CORBA.TypeCode;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.Streamable;
/*    */ 
/*    */ 
/*    */ public final class LocatorHolder
/*    */   implements Streamable
/*    */ {
/* 12 */   public Locator value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public LocatorHolder() {}
/*    */ 
/*    */   
/*    */   public LocatorHolder(Locator paramLocator) {
/* 20 */     this.value = paramLocator;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 25 */     this.value = LocatorHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 30 */     LocatorHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 35 */     return LocatorHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/activation/LocatorHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */