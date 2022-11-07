-printconfiguration build/tmp/full-r8-config.txt

-renamesourcefileattribute SourceFile
-keepattributes SourceFile, LineNumberTable

-keepclassmembers, allowshrinking, allowobfuscation interface * { @retrofit2.http.* <methods>; }
-keepclasseswithmembers class * { @retrofit2.http.* <methods>; }

-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-keepclassmembers,allowobfuscation class * { @com.google.gson.annotations.SerializedName <fields>;}