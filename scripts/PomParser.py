#! python2
import os, re
# open pom.xml file
mypom = open('\\var\\jenkins_home\\workspace\\sources\\pom.xml')
mypomcontent = mypom.read()
print(mypomcontent)
# find app name in pom.xml
mypomcontentregex = re.compile(r'(<finalName>)(\w+)')
mo = mypomcontentregex.search(mypomcontent)
print('FP_NAME: ' + mo.group(2))
print($BUILD_NUMBER)
#rename app
appName = mo.group(2)
fullAppName = appName + '-' + str($BUILD_NUMBER)
print(fullAppName)
os.environ["FPname"] = str(fullAppName)
