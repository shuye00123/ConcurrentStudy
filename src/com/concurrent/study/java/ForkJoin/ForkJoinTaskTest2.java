package com.concurrent.study.java.ForkJoin;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by shuye on 2016/12/25.
 */
public class ForkJoinTaskTest2 {
    public static void main(String[] args) {
        Integer count = new ForkJoinPool().invoke(new CountTask(Paths.get("E:/")));
        System.out.println("e盘文件数量："+count);
    }
    static class CountTask extends RecursiveTask<Integer> {
        private Path dir;

        public CountTask(Path dir) {
            this.dir = dir;
        }

        @Override
        protected Integer compute() {
            int count = 0;
            List<CountTask> subTask = new ArrayList<CountTask>();
            try {
                DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
                for (Path subPath:ds) {
                    if (Files.isDirectory(subPath, LinkOption.NOFOLLOW_LINKS))subTask.add(new CountTask(subPath));else count++;
                    if (!subTask.isEmpty()){
                        for (CountTask task: invokeAll(subTask)){
                            count+=task.join();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return count;
        }
    }
}
