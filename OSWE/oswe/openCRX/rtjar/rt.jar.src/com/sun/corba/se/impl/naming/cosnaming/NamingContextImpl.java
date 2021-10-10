/*      */ package com.sun.corba.se.impl.naming.cosnaming;
/*      */ 
/*      */ import com.sun.corba.se.impl.logging.NamingSystemException;
/*      */ import com.sun.corba.se.impl.naming.namingutil.INSURLHandler;
/*      */ import com.sun.corba.se.spi.orb.ORB;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import org.omg.CORBA.BAD_PARAM;
/*      */ import org.omg.CORBA.Object;
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
/*      */ import org.omg.PortableServer.POA;
/*      */ import org.omg.PortableServer.Servant;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class NamingContextImpl
/*      */   extends NamingContextExtPOA
/*      */   implements NamingContextDataStore
/*      */ {
/*      */   protected POA nsPOA;
/*      */   private Logger readLogger;
/*      */   private Logger updateLogger;
/*      */   private Logger lifecycleLogger;
/*      */   private NamingSystemException wrapper;
/*   99 */   private static NamingSystemException staticWrapper = NamingSystemException.get("naming.update");
/*      */ 
/*      */   
/*      */   private InterOperableNamingImpl insImpl;
/*      */ 
/*      */   
/*      */   protected transient ORB orb;
/*      */ 
/*      */   
/*      */   public static final boolean debug = false;
/*      */ 
/*      */   
/*      */   public NamingContextImpl(ORB paramORB, POA paramPOA) throws Exception {
/*  112 */     this.orb = paramORB;
/*  113 */     this.wrapper = NamingSystemException.get(paramORB, "naming.update");
/*      */ 
/*      */     
/*  116 */     this.insImpl = new InterOperableNamingImpl();
/*  117 */     this.nsPOA = paramPOA;
/*  118 */     this.readLogger = paramORB.getLogger("naming.read");
/*  119 */     this.updateLogger = paramORB.getLogger("naming.update");
/*  120 */     this.lifecycleLogger = paramORB.getLogger("naming.lifecycle");
/*      */   }
/*      */ 
/*      */   
/*      */   public POA getNSPOA() {
/*  125 */     return this.nsPOA;
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
/*      */   public void bind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  158 */     if (paramObject == null) {
/*      */       
/*  160 */       this.updateLogger.warning("<<NAMING BIND>> unsuccessful because NULL Object cannot be Bound ");
/*      */       
/*  162 */       throw this.wrapper.objectIsNull();
/*      */     } 
/*      */     
/*  165 */     NamingContextImpl namingContextImpl = this;
/*  166 */     doBind(namingContextImpl, paramArrayOfNameComponent, paramObject, false, BindingType.nobject);
/*  167 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  170 */       this.updateLogger.fine("<<NAMING BIND>><<SUCCESS>> Name = " + 
/*  171 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
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
/*      */   public void bind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  204 */     if (paramNamingContext == null) {
/*  205 */       this.updateLogger.warning("<<NAMING BIND>><<FAILURE>> NULL Context cannot be Bound ");
/*      */       
/*  207 */       throw new BAD_PARAM("Naming Context should not be null ");
/*      */     } 
/*      */     
/*  210 */     NamingContextImpl namingContextImpl = this;
/*  211 */     doBind(namingContextImpl, paramArrayOfNameComponent, (Object)paramNamingContext, false, BindingType.ncontext);
/*  212 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  215 */       this.updateLogger.fine("<<NAMING BIND>><<SUCCESS>> Name = " + 
/*  216 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
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
/*      */   public void rebind(NameComponent[] paramArrayOfNameComponent, Object paramObject) throws NotFound, CannotProceed, InvalidName {
/*  247 */     if (paramObject == null) {
/*      */       
/*  249 */       this.updateLogger.warning("<<NAMING REBIND>><<FAILURE>> NULL Object cannot be Bound ");
/*      */       
/*  251 */       throw this.wrapper.objectIsNull();
/*      */     } 
/*      */     
/*      */     try {
/*  255 */       NamingContextImpl namingContextImpl = this;
/*  256 */       doBind(namingContextImpl, paramArrayOfNameComponent, paramObject, true, BindingType.nobject);
/*  257 */     } catch (AlreadyBound alreadyBound) {
/*  258 */       this.updateLogger.warning("<<NAMING REBIND>><<FAILURE>>" + 
/*  259 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent) + " is already bound to a Naming Context");
/*      */ 
/*      */       
/*  262 */       throw this.wrapper.namingCtxRebindAlreadyBound(alreadyBound);
/*      */     } 
/*  264 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  267 */       this.updateLogger.fine("<<NAMING REBIND>><<SUCCESS>> Name = " + 
/*  268 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
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
/*      */   public void rebind_context(NameComponent[] paramArrayOfNameComponent, NamingContext paramNamingContext) throws NotFound, CannotProceed, InvalidName {
/*  298 */     if (paramNamingContext == null) {
/*      */       
/*  300 */       this.updateLogger.warning("<<NAMING REBIND>><<FAILURE>> NULL Context cannot be Bound ");
/*      */       
/*  302 */       throw this.wrapper.objectIsNull();
/*      */     } 
/*      */     
/*      */     try {
/*  306 */       NamingContextImpl namingContextImpl = this;
/*  307 */       doBind(namingContextImpl, paramArrayOfNameComponent, (Object)paramNamingContext, true, BindingType.ncontext);
/*  308 */     } catch (AlreadyBound alreadyBound) {
/*      */       
/*  310 */       this.updateLogger.warning("<<NAMING REBIND>><<FAILURE>>" + 
/*  311 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent) + " is already bound to a CORBA Object");
/*      */       
/*  313 */       throw this.wrapper.namingCtxRebindctxAlreadyBound(alreadyBound);
/*      */     } 
/*  315 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  318 */       this.updateLogger.fine("<<NAMING REBIND>><<SUCCESS>> Name = " + 
/*  319 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
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
/*      */   public Object resolve(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  349 */     NamingContextImpl namingContextImpl = this;
/*  350 */     Object object = doResolve(namingContextImpl, paramArrayOfNameComponent);
/*  351 */     if (object != null) {
/*  352 */       if (this.readLogger.isLoggable(Level.FINE)) {
/*  353 */         this.readLogger.fine("<<NAMING RESOLVE>><<SUCCESS>> Name: " + 
/*  354 */             NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
/*      */       }
/*      */     } else {
/*  357 */       this.readLogger.warning("<<NAMING RESOLVE>><<FAILURE>> Name: " + 
/*  358 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
/*      */     } 
/*  360 */     return object;
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
/*      */   public void unbind(NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  387 */     NamingContextImpl namingContextImpl = this;
/*  388 */     doUnbind(namingContextImpl, paramArrayOfNameComponent);
/*  389 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  392 */       this.updateLogger.fine("<<NAMING UNBIND>><<SUCCESS>> Name: " + 
/*  393 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
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
/*      */   public void list(int paramInt, BindingListHolder paramBindingListHolder, BindingIteratorHolder paramBindingIteratorHolder) {
/*  414 */     NamingContextImpl namingContextImpl = this;
/*  415 */     synchronized (namingContextImpl) {
/*  416 */       namingContextImpl.List(paramInt, paramBindingListHolder, paramBindingIteratorHolder);
/*      */     } 
/*  418 */     if (this.readLogger.isLoggable(Level.FINE) && paramBindingListHolder.value != null)
/*      */     {
/*      */       
/*  421 */       this.readLogger.fine("<<NAMING LIST>><<SUCCESS>>list(" + paramInt + ") -> bindings[" + paramBindingListHolder.value.length + "] + iterator: " + paramBindingIteratorHolder.value);
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
/*      */   public synchronized NamingContext new_context() {
/*  437 */     this.lifecycleLogger.fine("Creating New Naming Context ");
/*  438 */     NamingContextImpl namingContextImpl = this;
/*  439 */     synchronized (namingContextImpl) {
/*  440 */       NamingContext namingContext = namingContextImpl.NewContext();
/*  441 */       if (namingContext != null) {
/*  442 */         this.lifecycleLogger.fine("<<LIFECYCLE CREATE>><<SUCCESS>>");
/*      */       }
/*      */       else {
/*      */         
/*  446 */         this.lifecycleLogger.severe("<<LIFECYCLE CREATE>><<FAILURE>>");
/*      */       } 
/*  448 */       return namingContext;
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
/*  480 */     NamingContext namingContext1 = null;
/*  481 */     NamingContext namingContext2 = null;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  486 */       namingContext1 = new_context();
/*  487 */       bind_context(paramArrayOfNameComponent, namingContext1);
/*  488 */       namingContext2 = namingContext1;
/*  489 */       namingContext1 = null;
/*      */     } finally {
/*      */       try {
/*  492 */         if (namingContext1 != null)
/*  493 */           namingContext1.destroy(); 
/*  494 */       } catch (NotEmpty notEmpty) {}
/*      */     } 
/*      */     
/*  497 */     if (this.updateLogger.isLoggable(Level.FINE))
/*      */     {
/*      */       
/*  500 */       this.updateLogger.fine("<<NAMING BIND>>New Context Bound To " + 
/*      */           
/*  502 */           NamingUtils.getDirectoryStructuredName(paramArrayOfNameComponent));
/*      */     }
/*  504 */     return namingContext2;
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
/*      */   public void destroy() throws NotEmpty {
/*  518 */     this.lifecycleLogger.fine("Destroying Naming Context ");
/*  519 */     NamingContextImpl namingContextImpl = this;
/*  520 */     synchronized (namingContextImpl) {
/*  521 */       if (namingContextImpl.IsEmpty() == true) {
/*      */         
/*  523 */         namingContextImpl.Destroy();
/*  524 */         this.lifecycleLogger.fine("<<LIFECYCLE DESTROY>><<SUCCESS>>");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  529 */         this.lifecycleLogger.warning("<<LIFECYCLE DESTROY>><<FAILURE>> NamingContext children are not destroyed still..");
/*      */         
/*  531 */         throw new NotEmpty();
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
/*      */ 
/*      */   
/*      */   public static void doBind(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent, Object paramObject, boolean paramBoolean, BindingType paramBindingType) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
/*  581 */     if (paramArrayOfNameComponent.length < 1) {
/*  582 */       throw new InvalidName();
/*      */     }
/*      */     
/*  585 */     if (paramArrayOfNameComponent.length == 1) {
/*      */       
/*  587 */       if ((paramArrayOfNameComponent[0]).id.length() == 0 && (paramArrayOfNameComponent[0]).kind.length() == 0) {
/*  588 */         throw new InvalidName();
/*      */       }
/*      */ 
/*      */       
/*  592 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  594 */         BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*  595 */         if (paramBoolean) {
/*  596 */           Object object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*  597 */           if (object != null)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  604 */             if (bindingTypeHolder.value.value() == BindingType.nobject.value()) {
/*  605 */               if (paramBindingType.value() == BindingType.ncontext.value()) {
/*  606 */                 throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */ 
/*      */               
/*      */               }
/*      */             
/*      */             }
/*  612 */             else if (paramBindingType.value() == BindingType.nobject.value()) {
/*  613 */               throw new NotFound(NotFoundReason.not_object, paramArrayOfNameComponent);
/*      */             } 
/*      */ 
/*      */             
/*  617 */             paramNamingContextDataStore.Unbind(paramArrayOfNameComponent[0]);
/*      */           }
/*      */         
/*      */         }
/*  621 */         else if (paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder) != null) {
/*      */           
/*  623 */           throw new AlreadyBound();
/*      */         } 
/*      */ 
/*      */         
/*  627 */         paramNamingContextDataStore.Bind(paramArrayOfNameComponent[0], paramObject, paramBindingType);
/*      */       } 
/*      */     } else {
/*      */       
/*  631 */       NamingContext namingContext2, namingContext1 = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */       
/*  634 */       NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  635 */       System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */       
/*  638 */       switch (paramBindingType.value()) {
/*      */ 
/*      */         
/*      */         case 0:
/*  642 */           if (paramBoolean) {
/*  643 */             namingContext1.rebind(arrayOfNameComponent, paramObject);
/*      */           } else {
/*  645 */             namingContext1.bind(arrayOfNameComponent, paramObject);
/*      */           } 
/*      */           return;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/*  652 */           namingContext2 = (NamingContext)paramObject;
/*      */           
/*  654 */           if (paramBoolean) {
/*  655 */             namingContext1.rebind_context(arrayOfNameComponent, namingContext2);
/*      */           } else {
/*  657 */             namingContext1.bind_context(arrayOfNameComponent, namingContext2);
/*      */           } 
/*      */           return;
/*      */       } 
/*      */       
/*  662 */       throw staticWrapper.namingCtxBadBindingtype();
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
/*      */   public static Object doResolve(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  698 */     Object object = null;
/*  699 */     BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*      */ 
/*      */ 
/*      */     
/*  703 */     if (paramArrayOfNameComponent.length < 1) {
/*  704 */       throw new InvalidName();
/*      */     }
/*      */     
/*  707 */     if (paramArrayOfNameComponent.length == 1) {
/*  708 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  710 */         object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*      */       } 
/*  712 */       if (object == null)
/*      */       {
/*  714 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*  716 */       return object;
/*      */     } 
/*      */     
/*  719 */     if ((paramArrayOfNameComponent[1]).id.length() == 0 && (paramArrayOfNameComponent[1]).kind.length() == 0) {
/*  720 */       throw new InvalidName();
/*      */     }
/*      */     
/*  723 */     NamingContext namingContext = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */     
/*  726 */     NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  727 */     System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  733 */       Servant servant = paramNamingContextDataStore.getNSPOA().reference_to_servant((Object)namingContext);
/*      */       
/*  735 */       return doResolve((NamingContextDataStore)servant, arrayOfNameComponent);
/*  736 */     } catch (Exception exception) {
/*  737 */       return namingContext.resolve(arrayOfNameComponent);
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
/*      */   public static void doUnbind(NamingContextDataStore paramNamingContextDataStore, NameComponent[] paramArrayOfNameComponent) throws NotFound, CannotProceed, InvalidName {
/*  769 */     if (paramArrayOfNameComponent.length < 1) {
/*  770 */       throw new InvalidName();
/*      */     }
/*      */     
/*  773 */     if (paramArrayOfNameComponent.length == 1) {
/*      */       
/*  775 */       if ((paramArrayOfNameComponent[0]).id.length() == 0 && (paramArrayOfNameComponent[0]).kind.length() == 0) {
/*  776 */         throw new InvalidName();
/*      */       }
/*      */       
/*  779 */       Object object = null;
/*  780 */       synchronized (paramNamingContextDataStore) {
/*      */         
/*  782 */         object = paramNamingContextDataStore.Unbind(paramArrayOfNameComponent[0]);
/*      */       } 
/*      */       
/*  785 */       if (object == null)
/*      */       {
/*  787 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  794 */     NamingContext namingContext = resolveFirstAsContext(paramNamingContextDataStore, paramArrayOfNameComponent);
/*      */ 
/*      */     
/*  797 */     NameComponent[] arrayOfNameComponent = new NameComponent[paramArrayOfNameComponent.length - 1];
/*  798 */     System.arraycopy(paramArrayOfNameComponent, 1, arrayOfNameComponent, 0, paramArrayOfNameComponent.length - 1);
/*      */ 
/*      */     
/*  801 */     namingContext.unbind(arrayOfNameComponent);
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
/*  821 */     Object object = null;
/*  822 */     BindingTypeHolder bindingTypeHolder = new BindingTypeHolder();
/*  823 */     NamingContext namingContext = null;
/*      */     
/*  825 */     synchronized (paramNamingContextDataStore) {
/*      */       
/*  827 */       object = paramNamingContextDataStore.Resolve(paramArrayOfNameComponent[0], bindingTypeHolder);
/*  828 */       if (object == null)
/*      */       {
/*  830 */         throw new NotFound(NotFoundReason.missing_node, paramArrayOfNameComponent);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  835 */     if (bindingTypeHolder.value != BindingType.ncontext)
/*      */     {
/*  837 */       throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  842 */       namingContext = NamingContextHelper.narrow(object);
/*  843 */     } catch (BAD_PARAM bAD_PARAM) {
/*      */       
/*  845 */       throw new NotFound(NotFoundReason.not_context, paramArrayOfNameComponent);
/*      */     } 
/*      */ 
/*      */     
/*  849 */     return namingContext;
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
/*      */   public String to_string(NameComponent[] paramArrayOfNameComponent) throws InvalidName {
/*  865 */     if (paramArrayOfNameComponent == null || paramArrayOfNameComponent.length == 0)
/*      */     {
/*  867 */       throw new InvalidName();
/*      */     }
/*  869 */     NamingContextImpl namingContextImpl = this;
/*      */     
/*  871 */     String str = this.insImpl.convertToString(paramArrayOfNameComponent);
/*      */     
/*  873 */     if (str == null)
/*      */     {
/*  875 */       throw new InvalidName();
/*      */     }
/*      */     
/*  878 */     return str;
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
/*      */   public NameComponent[] to_name(String paramString) throws InvalidName {
/*  894 */     if (paramString == null || paramString.length() == 0)
/*      */     {
/*  896 */       throw new InvalidName();
/*      */     }
/*  898 */     NamingContextImpl namingContextImpl = this;
/*      */     
/*  900 */     NameComponent[] arrayOfNameComponent = this.insImpl.convertToNameComponent(paramString);
/*  901 */     if (arrayOfNameComponent == null || arrayOfNameComponent.length == 0)
/*      */     {
/*  903 */       throw new InvalidName();
/*      */     }
/*  905 */     for (byte b = 0; b < arrayOfNameComponent.length; b++) {
/*      */ 
/*      */ 
/*      */       
/*  909 */       if (((arrayOfNameComponent[b]).id == null || (arrayOfNameComponent[b]).id
/*  910 */         .length() == 0) && ((arrayOfNameComponent[b]).kind == null || (arrayOfNameComponent[b]).kind
/*      */         
/*  912 */         .length() == 0)) {
/*  913 */         throw new InvalidName();
/*      */       }
/*      */     } 
/*  916 */     return arrayOfNameComponent;
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
/*  938 */     if (paramString2 == null || paramString2.length() == 0)
/*      */     {
/*  940 */       throw new InvalidName();
/*      */     }
/*  942 */     if (paramString1 == null)
/*      */     {
/*  944 */       throw new InvalidAddress();
/*      */     }
/*      */     
/*  947 */     NamingContextImpl namingContextImpl = this;
/*  948 */     String str = null;
/*  949 */     str = this.insImpl.createURLBasedAddress(paramString1, paramString2);
/*      */ 
/*      */     
/*      */     try {
/*  953 */       INSURLHandler.getINSURLHandler().parseURL(str);
/*  954 */     } catch (BAD_PARAM bAD_PARAM) {
/*  955 */       throw new InvalidAddress();
/*      */     } 
/*      */     
/*  958 */     return str;
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
/*      */   public Object resolve_str(String paramString) throws NotFound, CannotProceed, InvalidName {
/*  980 */     Object object = null;
/*      */     
/*  982 */     if (paramString == null || paramString.length() == 0)
/*      */     {
/*  984 */       throw new InvalidName();
/*      */     }
/*  986 */     NamingContextImpl namingContextImpl = this;
/*      */     
/*  988 */     NameComponent[] arrayOfNameComponent = this.insImpl.convertToNameComponent(paramString);
/*      */     
/*  990 */     if (arrayOfNameComponent == null || arrayOfNameComponent.length == 0)
/*      */     {
/*  992 */       throw new InvalidName();
/*      */     }
/*  994 */     object = resolve(arrayOfNameComponent);
/*  995 */     return object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String nameToString(NameComponent[] paramArrayOfNameComponent) {
/* 1003 */     StringBuffer stringBuffer = new StringBuffer("{");
/* 1004 */     if (paramArrayOfNameComponent != null || paramArrayOfNameComponent.length > 0) {
/* 1005 */       for (byte b = 0; b < paramArrayOfNameComponent.length; b++) {
/* 1006 */         if (b > 0)
/* 1007 */           stringBuffer.append(","); 
/* 1008 */         stringBuffer.append("[")
/* 1009 */           .append((paramArrayOfNameComponent[b]).id)
/* 1010 */           .append(",")
/* 1011 */           .append((paramArrayOfNameComponent[b]).kind)
/* 1012 */           .append("]");
/*      */       } 
/*      */     }
/* 1015 */     stringBuffer.append("}");
/* 1016 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dprint(String paramString) {
/* 1023 */     NamingUtils.dprint("NamingContextImpl(" + 
/* 1024 */         Thread.currentThread().getName() + " at " + 
/* 1025 */         System.currentTimeMillis() + " ems): " + paramString);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/naming/cosnaming/NamingContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */