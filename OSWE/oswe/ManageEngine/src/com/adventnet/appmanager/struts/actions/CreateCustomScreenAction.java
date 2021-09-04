/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
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
/*     */ public final class CreateCustomScreenAction
/*     */   extends Action
/*     */ {
/*  42 */   private AMConnectionPool cp = AMConnectionPool.getInstance();
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
/*  74 */     ActionMessages messages = new ActionMessages();
/*  75 */     ActionErrors errors = new ActionErrors();
/*     */     try
/*     */     {
/*  78 */       String csname = (String)((DynaActionForm)form).get("customscreenname");
/*  79 */       String csdesc = (String)((DynaActionForm)form).get("customscreendescription");
/*  80 */       String haid = (String)((DynaActionForm)form).get("haid");
/*  81 */       String camid = (String)((DynaActionForm)form).get("camid");
/*     */       
/*  83 */       String screenType = (String)((DynaActionForm)form).get("camscreentype");
/*     */       
/*     */ 
/*  86 */       request.setAttribute("csname", csname);
/*  87 */       request.setAttribute("csdesc", csdesc);
/*  88 */       request.setAttribute("haid", haid);
/*  89 */       request.setAttribute("camid", String.valueOf(camid));
/*  90 */       if (CAMDBUtil.camScreenExists(camid, FormatUtil.replaceStringBySpecifiedString(csname, "'", "\\'", 0)))
/*     */       {
/*  92 */         errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Screen with the name \"" + csname + "\" already exists. Please provide a different name", ""));
/*  93 */         saveErrors(request, errors);
/*  94 */         return mapping.getInputForward();
/*     */       }
/*  96 */       int casID = CAMDBUtil.insertCAMScreenDetails(FormatUtil.replaceStringBySpecifiedString(csname, "'", "\\'", 0), csdesc, Integer.parseInt(screenType), Integer.parseInt(camid));
/*  97 */       request.setAttribute("screenid", String.valueOf(casID));
/*     */       
/*  99 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("Created the custom screen " + csname + " successfully"));
/* 100 */       saveMessages(request, messages);
/*     */ 
/*     */     }
/*     */     catch (Exception ee)
/*     */     {
/*     */ 
/* 106 */       ee.printStackTrace();
/* 107 */       errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("Error ocurred while creating the screen. Please try again."));
/*     */     }
/*     */     
/*     */ 
/* 111 */     if (!errors.isEmpty()) {
/* 112 */       saveErrors(request, errors);
/* 113 */       return mapping.getInputForward();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 118 */     return mapping.findForward("success");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\CreateCustomScreenAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */