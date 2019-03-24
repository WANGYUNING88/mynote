/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * DateTime: 2018/8/3 22:17
 **/
package com.wip.api;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 七牛文件上传API
 */
public class QiNiuCloudService {

    /**
     * 密钥凭证
     */
    private static final String ACCESS_KEY
            = "yea2PHKutyBW1cAwxFNFnCT4EZwkhkER35em1u_W";
    private static final String SECRET_KEY
            = "_6utPnNdm8sYR_e09yYffx1OUVRnJ54vekJI3pFv";
    /**
     * 仓库
     */
    private static final String BUCKET = "my_note";

    /**
     * 七牛外网访问地址
     */
    public static final String QINIU_UPLOAD_SITE = "pov3hab3s.bkt.clouddn.com";
            //"idv093d.qiniudns.com";

    /**
     * 上传文件到七牛公共方法
     * @param file          上传的文件
     * @param fileName      文件名
     * @return
     */
    public static String upload(MultipartFile file, String fileName) {

        // 构造一个带指定Zone对象的配置类
        // 华东 Zone.zone0()
        // 华北 Zone.zone1()
        // 华南 Zone.zone2()
        // 北美 Zone.zoneNa0()
        Configuration cfg = new Configuration(Zone.zone1());
        // 其它参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);
        try {
            Response response = null;
            response = uploadManager.put(file.getInputStream(), fileName, upToken,null,null);

            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return  putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());
            } catch (QiniuException ex2) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
