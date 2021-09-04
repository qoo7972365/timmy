/*     */ package com.adventnet.appmanager.servlets.comm;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.AMCacheHandler;
/*     */ import com.adventnet.appmanager.fault.AMEventBuffer;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.logging.AMLog;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.nms.eventdb.Event;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncEventServlet
/*     */   extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public void init(ServletConfig sConfig)
/*     */     throws ServletException
/*     */   {
/*     */     try
/*     */     {
/*  46 */       super.init(sConfig);
/*     */     }
/*     */     catch (ServletException e)
/*     */     {
/*  50 */       AMLog.debug("Error while getting the EventAPI", e);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  56 */     doGet(request, response);
/*     */   }
/*     */   
/*     */   public byte[] readBytesFromStream(InputStream is)
/*     */   {
/*  61 */     ArrayList list = new ArrayList();
/*     */     try
/*     */     {
/*     */       int c;
/*  65 */       while ((c = is.read()) != -1)
/*     */       {
/*  67 */         list.add(new Byte((byte)c));
/*     */       }
/*  69 */       if (!list.isEmpty())
/*     */       {
/*  71 */         int size = list.size();
/*  72 */         byte[] bytes = new byte[size];
/*  73 */         for (int i = 0; i < size; i++)
/*     */         {
/*  75 */           bytes[i] = ((Byte)list.get(i)).byteValue();
/*     */         }
/*  77 */         return bytes;
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  82 */       AMLog.fatal("Error while reading event object from the input stream", e);
/*  83 */       return null;
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  90 */     String operation = request.getParameter("operation");
/*  91 */     if (EnterpriseUtil.isAdminServer())
/*     */     {
/*  93 */       if ((operation != null) && (operation.equals("checkEventSynch")))
/*     */       {
/*     */ 
/*  96 */         String entity = request.getParameter("entity");
/*  97 */         String ttimeStr = request.getParameter("ttime");
/*  98 */         boolean responseObj = false;
/*  99 */         if ((entity != null) && (ttimeStr != null))
/*     */         {
/* 101 */           HashMap entityMap = (HashMap)AMCacheHandler.events.get(entity);
/* 102 */           long ttime = -1L;
/* 103 */           long ttimeFromCache = -1L;
/*     */           try
/*     */           {
/* 106 */             ttime = Long.parseLong(ttimeStr);
/* 107 */             ttimeFromCache = (entityMap != null) && (entityMap.get("TIME") != null) ? Long.parseLong((String)entityMap.get("TIME")) : -1L;
/*     */           }
/*     */           catch (NumberFormatException nfe)
/*     */           {
/* 111 */             nfe.printStackTrace();
/*     */           }
/* 113 */           AMLog.debug("SyncEventServlet::: ttime:" + ttime + "\tttimeFromCache:" + ttimeFromCache + "\tentityMap:" + entityMap);
/* 114 */           if ((entityMap != null) && (ttimeFromCache != -1L) && (ttime != -1L) && (ttimeFromCache == ttime))
/*     */           {
/* 116 */             responseObj = true;
/*     */           }
/*     */           else
/*     */           {
/* 120 */             ResultSet rs = null;
/*     */             try
/*     */             {
/* 123 */               String query = "SELECT ID FROM Event WHERE ENTITY='" + entity + "' and TTIME='" + ttime + "'";
/* 124 */               rs = AMConnectionPool.executeQueryStmt(query);
/* 125 */               if (rs.next())
/*     */               {
/* 127 */                 responseObj = true;
/*     */               }
/*     */             }
/*     */             catch (Exception re)
/*     */             {
/* 132 */               AMLog.debug("Error while checking the existance of event object with entity " + entity + "\n Error Message:" + re.getMessage());
/* 133 */               AMLog.fatal("Error while checking the existance of event object with entity " + entity, re);
/*     */             }
/*     */             finally
/*     */             {
/* 137 */               AMConnectionPool.closeStatement(rs);
/*     */             }
/*     */           }
/*     */         }
/* 141 */         sendResponse(response, String.valueOf(responseObj));
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 146 */         Event event = null;
/* 147 */         boolean responseRet = false;
/* 148 */         String eventParams = request.getParameter("eventParams");
/* 149 */         AMLog.logSyncDebug("Event Params in SyncEventServlet is :" + eventParams);
/* 150 */         if ((eventParams != null) && (!eventParams.equals("")))
/*     */         {
/*     */           try
/*     */           {
/* 154 */             JSONObject obj = new JSONObject(eventParams);
/* 155 */             event = new Event();
/* 156 */             event.setEntity(obj.getString("entity"));
/* 157 */             event.setId(obj.getInt("id"));
/* 158 */             event.setSource(obj.getString("source"));
/* 159 */             event.setText(obj.getString("text"));
/* 160 */             event.setTime(obj.getLong("time"));
/* 161 */             event.setSeverity(obj.getInt("severity"));
/* 162 */             event.setNode(obj.getString("node"));
/* 163 */             event.setCategory(obj.getString("category"));
/* 164 */             event.setGroupName(obj.getString("groupName"));
/* 165 */             AMLog.info("EVENT-PUSH-FROM-MAS::::Entity::::" + obj.getString("entity") + "     Severity::::" + obj.getInt("severity") + "   Date::::" + new Date(obj.getLong("time")));
/*     */           }
/*     */           catch (JSONException e)
/*     */           {
/* 169 */             e.printStackTrace();
/* 170 */             AMLog.debug("Error while parsing the json string to form the event object");
/*     */           }
/*     */         }
/* 173 */         if (event != null)
/*     */         {
/*     */           try
/*     */           {
/* 177 */             FaultUtil.addEvent(event, true);
/* 178 */             responseRet = true;
/* 179 */             AMLog.logSyncDebug("Event with entity " + event.getEntity() + ", ID : " + event.getId() + " added successfully in the Admin Server");
/*     */ 
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 184 */             AMLog.debug("[ClassName : SyncEventServlet, MethodName:doGet] Error while adding the Event with entity" + event.getEntity() + " in the Admin Server. \n Error message :" + ex.getMessage());
/* 185 */             AMLog.fatal("Error while adding theEvent with entity" + event.getEntity() + " in the Admin Server", ex);
/* 186 */             responseRet = false;
/*     */           }
/* 188 */           sendResponse(response, String.valueOf(responseRet));
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/* 194 */     else if ((operation != null) && (operation.equals("setPushModelStatus")))
/*     */     {
/* 196 */       String statusStr = request.getParameter("status");
/* 197 */       String eventId = request.getParameter("EventID");
/* 198 */       AMLog.logSyncDebug("Parameters in SyncEventServlet::: statusStr=" + statusStr + " eventId=" + eventId);
/* 199 */       if ((statusStr != null) && (eventId != null) && (!eventId.equals("-1")))
/*     */       {
/* 201 */         boolean status = Boolean.valueOf(statusStr).booleanValue();
/* 202 */         AMEventBuffer buffer = AMEventBuffer.getInstance();
/* 203 */         boolean bufferOverflowStatus = buffer.isBufferOverflowed();
/* 204 */         boolean isEventSynchedInAdminServer = buffer.isEventSynchedInAdminServer();
/* 205 */         AMLog.logSyncDebug("Buffer overflow status :" + bufferOverflowStatus + "\tAdmin Server Event Synch Status :" + isEventSynchedInAdminServer);
/* 206 */         if (bufferOverflowStatus)
/*     */         {
/* 208 */           buffer.resetBufferOverflowStatus();
/*     */         }
/*     */         else
/*     */         {
/* 212 */           ResultSet rs = null;
/* 213 */           String query = "SELECT ID FROM Event WHERE ID > " + eventId + " AND CATEGORY NOT IN ('7000','7001','7080','7081','7010','7011','7020','7021','7070','7071','7040','7041','7030','7031','7050','7051','7052','7053','3700','3701','9500','9501','9630','9631')";
/*     */           try
/*     */           {
/* 216 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 217 */             boolean dbStatus = rs.next();
/* 218 */             AMLog.logSyncDebug("SyncEventServlet::: Query Event table with event id:" + eventId + " and event result status:" + dbStatus);
/* 219 */             if (!dbStatus)
/*     */             {
/* 221 */               buffer.setEventSynchedInAdminServer(status);
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 226 */             e.printStackTrace();
/*     */           }
/*     */           finally
/*     */           {
/* 230 */             AMConnectionPool.closeStatement(rs);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendResponse(HttpServletResponse response, String responseStr)
/*     */     throws IOException
/*     */   {
/* 240 */     response.setHeader("Content-Type", "text/plain");
/* 241 */     PrintWriter out = response.getWriter();
/* 242 */     out.write(responseStr);
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\com\adventnet\appmanager\servlets\comm\SyncEventServlet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */