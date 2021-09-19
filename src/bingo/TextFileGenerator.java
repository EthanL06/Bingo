package bingo;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextFileGenerator implements Runnable{

    private final String filePath;
    private final String schedule;
    private final String winnerSchedule;

    public TextFileGenerator(String filePath, String schedule, String winnerSchedule) {
        this.filePath = filePath;
        this.schedule = schedule;
        this.winnerSchedule = winnerSchedule;
    }

    /**
     * Creates the schedule.txt file and the winners.txt file
     */
    @Override
    public void run() {
        createScheduleFile();
        createWinnerScheduleFile();
    }

    /**
     * Creates the schedule.txt file
     */
    private void createScheduleFile() {
        try {
            File scheduleFile = new File(filePath, "schedule.txt");
//            FileWriter fw = new FileWriter(scheduleFile);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(scheduleFile), StandardCharsets.UTF_8);
            osw.write("SCHEDULE:\n" + schedule);
            osw.close();

            System.out.println("schedule.txt successfully created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the winners.txt file
     */
    private void createWinnerScheduleFile() {
        try {
            File scheduleFile = new File(filePath, "winners.txt");

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(scheduleFile), StandardCharsets.UTF_8);
            osw.write("WINNERS:\n" + winnerSchedule);
            osw.close();

            System.out.println("winners.txt successfully created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}