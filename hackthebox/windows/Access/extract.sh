#!/bin/bash
for i in `mdb-tables backup.mdb`
  do
  #echo $i
  line=`mdb-export backup.mdb $i | wc -l`

  #echo $line $content
  #echo $line
  if [ $line -gt 1 ]
  then
    #rm -f $i
    mdb-export backup.mdb $i > tables/$i;
    #echo $i
  fi
  done