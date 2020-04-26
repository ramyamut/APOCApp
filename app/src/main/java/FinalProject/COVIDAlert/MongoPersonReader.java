package FinalProject.COVIDAlert;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MongoPersonReader {

    private String url;

    public MongoPersonReader() {
        this.url = "http://10.0.2.2:3000";
    }

    public Person getPerson(int id) {
        List<Person> people = getAllPeople();
        for(Person person: people) {
            if(id == person.getId()) {
                return person;
            }
        }
        return null;
    }

    public List<Person> getRegionMatches(String region) {
        List<Person> people = getAllPeople();
        List<Person> matches = new LinkedList<Person>();
        for(Person person: people) {
            if(person.getRegion().equalsIgnoreCase(region)) {
                matches.add(person);
            }
        }
        return matches;
    }

    public List<Person> getAllPeople() {
        String urlStr = this.url + "/getAllPeople";
        try {
            URL urlObj = new URL(urlStr);
            GetPeopleTask task = new GetPeopleTask();
            task.execute(urlObj);
            List<Person> people = task.get();
            return people;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    class GetPeopleTask extends AsyncTask<URL, String, List<Person>> {

        @Override
        protected List<Person> doInBackground(URL... urls) {
            try {
                List<Person> people = new LinkedList<Person>();
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONArray data = new JSONArray(line);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject json = data.getJSONObject(i);
                        Person person = new Person(json);
                        people.add(person);
                    }
                }
                return people;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean setTestPos(int id) {
        String urlStr = this.url + "/testPos?idNum=" + id;
        try {
            URL urlObj = new URL(urlStr);
            SetTestPosTask task = new SetTestPosTask();
            task.execute(urlObj);
            boolean success = task.get();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    class SetTestPosTask extends AsyncTask<URL, String, Boolean> {

        @Override
        protected Boolean doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                String success="";
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject jsonObject = new JSONObject(line);
                    success = jsonObject.getString("status");
                }
                if(success.equalsIgnoreCase("success")) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

}
