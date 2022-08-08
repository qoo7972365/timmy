#!/usr/bin/env python
#coding=utf-8

from aliyunsdkcore.client import AcsClient
from aliyunsdkcore.acs_exception.exceptions import ClientException
from aliyunsdkcore.acs_exception.exceptions import ServerException
from aliyunsdkcore.auth.credentials import AccessKeyCredential
from aliyunsdkcore.auth.credentials import StsTokenCredential
from aliyunsdkalidns.request.v20150109.DescribeDomainsRequest import DescribeDomainsRequest
from aliyunsdkbssopenapi.request.v20171214.QueryBillOverviewRequest import QueryBillOverviewRequest



credentials = AccessKeyCredential('LTAI5t9hYXZdoGATzHFc2T14', 'X9Dn5LXmiEolPl1lpVdgTm59FZvey7')
region_list = ['cn-qingdao',
'cn-beijing','cn-zhangjiakou','cn-huhehaote','cn-wulanchabu','cn-hangzhou','cn-shanghai','cn-shenzhen','cn-chengdu','cn-hongkong','ap-northeast-1','ap-southeast-1','ap-southeast-2','ap-southeast-3','ap-southeast-5','us-east-1','us-west-1','eu-west-1','eu-central-1','ap-south-1','me-east-1']

for reg in region_list:
    try :
        client = AcsClient(region_id=reg, credential=credentials)

        request = DescribeDomainsRequest()
        #request = QueryBillOverviewRequest()
        request.set_accept_format('json')
        #request.set_BillingCycle("2022-08")
        response = client.do_action_with_exception(request)
        # python2:  print(response) 
        print(str(response, encoding='utf-8'))
    except Exception as error:
        print(error)