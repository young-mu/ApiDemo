JNIitf
======

JNI interfaces including JNI downcall, JNI downcall (onload), JNI upcall and JNI invoke

Usages:

<pre><code>
\# cd JNIitf
\# android list target (choose SDK target ID)
\# ./make [id]
</pre></code>

Bug:
* in Nexus9 arm64-v8a, /data/data/<package>/lib fails to link /data/app/<package>/lib/<arch>, but links /data/app/<package>, so libcallee.so cannot be found, this is android-5.1.1 internal bug.

TODO list:
* transfer platform from ant-based CLI to gradle-based Android Studio
