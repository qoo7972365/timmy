/*     */ package org.omg.CORBA.portable;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.ContextList;
/*     */ import org.omg.CORBA.DomainManager;
/*     */ import org.omg.CORBA.ExceptionList;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.Request;
/*     */ import org.omg.CORBA.SetOverrideType;
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
/*     */ 
/*     */ 
/*     */ public abstract class ObjectImpl
/*     */   implements Object
/*     */ {
/*     */   private transient Delegate __delegate;
/*     */   
/*     */   public Delegate _get_delegate() {
/*  70 */     if (this.__delegate == null)
/*  71 */       throw new BAD_OPERATION("The delegate has not been set!"); 
/*  72 */     return this.__delegate;
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
/*     */   
/*     */   public void _set_delegate(Delegate paramDelegate) {
/*  87 */     this.__delegate = paramDelegate;
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
/*     */   public abstract String[] _ids();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object _duplicate() {
/* 109 */     return _get_delegate().duplicate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _release() {
/* 116 */     _get_delegate().release(this);
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
/*     */   public boolean _is_a(String paramString) {
/* 130 */     return _get_delegate().is_a(this, paramString);
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
/*     */   public boolean _is_equivalent(Object paramObject) {
/* 144 */     return _get_delegate().is_equivalent(this, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean _non_existent() {
/* 155 */     return _get_delegate().non_existent(this);
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
/*     */   public int _hash(int paramInt) {
/* 168 */     return _get_delegate().hash(this, paramInt);
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
/*     */   public Request _request(String paramString) {
/* 181 */     return _get_delegate().request(this, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request _create_request(Context paramContext, String paramString, NVList paramNVList, NamedValue paramNamedValue) {
/* 203 */     return _get_delegate().create_request(this, paramContext, paramString, paramNVList, paramNamedValue);
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
/*     */   public Request _create_request(Context paramContext, String paramString, NVList paramNVList, NamedValue paramNamedValue, ExceptionList paramExceptionList, ContextList paramContextList) {
/* 244 */     return _get_delegate().create_request(this, paramContext, paramString, paramNVList, paramNamedValue, paramExceptionList, paramContextList);
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
/*     */   public Object _get_interface_def() {
/* 268 */     Delegate delegate = _get_delegate();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 273 */       return delegate.get_interface_def(this);
/*     */     }
/* 275 */     catch (NO_IMPLEMENT nO_IMPLEMENT) {
/*     */       
/*     */       try {
/* 278 */         Class[] arrayOfClass = { Object.class };
/*     */         
/* 280 */         Method method = delegate.getClass().getMethod("get_interface", arrayOfClass);
/* 281 */         Object[] arrayOfObject = { this };
/* 282 */         return (Object)method.invoke(delegate, arrayOfObject);
/*     */       }
/* 284 */       catch (InvocationTargetException invocationTargetException) {
/* 285 */         Throwable throwable = invocationTargetException.getTargetException();
/* 286 */         if (throwable instanceof Error) {
/* 287 */           throw (Error)throwable;
/*     */         }
/* 289 */         if (throwable instanceof RuntimeException) {
/* 290 */           throw (RuntimeException)throwable;
/*     */         }
/*     */         
/* 293 */         throw new NO_IMPLEMENT();
/*     */       }
/* 295 */       catch (RuntimeException runtimeException) {
/* 296 */         throw runtimeException;
/* 297 */       } catch (Exception exception) {
/* 298 */         throw new NO_IMPLEMENT();
/*     */       } 
/*     */     } 
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
/*     */   public ORB _orb() {
/* 313 */     return _get_delegate().orb(this);
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
/*     */   
/*     */   public Policy _get_policy(int paramInt) {
/* 328 */     return _get_delegate().get_policy(this, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DomainManager[] _get_domain_managers() {
/* 339 */     return _get_delegate().get_domain_managers(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object _set_policy_override(Policy[] paramArrayOfPolicy, SetOverrideType paramSetOverrideType) {
/* 360 */     return _get_delegate().set_policy_override(this, paramArrayOfPolicy, paramSetOverrideType);
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
/*     */   public boolean _is_local() {
/* 372 */     return _get_delegate().is_local(this);
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
/*     */   public ServantObject _servant_preinvoke(String paramString, Class paramClass) {
/* 409 */     return _get_delegate().servant_preinvoke(this, paramString, paramClass);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _servant_postinvoke(ServantObject paramServantObject) {
/* 428 */     _get_delegate().servant_postinvoke(this, paramServantObject);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream _request(String paramString, boolean paramBoolean) {
/* 449 */     return _get_delegate().request(this, paramString, paramBoolean);
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
/*     */   public InputStream _invoke(OutputStream paramOutputStream) throws ApplicationException, RemarshalException {
/* 475 */     return _get_delegate().invoke(this, paramOutputStream);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void _releaseReply(InputStream paramInputStream) {
/* 492 */     _get_delegate().releaseReply(this, paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 502 */     if (this.__delegate != null) {
/* 503 */       return this.__delegate.toString(this);
/*     */     }
/* 505 */     return getClass().getName() + ": no delegate set";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 514 */     if (this.__delegate != null) {
/* 515 */       return this.__delegate.hashCode(this);
/*     */     }
/* 517 */     return super.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 529 */     if (this.__delegate != null) {
/* 530 */       return this.__delegate.equals(this, paramObject);
/*     */     }
/* 532 */     return (this == paramObject);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/CORBA/portable/ObjectImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */