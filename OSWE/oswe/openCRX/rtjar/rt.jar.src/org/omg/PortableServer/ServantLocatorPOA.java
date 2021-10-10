/*    */ package org.omg.PortableServer;
/*    */ 
/*    */ import java.util.Hashtable;
/*    */ import org.omg.CORBA.BAD_OPERATION;
/*    */ import org.omg.CORBA.ORB;
/*    */ import org.omg.CORBA.portable.InputStream;
/*    */ import org.omg.CORBA.portable.InvokeHandler;
/*    */ import org.omg.CORBA.portable.OutputStream;
/*    */ import org.omg.CORBA.portable.ResponseHandler;
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
/*    */ public abstract class ServantLocatorPOA
/*    */   extends Servant
/*    */   implements ServantLocatorOperations, InvokeHandler
/*    */ {
/* 36 */   private static Hashtable _methods = new Hashtable<>();
/*    */   
/*    */   static {
/* 39 */     _methods.put("preinvoke", new Integer(0));
/* 40 */     _methods.put("postinvoke", new Integer(1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/* 47 */     throw new BAD_OPERATION();
/*    */   }
/*    */ 
/*    */   
/* 51 */   private static String[] __ids = new String[] { "IDL:omg.org/PortableServer/ServantLocator:1.0", "IDL:omg.org/PortableServer/ServantManager:1.0" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/* 57 */     return (String[])__ids.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public ServantLocator _this() {
/* 62 */     return ServantLocatorHelper.narrow(
/* 63 */         _this_object());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServantLocator _this(ORB paramORB) {
/* 68 */     return ServantLocatorHelper.narrow(
/* 69 */         _this_object(paramORB));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ServantLocatorPOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */