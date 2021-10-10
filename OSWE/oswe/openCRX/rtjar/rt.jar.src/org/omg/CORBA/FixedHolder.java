/*    */ package org.omg.CORBA;
/*    */ 
/*    */ import java.math.BigDecimal;
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
/*    */ public final class FixedHolder
/*    */   implements Streamable
/*    */ {
/*    */   public BigDecimal value;
/*    */   
/*    */   public FixedHolder() {}
/*    */   
/*    */   public FixedHolder(BigDecimal paramBigDecimal) {
/* 65 */     this.value = paramBigDecimal;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 75 */     this.value = paramInputStream.read_fixed();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 85 */     paramOutputStream.write_fixed(this.value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 95 */     return ORB.init().get_primitive_tc(TCKind.tk_fixed);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/FixedHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */