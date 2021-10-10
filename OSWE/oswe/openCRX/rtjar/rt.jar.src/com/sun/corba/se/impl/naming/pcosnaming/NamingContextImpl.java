/*      */ package com.sun.corba.se.impl.naming.pcosnaming;
/*      */ 
/*      */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*      */ import com.sun.corba.se.impl.naming.cosnaming.InterOperableNamingImpl;
/*      */ import com.sun.corba.se.impl.naming.cosnaming.NamingContextDataStore;
/*      */ import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
/*      */ import com.sun.corba.se.impl.naming.namingutil.INSURLHandler;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import java.io.Serializable;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import org.omg.CORBA.BAD_PARAM;
/*      */ import org.omg.CORBA.CompletionStatus;
/*      */ import org.omg.CORBA.ORB;
/*      */ import org.omg.CORBA.Object;
/*      */ import org.omg.CORBA.Policy;
/*      */ import org.omg.CORBA.SystemException;
/*      */ import org.omg.CosNaming.BindingIterator;
/*      */ import org.omg.CosNaming.BindingIteratorHelper;
/*      */ import org.omg.CosNaming.BindingIteratorHolder;
/*      */ import org.omg.CosNaming.BindingListHolder;
/*      */ import org.omg.CosNaming.BindingType;
/*      */ import org.omg.CosNaming.BindingTypeHolder;
/*      */ import org.omg.CosNaming.NameComponent;
/*      */ import org.omg.CosNaming.NamingContext;
/*      */ import org.omg.CosNaming.NamingContextExtPOA;
/*      */ import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
/*      */ import org.omg.CosNaming.NamingContextHelper;
/*      */ import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
/*      */ import org.omg.CosNaming.NamingContextPackage.CannotProceed;
/*      */ import org.omg.CosNaming.NamingContextPackage.InvalidName;
/*      */ import org.omg.CosNaming.NamingContextPackage.NotEmpty;
/*      */ import org.omg.CosNaming.NamingContextPackage.NotFound;
/*      */ import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
/*      */ import org.omg.PortableServer.IdAssignmentPolicyValue;
/*      */ import org.omg.PortableServer.LifespanPolicyValue;
/*      */ import org.omg.PortableServer.POA;
/*      */ import org.omg.PortableServer.Servant;
/*      */ import org.omg.PortableServer.ServantRetentionPolicyValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NamingContextImpl
/*      */   extends NamingContextExtPOA
/*      */   implements NamingContextDataStore, Serializable
/*      */ {
/*      */   private transient ORB orb;
/*      */   private final String objKey;
/*   93 */   private final Hashtable theHashtable = new Hashtable<>();
/*      */ 
/*      */ 
/*      */   
/*      */   private transient NameService theNameServiceHandle;
/*      */ 
/*      */   
/*      */   private transient ServantManagerImpl theServantManagerImplHandle;
/*      */ 
/*      */   
/*      */   private transient InterOperableNamingImpl insImpl;
/*      */ 
/*      */   
/*      */   private transient NamingSystemException readWrapper;
/*      */ 
/*      */   
/*      */   private transient NamingSystemException updateWrapper;
/*      */ 
/*      */   
/*  112 */   private static POA biPOA = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean debug;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamingContextImpl(ORB paramORB, String paramString, NameService paramNameService, ServantManagerImpl paramServantManagerImpl) throws Exception {
/*  130 */     this.orb = paramORB;
/*  131 */     this.readWrapper = NamingSystemException.get(paramORB, "naming.read");
/*      */     
/*  133 */     this.updateWrapper = NamingSystemException.get(paramORB, "naming.update");
/*      */ 
/*      */     
/*  136 */     debug = true;
/*  137 */     this.objKey = paramString;
/*  138 */     this.theNameServiceHandle = paramNameService;
/*  139 */     this.theServantManagerImplHandle = paramServantManagerImpl;
/*  140 */     this.insImpl = new InterOperableNamingImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   InterOperableNamingImpl getINSImpl() {
/*  146 */     if (this.insImpl == null)
/*      */     {
/*      */ 
/*      */       
/*  150 */       this.insImpl = new InterOperableNamingImpl();
/*      */     }
/*      */     
/*  153 */     return this.insImpl;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRootNameService(NameService paramNameService) {
/*  158 */     this.theNameServiceHandle = paramNameService;
/*      */   }
/*      */   
/*      */   public void setORB(ORB paramORB) {
/*  162 */     this.orb = paramORB;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServantManagerImpl(ServantManagerImpl paramServantManagerImpl) {
/*  168 */     this.theServantManagerImplHandle = paramServantManagerImpl;
/*      */   }
/*      */   
/*      */   public POA getNSPOA() {
/*  172 */     return this.theNameServiceHandle.getNSPOA();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  207 */     if (paramObject == null) {
/*  208 */       throw this.updateWrapper.objectIsNull();
/*      */     }
/*      */     
/*  211 */     if (debug) {
/*  212 */       dprint("bind " + nameToString(paramArrayOfNameComponent) + " to " + paramObject);
/*      */     }
/*  214 */     NamingContextImpl namingContextImpl = this;
/*  215 */     doBind(namingContextImpl, paramArrayOfNameComponent, paramObject, false, BindingType.nobject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  245 */     if (paramNamingContext == null) {
/*  246 */       throw this.updateWrapper.objectIsNull();
/*      */     }
/*      */     
/*  249 */     NamingContextImpl namingContextImpl = this;
/*  250 */     doBind(namingContextImpl, paramArrayOfNameComponent, (Object)paramNamingContext, false, BindingType.ncontext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rebind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName {
/*  279 */     if (paramObject == null)
/*      */     {
/*  281 */       throw this.updateWrapper.objectIsNull();
/*      */     }
/*      */     try {
/*  284 */       if (debug) {
/*  285 */         dprint("rebind " + nameToString(paramArrayOfNameComponent) + " to " + paramObject);
/*      */       }
/*  287 */       NamingContextImpl namingContextImpl = this;
/*  288 */       doBind(namingContextImpl, paramArrayOfNameComponent, paramObject, true, BindingType.nobject);
/*  289 */     } catch (AlreadyBound alreadyBound) {
/*      */       
/*  291 */       throw this.updateWrapper.namingCtxRebindAlreadyBound(alreadyBound);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rebind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName {
/*      */     try {
/*  321 */       if (debug) {
/*  322 */         dprint("rebind_context " + nameToString(paramArrayOfNameComponent) + " to " + paramNamingContext);
/*      */       }
/*  324 */       NamingContextImpl namingContextImpl = this;
/*  325 */       doBind(namingContextImpl, paramArrayOfNameComponent, (Object)paramNamingContext, true, BindingType.ncontext);
/*  326 */     } catch (AlreadyBound alreadyBound) {
/*      */       
/*  328 */       throw this.updateWrapper.namingCtxRebindAlreadyBound(alreadyBound);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object resolve(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  356 */     if (debug) {
/*  357 */       dprint("resolve " + nameToString(paramArrayOfNameComponent));
/*      */     }
/*  359 */     NamingContextImpl namingContextImpl = this;
/*  360 */     return doResolve(namingContextImpl, paramArrayOfNameComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unbind(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  384 */     if (debug) {
/*  385 */       dprint("unbind " + nameToString(paramArrayOfNameComponent));
/*      */     }
/*  387 */     NamingContextImpl namingContextImpl = this;
/*  388 */     doUnbind(namingContextImpl, paramArrayOfNameComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void list(int paramInt, BindingListHolder paramBindingListHolder, BindingIteratorHolder paramBindingIteratorHolder) {
/*  405 */     if (debug) {
/*  406 */       dprint("list(" + paramInt + ")");
/*      */     }
/*  408 */     NamingContextImpl namingContextImpl = this;
/*  409 */     synchronized (namingContextImpl) {
/*  410 */       namingContextImpl.List(paramInt, paramBindingListHolder, paramBindingIteratorHolder);
/*      */     } 
/*  412 */     if (debug && paramBindingListHolder.value != null) {
/*  413 */       dprint("list(" + paramInt + ") -> bindings[" + paramBindingListHolder.value.length + "] + iterator: " + paramBindingIteratorHolder.value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized NamingContext new_context() {
/*  427 */     if (debug)
/*  428 */       dprint("new_context()"); 
/*  429 */     NamingContextImpl namingContextImpl = this;
/*  430 */     synchronized (namingContextImpl) {
/*  431 */       return namingContextImpl.NewContext();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamingContext bind_new_context(NameComponent[] paramArrayOfNameComponent) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
/*  463 */     NamingContext namingContext1 = null;
/*  464 */     NamingContext namingContext2 = null;
/*      */     try {
/*  466 */       if (debug) {
/*  467 */         dprint("bind_new_context " + nameToString(paramArrayOfNameComponent));
/*      */       }
/*  469 */       namingContext1 = new_context();
/*  470 */       bind_context(paramArrayOfNameComponent, namingContext1);
/*  471 */       namingContext2 = namingContext1;
/*  472 */       namingContext1 = null;
/*      */     } finally {
/*      */       try {
/*  475 */         if (namingContext1 != null)
/*  476 */           namingContext1.destroy(); 
/*  477 */       } catch (NotEmpty notEmpty) {}
/*      */     } 
/*      */     
/*  480 */     return namingContext2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void destroy() throws NotEmpty {
/*  493 */     if (debug)
/*  494 */       dprint("destroy "); 
/*  495 */     NamingContextImpl namingContextImpl = this;
/*  496 */     synchronized (namingContextImpl) {
/*  497 */       if (namingContextImpl.IsEmpty() == true) {
/*      */         
/*  499 */         namingContextImpl.Destroy();
/*      */       } else {
/*      */         
/*  502 */         throw new NotEmpty();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doBind(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent, Object paramObject, boolean paramBoolean, BindingType paramBindingType) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  550 */     if (paramArrayOfNameComponent.length < 1) {
/*  551 */       throw new InvalidName();
/*      */     }
/*      */     
/*  554 */     if (paramArrayOfNameComponent.length == 1) {
/*      */       
/*  556 */       if ((paramArrayOfNameComponent[0]).id.length() == 0 && (paramArrayOfNameComponent[0]).kind.length() == 0) {
/*  557 */         throw new InvalidName();
/*      */       }
/*      */       
/*  560 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  562 */         BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*  563 */         if (paramBoolean) {
/*  564 */           Object object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*  565 */           if (object != null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  572 */             if (bindingTypeHolder.value.value() == BindingType.nobject.value()) {
/*  573 */               if (paramBindingType.value() == BindingType.ncontext.value()) {
/*  574 */                 throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */               
/*      */               }
/*      */             
/*      */             }
/*  579 */             else if (paramBindingType.value() == BindingType.nobject.value()) {
/*  580 */               throw new NotFound(NotFoundReason.not_object, paramArrayOfNameComponent);
/*      */             } 
/*      */             
/*  583 */             paramNamingContextDataStore.Unbind(paramArrayOfNameComponent[0]);
/*      */           }
/*      */         
/*  586 */         } else if (paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder) != null) {
/*  587 */           throw new AlreadyBound();
/*      */         } 
/*      */ 
/*      */         
/*  591 */         paramNamingContextDataStore.Bind(paramArrayOfNameComponent[0], paramObject, paramBindingType);
/*      */       } 
/*      */     } else {
/*      */       
/*  595 */       NamingContext namingContext2, namingContext1 = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */       
/*  598 */       NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  599 */       System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */       
/*  602 */       switch (paramBindingType.value()) {
/*      */ 
/*      */         
/*      */         case 0:
/*  606 */           if (paramBoolean) {
/*  607 */             namingContext1.rebind(arrayOfNameComponent, paramObject);
/*      */           } else {
/*  609 */             namingContext1.bind(arrayOfNameComponent, paramObject);
/*      */           } 
/*      */           return;
/*      */ 
/*      */         
/*      */         case 1:
/*  615 */           namingContext2 = (NamingContext)paramObject;
/*      */           
/*  617 */           if (paramBoolean) {
/*  618 */             namingContext1.rebind_context(arrayOfNameComponent, namingContext2);
/*      */           } else {
/*  620 */             namingContext1.bind_context(arrayOfNameComponent, namingContext2);
/*      */           } 
/*      */           return;
/*      */       } 
/*      */       
/*  625 */       throw this.updateWrapper.namingCtxBadBindingtype();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object doResolve(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  660 */     Object object = null;
/*  661 */     BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*      */ 
/*      */     
/*  664 */     if (paramArrayOfNameComponent.length < 1) {
/*  665 */       throw new InvalidName();
/*      */     }
/*      */     
/*  668 */     if (paramArrayOfNameComponent.length == 1) {
/*  669 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  671 */         object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*      */       } 
/*  673 */       if (object == null)
/*      */       {
/*  675 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*  677 */       return object;
/*      */     } 
/*      */     
/*  680 */     if ((paramArrayOfNameComponent[1]).id.length() == 0 && (paramArrayOfNameComponent[1]).kind.length() == 0) {
/*  681 */       throw new InvalidName();
/*      */     }
/*  683 */     NamingContext namingContext = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */     
/*  686 */     NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  687 */     System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */     
/*  690 */     return namingContext.resolve(arrayOfNameComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void doUnbind(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  721 */     if (paramArrayOfNameComponent.length < 1) {
/*  722 */       throw new InvalidName();
/*      */     }
/*      */     
/*  725 */     if (paramArrayOfNameComponent.length == 1) {
/*      */       
/*  727 */       if ((paramArrayOfNameComponent[0]).id.length() == 0 && (paramArrayOfNameComponent[0]).kind.length() == 0) {
/*  728 */         throw new InvalidName();
/*      */       }
/*  730 */       Object object = null;
/*  731 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  733 */         object = paramNamingContextDataStore.Unbind(paramArrayOfNameComponent[0]);
/*      */       } 
/*      */       
/*  736 */       if (object == null)
/*      */       {
/*  738 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  745 */     NamingContext namingContext = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */     
/*  748 */     NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  749 */     System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */     
/*  752 */     namingContext.unbind(arrayOfNameComponent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static NamingContext resolveFirstAsContext(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent) throws NotFound {
/*  772 */     Object object = null;
/*  773 */     BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*  774 */     NamingContext namingContext = null;
/*      */     
/*  776 */     synchronized (paramNamingContextDataStore) {
/*      */       
/*  778 */       object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*  779 */       if (object == null)
/*      */       {
/*  781 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  786 */     if (bindingTypeHolder.value != BindingType.ncontext)
/*      */     {
/*  788 */       throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  793 */       namingContext = NamingContextHelper.narrow(object);
/*  794 */     } catch (BAD_PARAM bAD_PARAM) {
/*      */       
/*  796 */       throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */     } 
/*      */ 
/*      */     
/*  800 */     return namingContext;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String nameToString(NameComponent[] paramArrayOfNameComponent) {
/*  805 */     StringBuffer stringBuffer = new StringBuffer("{");
/*  806 */     if (paramArrayOfNameComponent != null || paramArrayOfNameComponent.length > 0) {
/*  807 */       for (byte b = 0; b < paramArrayOfNameComponent.length; b++) {
/*  808 */         if (b > 0)
/*  809 */           stringBuffer.append(","); 
/*  810 */         stringBuffer.append("[")
/*  811 */           .append((paramArrayOfNameComponent[b]).id)
/*  812 */           .append(",")
/*  813 */           .append((paramArrayOfNameComponent[b]).kind)
/*  814 */           .append("]");
/*      */       } 
/*      */     }
/*  817 */     stringBuffer.append("}");
/*  818 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dprint(String paramString) {
/*  825 */     NamingUtils.dprint("NamingContextImpl(" + 
/*  826 */         Thread.currentThread().getName() + " at " + 
/*  827 */         System.currentTimeMillis() + " ems): " + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void Bind(NameComponent paramNameComponent, Object paramObject, BindingType paramBindingType) {
/*  857 */     if (paramObject == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  862 */     InternalBindingKey internalBindingKey = new InternalBindingKey(paramNameComponent);
/*      */     
/*      */     try {
/*      */       InternalBindingValue internalBindingValue1;
/*  866 */       if (paramBindingType.value() == 0) {
/*      */ 
/*      */ 
/*      */         
/*  870 */         internalBindingValue1 = new InternalBindingValue(paramBindingType, this.orb.object_to_string(paramObject));
/*  871 */         internalBindingValue1.setObjectRef(paramObject);
/*      */       }
/*      */       else {
/*      */         
/*  875 */         String str = this.theNameServiceHandle.getObjectKey(paramObject);
/*  876 */         internalBindingValue1 = new InternalBindingValue(paramBindingType, str);
/*  877 */         internalBindingValue1.setObjectRef(paramObject);
/*      */       } 
/*      */ 
/*      */       
/*  881 */       InternalBindingValue internalBindingValue2 = this.theHashtable.put(internalBindingKey, internalBindingValue1);
/*      */       
/*  883 */       if (internalBindingValue2 != null)
/*      */       {
/*      */         
/*  886 */         throw this.updateWrapper.namingCtxRebindAlreadyBound();
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/*  891 */         this.theServantManagerImplHandle.updateContext(this.objKey, this);
/*  892 */       } catch (Exception exception) {
/*      */ 
/*      */         
/*  895 */         throw this.updateWrapper.bindUpdateContextFailed(exception);
/*      */       }
/*      */     
/*  898 */     } catch (Exception exception) {
/*      */ 
/*      */       
/*  901 */       throw this.updateWrapper.bindFailure(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object Resolve(NameComponent paramNameComponent, BindingTypeHolder paramBindingTypeHolder) throws SystemException {
/*  924 */     if (paramNameComponent.id.length() == 0 && paramNameComponent.kind.length() == 0) {
/*      */ 
/*      */       
/*  927 */       paramBindingTypeHolder.value = BindingType.ncontext;
/*  928 */       return this.theNameServiceHandle.getObjectReferenceFromKey(this.objKey);
/*      */     } 
/*      */ 
/*      */     
/*  932 */     InternalBindingKey internalBindingKey = new InternalBindingKey(paramNameComponent);
/*      */     
/*  934 */     InternalBindingValue internalBindingValue = (InternalBindingValue)this.theHashtable.get(internalBindingKey);
/*      */     
/*  936 */     if (internalBindingValue == null)
/*      */     {
/*      */ 
/*      */       
/*  940 */       return null;
/*      */     }
/*      */     
/*  943 */     Object object = null;
/*  944 */     paramBindingTypeHolder.value = internalBindingValue.theBindingType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  951 */       if (internalBindingValue.strObjectRef.startsWith("NC")) {
/*  952 */         paramBindingTypeHolder.value = BindingType.ncontext;
/*  953 */         return this.theNameServiceHandle.getObjectReferenceFromKey(internalBindingValue.strObjectRef);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  958 */       object = internalBindingValue.getObjectRef();
/*      */       
/*  960 */       if (object == null) {
/*      */         
/*      */         try {
/*  963 */           object = this.orb.string_to_object(internalBindingValue.strObjectRef);
/*  964 */           internalBindingValue.setObjectRef(object);
/*  965 */         } catch (Exception exception) {
/*  966 */           throw this.readWrapper.resolveConversionFailure(CompletionStatus.COMPLETED_MAYBE, exception);
/*      */         }
/*      */       
/*      */       }
/*      */     }
/*  971 */     catch (Exception exception) {
/*  972 */       throw this.readWrapper.resolveFailure(CompletionStatus.COMPLETED_MAYBE, exception);
/*      */     } 
/*      */ 
/*      */     
/*  976 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object Unbind(NameComponent paramNameComponent) throws SystemException {
/*      */     try {
/*  998 */       InternalBindingKey internalBindingKey = new InternalBindingKey(paramNameComponent);
/*  999 */       InternalBindingValue internalBindingValue = null;
/*      */       
/*      */       try {
/* 1002 */         internalBindingValue = (InternalBindingValue)this.theHashtable.remove(internalBindingKey);
/* 1003 */       } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */       
/* 1007 */       this.theServantManagerImplHandle.updateContext(this.objKey, this);
/*      */       
/* 1009 */       if (internalBindingValue == null) {
/* 1010 */         return null;
/*      */       }
/*      */       
/* 1013 */       if (internalBindingValue.strObjectRef.startsWith("NC")) {
/* 1014 */         this.theServantManagerImplHandle.readInContext(internalBindingValue.strObjectRef);
/*      */         
/* 1016 */         return this.theNameServiceHandle.getObjectReferenceFromKey(internalBindingValue.strObjectRef);
/*      */       } 
/*      */       
/* 1019 */       Object object = internalBindingValue.getObjectRef();
/*      */       
/* 1021 */       if (object == null)
/*      */       {
/* 1023 */         object = this.orb.string_to_object(internalBindingValue.strObjectRef);
/*      */       }
/*      */       
/* 1026 */       return object;
/*      */     }
/* 1028 */     catch (Exception exception) {
/* 1029 */       throw this.updateWrapper.unbindFailure(CompletionStatus.COMPLETED_MAYBE, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void List(int paramInt, BindingListHolder paramBindingListHolder, BindingIteratorHolder paramBindingIteratorHolder) throws SystemException {
/* 1047 */     if (biPOA == null) {
/* 1048 */       createbiPOA();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1053 */       PersistentBindingIterator persistentBindingIterator = new PersistentBindingIterator((ORB)this.orb, (Hashtable)this.theHashtable.clone(), biPOA);
/*      */       
/* 1055 */       persistentBindingIterator.list(paramInt, paramBindingListHolder);
/*      */       
/* 1057 */       byte[] arrayOfByte = biPOA.activate_object((Servant)persistentBindingIterator);
/* 1058 */       Object object = biPOA.id_to_reference(arrayOfByte);
/*      */ 
/*      */ 
/*      */       
/* 1062 */       BindingIterator bindingIterator = BindingIteratorHelper.narrow(object);
/*      */       
/* 1064 */       paramBindingIteratorHolder.value = bindingIterator;
/* 1065 */     } catch (SystemException systemException) {
/* 1066 */       throw systemException;
/* 1067 */     } catch (Exception exception) {
/* 1068 */       throw this.readWrapper.transNcListGotExc(exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private synchronized void createbiPOA() {
/* 1073 */     if (biPOA != null) {
/*      */       return;
/*      */     }
/*      */     try {
/* 1077 */       POA pOA = (POA)this.orb.resolve_initial_references("RootPOA");
/*      */       
/* 1079 */       pOA.the_POAManager().activate();
/*      */       
/* 1081 */       byte b = 0;
/* 1082 */       Policy[] arrayOfPolicy = new Policy[3];
/* 1083 */       arrayOfPolicy[b++] = (Policy)pOA.create_lifespan_policy(LifespanPolicyValue.TRANSIENT);
/*      */       
/* 1085 */       arrayOfPolicy[b++] = (Policy)pOA.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID);
/*      */       
/* 1087 */       arrayOfPolicy[b++] = (Policy)pOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN);
/*      */       
/* 1089 */       biPOA = pOA.create_POA("BindingIteratorPOA", null, arrayOfPolicy);
/* 1090 */       biPOA.the_POAManager().activate();
/* 1091 */     } catch (Exception exception) {
/* 1092 */       throw this.readWrapper.namingCtxBindingIteratorCreate(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NamingContext NewContext() throws SystemException {
/*      */     try {
/* 1106 */       return this.theNameServiceHandle.NewContext();
/* 1107 */     } catch (SystemException systemException) {
/* 1108 */       throw systemException;
/* 1109 */     } catch (Exception exception) {
/* 1110 */       throw this.updateWrapper.transNcNewctxGotExc(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void Destroy() throws SystemException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String to_string(NameComponent[] paramArrayOfNameComponent) throws InvalidName {
/* 1152 */     if (paramArrayOfNameComponent == null || paramArrayOfNameComponent.length == 0)
/*      */     {
/* 1154 */       throw new InvalidName();
/*      */     }
/*      */     
/* 1157 */     String str = getINSImpl().convertToString(paramArrayOfNameComponent);
/*      */     
/* 1159 */     if (str == null)
/*      */     {
/* 1161 */       throw new InvalidName();
/*      */     }
/*      */     
/* 1164 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameComponent[] to_name(String paramString) throws InvalidName {
/* 1179 */     if (paramString == null || paramString.length() == 0)
/*      */     {
/* 1181 */       throw new InvalidName();
/*      */     }
/*      */     
/* 1184 */     NameComponent[] arrayOfNameComponent = getINSImpl().convertToNameComponent(paramString);
/* 1185 */     if (arrayOfNameComponent == null || arrayOfNameComponent.length == 0)
/*      */     {
/* 1187 */       throw new InvalidName();
/*      */     }
/* 1189 */     for (byte b = 0; b < arrayOfNameComponent.length; b++) {
/*      */ 
/*      */ 
/*      */       
/* 1193 */       if (((arrayOfNameComponent[b]).id == null || (arrayOfNameComponent[b]).id
/* 1194 */         .length() == 0) && ((arrayOfNameComponent[b]).kind == null || (arrayOfNameComponent[b]).kind
/*      */         
/* 1196 */         .length() == 0)) {
/* 1197 */         throw new InvalidName();
/*      */       }
/*      */     } 
/* 1200 */     return arrayOfNameComponent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String to_url(String paramString1, String paramString2) throws InvalidAddress, InvalidName {
/* 1222 */     if (paramString2 == null || paramString2.length() == 0)
/*      */     {
/* 1224 */       throw new InvalidName();
/*      */     }
/* 1226 */     if (paramString1 == null)
/*      */     {
/* 1228 */       throw new InvalidAddress();
/*      */     }
/* 1230 */     String str = null;
/*      */     try {
/* 1232 */       str = getINSImpl().createURLBasedAddress(paramString1, paramString2);
/* 1233 */     } catch (Exception exception) {
/* 1234 */       str = null;
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1239 */       INSURLHandler.getINSURLHandler().parseURL(str);
/* 1240 */     } catch (BAD_PARAM bAD_PARAM) {
/* 1241 */       throw new InvalidAddress();
/*      */     } 
/*      */     
/* 1244 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object resolve_str(String paramString) throws NotFound, CannotProceed, InvalidName {
/* 1264 */     Object object = null;
/*      */     
/* 1266 */     if (paramString == null || paramString.length() == 0)
/*      */     {
/* 1268 */       throw new InvalidName();
/*      */     }
/*      */     
/* 1271 */     NameComponent[] arrayOfNameComponent = getINSImpl().convertToNameComponent(paramString);
/* 1272 */     if (arrayOfNameComponent == null || arrayOfNameComponent.length == 0)
/*      */     {
/* 1274 */       throw new InvalidName();
/*      */     }
/* 1276 */     object = resolve(arrayOfNameComponent);
/* 1277 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean IsEmpty() {
/* 1285 */     return this.theHashtable.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printSize() {
/* 1293 */     System.out.println("Hashtable Size = " + this.theHashtable.size());
/* 1294 */     Enumeration enumeration = this.theHashtable.keys();
/* 1295 */     while (enumeration.hasMoreElements()) {
/*      */ 
/*      */       
/* 1298 */       InternalBindingValue internalBindingValue = (InternalBindingValue)this.theHashtable.get(enumeration.nextElement());
/* 1299 */       if (internalBindingValue != null)
/*      */       {
/* 1301 */         System.out.println("value = " + internalBindingValue.strObjectRef);
/*      */       }
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/pcosnaming/NamingContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */