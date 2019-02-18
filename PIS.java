import java.io.*;

public class PIS {
    public static void main(String args[]) {
        try {
            String pathname = "wiki_data_samples.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line;
            String[] text = new String[99999999];
            line = br.readLine();
            text[0] = line;
            int i = 0;
            int total = 0;
            int count, match, rate;
            while (line != null) {
                line = br.readLine();
                text[++i] = line;
            }
            try {
                int s = 0;
                String pathname2 = "sample.txt";
                File filename2 = new File(pathname2);
                InputStreamReader reader2 = new InputStreamReader(new FileInputStream(filename2));
                BufferedReader br2 = new BufferedReader(reader2);
                String line2 = "";
                String[] sample = new String[512];
                line2 = br2.readLine();
                sample[0] = line2;
                int j = 0;
                while (line2 != null) {
                    line2 = br2.readLine();
                    sample[++j] = line2;
                }
                for (int a = 0; a < 1024; a++) {
                    s++;
                    if (sample[a] == null) {
                        break;
                    }
                    count = sample[a].length() - 49;
                    match = 0;
                    if (sample[a].length() >= 50) {
                        char[] chsample = sample[a].toCharArray();
                        for (int x = 0; x < count; x++) {
                            char temp[] = new char[50];
                            System.arraycopy(chsample, x, temp, 0, 50);
                            int pref[] = prefix(temp);
                            for (int y = 0; y < text.length; y++) {
                                if (matcher(text[y], temp, pref) != -1) {
                                    match++;
                                    break;
                                }
                            }
                        }
                    }
                    rate = 100 * match / count;
                    System.out.println("sentence" + a + ":" + rate + "%");
                    total += rate;
                }
                total = total / s;
                System.out.println("total" + ":" + total + "%");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[] prefix(char[] sample) {
        int m = sample.length;
        int pre[] = new int[m];
        pre[0] = -1;
        int i = 0;
        for (int j = 1; j < m; j++) {
            i = pre[j - 1];
            while ((i >= 0) && (sample[j] != sample[i + 1])) {
                i = pre[i];
            }
            if (sample[j] == sample[i + 1]) {
                pre[j] = i + 1;
            } else {
                pre[j] = -1;
            }
        }
        return pre;
    }

    private static int matcher(String txt, char[] sample, int[] pref) {
        if (txt == null) {
            return -1;
        }
        int n = txt.length();
        char[] chtext = txt.toCharArray();
        int j = 0;
        for (int i = 0; i < n; ) {
            if (chtext[i] == sample[j]) {
                if (j == sample.length - 1) {
                    return i - sample.length + 1;
                }
                j++;
                i++;
            } else if (j == 0) {
                i++;
            } else {
                j = pref[j - 1] + 1;
            }
        }
        return -1;
    }
}
