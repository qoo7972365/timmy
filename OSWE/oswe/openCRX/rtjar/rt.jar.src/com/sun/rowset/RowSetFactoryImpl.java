/*    */ package com.sun.rowset;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.rowset.CachedRowSet;
/*    */ import javax.sql.rowset.FilteredRowSet;
/*    */ import javax.sql.rowset.JdbcRowSet;
/*    */ import javax.sql.rowset.JoinRowSet;
/*    */ import javax.sql.rowset.RowSetFactory;
/*    */ import javax.sql.rowset.WebRowSet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RowSetFactoryImpl
/*    */   implements RowSetFactory
/*    */ {
/*    */   public CachedRowSet createCachedRowSet() throws SQLException {
/* 49 */     return new CachedRowSetImpl();
/*    */   }
/*    */   
/*    */   public FilteredRowSet createFilteredRowSet() throws SQLException {
/* 53 */     return new FilteredRowSetImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public JdbcRowSet createJdbcRowSet() throws SQLException {
/* 58 */     return new JdbcRowSetImpl();
/*    */   }
/*    */   
/*    */   public JoinRowSet createJoinRowSet() throws SQLException {
/* 62 */     return new JoinRowSetImpl();
/*    */   }
/*    */   
/*    */   public WebRowSet createWebRowSet() throws SQLException {
/* 66 */     return new WebRowSetImpl();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/rowset/RowSetFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */