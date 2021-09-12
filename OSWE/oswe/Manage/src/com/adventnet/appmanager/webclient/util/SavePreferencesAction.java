/*     */ package com.adventnet.appmanager.webclient.util;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.personalize.AMPersonalize;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.nms.util.NmsLogMgr;
/*     */ import com.adventnet.nms.util.PureUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.struts.action.Action;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ 
/*     */ public class SavePreferencesAction extends Action
/*     */ {
/*     */   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  33 */     String selectedskin = request.getParameter("skins");
/*  34 */     String selectedscheme = request.getParameter("scheme");
/*  35 */     String selectedReloadTime = request.getParameter("reloadtime");
/*  36 */     String methodname = request.getParameter("method");
/*  37 */     if (methodname != null) {
/*  38 */       if (methodname.equalsIgnoreCase("edittab")) {
/*  39 */         return editTab(mapping, form, request, response);
/*     */       }
/*  41 */       if (methodname.equalsIgnoreCase("savetab")) {
/*  42 */         return saveTabOrder(mapping, form, request, response);
/*     */       }
/*     */     }
/*  45 */     int selectedReloadInSecs = 300;
/*  46 */     if ((selectedReloadTime != null) && (selectedReloadTime.trim().length() != 0))
/*     */     {
/*  48 */       selectedReloadInSecs = Integer.parseInt(selectedReloadTime) * 60;
/*     */     }
/*     */     else
/*     */     {
/*  52 */       selectedReloadTime = "5";
/*     */     }
/*     */     
/*  55 */     selectedReloadTime = String.valueOf(selectedReloadInSecs);
/*  56 */     HttpSession session = request.getSession();
/*  57 */     session.setAttribute("selectedskin", selectedskin);
/*  58 */     session.setAttribute("selectedscheme", selectedscheme);
/*  59 */     session.setAttribute("customreloadperiod", selectedReloadTime);
/*  60 */     saveState(request);
/*  61 */     ActionForward forward = mapping.findForward("success");
/*  62 */     return forward;
/*     */   }
/*     */   
/*  65 */   public void savealarmviewlength(HttpServletRequest request) { saveState(request); }
/*     */   
/*     */ 
/*     */   protected void saveState(HttpServletRequest request)
/*     */   {
/*     */     try
/*     */     {
/*  72 */       HttpSession session = request.getSession();
/*  73 */       ServletContext context = session.getServletContext();
/*  74 */       String stateFile = context.getInitParameter("webClientStateFile");
/*  75 */       String userdir = PureUtils.rootDir + "users/" + request.getRemoteUser();
/*  76 */       File userdirf = new File(userdir);
/*  77 */       if (!userdirf.exists())
/*     */       {
/*  79 */         userdirf.mkdirs();
/*     */       }
/*  81 */       String path = PureUtils.rootDir + "users/" + request.getRemoteUser() + "/" + stateFile;
/*  82 */       Properties prop = new Properties();
/*  83 */       prop.put("selectedskin", session.getAttribute("selectedskin"));
/*  84 */       prop.put("selectedscheme", session.getAttribute("selectedscheme"));
/*  85 */       prop.put("customreloadperiod", session.getAttribute("customreloadperiod"));
/*  86 */       if (session.getAttribute("alarm_viewlength") != null) {
/*  87 */         prop.put("alarm_viewlength", session.getAttribute("alarm_viewlength"));
/*     */       }
/*  89 */       if (session.getAttribute("sql_viewlength") != null) {
/*  90 */         prop.put("sql_viewlength", session.getAttribute("sql_viewlength"));
/*     */       }
/*  92 */       File f = new File(path);
/*  93 */       if (!f.exists())
/*     */       {
/*  95 */         f.createNewFile();
/*     */       }
/*  97 */       FileOutputStream outStream = new FileOutputStream(f);
/*  98 */       prop.store(outStream, "");
/*  99 */       outStream.close();
/* 100 */       if (Constants.isIt360)
/*     */       {
/* 102 */         sendPreferenceDetailsToOtherModulesOfIT360(request, prop);
/*     */       }
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 107 */       NmsLogMgr.MISCUSER.fail("Exception", ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 113 */   private void sendPreferenceDetailsToOtherModulesOfIT360(HttpServletRequest request, Properties prop) { sendPreferenceDetailsToNetworksModule(request, prop); }
/*     */   
/*     */   private void sendPreferenceDetailsToNetworksModule(HttpServletRequest request, Properties prop) {
/* 116 */     String customReloadPeriod = prop.getProperty("customreloadperiod");
/* 117 */     if ((customReloadPeriod != null) && (!customReloadPeriod.trim().equals("")))
/*     */     {
/* 119 */       String serviceUrl = com.adventnet.appmanager.util.ExtProdUtil.getServiceUrl("OpManager");
/* 120 */       String queryString = "selectedskin=Grey&pageorsession=RefreshPage&SavePreferences=Apply&userName=" + request.getRemoteUser() + "&pagerefreshinterval=" + request.getParameter("reloadtime");
/* 121 */       com.adventnet.appmanager.util.HttpClient.getResponse(serviceUrl + "/SavePreferences.do", "G", queryString);
/*     */     }
/*     */   }
/*     */   
/*     */   private ActionForward saveTabOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 127 */     ArrayList orderedtabList = new ArrayList();
/* 128 */     String userid = null;
/*     */     try {
/* 130 */       int nooftabs = 12;
/* 131 */       if (Constants.sqlManager) {
/* 132 */         nooftabs = 7;
/*     */       }
/*     */       
/* 135 */       HttpSession session = request.getSession();
/*     */       
/* 137 */       if (request.isUserInRole("DEMO")) {
/* 138 */         ActionMessages messages = new ActionMessages();
/* 139 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new org.apache.struts.action.ActionMessage(FormatUtil.getString("am.webclient.historydata.jsalertfordemo.text")));
/* 140 */         saveMessages(request, messages);
/* 141 */         return new ActionForward("/MyPage.do?method=viewDashBoard");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 148 */       Enumeration en = request.getParameterNames();
/* 149 */       while (en.hasMoreElements()) {
/* 150 */         String paramName = (String)en.nextElement();
/* 151 */         System.out.println(paramName + " = " + request.getParameter(paramName) + "<br/>");
/*     */       }
/* 153 */       ManagedApplication mo = new ManagedApplication();
/* 154 */       userid = mo.getUserID(request);
/* 155 */       String consoletype = request.getParameter("consoletype");
/* 156 */       String viewtype = "1";
/* 157 */       String deleteOldOrder = "delete from AM_TABUSERMAPPING where userid=" + userid;
/* 158 */       if ((consoletype != null) && (consoletype.trim().equals("sla"))) {
/* 159 */         nooftabs = 4;
/* 160 */         viewtype = "2";
/*     */       }
/* 162 */       deleteOldOrder = deleteOldOrder + " and VIEWTYPE=" + viewtype;
/* 163 */       AMConnectionPool.executeUpdateStmt(deleteOldOrder);
/*     */       
/* 165 */       for (int orderid = 0; orderid < nooftabs; orderid++) {
/* 166 */         String tabid = request.getParameter("selectedPage" + (orderid + 1));
/* 167 */         String[] tabidtabtypeArr = tabid.split("_");
/* 168 */         if ((tabidtabtypeArr != null) && (tabidtabtypeArr.length >= 2) && (!tabidtabtypeArr[0].equals("-100"))) {
/* 169 */           tabid = tabidtabtypeArr[0];
/*     */           
/* 171 */           String tabtype = tabidtabtypeArr[1];
/*     */           try {
/* 173 */             String insertQuery = "insert into AM_TABUSERMAPPING values(" + tabid + "," + userid + "," + orderid + "," + tabtype + "," + viewtype + ")";
/* 174 */             AMConnectionPool.executeUpdateStmt(insertQuery);
/*     */           } catch (Exception ex) {
/* 176 */             ex.printStackTrace();
/*     */           }
/* 178 */           orderedtabList.add(tabid);
/*     */         }
/*     */       }
/* 181 */       session.setAttribute("orderedtablist", orderedtabList);
/*     */     } catch (Exception exc) {
/* 183 */       exc.printStackTrace();
/*     */     }
/*     */     
/* 186 */     DBUtil.reloadOrderedTabIdList(request.getRemoteUser(), userid);
/*     */     
/*     */ 
/* 189 */     return new ActionForward("/jsp/AMTabEdit.jsp?savedstatus=true");
/*     */   }
/*     */   
/*     */   private ActionForward editTab(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*     */     try {
/* 195 */       response.setHeader("Cache-Control", "no-cache");
/* 196 */       response.setHeader("Cache-Control", "no-store");
/* 197 */       String username = request.getRemoteUser();
/* 198 */       String consoletype = request.getParameter("consoletype");
/* 199 */       List<HashMap<String, String>> selectedtaborder = AMPersonalize.getOrderedTabIdList(request, consoletype);
/* 200 */       int tabSize = 0;
/* 201 */       if ((consoletype != null) && (consoletype.equals("admin")))
/*     */       {
/* 203 */         if (Constants.sqlManager) {
/* 204 */           tabSize = 7;
/*     */         }
/*     */         else {
/* 207 */           tabSize = 12;
/*     */         }
/*     */       }
/* 210 */       else if ((consoletype != null) && (consoletype.equals("sla")))
/*     */       {
/* 212 */         tabSize = 4;
/*     */       }
/* 214 */       for (int i = selectedtaborder.size(); i < tabSize; i++) {
/* 215 */         HashMap notselected = new HashMap();
/* 216 */         notselected.put("TABID", "-100");
/* 217 */         notselected.put("TABTYPE", "-100");
/* 218 */         notselected.put("TABNAME", FormatUtil.getString("am.mypage.notselected.text"));
/* 219 */         selectedtaborder.add(notselected);
/*     */       }
/* 221 */       java.util.Map<String, java.util.LinkedHashMap<String, String>> globaltabs = AMPersonalize.getAllTabs(username, consoletype);
/* 222 */       request.setAttribute("globaltabs", globaltabs);
/* 223 */       request.setAttribute("selectedtaborder", selectedtaborder);
/*     */     } catch (Exception exc) {
/* 225 */       exc.printStackTrace();
/*     */     }
/* 227 */     return new ActionForward("/jsp/AMTabEdit.jsp");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\webclient\util\SavePreferencesAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */