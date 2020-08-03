package com.zzq.demo.kotlin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zzq.demo.R
import java.util.*

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        var demo = Demo()
        // var demo = Demo(123)
        demo.lastName = "joe"
        // demo.name = "ppp"
        toast(demo.name)
    }

    // class Demo private constructor () {
    // class Demo constructor () {
    // 默认是final表示不能被继承，open表示可以被继承
    open class Demo() {

        var lastName: String = "zhang"
            get() = field.toUpperCase(Locale.ROOT)  // 将变量赋值后转换为大写
            set(value) {
                field = "=$value=";
            }

        var firstName = "joe"
            private set

        var name: String

        /*
        init相当于主构造函数Demo()
        */
        init {
            name = "$firstName-$lastName"
        }

        // private constructor(){}

        // 类也可以有二级构造函数，需要加前缀 constructor
        // 如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。在同一个类中代理另一个构造函数使用 this 关键字
        constructor(a: Int) : this() {
            name = "$firstName-$lastName-$a"
        }

        // 允许子类重写
        open fun study() {
            println("我毕业了")
        }
    }

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    /**
     * 定义接口
     */
    interface CallbackInterface {
        fun test()

        fun bar()    // 未实现
        fun foo() {  //已实现
            // 可选的方法体
            // 允许方法有默认实现
            println("foo")
        }
    }

    fun interfaceTest() {
        setCallback(object : CallbackInterface {
            override fun test() {

            }

            override fun bar() {

            }
        })
    }

    fun setCallback(obj: CallbackInterface) {

    }

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------

    /*
    Kotlin 中没有基础数据类型，只有封装的数字类型，你每定义的一个变量，其实 Kotlin 帮你封装了一个对象，这样可以保证不会出现空指针。
    数字类型也一样，所以在比较两个数字的时候，就有比较数据大小和比较两个对象是否相同的区别了。
    在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小。
    * */
    fun num() {
        // val a: Int = 10_000
        val a: Int = 10000
        println(a === a) // true，值相等，对象地址相等

        //经过了装箱，创建了两个不同的对象
        val boxedA: Int? = a
        val anotherBoxedA: Int? = a

        //虽然经过了装箱，但是值是相等的，都是10000
        println(boxedA === anotherBoxedA) //  false，值相等，对象地址不一样
        println(boxedA == anotherBoxedA) // true，值相等
    }

    fun whenDemo(x: Int) {
        /*when 将它的参数和所有的分支条件顺序比较，直到某个分支满足条件。
        when 既可以被当做表达式使用也可以被当做语句使用。
        如果它被当做表达式，符合条件的分支的值就是整个表达式的值，如果当做语句使用， 则忽略个别分支的值。*/
        when (x) {
            1 -> print("x == 1")
            2 -> print("x == 2")
            else -> { // 注意这个块
                print("x 不是 1 ，也不是 2")
            }
        }

        /*在 when 中，else 同 switch 的 default。如果其他分支都不满足条件将会求值 else 分支。
        如果很多分支需要用相同的方式处理，则可以把多个分支条件放在一起，用逗号分隔：*/
        when (x) {
            0, 1 -> print("x == 0 or x == 1")
            in 5..10 -> print("x is in the range")
            !in 10..20 -> print("x is outside the range")
            else -> print("otherwise")
        }
    }

    fun newArray() {
        //[1,2,3]
        val a = arrayOf(1, 2, 3)
        //[0,2,4]
        val b = Array(3, { i -> (i * 2) })

        //读取数组内容
        println(a[0])    // 输出结果：1
        println(b[1])    // 输出结果：2
    }

    private fun add(a: Int, b: Int): Int {
        return a + b
    }

    private fun toast(toastStr: String) {
        Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show()
    }

    // vars(1, 2, 3, 4, 5, 6);
    private fun vars(vararg v: Int) {
        for (vt in v) {
            Log.v("---zzq---", vt.toString())
        }
    }

    private fun rangeTo() {
        // [0,9]
        for (i in 0..9) {
            Log.v("---zzq---", i.toString())
        }
    }

    private fun showParseInt() {
        val aaa = parseInt(null)
        val bbb = parseInt("123")
        if (aaa != null && bbb != null) {
            toast((aaa * bbb).toString())
        }
        /*
        //类型后面加?表示可为空
        var age: String? = "23"
        //抛出空指针异常
        val ages = age!!.toInt()
        //不做处理返回 null
        val ages1 = age?.toInt()
        //age为空返回-1
        val ages2 = age?.toInt() ?: -1
        */
    }

    private fun parseInt(str: String?): Int? {
        return str?.toInt() ?: -1
    }

    private fun getStringLength(obj: Any): Int {
        if (obj is String) {
            return obj.length
        }
        return 0
    }

}