/*     */ package com.adventnet.appmanager.tags;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspException;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ 
/*     */ public class CommonLinks extends javax.servlet.jsp.tagext.BodyTagSupport
/*     */ {
/*     */   private String linkType;
/*     */   private String resourceid;
/*     */   private String nameToTrim;
/*     */   private int trimLength;
/*     */   
/*     */   public int doAfterBody() throws JspException
/*     */   {
/*  18 */     return 0;
/*     */   }
/*     */   
/*     */   public int doEndTag() throws JspException
/*     */   {
/*     */     try
/*     */     {
/*  25 */       if (this.resourceid != null)
/*     */       {
/*  27 */         setResourceid((String)org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager.evaluate("resourceid", this.resourceid, String.class, this, this.pageContext));
/*     */       }
/*  29 */       if (this.nameToTrim != null)
/*     */       {
/*  31 */         setNameToTrim((String)org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager.evaluate("nameToTrim", this.nameToTrim, String.class, this, this.pageContext));
/*     */       }
/*  33 */       JspWriter out = this.pageContext.getOut();
/*  34 */       String toreturn = "";
/*  35 */       if (this.linkType.equals("associateMonitors"))
/*     */       {
/*  37 */         toreturn = getAssociateMonitorsLink();
/*     */       }
/*  39 */       else if (this.linkType.equals("configAlarmsForMG"))
/*     */       {
/*  41 */         toreturn = getConfigAlarmsForMG();
/*     */       }
/*  43 */       else if (this.linkType.equals("trimText"))
/*     */       {
/*  45 */         toreturn = getTrimmedText();
/*     */       }
/*  47 */       out.println(toreturn);
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/*  51 */       ex.printStackTrace();
/*     */     }
/*  53 */     return 6;
/*     */   }
/*     */   
/*     */   private String getAssociateMonitorsLink() {
/*  57 */     StringBuilder toreturn = new StringBuilder();
/*  58 */     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*     */     
/*  60 */     int mgId = Integer.parseInt(this.resourceid);
/*  61 */     String style = "class='staticlinks'";
/*  62 */     if (!this.styleClass.equals(""))
/*     */     {
/*  64 */       style = "class='" + this.styleClass + "'";
/*     */     }
/*  66 */     if (((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN"))) || (request.isUserInRole("ADMIN")))
/*     */     {
/*  68 */       toreturn.append(" <a href='/showresource.do?type=All&method=getMonitorForm&haid=" + this.resourceid + "' " + style + "><span class='summary-nav-menu'><img src='/images/icon_assoicatemonitors.gif'  width='14' height='13' border='0' hspace='3' valign='middle'>" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "</span></a>");
/*     */     }
/*     */     else
/*     */     {
/*  72 */       toreturn.append("&nbsp;");
/*     */     }
/*     */     
/*     */ 
/*  76 */     return toreturn.toString();
/*     */   }
/*     */   
/*     */   private String getConfigAlarmsForMG()
/*     */   {
/*  81 */     StringBuilder toreturn = new StringBuilder();
/*  82 */     HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
/*  83 */     String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*  84 */     int mgId = Integer.parseInt(this.resourceid);
/*  85 */     String style = "class='staticlinks'";
/*  86 */     if (!this.styleClass.equals(""))
/*     */     {
/*  88 */       style = "class='" + this.styleClass + "'";
/*     */     }
/*  90 */     if (((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN"))) || (request.isUserInRole("ADMIN")))
/*     */     {
/*  92 */       toreturn.append("<a href='/showActionProfiles.do?method=getHAProfiles&haid=" + this.resourceid + "' " + style + " ><span class='summary-nav-menu'><img src='/images/icon_associateaction.gif' hspace='3'  valign='middle' alt=" + ALERTCONFIG_TEXT + " border='0'>" + ALERTCONFIG_TEXT + "</span></a>");
/*     */     }
/*     */     else
/*     */     {
/*  96 */       toreturn.append("&nbsp;");
/*     */     }
/*     */     
/*     */ 
/* 100 */     return toreturn.toString();
/*     */   }
/*     */   
/*     */   private String getTrimmedText() {
/* 104 */     return FormatUtil.getTrimmedText(this.nameToTrim, this.trimLength);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */   private String styleClass = "";
/*     */   
/*     */   public void setStyleClass(String styleClass) {
/* 114 */     this.styleClass = styleClass;
/*     */   }
/*     */   
/*     */   public String getStyleClass() {
/* 118 */     return this.styleClass;
/*     */   }
/*     */   
/*     */   public void setNameToTrim(String nameToTrim)
/*     */   {
/* 123 */     this.nameToTrim = nameToTrim;
/*     */   }
/*     */   
/*     */   public String getNameToTrim() {
/* 127 */     return this.nameToTrim;
/*     */   }
/*     */   
/*     */   public int getTrimLength() {
/* 131 */     return this.trimLength;
/*     */   }
/*     */   
/*     */   public void setTrimLength(int trimLength) {
/* 135 */     this.trimLength = trimLength;
/*     */   }
/*     */   
/*     */   public void setLinkType(String linkType) {
/* 139 */     this.linkType = linkType;
/*     */   }
/*     */   
/*     */   public String getLinkType() {
/* 143 */     return this.linkType;
/*     */   }
/*     */   
/*     */   public void setResourceid(String resourceid) {
/* 147 */     this.resourceid = resourceid;
/*     */   }
/*     */   
/*     */   public String getResourceid() {
/* 151 */     return this.resourceid;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\tags\CommonLinks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */