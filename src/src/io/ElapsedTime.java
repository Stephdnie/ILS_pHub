package src.io;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ElapsedTime {

    private ElapsedTime() {

    }

    public static long systemTime() {
        return System.nanoTime();
    }

    public static long calcElapsed(final long start, final long end) {
        return (end - start);
    }

    public static double calcSeconds(final double timeInNanoSeconds) {
        return TimeUnit.SECONDS.convert(Math.round(timeInNanoSeconds), TimeUnit.NANOSECONDS);
    }

    public static String calcElapsedHMS(final long start, final long end) {
        return ElapsedTime.calcHMS(end - start);
    }

    public static String calcHMS(final long timeInNanoSeconds) {

        long t = timeInNanoSeconds;
        long hours = TimeUnit.NANOSECONDS.toHours(t);
        t -= TimeUnit.HOURS.toNanos(hours);
        long minutes = TimeUnit.NANOSECONDS.toMinutes(t);
        t -= TimeUnit.MINUTES.toNanos(minutes);
        long seconds = TimeUnit.NANOSECONDS.toSeconds(t);
        t -= TimeUnit.SECONDS.toNanos(seconds);
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(t);
        t -= TimeUnit.MILLISECONDS.toNanos(milliseconds);
        long microseconds = TimeUnit.NANOSECONDS.toMicros(t);
        t -= TimeUnit.MICROSECONDS.toNanos(microseconds);
        long nanoseconds = TimeUnit.NANOSECONDS.toNanos(t);

        String r = "";
        if (hours > 0) {
            r += hours + "h";
        }
        if (minutes > 0) {
            r += " " + minutes + "m";
        }
        if (seconds > 0) {
            r += " " + seconds + "s";
        }
        if (milliseconds > 0) {
            r += " " + milliseconds + "ms";
        }
        if (microseconds > 0) {
            r += " " + microseconds + "us";
        }
        if (nanoseconds > 0) {
            r += " " + nanoseconds + "ns";
        }

        if (r.length() > 0) {
            if (r.charAt(0) == ' ') {
                r = r.substring(1, r.length());
            }
        }
        return r;
    }

    public static void doPause(final long timeInNanoSeconds) {
        try {
            Thread.sleep(TimeUnit.NANOSECONDS.toMillis(timeInNanoSeconds));
        } catch (InterruptedException ex) {
            Logger.getLogger(ElapsedTime.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
