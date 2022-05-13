package com.pjqdev.nirvana.demo.database;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IssuesDB {
        private static final String INSERT_ISSUE = "INSERT INTO issues (id, node_id, number, title, url, repository_url," +
                " html_url, state, locked, created_at, updated_at, closed_at, body, timeline_url, username, user_url)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS issues (" +
                "id INT PRIMARY KEY NOT NULL, " +
                "node_id TEXT NOT NULL, " +
                "number INT NOT NULL, " +
                "title TEXT, " +
                "url TEXT NOT NULL, " +
                "repository_url TEXT NOT NULL," +
                "html_url TEXT NOT NULL, " +
                "state TEXT, " +
                "locked BOOLEAN NOT NULL, " +
                "created_at TIMESTAMP, " +
                "updated_at TIMESTAMP, " +
                "closed_at TIMESTAMP, " +
                "body TEXT, " +
                "timeline_url TEXT, " +
                "username TEXT, " +
                "user_url TEXT)";

        public static LocalDate getTwoWeeksAgo() {
            LocalDate today = LocalDate.now();

            return today.minusWeeks(2);
        }

        public static Timestamp stringToTimestamp(String time) {
            try {
                if (time == null) return null;

                time = time.replace("T", " ");
                time = time.replace("Z", "");
                Timestamp timestamp = Timestamp.valueOf(time);
                System.out.println(timestamp);

                return timestamp;
            } catch(Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        public static void populateDB(JSONArray jsonData) {
            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/nirvanaDB",
                                "postgres", "root");
                Statement stmt = connection.createStatement();
//                String sql = "DELETE from issues;";
                stmt.executeUpdate(CREATE_TABLE);
                stmt.close();
                for (Object jsonDatum : jsonData) {
                    JSONObject issue = (JSONObject) jsonDatum;
                    JSONObject user = (JSONObject) issue.get("user");

                    long id = issue.get("id") != null ? (long) issue.get("id") : 0;
                    String node_id = issue.get("node_id") != null ? (String) issue.get("node_id") : " ";
                    long number = issue.get("number") != null ? (long) issue.get("number") : 0;
                    String title = issue.get("title") != null ? (String) issue.get("title") : " ";
                    String url = issue.get("url") != null ? (String) issue.get("url") : " ";
                    String repository_url = issue.get("repository_url") != null ? (String) issue.get("repository_url") : " ";
                    String html_url = issue.get("html_url") != null ? (String) issue.get("html_url") : " ";
                    String state = issue.get("state") != null ? (String) issue.get("state") : " ";
                    boolean locked = issue.get("locked") != null ? (boolean) issue.get("locked") : false;
                    Timestamp created_at = issue.get("created_at") != null ? stringToTimestamp((String) issue.get("created_at")) : stringToTimestamp("0001-01-01 00:00:00.0");
                    Timestamp updated_at = issue.get("updated_at") != null ? stringToTimestamp((String) issue.get("updated_at")) : stringToTimestamp("0001-01-01 00:00:00.0");
                    Timestamp closed_at = issue.get("closed_at") != null ? stringToTimestamp((String) issue.get("closed_at")) : stringToTimestamp("0001-01-01 00:00:00.0");
                    String body = issue.get("body") != null ? (String) issue.get("body") : " ";
                    String timeline_url = issue.get("timeline_url") != null ? (String) issue.get("timeline_url") : " ";
                    String username = user.get("login") != null ? (String) user.get("login") : " ";
                    String user_url = user.get("url") != null ? (String) user.get("url") : " ";


                    System.out.print(id + ": ");
                    System.out.println(title);

                    var preparedStatement = connection.prepareStatement(INSERT_ISSUE);
                    preparedStatement.setLong(1, id);
                    preparedStatement.setString(2, node_id);
                    preparedStatement.setLong(3, number);
                    preparedStatement.setString(4, title);
                    preparedStatement.setString(5, url);
                    preparedStatement.setString(6, repository_url);
                    preparedStatement.setString(7, html_url);
                    preparedStatement.setString(8, state);
                    preparedStatement.setBoolean(9, locked);
                    preparedStatement.setTimestamp(10, created_at);
                    preparedStatement.setTimestamp(11, updated_at);
                    preparedStatement.setTimestamp(12, closed_at);
                    preparedStatement.setString(13, body);
                    preparedStatement.setString(14, timeline_url);
                    preparedStatement.setString(15, username);
                    preparedStatement.setString(16, user_url);

//                System.out.println(preparedStatement);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void getIssues() {
            try {
                //Public API:
                //https://github.com/facebook/react/issues

                String twoWeeksAgo = String.valueOf(getTwoWeeksAgo());

                URL url = new URL("https://api.github.com/repos/facebook/react/issues?q=is%3Aissue+is%3Aopen+created%3A%3E" + twoWeeksAgo + "+state%3Aopen");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                //Check if connect is made
                int responseCode = conn.getResponseCode();

                // 200 OK
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {
                    System.out.println(url.openStream());
                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    //Close the scanner
                    scanner.close();

                    //JSON simple library Setup with Maven is used to convert strings to JSON
                    JSONParser parse = new JSONParser();
                    JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                    populateDB(dataObject);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
