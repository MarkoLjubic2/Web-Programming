package http;

import app.Quote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class SecondServer {

    public static final List<Quote> quotesOfTheDay = List.of(
            new Quote("Steve Jobs","Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma which is living with the results of other people's thinking."),
            new Quote("Jeff Bezos", "If you're competitor-focused, you have to wait until there is a competitor doing something. Being customer-focused allows you to be more pioneering."),
            new Quote("Larry Ellison", "Taking care of your employees is extremely important and very, very visible."),
            new Quote("Mark Zuckerberg", "The biggest risk is not taking any risk In a world that's changing really quickly, the only strategy that is guaranteed to fail, is not taking risks."),
            new Quote("Marissa Mayer", "You can't have everything you want, but you can have the things that really matter to you."),
            new Quote("John Chambers", "If you don't innovate fast, disrupt your industry, disrupt yourself, you'll be left behind."),
            new Quote("Alan Kay", "The best way to predict the future is to invent it.")
    );

    public static Quote quoteOfTheDay;
    public static LocalDate lastQuoteDate;

    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(9002);
            while (true) {
                Socket socket = ss.accept();
                new Thread(new SecondServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Quote getQuoteOfTheDay() {
        if (quoteOfTheDay == null || !LocalDate.now().equals(lastQuoteDate)) {
            quoteOfTheDay = getRandomQuote();
            lastQuoteDate = LocalDate.now();
        }
        return quoteOfTheDay;
    }

    private static Quote getRandomQuote() {
        int randomIndex = new Random().nextInt(quotesOfTheDay.size());
        return quotesOfTheDay.get(randomIndex);
    }

}
