JNIitf
======

JNI interfaces including JNI downcall, JNI downcall (onload), JNI upcall and JNI invoke

Usages:

<pre><code>
\# cd JNIitf
\# android list target (choose SDK target ID)
\# ./make <id>
</pre></code>

Bug:
* in Nexus 9, /data/data/<package>/lib fails to link /data/app/<package>/lib/<arch>, but links /data/app/<package>, so libcallee.so cannot be found

TODO list:
* transfer platform from ant-based CLI to gradle-based Android Studio

