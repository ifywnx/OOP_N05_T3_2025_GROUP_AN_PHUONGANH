import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.awt.Desktop;

public class KhachhangView {
    public void hienThi(KhachhangModel model) {
        try {
            String tenFile = "khachhang.html";

            String html = "<html><head><title>Thông tin khách hàng</title></head><body>" +
                    "<h1>Thông tin khách hàng</h1>" +
                    "<p><strong>Mã KH:</strong> " + model.getMaKhachhang() + "</p>" +
                    "<p><strong>Tên KH:</strong> " + model.getTenKhachHang() + "</p>" +
                    "<p><strong>Địa chỉ:</strong> " + model.getDiaChi() + "</p>" +
                    "<p><strong>Số điện thoại:</strong> " + model.getSoDienThoai() + "</p>" +
                    "</body></html>";

            FileWriter fw = new FileWriter(tenFile);
            fw.write(html);
            fw.close();

            File htmlFile = new File(tenFile);
            
            // Sử dụng Chrome để mở
            Desktop.getDesktop().browse(htmlFile.toURI());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



    

