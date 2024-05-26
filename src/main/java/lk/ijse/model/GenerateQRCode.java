package lk.ijse.model;

import java.nio.file.Paths;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class GenerateQRCode {

    public static void main(String[] args) throws Exception {

        String data = " BeeHiveId - H001 "+
                "\n Beehive - Type Medium " +
               "\n location - kalawana-north" +
                     "\n population - 3000 " +
                "\n Last InspectionDate - 2023-10-10 " +
                "\n Inspection Result - all good";
        String path = "E:\\qrcode\\hive.jpg";

        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, 500, 500);

        MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

    }

}