/*    */ package com.sun.jndi.toolkit.dir;
/*    */ 
/*    */ import javax.naming.NamingEnumeration;
/*    */ import javax.naming.NamingException;
/*    */ import javax.naming.directory.Attributes;
/*    */ import javax.naming.directory.DirContext;
/*    */ import javax.naming.directory.SearchControls;
/*    */ import javax.naming.directory.SearchResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirSearch
/*    */ {
/*    */   public static NamingEnumeration<SearchResult> search(DirContext paramDirContext, Attributes paramAttributes, String[] paramArrayOfString) throws NamingException {
/* 40 */     SearchControls searchControls = new SearchControls(1, 0L, 0, paramArrayOfString, false, false);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return new LazySearchEnumerationImpl(new ContextEnumerator(paramDirContext, 1), new ContainmentFilter(paramAttributes), searchControls);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NamingEnumeration<SearchResult> search(DirContext paramDirContext, String paramString, SearchControls paramSearchControls) throws NamingException {
/* 54 */     if (paramSearchControls == null) {
/* 55 */       paramSearchControls = new SearchControls();
/*    */     }
/* 57 */     return new LazySearchEnumerationImpl(new ContextEnumerator(paramDirContext, paramSearchControls
/* 58 */           .getSearchScope()), new SearchFilter(paramString), paramSearchControls);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NamingEnumeration<SearchResult> search(DirContext paramDirContext, String paramString, Object[] paramArrayOfObject, SearchControls paramSearchControls) throws NamingException {
/* 67 */     String str = SearchFilter.format(paramString, paramArrayOfObject);
/* 68 */     return search(paramDirContext, str, paramSearchControls);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/toolkit/dir/DirSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */