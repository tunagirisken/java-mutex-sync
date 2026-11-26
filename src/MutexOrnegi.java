import java.util.concurrent.locks.ReentrantLock;

// POSIX pthreads ile yazilan klasik mutex orneginin Java karsiligi.
// Iki thread ayni paylasilan degiskeni (x) kilit altinda arttirir.
public class MutexOrnegi {

    private static final ReentrantLock mutex = new ReentrantLock(); // pthread_mutex_t
    private static int x = 0; // paylasilan degisken

    private static void threadIsi(String ad) {
        int okunan;
        mutex.lock(); // pthread_mutex_lock
        try {
            x = x + 1;   // kritik bolge
            okunan = x;  // degeri kilit altindayken oku
        } finally {
            mutex.unlock(); // pthread_mutex_unlock
        }
        // Kilit disinda x'i dogrudan okumak yaris durumuna yol acar,
        // bu yuzden kilit icinde okunan degeri yazdiriyoruz.
        System.out.printf("X = %d in Thread %s%n", okunan, ad);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> threadIsi("A"), "A");
        Thread b = new Thread(() -> threadIsi("B"), "B");

        // pthread_create
        a.start();
        b.start();

        // pthread_join
        a.join();
        b.join();

        System.out.println("Son deger: X = " + x);
    }
}
