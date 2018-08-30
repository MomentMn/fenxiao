package com.hansan.fenxiao.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hansan.fenxiao.constant.WechatConstant;
import com.hansan.fenxiao.service.IAccessTokenService;
import com.hansan.fenxiao.service.IQrCodeService;
import com.hansan.fenxiao.service.IWeChatService;
import com.hansan.fenxiao.utils.OSinfo;
import com.hansan.fenxiao.utils.PropertyUtil;
import com.hansan.fenxiao.utils.QRHttpUtils;
import com.hansan.fenxiao.utils.QiNiuUtil;
import com.hansan.fenxiao.weixin.entity.WeChatUserInfo;
import com.hansan.fenxiao.weixin.entity.WeiXinQrCode;

@Service
public class QrCodeServiceImpl implements IQrCodeService {

	@Resource(name = "accessTokenService")
	private IAccessTokenService accessTokenService;
	@Resource(name = "weChatService")
	private IWeChatService<WeChatUserInfo> weChatService;
	private String qiniukey;

	/**
	 * @param oneLevelUserId
	 * @param twoLevelUserId
	 * @param userId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @description:根据一二级关系 生成二维码，并且更新微信表数据，且更新关系表User中的superior
	 * @author:王涛
	 * @createTime:2018年6月6日 上午10:42:30
	 */
	@Override
	public String getQrCodeUrl(int userId, int productID, int... ids) throws Exception {
		int size = ids.length;
		String url = "";
		switch (size) {
			case 0:
				url = this.createQrCode(String.valueOf(System.currentTimeMillis()), WechatConstant.MYURL + userId);
				break;
			case 1:

				String scene = String.valueOf(ids[0]);
				String redirectUrl = WechatConstant.URL + "?paramt=" + URLEncoder.encode(WechatConstant.ORDERURL + productID + "&param=" + scene, "utf-8");
				url = this.createQrCode(String.valueOf(System.currentTimeMillis()), redirectUrl);
				break;
			case 2:
				String scen = String.valueOf(ids[0]) + "~" + String.valueOf(ids[1]);
				String url2 = WechatConstant.URL + "?paramt=" + URLEncoder.encode(WechatConstant.ORDERURL + productID + "&param=" + scen, "utf-8");
				url = this.createQrCode(String.valueOf(System.currentTimeMillis()), url2);

				break;
			default:
				throw new Exception("等级不得超过三级");
		}
		return url;
	}

	/**
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 * @description:type 1 表示谷歌生成二维码
	 * @author:王涛
	 * @createTime:2018年6月6日 上午11:40:06
	 */
	public String createQrCode(String name, String scenes) {
		System.out.println("tiaozhuan : " + scenes);
		int width = 300;//图片的宽度
		int height = 300;//图片的高度
		String format = "png";//图片的格式
		String contents = scenes; //图片的内容,即网址链接
		//定义二维码的参数
		HashMap map = new HashMap();
		map.put(EncodeHintType.CHARACTER_SET, "utf-8");//字符集
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//纠错等级LMQH,一般用M
		map.put(EncodeHintType.MARGIN, 2);//边距
		//生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, map);
			String path="";
			if(OSinfo.isLinux()){
				path = PropertyUtil.getProperty("linux.local.file") + name + ".png";
			}
			if(OSinfo.isWindows()){
				path=PropertyUtil.getProperty("windows.local.file") + name + ".png";
			}
			//String path = PropertyUtil.getProperty("linux.local.file") + name + ".png";
			Path file = new File(path).toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
			qiniukey = QiNiuUtil.saveRemoteFile1(path);
		} catch (Exception e) {

		}
		String url = PropertyUtil.getProperty("qiniu.outIp") + qiniukey;
		System.out.println(url);
		return url;
	}

	/**
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @description:生成永久二维码
	 * @author:王涛
	 * @createTime:2018年6月4日 上午9:57:04
	 */
	public String createQrCode(String scenes) throws UnsupportedEncodingException {
		String accessToken = accessTokenService.getAccessToken(WechatConstant.APPID, WechatConstant.APPSECRET);
		String sceneStr = WechatConstant.CODE;
		sceneStr = scenes;
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("access_token", accessToken);
		Map<String, String> intMap = new HashMap<String, String>();
		intMap.put("scene_str", sceneStr);
		Map<String, Map<String, String>> mapMap = new HashMap<String, Map<String, String>>();
		mapMap.put("scene", intMap);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("action_name", WechatConstant.QR_LIMIT_STR_SCENE);
		paramsMap.put("action_info", mapMap);
		JSONObject jsonObject = JSONObject.fromObject(paramsMap);
		String data = jsonObject.toString();
		data = QRHttpUtils.HttpsDefaultExecute(WechatConstant.REQUEST_POST, WechatConstant.CREATE_TICKET_PATH, params, data);
		WeiXinQrCode qr = null;
		JSONObject qrObject = JSONObject.fromObject(data);
		qr = (WeiXinQrCode) JSONObject.toBean(qrObject, WeiXinQrCode.class);
		String path = weChatService.showQrcode(qr.getTicket());
		String str = URLEncoder.encode(path.substring(path.indexOf("?") + 8), "utf-8");
		String requestPath = path.substring(0, path.indexOf("?") + 8) + str;
		return requestPath;
	}

}
