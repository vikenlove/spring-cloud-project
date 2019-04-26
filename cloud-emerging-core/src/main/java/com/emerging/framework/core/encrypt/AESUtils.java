package com.emerging.framework.core.encrypt;



import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;



/**
 * aes加解密
 * 
 * @author llz
 *
 */
public class AESUtils {

	/**
	 * 
	 * 原始密钥+账号=S1；MD5(S1)=M1；M1.Left（16）=SS
	 * 
	 * @param key
	 * @param garble
	 * @return
	 */
	public static String getKeyByKey(String key, String garble) {
		String newKey = key + garble;
		newKey = Md5Util.MD5(newKey);
		return newKey.substring(0, 16);
	}

	/**
	 * 加密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @param garble
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc, String sKey, String garble,String jmFlag) throws Exception {
		//如果jmFlag为空直接返回，否则加密
		if(!StringUtils.isEmpty(jmFlag)) {
			// 处理密钥
			sKey = getKeyByKey(sKey, garble);
			// 判断Key是否正确
			// if (sKey == null) {
			// return null;
			// }
			// 判断Key是否为16位
			// if (sKey.length() != 16) {
			// return null;
			// }
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = sSrc.getBytes("utf-8");
			// encrypted = DataInterface.compressBytes(encrypted);
			encrypted = cipher.doFinal(encrypted);
			return Base64.getBase64(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		}else {
			return sSrc;
		}
	}

	/**
	 * 解密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @param garble
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc, String sKey, String garble) throws Exception {
		sKey = getKeyByKey(sKey, garble);
		// System.out.println("sKey=" + sKey);
		// 判断Key是否正确
		// if (sKey == null) {
		// return null;
		// }
		// 判断Key是否为16位
		// if (sKey.length() != 16) {
		// return null;
		// }
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] dncrypted = Base64.getFromBase64Byte(sSrc);// 先用base64解密
		byte[] original = cipher.doFinal(dncrypted);
		// original = DataInterface.decompressBytes(original);
		// 原来编码格式为"GBK"，现在改为"utf-8"
		return new String(original, "utf-8");
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		// String cKey =
		// "246747421564763642534886559237341108649172882697537721012022063613415981986142070830461023097638459";
		String cKey = "394244345901082288003919818203836632246929337687092261476346356733287511144654298915712539164029428";
		// 需要加密的字串
		String cSrc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MSGROOT><USERNAME>zxtcsywy</USERNAME><USERPWD>DBA32A0020E855013DACF14FEC412472</USERPWD><VERSION>1.0.1604.1</VERSION></MSGROOT>";
		// 加密
		String enString = AESUtils.Encrypt(cSrc, cKey, "zxtcsywy","");
		// System.out.println("加密后的字串是：" + enString);
		// enString =
		// "bRaW5uvFJ2Cj0SSBHnGSbC+gTZqPNZXdzTBBOmLONoJ4YYKAr0m/PpCG3ml2mHDNTaP4I874mCNhrySgXeA93/Nk2HrJgM9m1/9SgTJO9cm0DSZoKV+/+xaqJNibV66QZ3FWiwl9pRxwUroUC/lZ4uxlmbP3WbyLuoE6ofDj2J2GXag9GZlg+4dJIN/4uhm//rh/r6LZnMYQtSZIRdkRN3W3XpP+mPzg7wV9QQb33rGAOiPEyaBKj9iNE7qbfTGoGfDiz6PQZkIXcQ8WzAn6F9khblbjFEWeDI4dBzyQN556RPDE9i34uPJhc4zY403GQh6t7jQBWD87CeFi2bsLCutBsvW/R+M6qq2QQkj1RcmLy2ogJZ2pr+43HZhFSBf17usa9NKU0hERAvRrALfg0RTI7XLN8f1eJx3xj4owLLKPMNFClbDPD5F1fdOG1zPmHluaAbJKIDJ7aaZaWLiASz1LtAE659GRxyNWZWyKafo+s+YmgWoghwXEjac5jMOsMBS9haCybBp8AQON8yh7dnQ+fSeWuijHujoahxFmNvF5xVBgHBCz2KttGhi9npKbfESBxHVbzrA34sJIEXCUWVZz95pHaB4gwAJc5ReT1ai5Rtdmmhghu7JpcfMg9EdcxxZV29LgQJatTJvJq5/z17XxZx4I1OkhmQx/p3ifiPYY2FetJeTXYZF7Bm2Il846nAYh/oI1+nhbiKH1sJzoJFP1oSD7L7R85RFPJwNzRZ0+nhsGMgxG9UJl7JpHTwBnYG0xRSHQLbau7aevJWTYgmlH+1fAXOIlYgpXGB/jAmhvwQU45IvKZ8qeJpK1hjYgEkEJOSLNlFZC8yd9TOrBLRjHPhWt2Aw1CbiFJe+3qdECYl+tLpP8JyJWLM6QKeU6CEUrzngix7APazofvjHHG/y/BnwWXNaTjhKzva1dTzZb5TGEsCdTUkx0/YqIVlJ1Fji8Lh5o73S3zih0Q72ug0BNeUx5jYo3T06I1NHVFUviyG0vAX3nV0vGUyCPfZ3jovEfJzRFYTijLeGdNbZuYzYFOJzpK5hRl3qLthrt/1Etl9BT0l1PXsYnzKioChVI8yExZF0HJ2kNvNcRgSxjTPbsCU+1d3VFOZRm5SLJgqg=";
		enString = "zJsC1y352iA5F2TjOq0kahDt5YKqh8oIXRrRqQ4u6CbwA3BSy1xJJbelea08ubYLYO/kGYfQDdSFl2ftsmmZVgkvk+kZv+i6HDxe7WLE/rviwTC0L/KRhTHx5q35T2WRDPlMGJI7baRrAG32NTJuTaHckR3fz9K1pr/7BOEGeUgGG1kreBzvf8i7CGRZ94nxNIHLSe30Qish2+gp6Etzer7+p51Y+vf08NRB2/jBHrTcFfh5J+fBqOmBHRoCwlnjE87zdUEoYwSf+ycLRP7tloANnhFdi2SzS9J0c0PiAdlCDCVvh9TccHpmYWv0lAJAKV3EuMYSDBNg18cj1FcDO6nTTgxkxZLMt9+NK0rjdhtAS4TU96OO9etZkfGGS0IU2iOFKC8p/5dEdtc9Kw+dkQnDpuFRppvEieBwzXyMcfXpNMqdC/Wu5L46TLFLElXYV0sgk+EoLH97r+qgCpy7OTedG4F5lYlR+87JXfxU4MDwVq7cUwB5arZLIoHL6qnm4seNHO/xmjKrnT0Ck14XOZkc8HhvIf/GMLw0kRF4zSVw0PGYeYKmWzqQ1X8D/unf7StWMLd3TElXfBf2RKfs5fbc7GkofwXyq/cbnW5P8Ot471AKSHpMsOT0Hl/o8W4zCx7ibR72ntg8LhJqSPJva58ROHsiiMUR/LEgr9xkhzc+5Ord8pawMirCx/VXrt+7WiqFtwdS2A9c7YaTZgzBLgbRJRuZEJ3nKCJqVNABdfs9/A3u576Sy/qIHTx3ey+nzIK5+cPIRaufGhVSYaRwV2eEEeHPpQ3nex3z52utrGj4ViZODjMev89az1rgG3kgVhfpC5qj8Uv0Ex+n3hxInIBrh+ML6f5FMxYxCZ1eMVA8D/DLXnXaEfs4nqg7/kAfhNBB1N2RsUOmBjy43Hco/3SHlk2+NMYtuHZmgvdKadZrnHjVLb29/1n9ywo4r5w1UIhOFVrcYwQFahZeiTBZyamY5hQThtx+GUzKZ4c8hiwhXf8IcZlE2eplkEg3VuQBPvTdI3H1BnA8r+DRKw/RY8tl2ukC0RwYDRqHzQnDO3x2N3o/0775jmjqQeMRulPxmxAOwPfTDIbjmTl5pTdrXZl1KQqAeh5Pl5Q46xtRi2s=";
		byte[] bytes = Base64.getFromBase64Byte(enString);

		FileUtils.writeByteArrayToFile(new File("D:\\logs\\1.1.txt"), bytes);

		// 解密
		String DeString = AESUtils.Decrypt(enString, cKey, "zxtcsywy");
		System.out.println("解密后的字串是：" + DeString);
	}
}
