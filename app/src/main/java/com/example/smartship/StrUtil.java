package com.example.smartship;



import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }
    //判断是否是有效 ip
    public static boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
    //拼接正确url地址
    public static String setRightUrl(String url) {
        String tempStr = "";
        if (!StrUtil.isEmpty(url))
            if (url.startsWith("http")) {
                tempStr = url;
            } else {
                tempStr = "http://" + url;
            }
        return tempStr;
    }

    //检查正确的密码格式
    public static boolean CheckPassword(String input) {
        //6-16位，字母、数字、字符
        String regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,16}$";
        return input.matches(regStr);
    }

    //手机号码格式验证  规则：1开头；11位纯数字
    public static boolean checkPhone(String string) {
        String regex = "^1\\d{10}$";
        return string.matches(regex);
    }

    //将0,1，2，3转换成00，01，02，03
    public static String toStringTwo(int val) {
        String temp;
        if (val < 10) {
            temp = "0" + val;
        } else {
            temp = val + "";
        }
        return temp;
    }

    //是否是空字节
    public static boolean isEmptyByte(byte[] result) {
        if (result != null) {
            return (result.length == 0);
        }
        return false;
    }

    //判断两个字符串是否相等,如果有一个字符串为空，返回false
    public static boolean isEqualsTwoStrs(String string1,String string2) {
        if (isEmpty(string1)){
            return false;
        }
        if (isEmpty(string2)){
            return false;
        }
        if (!string1.equals(string2)){
            return false;
        }
        return true;
    }


    /**
     * 将byte[]数组转化为String类型
     *
     * @param arg    需要转换的byte[]数组
     * @param length 需要转换的数组长度
     * @return 转换后的String队形
     */
    public static String byteToStr(byte[] arg, int length) {
        String result = "";
        if (arg != null) {
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])) + " ";
            }
            return result;
        }
        return "";
    }

    /**
     * 将String转化为byte[]数组
     *
     * @param arg 需要转换的String对象
     * @return 转换后的byte[]数组
     */
    public static byte[] strToByteArray(String arg) {
        if (arg != null) {
            /* 1.先去除String中的' '，然后将String转换为char数组 */
            char[] NewArray = new char[1000];
            char[] array = arg.toCharArray();
            int length = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] != ' ') {
                    NewArray[length] = array[i];
                    length++;
                }
            }
            /* 将char数组中的值转成一个实际的十进制数组 */
            int EvenLength = (length % 2 == 0) ? length : length + 1;
            if (EvenLength != 0) {
                int[] data = new int[EvenLength];
                data[EvenLength - 1] = 0;
                for (int i = 0; i < length; i++) {
                    if (NewArray[i] >= '0' && NewArray[i] <= '9') {
                        data[i] = NewArray[i] - '0';
                    } else if (NewArray[i] >= 'a' && NewArray[i] <= 'f') {
                        data[i] = NewArray[i] - 'a' + 10;
                    } else if (NewArray[i] >= 'A' && NewArray[i] <= 'F') {
                        data[i] = NewArray[i] - 'A' + 10;
                    }
                }
                /* 将 每个char的值每两个组成一个16进制数据 */
                byte[] byteArray = new byte[EvenLength / 2];
                for (int i = 0; i < EvenLength / 2; i++) {
                    byteArray[i] = (byte) (data[i * 2] * 16 + data[i * 2 + 1]);
                }
                return byteArray;
            }
        }
        return new byte[]{};
    }

    /**
     * 将int转化为byte[]数组
     * <p>
     * 将int转为低字节在后，高字节在前的byte数组
     * 例如：0 -> 00 00;1 -> 00 01;
     */
    public static byte[] intToByteArray(int value) {
        byte[] src = new byte[2];
//        src[0] = (byte) ((value >> 24) & 0xFF);
//        src[1] = (byte) ((value >> 16) & 0xFF);
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int转化为byte[]数组
     * <p>
     * 将int转为低字节在后，高字节在前的byte数组
     * 例如：0 ->  00;1 ->  01;
     */
    public static byte[] intToByteArray2(int value) {
        byte[] src = new byte[1];
//        src[0] = (byte) ((value >> 24) & 0xFF);
//        src[1] = (byte) ((value >> 16) & 0xFF);
//        src[0] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将16进制String转化为int
     * <p>
     * 将高字节在前转为int，低字节在后的byte数组(与intToByteArray想对应)
     * 例如：0000 -> 0 ; 0001 -> 1;
     */
    public static int str16ToInt(String str) {
        return Integer.parseInt(str, 16);
    }

    /**
     * @param data1
     * @param data2
     * @return data1 与 data2拼接的结果
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    /**
     * CRC16计算产生校验码
     *
     * @param data 需要校验的数据
     * @return 校验码
     */
    public static String Make_CRC(byte[] data) {
        byte[] buf = new byte[data.length];// 存储需要产生校验码的数据
        for (int i = 0; i < data.length; i++) {
            buf[i] = data[i];
        }
        int len = buf.length;
        int crc = 0xFFFF;//16位
        for (int pos = 0; pos < len; pos++) {
            if (buf[pos] < 0) {
                crc ^= (int) buf[pos] + 256; // XOR byte into least sig. byte of
                // crc
            } else {
                crc ^= (int) buf[pos]; // XOR byte into least sig. byte of crc
            }
            for (int i = 8; i != 0; i--) { // Loop over each bit
                if ((crc & 0x0001) != 0) { // If the LSB is set
                    crc >>= 1; // Shift right and XOR 0xA001
                    crc ^= 0xA001;
                } else
                    // Else LSB is not set
                    crc >>= 1; // Just shift right
            }
        }
        String c = Integer.toHexString(crc);
        if (c.length() == 4) {
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 3) {
            c = "0" + c;
            c = c.substring(2, 4) + c.substring(0, 2);
        } else if (c.length() == 2) {
            c = "0" + c.substring(1, 2) + "0" + c.substring(0, 1);
        } else {
            c = "00 00";
        }
        return c;
    }


}



