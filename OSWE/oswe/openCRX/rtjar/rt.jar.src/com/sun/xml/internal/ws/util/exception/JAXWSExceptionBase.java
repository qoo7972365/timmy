/*     */ package com.sun.xml.internal.ws.util.exception;
/*     */ 
/*     */ import com.sun.istack.internal.localization.Localizable;
/*     */ import com.sun.istack.internal.localization.LocalizableMessage;
/*     */ import com.sun.istack.internal.localization.LocalizableMessageFactory;
/*     */ import com.sun.istack.internal.localization.Localizer;
/*     */ import com.sun.istack.internal.localization.NullLocalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JAXWSExceptionBase
/*     */   extends WebServiceException
/*     */   implements Localizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private transient Localizable msg;
/*     */   
/*     */   protected JAXWSExceptionBase(String key, Object... args) {
/*  60 */     super(findNestedException(args));
/*  61 */     this.msg = (Localizable)new LocalizableMessage(getDefaultResourceBundleName(), key, args);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXWSExceptionBase(String message) {
/*  66 */     this((Localizable)new NullLocalizable(message));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXWSExceptionBase(Throwable throwable) {
/*  73 */     this((Localizable)new NullLocalizable(throwable.toString()), throwable);
/*     */   }
/*     */   
/*     */   protected JAXWSExceptionBase(Localizable msg) {
/*  77 */     this.msg = msg;
/*     */   }
/*     */   
/*     */   protected JAXWSExceptionBase(Localizable msg, Throwable cause) {
/*  81 */     super(cause);
/*  82 */     this.msg = msg;
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
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/*  95 */     out.defaultWriteObject();
/*     */     
/*  97 */     out.writeObject(this.msg.getResourceBundleName());
/*  98 */     out.writeObject(this.msg.getKey());
/*  99 */     Object[] args = this.msg.getArguments();
/* 100 */     if (args == null) {
/* 101 */       out.writeInt(-1);
/*     */       return;
/*     */     } 
/* 104 */     out.writeInt(args.length);
/*     */     
/* 106 */     for (int i = 0; i < args.length; i++) {
/* 107 */       if (args[i] == null || args[i] instanceof java.io.Serializable) {
/* 108 */         out.writeObject(args[i]);
/*     */       } else {
/* 110 */         out.writeObject(args[i].toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*     */     Object[] args;
/* 117 */     in.defaultReadObject();
/*     */     
/* 119 */     String resourceBundleName = (String)in.readObject();
/* 120 */     String key = (String)in.readObject();
/* 121 */     int len = in.readInt();
/* 122 */     if (len < -1)
/* 123 */       throw new NegativeArraySizeException(); 
/* 124 */     if (len == -1) {
/* 125 */       args = null;
/* 126 */     } else if (len < 255) {
/* 127 */       args = new Object[len];
/* 128 */       for (int i = 0; i < args.length; i++) {
/* 129 */         args[i] = in.readObject();
/*     */       }
/*     */     } else {
/* 132 */       List<Object> argList = new ArrayList(Math.min(len, 1024));
/* 133 */       for (int i = 0; i < len; i++) {
/* 134 */         argList.add(in.readObject());
/*     */       }
/* 136 */       args = argList.toArray(new Object[argList.size()]);
/*     */     } 
/* 138 */     this.msg = (new LocalizableMessageFactory(resourceBundleName)).getMessage(key, args);
/*     */   }
/*     */   
/*     */   private static Throwable findNestedException(Object[] args) {
/* 142 */     if (args == null) {
/* 143 */       return null;
/*     */     }
/* 145 */     for (Object o : args) {
/* 146 */       if (o instanceof Throwable)
/* 147 */         return (Throwable)o; 
/* 148 */     }  return null;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 152 */     Localizer localizer = new Localizer();
/* 153 */     return localizer.localize(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String getDefaultResourceBundleName();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getKey() {
/* 166 */     return this.msg.getKey();
/*     */   }
/*     */   
/*     */   public final Object[] getArguments() {
/* 170 */     return this.msg.getArguments();
/*     */   }
/*     */   
/*     */   public final String getResourceBundleName() {
/* 174 */     return this.msg.getResourceBundleName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/exception/JAXWSExceptionBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */