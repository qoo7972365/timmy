/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.cam.CAMServerUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil;
/*     */ import com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionError;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.action.DynaActionForm;
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
/*     */ public final class CreateCustomApplicationAction
/*     */   extends Action
/*     */ {
/*  47 */   private AMConnectionPool cp = AMConnectionPool.getInstance();
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
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/*  81 */     boolean isWizard = request.getParameter("wiz") != null;
/*  82 */     boolean showconfigurealerts = false;
/*  83 */     String configurealerts = request.getParameter("configurealerts");
/*  84 */     if ((configurealerts != null) && (configurealerts.equals("true")))
/*     */     {
/*  86 */       showconfigurealerts = true;
/*     */     }
/*     */     
/*  89 */     ActionMessages messages = new ActionMessages();
/*  90 */     ActionErrors errors = new ActionErrors();
/*  91 */     String haid = null;
/*     */     try
/*     */     {
/*  94 */       boolean isallowed = DataCollectionControllerUtil.isallowed();
/*  95 */       if (!isallowed)
/*     */       {
/*  97 */         if (isWizard)
/*     */         {
/*  99 */           if (showconfigurealerts)
/*     */           {
/* 101 */             return new ActionForward("/showActionProfiles.do?method=getHAProfiles");
/*     */           }
/*     */           
/* 104 */           return new ActionForward("/showresource.do?method=associatemonitorsinwiz");
/*     */         }
/*     */         
/*     */ 
/* 108 */         return new ActionForward("/adminAction.do?method=showMonitorTemplates");
/*     */       }
/*     */       
/* 111 */       String camname = (String)((DynaActionForm)form).get("customapplicationname");
/* 112 */       String camdesc = (String)((DynaActionForm)form).get("customapplicationdescription");
/* 113 */       haid = (String)((DynaActionForm)form).get("haid");
/* 114 */       int caID = -1;
/* 115 */       if (DataCollectionDBUtil.isMOExists(camname)) {
/* 116 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("cam.camnameexists"));
/*     */         
/* 118 */         request.setAttribute("customapplicationname", camname);
/* 119 */         request.setAttribute("customapplicationdescription", camdesc);
/*     */       }
/*     */       else
/*     */       {
/* 123 */         caID = CAMDBUtil.create(camname, camdesc, haid);
/* 124 */         CAMServerUtil.checkAndScheduleCAMJob(caID);
/*     */         
/* 126 */         request.setAttribute("camid", String.valueOf(caID));
/*     */       }
/*     */       
/* 129 */       request.setAttribute("camname", camname);
/* 130 */       request.setAttribute("camdesc", camdesc);
/* 131 */       request.setAttribute("haid", haid);
/*     */ 
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*     */ 
/* 138 */       ee.printStackTrace();
/* 139 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.check", ee.toString()));
/*     */     }
/*     */     
/*     */ 
/* 143 */     if (!errors.isEmpty()) {
/* 144 */       saveErrors(request, errors);
/* 145 */       return new ActionForward("/adminAction.do?method=reloadHostDiscoveryForm&type=Custom-Application&haid=" + haid);
/*     */     }
/*     */     
/* 148 */     if (isWizard)
/*     */     {
/* 150 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.applicationcreation.wizard.success"));
/*     */     }
/*     */     else
/*     */     {
/* 154 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cam.applicationcreation.success"));
/*     */     }
/* 156 */     saveMessages(request, messages);
/* 157 */     if (isWizard)
/*     */     {
/* 159 */       if (showconfigurealerts)
/*     */       {
/* 161 */         return new ActionForward("/showActionProfiles.do?method=getHAProfiles");
/*     */       }
/*     */       
/* 164 */       return mapping.findForward("associatemonitorsinwiz");
/*     */     }
/*     */     
/* 167 */     return new ActionForward("/ShowCAM.do?method=showCAMApplication&camid=" + request.getAttribute("camid") + "&haid=" + request.getAttribute("haid"), true);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CreateCustomApplicationAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */