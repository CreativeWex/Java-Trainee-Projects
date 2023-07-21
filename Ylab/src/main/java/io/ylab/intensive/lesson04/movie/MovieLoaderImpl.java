package io.ylab.intensive.lesson04.movie;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
  private final DataSource dataSource;
  private final List<Movie> movies = new ArrayList<>();

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // Можно было считывать из файла в объект и сразу добавлять в Batch prepared Statement (сделал так с сортировкой)
  // Но я решил реализовать через предварительное сохранение объектов в список
  // 1. Чтобы по SRP сепарировать методы
  // 2. Чтобы быстрее высвобождать открытый файл и не держать его открытым в тот момент, когда происходит запись в бд
  // Советовался по этому способу в чате, замеряли по времени, вроде даже мой способ быстрее вышеописанного
  // В любом случае, про существование альтернативного решения знаю, это показалось более правильным, буду рад фидбеку на этот счет

  public void saveToDatabase() throws SQLException {
    String query = "INSERT INTO movie(year, length, title, subject, actors, actress, director, popularity, awards) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      for (Movie movie : movies) {
        if (movie.getYear() == null) {
          preparedStatement.setNull(1, Types.INTEGER);
        } else {
          preparedStatement.setInt(1, movie.getYear());
        }
        if (movie.getLength() == null) {
          preparedStatement.setNull(2, Types.INTEGER);
        } else {
          preparedStatement.setInt(2, movie.getLength());
        }
        preparedStatement.setString(3, movie.getTitle());
        preparedStatement.setString(4, movie.getSubject());
        preparedStatement.setString(5, movie.getActors());
        preparedStatement.setString(6, movie.getActress());
        preparedStatement.setString(7, movie.getDirector());
        if (movie.getPopularity() == null) {
          preparedStatement.setNull(8, Types.INTEGER);
        } else {
          preparedStatement.setInt(8, movie.getPopularity());
        }
        if (movie.getAwards() == null) {
          preparedStatement.setNull(9, Types.BOOLEAN);
        } else {
          preparedStatement.setBoolean(9, movie.getAwards());
        }
        preparedStatement.addBatch();
      }
      preparedStatement.executeBatch();
    }
  }

  @Override
  public void loadData(File file) {
    try (FileInputStream fileInputStream = new FileInputStream(file);
         Scanner scanner = new Scanner(fileInputStream)) {
      scanner.nextLine();
      scanner.nextLine();

      while (scanner.hasNextLine()) {
        String[] input = scanner.nextLine().split(";");
        Movie movie = new Movie();
        if (!input[0].isEmpty()) {
          movie.setYear(Integer.parseInt(input[0]));
        }
        if (!input[1].isEmpty()) {
          movie.setLength(Integer.parseInt(input[1]));
        }
        movie.setTitle(input[2]);
        movie.setSubject(input[3]);
        movie.setActors(input[4]);
        movie.setActress(input[5]);
        movie.setDirector(input[6]);
        if (!input[7].isEmpty()) {
          movie.setPopularity(Integer.parseInt(input[7]));
        }
        if (!input[8].isEmpty()) {
          movie.setAwards(Boolean.getBoolean(input[8]));
        }
        movies.add(movie);
      }
      saveToDatabase();
    } catch (IOException | SQLException e) {
      System.err.println("Loading movie data error " + e.getMessage());
    }
  }
}