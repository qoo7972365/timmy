/*     */ package com.sun.corba.se.impl.orb;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import com.sun.corba.se.spi.orb.Operation;
/*     */ import com.sun.corba.se.spi.orb.StringPair;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrefixParserAction
/*     */   extends ParserActionBase
/*     */ {
/*     */   private Class componentType;
/*     */   private ORBUtilSystemException wrapper;
/*     */   
/*     */   public PrefixParserAction(String paramString1, Operation paramOperation, String paramString2, Class paramClass) {
/*  51 */     super(paramString1, true, paramOperation, paramString2);
/*  52 */     this.componentType = paramClass;
/*  53 */     this.wrapper = ORBUtilSystemException.get("orb.lifecycle");
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
/*     */   public Object apply(Properties paramProperties) {
/*  65 */     String str = getPropertyName();
/*  66 */     int i = str.length();
/*  67 */     if (str.charAt(i - 1) != '.') {
/*  68 */       str = str + '.';
/*  69 */       i++;
/*     */     } 
/*     */     
/*  72 */     LinkedList<Object> linkedList = new LinkedList();
/*     */ 
/*     */     
/*  75 */     Iterator<String> iterator = paramProperties.keySet().iterator();
/*  76 */     while (iterator.hasNext()) {
/*  77 */       String str1 = iterator.next();
/*  78 */       if (str1.startsWith(str)) {
/*  79 */         String str2 = str1.substring(i);
/*  80 */         String str3 = paramProperties.getProperty(str1);
/*  81 */         StringPair stringPair = new StringPair(str2, str3);
/*  82 */         Object object = getOperation().operate(stringPair);
/*  83 */         linkedList.add(object);
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     int j = linkedList.size();
/*  88 */     if (j > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       Object object = null;
/*     */       try {
/*  95 */         object = Array.newInstance(this.componentType, j);
/*  96 */       } catch (Throwable throwable) {
/*  97 */         throw this.wrapper.couldNotCreateArray(throwable, 
/*  98 */             getPropertyName(), this.componentType, new Integer(j));
/*     */       } 
/*     */ 
/*     */       
/* 102 */       Iterator<Object> iterator1 = linkedList.iterator();
/* 103 */       byte b = 0;
/* 104 */       while (iterator1.hasNext()) {
/* 105 */         Object object1 = iterator1.next();
/*     */         
/*     */         try {
/* 108 */           Array.set(object, b, object1);
/* 109 */         } catch (Throwable throwable) {
/* 110 */           throw this.wrapper.couldNotSetArray(throwable, 
/* 111 */               getPropertyName(), new Integer(b), this.componentType, new Integer(j), object1
/*     */               
/* 113 */               .toString());
/*     */         } 
/* 115 */         b++;
/*     */       } 
/*     */       
/* 118 */       return object;
/*     */     } 
/* 120 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/orb/PrefixParserAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */