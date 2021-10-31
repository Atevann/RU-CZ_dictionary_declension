import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class mainClass {
    public static void main(String[] args) {
        new mainGUI();
    }

    // translate word with https://slovniky.lingea.cz/rusko-cesky/
    public ArrayList<String> translateWord(String word){
        ArrayList<String> result = new ArrayList<String>();
        try {
            Connection.Response response=
                    // Connect to URL using method POST with required word
                    Jsoup.connect("https://slovniky.lingea.cz/rusko-cesky/")
                            .method(Connection.Method.POST)
                            .data("word", word)
                            .execute();
            // Save HTML page to variable
            Document translatedWordPage = response.parse();

            // Select table with translation result
            Element table = translatedWordPage.select("table").get(0);

            // Select rows contained required information
            Elements rows = table.select("tr");

            // Get translated word and word gender
            result.add("Слово: \t" + rows.select("h1.lex_ful_entr").text());
            result.add("Род: \t" + rows.select("span.lex_ful_morf").text());

            // Parsing translation table
            for (Element row : rows){

                // Get word translation
                result.add(row.select("span.lex_ful_tran, span.lex_ful_phrs").text());

                // Get translation examples
                Elements examples = row.select("span.lex_ful_coll2, span.lex_ful_samp2, span.lex_ful_idis2");
                for (Element example : examples){
                    result.add("\t *" + example.text());
                }
            }

        } catch (IOException e) {
            result.add("Error");
        }
        catch (IndexOutOfBoundsException e){
            result.add("Слово не найдено");
        }

        return result;
    }


    // Find declination of the word on https://prirucka.ujc.cas.cz/
    public ArrayList<String> sklonenie (String word){
        ArrayList<String> result = new ArrayList<String>();
        try {
            // Connect to URL using method POST with required word
            Connection.Response response=
                    Jsoup.connect("https://prirucka.ujc.cas.cz/")
                            .method(Connection.Method.POST)
                            .data("slovo", word)
                            .execute();

            // Save HTML to variable
            Document skloneniePage = response.parse();

            // Select table with required information
            Element table = skloneniePage.select("table").get(0);

            Elements rows = table.select("tr");
            //Parse rows
            for (Element row : rows){
                Elements columns = row.select("td");

                //Parse columns
                for (Element column : columns){
                    result.add(column.text());
                }
            }

            } catch (Exception e){
            result.add("-"); result.add("-"); result.add("-");
            }

        return result;
    }

}
