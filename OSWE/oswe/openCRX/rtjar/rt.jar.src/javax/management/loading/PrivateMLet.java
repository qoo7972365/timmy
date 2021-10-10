/*    */ package javax.management.loading;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.net.URLStreamHandlerFactory;
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
/*    */ public class PrivateMLet
/*    */   extends MLet
/*    */   implements PrivateClassLoader
/*    */ {
/*    */   private static final long serialVersionUID = 2503458973393711979L;
/*    */   
/*    */   public PrivateMLet(URL[] paramArrayOfURL, boolean paramBoolean) {
/* 57 */     super(paramArrayOfURL, paramBoolean);
/*    */   }
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
/*    */   public PrivateMLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader, boolean paramBoolean) {
/* 75 */     super(paramArrayOfURL, paramClassLoader, paramBoolean);
/*    */   }
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
/*    */   public PrivateMLet(URL[] paramArrayOfURL, ClassLoader paramClassLoader, URLStreamHandlerFactory paramURLStreamHandlerFactory, boolean paramBoolean) {
/* 97 */     super(paramArrayOfURL, paramClassLoader, paramURLStreamHandlerFactory, paramBoolean);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/loading/PrivateMLet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */