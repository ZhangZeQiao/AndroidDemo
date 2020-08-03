package com.zzq.demo.kotlin

// 二、懒汉式实现单例模式
class SingletonDemo02 private constructor() {

    /*
    在Kotlin中类没有静态方法。
    如果你需要写一个可以无需用一个类的实例来调用，但需要访问类内部的函数（例如，工厂方法,单例等），你可以把该类声明为一个对象。
    该对象与其他语言的静态成员是类似的。
    */

    // 伴生对象
    companion object {

        private var instance: SingletonDemo02? = null
            get() {
                if (field == null) {
                    field = SingletonDemo02()
                }
                return field
            }

        fun getIns(): SingletonDemo02 {
            // 细心的小伙伴肯定发现了，这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
            return instance!!
            /*
            先阐述两个概念：
            "?"加在变量名后，系统在任何情况不会报它的空指针异常。
            "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
            */
        }
    }

    // ----------------------------------------------
    // ----------------------------------------------

    // 三、线程安全的懒汉式
    class SingletonDemo03 private constructor() {

        companion object {

            private var instance: SingletonDemo03? = null
                get() {
                    if (field == null) {
                        field = SingletonDemo03()
                    }
                    return field
                }

            // 在Kotlin中，如果你需要将方法声明为同步，需要添加@Synchronized注解
            @Synchronized
            fun getIns(): SingletonDemo03 {
                return instance!!
            }
        }
    }

    // ----------------------------------------------
    // ----------------------------------------------

    // 双重校验锁式（Double Check)
    class SingletonDemo04 private constructor() {

        companion object {
            /*
            Lazy是接受一个 lambda 并返回一个 Lazy 实例的函数，返回的实例可以作为实现延迟属性的委托：
            第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果，后续调用 get() 只是返回记录的结果。

            这里还有有两个额外的知识点：高阶函数、委托属性
            */
            val instance: SingletonDemo04 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
                SingletonDemo04()
            }
        }
    }

    // ----------------------------------------------
    // ----------------------------------------------

    // 静态内部类式
    class SingletonDemo05 private constructor() {

        companion object {
            val instance = SingletonHolder.holder
        }

        private object SingletonHolder {
            val holder = SingletonDemo05()
        }
    }
}