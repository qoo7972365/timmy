/*    */ package com.adventnet.appmanager.struts.actions;
/*    */ 
/*    */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*    */ import com.adventnet.appmanager.util.AppManagerUtil;
/*    */ import com.adventnet.appmanager.util.Constants;
/*    */ import com.adventnet.appmanager.util.DBUtil;
/*    */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*    */ import com.adventnet.nms.util.NmsUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Properties;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.struts.action.Action;
/*    */ import org.apache.struts.action.ActionForm;
/*    */ import org.apache.struts.action.ActionForward;
/*    */ import org.apache.struts.action.ActionMapping;
/*    */ import org.apache.struts.util.LabelValueBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ShowTile
/*    */   extends Action
/*    */ {
/*    */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*    */     throws Exception
/*    */   {
/* 33 */     if (request.getParameter("haid") != null) {
/* 34 */       request.setAttribute("haid", request.getParameter("haid"));
/*    */     }
/* 36 */     String tilename = request.getParameter("TileName");
/* 37 */     request.setAttribute("disableRestrictedAdmin", DBUtil.getGlobalConfigValue("disableRestrictedAdmin"));
/* 38 */     if (tilename.equals("Tile.AdminConf"))
/*    */     {
/* 40 */       if ((request.isUserInRole("OPERATOR")) || (request.isUserInRole("MANAGER"))) {
/* 41 */         return mapping.findForward("accessRestricted");
/*    */       }
/* 43 */       request.setAttribute("HelpKey", "Admin Activities");
/* 44 */       request.setAttribute("productEdition", Constants.getCategorytype());
/* 45 */       if (Constants.isIt360)
/*    */       {
/* 47 */         return new ActionForward("IT360.Admin");
/*    */       }
/*    */     }
/* 50 */     else if ((tilename.equals(".EmailActions")) || (tilename.equals(".SMSActions")) || (tilename.equals(".AdminEmailActions")))
/*    */     {
/*    */       try {
/* 53 */         AMActionForm amForm = (AMActionForm)form;
/* 54 */         if (amForm == null) {
/* 55 */           amForm = new AMActionForm();
/*    */         }
/* 57 */         String pleaseSelect = NmsUtil.GetString("am.webclient.newaction.combobox.selectBusinessHour");
/* 58 */         ArrayList businessHourNameList = new ArrayList();
/* 59 */         businessHourNameList.add(new LabelValueBean(pleaseSelect, ""));
/* 60 */         ArrayList list = DBUtil.getBusinessHourNameAndID();
/* 61 */         int size = list.size();
/* 62 */         for (int i = 0; i < size; i++)
/*    */         {
/* 64 */           Properties prop = (Properties)list.get(i);
/* 65 */           businessHourNameList.add(new LabelValueBean(prop.getProperty("name"), prop.getProperty("id")));
/*    */         }
/* 67 */         request.setAttribute("businessHourNameList", businessHourNameList);
/* 68 */         amForm.setBusinessHourNames(businessHourNameList);
/*    */         
/* 70 */         if (!DBUtil.hasGlobalConfigValue("NewEmailActionAccessed"))
/*    */         {
/* 72 */           AppManagerUtil.insertTimeforMetrack("NewEmailActionAccessed");
/* 73 */           DBUtil.insertIntoGlobalConfig("NewEmailActionAccessed", "true");
/*    */         }
/*    */       } catch (Exception e) {
/* 76 */         e.printStackTrace();
/*    */       }
/* 78 */     } else if ("Tile.usergroups.Conf".equals(tilename)) {
/* 79 */       String userName = request.getRemoteUser();
/* 80 */       if (((!"admin".equalsIgnoreCase(userName)) && ((request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN"))) && (Constants.isPrivilegedUser(request))) || ((EnterpriseUtil.isManagedServer()) && (Constants.ssoEnabled))) {
/* 81 */         return mapping.findForward("accessRestricted");
/*    */       }
/* 83 */     } else if ((".NewManagedServer".equals(tilename)) || (".EditManagedServer".equals(tilename))) {
/* 84 */       ArrayList<String> aListOfMasGroupNames = EnterpriseUtil.getMASGroupNameList();
/* 85 */       if ((aListOfMasGroupNames != null) && (aListOfMasGroupNames.size() > 0)) {
/* 86 */         request.setAttribute("masCreatedMasGroupNames", aListOfMasGroupNames);
/*    */       }
/*    */     }
/* 89 */     else if ("Tile.NewMonitorList".equals(tilename))
/*    */     {
/* 91 */       if (request.getParameter("reqForAdminLayout") != null) {
/* 92 */         request.setAttribute("reqForAdminLayout", request.getParameter("reqForAdminLayout"));
/*    */       }
/*    */     }
/*    */     
/* 96 */     ActionForward forward = mapping.findForward("success");
/* 97 */     return new ActionForward(tilename);
/*    */   }
/*    */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\ShowTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */