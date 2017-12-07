package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.avm.serialport_142.MainHandler;
import com.avm.serialport_142.service.CommService;
import com.avm.serialport_142.service.CommServiceThread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import box.lilei.box_client.box.BoxAction;
import box.lilei.box_client.box.BoxParams;
import box.lilei.box_client.client.biz.AdBiz;
import box.lilei.box_client.client.biz.impl.AdBizImpl;
import box.lilei.box_client.client.model.jsonmodel.AdJsonInfo;
import box.lilei.box_client.client.okhttp.CommonOkHttpClient;
import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDataListener;
import box.lilei.box_client.client.okhttp.listener.DisposeDownloadDataListener;
import box.lilei.box_client.client.okhttp.listener.DisposeSizeListener;
import box.lilei.box_client.client.okhttp.request.CommonRequest;
import box.lilei.box_client.client.presenter.ActivePresenter;
import box.lilei.box_client.client.view.ActiveView;
import box.lilei.box_client.contants.Constants;
import box.lilei.box_client.db.AdBean;
import box.lilei.box_client.db.GoodsBean;
import box.lilei.box_client.db.PercentBean;
import box.lilei.box_client.db.RoadBean;
import box.lilei.box_client.db.biz.AdBeanService;
import box.lilei.box_client.db.biz.GoodsBeanService;
import box.lilei.box_client.db.biz.PercentBeanService;
import box.lilei.box_client.db.biz.RoadBeanService;
import box.lilei.box_client.util.FileUtils;
import box.lilei.box_client.util.JsonListUtil;
import box.lilei.box_client.util.SharedPreferencesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lilei on 2017/11/7.
 * 激活页面处理业务
 */

public class ActivePresenterImpl implements ActivePresenter {

    private Context mContext;
    private ActiveView activeView;

    //广告类处理
    private AdBiz adBiz;
    //数据库处理
    private AdBeanService adBeanService;
    private RoadBeanService roadBeanService;
    private GoodsBeanService goodsBeanService;
    private PercentBeanService percentBeanService;

    int count = 0;
    int successNum = 0;
    int failNum = 0;
    //所有文件名称
    List<String> allFileName;
    //已经存在的文件名称
    List<String> existFileNames;
    //需要下载的文件名称
    List<String> downloadName;


    public ActivePresenterImpl(Context mContext, ActiveView activeView) {
        this.mContext = mContext;
        this.activeView = activeView;
    }

    /**
     * 加载所有数据
     *
     * @param imei
     */
    @Override
    public void loadAllDataFromUrl(String imei) {
        //初始化数据库对象
        adBiz = new AdBizImpl();
        adBeanService = new AdBeanService(mContext, AdBean.class);
        roadBeanService = new RoadBeanService(mContext, RoadBean.class);
        goodsBeanService = new GoodsBeanService(mContext, GoodsBean.class);
        percentBeanService = new PercentBeanService(mContext, PercentBean.class);


        StringBuilder url = new StringBuilder(Constants.BANNER_AD_URL);
        url.append("&machineid=" + imei);
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url.toString(), null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObject) {
                //清空数据
                adBeanService.clearBean();
                roadBeanService.clearBean();
                goodsBeanService.clearBean();
                percentBeanService.clearBean();
                parseHeartJson(responseObject);
            }

