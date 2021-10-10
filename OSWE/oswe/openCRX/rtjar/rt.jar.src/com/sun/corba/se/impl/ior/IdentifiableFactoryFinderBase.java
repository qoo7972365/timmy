/*    */ package com.sun.corba.se.impl.ior;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.IORSystemException;
/*    */ import com.sun.corba.se.spi.ior.Identifiable;
/*    */ import com.sun.corba.se.spi.ior.IdentifiableFactory;
/*    */ import com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
/*    */ import com.sun.corba.se.spi.orb.ORB;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.omg.CORBA_2_3.portable.InputStream;
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
/*    */ public abstract class IdentifiableFactoryFinderBase
/*    */   implements IdentifiableFactoryFinder
/*    */ {
/*    */   private ORB orb;
/*    */   private Map map;
/*    */   protected IORSystemException wrapper;
/*    */   
/*    */   protected IdentifiableFactoryFinderBase(ORB paramORB) {
/* 52 */     this.map = new HashMap<>();
/* 53 */     this.orb = paramORB;
/* 54 */     this.wrapper = IORSystemException.get(paramORB, "oa.ior");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected IdentifiableFactory getFactory(int paramInt) {
/* 60 */     Integer integer = new Integer(paramInt);
/* 61 */     return (IdentifiableFactory)this.map.get(integer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Identifiable handleMissingFactory(int paramInt, InputStream paramInputStream);
/*    */ 
/*    */   
/*    */   public Identifiable create(int paramInt, InputStream paramInputStream) {
/* 70 */     IdentifiableFactory identifiableFactory = getFactory(paramInt);
/*    */     
/* 72 */     if (identifiableFactory != null) {
/* 73 */       return identifiableFactory.create(paramInputStream);
/*    */     }
/* 75 */     return handleMissingFactory(paramInt, paramInputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerFactory(IdentifiableFactory paramIdentifiableFactory) {
/* 80 */     Integer integer = new Integer(paramIdentifiableFactory.getId());
/* 81 */     this.map.put(integer, paramIdentifiableFactory);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/IdentifiableFactoryFinderBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */