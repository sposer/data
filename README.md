# data

ObjectBox的简易增删改查帮助类，以方便平时快速开发，只适用于Kotlin

## 使用

1. 确保repositories中有`mavenCentral()`，如：

    ``` java
    repositories {
        ...
        mavenCentral()
        ...
    }
    
    ```

2. 在跟目录build.gradle中添加：

    ``` java
    dependencies {
        //下面是自动生成的
        classpath "com.android.tools.build:gradle:7.1.3"
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20'
        //添加的
        classpath "io.objectbox:objectbox-gradle-plugin:3.1.2"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
    ```

3. 在子目录的build.gradle中添加：

    ``` java
    plugins {
        id 'com.android.application'
        id 'kotlin-android'
        //添加的
        id 'io.objectbox'
    }
    dependencies {
        //添加的
        implementation "top.heue.utils:data:1.1.0"
    }
    ```

4. 点击`Sync Now`等待完成
5. 新建一个数据类，假设为`SampleData`：

    ``` java
    @Entity//必须
    public class SampleData {
        @Id//必须
        public long id;//必须为long类型
        public String text;//自定义，之后可以随意
    }
    ```

6. 点击一次`Build->Make Project`，这样是为了在这个项目生成`MyObjectBox`类
7. 在合适的地方初始化，这里假设为应用Application的onCreate()里：

    ``` java
    @Override
    public void onCreate() {
        super.onCreate();
        ...
        //DataHelper
        DataHelper.init(this, MyObjectBox.builder());
    }
    ```

8. 之后可以有如下使用方式：

    ``` java
    //为了方便加了一个typealias DH = DataHelper
    //使用时可以将DataHelper换为DH
    
    //添加或更改
    DataHelper.addOrSet<T>(data)
    //查找所有
    DataHelper.findAll<SampleData>()
    //指定字段查找，注意SampleData_为build后自动生成的类，多一个下划线
    DataHelper.findByAny(SampleData_.text, s)
    //删除
    DataHelper.removeByObject<SampleData>(data)
    DataHelper.removeById<T>(id)
    //查找所有时排序，这个一般不用，只是加着
    DataHelper.findByReverse(property: Property<T>)
    ```
