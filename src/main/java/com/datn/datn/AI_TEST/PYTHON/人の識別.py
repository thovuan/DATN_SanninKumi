import cv2

# Khởi tạo bộ phát hiện người với HOG và SVM
hog = cv2.HOGDescriptor()
hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector())

# Mở video (0 cho webcam hoặc đường dẫn đến file video)
cap = cv2.VideoCapture(0)

while cap.isOpened():
    ret, frame = cap.read()
    if not ret:
        break

    # Chuyển đổi khung hình sang thang độ xám
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Phát hiện người trong khung hình
    boxes, weights = hog.detectMultiScale(gray, winStride=(8,8))

    # Vẽ hình chữ nhật quanh các đối tượng được phát hiện
    for (x, y, w, h) in boxes:
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

    # Hiển thị khung hình với các đối tượng được phát hiện
    cv2.imshow('Human Detection', frame)

    # Nhấn 'q' để thoát
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
