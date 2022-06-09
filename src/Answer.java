import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Answer {
	private ArrayList<String> userAnswers;
	private ArrayList<Boolean> ifCorrect;
	private ArrayList<String> correctAnswers;
	private ArrayList<Integer> numbers;
	private ArrayList<String> ifMarked;
	private Connection conn;
	private double score;
	private int mcq;

	public Answer() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("<Answer> constructor: " + e.getMessage());
		}

		userAnswers = new ArrayList<String>();
		ifCorrect = new ArrayList<Boolean>();
		correctAnswers = new ArrayList<String>();
		numbers = new ArrayList<Integer>();
		ifMarked = new ArrayList<String>();
		score = 0;

		count();
	}

	public void count() {
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + RangePanel.getTestID() + "'";
			ResultSet result = stat.executeQuery(query);

			while (result.next()) {
				numbers.add(result.getInt(1));
				userAnswers.add(null);
				ifMarked.add("FALSE");
			}
			result.close();
		} catch (Exception e) {
			System.out.println("<Answer> count: " + e.getMessage());
		}
	}

	public ArrayList<Integer> getNumbers() {
		return this.numbers;
	}

	public void setUserAnswers(int i, String ans) {
		userAnswers.set(i, ans);
	}

	public ArrayList<String> getUserAnswers() {
		return this.userAnswers;
	}

	public void insertUserAnswers() {
		try {
			Statement stat = conn.createStatement();
			for (int i = 0; i < userAnswers.size(); i++) {
				String query = "UPDATE UserQuestion SET UserAnswer = '" + userAnswers.get(i) + "' WHERE Number = '"
						+ numbers.get(i) + "' AND UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
						+ RangePanel.getTestID() + "'";
				stat.execute(query);
			}
		} catch (Exception e) {
			System.out.println("<Answer> insertUserAnswers: " + e.getMessage());
		}
	}

	public ArrayList<String> catchUserAnswers() {
		userAnswers = new ArrayList<String>();
		try {
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT UserAnswer FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + RangePanel.getTestID() + "'";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					userAnswers.add(result.getString(1));
				}
				result.close();
			}
			return userAnswers;
		} catch (Exception e) {
			System.out.println("<Answer> catchUserAnswers: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<Boolean> getIfCorrect(ArrayList<String> userAnswers, ArrayList<String> correctAnswers) {
		System.out.println(userAnswers);
		System.out.println(correctAnswers);
		for (int i = 0; i < userAnswers.size(); i++) {
			if (userAnswers.get(i) != null) {
				if (userAnswers.get(i).equals(correctAnswers.get(i))) {
					ifCorrect.add(true);
				} else {
					ifCorrect.add(false);
				}
			} else {
				ifCorrect.add(false);
			}
		}
		return this.ifCorrect;
	}

	public ArrayList<Boolean> getIfCorrect() {
		return this.ifCorrect;
	}

	public ArrayList<String> catchCorrectAnswers() {
		correctAnswers = new ArrayList<String>();
		try {
			Statement stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT Answer FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + RangePanel.getTestID() + "'";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					correctAnswers.add(result.getString(1));
				}
				result.close();
			}
		} catch (Exception e) {
			System.out.println("<Answer> catchCorrectAnswers: " + e.getMessage());
		}
		return this.correctAnswers;
	}

	public ArrayList<String> getCorrectAnswers() {
		return this.correctAnswers;
	}

	public void setIfMarked(int i, String s) {
		ifMarked.set(i, s);
	}

	public ArrayList<String> getIfMarked() {
		return this.ifMarked;
	}

	public void insertIfMarked() {
		try {
			Statement stat = conn.createStatement();

			for (int i = 0; i < ifMarked.size(); i++) {
				String query = "UPDATE UserQuestion SET Mark = '" + ifMarked.get(i) + "' WHERE Number = '"
						+ numbers.get(i) + "' AND UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
						+ RangePanel.getTestID() + "'";
				stat.execute(query);
			}
		} catch (Exception e) {
			System.out.println("<Answer> insertIfMarked: " + e.getMessage());
		}
	}

	public int getNotMCQ() {
		mcq = 0;
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT * FROM UserQuestion WHERE `MCQ` = 'FALSE'" + " AND UserID = '"
					+ LoginPanel.getUserID() + "' AND TestID = '" + RangePanel.getTestID() + "'";
			ResultSet result = stat.executeQuery(query);
			while (result.next()) {
				mcq++;
			}
			result.close();
		} catch (Exception e) {
			System.out.println("<Answer> qNum: " + e.getMessage());
		}
		return mcq;
	}

	public double getScore() {
		return this.score;
	}

	public double countScore() {
		int correctNum = 0;
		mcq = getNotMCQ();
		score = 0;
		userAnswers = catchUserAnswers();
		correctAnswers = catchCorrectAnswers();
		ifCorrect = getIfCorrect(userAnswers, correctAnswers);

		for (int i = 0; i < mcq; i++) {
			if (ifCorrect.get(i).equals(true)) {
				score += 2;
			}
		}
		for (int i = mcq; i < numbers.size(); i++) {
			if (ifCorrect.get(i).equals(true)) {
				score += 2;
			} else if (ifCorrect.get(i).equals(false) && userAnswers.get(i) != null) {
				for (int m = 0; m < correctAnswers.get(i).length(); m++) {
					for (int n = 0; n < userAnswers.get(i).length(); n++) {
						// 檢測答案選項是否符合考生的選項
						if (correctAnswers.get(i).charAt(m) == userAnswers.get(i).charAt(n)) {
							correctNum += 1; // 答對一個選項
						}
					}
				}
				if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) > 2) {
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 2) {
					score += 0.4;
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 1) {
					score += 1.2;
					correctNum = 0;
				} else if ((correctAnswers.get(i).length() + userAnswers.get(i).length()) - (2 * correctNum) == 0) {
					score += 2;
					correctNum = 0;
				}
			}
			// 所有選項均答對者，得2分；答錯1個選項者，得 1.2分；答錯2個選項者，得0.4分；答錯多於2個選項或所有選項均未作答者，該題以零分計算。
		}
		return score;
	}
}
