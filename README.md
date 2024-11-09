# Quản Lý Trang Trại Vật Nuôi

## Giới Thiệu

Đây là một hệ thống quản lý trang trại vật nuôi với hai giao diện chính:
- **Giao diện người quản lý**: Dành cho người quản lý trang trại để thực hiện các công việc như thêm, sửa, xóa, sắp xếp, thống kê vật nuôi và khách hàng.
- **Giao diện khách hàng**: Dành cho khách hàng để xem tình trạng vật nuôi khả dụng, mua vật nuôi và theo dõi lịch sử giao dịch.

## Các Tính Năng

### 1. **Vai Trò Người Quản Lý**
- **Quản lý vật nuôi**:
  - Thêm, sửa, xóa, cập nhật thông tin về vật nuôi trong trang trại.
  - Sắp xếp và thống kê các đợt nhập vật nuôi.
- **Quản lý khách hàng**:
  - Thống kê số lượng khách hàng đã đăng ký và các giao dịch.
  - Xem danh sách khách hàng và lịch sử giao dịch.

### 2. **Vai Trò Khách Hàng**
- **Khám phá vật nuôi**:
  - Xem các vật nuôi khả dụng trong trang trại.
  - Kiểm tra tình trạng vật nuôi (sẵn có hay đã bán hết).
- **Mua vật nuôi**:
  - Mua vật nuôi từ trang trại với các thông tin chi tiết.
- **Xem lịch sử mua hàng**:
  - Khách hàng có thể xem lại các vật nuôi đã mua trong quá khứ.

## Yêu Cầu Hệ Thống

- **Java**: Phiên bản 12 trở lên.
- **IDE**: NetBeans, IntelliJ IDEA, Eclipse, Visual Studio Code (nếu cần).
- **Maven**: Để quản lý các phụ thuộc và build dự án (nếu cần).
- **Hệ điều hành**: Hệ điều hành Windows, macOS hoặc Linux.

## Cài Đặt và Chạy Dự Án

### 1. **Cài Đặt Dự Án Qua IDE**
1. Mở thư mục gốc của dự án trong IDE yêu thích.
2. Cấu hình dự án với Maven (nếu cần).
3. Chạy dự án từ IDE.

### 2. **Cài Đặt Dự Án Không Cần IDE**
1. Tải file `pig-1.0-SNAPSHOT.jar` đã build từ thư mục `target`.
2. **Windows**:
   - Nhấp đúp vào file JAR để chạy ứng dụng.
3. **Mac/Linux**:
   - Mở terminal và chạy lệnh:
     ```bash
     java -jar pig-1.0-SNAPSHOT.jar
     ```

## Đăng Nhập

### 1. **Đăng Nhập với Vai Trò Người Quản Lý**
- **Username**: `admin`
- **Password**: `123654`

### 2. **Đăng Nhập với Vai Trò Khách Hàng**
- Chọn **Sign Up** để đăng ký tài khoản mới.
- Nhập **username** và **password**, sau đó sử dụng thông tin đó để đăng nhập.

## Hướng Dẫn Sử Dụng

Chi tiết về cách sử dụng giao diện người quản lý và khách hàng sẽ được trình bày trong báo cáo đính kèm. Báo cáo này cũng sẽ đề cập đến các lỗi không hợp lệ có thể gặp phải trong quá trình sử dụng hệ thống.

## Các Điểm Cần Cải Thiện

- **Giao diện người dùng**:
  - Giao diện chưa thực sự bắt mắt và thân thiện với người dùng. Cần cải thiện thiết kế UI trong các phiên bản tiếp theo.
- **Lịch sử mua hàng**:
  - Tính năng xem lại lịch sử mua hàng dành cho khách hàng hiện chưa có, dự kiến sẽ được bổ sung trong các phiên bản tương lai.
- **Bảo mật**:
  - Hệ thống đăng nhập hiện chỉ hỗ trợ xác thực cơ bản, cần cải thiện thêm bảo mật trong các phiên bản tiếp theo.

## Cập Nhật và Tính Năng Sắp Tới

Chúng tôi sẽ tiếp tục phát triển hệ thống với các tính năng mới:
- Cải tiến giao diện người dùng (UI).
- Cập nhật tính năng bảo mật.
- Bổ sung tính năng thống kê chi tiết hơn cho người quản lý.

## Cách Đóng Góp

Chúng tôi hoan nghênh các đóng góp từ cộng đồng. Nếu bạn muốn đóng góp vào dự án này, vui lòng thực hiện theo các bước dưới đây:

1. Fork dự án này.
2. Tạo một nhánh mới (`git checkout -b feature-name`).
3. Commit thay đổi của bạn (`git commit -am 'Add new feature'`).
4. Push nhánh của bạn (`git push origin feature-name`).
5. Tạo một pull request mô tả thay đổi của bạn.

## Giấy Phép

Dự án này được cấp phép theo Giấy phép MIT. Bạn có thể tham khảo chi tiết trong file LICENSE.
