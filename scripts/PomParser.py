#! python
import os, re
# open pom.xml file
mypom = open('E:\\FilesForPy\\pom.xml')
mypomcontent = mypom.read()
print(mypomcontent)
# Try to find app name in pom.xml
mypomcontentregex = re.compile(r'(<finalName>)(\w+)')
mo = mypomcontentregex.search(mypomcontent)
print('FP_NAME: ' + mo.group(2))
#
appName = mo.group(2)
