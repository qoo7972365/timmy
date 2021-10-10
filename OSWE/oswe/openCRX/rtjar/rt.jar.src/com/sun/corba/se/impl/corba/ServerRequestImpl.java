/*     */ package com.sun.corba.se.impl.corba;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.protocol.CorbaMessageMediator;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.Bounds;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.Context;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.ServerRequest;
/*     */ import org.omg.CORBA.TCKind;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerRequestImpl
/*     */   extends ServerRequest
/*     */ {
/*  57 */   private ORB _orb = null;
/*  58 */   private ORBUtilSystemException _wrapper = null;
/*  59 */   private String _opName = null;
/*  60 */   private NVList _arguments = null;
/*  61 */   private Context _ctx = null;
/*  62 */   private InputStream _ins = null;
/*     */   
/*     */   private boolean _paramsCalled = false;
/*     */   
/*     */   private boolean _resultSet = false;
/*     */   private boolean _exceptionSet = false;
/*  68 */   private Any _resultAny = null;
/*  69 */   private Any _exception = null;
/*     */ 
/*     */   
/*     */   public ServerRequestImpl(CorbaMessageMediator paramCorbaMessageMediator, ORB paramORB) {
/*  73 */     this._opName = paramCorbaMessageMediator.getOperationName();
/*  74 */     this._ins = (InputStream)paramCorbaMessageMediator.getInputObject();
/*  75 */     this._ctx = null;
/*     */ 
/*     */     
/*  78 */     this._orb = paramORB;
/*  79 */     this._wrapper = ORBUtilSystemException.get(paramORB, "oa.invocation");
/*     */   }
/*     */ 
/*     */   
/*     */   public String operation() {
/*  84 */     return this._opName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arguments(NVList paramNVList) {
/*  89 */     if (this._paramsCalled) {
/*  90 */       throw this._wrapper.argumentsCalledMultiple();
/*     */     }
/*  92 */     if (this._exceptionSet) {
/*  93 */       throw this._wrapper.argumentsCalledAfterException();
/*     */     }
/*  95 */     if (paramNVList == null) {
/*  96 */       throw this._wrapper.argumentsCalledNullArgs();
/*     */     }
/*  98 */     this._paramsCalled = true;
/*     */     
/* 100 */     NamedValue namedValue = null;
/* 101 */     for (byte b = 0; b < paramNVList.count(); b++) {
/*     */       try {
/* 103 */         namedValue = paramNVList.item(b);
/* 104 */       } catch (Bounds bounds) {
/* 105 */         throw this._wrapper.boundsCannotOccur(bounds);
/*     */       } 
/*     */       
/*     */       try {
/* 109 */         if (namedValue.flags() == 1 || namedValue
/* 110 */           .flags() == 3)
/*     */         {
/* 112 */           namedValue.value().read_value(this._ins, namedValue.value().type());
/*     */         }
/* 114 */       } catch (Exception exception) {
/* 115 */         throw this._wrapper.badArgumentsNvlist(exception);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 120 */     this._arguments = paramNVList;
/*     */     
/* 122 */     this._orb.getPIHandler().setServerPIInfo(this._arguments);
/* 123 */     this._orb.getPIHandler().invokeServerPIIntermediatePoint();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set_result(Any paramAny) {
/* 128 */     if (!this._paramsCalled)
/* 129 */       throw this._wrapper.argumentsNotCalled(); 
/* 130 */     if (this._resultSet)
/* 131 */       throw this._wrapper.setResultCalledMultiple(); 
/* 132 */     if (this._exceptionSet)
/* 133 */       throw this._wrapper.setResultAfterException(); 
/* 134 */     if (paramAny == null) {
/* 135 */       throw this._wrapper.setResultCalledNullArgs();
/*     */     }
/* 137 */     this._resultAny = paramAny;
/* 138 */     this._resultSet = true;
/*     */ 
/*     */ 
/*     */     
/* 142 */     this._orb.getPIHandler().setServerPIInfo(this._resultAny);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set_exception(Any paramAny) {
/* 152 */     if (paramAny == null) {
/* 153 */       throw this._wrapper.setExceptionCalledNullArgs();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 158 */     TCKind tCKind = paramAny.type().kind();
/* 159 */     if (tCKind != TCKind.tk_except) {
/* 160 */       throw this._wrapper.setExceptionCalledBadType();
/*     */     }
/* 162 */     this._exception = paramAny;
/*     */ 
/*     */ 
/*     */     
/* 166 */     this._orb.getPIHandler().setServerPIExceptionInfo(this._exception);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (!this._exceptionSet && !this._paramsCalled)
/*     */     {
/* 175 */       this._orb.getPIHandler().invokeServerPIIntermediatePoint();
/*     */     }
/*     */     
/* 178 */     this._exceptionSet = true;
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
/*     */   public Any checkResultCalled() {
/* 200 */     if (this._paramsCalled && this._resultSet)
/* 201 */       return null; 
/* 202 */     if (this._paramsCalled && !this._resultSet && !this._exceptionSet) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */         
/* 208 */         TypeCode typeCode = this._orb.get_primitive_tc(TCKind.tk_void);
/*     */         
/* 210 */         this._resultAny = this._orb.create_any();
/* 211 */         this._resultAny.type(typeCode);
/* 212 */         this._resultSet = true;
/*     */         
/* 214 */         return null;
/* 215 */       } catch (Exception exception) {
/* 216 */         throw this._wrapper.dsiResultException(CompletionStatus.COMPLETED_MAYBE, exception);
/*     */       } 
/*     */     }
/* 219 */     if (this._exceptionSet) {
/* 220 */       return this._exception;
/*     */     }
/* 222 */     throw this._wrapper.dsimethodNotcalled(CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalReplyParams(OutputStream paramOutputStream) {
/* 233 */     this._resultAny.write_value(paramOutputStream);
/*     */ 
/*     */     
/* 236 */     NamedValue namedValue = null;
/*     */     
/* 238 */     for (byte b = 0; b < this._arguments.count(); b++) {
/*     */       try {
/* 240 */         namedValue = this._arguments.item(b);
/* 241 */       } catch (Bounds bounds) {}
/*     */       
/* 243 */       if (namedValue.flags() == 2 || namedValue
/* 244 */         .flags() == 3) {
/* 245 */         namedValue.value().write_value(paramOutputStream);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Context ctx() {
/* 252 */     if (!this._paramsCalled || this._resultSet || this._exceptionSet) {
/* 253 */       throw this._wrapper.contextCalledOutOfOrder();
/*     */     }
/* 255 */     throw this._wrapper.contextNotImplemented();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/corba/ServerRequestImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */