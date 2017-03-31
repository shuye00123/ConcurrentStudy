package com.concurrent.study.java.Lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by shuye on 2017/3/31.
 */
public class StampedLockTest {
    private StampedLock lock = new StampedLock();

    private Integer key = 0;

    private void writeLock(){
        long stamp = lock.writeLock();
        try {
            key = 1;
            System.out.printf("write lock is %s,lock's stamp is %d,key value is %d", lock.isWriteLocked(), stamp, key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlockWrite(stamp);
        }
    }

    private void readLock(){
        long stamp = lock.readLock();
        try {
            System.out.printf("read lock is %s,lock's stamp is %d,key value is %d", lock.isReadLocked(), stamp, key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock(stamp);
        }
    }

    private void optimisticReadLock(){
        long stamp = lock.tryOptimisticRead();
    }

}
