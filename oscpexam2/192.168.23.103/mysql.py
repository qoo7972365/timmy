import pymysql

conn = pymysql.connect(host='192.168.23.103'
,user = 'dbadmin'
,passwd='xk7GQzU4qxDWe' # 密碼
,port= 3306 # 埠，默認為3306
,db='test_db' # 資料庫名稱
,charset='utf8' # 字符編碼
)

cur = conn.cursor() # 生成游標對象

sql="show databases;" # SQL語句

cur.execute(sql) # 執行SQL語句

data = cur.fetchall() # 通過fetchall方法獲得數據

for i in data[:2]: # 列印輸出前2條數據

	print (i)

cur.close() # 關閉游標

conn.close() # 關閉連接

