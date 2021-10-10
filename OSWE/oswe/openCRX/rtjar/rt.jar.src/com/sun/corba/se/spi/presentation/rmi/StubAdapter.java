/*     */ package com.sun.corba.se.spi.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.impl.oa.poa.POAManagerImpl;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.rmi.CORBA.Stub;
/*     */ import javax.rmi.CORBA.Tie;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.PortableServer.POA;
/*     */ import org.omg.PortableServer.POAManager;
/*     */ import org.omg.PortableServer.POAPackage.ServantNotActive;
/*     */ import org.omg.PortableServer.POAPackage.WrongPolicy;
/*     */ import org.omg.PortableServer.Servant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StubAdapter
/*     */ {
/*  64 */   private static ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");
/*     */ 
/*     */   
/*     */   public static boolean isStubClass(Class<?> paramClass) {
/*  68 */     return (ObjectImpl.class.isAssignableFrom(paramClass) || DynamicStub.class
/*  69 */       .isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isStub(Object paramObject) {
/*  74 */     return (paramObject instanceof DynamicStub || paramObject instanceof ObjectImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDelegate(Object paramObject, Delegate paramDelegate) {
/*  80 */     if (paramObject instanceof DynamicStub) {
/*  81 */       ((DynamicStub)paramObject).setDelegate(paramDelegate);
/*  82 */     } else if (paramObject instanceof ObjectImpl) {
/*  83 */       ((ObjectImpl)paramObject)._set_delegate(paramDelegate);
/*     */     } else {
/*  85 */       throw wrapper.setDelegateRequiresStub();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object activateServant(Servant paramServant) {
/*  92 */     POA pOA = paramServant._default_POA();
/*  93 */     Object object = null;
/*     */     
/*     */     try {
/*  96 */       object = pOA.servant_to_reference(paramServant);
/*  97 */     } catch (ServantNotActive servantNotActive) {
/*  98 */       throw wrapper.getDelegateServantNotActive(servantNotActive);
/*  99 */     } catch (WrongPolicy wrongPolicy) {
/* 100 */       throw wrapper.getDelegateWrongPolicy(wrongPolicy);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 105 */     POAManager pOAManager = pOA.the_POAManager();
/* 106 */     if (pOAManager instanceof POAManagerImpl) {
/* 107 */       POAManagerImpl pOAManagerImpl = (POAManagerImpl)pOAManager;
/* 108 */       pOAManagerImpl.implicitActivation();
/*     */     } 
/*     */     
/* 111 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object activateTie(Tie paramTie) {
/* 125 */     if (paramTie instanceof ObjectImpl)
/* 126 */       return paramTie.thisObject(); 
/* 127 */     if (paramTie instanceof Servant) {
/* 128 */       Servant servant = (Servant)paramTie;
/* 129 */       return activateServant(servant);
/*     */     } 
/* 131 */     throw wrapper.badActivateTieCall();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Delegate getDelegate(Object paramObject) {
/* 141 */     if (paramObject instanceof DynamicStub)
/* 142 */       return ((DynamicStub)paramObject).getDelegate(); 
/* 143 */     if (paramObject instanceof ObjectImpl)
/* 144 */       return ((ObjectImpl)paramObject)._get_delegate(); 
/* 145 */     if (paramObject instanceof Tie) {
/* 146 */       Tie tie = (Tie)paramObject;
/* 147 */       Object object = activateTie(tie);
/* 148 */       return getDelegate(object);
/*     */     } 
/* 150 */     throw wrapper.getDelegateRequiresStub();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ORB getORB(Object paramObject) {
/* 155 */     if (paramObject instanceof DynamicStub)
/* 156 */       return ((DynamicStub)paramObject).getORB(); 
/* 157 */     if (paramObject instanceof ObjectImpl) {
/* 158 */       return ((ObjectImpl)paramObject)._orb();
/*     */     }
/* 160 */     throw wrapper.getOrbRequiresStub();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getTypeIds(Object paramObject) {
/* 165 */     if (paramObject instanceof DynamicStub)
/* 166 */       return ((DynamicStub)paramObject).getTypeIds(); 
/* 167 */     if (paramObject instanceof ObjectImpl) {
/* 168 */       return ((ObjectImpl)paramObject)._ids();
/*     */     }
/* 170 */     throw wrapper.getTypeIdsRequiresStub();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void connect(Object paramObject, ORB paramORB) throws RemoteException {
/* 176 */     if (paramObject instanceof DynamicStub) {
/* 177 */       ((DynamicStub)paramObject).connect(paramORB);
/*     */     }
/* 179 */     else if (paramObject instanceof Stub) {
/* 180 */       ((Stub)paramObject).connect(paramORB);
/* 181 */     } else if (paramObject instanceof ObjectImpl) {
/* 182 */       paramORB.connect((Object)paramObject);
/*     */     } else {
/* 184 */       throw wrapper.connectRequiresStub();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isLocal(Object paramObject) {
/* 189 */     if (paramObject instanceof DynamicStub)
/* 190 */       return ((DynamicStub)paramObject).isLocal(); 
/* 191 */     if (paramObject instanceof ObjectImpl) {
/* 192 */       return ((ObjectImpl)paramObject)._is_local();
/*     */     }
/* 194 */     throw wrapper.isLocalRequiresStub();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static OutputStream request(Object paramObject, String paramString, boolean paramBoolean) {
/* 200 */     if (paramObject instanceof DynamicStub) {
/* 201 */       return ((DynamicStub)paramObject).request(paramString, paramBoolean);
/*     */     }
/* 203 */     if (paramObject instanceof ObjectImpl) {
/* 204 */       return ((ObjectImpl)paramObject)._request(paramString, paramBoolean);
/*     */     }
/*     */     
/* 207 */     throw wrapper.requestRequiresStub();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/presentation/rmi/StubAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */