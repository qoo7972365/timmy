/*     */ package com.adventnet.appmanager.struts.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.db.DBQueryUtil;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.fault.SmtpMailer;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionErrors;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.action.ActionMessage;
/*     */ import org.apache.struts.action.ActionMessages;
/*     */ import org.apache.struts.actions.DispatchAction;
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
/*     */ public final class QueryActions
/*     */   extends DispatchAction
/*     */ {
/* 169 */   private ManagedApplication mo = new ManagedApplication();
/* 170 */   private AdminActions aa = new AdminActions();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward createExecQueryAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 178 */     String type = request.getParameter("cancel");
/* 179 */     String returnpath = request.getParameter("returnpath");
/* 180 */     String popupParam = request.getParameter("popup");
/* 181 */     boolean popup = (popupParam != null) && (popupParam.equals("true"));
/* 182 */     ActionMessages messages = new ActionMessages();
/* 183 */     if (type.equals("false"))
/*     */     {
/* 185 */       String displayname = request.getParameter("displayname");
/* 186 */       if (DBQueryUtil.getDBType().equals("mssql"))
/*     */       {
/* 188 */         displayname = displayname.replaceAll("'", "''");
/*     */       }
/*     */       else
/*     */       {
/* 192 */         displayname = displayname.replaceAll("'", "\\\\'");
/*     */       }
/* 194 */       String checkquery = "select ID from AM_ACTIONPROFILE where NAME='" + displayname + "'";
/* 195 */       ArrayList list = this.mo.getRows(checkquery);
/* 196 */       if (list.size() == 0)
/*     */       {
/* 198 */         int id = DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE");
/* 199 */         String insertquery = "insert into AM_ACTIONPROFILE (ID,NAME, TYPE) values(" + id + ",'" + displayname + "',10 ) ";
/* 200 */         this.mo.executeUpdateStmt(insertquery);
/*     */         
/* 202 */         if (id > 0)
/*     */         {
/* 204 */           int appendMessage = 0;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 209 */           if (request.getParameter("appendMessage") != null)
/*     */           {
/* 211 */             appendMessage = 1;
/*     */           }
/*     */           
/* 214 */           if (id < 0) {
/* 215 */             id = 1;
/*     */           }
/* 217 */           PreparedStatement ps = AMConnectionPool.getConnection().prepareStatement("insert into AM_QUERYACTIONS (ID,EMAIL_ACTION_ID,QUERY) values (?,?,?)");
/*     */           try
/*     */           {
/* 220 */             ps.setInt(1, id);
/* 221 */             ps.setInt(2, Integer.parseInt(request.getParameter("sendmail")));
/* 222 */             ps.setString(3, request.getParameter("query"));
/* 223 */             ps.executeUpdate();
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             try
/*     */             {
/* 233 */               if (ps != null)
/*     */               {
/* 235 */                 ps.close();
/*     */               }
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 240 */               ex.printStackTrace();
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 250 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("executequeryaction.create.success"));
/*     */           }
/*     */           catch (Exception exp)
/*     */           {
/* 227 */             exp.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/*     */             try
/*     */             {
/* 233 */               if (ps != null)
/*     */               {
/* 235 */                 ps.close();
/*     */               }
/*     */             }
/*     */             catch (Exception ex)
/*     */             {
/* 240 */               ex.printStackTrace();
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 252 */           saveMessages(request, messages);
/* 253 */           if (popup)
/*     */           {
/* 255 */             this.aa.associateActions(request);
/* 256 */             messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("emailaction.createandassociate.success"));
/*     */             
/* 258 */             return new ActionForward("/jsp/ThresholdCreationForwarder.jsp");
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 265 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("executequeryaction.create.alreadyexists", request.getParameter("displayname")));
/* 266 */         saveMessages(request, messages);
/* 267 */         if (popup)
/*     */         {
/* 269 */           return new ActionForward("/jsp/ExecQueryActionForm.jsp");
/*     */         }
/* 271 */         return new ActionForward("/queryAction.do?method=showExecQueryAction&save='true'");
/*     */       }
/*     */       
/* 274 */       if (returnpath != null)
/*     */       {
/* 276 */         request.setAttribute("showAdvancedOptions", "true");
/* 277 */         return new ActionForward(returnpath);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 286 */       return mapping.findForward("success");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 291 */     if (returnpath != null) {
/* 292 */       return new ActionForward(returnpath);
/*     */     }
/* 294 */     return mapping.findForward("success");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward showExecQueryAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 308 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 309 */     ResultSet set = null;
/*     */     
/* 311 */     AMActionForm amform = (AMActionForm)form;
/* 312 */     ActionMessages messages = new ActionMessages();
/* 313 */     ArrayList rows = new ArrayList();
/* 314 */     String actionID = request.getParameter("actionID");
/*     */     
/* 316 */     String query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1";
/* 317 */     if (EnterpriseUtil.isAdminServer()) {
/* 318 */       query = "SELECT AM_ACTIONPROFILE.ID,AM_ACTIONPROFILE.NAME,TOADDRESS FROM AM_ACTIONPROFILE,AM_EMAILACTION where AM_EMAILACTION.ID=AM_ACTIONPROFILE.ID AND AM_ACTIONPROFILE.TYPE=1 and AM_ACTIONPROFILE.NAME !='ADMINEMAIL'";
/*     */     }
/*     */     try
/*     */     {
/* 322 */       set = AMConnectionPool.executeQueryStmt(query);
/* 323 */       AMLog.debug("Execute query Action : " + query);
/* 324 */       while (set.next())
/*     */       {
/* 326 */         String labelvalue = set.getString(2) + ":(" + set.getString(3) + ")";
/* 327 */         Properties dataProps = new Properties();
/* 328 */         dataProps.setProperty("label", labelvalue);
/* 329 */         dataProps.setProperty("value", String.valueOf(set.getInt(1)));
/* 330 */         rows.add(dataProps);
/*     */       }
/* 332 */       AMConnectionPool.closeStatement(set);
/*     */       
/*     */ 
/* 335 */       ((AMActionForm)form).setMaillist(rows);
/*     */ 
/*     */     }
/*     */     catch (Exception exp)
/*     */     {
/* 340 */       AMLog.fatal("Execute Query Action Exception ", exp);
/* 341 */       exp.printStackTrace();
/* 342 */       throw new Exception(exp);
/*     */     }
/*     */     finally {
/* 345 */       AMConnectionPool.closeStatement(set);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 354 */     if (actionID != null) {
/* 355 */       query = "select AM_QUERYACTIONS.ID,NAME,EMAIL_ACTION_ID,QUERY from AM_QUERYACTIONS,AM_ACTIONPROFILE where AM_QUERYACTIONS.ID =AM_ACTIONPROFILE.ID and  AM_ACTIONPROFILE.ID=" + actionID;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 361 */       rows = new ArrayList();
/* 362 */       rows = this.mo.getRows(query);
/* 363 */       if (rows != null)
/*     */       {
/* 365 */         ArrayList row = (ArrayList)rows.get(0);
/*     */         
/*     */ 
/* 368 */         amform.setId(Integer.parseInt((String)row.get(0)));
/* 369 */         amform.setDisplayname((String)row.get(1));
/* 370 */         amform.setSendmail((String)row.get(2));
/* 371 */         amform.setQuery((String)row.get(3));
/* 372 */         amform.setMethod("editExecQueryAction");
/*     */       }
/*     */     }
/*     */     
/* 376 */     return mapping.findForward("execQueryAction");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward sendActionDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 385 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 386 */     AMActionForm am = (AMActionForm)form;
/* 387 */     ActionMessages messages = new ActionMessages();
/* 388 */     ActionErrors errors = new ActionErrors();
/* 389 */     response.setContentType("text/html; charset=UTF-8");
/* 390 */     ResultSet rs = null;
/* 391 */     String temp = null;
/* 392 */     String sname = null;
/* 393 */     String emailid = null;
/* 394 */     String host = InetAddress.getLocalHost().getHostName();
/* 395 */     String message = "Java";
/* 396 */     String port = System.getProperty("webserver.port");
/* 397 */     ResultSet rst = null;
/*     */     try
/*     */     {
/* 400 */       if (request.getParameter("message") != null) {
/* 401 */         message = request.getParameter("message");
/*     */       }
/* 403 */       emailid = request.getParameter("emailid");
/* 404 */       int i = 0;
/* 405 */       String actionquery = null;
/* 406 */       if (emailid != null)
/*     */       {
/* 408 */         String q1 = "select HOST from AM_MAILSETTINGS";
/* 409 */         rst = AMConnectionPool.executeQueryStmt(q1);
/* 410 */         String returnVal = null;
/* 411 */         String sentactid = null;
/* 412 */         if (rst.next())
/*     */         {
/* 414 */           String portFilled = FormatUtil.getString("am.action.queryaction.message", new String[] { message });
/* 415 */           SmtpMailer mailer = new SmtpMailer(emailid, emailid, "", FormatUtil.getString("am.action.queryaction.testemail.message", new String[] { message }));
/* 416 */           returnVal = mailer.sendMessage("", null, portFilled);
/* 417 */           if (returnVal == null)
/*     */           {
/* 419 */             sname = request.getParameter("emailname");
/* 420 */             sname = sname + "_Action";
/* 421 */             actionquery = "INSERT INTO AM_ACTIONPROFILE (ID,NAME,TYPE) VALUES(" + DBQueryUtil.getIncrementedID("ID", "AM_ACTIONPROFILE") + ",'" + sname + "','1')";
/* 422 */             AMConnectionPool.executeUpdateStmt(actionquery);
/* 423 */             String getid = "SELECT ID FROM AM_ACTIONPROFILE WHERE NAME= '" + sname + "' AND TYPE = '1'";
/* 424 */             rs = AMConnectionPool.executeQueryStmt(getid);
/* 425 */             String actid = "";
/* 426 */             if (rs.next()) {
/* 427 */               actid = rs.getString("ID");
/*     */             }
/*     */             else
/*     */             {
/* 431 */               ((AMActionForm)form).setMethod("createExecQueryAction");
/*     */             }
/*     */             
/* 434 */             String subject = FormatUtil.getString("am.webclient.managermail.bsm.alertfrommessage.text", new String[] { OEMUtil.getOEMString("product.name") });
/* 435 */             String act2 = "insert into AM_EMAILACTION (ID, FROMADDRESS, TOADDRESS, SUBJECT, MESSAGE,SMTPPORT) values (" + actid + ",'" + emailid + "','" + emailid + "','" + subject + "','" + FormatUtil.getString("am.webclient.mail.default.message.text", new String[] { OEMUtil.getOEMString("product.name") }) + "',25)";
/* 436 */             AMConnectionPool.executeUpdateStmt(act2);
/* 437 */             Properties pro = new Properties();
/* 438 */             ArrayList rows = new ArrayList();
/* 439 */             pro.setProperty("label", sname);
/* 440 */             pro.setProperty("value", actid);
/* 441 */             rows.add(pro);
/*     */             
/* 443 */             am.setSendmail(actid);
/* 444 */             sentactid = returnVal + "," + actid;
/* 445 */             PrintWriter pw = response.getWriter();
/* 446 */             pw.print(sentactid);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/* 452 */             returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.mailmessage.text");
/* 453 */             sentactid = returnVal + ",0";
/* 454 */             PrintWriter pw = response.getWriter();
/* 455 */             pw.print(sentactid);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 460 */           returnVal = FormatUtil.getString("am.webclient.schedulereport.showwschedule.smtpmailmessage.text");
/*     */           
/* 462 */           sentactid = returnVal + ",0";
/* 463 */           PrintWriter pw = response.getWriter();
/* 464 */           pw.print(sentactid);
/*     */         }
/* 466 */         rst.close();
/*     */       }
/*     */     }
/*     */     catch (Exception es)
/*     */     {
/* 471 */       es.printStackTrace();
/*     */     }
/*     */     finally {
/* 474 */       AMConnectionPool.closeStatement(rst);
/* 475 */       AMConnectionPool.closeStatement(rs);
/*     */     }
/* 477 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward editExecQueryAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 488 */     ActionMessages messages = new ActionMessages();
/* 489 */     AMActionForm amform = (AMActionForm)form;
/* 490 */     String update = request.getParameter("update");
/* 491 */     String displayname = request.getParameter("displayname");
/* 492 */     if (DBQueryUtil.getDBType().equals("mssql"))
/*     */     {
/* 494 */       displayname = displayname.replaceAll("'", "''");
/*     */     }
/*     */     else
/*     */     {
/* 498 */       displayname = displayname.replaceAll("'", "\\\\'");
/*     */     }
/* 500 */     String checkquery = "select ID from AM_ACTIONPROFILE where NAME='" + displayname + "' and ID!=" + amform.getId();
/* 501 */     ArrayList list = this.mo.getRows(checkquery);
/* 502 */     if (list.size() == 0)
/*     */     {
/* 504 */       String updateactionquery = "update AM_ACTIONPROFILE set NAME='" + displayname + "' where ID = " + amform.getId();
/* 505 */       this.mo.executeUpdateStmt(updateactionquery);
/*     */       
/* 507 */       PreparedStatement ps = null;
/*     */       try
/*     */       {
/* 510 */         if (update == null) {
/* 511 */           int appendMessage = 0;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 516 */           if (request.getParameter("appendMessage") != null)
/*     */           {
/* 518 */             appendMessage = 1;
/*     */           }
/* 520 */           ps = AMConnectionPool.getConnection().prepareStatement("update AM_QUERYACTIONS set QUERY=?, EMAIL_ACTION_ID=? where ID =?");
/*     */           try
/*     */           {
/* 523 */             ps.setString(1, amform.getQuery());
/* 524 */             ps.setInt(2, Integer.parseInt(amform.getSendmail()));
/* 525 */             ps.setInt(3, amform.getId());
/* 526 */             ps.executeUpdate();
/*     */           }
/*     */           catch (Exception exp)
/*     */           {
/* 530 */             exp.printStackTrace();
/*     */           }
/*     */         } else {
/* 533 */           ps = AMConnectionPool.getConnection().prepareStatement("update AM_QUERYACTIONS set QUERY=?,EMAIL_ACTION_ID=?  where ID =?");
/*     */           
/*     */           try
/*     */           {
/* 537 */             ps.setString(1, amform.getQuery());
/* 538 */             ps.setInt(2, Integer.parseInt(amform.getSendmail()));
/* 539 */             ps.setInt(3, amform.getId());
/* 540 */             ps.executeUpdate();
/*     */           }
/*     */           catch (Exception exp)
/*     */           {
/* 544 */             exp.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 556 */           if (ps != null)
/*     */           {
/* 558 */             ps.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 563 */           ex.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 574 */         messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("executequeryaction.edit.success"));
/*     */       }
/*     */       catch (Exception exp)
/*     */       {
/* 550 */         exp.printStackTrace();
/*     */       }
/*     */       finally
/*     */       {
/*     */         try
/*     */         {
/* 556 */           if (ps != null)
/*     */           {
/* 558 */             ps.close();
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 563 */           ex.printStackTrace();
/*     */         }
/*     */       }
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
/* 576 */       saveMessages(request, messages);
/*     */     }
/*     */     else
/*     */     {
/* 580 */       messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("executequeryaction.create.alreadyexists", request.getParameter("displayname")));
/* 581 */       saveMessages(request, messages);
/* 582 */       if (update == null) {
/* 583 */         return new ActionForward("/queryAction.do?method=showExecQueryAction&actionID=" + amform.getId() + "&haid=null");
/*     */       }
/* 585 */       return new ActionForward("/showActionProfiles.do?method=getActionDetails&actionid=" + amform.getId());
/*     */     }
/*     */     
/* 588 */     if (update == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 596 */       return mapping.findForward("success");
/*     */     }
/*     */     
/* 599 */     return new ActionForward("/showActionProfiles.do?method=getActionDetails&actionid=" + amform.getId());
/*     */   }
/*     */   
/*     */ 
/*     */   public ActionForward deleteExecQueryAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws Exception
/*     */   {
/* 606 */     String[] applications = request.getParameterValues("querycheckbox");
/* 607 */     String messg = "";
/* 608 */     if (applications != null)
/*     */     {
/* 610 */       for (int i = 0; i < applications.length; i++)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 619 */         FaultUtil.deleteAction(applications[i]);
/*     */       }
/* 621 */       messg = "executequeryaction.delete.success";
/*     */     }
/* 623 */     ActionMessages messages = new ActionMessages();
/* 624 */     messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messg));
/* 625 */     saveMessages(request, messages);
/* 626 */     return mapping.findForward("success");
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\struts\actions\QueryActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */