/*     */ package com.adventnet.appmanager.servlets;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.AgentUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public class Agent
/*     */   extends HttpServlet
/*     */ {
/*     */   protected void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  33 */     resp.setContentType("text/xml; charset=UTF-8");
/*  34 */     process(req, resp, true);
/*     */   }
/*     */   
/*     */   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
/*     */   {
/*  39 */     resp.setContentType("text/xml; charset=UTF-8");
/*  40 */     process(req, resp, false);
/*     */   }
/*     */   
/*     */ 
/*     */   private void process(HttpServletRequest req, HttpServletResponse resp, boolean isGet)
/*     */   {
/*     */     try
/*     */     {
/*  48 */       PrintWriter out = resp.getWriter();
/*  49 */       resp.setCharacterEncoding("UTF-8");
/*  50 */       String method = req.getParameter("method");
/*     */       
/*  52 */       String responsexml = "";
/*  53 */       String agentid = req.getParameter("agentid");
/*  54 */       AMLog.debug("[ agent request ] [ method : " + method + " ] [ Agent id : " + agentid + " ]");
/*     */       
/*  56 */       if (method != null)
/*     */       {
/*     */ 
/*  59 */         String agentname = req.getParameter("agentname");
/*  60 */         String agentip = req.getParameter("agentip");
/*  61 */         if ((!method.equalsIgnoreCase("updateagent")) && ((agentid == null) || (agentid.equalsIgnoreCase(""))))
/*     */         {
/*  63 */           AMLog.debug("[ No Agent ID]");
/*  64 */           method = "updateagent";
/*     */           
/*  66 */           if (agentid.equalsIgnoreCase("-1"))
/*     */           {
/*  68 */             method = "updateagent";
/*     */           }
/*     */         }
/*     */         
/*  72 */         if ((agentid != null) && (!agentid.equalsIgnoreCase("")) && (!agentid.equalsIgnoreCase("-1")))
/*     */         {
/*  74 */           ResultSet rst = null;
/*     */           
/*     */ 
/*     */           try
/*     */           {
/*  79 */             String query = "select * from AM_RBMAGENTDATA where AGENTID=" + agentid;
/*  80 */             AMConnectionPool.getInstance();rst = AMConnectionPool.executeQueryStmt(query);
/*  81 */             if (rst.next())
/*     */             {
/*  83 */               AMLog.debug("AGENT EXITS -- GOING THE NEXT STEP");
/*     */             }
/*     */             else
/*     */             {
/*  87 */               AMLog.debug("AGENT : " + agentid + " - " + agentname + " - " + agentip + " -- Not exists . So returning AGENT to Stop Polling");
/*  88 */               responsexml = "<APP><AGENT ERROR=\"AGENT_NOT_EXISTS_IN_APPMANAGER\"></AGENT></APP>";
/*     */               
/*  90 */               method = "AGENT_NOT_EXIST";
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */             if (rst != null)
/*     */             {
/*     */               try
/*     */               {
/* 103 */                 rst.close();
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 107 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 112 */             if (agentid == null) {
/*     */               break label414;
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/*  96 */             e.printStackTrace();
/*     */           }
/*     */           finally {
/*  99 */             if (rst != null)
/*     */             {
/*     */               try
/*     */               {
/* 103 */                 rst.close();
/*     */               }
/*     */               catch (Exception e)
/*     */               {
/* 107 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 112 */         if ((!agentid.equalsIgnoreCase("")) && (!agentid.equalsIgnoreCase("-1")) && (!method.equalsIgnoreCase("updateagent")))
/*     */         {
/* 114 */           AgentUtil.getInstance();AgentUtil.updateLastUpdateTime(agentid);
/*     */         }
/*     */         
/*     */         label414:
/* 118 */         if (method.equalsIgnoreCase("updateagent"))
/*     */         {
/* 120 */           AMLog.debug("[ agent request ] [ Update the Agent] ]");
/*     */           
/*     */ 
/*     */ 
/* 124 */           AgentUtil.getInstance();responsexml = AgentUtil.updateAgent(req);
/* 125 */           AMLog.debug("[Update Agent response ] " + responsexml);
/*     */         }
/* 127 */         else if (method.equalsIgnoreCase("checkforupdate"))
/*     */         {
/* 129 */           String agentBuildNo = req.getParameter("agentbuildnumber");
/* 130 */           String checkPPm = req.getParameter("ppmcheck");
/* 131 */           if ((checkPPm != null) && (checkPPm.equalsIgnoreCase("true")) && (agentBuildNo != null) && (!agentBuildNo.equalsIgnoreCase("")))
/*     */           {
/* 133 */             AMLog.debug("[ CHECKFORUPDATE] [ PPM CHECK]");
/* 134 */             AgentUtil.getInstance();int ver = AgentUtil.checkVersion(agentBuildNo);
/* 135 */             if (ver == 1)
/*     */             {
/* 137 */               responsexml = "<APP><AGENT UPDATEAGENT=\"DOWNLOAD\"></AGENT></APP>";
/*     */             }
/* 139 */             else if (ver == 2)
/*     */             {
/* 141 */               responsexml = "<APP><AGENT UPDATEAGENT=\"AGENTVERSION_HIGHER\"></AGENT></APP>";
/*     */             }
/*     */             else
/*     */             {
/* 145 */               AMLog.debug("[ CHECKFORUPDATE] [ No PPM CHECK] [ VERSION NOT MATCHED]");
/* 146 */               responsexml = AgentUtil.getInstance().getConfigurations(agentid, false);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 151 */             AMLog.debug("[ CHECKFORUPDATE] [ No PPM CHECK]");
/* 152 */             responsexml = AgentUtil.getInstance().getConfigurations(agentid, false);
/*     */           }
/*     */         }
/* 155 */         else if (method.equalsIgnoreCase("updatereport"))
/*     */         {
/* 157 */           String reportxml = req.getParameter("reportxml");
/* 158 */           if ((reportxml != null) && (!reportxml.equalsIgnoreCase("")))
/*     */           {
/* 160 */             AgentUtil.getInstance().updateReport(agentid, reportxml);
/*     */           }
/*     */           
/*     */         }
/* 164 */         else if (method.equalsIgnoreCase("getconfig"))
/*     */         {
/*     */ 
/* 167 */           String resids = req.getParameter("resids");
/* 168 */           AMLog.debug("[GET Config Method  from Agent " + agentid + "] Resids : " + resids);
/* 169 */           if ((resids != null) && (!resids.equals("")))
/*     */           {
/* 171 */             responsexml = AgentUtil.getInstance().getConfigurations(agentid, resids);
/*     */           }
/*     */         }
/* 174 */         else if (method.equalsIgnoreCase("shutdownagent"))
/*     */         {
/* 176 */           AgentUtil.getInstance();responsexml = AgentUtil.setAgentDownStatus(agentid);
/*     */ 
/*     */         }
/* 179 */         else if (method.equalsIgnoreCase("downloadagent"))
/*     */         {
/* 181 */           AgentUtil.getInstance();responsexml = AgentUtil.getAgentPPM();
/*     */ 
/*     */         }
/* 184 */         else if ("getSeleniumScript".equals(method))
/*     */         {
/*     */           try
/*     */           {
/* 188 */             String resourceId = req.getParameter("mid");
/* 189 */             boolean isTestPlay = Boolean.parseBoolean(req.getParameter("isTestPlay"));
/* 190 */             responsexml = AgentUtil.getInstance().getSeleniumScript(resourceId);
/*     */           } catch (Exception e) {
/* 192 */             e.printStackTrace();
/* 193 */             responsexml = "Script not found";
/*     */           }
/*     */         }
/*     */       } else {
/* 197 */         String reqMethod = req.getHeader("method");
/* 198 */         if ("imgFileUpload".equals(reqMethod)) {
/* 199 */           AgentUtil.getInstance().saveImageInDisk(req);
/*     */         }
/*     */       }
/* 202 */       out.println(responsexml);
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 207 */       e.printStackTrace();
/*     */ 
/*     */     }
/*     */     catch (Throwable e1)
/*     */     {
/* 212 */       e1.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\Agent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */