package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
*
*@ClassName: FastDFSUtil
*@Description
*@Author yang
*@Date 2020/9/22
*@Time 17:59
 * 　文件上传
 * 　文件删除
 * 　文件下载
 * 　文件信息获取
 * 　Storage信息获取
 *   Tracker信息获取
*/
public class FastDFSUtil {
    /**
     * 加载Tracker链接信息
     */
    static{
        try {
            //查找classpath下的文件路径
            String filename = new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker链接信息
            ClientGlobal.init(filename);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @param fastDFSFile 上传的文件信息封装
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws IOException, MyException {
        //附加参数
        NameValuePair[] meta_list=new NameValuePair[1];
        meta_list[0]=new NameValuePair("author",fastDFSFile.getAuthor());
        //创建一个Tracker访问的客户端对象TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient访问TrackerServer服务,获取连接信息
        TrackerServer trackerServer= trackerClient.getConnection();
        //通过TrackerServer的连接信息可以获取Storage的连接信息,创建StorageClient对象存储Storage的连接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        /**
         * 通过StorageClient访问Storage,实现文件上传,并且获取文件上传后的存储信息
         * 参数:1.上传文件的字节数组
         * 2.文件的扩展名  jpg
         * 3.附加参数  比如:作者
         *
         * uploads[]
         *   uploads[0]:文件上传所存储的Storage的组名
         *   uploads[1]:文件存储到Storage上的文件名
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName :文件的组名
     * @param remoteFileName : 文件的存储路径名字
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws  Exception{
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer获取Storage信息,创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //获取文件信息
        return storageClient.get_file_info(groupName,remoteFileName);
    }

    /**
     *  文件下载
     * @param groupName :文件的组名
     * @param remoteFileName : 文件的存储路径名字
     * @return
     */
    public static InputStream downloadFile(String groupName, String remoteFileName) throws IOException, MyException {
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer获取Storage信息,创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);
        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 删除文件
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName)throws  Exception{
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer获取Storage信息,创建StorageClient对象存储Storage信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        //删除文件
        storageClient.delete_file(groupName,remoteFileName);

    }

    /**
     * 获取Storage信息
     * @throws Exception
     */
    public static StorageServer getStorages() throws  Exception{
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取Storage的IP和端口信息
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName)throws  Exception{
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }


    /**
     * 获取Tracker信息
     * @return
     * @throws Exception
     */
    public static String getTrackerInfo()throws  Exception{
        //创建一个TrackerClient对象,通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer的连接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //Tracker的IP,HTTP端口
        String ip = trackerServer.getInetSocketAddress().getHostString();
        int trackers_http_port = ClientGlobal.getG_tracker_http_port();
        String url = "http://" + ip + ":" + trackers_http_port;

        return url;
    }
    public static void main(String[] args) throws Exception {
        FileInfo file = getFile("group1", "M00/00/00/wKjThF9p3x-Ae0ajAAHnw6T_mTQ070.jpg");
        System.out.println("测试"+file.getSourceIpAddr());

        //定义一个缓冲区
//        InputStream is = downloadFile("group1", "M00/00/00/wKjThF9p3x-Ae0ajAAHnw6T_mTQ070.jpg");
//        //将文件写入到本地磁盘
//        FileOutputStream os = new FileOutputStream("D:/1.jpg");
//        //定义一个缓冲区
//        byte[] buffer = new byte[1024];
//        while (is.read(buffer)!=-1){
//            os.write(buffer);
//        }
//        os.flush();
//        os.close();
//        is.close();

        //文件删除
       // deleteFile("group1", "M00/00/00/wKjThF9p3x-Ae0ajAAHnw6T_mTQ070.jpg");

        //获取Storage信息
//        StorageServer storageServer = getStorages();
//        System.out.println(storageServer.getStorePathIndex());
//        System.out.println(storageServer.getInetSocketAddress().getHostName());//ip信息

        //获取Storage组的IP和端口信息
        ServerInfo[] group1s = getServerInfo("group1", "M00/00/00/wKjThF9p3x-Ae0ajAAHnw6T_mTQ070.jpg");
        for (ServerInfo group: group1s){
            System.out.println(group.getIpAddr());
            System.out.println(group.getPort());
        }
    }
}
