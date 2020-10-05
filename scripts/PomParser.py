#!/usr/bin/env python2
import os, re
# open pom.xml file
build_number = os.getenv('BUILD_NUMBER')
#username = os.getenv('USERNAME')
#password = os.getenv('PASSWORD')
request = 'curl -XPOST -v -u $USERNAME:$PASSWORD --upload-file "/var/jenkins_home/workspace/Pipeline_Job/target/$appNameold.jar" "http://192.168.0.84:8081/nexus/content/repositories/Testrep/$FPname.jar"'

def uploadartifact(param):
        result = (os.popen(param).read())
        print(result)

mypom = open('/var/jenkins_home/workspace/Pipeline_Job/pom.xml')
mypomcontent = mypom.read()
# find app name in pom.xml
mypomcontentregex = re.compile(r'(<finalName>)(\w+)')
mo = mypomcontentregex.search(mypomcontent)
#print('FP_NAME: ' + mo.group(2))
#print(build_number)
#rename app
appName = mo.group(2)
fullAppName = appName + '-' + str(build_number)
print fullAppName
os.environ["FPname"] = str(fullAppName)
os.environ["appNameold"] = str(appName)
uploadartifact(request)
