/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.org.omg.CORBA.Repository;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*     */ import com.sun.org.omg.SendingContext._CodeBaseImplBase;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Stack;
/*     */ import javax.rmi.CORBA.Util;
/*     */ import javax.rmi.CORBA.ValueHandler;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.ORB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FVDCodeBaseImpl
/*     */   extends _CodeBaseImplBase
/*     */ {
/*  58 */   private static Hashtable fvds = new Hashtable<>();
/*     */ 
/*     */ 
/*     */   
/*  62 */   private transient ORB orb = null;
/*     */   
/*  64 */   private transient OMGSystemException wrapper = OMGSystemException.get("rpc.encoding");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private transient ValueHandlerImpl vhandler = null;
/*     */ 
/*     */   
/*     */   void setValueHandler(ValueHandler paramValueHandler) {
/*  75 */     this.vhandler = (ValueHandlerImpl)paramValueHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public Repository get_ir() {
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String implementation(String paramString) {
/*     */     try {
/*  88 */       if (this.vhandler == null) {
/*  89 */         this.vhandler = ValueHandlerImpl.getInstance(false);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  94 */       String str = Util.getCodebase(this.vhandler.getClassFromType(paramString));
/*  95 */       if (str == null) {
/*  96 */         return "";
/*     */       }
/*  98 */       return str;
/*  99 */     } catch (ClassNotFoundException classNotFoundException) {
/* 100 */       throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE, classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] implementations(String[] paramArrayOfString) {
/* 106 */     String[] arrayOfString = new String[paramArrayOfString.length];
/*     */     
/* 108 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 109 */       arrayOfString[b] = implementation(paramArrayOfString[b]);
/*     */     }
/* 111 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullValueDescription meta(String paramString) {
/*     */     try {
/* 117 */       FullValueDescription fullValueDescription = (FullValueDescription)fvds.get(paramString);
/*     */       
/* 119 */       if (fullValueDescription == null) {
/*     */ 
/*     */         
/* 122 */         if (this.vhandler == null) {
/* 123 */           this.vhandler = ValueHandlerImpl.getInstance(false);
/*     */         }
/*     */         
/*     */         try {
/* 127 */           fullValueDescription = ValueUtility.translate(_orb(), 
/* 128 */               ObjectStreamClass.lookup(this.vhandler.getAnyClassFromType(paramString)), (ValueHandler)this.vhandler);
/* 129 */         } catch (Throwable throwable) {
/* 130 */           if (this.orb == null)
/* 131 */             this.orb = ORB.init(); 
/* 132 */           fullValueDescription = ValueUtility.translate(this.orb, 
/* 133 */               ObjectStreamClass.lookup(this.vhandler.getAnyClassFromType(paramString)), (ValueHandler)this.vhandler);
/*     */         } 
/*     */         
/* 136 */         if (fullValueDescription != null) {
/* 137 */           fvds.put(paramString, fullValueDescription);
/*     */         } else {
/* 139 */           throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE);
/*     */         } 
/*     */       } 
/*     */       
/* 143 */       return fullValueDescription;
/* 144 */     } catch (Throwable throwable) {
/* 145 */       throw this.wrapper.incompatibleValueImpl(CompletionStatus.COMPLETED_MAYBE, throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   public FullValueDescription[] metas(String[] paramArrayOfString) {
/* 150 */     FullValueDescription[] arrayOfFullValueDescription = new FullValueDescription[paramArrayOfString.length];
/*     */     
/* 152 */     for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 153 */       arrayOfFullValueDescription[b] = meta(paramArrayOfString[b]);
/*     */     }
/* 155 */     return arrayOfFullValueDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] bases(String paramString) {
/*     */     try {
/* 163 */       if (this.vhandler == null) {
/* 164 */         this.vhandler = ValueHandlerImpl.getInstance(false);
/*     */       }
/*     */       
/* 167 */       Stack<String> stack = new Stack();
/* 168 */       Class<?> clazz = ObjectStreamClass.lookup(this.vhandler.getClassFromType(paramString)).forClass().getSuperclass();
/*     */       
/* 170 */       while (!clazz.equals(Object.class)) {
/* 171 */         stack.push(this.vhandler.createForAnyType(clazz));
/* 172 */         clazz = clazz.getSuperclass();
/*     */       } 
/*     */       
/* 175 */       String[] arrayOfString = new String[stack.size()];
/* 176 */       for (int i = arrayOfString.length - 1; i >= 0; i++) {
/* 177 */         arrayOfString[i] = stack.pop();
/*     */       }
/* 179 */       return arrayOfString;
/* 180 */     } catch (Throwable throwable) {
/* 181 */       throw this.wrapper.missingLocalValueImpl(CompletionStatus.COMPLETED_MAYBE, throwable);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/FVDCodeBaseImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */