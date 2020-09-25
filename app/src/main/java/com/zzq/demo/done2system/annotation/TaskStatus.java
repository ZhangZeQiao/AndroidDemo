package com.zzq.demo.done2system.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 使用 @IntDef 来限定常量不允许重复
// 还有，加了 @IntDef @StringDef 才能在使用时做这样的检查：调用方法时传入的参数的值一定要用这样的调用方式TaskStatus.XXX
@IntDef({
        TaskStatus.UN_KNOW,
        TaskStatus.UN_START,
        TaskStatus.PROGRESSING,
        TaskStatus.COMPLETED
})
/*@StringDef({
        TaskStatus.UN_KNOW,
        TaskStatus.UN_START,
        TaskStatus.PROGRESSING,
        TaskStatus.COMPLETED
})*/
@Retention(RetentionPolicy.SOURCE) // 告诉编译器，该注解是源代码级别的，生成 class 文件的时候这个注解就被编译器自动去掉了
public @interface TaskStatus {
    // 和接口成员一样，注解类的成员默认就是 public static final 修饰的
    int UN_KNOW = -1;
    int UN_START = 0;
    int PROGRESSING = 1;
    int COMPLETED = 2;

    /*String UN_KNOW = "-1";
    String UN_START = "0";
    String PROGRESSING = "1";
    String COMPLETED = "2";*/
}

   /* private void doTask(@TaskStatus int status) {
        switch (status) {
            case TaskStatus.UN_KNOW:
                break;
            case TaskStatus.UN_START:
                break;
            case TaskStatus.PROGRESSING:
                break;
            case TaskStatus.COMPLETED:
                break;
            default:
                break;
        }
    }*/
