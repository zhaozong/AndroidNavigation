# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/ashen/NVPACK/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
################common###############
-keep class com.jph.android.entity.** { *; } #实体类不参与混淆
-keep class com.jph.android.view.** { *; } #自定义控件不参与混淆
-keepnames class * implements java.io.Serializable #保持 Serializable 不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
