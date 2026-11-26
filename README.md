# Java ile Mutex ve Thread Senkronizasyonu

POSIX pthreads ile yazılan klasik mutex örneğinin Java karşılığı.
İki thread, paylaşılan bir sayacı (`x`) `ReentrantLock` ile korunan
kritik bölge içinde artırır; böylece yarış durumu (race condition) engellenir.

## pthreads → Java eşlemesi
| pthreads (C) | Java |
|---|---|
| `pthread_mutex_t` | `java.util.concurrent.locks.ReentrantLock` |
| `pthread_mutex_lock` / `unlock` | `lock()` / `unlock()` (`try/finally` içinde) |
| `pthread_create` | `new Thread(...).start()` |
| `pthread_join` | `Thread.join()` |

## Gereksinimler
- JDK 8 veya üzeri (JDK 21 ile test edildi)

## Derleme ve çalıştırma
```bash
javac -d out src/*.java
java -cp out MutexOrnegi
```

Örnek çıktı (thread sırası her çalıştırmada değişebilir):
```
X = 1 in Thread A
X = 2 in Thread B
Son deger: X = 2
```

## Notlar
- `unlock()` her zaman `finally` bloğunda çağrılır; kritik bölgede hata olsa bile kilit serbest kalır.
- `x` değeri kilit altındayken yerel bir değişkene okunur ve o yazdırılır. Kilit dışında
  `x`'i okumak, diğer thread'in o anda değeri değiştirmesi nedeniyle tutarsız çıktıya yol açabilir.
