import pyodbc

conn = pyodbc.connect('DRIVER={FreeTDS};SERVER=10.11.1.31;PORT=1433;UID=saPWD=poiuytrewq;DATABASE=bankdb;UseNTLMv2=yes;TDS_Version=8.0;Trusted_Domain=domain.local;')

cursor.execute("SELECT * FROM <tablename>")
rows = cursor.fetchall()

if rows:
    print(rows)