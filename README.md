JNIitf
======

JNI interfaces including JNI downcall, JNI downcall (onload), JNI upcall and JNI invoke

Usages:

first build:
<pre><code>
\# cd JNIitf
\# android list target (choose SDK target ID)
\# android update project -n JNIitf -p . -t [target-ID]
\# ndk-build
\# ant debug
\# adb install bin/JNIitf-debug.apk
</pre></code>

rebuild:
<pre><code>
\# ant clean
\# ant debug
</pre></code>
