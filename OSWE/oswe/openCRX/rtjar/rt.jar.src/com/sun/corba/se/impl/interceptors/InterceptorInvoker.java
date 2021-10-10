/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.oa.ObjectAdapter;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.PortableInterceptor.ClientRequestInterceptor;
/*     */ import org.omg.PortableInterceptor.ForwardRequest;
/*     */ import org.omg.PortableInterceptor.IORInterceptor;
/*     */ import org.omg.PortableInterceptor.IORInterceptor_3_0;
/*     */ import org.omg.PortableInterceptor.ObjectReferenceTemplate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InterceptorInvoker
/*     */ {
/*     */   private ORB orb;
/*     */   private InterceptorList interceptorList;
/*     */   private boolean enabled = false;
/*     */   private PICurrent current;
/*     */   
/*     */   InterceptorInvoker(ORB paramORB, InterceptorList paramInterceptorList, PICurrent paramPICurrent) {
/*  85 */     this.orb = paramORB;
/*  86 */     this.interceptorList = paramInterceptorList;
/*  87 */     this.enabled = false;
/*  88 */     this.current = paramPICurrent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setEnabled(boolean paramBoolean) {
/*  95 */     this.enabled = paramBoolean;
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
/*     */   void objectAdapterCreated(ObjectAdapter paramObjectAdapter) {
/* 110 */     if (this.enabled) {
/*     */       
/* 112 */       IORInfoImpl iORInfoImpl = new IORInfoImpl(paramObjectAdapter);
/*     */ 
/*     */ 
/*     */       
/* 116 */       IORInterceptor[] arrayOfIORInterceptor = (IORInterceptor[])this.interceptorList.getInterceptors(2);
/*     */       
/* 118 */       int i = arrayOfIORInterceptor.length;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       int j;
/*     */ 
/*     */ 
/*     */       
/* 127 */       for (j = i - 1; j >= 0; j--) {
/* 128 */         IORInterceptor iORInterceptor = arrayOfIORInterceptor[j];
/*     */         try {
/* 130 */           iORInterceptor.establish_components(iORInfoImpl);
/*     */         }
/* 132 */         catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       iORInfoImpl.makeStateEstablished();
/*     */       
/* 141 */       for (j = i - 1; j >= 0; j--) {
/* 142 */         IORInterceptor iORInterceptor = arrayOfIORInterceptor[j];
/* 143 */         if (iORInterceptor instanceof IORInterceptor_3_0) {
/* 144 */           IORInterceptor_3_0 iORInterceptor_3_0 = (IORInterceptor_3_0)iORInterceptor;
/*     */ 
/*     */           
/* 147 */           iORInterceptor_3_0.components_established(iORInfoImpl);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       iORInfoImpl.makeStateDone();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void adapterManagerStateChanged(int paramInt, short paramShort) {
/* 161 */     if (this.enabled) {
/*     */       
/* 163 */       IORInterceptor[] arrayOfIORInterceptor = (IORInterceptor[])this.interceptorList.getInterceptors(2);
/*     */       
/* 165 */       int i = arrayOfIORInterceptor.length;
/*     */       
/* 167 */       for (int j = i - 1; j >= 0; j--) {
/*     */         try {
/* 169 */           IORInterceptor iORInterceptor = arrayOfIORInterceptor[j];
/* 170 */           if (iORInterceptor instanceof IORInterceptor_3_0) {
/* 171 */             IORInterceptor_3_0 iORInterceptor_3_0 = (IORInterceptor_3_0)iORInterceptor;
/* 172 */             iORInterceptor_3_0.adapter_manager_state_changed(paramInt, paramShort);
/*     */           }
/*     */         
/* 175 */         } catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void adapterStateChanged(ObjectReferenceTemplate[] paramArrayOfObjectReferenceTemplate, short paramShort) {
/* 185 */     if (this.enabled) {
/*     */       
/* 187 */       IORInterceptor[] arrayOfIORInterceptor = (IORInterceptor[])this.interceptorList.getInterceptors(2);
/*     */       
/* 189 */       int i = arrayOfIORInterceptor.length;
/*     */       
/* 191 */       for (int j = i - 1; j >= 0; j--) {
/*     */         try {
/* 193 */           IORInterceptor iORInterceptor = arrayOfIORInterceptor[j];
/* 194 */           if (iORInterceptor instanceof IORInterceptor_3_0) {
/* 195 */             IORInterceptor_3_0 iORInterceptor_3_0 = (IORInterceptor_3_0)iORInterceptor;
/* 196 */             iORInterceptor_3_0.adapter_state_changed(paramArrayOfObjectReferenceTemplate, paramShort);
/*     */           } 
/* 198 */         } catch (Exception exception) {}
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
/*     */ 
/*     */ 
/*     */   
/*     */   void invokeClientInterceptorStartingPoint(ClientRequestInfoImpl paramClientRequestInfoImpl) {
/* 216 */     if (this.enabled) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 221 */         this.current.pushSlotTable();
/* 222 */         paramClientRequestInfoImpl.setPICurrentPushed(true);
/* 223 */         paramClientRequestInfoImpl.setCurrentExecutionPoint(0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 228 */         ClientRequestInterceptor[] arrayOfClientRequestInterceptor = (ClientRequestInterceptor[])this.interceptorList.getInterceptors(0);
/* 229 */         int i = arrayOfClientRequestInterceptor.length;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 234 */         int j = i;
/* 235 */         boolean bool = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         for (byte b = 0; bool && b < i; b++) {
/*     */           try {
/* 245 */             arrayOfClientRequestInterceptor[b].send_request(paramClientRequestInfoImpl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 261 */           catch (ForwardRequest forwardRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 266 */             j = b;
/* 267 */             paramClientRequestInfoImpl.setForwardRequest(forwardRequest);
/* 268 */             paramClientRequestInfoImpl.setEndingPointCall(2);
/*     */             
/* 270 */             paramClientRequestInfoImpl.setReplyStatus((short)3);
/*     */             
/* 272 */             updateClientRequestDispatcherForward(paramClientRequestInfoImpl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 279 */             bool = false;
/*     */           }
/* 281 */           catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 286 */             j = b;
/* 287 */             paramClientRequestInfoImpl.setEndingPointCall(1);
/*     */             
/* 289 */             paramClientRequestInfoImpl.setReplyStatus((short)1);
/* 290 */             paramClientRequestInfoImpl.setException((Exception)systemException);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 297 */             bool = false;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 302 */         paramClientRequestInfoImpl.setFlowStackIndex(j);
/*     */       }
/*     */       finally {
/*     */         
/* 306 */         this.current.resetSlotTable();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void invokeClientInterceptorEndingPoint(ClientRequestInfoImpl paramClientRequestInfoImpl) {
/* 317 */     if (this.enabled) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 322 */         paramClientRequestInfoImpl.setCurrentExecutionPoint(2);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 327 */         ClientRequestInterceptor[] arrayOfClientRequestInterceptor = (ClientRequestInterceptor[])this.interceptorList.getInterceptors(0);
/* 328 */         int i = paramClientRequestInfoImpl.getFlowStackIndex();
/*     */ 
/*     */ 
/*     */         
/* 332 */         int j = paramClientRequestInfoImpl.getEndingPointCall();
/*     */ 
/*     */ 
/*     */         
/* 336 */         if (j == 0 && paramClientRequestInfoImpl
/*     */           
/* 338 */           .getIsOneWay()) {
/*     */           
/* 340 */           j = 2;
/* 341 */           paramClientRequestInfoImpl.setEndingPointCall(j);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 349 */         for (int k = i - 1; k >= 0; k--)
/*     */         {
/*     */           try {
/* 352 */             switch (j) {
/*     */               case 0:
/* 354 */                 arrayOfClientRequestInterceptor[k].receive_reply(paramClientRequestInfoImpl);
/*     */                 break;
/*     */               case 1:
/* 357 */                 arrayOfClientRequestInterceptor[k].receive_exception(paramClientRequestInfoImpl);
/*     */                 break;
/*     */               case 2:
/* 360 */                 arrayOfClientRequestInterceptor[k].receive_other(paramClientRequestInfoImpl);
/*     */                 break;
/*     */             } 
/*     */           
/* 364 */           } catch (ForwardRequest forwardRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 369 */             j = 2;
/*     */             
/* 371 */             paramClientRequestInfoImpl.setEndingPointCall(j);
/* 372 */             paramClientRequestInfoImpl.setReplyStatus((short)3);
/* 373 */             paramClientRequestInfoImpl.setForwardRequest(forwardRequest);
/* 374 */             updateClientRequestDispatcherForward(paramClientRequestInfoImpl);
/*     */           }
/* 376 */           catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 381 */             j = 1;
/*     */             
/* 383 */             paramClientRequestInfoImpl.setEndingPointCall(j);
/* 384 */             paramClientRequestInfoImpl.setReplyStatus((short)1);
/* 385 */             paramClientRequestInfoImpl.setException((Exception)systemException);
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } finally {
/*     */         
/* 392 */         if (paramClientRequestInfoImpl != null && paramClientRequestInfoImpl.isPICurrentPushed()) {
/* 393 */           this.current.popSlotTable();
/*     */         }
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
/*     */ 
/*     */   
/*     */   void invokeServerInterceptorStartingPoint(ServerRequestInfoImpl paramServerRequestInfoImpl) {
/* 411 */     if (this.enabled) {
/*     */       
/*     */       try {
/* 414 */         this.current.pushSlotTable();
/* 415 */         paramServerRequestInfoImpl.setSlotTable(this.current.getSlotTable());
/*     */ 
/*     */ 
/*     */         
/* 419 */         this.current.pushSlotTable();
/*     */         
/* 421 */         paramServerRequestInfoImpl.setCurrentExecutionPoint(0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 426 */         ServerRequestInterceptor[] arrayOfServerRequestInterceptor = (ServerRequestInterceptor[])this.interceptorList.getInterceptors(1);
/* 427 */         int i = arrayOfServerRequestInterceptor.length;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 432 */         int j = i;
/* 433 */         boolean bool = true;
/*     */ 
/*     */ 
/*     */         
/* 437 */         for (byte b = 0; bool && b < i; b++) {
/*     */           
/*     */           try {
/* 440 */             arrayOfServerRequestInterceptor[b]
/* 441 */               .receive_request_service_contexts(paramServerRequestInfoImpl);
/*     */           }
/* 443 */           catch (ForwardRequest forwardRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 448 */             j = b;
/* 449 */             paramServerRequestInfoImpl.setForwardRequest(forwardRequest);
/* 450 */             paramServerRequestInfoImpl.setIntermediatePointCall(1);
/*     */             
/* 452 */             paramServerRequestInfoImpl.setEndingPointCall(2);
/*     */             
/* 454 */             paramServerRequestInfoImpl.setReplyStatus((short)3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 461 */             bool = false;
/*     */           }
/* 463 */           catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 469 */             j = b;
/* 470 */             paramServerRequestInfoImpl.setException((Exception)systemException);
/* 471 */             paramServerRequestInfoImpl.setIntermediatePointCall(1);
/*     */             
/* 473 */             paramServerRequestInfoImpl.setEndingPointCall(1);
/*     */             
/* 475 */             paramServerRequestInfoImpl.setReplyStatus((short)1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 482 */             bool = false;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 488 */         paramServerRequestInfoImpl.setFlowStackIndex(j);
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 493 */         this.current.popSlotTable();
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
/*     */   void invokeServerInterceptorIntermediatePoint(ServerRequestInfoImpl paramServerRequestInfoImpl) {
/* 505 */     int i = paramServerRequestInfoImpl.getIntermediatePointCall();
/*     */     
/* 507 */     if (this.enabled && i != 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 513 */       paramServerRequestInfoImpl.setCurrentExecutionPoint(1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 518 */       ServerRequestInterceptor[] arrayOfServerRequestInterceptor = (ServerRequestInterceptor[])this.interceptorList.getInterceptors(1);
/*     */       
/* 520 */       int j = arrayOfServerRequestInterceptor.length;
/*     */ 
/*     */ 
/*     */       
/* 524 */       for (byte b = 0; b < j; b++) {
/*     */         
/*     */         try {
/* 527 */           arrayOfServerRequestInterceptor[b].receive_request(paramServerRequestInfoImpl);
/*     */         }
/* 529 */         catch (ForwardRequest forwardRequest) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 535 */           paramServerRequestInfoImpl.setForwardRequest(forwardRequest);
/* 536 */           paramServerRequestInfoImpl.setEndingPointCall(2);
/*     */           
/* 538 */           paramServerRequestInfoImpl.setReplyStatus((short)3);
/*     */           
/*     */           break;
/* 541 */         } catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 547 */           paramServerRequestInfoImpl.setException((Exception)systemException);
/* 548 */           paramServerRequestInfoImpl.setEndingPointCall(1);
/*     */           
/* 550 */           paramServerRequestInfoImpl.setReplyStatus((short)1);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void invokeServerInterceptorEndingPoint(ServerRequestInfoImpl paramServerRequestInfoImpl) {
/* 563 */     if (this.enabled) {
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 578 */         ServerRequestInterceptor[] arrayOfServerRequestInterceptor = (ServerRequestInterceptor[])this.interceptorList.getInterceptors(1);
/* 579 */         int i = paramServerRequestInfoImpl.getFlowStackIndex();
/*     */ 
/*     */ 
/*     */         
/* 583 */         int j = paramServerRequestInfoImpl.getEndingPointCall();
/*     */ 
/*     */ 
/*     */         
/* 587 */         for (int k = i - 1; k >= 0; k--) {
/*     */           try {
/* 589 */             switch (j) {
/*     */               case 0:
/* 591 */                 arrayOfServerRequestInterceptor[k].send_reply(paramServerRequestInfoImpl);
/*     */                 break;
/*     */               case 1:
/* 594 */                 arrayOfServerRequestInterceptor[k].send_exception(paramServerRequestInfoImpl);
/*     */                 break;
/*     */               case 2:
/* 597 */                 arrayOfServerRequestInterceptor[k].send_other(paramServerRequestInfoImpl);
/*     */                 break;
/*     */             } 
/*     */           
/* 601 */           } catch (ForwardRequest forwardRequest) {
/*     */ 
/*     */ 
/*     */             
/* 605 */             j = 2;
/*     */             
/* 607 */             paramServerRequestInfoImpl.setEndingPointCall(j);
/* 608 */             paramServerRequestInfoImpl.setForwardRequest(forwardRequest);
/* 609 */             paramServerRequestInfoImpl.setReplyStatus((short)3);
/* 610 */             paramServerRequestInfoImpl.setForwardRequestRaisedInEnding();
/*     */           }
/* 612 */           catch (SystemException systemException) {
/*     */ 
/*     */ 
/*     */             
/* 616 */             j = 1;
/*     */             
/* 618 */             paramServerRequestInfoImpl.setEndingPointCall(j);
/* 619 */             paramServerRequestInfoImpl.setException((Exception)systemException);
/* 620 */             paramServerRequestInfoImpl.setReplyStatus((short)1);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 626 */         paramServerRequestInfoImpl.setAlreadyExecuted(true);
/*     */       }
/*     */       finally {
/*     */         
/* 630 */         this.current.popSlotTable();
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
/*     */ 
/*     */   
/*     */   private void updateClientRequestDispatcherForward(ClientRequestInfoImpl paramClientRequestInfoImpl) {
/* 647 */     ForwardRequest forwardRequest = paramClientRequestInfoImpl.getForwardRequestException();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 653 */     if (forwardRequest != null) {
/* 654 */       Object object = forwardRequest.forward;
/*     */ 
/*     */       
/* 657 */       IOR iOR = ORBUtility.getIOR(object);
/* 658 */       paramClientRequestInfoImpl.setLocatedIOR(iOR);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/InterceptorInvoker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */