/*     */ package com.sun.corba.se.impl.javax.rmi.CORBA;
/*     */ 
/*     */ import com.sun.corba.se.impl.ior.StubIORImpl;
/*     */ import com.sun.corba.se.impl.logging.UtilSystemException;
/*     */ import com.sun.corba.se.impl.presentation.rmi.StubConnectImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.rmi.RemoteException;
/*     */ import javax.rmi.CORBA.Stub;
/*     */ import javax.rmi.CORBA.StubDelegate;
/*     */ import org.omg.CORBA.ORB;
/*     */ import org.omg.CORBA.Object;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StubDelegateImpl
/*     */   implements StubDelegate
/*     */ {
/*  65 */   static UtilSystemException wrapper = UtilSystemException.get("rmiiiop");
/*     */ 
/*     */   
/*     */   private StubIORImpl ior;
/*     */ 
/*     */   
/*     */   public StubIORImpl getIOR() {
/*  72 */     return this.ior;
/*     */   }
/*     */ 
/*     */   
/*     */   public StubDelegateImpl() {
/*  77 */     this.ior = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(Stub paramStub) {
/*  87 */     if (this.ior == null) {
/*  88 */       this.ior = new StubIORImpl((Object)paramStub);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode(Stub paramStub) {
/*  98 */     init(paramStub);
/*  99 */     return this.ior.hashCode();
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
/*     */   public boolean equals(Stub paramStub, Object paramObject) {
/* 111 */     if (paramStub == paramObject) {
/* 112 */       return true;
/*     */     }
/*     */     
/* 115 */     if (!(paramObject instanceof Stub)) {
/* 116 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 121 */     Stub stub = (Stub)paramObject;
/* 122 */     if (stub.hashCode() != paramStub.hashCode()) {
/* 123 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return paramStub.toString().equals(stub.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 136 */     if (this == paramObject) {
/* 137 */       return true;
/*     */     }
/* 139 */     if (!(paramObject instanceof StubDelegateImpl)) {
/* 140 */       return false;
/*     */     }
/* 142 */     StubDelegateImpl stubDelegateImpl = (StubDelegateImpl)paramObject;
/*     */     
/* 144 */     if (this.ior == null) {
/* 145 */       return (this.ior == stubDelegateImpl.ior);
/*     */     }
/* 147 */     return this.ior.equals(stubDelegateImpl.ior);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 151 */     if (this.ior == null) {
/* 152 */       return 0;
/*     */     }
/* 154 */     return this.ior.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(Stub paramStub) {
/* 165 */     if (this.ior == null) {
/* 166 */       return null;
/*     */     }
/* 168 */     return this.ior.toString();
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
/*     */   public void connect(Stub paramStub, ORB paramORB) throws RemoteException {
/* 185 */     this.ior = StubConnectImpl.connect(this.ior, (Object)paramStub, (ObjectImpl)paramStub, paramORB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readObject(Stub paramStub, ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 194 */     if (this.ior == null) {
/* 195 */       this.ior = new StubIORImpl();
/*     */     }
/* 197 */     this.ior.doRead(paramObjectInputStream);
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
/*     */   public void writeObject(Stub paramStub, ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 211 */     init(paramStub);
/* 212 */     this.ior.doWrite(paramObjectOutputStream);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/javax/rmi/CORBA/StubDelegateImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */