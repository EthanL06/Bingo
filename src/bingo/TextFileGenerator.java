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

    @Override
    public void run() {
        createScheduleFile();
        createWinnerScheduleFile();
    }

    private void createScheduleFile() {
        try {
            File scheduleFile = new File(filePath, "schedule.txt");
//            FileWriter fw = new FileWriter(scheduleFile);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(scheduleFile), StandardCharsets.UTF_8);
            osw.write("SCHEDULE:\n" + schedule);
            osw.close();
//            PrintWriter pw = new PrintWriter(osw);
//
//            pw.println("SCHEDULE:\n");
//            pw.println(schedule);
//            pw.close();

            System.out.println("schedule.txt successfully created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createWinnerScheduleFile() {
        try {
            File scheduleFile = new File(filePath, "winners.txt");

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(scheduleFile), StandardCharsets.UTF_8);
            osw.write("WINNERS:\n" + winnerSchedule);
            osw.close();
//            FileWriter fw = new FileWriter(scheduleFile);
//            PrintWriter pw = new PrintWriter(fw);

//            pw.println("WINNERS:\n");
//            pw.println(winnerSchedule);
//            pw.close();

            System.out.println("winners.txt successfully created.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}