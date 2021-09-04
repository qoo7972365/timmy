/*     */ package com.adventnet.appmanager.struts.form;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ 
/*     */ 
/*     */ public class AMProcessTemplateForm
/*     */   extends ActionForm
/*     */ {
/*  11 */   private final Map dynamicelement = new HashMap();
/*     */   String templateName;
/*     */   String templateDescription;
/*  14 */   String addprocesstype = "manual";
/*     */   String method;
/*     */   String processName;
/*     */   String processPath;
/*     */   String processArgument;
/*  19 */   boolean thresholdCheck = true;
/*     */   
/*     */   String serviceDName;
/*     */   String serviceName;
/*     */   
/*     */   public Map getDynamicElement()
/*     */   {
/*  26 */     return this.dynamicelement;
/*     */   }
/*     */   
/*     */   public void setTemplateName(String templateName) {
/*  30 */     this.templateName = templateName;
/*     */   }
/*     */   
/*     */   public String getTemplateName()
/*     */   {
/*  35 */     return this.templateName;
/*     */   }
/*     */   
/*     */   public void setTemplateDescription(String templateDescription)
/*     */   {
/*  40 */     this.templateDescription = templateDescription;
/*     */   }
/*     */   
/*     */   public String getTemplateDescription()
/*     */   {
/*  45 */     return this.templateDescription;
/*     */   }
/*     */   
/*     */   public void setAddProcessType(String addprocessmethod)
/*     */   {
/*  50 */     this.addprocesstype = addprocessmethod;
/*     */   }
/*     */   
/*     */   public String getAddProcessType()
/*     */   {
/*  55 */     return this.addprocesstype;
/*     */   }
/*     */   
/*     */   public String getMethod()
/*     */   {
/*  60 */     return this.method;
/*     */   }
/*     */   
/*     */   public void setMethod(String method)
/*     */   {
/*  65 */     this.method = method;
/*     */   }
/*     */   
/*     */   public void setProcessName(String processName)
/*     */   {
/*  70 */     this.processName = processName;
/*     */   }
/*     */   
/*     */   public String getProcessName()
/*     */   {
/*  75 */     return this.processName;
/*     */   }
/*     */   
/*     */   public void setServiceDisplayName(String serviceDName)
/*     */   {
/*  80 */     this.serviceDName = serviceDName;
/*     */   }
/*     */   
/*     */   public String getServiceDisplayName()
/*     */   {
/*  85 */     return this.serviceDName;
/*     */   }
/*     */   
/*     */   public void setServiceName(String serviceName)
/*     */   {
/*  90 */     this.serviceName = serviceName;
/*     */   }
/*     */   
/*     */   public String getServiceName()
/*     */   {
/*  95 */     return this.serviceName;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setProcessPath(String processpath)
/*     */   {
/* 101 */     this.processPath = processpath;
/*     */   }
/*     */   
/*     */   public String getProcessPath()
/*     */   {
/* 106 */     return this.processPath;
/*     */   }
/*     */   
/*     */   public String setProcessCommandArgs(String processArgument)
/*     */   {
/* 111 */     return this.processArgument;
/*     */   }
/*     */   
/*     */   public String getProcessCommandArgs()
/*     */   {
/* 116 */     return this.processArgument;
/*     */   }
/*     */   
/*     */   public boolean isThresholdChecked()
/*     */   {
/* 121 */     return this.thresholdCheck;
/*     */   }
/*     */   
/*     */   public void setThresholdChecked(boolean thresholdCheck)
/*     */   {
/* 126 */     this.thresholdCheck = thresholdCheck;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setValue(String key, Object value)
/*     */   {
/* 132 */     this.dynamicelement.put(key, value);
/*     */   }
/*     */   
/*     */   public Object getValue(String key)
/*     */   {
/* 137 */     return this.dynamicelement.get(key);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\form\AMProcessTemplateForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */