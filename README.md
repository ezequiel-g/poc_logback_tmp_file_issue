# POC log to many records.
POC created to check a bug in logback 1.2.3 and possible fix and workaround.

**Issue:** when logging a lot of message using SizeAndTimeBasedRollingPolicy LogBack a .tmp file is created and not compressed. It seems that the .tmp file is still receiving message while rolling file and when it try to create a compressed file it already exists.

**App example:** App class is a simple class that just logs a lot in a while loop.
* it create random message using **UUID.randomUUID()** concatenating it depending **multiplicationFactor** value.
* it will log all log levels, building random message for each of them depending in **repeat** value.

## branch [master] issue.
After running the app log folder will looks like this, in this example it created 2 .tmp files.
* <slf4j.version>1.7.30</slf4j.version>
* <logback.version>1.2.3</logback.version>

### Folder list example
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

### Logback debug info.

~~~
23:03:27,244 |-INFO in ch.qos.logback.core.joran.action.ImplicitModelAction - Assuming default class name [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for tag [encoder]
23:03:27,377 |-INFO in ch.qos.logback.classic.model.processor.RootLoggerModelHandler@61baa894 - Setting level of ROOT logger to ALL
23:03:27,380 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler@7d4793a8 - Processing appender named [file_rollover]
23:03:27,380 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler@7d4793a8 - About to instantiate appender of type [ch.qos.logback.core.rolling.RollingFileAppender]
23:03:27,404 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@801197928 - setting totalSizeCap to 2 MB
23:03:27,408 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@801197928 - Archive files will be limited to [5 KB] each.
23:03:27,458 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@801197928 - Will use zip compression
23:03:27,460 |-INFO in c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@801197928 - Will use the pattern target/myApp_logback.%d{yyyy-MM-dd}-%i.log for the active file
23:03:27,469 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@6b71769e - The date pattern is 'yyyy-MM-dd' from file name pattern 'target/myApp_logback.%d{yyyy-MM-dd}-%i.log.zip'.
23:03:27,469 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@6b71769e - Roll-over at midnight.
23:03:27,480 |-INFO in ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP@6b71769e - Setting initial period to Mon Nov 30 23:03:16 ART 2020
23:03:27,508 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[file_rollover] - Active log file name: target/myApp_logback.log
23:03:27,508 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[file_rollover] - File property is set to [target/myApp_logback.log]
23:03:27,509 |-INFO in ch.qos.logback.core.model.processor.AppenderModelHandler@7d4793a8 - Attaching appender [file_rollover] to Logger[ROOT]
23:03:27,512 |-INFO in ch.qos.logback.core.model.processor.DefaultProcessor@3a03464 - End of configuration.
23:03:27,513 |-INFO in ch.qos.logback.classic.joran.JoranConfigurator@6537cf78 - Registering current configuration as safe fallback point
23:03:27,827 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-8.log1534365179079300.tmp]
23:03:27,835 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-8.log1534365179079300.tmp] as [target\myApp_logback.2020-11-30-8.log.zip]
23:03:27,849 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-9.log1534365200126200.tmp]
23:03:27,863 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-10.log1534365214689700.tmp]
23:03:27,874 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-8.log1534365179079300.tmp] as [target\myApp_logback.2020-11-30-8.log.zip]
23:03:27,876 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - first clean up after appender initialization
23:03:27,877 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Multiple periods, i.e. 32 periods, seem to have elapsed. This is expected at application start.
23:03:27,884 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-11.log1534365236432500.tmp]
23:03:27,939 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-12.log1534365291566100.tmp]
23:03:27,963 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:27,963 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-9.log1534365200126200.tmp] as [target\myApp_logback.2020-11-30-9.log.zip]
23:03:27,974 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-13.log1534365326102400.tmp]
23:03:27,976 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-9.log1534365200126200.tmp] as [target\myApp_logback.2020-11-30-9.log.zip]
23:03:27,990 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:27,990 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-10.log1534365214689700.tmp] as [target\myApp_logback.2020-11-30-10.log.zip]
23:03:28,014 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-10.log1534365214689700.tmp] as [target\myApp_logback.2020-11-30-10.log.zip]
23:03:28,017 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-14.log1534365369686900.tmp]
23:03:28,023 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:28,023 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-11.log1534365236432500.tmp] as [target\myApp_logback.2020-11-30-11.log.zip]
23:03:28,038 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-11.log1534365236432500.tmp] as [target\myApp_logback.2020-11-30-11.log.zip]
23:03:28,065 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:28,066 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-12.log1534365291566100.tmp] as [target\myApp_logback.2020-11-30-12.log.zip]
23:03:28,076 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-15.log1534365427830100.tmp]
23:03:28,091 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-12.log1534365291566100.tmp] as [target\myApp_logback.2020-11-30-12.log.zip]
23:03:28,124 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:28,124 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-13.log1534365326102400.tmp] as [target\myApp_logback.2020-11-30-13.log.zip]
23:03:28,142 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-13.log1534365326102400.tmp] as [target\myApp_logback.2020-11-30-13.log.zip]
23:03:28,149 |-INFO in c.q.l.core.rolling.helper.TimeBasedArchiveRemover - Removed  0 Bytes of files
23:03:28,150 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - ZIP compressing [target\myApp_logback.2020-11-30-14.log1534365369686900.tmp] as [target\myApp_logback.2020-11-30-14.log.zip]
23:03:28,188 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [target\myApp_logback.log] to [target\myApp_logback.2020-11-30-16.log1534365539478900.tmp]
23:03:28,194 |-INFO in ch.qos.logback.core.rolling.helper.Compressor - Done ZIP compressing [target\myApp_logback.2020-11-30-14.log1534365369686900.tmp] as [target\myApp_logback.2020-11-30-14.log.zip]

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
