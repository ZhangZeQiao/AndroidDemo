package com.zzq.demo.kotlin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zzq.demo.R
import kotlinx.coroutines.*
import java.util.*
import kotlin.properties.Delegates

class KotlinActivity : AppCompatActivity() {

    private var mTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        /*var demo = Demo()
        // var demo = Demo(123)
        demo.lastName = "joe"
        // demo.name = "ppp"
        toast(demo.name)*/

        // 协程
        // coroutine04()

        // 高阶函数
        /*mTv = findViewById(R.id.tv)
        setTvContent(mTv)
        applyTest()
        iterableTest()*/

        // 委托
        val lazyTest = LazyTest()
        Log.v("---zzq---", "lazyTest-----1-----" + lazyTest.lazyFieldIns)
        Log.v("---zzq---", "lazyTest-----2-----" + lazyTest.lazyFieldIns)
        val a = 5
        val b = 5
        // val lazyTest2 = LazyTest2 {
        //     a * b
        // }
        val lazyTest2 = LazyTest2(computerFun = {
            Log.v("---zzq---", "lazyTest2---0---a=$a---b=$b---a * b = ${a * b}")
            return@LazyTest2 a * b
        })
        Log.v("---zzq---", "lazyTest2---1---" + lazyTest2.lazyFieldIns)
        Log.v("---zzq---", "lazyTest2---2---" + lazyTest2.lazyFieldIns)
        observableTest()
    }

    /*
    lazy() 是一个函数, 接受一个 Lambda 表达式作为参数, 返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托：
    第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
    */
    class LazyTest {
        // 可用于双保险单例模式的实现：by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {}
        val lazyFieldIns: ApplyTest by lazy {
            Log.v("---zzq---", "lazyTest-----0-----")
            ApplyTest()
        }
    }

    class LazyTest2(computerFun: () -> Int) {
        val lazyFieldIns: Int by lazy(computerFun)
    }

    private fun observableTest() {
        /*
        observable 可以用于实现观察者模式。
        Delegates.observable() 函数接受两个参数: 第一个是初始化值, 第二个是属性值变化事件的响应器(handler)。
        在属性赋值后会执行事件的响应器(handler)，它有三个参数：被赋值的属性、旧值和新值：
        */
        var str: String by Delegates.observable("111") { prop, old, new ->
            Log.v("---zzq---", "prop==$prop---old==$old---new==$new")
        }
        Log.v("---zzq---", "str==$str")
        str = "222"
        Log.v("---zzq---", "str==$str")
    }


    // ------------------------------------------------------------------
    // ------------------------------------------------------------------


    // run作用域
    private fun setTvContent(tv: TextView?) {
        /*mTv?.text = "111"
        mTv?.setTextColor(Color.BLUE)
        mTv?.textSize = 50f*/
        /*
        run 函数基本是 let 和 with 的结合体，对象调用 run 函数，接收一个 lambda 函数为参数，传入 this 并以闭包形式返回，返回值是最后的计算结果。
        */
        val content = "123321"
        mTv?.run {
            text = content
            setTextColor(Color.BLUE)
            textSize = 50f
        }
        /*
        像上面这个例子，在需要多次设置属性，但设置属性后返回值不是改对象（或无返回值：Unit）不能链式调用的时候，就非常适合使用 run 函数。
        一种伪链式调用，把对同一个对象的操作圈在一个代码块中
        */
    }

    private fun applyTest() {
        val applyTestIns = ApplyTest()
        Log.v("---zzq---", "---" +
                /*
                由于 apply 函数返回的是调用对象自身，我们可以借助 apply 函数的特性进行多级判空。
                或者对中间某个属性进行操作
                */
                applyTestIns.child?.apply {
                    name = "hahahaha"
                }?.name
        )
    }

    class ApplyTest {

        var child: ApplyTestChild? = null

        init {
            child = ApplyTestChild()
        }

        class ApplyTestChild {
            var name = "haha"
        }
    }

    private fun iterableTest() {
        val list = listOf(1, 2, 3, 4, 5)
        list.forEach {
            Log.v("---zzq---", "forEach===$it")
        }
        list.map {
            Log.v("---zzq---", "map===$it")
        }

        val mapIns = mapOf<String, Int>(
                "1" to 1,
                "2" to 2,
                "3" to 3,
                "4" to 4,
                "5" to 5
        )
        mapIns.forEach {
            Log.v("---zzq---", "forEach===${it.key}===${it.value}")
        }
        mapIns.map {
            Log.v("---zzq---", "map===${it.key}===${it.value}")
        }

        /*
        1.在固定长度或者长度不需要计算的时候for循环效率高于foreach，在不确定长度或者计算长度有损性能的时候用foreach比较方便
        2.foreach适用于只是进行集合或数组遍历，for则在较复杂的循环中效率更高。

        性能显然是 for>forEach>map

        哪个可读性强选哪个。恕我直言，绝大部分公司产品、绝大部分程序，体现不出三者的性能差异。

        map并不是让你用来做遍历的而是做映射的。
        forEach这个才是用来遍历数组的
        for 非数组 或者 forEach满足不了的情况下使用
        */
    }

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------


    // 协程：立即启动型【协程启动模式是默认的DEAFAULT，也就是创建并立即启动的】
    fun coroutine01() {
        // 协程
        GlobalScope.launch {
            delay(1000_0L)
            Log.v("---zzq---", "World!")
        }
        Log.v("---zzq---", "Hello,")
    }

    // 协程：LAZY启动型
    fun coroutine02() {
        val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
            Log.v("---zzq---", "World!")
        }
        Log.v("---zzq---", "Hello,")
        job.start()
    }

    // 取消一个协程
    fun coroutine03() {
        // 默认自动start
        val job = GlobalScope.launch {
            delay(1000L)
            Log.v("---zzq---", "World!")
        }
        Log.v("---zzq---", "Hello,")
        job.cancel()
    }

    // join()等待协程执行完毕
    fun coroutine04() {
        /*
        挂起函数不可以在main函数中被调用，那么我们怎么调试呢？对了，就是使用runBlocking 函数！
        我们可以使用 runBlocking 函数，构建一个主协程，从而调试我们的协程代码。
        */
        /*
        最外层的runBlocking为最高级的协程 (一般为主协程), 其他协程如launch {} 因为层级较低能跑在runBlocking里。
        runBlocking的最大特点就是它的delay()可以阻塞当前的线程，和Thread.sleep()有着相同的效果。
        */
        /*
        runBlocking {}是创建一个新的协程同时阻塞当前线程，直到协程结束。这个不应该在协程中使用，主要是为main函数和测试设计的。
         */
        runBlocking {
            val job = GlobalScope.launch {
                delay(1000L)
                Log.v("---zzq---", "World!")
                delay(1000L)
            }
            Log.v("---zzq---", "Hello,")
            // Suspends the coroutine until this job is complete【挂起协程，直到此任务完成】
            job.join()
            delay(1000L)
            Log.v("---zzq---", "Good,")
        }
    }

    // ------------------------------------------------------------------
    // ------------------------------------------------------------------


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