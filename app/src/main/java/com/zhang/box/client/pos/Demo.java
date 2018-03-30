package com.zhang.box.client.pos;

import android_serialport_api.SerialPort;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Demo {

    private static List<String> commList = null;   //保存可用端口号
    private static SerialPort serialPort = null;   //保存串口对象

    private static int posPort = -1;

    //初始化方法
    public static void main(String[] args) throws InterruptedException {
//        commList = SerialTool.findPort();
//        if (commList == null || commList.size() < 1) {
//            System.out.println("没有搜索到有效串口！");
//        } else {
//            for (int i = 0; i < commList.size(); i++) {
//                if (posPort == -1) {
//                    //遍历可用端口 查询是否是POS机
//                    //串口名称
//                    String commName = commList.get(i);
//                    //波特率
//                    String bpsStr = "9600";
//                    //检查串口名称是否获取正确
//                    if (commName == null || commName.equals("")) {
//                        System.out.println("没有搜索到有效串口！");
//                    } else {
//
//                        //串口名、波特率均获取正确时
//                        int bps = Integer.parseInt(bpsStr);
//                        try {
//                            //获取指定端口名及波特率的串口对象
//                            serialPort = SerialTool.openPort(commName, bps);
//                            SerialTool.addListener(serialPort, new SerialListener(i));
//                        } catch (UnsupportedCommOperationException | PortInUseException | NoSuchPortException | TooManyListenersException e1) {
//                            e1.printStackTrace();
//                        }

                        //发送数据
//                        String message = "020034313030310001043030320010323031383031313831343233333230313030340001310313";
//                        try {
//                            System.out.println("正准备向" + "发送测试数据...");
//                            SerialTool.sendToPort(serialPort, hex2byte(message));
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//
//                }
//                Thread.sleep(2000);
//            }



            //测试发送数据
//            send(1,1);
//        }
    }

    /**
     *
     * @param type
     * @param money
     */
    public static void send(int type, int money) {

        PosRequest pr = new PosRequest();

        TLVBody body1 = new TLVBody();
        body1.setTitle(strTo16("001"));
        body1.setContent(longTo16(type, 2));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        TLVBody body2 = new TLVBody();
        body2.setTitle(strTo16("002"));
        body2.setContent(strTo16(sdf.format(new Date()) + (10 + (int) (Math.random() * 90))));

        TLVBody body3 = new TLVBody();
        body3.setTitle(strTo16("004"));
        body3.setContent(strTo16(money + ""));

        List<TLVBody> tlvList = new ArrayList<>();
        tlvList.add(body1);
        tlvList.add(body2);
        tlvList.add(body3);

        pr.setTlvBody(tlvList);

        try {
            SerialTool.sendToPort(hex2byte(pr.toHex().toUpperCase(Locale.SIMPLIFIED_CHINESE)));
        } catch (IOException e) {

            e.printStackTrace();
        }
    }


//    public static class SerialListener implements SerialPortEventListener {
//
//        /**
//         * 处理监控到的串口事件
//         */
//        private int i;
//
//        public SerialListener(int i) {
//            this.i = i;
//        }
//
//        public void serialEvent(SerialPortEvent serialPortEvent) {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            switch (serialPortEvent.getEventType()) {
//                case SerialPortEvent.BI: // 10 通讯中断
////                    JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
//                    break;
//                case SerialPortEvent.OE: // 7 溢位（溢出）错误
//                    break;
//                case SerialPortEvent.FE: // 9 帧错误
//                    break;
//                case SerialPortEvent.PE: // 8 奇偶校验错误
//                    break;
//                case SerialPortEvent.CD: // 6 载波检测
//                    break;
//                case SerialPortEvent.CTS: // 3 清除待发送数据
//                    break;
//                case SerialPortEvent.DSR: // 4 待发送数据准备好了
//                    break;
//                case SerialPortEvent.RI: // 5 振铃指示
//                    break;
//                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
//                    break;
//                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
//                    byte[] data;
//                    try {
//                        data = SerialTool.readFromPort(serialPort);
//                        String responseData = printHexString(data);
//                        responseData = responseData.replace(" ", "");
//
//                        if (responseData.equals("020007323030310001040302")) {
//                            System.out.println("收到POS响应，已找到POS机端口");
//                            posPort = i;
//                            return;
//                        }
//
//                        System.out.println("响应加密：" + responseData);
//                        PosRequest posRequest = PosRequest.decRequest(responseData);
//                        String srt3 = posRequest.toString();
//                        System.out.println("响应解密：" + srt3);
//
//                        for (int i = 0; i < posRequest.getTlvBody().size(); i++) {
//                            TLVBody thisTLV = posRequest.getTlvBody().get(i);
//                            if (thisTLV.getTitle().equals("002")) {
//                            }
//                            if (thisTLV.getTitle().equals("039")) {
//                                if (thisTLV.getContent().equals("00")) {
//                                    //成功
//                                } else {
//                                    //失败
//                                }
//                            }
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//
//    }

    public static String printHexString(byte[] b) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sbf.append(hex.toUpperCase() + " ");
        }
        return sbf.toString().trim();
    }

    /**
     * 字符串转16进制
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {

        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "");
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }

    public static String strTo16(String s) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str.append(s4);
        }
        return str.toString().trim();
    }

    public static String longTo16(long num, int length) {
        StringBuffer str = new StringBuffer();
        String result = Long.toHexString(num);
        str.append(result);
        for (int i = 0; i < length - result.length(); i++) {
            str.insert(0, "0");
        }
        return str.toString();
    }
}
