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
/*    */ public abstract class ServantActivatorPOA
/*    */   extends Servant
/*    */   implements ServantActivatorOperations, InvokeHandler
/*    */ {
/* 22 */   private static Hashtable _methods = new Hashtable<>();
/*    */   
/*    */   static {
/* 25 */     _methods.put("incarnate", new Integer(0));
/* 26 */     _methods.put("etherealize", new Integer(1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/* 33 */     throw new BAD_OPERATION();
/*    */   }
/*    */ 
/*    */   
/* 37 */   private static String[] __ids = new String[] { "IDL:omg.org/PortableServer/ServantActivator:2.3", "IDL:omg.org/PortableServer/ServantManager:1.0" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte) {
/* 43 */     return (String[])__ids.clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public ServantActivator _this() {
/* 48 */     return ServantActivatorHelper.narrow(
/* 49 */         _this_object());
/*    */   }
/*    */ 
/*    */   
/*    */   public ServantActivator _this(ORB paramORB) {
/* 54 */     return ServantActivatorHelper.narrow(
/* 55 */         _this_object(paramORB));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/ServantActivatorPOA.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */