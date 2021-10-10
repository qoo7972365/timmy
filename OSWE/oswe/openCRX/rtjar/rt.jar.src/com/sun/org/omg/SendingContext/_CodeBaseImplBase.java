/*     */ package com.sun.org.omg.SendingContext;
/*     */ 
/*     */ import com.sun.org.omg.CORBA.Repository;
/*     */ import com.sun.org.omg.CORBA.RepositoryHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdHelper;
/*     */ import com.sun.org.omg.CORBA.RepositoryIdSeqHelper;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
/*     */ import com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
/*     */ import com.sun.org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
/*     */ import com.sun.org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;
/*     */ import java.util.Hashtable;
/*     */ import org.omg.CORBA.BAD_OPERATION;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.InvokeHandler;
/*     */ import org.omg.CORBA.portable.ObjectImpl;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ import org.omg.CORBA.portable.ResponseHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class _CodeBaseImplBase
/*     */   extends ObjectImpl
/*     */   implements CodeBase, InvokeHandler
/*     */ {
/*  44 */   private static Hashtable _methods = new Hashtable<>();
/*     */   
/*     */   static {
/*  47 */     _methods.put("get_ir", new Integer(0));
/*  48 */     _methods.put("implementation", new Integer(1));
/*  49 */     _methods.put("implementations", new Integer(2));
/*  50 */     _methods.put("meta", new Integer(3));
/*  51 */     _methods.put("metas", new Integer(4));
/*  52 */     _methods.put("bases", new Integer(5));
/*     */   }
/*     */   public OutputStream _invoke(String paramString, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
/*     */     Repository repository;
/*     */     String str3, arrayOfString2[], str2, arrayOfString1[], str1, str4, arrayOfString4[];
/*     */     FullValueDescription fullValueDescription, arrayOfFullValueDescription[];
/*     */     String[] arrayOfString3;
/*  59 */     OutputStream outputStream = paramResponseHandler.createReply();
/*  60 */     Integer integer = (Integer)_methods.get(paramString);
/*  61 */     if (integer == null) {
/*  62 */       throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*  64 */     switch (integer.intValue()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 0:
/*  70 */         repository = null;
/*  71 */         repository = get_ir();
/*  72 */         RepositoryHelper.write(outputStream, repository);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 131 */         return outputStream;case 1: str3 = RepositoryIdHelper.read(paramInputStream); str4 = null; str4 = implementation(str3); outputStream.write_string(str4); return outputStream;case 2: arrayOfString2 = RepositoryIdSeqHelper.read(paramInputStream); str4 = null; arrayOfString4 = implementations(arrayOfString2); URLSeqHelper.write(outputStream, arrayOfString4); return outputStream;case 3: str2 = RepositoryIdHelper.read(paramInputStream); arrayOfString4 = null; fullValueDescription = meta(str2); FullValueDescriptionHelper.write(outputStream, fullValueDescription); return outputStream;case 4: arrayOfString1 = RepositoryIdSeqHelper.read(paramInputStream); fullValueDescription = null; arrayOfFullValueDescription = metas(arrayOfString1); ValueDescSeqHelper.write(outputStream, arrayOfFullValueDescription); return outputStream;case 5: str1 = RepositoryIdHelper.read(paramInputStream); arrayOfFullValueDescription = null; arrayOfString3 = bases(str1); RepositoryIdSeqHelper.write(outputStream, arrayOfString3); return outputStream;
/*     */     } 
/*     */     throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
/*     */   }
/* 135 */   private static String[] __ids = new String[] { "IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] _ids() {
/* 141 */     return (String[])__ids.clone();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/omg/SendingContext/_CodeBaseImplBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */