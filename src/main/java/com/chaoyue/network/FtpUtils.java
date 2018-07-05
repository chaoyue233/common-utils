package com.chaoyue.network;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FtpUtils {
    public static final String FTP_BASE_URL = "http://chaoyue.test.com";

    //ftp服务器地址
    private static final String address = "";
    //ftp服务器端口号默认为21
    private static final Integer port = 21;
    //ftp登录账号
    private static final String username = "";
    //ftp登录密码
    private static final String password = "";

    public static void uploadFile(String path, String filename, InputStream input) {
        FTPClient ftpClient = new FTPClient();
        try {
            int reply;
            ftpClient.connect(address, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(username, password);//登录
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
            ftpClient.changeWorkingDirectory(path);
            ftpClient.storeFile(filename, input);

            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            log.error("upload file to ftp error ", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ignored) {
                }
            }
        }
    }
}