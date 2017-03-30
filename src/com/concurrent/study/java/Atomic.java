package com.concurrent.study.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by7/3/23.
 * atomic使用的是cas，
 * cas在Unsafe类，
 * 共有三种cas方法
 * public final native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5)
 * public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5)
 * public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6)
 * native说明是调用系统底层算法...
 * UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapObject(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jobject e_h, jobject x_h))
 UnsafeWrapper("Unsafe_CompareAndSwapObject");
 oop x = JNIHandles::resolve(x_h);
 oop e = JNIHandles::resolve(e_h);
 oop p = JNIHandles::resolve(obj);
 HeapWord* addr = (HeapWord *)index_oop_from_field_offset_long(p, offset);
 oop res = oopDesc::atomic_compare_exchange_oop(x, addr, e, true);
 jboolean success  = (res == e);
 if (success)
 update_barrier_set((void*)addr, x);
 return success;
 UNSAFE_END

 UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapInt(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jint e, jint x))
 UnsafeWrapper("Unsafe_CompareAndSwapInt");
 oop p = JNIHandles::resolve(obj);
 jint* addr = (jint *) index_oop_from_field_offset_long(p, offset);
 return (jint)(Atomic::cmpxchg(x, addr, e)) == e;
 UNSAFE_END

 UNSAFE_ENTRY(jboolean, Unsafe_CompareAndSwapLong(JNIEnv *env, jobject unsafe, jobject obj, jlong offset, jlong e, jlong x))
 UnsafeWrapper("Unsafe_CompareAndSwapLong");
 Handle p (THREAD, JNIHandles::resolve(obj));
 jlong* addr = (jlong*)(index_oop_from_field_offset_long(p(), offset));
 if (VM_Version::supports_cx8())
 return (jlong)(Atomic::cmpxchg(x, addr, e)) == e;
 else {
 jboolean success = false;
 ObjectLocker ol(p, THREAD);
 if (*addr == e) { *addr = x; success = true; }
 return success;
 }
 UNSAFE_END
 */
public class Atomic {
    private static AtomicInteger i;

    public static class doIncrease implements Runnable{

        private AtomicInteger integer;

        public doIncrease(AtomicInteger integer) {
            this.integer = integer;
        }

        @Override
        public void run() {
            integer.getAndIncrement();
        }
    }

    public static void main(String[] args){
        i = new AtomicInteger(0);
        Thread t1 = new Thread(new doIncrease(i));
        Thread t2 = new Thread(new doIncrease(i));
        Thread t3 = new Thread(new doIncrease(i));
        t1.start();
        t2.start();
        t3.start();
        assert i.get() == 3;
        System.out.println("success");
    }
}
