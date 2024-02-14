package com.xuecheng.media;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.Bytes;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 测试大文件上传的方法
 * @date 2023/2/17 11:55
 */
public class BigFileTest {

    //分块测试
    @Test
    public void testChunk() throws IOException {
        File sourceFile = new File("D:\\develop\\upload\\01.mp4");
        //分块文件存储路径
        String chunkFilePath = "D:\\develop\\upload\\chunk\\";
        //分块文件大小
        int chunkSize = 1024*1024*50;
        //分块文件个数
        int chunkNum = (int) Math.ceil(sourceFile.length()*1.0/chunkSize);
        //使用流读写文件
        RandomAccessFile raf_r = new RandomAccessFile(sourceFile,"r");
        byte[] bytes = new byte[1024];
        for (int i = 0; i < chunkNum; i++) {
            File chunkFile = new File(chunkFilePath+i);
            //分块文件写入流
            RandomAccessFile raf_rw = new RandomAccessFile(chunkFile,"rw");
            int len = -1;

            while ((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
                if(chunkFile.length()>=chunkSize){
                    break;
                }
            }
            raf_rw.close();
        }
        raf_r.close();

    }
    @Test
    public void testMerge() throws IOException {
        //分块文件目录
        File chunkFolder = new File("D:\\develop\\upload\\chunk\\");
        //源文件
        File sourseFile = new File("D:\\develop\\upload\\01.mp4");
        //合并后的文件
        File mergeFile = new File("D:\\develop\\upload\\01_2.mp4");
        //取到文件夹里面的所有文件并放到数组中
        File[] files = chunkFolder.listFiles();
        //将文件按名字排序
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return Integer.parseInt(o1.getName())-Integer.parseInt(o2.getName());
            }
        });

        byte[] bytes = new byte[1024];
        RandomAccessFile raf_rw = new RandomAccessFile(mergeFile,"rw");
        for ( File file:fileList){
            RandomAccessFile raf_r = new RandomAccessFile(file,"r");
            int len = -1;
            while((len=raf_r.read(bytes))!=-1){
                raf_rw.write(bytes,0,len);
            }
            raf_r.close();
        }
        raf_rw.close();

        //校验是否一致
        FileInputStream fileInputStream_merge = new FileInputStream(mergeFile);
        FileInputStream fileInputStream_source = new FileInputStream(sourseFile);
        String md5_merge = DigestUtils.md5Hex(fileInputStream_merge);
        String md5_source = DigestUtils.md5Hex(fileInputStream_source);
        if(md5_merge.equals(md5_source)){
            System.out.println("文件合并成功");
        }
    }
}
