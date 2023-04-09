import java.util.concurrent.atomic.AtomicInteger;

public class Counter implements Runnable {
    private String[] texts;
    private int lengthForCount;
    private AtomicInteger counter;

    public Counter(String[] texts, int lengthForCount, AtomicInteger counter) {
        this.texts = texts;
        this.lengthForCount = lengthForCount;
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < texts.length; i++) {
            if (texts[i].length() == lengthForCount) {
                // строка является палиндромом?
                String reversedString = new StringBuffer(texts[i]).reverse().toString();
                if (texts[i].equals(reversedString)) {
                    counter.incrementAndGet();
                } else {
                    // символы идут в не убывающем порядке?
                    // (если строка содержит только один уникальный символ -
                    // это частный случай не убывающего порядка, поэтому эта проверка избыточна)
                    int j;
                    for (j = 1; j < lengthForCount - 1; j++) {
                        if (texts[i].charAt(j) > texts[i].charAt(j + 1)) {
                            break;
                        }
                    }
                    if (j == lengthForCount) { // вся строка пройдена - проверка прошла успешно
                        counter.incrementAndGet();
                    }
                }
            }
        }
    }
}
