import static org.junit.Assert.*;
import org.junit.Test;

/**
 * http://www.topcoder.com/stat?c=problem_statement&pm=9895
 * 
 * @author hps
 * 
 */
public class Embassy {

    public int visaApplication(int[] forms, int dayLength, int openTime) {
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < forms.length; ++i) {
            int time1 = syncVisa(forms, dayLength, openTime, i, 0);
            int time2 = syncVisa(forms, dayLength, openTime, i, openTime);
            time = Math.min(Math.min(time1, time2), time);
        }
        return time;
    }

    /**
     * Lösung ist unvollständig: es muss auch rückwärts gerechnet werden.
     */
    public int visaApplicationOld(int[] forms, int dayLength, int openTime) {
        int time = 0;
        for (int i = 1; i < forms.length; ++i) {
            time += forms[i];
            int daytime = time - (time / dayLength) * dayLength;
            if (daytime > openTime) {
                time = (time / dayLength + 1) * dayLength;
            }
        }
        return time + forms[0];
    }
    
    int dayStart(int time, int dayLength) {
        if (0 <= time) {
            return (time / dayLength) * dayLength;
        } else {
            return (time / dayLength) * dayLength - dayLength;
        }
    }
    
    /**
     * Das Ende der k-ten Aktion wird auf itime gesetzt.
     */
    private int syncVisa(int[] forms, int dayLength, int openTime, int k, int itime) {
        int end = itime;
        for (int i=k+1; i<forms.length; ++i) {
            end += forms[i];
            int dayTime = end - dayStart(end, dayLength);
            if(dayTime > openTime) {
                end = end + (dayLength - dayTime);
            }
        }
        int start = itime-forms[k];
        for (int i=k-1; 0 <= i; --i) {
            int dayTime = start - dayStart(start, dayLength);
            if(dayTime > openTime) {
                start = dayStart(start, dayLength) + openTime;
            }
            start = start - forms[i];
        }
        return end - start;
    }

    @Test
    public void test() {
        assertEquals(0,dayStart(0,24));
        assertEquals(0,dayStart(1,24));
        assertEquals(24,dayStart(25,24));
        assertEquals(-24,dayStart(-1,24));
        assertEquals(-48,dayStart(-25,24));
        assertEquals(12, visaApplication(new int[] { 4, 4, 4 }, 24, 8));
        assertEquals(28, visaApplication(new int[] { 4, 4, 4, 4 }, 24, 8));
        assertEquals(73, visaApplication(new int[] { 2, 2, 2, 2 }, 24, 1));
        assertEquals(16945, visaApplication(new int[] { 25, 500, 630, 2500, 1000, 350,
                22, 58, 100, 400, 500, 5000 }, 1440, 360));
    }
}

