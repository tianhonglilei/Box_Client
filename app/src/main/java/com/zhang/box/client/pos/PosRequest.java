package com.zhang.box.client.pos;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求类
 */
public class PosRequest {

    /**
     * 起始符
     * 1字节 固定值：02
     */
    private String STX = "02";
    /**
     * LEN
     * TYPE+tlbBody的长度 10进制 4位
     * 如11长度 传 0011
     */
    private String LEN;
    /**
     * 报文类型
     * 1:请求 2:应答
     * 这里固定为1 十六进制为31
     */
    private String TYPE = "31";
    /**
     * 交易所需元素
     */
    private List<TLVBody> tlvBody;
    /**
     * 终止符
     * 1字节 固定03
     */
    private String ETX = "03";
    /**
     * 纵向冗余校验码
     */
    private String LRC;

    public String getSTX() {
        return STX;
    }

    public void setSTX(String STX) {
        this.STX = STX;
    }

    public String getLEN() {
        return LEN;
    }

    public void setLEN(String LEN) {
        this.LEN = LEN;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public List<TLVBody> getTlvBody() {
        return tlvBody;
    }

    public void setTlvBody(List<TLVBody> tlvBody) {
        this.tlvBody = tlvBody;
    }

    public String getETX() {
        return ETX;
    }

    public void setETX(String ETX) {
        this.ETX = ETX;
    }

    public String getLRC() {
        return LRC;
    }

    public void setLRC(String LRC) {
        this.LRC = LRC;
    }

    /**
     * 计算校验码
     */
    public void setLRC() {
        StringBuffer sb = new StringBuffer();
        sb.append(TYPE);
        int length = TYPE.length() / 2;
        for (int i = 0; i < tlvBody.size(); i++) {
            sb.append(tlvBody.get(i).toHex());
            length += tlvBody.get(i).getContent().length() / 2 + tlvBody.get(i).getLength().length() / 2 + tlvBody.get(i).getTitle().length() / 2;
        }
        String lengthStr = Test.strLength(length + "", 4);
//        String lengthStr = Test.longTo16(length, 4);
//        System.out.println("lengthStr:" + lengthStr);
        sb.insert(0, lengthStr);
        sb.append(ETX);

//        System.out.println(sb.toString());

//        System.out.println(Test.strLength(checkcode_0007(sb.toString()), 2));
        this.LRC = Test.strLength(checkcode_0007(sb.toString()), 2);
    }

    /**
     * 计算LRC
     *
     * @param para 传入LEN+TYPE+BODY+ETX
     * @return LRC
     */
    public static String checkcode_0007(String para) {
        int length = para.length() / 2;
        String[] dateArr = new String[length];

        for (int i = 0; i < length; i++) {
            dateArr[i] = para.substring(i * 2, i * 2 + 2);
        }
        String code = "00";
        for (int i = 0; i < dateArr.length; i++) {
            code = xor(code, dateArr[i]);
        }
        return code;
    }

    private static String xor(String strHex_X, String strHex_Y) {
        //将x、y转成二进制形式
        String anotherBinary = Integer.toBinaryString(Integer.valueOf(strHex_X, 16));
        String thisBinary = Integer.toBinaryString(Integer.valueOf(strHex_Y, 16));
        String result = "";
        //判断是否为8位二进制，否则左补零
        if (anotherBinary.length() != 8) {
            for (int i = anotherBinary.length(); i < 8; i++) {
                anotherBinary = "0" + anotherBinary;
            }
        }
        if (thisBinary.length() != 8) {
            for (int i = thisBinary.length(); i < 8; i++) {
                thisBinary = "0" + thisBinary;
            }
        }
        //异或运算
        for (int i = 0; i < anotherBinary.length(); i++) {
            //如果相同位置数相同，则补0，否则补1
            if (thisBinary.charAt(i) == anotherBinary.charAt(i))
                result += "0";
            else {
                result += "1";
            }
        }
        return Integer.toHexString(Integer.parseInt(result, 2));
    }

    @Override
    public String toString() {
        return "PosRequest{" +
                "STX='" + STX + '\'' +
                ", LEN='" + LEN + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", tlvBody=" + tlvBody +
                ", ETX='" + ETX + '\'' +
                ", LRC='" + LRC + '\'' +
                '}';
    }

    /**
     * 计算请求数据
     *
     * @return 完整请求数据
     */
    public String toHex() {
        setLRC();
        StringBuffer sb = new StringBuffer();
        sb.append(STX);
//        sb.append(LEN);
        sb.append(TYPE);
        int length = TYPE.length() / 2;
        for (int i = 0; i < tlvBody.size(); i++) {
            sb.append(tlvBody.get(i).toHex());
            length += tlvBody.get(i).getContent().length() / 2 + tlvBody.get(i).getLength().length() / 2 + tlvBody.get(i).getTitle().length() / 2;
        }
        String lengthStr = Test.strLength(length + "", 4);
//        System.out.println(length);
//        String lengthStr = Test.longTo16(length, 4);
//        System.out.println(lengthStr);
        //注意插入到起始符之后 和计算LRC有区别
        sb.insert(STX.length(), lengthStr);
        sb.append(ETX);
        sb.append(LRC);
        return sb.toString();
    }

    /**
     * 解密响应数据
     *
     * @param data
     * @return
     */
    public static PosRequest decRequest(String data) {
        //先去空格
        data = data.replace(" ", "");
        PosRequest posRequest = new PosRequest();
        //按指定格式截取数据
        posRequest.setSTX(data.substring(0, 2));
        posRequest.setLEN(data.substring(2, 6));
        posRequest.setTYPE(data.substring(6, 8));

        int length = Integer.parseInt(posRequest.getLEN());
        length = length * 2 + 10;

        data = data.substring(0, length);

        //数据初始位置


        //数据初始位置
        int index = 8;
        //结束符位置
        int ETXposition = data.length() - 4;

        List<TLVBody> bodyList = new ArrayList<>();

        while (true) {
            //判断内容是否全部读取完成
            if (index == ETXposition) {
                break;
            }
            TLVBody body = new TLVBody();
            //读取标题 然后16进制->String
            body.setTitle(Test.hexStringToString(data.substring(index, index + 6)));
            index += 6;
            //读取数据长度 不解析
            String len = data.substring(index, index + 4);
            body.setLength(data.substring(index, index + 4));
            index += 4;
            //读取标题 001为交易类型 交易类型的数据不解析
            if (body.getTitle().equals("001")) {
                body.setContent(data.substring(index, index + Integer.parseInt(body.getLength(), 16) * 2));
            } else if (body.getTitle().equals("040")) {
                try {
                    //040为应答结果 解析后 再改为GB2312格式
                    body.setContent(new String(Test.hexStringToBytes(data.substring(index, index + Integer.parseInt(body.getLength(), 16) * 2)), "gb2312"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                //其他的为默认 16进制->String 解析
                body.setContent(Test.hexStringToString(data.substring(index, index + Integer.parseInt(body.getLength(), 16) * 2)));
            }
            //由于Integer.parseInt(Integer x)好像是引用传递 所以保存一份备用变量 然后赋值
            body.setLength(len);
            index += Integer.parseInt(body.getLength(), 16) * 2;
            bodyList.add(body);
        }
        posRequest.setTlvBody(bodyList);
        //按照指定格式读取剩下的数据
        posRequest.setETX(data.substring(ETXposition, ETXposition + 2));
        posRequest.setLRC(data.substring(ETXposition + 2, data.length()));
        return posRequest;
    }
}
