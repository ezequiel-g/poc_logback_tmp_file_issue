# POC logback tmp file.
POC created to check a bug in logback 1.2.3.

Issue: rollover log files is leaving a .tmp file while compressing.


    <slf4j.version>1.7.30</slf4j.version>
    <logback.version>1.2.3</logback.version>

~~~
 /c/z_WS/sts-4/poc_logback_tmpfile/target
$ ls -l
total 2788
drwxr-xr-x 1 EzequielG 197121      0 Nov 22 02:10 classes/
-rw-r--r-- 1 EzequielG 197121 283198 Nov 22 02:09 myApp.2020-11-22-0.log.gz
-rw-r--r-- 1 EzequielG 197121 160937 Nov 22 02:09 myApp.2020-11-22-1.log.gz
-rw-r--r-- 1 EzequielG 197121 665600 Nov 22 02:09 myApp.2020-11-22-1.log767891225478700.tmp
-rw-r--r-- 1 EzequielG 197121 267408 Nov 22 02:09 myApp.2020-11-22-2.log.gz
-rw-r--r-- 1 EzequielG 197121 160721 Nov 22 02:09 myApp.2020-11-22-3.log.gz
-rw-r--r-- 1 EzequielG 197121 665600 Nov 22 02:09 myApp.2020-11-22-3.log767911943530900.tmp
-rw-r--r-- 1 EzequielG 197121 628555 Nov 22 02:09 myApp.log
drwxr-xr-x 1 EzequielG 197121      0 Nov 22 00:11 test-classes/

 /c/z_WS/sts-4/poc_logback_tmpfile/target

~~~

## FIXED: logback.xml_05_FixedWindowRollingPolicy_WORKS
example removing totalSizeCap, but still happening.

## TRIED: logback.xml_01_SizeAndTimeBasedRollingPolicy_backup_config
backup of current logback config

## TRIED: logback.xml_02_SizeAndTimeBasedRollingPolicy_removing_file_ISSSUE
example trying to removing <file> but issue is still happening.

## TRIED: logback.xml_03_AsyncAppender_ISSUE
example trying an AsyncAppender but issue is still happening.

## TRIED: logback.xml_04_SizeAndTimeBasedRollingPolicy_removing_totalSizeCap_ISSUE
example trying removing totalSizeCap but issue is still happening.

## TRIED: pom_bkp20201121.xml
example using alpha versions of slf4j and logback but issue is still happening

~~~
 /c/z_WS/sts-4/poc_logback_tmpfile/target
$ ls -l
total 2856
drwxr-xr-x 1 EzequielG 197121      0 Nov 22 02:30 classes/
-rw-r--r-- 1 EzequielG 197121   4498 Nov 22 02:31 myApp.2020-11-22-0.log.gz
-rw-r--r-- 1 EzequielG 197121   4640 Nov 22 02:31 myApp.2020-11-22-1.log.gz
-rw-r--r-- 1 EzequielG 197121  41472 Nov 22 02:31 myApp.2020-11-22-10.log769220574418000.tmp
-rw-r--r-- 1 EzequielG 197121  82955 Nov 22 02:31 myApp.2020-11-22-11.log769220601140900.tmp
-rw-r--r-- 1 EzequielG 197121 166400 Nov 22 02:31 myApp.2020-11-22-12.log769220652273300.tmp
-rw-r--r-- 1 EzequielG 197121 332800 Nov 22 02:31 myApp.2020-11-22-13.log769220734042600.tmp
-rw-r--r-- 1 EzequielG 197121 665600 Nov 22 02:31 myApp.2020-11-22-14.log769220847984600.tmp
-rw-r--r-- 1 EzequielG 197121   9124 Nov 22 02:31 myApp.2020-11-22-2.log.gz
-rw-r--r-- 1 EzequielG 197121  18169 Nov 22 02:31 myApp.2020-11-22-3.log.gz
-rw-r--r-- 1 EzequielG 197121  35896 Nov 22 02:31 myApp.2020-11-22-4.log.gz
-rw-r--r-- 1 EzequielG 197121  71321 Nov 22 02:31 myApp.2020-11-22-5.log.gz
-rw-r--r-- 1 EzequielG 197121 127992 Nov 22 02:31 myApp.2020-11-22-6.log.gz
-rw-r--r-- 1 EzequielG 197121 332800 Nov 22 02:31 myApp.2020-11-22-6.log769182801029300.tmp
-rw-r--r-- 1 EzequielG 197121   2399 Nov 22 02:31 myApp.2020-11-22-7.log.gz
-rw-r--r-- 1 EzequielG 197121 665600 Nov 22 02:31 myApp.2020-11-22-7.log769182899408100.tmp
-rw-r--r-- 1 EzequielG 197121   4648 Nov 22 02:31 myApp.2020-11-22-8.log.gz
-rw-r--r-- 1 EzequielG 197121   9161 Nov 22 02:31 myApp.2020-11-22-9.log.gz
-rw-r--r-- 1 EzequielG 197121 295755 Nov 22 02:31 myApp.log
drwxr-xr-x 1 EzequielG 197121      0 Nov 22 02:30 test-classes/

 /c/z_WS/sts-4/poc_logback_tmpfile/target

~~~

## TRIED: logback.xml_06_TimeBasedRollingPolicy_ISSUE
example removing totalSizeCap, but still happening.

## TRIED: log4j2 seems to work fine
it has a fixed number of daily backups logs are rollever around those sequence logs.
