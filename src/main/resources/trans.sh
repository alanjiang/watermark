#!/bin/sh
#$1:工具所在的位置，$2:被转换文件路径, $3: 目标PDF产品的路径
success=OK
fail=FAIL

#/opt/libreoffice6.2/program/soffice --headless --convert-to  pdf /tmp/答辩知识点汇总V0.3.docx --outdir /tmp/

/opt/libreoffice6.2/program/soffice --headless --convert-to  pdf $2 --outdir $3

if [ $? -eq 0 ];then
 echo $SUCCESS
 exit 0
else
 echo $FAIL
 exit 1
fi



