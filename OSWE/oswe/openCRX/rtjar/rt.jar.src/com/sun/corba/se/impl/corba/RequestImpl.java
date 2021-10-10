/*     */ package com.sun.corba.se.impl.corba;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Bounds;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.ContextList;
/*     */ import org.omg.CORBA.Environment;
/*     */ import org.omg.CORBA.ExceptionList;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Request;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.WrongTransaction;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.Delegate;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.RemarshalException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RequestImpl
/*     */   extends Request
/*     */ {
/*     */   protected Object _target;
/*     */   protected String _opName;
/*     */   protected NVList _arguments;
/*     */   protected ExceptionList _exceptions;
/*     */   private NamedValue _result;
/*     */   protected Environment _env;
/*     */   private Context _ctx;
/*     */   private ContextList _ctxList;
/*     */   protected ORB _orb;
/*     */   private ORBUtilSystemException _wrapper;
/*     */   protected boolean _isOneWay = false;
/*     */   private int[] _paramCodes;
/*     */   private long[] _paramLongs;
/*     */   private Object[] _paramObjects;
/*     */   protected boolean gotResponse = false;
/*     */   
/*     */   public RequestImpl(ORB paramORB, Object paramObject, Context paramContext, String paramString, NVList paramNVList, NamedValue paramNamedValue, ExceptionList paramExceptionList, ContextList paramContextList) {
/* 113 */     this._orb = paramORB;
/* 114 */     this._wrapper = ORBUtilSystemException.get(paramORB, "oa.invocation");
/*     */ 
/*     */ 
/*     */     
/* 118 */     this._target = paramObject;
/* 119 */     this._ctx = paramContext;
/* 120 */     this._opName = paramString;
/*     */ 
/*     */     
/* 123 */     if (paramNVList == null) {
/* 124 */       this._arguments = new NVListImpl(this._orb);
/*     */     } else {
/* 126 */       this._arguments = paramNVList;
/*     */     } 
/*     */     
/* 129 */     this._result = paramNamedValue;
/*     */ 
/*     */     
/* 132 */     if (paramExceptionList == null) {
/* 133 */       this._exceptions = new ExceptionListImpl();
/*     */     } else {
/* 135 */       this._exceptions = paramExceptionList;
/*     */     } 
/*     */     
/* 138 */     if (paramContextList == null) {
/* 139 */       this._ctxList = new ContextListImpl((ORB)this._orb);
/*     */     } else {
/* 141 */       this._ctxList = paramContextList;
/*     */     } 
/*     */     
/* 144 */     this._env = new EnvironmentImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object target() {
/* 150 */     return this._target;
/*     */   }
/*     */ 
/*     */   
/*     */   public String operation() {
/* 155 */     return this._opName;
/*     */   }
/*     */ 
/*     */   
/*     */   public NVList arguments() {
/* 160 */     return this._arguments;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamedValue result() {
/* 165 */     return this._result;
/*     */   }
/*     */ 
/*     */   
/*     */   public Environment env() {
/* 170 */     return this._env;
/*     */   }
/*     */ 
/*     */   
/*     */   public ExceptionList exceptions() {
/* 175 */     return this._exceptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContextList contexts() {
/* 180 */     return this._ctxList;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Context ctx() {
/* 185 */     if (this._ctx == null)
/* 186 */       this._ctx = new ContextImpl((ORB)this._orb); 
/* 187 */     return this._ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void ctx(Context paramContext) {
/* 192 */     this._ctx = paramContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_in_arg() {
/* 197 */     return this._arguments.add(1).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_named_in_arg(String paramString) {
/* 202 */     return this._arguments.add_item(paramString, 1).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_inout_arg() {
/* 207 */     return this._arguments.add(3).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_named_inout_arg(String paramString) {
/* 212 */     return this._arguments.add_item(paramString, 3).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_out_arg() {
/* 217 */     return this._arguments.add(2).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any add_named_out_arg(String paramString) {
/* 222 */     return this._arguments.add_item(paramString, 2).value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void set_return_type(TypeCode paramTypeCode) {
/* 227 */     if (this._result == null)
/* 228 */       this._result = new NamedValueImpl(this._orb); 
/* 229 */     this._result.value().type(paramTypeCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized Any return_value() {
/* 234 */     if (this._result == null)
/* 235 */       this._result = new NamedValueImpl(this._orb); 
/* 236 */     return this._result.value();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void add_exception(TypeCode paramTypeCode) {
/* 241 */     this._exceptions.add(paramTypeCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void invoke() {
/* 246 */     doInvocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void send_oneway() {
/* 251 */     this._isOneWay = true;
/* 252 */     doInvocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void send_deferred() {
/* 257 */     AsynchInvoke asynchInvoke = new AsynchInvoke(this._orb, this, false);
/* 258 */     (new Thread(asynchInvoke)).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean poll_response() {
/* 268 */     return this.gotResponse;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void get_response() throws WrongTransaction {
/* 274 */     while (!this.gotResponse) {
/*     */ 
/*     */       
/*     */       try {
/* 278 */         wait();
/*     */       }
/* 280 */       catch (InterruptedException interruptedException) {}
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
/*     */   protected void doInvocation() {
/* 293 */     Delegate delegate = StubAdapter.getDelegate(this._target);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     this._orb.getPIHandler().initiateClientPIRequest(true);
/* 301 */     this._orb.getPIHandler().setClientPIInfo(this);
/*     */     
/* 303 */     InputStream inputStream = null;
/*     */     try {
/* 305 */       OutputStream outputStream = delegate.request(null, this._opName, !this._isOneWay);
/*     */       
/*     */       try {
/* 308 */         for (byte b = 0; b < this._arguments.count(); b++) {
/* 309 */           NamedValue namedValue = this._arguments.item(b);
/* 310 */           switch (namedValue.flags()) {
/*     */             case 1:
/* 312 */               namedValue.value().write_value(outputStream);
/*     */               break;
/*     */ 
/*     */             
/*     */             case 3:
/* 317 */               namedValue.value().write_value(outputStream);
/*     */               break;
/*     */           } 
/*     */         } 
/* 321 */       } catch (Bounds bounds) {
/* 322 */         throw this._wrapper.boundsErrorInDiiRequest(bounds);
/*     */       } 
/*     */       
/* 325 */       inputStream = delegate.invoke(null, outputStream);
/* 326 */     } catch (ApplicationException applicationException) {
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 331 */     catch (RemarshalException remarshalException) {
/* 332 */       doInvocation();
/* 333 */     } catch (SystemException systemException) {
/* 334 */       this._env.exception((Exception)systemException);
/*     */ 
/*     */ 
/*     */       
/* 338 */       throw systemException;
/*     */     } finally {
/* 340 */       delegate.releaseReply(null, inputStream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unmarshalReply(InputStream paramInputStream) {
/* 348 */     if (this._result != null) {
/* 349 */       Any any = this._result.value();
/* 350 */       TypeCode typeCode = any.type();
/* 351 */       if (typeCode.kind().value() != 1) {
/* 352 */         any.read_value(paramInputStream, typeCode);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 357 */       for (byte b = 0; b < this._arguments.count(); b++) {
/* 358 */         Any any; NamedValue namedValue = this._arguments.item(b);
/* 359 */         switch (namedValue.flags()) {
/*     */ 
/*     */           
/*     */           case 2:
/*     */           case 3:
/* 364 */             any = namedValue.value();
/* 365 */             any.read_value(paramInputStream, any.type());
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 370 */     } catch (Bounds bounds) {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/RequestImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */