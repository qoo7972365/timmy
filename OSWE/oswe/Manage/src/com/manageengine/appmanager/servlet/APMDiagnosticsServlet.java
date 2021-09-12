/*     */ package com.manageengine.appmanager.servlet;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.tools.prevalent.Wield;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.manageengine.appmanager.diagnostics.constants.DiagnosticsConstants;
/*     */ import com.manageengine.appmanager.diagnostics.util.APMDiagnosticsFaultHandler;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
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
/*     */ public class APMDiagnosticsServlet
/*     */   extends HttpServlet
/*     */   implements DiagnosticsConstants
/*     */ {
/*     */   public void doPost(HttpServletRequest req, HttpServletResponse res)
/*     */     throws ServletException, IOException
/*     */   {
/*  42 */     doGet(req, res);
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  47 */     String req = request.getParameter("req");
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
/*  60 */     if ("GET_ALERTS_LIST".equalsIgnoreCase(req))
/*     */     {
/*  62 */       getAlertsList(request, response);
/*     */     }
/*  64 */     else if ("LICENSEVALIDATION".equalsIgnoreCase(req))
/*     */     {
/*  66 */       sendLicenseExpiryDiagProps(request, response);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void getAlertsList(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  84 */     Gson gson = new Gson();
/*  85 */     String jsonString = gson.toJson(APMDiagnosticsFaultHandler.getInstance().getAlertsList(request), new TypeToken() {}.getType());
/*  86 */     PrintWriter out = null;
/*     */     try
/*     */     {
/*  89 */       out = response.getWriter();
/*  90 */       out.println(jsonString);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/*  98 */       if (out != null)
/*     */       {
/* 100 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void sendAsJsonString(Object obj, Type type, HttpServletResponse response)
/*     */   {
/* 109 */     if (obj != null)
/*     */     {
/* 111 */       Gson gson = new Gson();
/* 112 */       String jsonString = gson.toJson(obj, type);
/*     */       
/* 114 */       Properties result = new Properties();
/* 115 */       result.put("Result", jsonString);
/*     */       try {
/* 117 */         result.store(response.getOutputStream(), "SERVER_RESPONSE");
/*     */       } catch (IOException e) {
/* 119 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void sendLicenseExpiryDiagProps(HttpServletRequest request, HttpServletResponse response)
/*     */   {
/* 133 */     Wield wield = Wield.getInstance();
/* 134 */     String usertype = wield.getUserType();
/* 135 */     String description = null;
/* 136 */     String message = null;
/* 137 */     String productName = OEMUtil.getOEMString("product.name");
/* 138 */     int state = 5;
/*     */     
/*     */ 
/* 141 */     if ((wield.getEvaluationDays() <= 0L) && (usertype != null) && (usertype.equals("R")) && (!"never".equalsIgnoreCase(wield.getEvaluationExpiryDate())))
/*     */     {
/* 143 */       state = 1;
/* 144 */       message = FormatUtil.getString("am.it360.licensing.register.expiry.msg");
/* 145 */       description = FormatUtil.getString("am.it360.licensing.register.expiry.dsc");
/*     */ 
/*     */     }
/* 148 */     else if (((wield.getEvaluationDays() > 15L) || ("never".equalsIgnoreCase(wield.getEvaluationExpiryDate()))) && (usertype != null) && ((usertype.equals("R")) || (usertype.equals("T"))))
/*     */     {
/* 150 */       state = 5;
/* 151 */       message = FormatUtil.getString("am.licensing.register.msg");
/* 152 */       description = FormatUtil.getString("am.licensing.register.dsc");
/*     */     }
/* 154 */     else if ((usertype != null) && ((usertype.equalsIgnoreCase("T")) || (usertype.equalsIgnoreCase("R"))) && (wield.getEvaluationDays() <= 7L) && (wield.getEvaluationDays() > 0L))
/*     */     {
/* 156 */       state = 1;
/* 157 */       String descriptionTrial = "";
/* 158 */       String descriptionProduct = "";
/* 159 */       if (usertype.equalsIgnoreCase("T"))
/*     */       {
/*     */ 
/* 162 */         message = FormatUtil.getString("am.it360.licensing.trial.going.expiry.msg", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/*     */         
/* 164 */         descriptionTrial = FormatUtil.getString("am.it360.licensing.trial.going.expiry.dsc", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/* 165 */         descriptionProduct = FormatUtil.getString("am.it360.licensing.contact.trial.dsc");
/*     */       }
/* 167 */       else if (usertype.equalsIgnoreCase("R"))
/*     */       {
/*     */ 
/* 170 */         message = FormatUtil.getString("am.it360.licensing.register.going.expiry.msg", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/*     */         
/* 172 */         descriptionTrial = FormatUtil.getString("am.it360.licensing.register.going.expiry.dsc", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/* 173 */         descriptionProduct = FormatUtil.getString("am.it360.licensing.contact.register.dsc");
/*     */       }
/*     */       
/*     */ 
/* 177 */       description = descriptionTrial + " " + descriptionProduct;
/*     */     }
/* 179 */     else if ((usertype != null) && ((usertype.equalsIgnoreCase("T")) || (usertype.equalsIgnoreCase("R"))) && (wield.getEvaluationDays() <= 15L) && (wield.getEvaluationDays() > 7L))
/*     */     {
/* 181 */       state = 4;
/*     */       
/* 183 */       if (usertype.equalsIgnoreCase("T"))
/*     */       {
/* 185 */         message = FormatUtil.getString("am.it360.licensing.trial.going.expiry.msg", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/* 186 */         description = FormatUtil.getString("am.it360.licensing.trial.going.expiry.dsc", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/*     */       }
/* 188 */       if (usertype.equalsIgnoreCase("R"))
/*     */       {
/* 190 */         message = FormatUtil.getString("am.it360.licensing.register.going.expiry.msg", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/* 191 */         description = FormatUtil.getString("am.it360.licensing.register.going.expiry.dsc", new Object[] { Long.valueOf(wield.getEvaluationDays()), getExpiryDateString(request) });
/*     */       }
/*     */     }
/* 194 */     else if ((wield.getEvaluationDays() <= 0L) && (usertype != null) && (usertype.equalsIgnoreCase("F")))
/*     */     {
/* 196 */       state = 1;
/* 197 */       if (productName.equalsIgnoreCase("IT360"))
/*     */       {
/*     */ 
/* 200 */         message = FormatUtil.getString("am.it360.licensing.trial.expiry.msg");
/*     */         
/* 202 */         description = FormatUtil.getString("am.it360.licensing.trial.expiry.dsc");
/*     */ 
/*     */ 
/*     */       }
/* 206 */       else if (description == null)
/*     */       {
/* 208 */         message = FormatUtil.getString("am.licensing.trial.expiry.msg");
/*     */         
/* 210 */         description = FormatUtil.getString("am.licensing.trial.expiry.dsc");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 215 */     if (message != null)
/*     */     {
/* 217 */       AMLog.debug("[IT360 License Expiry Alert] " + message);
/*     */     }
/*     */     
/*     */ 
/* 221 */     Properties diagData = new Properties();
/* 222 */     diagData.setProperty("DIAGONISTIC_NAME", request.getParameter("DIAGONISTIC_NAME"));
/* 223 */     diagData.setProperty("MESSAGE", message);
/* 224 */     diagData.setProperty("DESCRIPTION", description);
/* 225 */     diagData.setProperty("STATE", Integer.toString(state));
/* 226 */     diagData.setProperty("CREATED_TIME", Long.toString(System.currentTimeMillis()));
/*     */     
/* 228 */     sendAsJsonString(diagData, new TypeToken() {}.getType(), response);
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
/*     */   private String getExpiryDateString(HttpServletRequest request)
/*     */   {
/* 312 */     String eDate = "Ever";
/* 313 */     Wield wield = Wield.getInstance();
/* 314 */     Date dt = null;
/* 315 */     String expiryDate = wield.getEvaluationExpiryDate();
/* 316 */     if ((!"never".equalsIgnoreCase(expiryDate)) && (expiryDate != null))
/*     */     {
/* 318 */       StringTokenizer stoken = new StringTokenizer(expiryDate, " ");
/* 319 */       String[] l = new String[3];
/* 320 */       int i = 0;
/* 321 */       while (stoken.hasMoreTokens())
/*     */       {
/* 323 */         l[i] = stoken.nextToken().trim();
/* 324 */         i++;
/*     */       }
/* 326 */       String dateStr = l[0] + "/" + l[1] + "/" + l[2];
/* 327 */       dt = new Date(dateStr);
/*     */     }
/* 329 */     if (dt != null)
/*     */     {
/* 331 */       SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy", request.getLocale());
/* 332 */       eDate = sdf.format(dt);
/*     */     }
/* 334 */     return eDate;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\manageengine\appmanager\servlet\APMDiagnosticsServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */