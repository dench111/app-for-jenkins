#!/usr/bin/env python2
import os, re
name = ''
appName = 'build'
build_number = 777
fullAppName = appName + '-' + str(build_number)
print(fullAppName)
os.environ["name"] = "fullAppName"
print name
