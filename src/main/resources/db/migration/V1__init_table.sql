-- Tạo bảng roles
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL COMMENT 'Tên vai trò (ADMIN, USER, ...)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật'
);

-- Tạo bảng users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL COMMENT 'Tên',
    last_name VARCHAR(50) NOT NULL COMMENT 'Họ',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT 'Email',
    phone_number VARCHAR(15) COMMENT 'Số điện thoại',
    password VARCHAR(255) NOT NULL COMMENT 'Mật khẩu (hashed)',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Trạng thái hoạt động',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật'
);

-- Tạo bảng admins
CREATE TABLE admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL COMMENT 'Tên',
    last_name VARCHAR(50) NOT NULL COMMENT 'Họ',
    email VARCHAR(100) UNIQUE NOT NULL COMMENT 'Email',
    phone_number VARCHAR(15) COMMENT 'Số điện thoại',
    password VARCHAR(255) NOT NULL COMMENT 'Mật khẩu (hashed)',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Trạng thái hoạt động',
    role_id INT NOT NULL COMMENT 'Liên kết đến bảng roles',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tạo bảng verification_codes
CREATE TABLE verification_codes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL COMMENT 'Liên kết với người dùng',
    verification_code VARCHAR(10) NOT NULL COMMENT 'Mã xác minh (OTP)',
    expires_at DATETIME NOT NULL COMMENT 'Thời gian hết hạn của mã',
    is_verified BOOLEAN DEFAULT FALSE COMMENT 'Trạng thái xác minh',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng sos_alerts
CREATE TABLE sos_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'Người gửi tín hiệu SOS',
    message TEXT NOT NULL COMMENT 'Thông điệp SOS',
    latitude DOUBLE NOT NULL COMMENT 'Vĩ độ tại thời điểm gửi',
    longitude DOUBLE NOT NULL COMMENT 'Kinh độ tại thời điểm gửi',
    time_announcement DATETIME NOT NULL COMMENT 'Thời gian thông báo',
    is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'Trạng thái thông báo',
    number_alert INT NOT NULL DEFAULT 0 COMMENT 'Số lần thông báo',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian gửi tín hiệu',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời gian cập nhật',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng setting
CREATE TABLE setting (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(255) NOT NULL COMMENT 'Tên cài đặt',
    `value` VARCHAR(255) NOT NULL COMMENT 'Giá trị cài đặt',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật'
);

-- Tạo bảng travel_histories
CREATE TABLE travel_histories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'Liên kết đến người dùng',
    latitude DOUBLE NOT NULL COMMENT 'Vĩ độ điểm bắt đầu',
    longitude DOUBLE NOT NULL COMMENT 'Kinh độ điểm bắt đầu',
    sos_alert_id INT COMMENT 'Liên kết đến tín hiệu SOS',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (sos_alert_id) REFERENCES sos_alerts(id) ON DELETE SET NULL
);

-- Tạo bảng friends
CREATE TABLE friends (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'Người dùng',
    friend_id INT NOT NULL COMMENT 'ID bạn bè',
    relation VARCHAR(50) NOT NULL COMMENT 'Quan hệ (ví dụ: Bố, Mẹ, Bạn thân)',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng personal_sos_alerts
CREATE TABLE personal_sos_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sos_alert_id INT NOT NULL COMMENT 'Liên kết đến SOS Alert',
    friend_id INT NOT NULL COMMENT 'Liên kết đến bạn bè',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày tạo',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Ngày cập nhật',
    FOREIGN KEY (sos_alert_id) REFERENCES sos_alerts(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE
);
