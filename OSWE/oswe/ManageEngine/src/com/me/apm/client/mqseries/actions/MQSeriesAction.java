/*     */ package com.me.apm.client.mqseries.actions;
/*     */ 
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.server.framework.NewMonitorUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.ibm.mq.MQException;
/*     */ import com.ibm.mq.pcf.PCFConstants;
/*     */ import com.ibm.mq.pcf.PCFMessage;
/*     */ import com.ibm.mq.pcf.PCFMessageAgent;
/*     */ import com.lowagie.text.Chunk;
/*     */ import com.lowagie.text.Document;
/*     */ import com.lowagie.text.Font;
/*     */ import com.lowagie.text.Image;
/*     */ import com.lowagie.text.Paragraph;
/*     */ import com.lowagie.text.Phrase;
/*     */ import com.lowagie.text.pdf.PdfPCell;
/*     */ import com.lowagie.text.pdf.PdfPTable;
/*     */ import com.lowagie.text.pdf.PdfWriter;
/*     */ import com.me.apm.server.mqseries.connection.MQConnectionHandler;
/*     */ import com.me.apm.server.mqseries.event.EventManager;
/*     */ import com.me.apm.server.mqseries.event.EventUtil;
/*     */ import com.me.apm.server.mqseries.event.PdfPageEventListener;
/*     */ import com.me.apm.server.mqseries.util.MQUtil;
/*     */ import java.awt.Color;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.struts.action.ActionForm;
/*     */ import org.apache.struts.action.ActionForward;
/*     */ import org.apache.struts.action.ActionMapping;
/*     */ import org.apache.struts.actions.DispatchAction;
/*     */ import org.json.JSONArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MQSeriesAction
/*     */   extends DispatchAction
/*     */ {
/*     */   public ActionForward getEvents(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*  70 */     request.setAttribute("eventsList", getEventList(request));
/*  71 */     response.setContentType("text/html;charset=UTF-8");
/*  72 */     RequestDispatcher rd = request.getRequestDispatcher("/jsp/MQSeriesEventResults.jsp");
/*  73 */     rd.include(request, response);
/*  74 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ActionForward exportEvents(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/*     */     try
/*     */     {
/*  89 */       String moname = nZ(request.getParameter("moname"), "-1");
/*  90 */       String reportName = "WebSphereMQ_EventsReport_" + moname + "_" + new java.sql.Date(System.currentTimeMillis());
/*     */       
/*  92 */       List<HashMap<String, String>> eventsList = getEventList(request);
/*     */       
/*  94 */       response.setContentType("application/pdf;charset=UTF-8");
/*  95 */       response.setHeader("Content-Disposition", "attachment;filename=" + reportName + ".pdf");
/*     */       
/*  97 */       Document document = new Document();
/*  98 */       PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
/*     */       
/* 100 */       PdfPageEventListener footer = new PdfPageEventListener();
/* 101 */       writer.setPageEvent(footer);
/*     */       
/* 103 */       document.open();
/* 104 */       PdfPTable table = null;
/* 105 */       Font ft = new Font(MQUtil.getBaseFont());
/*     */       
/*     */ 
/* 108 */       String apm_logo = System.getProperty("user.dir") + File.separator + "images" + File.separator + OEMUtil.getOEMString("am.header.logo");
/* 109 */       Image image = Image.getInstance(apm_logo);
/* 110 */       document.add(image);
/*     */       
/* 112 */       Paragraph genTime = new Paragraph(FormatUtil.getString("am.mqseries.event.generatedon") + MQUtil.formatDateTime(new java.util.Date()), ft);
/* 113 */       genTime.setAlignment(2);
/* 114 */       document.add(genTime);
/*     */       
/* 116 */       Phrase newLine = new Phrase();
/* 117 */       newLine.add(Chunk.NEWLINE);
/* 118 */       PdfPTable newLineTbl = new PdfPTable(1);
/* 119 */       newLineTbl.setWidths(new int[] { 50 });
/* 120 */       newLineTbl.setTotalWidth(527.0F);
/* 121 */       newLineTbl.getDefaultCell().setBorder(1);
/* 122 */       newLineTbl.addCell(newLine);
/* 123 */       newLineTbl.writeSelectedRows(0, -1, 34.0F, 740.0F, writer.getDirectContent());
/*     */       
/* 125 */       document.add(newLine);
/* 126 */       document.add(new Paragraph(FormatUtil.getString("am.mqseries.event.event.report") + moname, ft));
/* 127 */       document.add(newLine);
/*     */       
/* 129 */       int index = 1;
/* 130 */       for (HashMap<String, String> event : eventsList) {
/* 131 */         table = new PdfPTable(2);
/* 132 */         table.setWidthPercentage(90.0F);
/* 133 */         table.setWidths(new int[] { 15, 35 });
/* 134 */         PdfPCell cell1 = new PdfPCell(new Paragraph("#" + index));
/* 135 */         cell1.setColspan(2);
/* 136 */         cell1.setBackgroundColor(Color.GRAY);
/* 137 */         cell1.setPadding(5.0F);
/* 138 */         table.addCell(cell1);
/* 139 */         for (Map.Entry entry : event.entrySet()) {
/* 140 */           table.addCell(new PdfPCell(new Paragraph(FormatUtil.getString(entry.getKey().toString()), ft)));
/* 141 */           table.addCell(new PdfPCell(new Paragraph(entry.getValue().toString(), ft)));
/*     */         }
/* 143 */         document.add(table);
/* 144 */         document.add(newLine);
/* 145 */         index++;
/*     */       }
/* 147 */       document.close();
/*     */     } catch (Exception e) {
/* 149 */       AMLog.debug("MQSeriesAction :: exportEvents :: error occurred while exporting events :: " + e.getMessage());
/* 150 */       e.printStackTrace();
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<HashMap<String, String>> getEventList(HttpServletRequest request)
/*     */   {
/* 161 */     MQConnectionHandler connHandler = null;
/* 162 */     List<HashMap<String, String>> eventsList = new ArrayList();
/*     */     try {
/* 164 */       String resourceId = nZ(request.getParameter("resourceid"), "-1");
/* 165 */       String queueName = nZ(request.getParameter("queueName"), "SYSTEM.ADMIN.CONFIG.EVENT");
/* 166 */       String fromDate = nZ(request.getParameter("fromDate"), "");
/* 167 */       String toDate = nZ(request.getParameter("toDate"), "");
/* 168 */       String searchString = nZ(request.getParameter("searchString"), "");
/* 169 */       String reasonCodes = nZ(request.getParameter("reasonCodes"), "");
/*     */       
/* 171 */       connHandler = new MQConnectionHandler(NewMonitorUtil.getArgsasProps("WebsphereMQ", resourceId));
/* 172 */       EventManager eveMgr = new EventManager(connHandler.getQmanagerConnection(), connHandler.getPCFConnection());
/* 173 */       eventsList = eveMgr.getEvents(queueName, fromDate, toDate, searchString, reasonCodes);
/*     */     } catch (Exception e) {
/* 175 */       AMLog.debug("MQSeriesAction :: getEventList :: Error Occued while retrieving events :" + e.getMessage());
/* 176 */       e.printStackTrace();
/*     */     } finally {
/* 178 */       if (connHandler != null) {
/* 179 */         connHandler.returnConnection();
/*     */       }
/*     */     }
/* 182 */     return eventsList;
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
/*     */   public ActionForward getEventStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 196 */     MQConnectionHandler connHandler = null;
/* 197 */     JSONArray eventsStatus = new JSONArray();
/*     */     try {
/* 199 */       String resourceId = nZ(request.getParameter("resourceid"), "-1");
/* 200 */       connHandler = new MQConnectionHandler(NewMonitorUtil.getArgsasProps("WebsphereMQ", resourceId));
/* 201 */       eventsStatus = EventUtil.getMQEventStats(connHandler.getPCFConnection());
/*     */     } catch (Exception e) {
/* 203 */       e.printStackTrace();
/*     */     } finally {
/* 205 */       if (connHandler != null) {
/* 206 */         connHandler.returnConnection();
/*     */       }
/*     */     }
/* 209 */     response.setContentType("application/json;charset=UTF-8");
/* 210 */     PrintWriter out = response.getWriter();
/* 211 */     out.print(eventsStatus.toString());
/* 212 */     out.flush();
/* 213 */     return null;
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
/*     */   public ActionForward startService(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 227 */     MQConnectionHandler connHandler = null;
/* 228 */     String success = "Failure";
/* 229 */     String message = "";
/*     */     try {
/* 231 */       String resourceId = nZ(request.getParameter("resourceId"), "-1");
/* 232 */       String serviceName = nZ(request.getParameter("serviceName"), "-1");
/*     */       
/* 234 */       connHandler = new MQConnectionHandler(NewMonitorUtil.getArgsasProps("WebsphereMQ", resourceId));
/*     */       
/* 236 */       PCFMessageAgent agent = connHandler.getPCFConnection();
/* 237 */       PCFMessage[] responses = null;
/*     */       PCFMessage[] arr$;
/* 239 */       int len$; int i$; try { PCFMessage pcfRequest = new PCFMessage(155);
/* 240 */         pcfRequest.addParameter(2077, serviceName);
/*     */         
/* 242 */         responses = agent.send(pcfRequest);
/* 243 */         for (PCFMessage pcfResponse : responses) {
/* 244 */           if (pcfResponse.getCompCode() == 0) {
/* 245 */             message = FormatUtil.getString("am.mqseries.service.start.success");
/*     */           } else {
/* 247 */             message = PCFConstants.lookupReasonCode(responses[0].getReason());
/*     */           }
/*     */         }
/* 250 */         success = "Success";
/*     */       } catch (MQException mqe) {
/* 252 */         responses = (PCFMessage[])mqe.exceptionSource;
/* 253 */         message = FormatUtil.getString("am.mqseries.service.error") + " : " + PCFConstants.lookupReasonCode(mqe.reasonCode) + ", " + FormatUtil.getString("am.mqseries.service.reason") + " : ";
/* 254 */         arr$ = responses;len$ = arr$.length;i$ = 0; } for (; i$ < len$; i$++) { PCFMessage pcfResponse = arr$[i$];
/* 255 */         if (pcfResponse.getCompCode() == 1) {
/* 256 */           switch (pcfResponse.getReason()) {
/*     */           case 3262: 
/* 258 */             message = message + FormatUtil.getString("am.mqseries.service.start.param.missing");
/* 259 */             break;
/*     */           case 3251: 
/* 261 */             message = message + FormatUtil.getString("am.mqseries.service.running");
/* 262 */             break;
/*     */           default: 
/* 264 */             message = message + PCFConstants.lookupReasonCode(responses[0].getReason());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 271 */       e.printStackTrace();
/*     */     } finally {
/* 273 */       if (connHandler != null) {
/* 274 */         connHandler.returnConnection();
/*     */       }
/*     */     }
/* 277 */     response.setContentType("text/plain;charset=UTF-8");
/* 278 */     PrintWriter out = response.getWriter();
/* 279 */     out.print(success + "|" + message);
/* 280 */     out.flush();
/* 281 */     return null;
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
/*     */   public ActionForward stopService(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
/*     */   {
/* 295 */     MQConnectionHandler connHandler = null;
/* 296 */     String success = "Failure";
/* 297 */     String message = "";
/*     */     try {
/* 299 */       String resourceId = nZ(request.getParameter("resourceId"), "-1");
/* 300 */       String serviceName = nZ(request.getParameter("serviceName"), "-1");
/*     */       
/* 302 */       connHandler = new MQConnectionHandler(NewMonitorUtil.getArgsasProps("WebsphereMQ", resourceId));
/*     */       
/* 304 */       PCFMessageAgent agent = connHandler.getPCFConnection();
/* 305 */       PCFMessage[] responses = null;
/*     */       PCFMessage[] arr$;
/* 307 */       int len$; int i$; try { PCFMessage pcfRequest = new PCFMessage(156);
/* 308 */         pcfRequest.addParameter(2077, serviceName);
/*     */         
/* 310 */         responses = agent.send(pcfRequest);
/* 311 */         for (PCFMessage pcfResponse : responses) {
/* 312 */           if (pcfResponse.getCompCode() == 0) {
/* 313 */             message = FormatUtil.getString("am.mqseries.service.stop.success");
/*     */           } else {
/* 315 */             message = PCFConstants.lookupReasonCode(responses[0].getReason());
/*     */           }
/*     */         }
/* 318 */         success = "Success";
/*     */       } catch (MQException mqe) {
/* 320 */         responses = (PCFMessage[])mqe.exceptionSource;
/* 321 */         message = FormatUtil.getString("am.mqseries.service.error") + " : " + PCFConstants.lookupReasonCode(mqe.reasonCode) + ", " + FormatUtil.getString("am.mqseries.service.reason") + " : ";
/* 322 */         arr$ = responses;len$ = arr$.length;i$ = 0; } for (; i$ < len$; i$++) { PCFMessage pcfResponse = arr$[i$];
/* 323 */         if (pcfResponse.getCompCode() == 1) {
/* 324 */           switch (pcfResponse.getReason()) {
/*     */           case 3263: 
/* 326 */             message = message + FormatUtil.getString("am.mqseries.service.stop.param.missing");
/* 327 */             break;
/*     */           case 3253: 
/* 329 */             message = message + FormatUtil.getString("am.mqseries.service.notrunning");
/* 330 */             break;
/*     */           default: 
/* 332 */             message = message + PCFConstants.lookupReasonCode(responses[0].getReason());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 339 */       e.printStackTrace();
/*     */     } finally {
/* 341 */       if (connHandler != null) {
/* 342 */         connHandler.returnConnection();
/*     */       }
/*     */     }
/* 345 */     response.setContentType("text/plain;charset=UTF-8");
/* 346 */     PrintWriter out = response.getWriter();
/* 347 */     out.print(success + "|" + message);
/* 348 */     out.flush();
/* 349 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String nZ(String str1, String str2)
/*     */   {
/*     */     try
/*     */     {
/* 360 */       if ((str1 != null) && (str1.trim().length() > 0)) {
/* 361 */         return str1.trim().equalsIgnoreCase("null") ? str2 : str1;
/*     */       }
/* 363 */       return str2;
/*     */     }
/*     */     catch (Exception e) {}
/* 366 */     return str2;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\me\apm\client\mqseries\actions\MQSeriesAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */