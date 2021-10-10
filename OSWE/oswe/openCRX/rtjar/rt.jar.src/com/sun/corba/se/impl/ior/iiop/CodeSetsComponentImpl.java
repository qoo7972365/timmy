/*     */ package com.sun.corba.se.impl.ior.iiop;
/*     */ 
/*     */ import com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
/*     */ import com.sun.corba.se.impl.encoding.MarshalInputStream;
/*     */ import com.sun.corba.se.impl.encoding.MarshalOutputStream;
/*     */ import com.sun.corba.se.spi.ior.TaggedComponentBase;
/*     */ import com.sun.corba.se.spi.ior.iiop.CodeSetsComponent;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import org.omg.CORBA_2_3.portable.InputStream;
/*     */ import org.omg.CORBA_2_3.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CodeSetsComponentImpl
/*     */   extends TaggedComponentBase
/*     */   implements CodeSetsComponent
/*     */ {
/*     */   CodeSetComponentInfo csci;
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  51 */     if (!(paramObject instanceof CodeSetsComponentImpl)) {
/*  52 */       return false;
/*     */     }
/*  54 */     CodeSetsComponentImpl codeSetsComponentImpl = (CodeSetsComponentImpl)paramObject;
/*     */     
/*  56 */     return this.csci.equals(codeSetsComponentImpl.csci);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  61 */     return this.csci.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  66 */     return "CodeSetsComponentImpl[csci=" + this.csci + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSetsComponentImpl() {
/*  72 */     this.csci = new CodeSetComponentInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSetsComponentImpl(InputStream paramInputStream) {
/*  77 */     this.csci = new CodeSetComponentInfo();
/*  78 */     this.csci.read((MarshalInputStream)paramInputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public CodeSetsComponentImpl(ORB paramORB) {
/*  83 */     if (paramORB == null) {
/*  84 */       this.csci = new CodeSetComponentInfo();
/*     */     } else {
/*  86 */       this.csci = paramORB.getORBData().getCodeSetComponentInfo();
/*     */     } 
/*     */   }
/*     */   
/*     */   public CodeSetComponentInfo getCodeSetComponentInfo() {
/*  91 */     return this.csci;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeContents(OutputStream paramOutputStream) {
/*  96 */     this.csci.write((MarshalOutputStream)paramOutputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 101 */     return 1;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/ior/iiop/CodeSetsComponentImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */