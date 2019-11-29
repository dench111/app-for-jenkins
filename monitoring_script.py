#!/usr/bin/env python
# -*- coding: utf-8 -*-
from __future__ import print_function
import json, sys, os, pprint, re, subprocess, io, platform
# *********Общий статус кластера********
elkStatus = 'curl -XGET "$ElasticURL:9200/_cluster/health?pretty"'
elkResponse = (os.popen(elkStatus).read())
elkRespAsDict = json.loads(elkResponse)
print(elkResponse)
# ********Проверка свободного пространства на дисках********
diskSpaceStatus = 'curl -XGET "$ElasticURL:9200/_cat/allocation?format=json"'
diskSpaceResponse = (os.popen(diskSpaceStatus).read())
diskSpaceResponseAsDict = json.loads(diskSpaceResponse)
diskSpaceResponseAsDict = diskSpaceResponseAsDict[0]
diskSpace = diskSpaceResponseAsDict['disk.percent']
markers = {'statusMarker': 0, 'unassignedMarker': 0, 'diskSpaceMarker': 0}
pingServer = 'ping -c 2 192.168.0.103'
uptimeServer = 'uptime'
ramload = 'free -h'
cpuload = 'vmstat 5 5'

def out_red(text):
    print("\033[31m {}".format(text))
def out_green(text):
    print("\033[32m {}".format(text))
def out_blue(text):
    print("\033[36m {}".format(text))
def checkServerHealth(param):
    result = (os.popen(param).read())
    out_blue(result)
    out_blue('============================================================================================================================')

def checkResponseStatus(Resp, diskSpace):
    if Resp['status'] != 'green':
        markers['statusMarker'] = 1
    if Resp['unassigned_shards'] > 0:
        markers['unassignedMarker'] = 1
    if int(diskSpace) > 85:
        markers['diskSpaceMarker'] = 1
    print(markers)
    

def markerCheck():
    alerttext = ()
    for k, v in markers.items():
        if v == 1:
            print('Alert!!!' + k + ' is bad')
            if k == 'statusMarker':
                statusalert = ('Alert!!! Elastic STATUS = ' + elkRespAsDict['status'])
                statusalert = str(statusalert)
                out_red(text)
                alerttext = alerttext + statusalert
            if k == 'unassignedMarker':
                print('Alert!!! unassigned_shards = ', + elkRespAsDict['unassigned_shards'])
                checkDir = (os.popen('pwd').read())
                print(checkDir)
                createFile = (os.popen('touch /var/lib/jenkins/workspace/Elastic_Notifier/testtest.txt').read())
                viewDir = (os.popen('ls -la').read())
                print(viewDir)
                unReq = 'curl -XGET "$ElasticURL:9200/_cat/shards?h=index,shard,prirep,state,unassigned.reason" | grep "UNASSIGNED" > /var/lib/jenkins/workspace/Elastic_Notifier/testtest.txt'
                os.system(unReq)
                reason = 'curl -XGET "$ElasticURL:9200/_cluster/allocation/explain?pretty"'
                reasonPr = (os.popen(reason).read())
                print(reasonPr)
                uassignedshards = ('Alert!!! unassigned_shards = ' + elkRespAsDict['unassigned_shards'])
                uassignedshards = str(uassignedshards)
                alerttext = alerttext + uassignedshards
            if k == 'diskSpaceMarker':
                    diskspacealert = ('Alert!!! disk.percent = ' + diskSpaceResponseAsDict['disk.percent'])
                    diskspacealert = str(diskspacealert)
                    alerttext = alerttext + diskspacealert
        elif v == 0:
            text = (k + ' is OK')
            out_green(text)
    if len(alerttext) != 0:
        os.environ["ELKStatus"] = str(alerttext)
        subprocess.call("/var/lib/jenkins/workspace/Ansible_Test_WithGit/sendemail.sh", shell=True)

checkServerHealth(pingServer)
checkServerHealth(uptimeServer)
checkServerHealth(ramload)
checkServerHealth(cpuload)
checkResponseStatus(elkRespAsDict, diskSpace)
markerCheck()
