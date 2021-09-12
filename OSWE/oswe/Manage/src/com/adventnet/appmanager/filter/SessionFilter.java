/*     */ package com.adventnet.appmanager.filter;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*     */ import com.adventnet.appmanager.server.framework.comm.AMUserResourcesSync;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.RestrictedUsersViewUtil;
/*     */ import com.adventnet.appmanager.util.UserSessionHandler;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ public class SessionFilter implements Filter
/*     */ {
/*  29 */   private ManagedApplication mo = new ManagedApplication();
/*     */   
/*     */   public void init(FilterConfig fc) throws ServletException
/*     */   {
/*  33 */     UserSessionHandler ush = UserSessionHandler.getInstance();
/*     */     
/*     */ 
/*  36 */     UserSessionHandler.accLockoutStatus = ush.getAccLockEnabledStatus();
/*  37 */     System.out.println("Account Lockout Enabled Status" + UserSessionHandler.accLockoutStatus);
/*     */     
/*     */ 
/*  40 */     UserSessionHandler.singleUserStatus = ush.getSingleUserSessionEnabledStatus();
/*  41 */     System.out.println("SingleUser Session Status " + UserSessionHandler.singleUserStatus);
/*     */   }
/*     */   
/*     */   public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws ServletException, IOException
/*     */   {
/*  46 */     HttpServletRequest request = (HttpServletRequest)req;
/*  47 */     HttpServletResponse response = (HttpServletResponse)res;
/*  48 */     String username = request.getRemoteUser();
/*  49 */     final HttpSession session = request.getSession();
/*  50 */     UserSessionHandler us = UserSessionHandler.getInstance();
/*  51 */     boolean isUrlSeq = false;
/*  52 */     if ((request.getParameter("actionmethod") != null) && (request.getParameter("actionmethod").equals("createUrlSeq")))
/*     */     {
/*  54 */       isUrlSeq = true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  64 */     String a = (String)session.getAttribute("sessionValidated");
/*  65 */     System.out.println("Session Validated Attribute Value" + a);
/*     */     
/*  67 */     final String u_id = this.mo.getUserID(request);
/*  68 */     if ((a == null) || (a.equals("")))
/*     */     {
/*     */ 
/*     */ 
/*  72 */       if (us.getAccLockEnabledStatus())
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */         if (u_id != null)
/*     */         {
/*  80 */           String lckStatus = us.getLockStatus(u_id);
/*  81 */           if ((lckStatus != null) && (lckStatus.equals("Locked")))
/*     */           {
/*  83 */             if (!us.unlockAccount(u_id, false))
/*     */             {
/*  85 */               long lockedTime = us.getLockedTime(u_id);
/*  86 */               int lockTimeOutValue = us.getAccLockTimeoutValue();
/*  87 */               long max = lockTimeOutValue * 60000;
/*  88 */               lockedTime += max;
/*  89 */               String lockTimeOut = String.valueOf(lockedTime);
/*  90 */               System.out.println("LOCKTIMEOUT:" + lockTimeOut);
/*  91 */               String urr = "/jsp/UserError.jsp?errType=accountlock&timeOut=" + lockTimeOut;
/*  92 */               response.sendRedirect(urr);
/*  93 */               return;
/*     */             }
/*     */           }
/*     */           
/*  97 */           us.removeFailureCount(u_id);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 104 */       if ((us.getSingleUserSessionEnabledStatus()) && (!"reportadmin".equals(username)) && (!isUrlSeq))
/*     */       {
/* 106 */         if (us.checkSessionForUserId(username) != null)
/*     */         {
/* 108 */           HttpSession userSession = us.checkSessionForUserId(username);
/* 109 */           if ((userSession != null) && (userSession != request.getSession()))
/*     */           {
/*     */             try
/*     */             {
/*     */ 
/* 114 */               Enumeration en = userSession.getAttributeNames();
/* 115 */               String logoutuser = request.getParameter("logoutuser");
/* 116 */               System.out.println("Logoutuser:" + logoutuser);
/* 117 */               System.out.println("query String" + request.getQueryString());
/* 118 */               System.out.println("getParameterMap()" + request.getParameterMap());
/* 119 */               System.out.println("getRequestURI()" + request.getRequestURI());
/* 120 */               if ("true".equals(logoutuser))
/*     */               {
/* 122 */                 us.removeSessionForUser(username);
/* 123 */                 System.out.println("(single user session)Invalidating session for " + userSession);
/* 124 */                 userSession.invalidate();
/* 125 */                 response.sendRedirect("/index.do");
/*     */               }
/*     */               else
/*     */               {
/* 129 */                 String remotehostaddr = (String)userSession.getAttribute("sessionHost");
/*     */                 String remotehostname;
/*     */                 try
/*     */                 {
/* 133 */                   InetAddress ia = InetAddress.getByName(remotehostaddr);
/* 134 */                   remotehostname = ia.getHostName();
/*     */                 }
/*     */                 catch (Exception uhe)
/*     */                 {
/* 138 */                   remotehostname = remotehostaddr;
/*     */                 }
/* 140 */                 String urr = "/jsp/UserError.jsp?errType=duplicatesession&remotehost=" + remotehostname + "&username=" + username;
/* 141 */                 response.sendRedirect(urr);
/*     */               }
/* 143 */               return;
/*     */             }
/*     */             catch (IllegalStateException e)
/*     */             {
/* 147 */               us.removeSessionForUser(username);
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 151 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 158 */           session.setAttribute("sessionHost", request.getRemoteHost());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 167 */       AMLog.debug("[SessionFilter::(doFilter)] setting usersession for user : " + username);
/* 168 */       us.setSessionValueForUserId(username, session);
/*     */     } catch (Exception e1) {
/* 170 */       e1.printStackTrace();
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 176 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run() {
/*     */           try {
/* 180 */             syncUserResourcesFromAdmin(session, u_id);
/*     */           }
/*     */           catch (Exception e) {
/* 183 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */         public void syncUserResourcesFromAdmin(HttpSession session, String u_id)
/*     */         {
/* 189 */           AMLog.debug("[SessionFilter::(doFilter)- (IsUserResTableUpdated)] u_id : " + u_id);
/*     */           
/*     */           try
/*     */           {
/* 193 */             String IsUserResTableUpdated = (String)session.getAttribute(u_id + "_IsUserResTableUpdated");
/* 194 */             String IsUserResTableUpdateStarted = (String)session.getAttribute(u_id + "_IsUserResTableUpdateStarted");
/* 195 */             AMLog.debug("[SessionFilter::(doFilter)- (IsUserResTableUpdated)] retreived for u_id : " + IsUserResTableUpdated);
/*     */             
/* 197 */             if ((!"true".equals(IsUserResTableUpdated)) && (!"true".equals(IsUserResTableUpdateStarted)) && (u_id != null) && (!"".equals(u_id)) && (!"-1".equals(u_id)))
/*     */             {
/* 199 */               session.setAttribute(u_id + "_IsUserResTableUpdateStarted", "true");
/* 200 */               if ((AMAutomaticPortChanger.isSsoEnabled()) && (EnterpriseUtil.isManagedServer))
/*     */               {
/* 202 */                 AMUserResourcesSync aurs = new AMUserResourcesSync(u_id);
/* 203 */                 boolean isRestrictedRole = aurs.isRestrictedRole();
/* 204 */                 if (isRestrictedRole) {
/* 205 */                   aurs.syncUserResourcesFromAdmin();
/* 206 */                   AMLog.debug("[SessionFilter::(doFilter)- (ssoEnabled and isMas part)]ruser(s) : " + u_id);
/*     */                 }
/*     */                 
/*     */ 
/*     */               }
/* 211 */               else if ((Constants.doUserResourceLoginUpdate) && (RestrictedUsersViewUtil.isRestrictedRole(u_id)))
/*     */               {
/*     */ 
/* 214 */                 RestrictedUsersViewUtil.insertAllResourcesOfOwner(u_id, true);
/* 215 */                 AMLog.debug("[SessionFilter::(doFilter)- (else part)]ruser(s) : " + u_id);
/*     */                 
/*     */                 try
/*     */                 {
/* 219 */                   Collection<String> c = new ArrayList();
/* 220 */                   c.add(u_id);
/* 221 */                   synchronized (this)
/*     */                   {
/* 223 */                     RestrictedUsersViewUtil.usersToBeUpdatedInResourcesTable.removeAll(c);
/*     */                   }
/*     */                 }
/*     */                 catch (Exception ex)
/*     */                 {
/* 228 */                   ex.printStackTrace();
/*     */                 }
/*     */               }
/*     */               
/* 232 */               session.setAttribute(u_id + "_IsUserResTableUpdated", "true");
/* 233 */               session.setAttribute(u_id + "_IsUserResTableUpdateStarted", "false");
/* 234 */               AMLog.debug("[SessionFilter::(doFilter)- (IsUserResTableUpdated)] set for u_id : " + u_id);
/*     */             }
/*     */             
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 240 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/*     */       }).start();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 248 */       e.printStackTrace();
/*     */     }
/*     */     
/* 251 */     fc.doFilter(request, response);
/*     */   }
/*     */   
/*     */   public void destroy() {}
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\filter\SessionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */