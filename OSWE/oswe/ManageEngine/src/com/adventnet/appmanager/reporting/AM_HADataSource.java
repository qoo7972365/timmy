/*    */ package com.adventnet.appmanager.reporting;
/*    */ 
/*    */ import dori.jasper.engine.JRDataSource;
/*    */ import dori.jasper.engine.JRException;
/*    */ import dori.jasper.engine.JRField;
/*    */ 
/*    */ public class AM_HADataSource
/*    */   implements JRDataSource
/*    */ {
/* 10 */   AMDataSet obj = null;
/* 11 */   int index = -1;
/* 12 */   int count = -1;
/*    */   
/*    */   public AM_HADataSource(AMDataSet dataObject)
/*    */   {
/* 16 */     this.obj = dataObject;
/* 17 */     this.count = this.obj.getRows();
/*    */   }
/*    */   
/*    */   public boolean next() throws JRException
/*    */   {
/* 22 */     this.index += 1;
/* 23 */     return this.index < this.count;
/*    */   }
/*    */   
/*    */   public Object getFieldValue(JRField field) throws JRException
/*    */   {
/* 28 */     Object value = null;
/* 29 */     String fieldName = field.getName();
/*    */     try
/*    */     {
/* 32 */       if ("Name".equals(fieldName))
/*    */       {
/* 34 */         value = this.obj.get(this.index, 0);
/*    */       }
/* 36 */       else if ("Min".equals(fieldName))
/*    */       {
/* 38 */         value = this.obj.get(this.index, 1);
/*    */       }
/* 40 */       else if ("Max".equals(fieldName))
/*    */       {
/* 42 */         value = this.obj.get(this.index, 2);
/*    */       }
/* 44 */       else if ("Avg".equals(fieldName))
/*    */       {
/* 46 */         value = this.obj.get(this.index, 3);
/*    */       }
/* 48 */       else if ("Availability".equals(fieldName))
/*    */       {
/* 50 */         value = this.obj.get(this.index, 7);
/*    */       }
/* 52 */       else if ("Clear".equals(fieldName))
/*    */       {
/* 54 */         value = this.obj.get(this.index, 4);
/*    */       }
/* 56 */       else if ("Critical".equals(fieldName))
/*    */       {
/* 58 */         value = this.obj.get(this.index, 5);
/*    */       }
/* 60 */       else if ("Warning".equals(fieldName))
/*    */       {
/* 62 */         value = this.obj.get(this.index, 6);
/*    */       }
/*    */     }
/*    */     catch (Exception exp) {
/* 66 */       throw new JRException(exp.toString());
/*    */     }
/* 68 */     return value;
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\AM_HADataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */