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

        Thread threadPalindrom = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                // строка является палиндромом?
                String reversedString = new StringBuffer(texts[i]).reverse().toString();
                if (texts[i].equals(reversedString)) {
                    Main.counterIncrement(texts[i].length());
                }
            }
        });
        Thread threadOneSimbol = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                // строка содержит только один уникальный символ?
                char first = texts[i].charAt(0);
                int j;
                for (j = 1; j < texts[i].length(); j++) {
                    if (texts[i].charAt(j) != first) {
                        break;
                    }
                }
                if (j == texts[i].length()) { // вся строка пройдена - проверка прошла успешно
                    Main.counterIncrement(texts[i].length());
                }
            }
        });
        Thread threadOrdered = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                // символы идут в не убывающем порядке?
                char prev = texts[i].charAt(0);
                int j;
                for (j = 1; j < texts[i].length(); j++) {
                    char current = texts[i].charAt(j);
                    if (current < prev) {
                        break;
                    }
                    prev = current;
                }
                if (j == texts[i].length()) { // вся строка пройдена - проверка прошла успешно
                    Main.counterIncrement(texts[i].length());
                }
            }
        });

        threadPalindrom.start();
        threadOneSimbol.start();
        threadOrdered.start();

        threadPalindrom.join();
        threadOneSimbol.join();
        threadOrdered.join();

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

    public static void counterIncrement(int len) {
        switch (len) {
            case 3:
                counter3.incrementAndGet();
                break;
            case 4:
                counter4.incrementAndGet();
                break;
            case 5:
                counter5.incrementAndGet();
                break;
        }
    }
}
