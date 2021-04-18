import pandas as pd
import pyodbc

cnxn_str = (
    r'DRIVER=ODBC Driver 11 for SQL Server;'
    r'SERVER=(local)\SQLEXPRESS;'
    r'Trusted_Connection=yes;'
    r'AttachDbFileName=/home/kali/timmy/oscp2/10.11.1.111/master.mdf;'
)
cnxn = pyodbc.connect(cnxn_str)
df = pd.read_sql("SELECT * FROM Table1", cnxn)