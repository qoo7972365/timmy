/*    */ package org.omg.CORBA;
/*    */ 
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
/*    */ public final class UnknownUserExceptionHolder
/*    */   implements Streamable
/*    */ {
/* 39 */   public UnknownUserException value = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public UnknownUserExceptionHolder() {}
/*    */ 
/*    */   
/*    */   public UnknownUserExceptionHolder(UnknownUserException paramUnknownUserException) {
/* 47 */     this.value = paramUnknownUserException;
/*    */   }
/*    */ 
/*    */   
/*    */   public void _read(InputStream paramInputStream) {
/* 52 */     this.value = UnknownUserExceptionHelper.read(paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void _write(OutputStream paramOutputStream) {
/* 57 */     UnknownUserExceptionHelper.write(paramOutputStream, this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public TypeCode _type() {
/* 62 */     return UnknownUserExceptionHelper.type();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/UnknownUserExceptionHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */