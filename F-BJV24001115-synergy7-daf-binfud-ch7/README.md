
# BinarFud

Back End Java Challenge - Chapter 7


## Delivery requested

Tahap 1:
```bash
1. Buat Aplikasi Gateway
2. Buat Aplikasi Notifikasi-Service
3. Routing request ke notifikasi-service dan binarfood-service di gateway
4. Terapkan Socket.io di notifiksi service
5. Buat kafka producer yang mengirim message apapun ke notifikasi service.
```

Tahap 2:
```bash
1. Migrasikan kirim email otp ke notifiksi service.
2. Ubah message yang dikirim oleh binarfood service berupa data(email, otp, jenis)
3. Consume data(email, otp, jenis) kafka di notifikasi service
```

## What's in this app?

```bash
1. Gateway yang merouting ke binarfud-service dan notification-service
2. Penggunaan Kafka di binarfud-service yang mengirim message producer sendOrderCompletedEvent ke 
   notifikasi-service dan diterima di message consumer listenOrderCompleted.
```