            @Override
            public void onFail(Object errorObject) {
                ((Exception) errorObject).printStackTrace();
                activeView.exitApplication();
            }
        }));
    }


    //文件下载主路径
    String urlString = "";

    //解析心跳数据
    private void parseHeartJson(Object responseObject) {
        JSONObject mainJson = JSONObject.parseObject(responseObject.toString());
        JSONArray adJsonArray = null;
        JSONArray roadJsonArray = null;
        JSONArray goodsJsonArray = null;
        JSONObject percentJsonObject = null;
        if (mainJson != null) {
            try {
                urlString = mainJson.getString("img_url");
                adJsonArray = mainJson.getJSONArray("adv");
                roadJsonArray = mainJson.getJSONArray("huodao");
                goodsJsonArray = mainJson.getJSONArray("machine_goods");
                percentJsonObject = mainJson.getJSONObject("product_info");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        allFileName = new ArrayList<>();
        downloadName = new ArrayList<>();
        existFileNames = new ArrayList<>();
        saveAdBean(adJsonArray);
        saveGoodsAndPercent(goodsJsonArray, percentJsonObject);
        saveRoadBean(roadJsonArray);
        //检查文件是否存在，并下载文件
        if (!FileUtils.exist(Constants.DEMO_FILE_PATH)) {
            FileUtils.creatSDDir("Box_client");
        }
        for (String name : allFileName) {
            if (!FileUtils.isFileExists(new File(Constants.DEMO_FILE_PATH), name)) {
                downloadName.add(name);
            } else {
                existFileNames.add(name);
            }

        }
//        for (String name:
//             existFileNames) {
//            File file = new File(Constants.DEMO_FILE_PATH+"/"+name);
//            Long size = FileUtils.getFileSize(file);
//            compareFileSize(size,name);
//        }
        startDownload();
    }

    private void startDownload() {
        count = downloadName.size();
        if (count == 0) {
            activeView.hiddenDialog();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    saveBoxSetting();
                }
            },13000);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    activeView.skipToADBannerActivity();
                }
            }, 15000);
            return;
        }
        activeView.showDialog("下载中...");
        for (String name :
                downloadName) {
            downloadFile(name);
        }
    }

    /**
     * 处理商品和营养成分
     *
     * @param goodsJsonArray
     * @param percentJsonObject
     */
    private void saveGoodsAndPercent(JSONArray goodsJsonArray, JSONObject percentJsonObject) {
        if (goodsJsonArray != null) {
            List<GoodsBean> goodsBeanList = JsonListUtil.getListByArray(GoodsBean.class, goodsJsonArray.toString());
            List<GoodsBean> goodsBeanList1 = new ArrayList<>();
            if (goodsBeanList != null) {
                for (GoodsBean goodsBean :
                        goodsBeanList) {
                    if (goodsBean.getId() != null) {
                        JSONArray jsonArray = percentJsonObject.getJSONArray(goodsBean.getId().toString());
                        if (jsonArray != null) {
                            List<PercentBean> percentList = JsonListUtil.getListByArray(PercentBean.class, jsonArray.toString());
                            if (percentBeanService.getPercentBeanListByGoodsId(goodsBean.getId()).size() == 0) {
                                percentBeanService.saveBeanList(percentList);
                            }
                            goodsBeanList1.add(goodsBean);
                        }
                        allFileName.add(goodsBean.getBig_img());
                        allFileName.add(goodsBean.getNo_pro_img());
                        allFileName.add(goodsBean.getSmall_img());
                    }
                }
                goodsBeanService.saveBeanList(goodsBeanList1);
            }
        }
    }

    /**
     * 处理货道信息
     *
     * @param roadJsonArray
     */
    private void saveRoadBean(JSONArray roadJsonArray) {
        if (roadJsonArray != null) {
            List<RoadBean> roadBeanList = JsonListUtil.getListByArray(RoadBean.class, roadJsonArray.toString());
            for (int i = 0; i < roadBeanList.size(); i++) {
                roadBeanList.get(i).setId(new Long(i + 1));
            }
            roadBeanService.saveBeanList(roadBeanList);
        }
    }

    /**
     * 处理广告
     *
     * @param adJsonArray
     */
    private void saveAdBean(JSONArray adJsonArray) {
        if (adJsonArray != null) {
            List<AdJsonInfo> adJsonList = JsonListUtil.getListByArray(AdJsonInfo.class, adJsonArray.toString());
            List<AdBean> adBeanList = adBiz.parseAdJsonListToAdBean(adJsonList);
            for (AdBean bean :
                    adBeanList) {
                bean.setAdUrl(urlString);
                String videoName = bean.getAdVideoFile();
                if (videoName != null && !videoName.equals("")) {
                    allFileName.add(videoName);
                }
                allFileName.add(bean.getAdImgFile());
            }

            adBeanService.saveBeanList(adBeanList);
        }
    }

    //下载文件
    private void downloadFile(final String fileName) {
        if (fileName != null && !fileName.equals("")) {
            CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(urlString + fileName, null), new DisposeDataHandle(new DisposeDownloadDataListener() {
                @Override
                public void onProgress(int progrss) {

                }

                @Override
                public void onSuccess(Object responseObject) {
                    activeView.changeDownloadProgress(count, ++successNum, failNum);
                }

                @Override
                public void onFail(Object errorObject) {
                    if (errorObject instanceof Exception) {
                        ((Exception) errorObject).printStackTrace();
                    }
                    activeView.changeDownloadProgress(count, successNum, ++failNum);
                }
            }, Constants.DEMO_FILE_PATH + "/" + fileName));
        }
    }

    int dif = 0;
    int same = 0;
    int other = 0;

    private void compareFileSize(Long fileSize, final String fileName) {
        if (fileName != null && !fileName.equals("")) {
            CommonOkHttpClient.compareSize(CommonRequest.createGetRequest(urlString + fileName, null), new DisposeDataHandle(new DisposeSizeListener() {
                @Override
                public void difference() {
                    ++dif;
                    downloadName.add(fileName);
                    if (dif + same + other == existFileNames.size()) {
                        startDownload();
                    }
                }

                @Override
                public void same() {
                    ++same;
                    if (dif + same + other == existFileNames.size()) {
                        startDownload();
                    }
                    Log.w("ActivePresenterImpl", "same:" + same);
                }

                @Override
                public void onSuccess(Object responseObject) {
                    ++other;
                }

                @Override
                public void onFail(Object errorObject) {
                    ++other;
                    if (errorObject instanceof Exception) {
                        ((Exception) errorObject).printStackTrace();
                    }
                    if (dif + same + other == existFileNames.size()) {
                        startDownload();
                    }
                }
            }, fileSize));

        }
    }

    String box_id;

    @Override
    public void getBoxId() {
        box_id = SharedPreferencesUtil.getString(mContext, BoxParams.BOX_ID);
        if (box_id != null && !box_id.equals("") && !box_id.equals("00000000")) {
            String code = SharedPreferencesUtil.getString(mContext, BoxParams.ACTIVE_CODE);
//            SharedPreferencesUtil.putString(mContext, BoxParams.ACTIVE_CODE, code);
            if (!code.equals("")) {
                activeBox(code);
            } else {
                activeView.showActiveLayout();
            }
        } else if (box_id.equals("00000000")) {
            getBoxIdFromBox();
        } else {
            activeView.showActiveLayout();
        }
    }

    @Override
    public void activeBox(final String code) {
        CommService commService = new CommService() {

            @Override
            public void result(int res) {
                if (res == CommService.ERROR_SYSTEM_SERVICE) {
                    Toast.makeText(mContext, "数据配置或者网络调用错误", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_SYSTEM_TIME) {
                    Toast.makeText(mContext, "系统时间不正确", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_CODE_NO_EXIST) {
                    Toast.makeText(mContext, "激活码不存在", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_SYSTEM_CODE) {
                    Toast.makeText(mContext, "激活码校验失败", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_ACTIVATE_CHECK) {
                    Toast.makeText(mContext, "激活校验失败", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_CODE_USED) {
                    Toast.makeText(mContext, "激活码已被使用", Toast.LENGTH_SHORT).show();
                } else if (res == CommService.ERROR_OTHER) {
                    Toast.makeText(mContext, "其他错误", Toast.LENGTH_SHORT).show();
                } else if (res == CommServiceThread.ERROR_IO_PROBLEM) {
                    Toast.makeText(mContext, "串口打开IO出错", Toast.LENGTH_SHORT).show();
                } else if (res == CommServiceThread.ERROR_PERMISSION_REJECT) {
                    Toast.makeText(mContext, "没有打开串口的权限", Toast.LENGTH_SHORT).show();
                } else if (res == CommServiceThread.ERROR_NOT_CONFIG) {
                    Toast.makeText(mContext, "系统中没有配置要打开的串口", Toast.LENGTH_SHORT).show();
                } else if (res == CommServiceThread.ERROR_UNKNOWN) {
                    Toast.makeText(mContext, "串口打开时的未知错误", Toast.LENGTH_SHORT).show();
                } else if (res == CommServiceThread.COMM_SERVICE_START) {
                    Toast.makeText(mContext, "激活启动成功", Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(box_id) && !box_id.equals("00000000")) {
                        activeView.hiddenActiveLayout(true);
                    } else {
                        getBoxIdFromBox();
                    }
                } else {
                    Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
                }
            }
        };
        commService.connect(mContext, code, 1);
    }

    @Override
    public void saveBoxSetting() {
        BoxParams boxParams = new BoxParams();
        if (!boxParams.getAvmSetInfo().equals("0")) {
            SharedPreferencesUtil.putString(mContext, BoxParams.LEFT_STATE, boxParams.getLeft_state());
            SharedPreferencesUtil.putString(mContext, BoxParams.RIGHT_STATE, boxParams.getRight_state());
            SharedPreferencesUtil.putString(mContext, BoxParams.LIGHT_TIME, boxParams.getStart_time() + boxParams.getEnd_time());
            SharedPreferencesUtil.putString(mContext, BoxParams.COLD_TEMP, boxParams.getCold_temp());
            SharedPreferencesUtil.putString(mContext, BoxParams.HOT_TEMP, boxParams.getHot_temp());
        }
    }

    private void getBoxIdFromBox() {
        while (true) {
            //获取机器号
            String box_id = BoxAction.getBoxId();
            if (!TextUtils.isEmpty(box_id) && !box_id.equals("00000000")) {
                SharedPreferencesUtil.putString(mContext, BoxParams.BOX_ID, box_id);
                Toast.makeText(mContext, box_id, Toast.LENGTH_SHORT).show();
                activeView.hiddenActiveLayout(true);
                break;
            } else {
                try {
                    Thread.sleep(500);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
