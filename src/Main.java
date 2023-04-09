import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Main {
    public static AtomicInteger counter3, counter4, counter5;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        counter3 = new AtomicInteger(0);
        counter4 = new AtomicInteger(0);
        counter5 = new AtomicInteger(0);

        Thread thread3 = new Thread(new Counter(texts, 3, counter3));
        Thread thread4 = new Thread(new Counter(texts, 4, counter4));
        Thread thread5 = new Thread(new Counter(texts, 5, counter5));

        thread3.start();
        thread4.start();
        thread5.start();

        thread3.join();
        thread4.join();
        thread5.join();

        System.out.println("Красивых слов с длиной 3: " + counter3);
        System.out.println("Красивых слов с длиной 4: " + counter4);
        System.out.println("Красивых слов с длиной 5: " + counter5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
