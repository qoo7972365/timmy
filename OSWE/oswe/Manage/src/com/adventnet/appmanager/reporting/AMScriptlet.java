/*    */ package com.adventnet.appmanager.reporting;
/*    */ 
/*    */ import dori.jasper.engine.JRDefaultScriptlet;
/*    */ import dori.jasper.engine.JRScriptletException;
/*    */ import dori.jasper.engine.fill.JRFillField;
/*    */ import dori.jasper.engine.fill.JRFillParameter;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AMScriptlet
/*    */   extends JRDefaultScriptlet
/*    */ {
/*    */   public void setParameterValue(String parameterName, Object value)
/*    */     throws JRScriptletException
/*    */   {
/* 19 */     JRFillParameter parameter = (JRFillParameter)this.parametersMap.get(parameterName);
/* 20 */     if (parameter == null)
/*    */     {
/* 22 */       throw new JRScriptletException("Parameter not found : " + parameterName);
/*    */     }
/*    */     
/* 25 */     if ((value != null) && (!parameter.getValueClass().isInstance(value)))
/*    */     {
/* 27 */       throw new JRScriptletException("Incompatible value assigned to parameter " + parameterName + ". Expected " + parameter.getValueClass().getName() + ".");
/*    */     }
/* 29 */     parameter.setValue(value);
/*    */   }
/*    */   
/*    */   public void setFieldValue(String fieldName, Object value) throws JRScriptletException
/*    */   {
/* 34 */     JRFillField field = (JRFillField)this.fieldsMap.get(fieldName);
/* 35 */     if (field == null)
/*    */     {
/* 37 */       throw new JRScriptletException("Field not found : " + fieldName);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 44 */     field.setValue(value);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\reporting\AMScriptlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */