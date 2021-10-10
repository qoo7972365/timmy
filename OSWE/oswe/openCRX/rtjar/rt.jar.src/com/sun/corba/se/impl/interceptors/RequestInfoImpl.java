/*     */ package com.sun.corba.se.impl.interceptors;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.EncapsOutputStream;
/*     */ import com.sun.corba.se.impl.logging.InterceptorsSystemException;
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.orbutil.ORBUtility;
/*     */ import com.sun.corba.se.impl.util.RepositoryId;
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import com.sun.corba.se.spi.legacy.connection.Connection;
/*     */ import com.sun.corba.se.spi.legacy.interceptor.RequestInfoExt;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContext;
/*     */ import com.sun.corba.se.spi.servicecontext.ServiceContexts;
/*     */ import com.sun.corba.se.spi.servicecontext.UnknownServiceContext;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import org.omg.CORBA.Any;
/*     */ import org.omg.CORBA.BAD_INV_ORDER;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.LocalObject;
/*     */ import org.omg.CORBA.NVList;
/*     */ import org.omg.CORBA.NamedValue;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.ParameterMode;
/*     */ import org.omg.CORBA.SystemException;
/*     */ import org.omg.CORBA.TypeCode;
/*     */ import org.omg.CORBA.UNKNOWN;
/*     */ import org.omg.CORBA.UserException;
/*     */ import org.omg.CORBA.portable.ApplicationException;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ import org.omg.Dynamic.Parameter;
/*     */ import org.omg.IOP.ServiceContext;
/*     */ import org.omg.IOP.ServiceContextHelper;
/*     */ import org.omg.PortableInterceptor.ForwardRequest;
/*     */ import org.omg.PortableInterceptor.InvalidSlot;
/*     */ import org.omg.PortableInterceptor.RequestInfo;
/*     */ import sun.corba.OutputStreamFactory;
/*     */ import sun.corba.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RequestInfoImpl
/*     */   extends LocalObject
/*     */   implements RequestInfo, RequestInfoExt
/*     */ {
/*     */   protected ORB myORB;
/*     */   protected InterceptorsSystemException wrapper;
/*     */   protected OMGSystemException stdWrapper;
/* 118 */   protected int flowStackIndex = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int startingPointCall;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int intermediatePointCall;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int endingPointCall;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   protected short replyStatus = -1;
/*     */   
/*     */   protected static final short UNINITIALIZED = -1;
/*     */   
/*     */   protected int currentExecutionPoint;
/*     */   
/*     */   protected static final int EXECUTION_POINT_STARTING = 0;
/*     */   
/*     */   protected static final int EXECUTION_POINT_INTERMEDIATE = 1;
/*     */   
/*     */   protected static final int EXECUTION_POINT_ENDING = 2;
/*     */   
/*     */   protected boolean alreadyExecuted;
/*     */   
/*     */   protected Connection connection;
/*     */   
/*     */   protected ServiceContexts serviceContexts;
/*     */   
/*     */   protected ForwardRequest forwardRequest;
/*     */   
/*     */   protected IOR forwardRequestIOR;
/*     */   
/*     */   protected SlotTable slotTable;
/*     */   
/*     */   protected Exception exception;
/*     */   
/*     */   protected static final int MID_REQUEST_ID = 0;
/*     */   
/*     */   protected static final int MID_OPERATION = 1;
/*     */   
/*     */   protected static final int MID_ARGUMENTS = 2;
/*     */   
/*     */   protected static final int MID_EXCEPTIONS = 3;
/*     */   
/*     */   protected static final int MID_CONTEXTS = 4;
/*     */   
/*     */   protected static final int MID_OPERATION_CONTEXT = 5;
/*     */   
/*     */   protected static final int MID_RESULT = 6;
/*     */   protected static final int MID_RESPONSE_EXPECTED = 7;
/*     */   protected static final int MID_SYNC_SCOPE = 8;
/*     */   protected static final int MID_REPLY_STATUS = 9;
/*     */   protected static final int MID_FORWARD_REFERENCE = 10;
/*     */   protected static final int MID_GET_SLOT = 11;
/*     */   protected static final int MID_GET_REQUEST_SERVICE_CONTEXT = 12;
/*     */   protected static final int MID_GET_REPLY_SERVICE_CONTEXT = 13;
/*     */   protected static final int MID_RI_LAST = 13;
/*     */   
/*     */   void reset() {
/* 187 */     this.flowStackIndex = 0;
/* 188 */     this.startingPointCall = 0;
/* 189 */     this.intermediatePointCall = 0;
/* 190 */     this.endingPointCall = 0;
/*     */     
/* 192 */     setReplyStatus((short)-1);
/* 193 */     this.currentExecutionPoint = 0;
/* 194 */     this.alreadyExecuted = false;
/* 195 */     this.connection = null;
/* 196 */     this.serviceContexts = null;
/* 197 */     this.forwardRequest = null;
/* 198 */     this.forwardRequestIOR = null;
/* 199 */     this.exception = null;
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
/*     */   public RequestInfoImpl(ORB paramORB) {
/* 241 */     this.myORB = paramORB;
/* 242 */     this.wrapper = InterceptorsSystemException.get(paramORB, "rpc.protocol");
/*     */     
/* 244 */     this.stdWrapper = OMGSystemException.get(paramORB, "rpc.protocol");
/*     */ 
/*     */ 
/*     */     
/* 248 */     PICurrent pICurrent = (PICurrent)paramORB.getPIHandler().getPICurrent();
/* 249 */     this.slotTable = pICurrent.getSlotTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int request_id();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String operation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Parameter[] arguments();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract TypeCode[] exceptions();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String[] contexts();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String[] operation_context();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Any result();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean response_expected();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short sync_scope() {
/* 332 */     checkAccess(8);
/* 333 */     return 1;
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
/*     */   public short reply_status() {
/* 348 */     checkAccess(9);
/* 349 */     return this.replyStatus;
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
/*     */   public abstract Object forward_reference();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Any get_slot(int paramInt) throws InvalidSlot {
/* 380 */     return this.slotTable.get_slot(paramInt);
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
/*     */   public abstract ServiceContext get_request_service_context(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ServiceContext get_reply_service_context(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection connection() {
/* 429 */     return this.connection;
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
/*     */   private void insertApplicationException(ApplicationException paramApplicationException, Any paramAny) throws UNKNOWN {
/*     */     try {
/* 450 */       RepositoryId repositoryId = RepositoryId.cache.getId(paramApplicationException
/* 451 */           .getId());
/* 452 */       String str1 = repositoryId.getClassName();
/*     */ 
/*     */       
/* 455 */       String str2 = str1 + "Helper";
/*     */       
/* 457 */       Class clazz = SharedSecrets.getJavaCorbaAccess().loadClass(str2);
/* 458 */       Class[] arrayOfClass = new Class[1];
/* 459 */       arrayOfClass[0] = InputStream.class;
/* 460 */       Method method = clazz.getMethod("read", arrayOfClass);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 465 */       InputStream inputStream = paramApplicationException.getInputStream();
/* 466 */       inputStream.mark(0);
/* 467 */       UserException userException = null;
/*     */       try {
/* 469 */         Object[] arrayOfObject = new Object[1];
/* 470 */         arrayOfObject[0] = inputStream;
/* 471 */         userException = (UserException)method.invoke(null, arrayOfObject);
/*     */       } finally {
/*     */ 
/*     */         
/*     */         try {
/* 476 */           inputStream.reset();
/*     */         }
/* 478 */         catch (IOException iOException) {
/* 479 */           throw this.wrapper.markAndResetFailed(iOException);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 485 */       insertUserException(userException, paramAny);
/* 486 */     } catch (ClassNotFoundException classNotFoundException) {
/* 487 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/* 488 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 489 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, noSuchMethodException);
/* 490 */     } catch (SecurityException securityException) {
/* 491 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, securityException);
/* 492 */     } catch (IllegalAccessException illegalAccessException) {
/* 493 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, illegalAccessException);
/* 494 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 495 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, illegalArgumentException);
/* 496 */     } catch (InvocationTargetException invocationTargetException) {
/* 497 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, invocationTargetException);
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
/*     */   private void insertUserException(UserException paramUserException, Any paramAny) throws UNKNOWN {
/*     */     try {
/* 513 */       if (paramUserException != null) {
/* 514 */         Class<?> clazz = paramUserException.getClass();
/* 515 */         String str1 = clazz.getName();
/* 516 */         String str2 = str1 + "Helper";
/*     */         
/* 518 */         Class clazz1 = SharedSecrets.getJavaCorbaAccess().loadClass(str2);
/*     */ 
/*     */         
/* 521 */         Class[] arrayOfClass = new Class[2];
/* 522 */         arrayOfClass[0] = Any.class;
/* 523 */         arrayOfClass[1] = clazz;
/* 524 */         Method method = clazz1.getMethod("insert", arrayOfClass);
/*     */ 
/*     */ 
/*     */         
/* 528 */         Object[] arrayOfObject = new Object[2];
/*     */         
/* 530 */         arrayOfObject[0] = paramAny;
/* 531 */         arrayOfObject[1] = paramUserException;
/* 532 */         method.invoke(null, arrayOfObject);
/*     */       } 
/* 534 */     } catch (ClassNotFoundException classNotFoundException) {
/* 535 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/* 536 */     } catch (NoSuchMethodException noSuchMethodException) {
/* 537 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, noSuchMethodException);
/* 538 */     } catch (SecurityException securityException) {
/* 539 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, securityException);
/* 540 */     } catch (IllegalAccessException illegalAccessException) {
/* 541 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, illegalAccessException);
/* 542 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 543 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, illegalArgumentException);
/* 544 */     } catch (InvocationTargetException invocationTargetException) {
/* 545 */       throw this.stdWrapper.unknownUserException(CompletionStatus.COMPLETED_MAYBE, invocationTargetException);
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
/*     */   protected Parameter[] nvListToParameterArray(NVList paramNVList) {
/* 562 */     int i = paramNVList.count();
/* 563 */     Parameter[] arrayOfParameter = new Parameter[i];
/*     */     try {
/* 565 */       for (byte b = 0; b < i; b++) {
/* 566 */         Parameter parameter = new Parameter();
/* 567 */         arrayOfParameter[b] = parameter;
/* 568 */         NamedValue namedValue = paramNVList.item(b);
/* 569 */         (arrayOfParameter[b]).argument = namedValue.value();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 577 */         (arrayOfParameter[b]).mode = ParameterMode.from_int(namedValue.flags() - 1);
/*     */       } 
/* 579 */     } catch (Exception exception) {
/* 580 */       throw this.wrapper.exceptionInArguments(exception);
/*     */     } 
/*     */     
/* 583 */     return arrayOfParameter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Any exceptionToAny(Exception paramException) {
/* 593 */     Any any = this.myORB.create_any();
/*     */     
/* 595 */     if (paramException == null)
/*     */     {
/*     */       
/* 598 */       throw this.wrapper.exceptionWasNull2(); } 
/* 599 */     if (paramException instanceof SystemException) {
/* 600 */       ORBUtility.insertSystemException((SystemException)paramException, any);
/*     */     }
/* 602 */     else if (paramException instanceof ApplicationException) {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 608 */         ApplicationException applicationException = (ApplicationException)paramException;
/*     */         
/* 610 */         insertApplicationException(applicationException, any);
/* 611 */       } catch (UNKNOWN uNKNOWN) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 617 */         ORBUtility.insertSystemException((SystemException)uNKNOWN, any);
/*     */       } 
/* 619 */     } else if (paramException instanceof UserException) {
/*     */       try {
/* 621 */         UserException userException = (UserException)paramException;
/* 622 */         insertUserException(userException, any);
/* 623 */       } catch (UNKNOWN uNKNOWN) {
/* 624 */         ORBUtility.insertSystemException((SystemException)uNKNOWN, any);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 629 */     return any;
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
/*     */   protected ServiceContext getServiceContext(HashMap<Integer, ServiceContext> paramHashMap, ServiceContexts paramServiceContexts, int paramInt) {
/* 641 */     ServiceContext serviceContext = null;
/* 642 */     Integer integer = new Integer(paramInt);
/*     */ 
/*     */ 
/*     */     
/* 646 */     serviceContext = (ServiceContext)paramHashMap.get(integer);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 651 */     if (serviceContext == null) {
/*     */ 
/*     */ 
/*     */       
/* 655 */       ServiceContext serviceContext1 = paramServiceContexts.get(paramInt);
/* 656 */       if (serviceContext1 == null) {
/* 657 */         throw this.stdWrapper.invalidServiceContextId();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 663 */       EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.myORB);
/*     */       
/* 665 */       serviceContext1.write((OutputStream)encapsOutputStream, GIOPVersion.V1_2);
/* 666 */       InputStream inputStream = encapsOutputStream.create_input_stream();
/* 667 */       serviceContext = ServiceContextHelper.read(inputStream);
/*     */       
/* 669 */       paramHashMap.put(integer, serviceContext);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 676 */     return serviceContext;
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
/*     */   protected void addServiceContext(HashMap<Integer, ServiceContext> paramHashMap, ServiceContexts paramServiceContexts, ServiceContext paramServiceContext, boolean paramBoolean) {
/* 697 */     int i = 0;
/*     */ 
/*     */     
/* 700 */     EncapsOutputStream encapsOutputStream = OutputStreamFactory.newEncapsOutputStream(this.myORB);
/* 701 */     InputStream inputStream = null;
/* 702 */     UnknownServiceContext unknownServiceContext = null;
/* 703 */     ServiceContextHelper.write((OutputStream)encapsOutputStream, paramServiceContext);
/* 704 */     inputStream = encapsOutputStream.create_input_stream();
/*     */ 
/*     */ 
/*     */     
/* 708 */     unknownServiceContext = new UnknownServiceContext(inputStream.read_long(), (InputStream)inputStream);
/*     */ 
/*     */     
/* 711 */     i = unknownServiceContext.getId();
/*     */     
/* 713 */     if (paramServiceContexts.get(i) != null)
/* 714 */       if (paramBoolean) {
/* 715 */         paramServiceContexts.delete(i);
/*     */       } else {
/* 717 */         throw this.stdWrapper.serviceContextAddFailed(new Integer(i));
/*     */       }  
/* 719 */     paramServiceContexts.put((ServiceContext)unknownServiceContext);
/*     */ 
/*     */     
/* 722 */     paramHashMap.put(new Integer(i), paramServiceContext);
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
/*     */   protected void setFlowStackIndex(int paramInt) {
/* 736 */     this.flowStackIndex = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getFlowStackIndex() {
/* 745 */     return this.flowStackIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEndingPointCall(int paramInt) {
/* 753 */     this.endingPointCall = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getEndingPointCall() {
/* 761 */     return this.endingPointCall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setIntermediatePointCall(int paramInt) {
/* 769 */     this.intermediatePointCall = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getIntermediatePointCall() {
/* 777 */     return this.intermediatePointCall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setStartingPointCall(int paramInt) {
/* 785 */     this.startingPointCall = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStartingPointCall() {
/* 793 */     return this.startingPointCall;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean getAlreadyExecuted() {
/* 801 */     return this.alreadyExecuted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAlreadyExecuted(boolean paramBoolean) {
/* 809 */     this.alreadyExecuted = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setReplyStatus(short paramShort) {
/* 816 */     this.replyStatus = paramShort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected short getReplyStatus() {
/* 824 */     return this.replyStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setForwardRequest(ForwardRequest paramForwardRequest) {
/* 832 */     this.forwardRequest = paramForwardRequest;
/* 833 */     this.forwardRequestIOR = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setForwardRequest(IOR paramIOR) {
/* 841 */     this.forwardRequestIOR = paramIOR;
/* 842 */     this.forwardRequest = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ForwardRequest getForwardRequestException() {
/* 849 */     if (this.forwardRequest == null && 
/* 850 */       this.forwardRequestIOR != null) {
/*     */ 
/*     */       
/* 853 */       Object object = iorToObject(this.forwardRequestIOR);
/* 854 */       this.forwardRequest = new ForwardRequest(object);
/*     */     } 
/*     */ 
/*     */     
/* 858 */     return this.forwardRequest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IOR getForwardRequestIOR() {
/* 865 */     if (this.forwardRequestIOR == null && 
/* 866 */       this.forwardRequest != null) {
/* 867 */       this.forwardRequestIOR = ORBUtility.getIOR(this.forwardRequest.forward);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 872 */     return this.forwardRequestIOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setException(Exception paramException) {
/* 880 */     this.exception = paramException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Exception getException() {
/* 888 */     return this.exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCurrentExecutionPoint(int paramInt) {
/* 897 */     this.currentExecutionPoint = paramInt;
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
/*     */   protected abstract void checkAccess(int paramInt) throws BAD_INV_ORDER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setSlotTable(SlotTable paramSlotTable) {
/* 922 */     this.slotTable = paramSlotTable;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object iorToObject(IOR paramIOR) {
/* 927 */     return ORBUtility.makeObjectReference(paramIOR);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/interceptors/RequestInfoImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */