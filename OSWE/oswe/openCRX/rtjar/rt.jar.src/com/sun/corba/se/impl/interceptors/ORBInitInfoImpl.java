/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.legacy.interceptor.ORBInitInfoExt;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.BAD_PARAM;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.ORBPackage.InvalidName;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.Policy;
/*     */ import org.omg.CORBA.PolicyError;
/*     */ import org.omg.IOP.CodecFactory;
/*     */ import org.omg.PortableInterceptor.ClientRequestInterceptor;
/*     */ import org.omg.PortableInterceptor.IORInterceptor;
/*     */ import org.omg.PortableInterceptor.Interceptor;
/*     */ import org.omg.PortableInterceptor.ORBInitInfo;
/*     */ import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;
/*     */ import org.omg.PortableInterceptor.ORBInitInfoPackage.InvalidName;
/*     */ import org.omg.PortableInterceptor.PolicyFactory;
/*     */ import org.omg.PortableInterceptor.ServerRequestInterceptor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ORBInitInfoImpl
/*     */   extends LocalObject
/*     */   implements ORBInitInfo, ORBInitInfoExt
/*     */ {
/*     */   private ORB orb;
/*     */   private InterceptorsSystemException wrapper;
/*     */   private ORBUtilSystemException orbutilWrapper;
/*     */   private OMGSystemException omgWrapper;
/*     */   private String[] args;
/*     */   private String orbId;
/*     */   private CodecFactory codecFactory;
/*  80 */   private int stage = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STAGE_PRE_INIT = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STAGE_POST_INIT = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STAGE_CLOSED = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String MESSAGE_ORBINITINFO_INVALID = "ORBInitInfo object is only valid during ORB_init";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ORBInitInfoImpl(ORB paramORB, String[] paramArrayOfString, String paramString, CodecFactory paramCodecFactory) {
/* 103 */     this.orb = paramORB;
/*     */     
/* 105 */     this.wrapper = InterceptorsSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 107 */     this.orbutilWrapper = ORBUtilSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 109 */     this.omgWrapper = OMGSystemException.get(paramORB, "rpc.protocol");
/*     */ 
/*     */     
/* 112 */     this.args = paramArrayOfString;
/* 113 */     this.orbId = paramString;
/* 114 */     this.codecFactory = paramCodecFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ORB getORB() {
/* 122 */     return this.orb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setStage(int paramInt) {
/* 130 */     this.stage = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkStage() {
/* 140 */     if (this.stage == 2) {
/* 141 */       throw this.wrapper.orbinitinfoInvalid();
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
/*     */   public String[] arguments() {
/* 155 */     checkStage();
/* 156 */     return this.args;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String orb_id() {
/* 163 */     checkStage();
/* 164 */     return this.orbId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodecFactory codec_factory() {
/* 175 */     checkStage();
/* 176 */     return this.codecFactory;
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
/*     */   public void register_initial_reference(String paramString, Object paramObject) throws InvalidName {
/* 192 */     checkStage();
/* 193 */     if (paramString == null) nullParam();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     if (paramObject == null) {
/* 202 */       throw this.omgWrapper.rirWithNullObject();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 216 */       this.orb.register_initial_reference(paramString, paramObject);
/* 217 */     } catch (InvalidName invalidName) {
/* 218 */       InvalidName invalidName1 = new InvalidName(invalidName.getMessage());
/* 219 */       invalidName1.initCause((Throwable)invalidName);
/* 220 */       throw invalidName1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object resolve_initial_references(String paramString) throws InvalidName {
/* 238 */     checkStage();
/* 239 */     if (paramString == null) nullParam();
/*     */     
/* 241 */     if (this.stage == 0)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 247 */       throw this.wrapper.rirInvalidPreInit();
/*     */     }
/*     */     
/* 250 */     Object object = null;
/*     */     
/*     */     try {
/* 253 */       object = this.orb.resolve_initial_references(paramString);
/*     */     }
/* 255 */     catch (InvalidName invalidName) {
/*     */       
/* 257 */       throw new InvalidName();
/*     */     } 
/*     */     
/* 260 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add_client_request_interceptor_with_policy(ClientRequestInterceptor paramClientRequestInterceptor, Policy[] paramArrayOfPolicy) throws DuplicateName {
/* 269 */     add_client_request_interceptor(paramClientRequestInterceptor);
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
/*     */   public void add_client_request_interceptor(ClientRequestInterceptor paramClientRequestInterceptor) throws DuplicateName {
/* 283 */     checkStage();
/* 284 */     if (paramClientRequestInterceptor == null) nullParam();
/*     */     
/* 286 */     this.orb.getPIHandler().register_interceptor((Interceptor)paramClientRequestInterceptor, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add_server_request_interceptor_with_policy(ServerRequestInterceptor paramServerRequestInterceptor, Policy[] paramArrayOfPolicy) throws DuplicateName, PolicyError {
/* 296 */     add_server_request_interceptor(paramServerRequestInterceptor);
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
/*     */   public void add_server_request_interceptor(ServerRequestInterceptor paramServerRequestInterceptor) throws DuplicateName {
/* 310 */     checkStage();
/* 311 */     if (paramServerRequestInterceptor == null) nullParam();
/*     */     
/* 313 */     this.orb.getPIHandler().register_interceptor((Interceptor)paramServerRequestInterceptor, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add_ior_interceptor_with_policy(IORInterceptor paramIORInterceptor, Policy[] paramArrayOfPolicy) throws DuplicateName, PolicyError {
/* 323 */     add_ior_interceptor(paramIORInterceptor);
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
/*     */   public void add_ior_interceptor(IORInterceptor paramIORInterceptor) throws DuplicateName {
/* 337 */     checkStage();
/* 338 */     if (paramIORInterceptor == null) nullParam();
/*     */     
/* 340 */     this.orb.getPIHandler().register_interceptor((Interceptor)paramIORInterceptor, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int allocate_slot_id() {
/* 351 */     checkStage();
/*     */     
/* 353 */     return ((PICurrent)this.orb.getPIHandler().getPICurrent()).allocateSlotId();
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
/*     */   public void register_policy_factory(int paramInt, PolicyFactory paramPolicyFactory) {
/* 366 */     checkStage();
/* 367 */     if (paramPolicyFactory == null) nullParam(); 
/* 368 */     this.orb.getPIHandler().registerPolicyFactory(paramInt, paramPolicyFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nullParam() throws BAD_PARAM {
/* 379 */     throw this.orbutilWrapper.nullParam();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/ORBInitInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */