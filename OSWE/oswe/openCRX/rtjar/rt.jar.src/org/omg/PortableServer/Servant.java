/*     */ package org.omg.PortableServer;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import org.omg.CORBA.BAD_INV_ORDER;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.NO_IMPLEMENT;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA_2_3.ORB;
/*     */ import org.omg.PortableServer.portable.Delegate;
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
/*     */ public abstract class Servant
/*     */ {
/*  44 */   private transient Delegate _delegate = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Delegate _get_delegate() {
/*  52 */     if (this._delegate == null) {
/*  53 */       throw new BAD_INV_ORDER("The Servant has not been associated with an ORB instance");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  58 */     return this._delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void _set_delegate(Delegate paramDelegate) {
/*  69 */     this._delegate = paramDelegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object _this_object() {
/*  79 */     return _get_delegate().this_object(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Object _this_object(ORB paramORB) {
/*     */     try {
/*  90 */       ((ORB)paramORB).set_delegate(this);
/*     */     }
/*  92 */     catch (ClassCastException classCastException) {
/*  93 */       throw new BAD_PARAM("POA Servant requires an instance of org.omg.CORBA_2_3.ORB");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return _this_object();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ORB _orb() {
/* 108 */     return _get_delegate().orb(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final POA _poa() {
/* 117 */     return _get_delegate().poa(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] _object_id() {
/* 128 */     return _get_delegate().object_id(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public POA _default_POA() {
/* 139 */     return _get_delegate().default_POA(this);
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
/*     */   public boolean _is_a(String paramString) {
/* 155 */     return _get_delegate().is_a(this, paramString);
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
/*     */   public boolean _non_existent() {
/* 167 */     return _get_delegate().non_existent(this);
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
/* 224 */     Delegate delegate = _get_delegate();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 229 */       return delegate.get_interface_def(this);
/* 230 */     } catch (AbstractMethodError abstractMethodError) {
/*     */       
/*     */       try {
/* 233 */         Class[] arrayOfClass = { Servant.class };
/*     */         
/* 235 */         Method method = delegate.getClass().getMethod("get_interface", arrayOfClass);
/* 236 */         Object[] arrayOfObject = { this };
/* 237 */         return (Object)method.invoke(delegate, arrayOfObject);
/* 238 */       } catch (InvocationTargetException invocationTargetException) {
/* 239 */         Throwable throwable = invocationTargetException.getTargetException();
/* 240 */         if (throwable instanceof Error)
/* 241 */           throw (Error)throwable; 
/* 242 */         if (throwable instanceof RuntimeException) {
/* 243 */           throw (RuntimeException)throwable;
/*     */         }
/* 245 */         throw new NO_IMPLEMENT();
/*     */       }
/* 247 */       catch (RuntimeException runtimeException) {
/* 248 */         throw runtimeException;
/* 249 */       } catch (Exception exception) {
/* 250 */         throw new NO_IMPLEMENT();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract String[] _all_interfaces(POA paramPOA, byte[] paramArrayOfbyte);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/Servant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */